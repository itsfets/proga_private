package core;

import console.CommandList;
import console.Console;
import network.Request;
import network.Response;
import network.Serializator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Runner implements Runnable {
    private final int port;
    private final BlockingQueue<Response> respQueue = new LinkedBlockingQueue<>();
    private final Selector selector = Selector.open();
    private final Serializator serial = new Serializator();
    private final BlockingQueue<Request> queue = new LinkedBlockingQueue<>();
    private final CommandList commandList;
    private final Console console;
    private SocketChannel sc;
    private volatile boolean connected;
    private volatile boolean running;

    public Runner(int port, CommandList commandList, Console console) throws IOException {
        this.port = port;
        this.commandList = commandList;
        this.console = console;
        this.running = true;
    }

    @Override
    public void run() {
        try {
            try_connect();
            while (running) {
                if (selector.select(1000) > 0) {
                    var keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        var key = keys.next();
                        keys.remove();
                        if (!key.isValid()) continue;
                        if (key.isValid() && key.isConnectable()) connect(key);
                        if (key.isValid() && key.isReadable()) read(key);
                        if (key.isValid() && key.isWritable()) write(key);
                        if (connected) update(key);
                    }
                }
                checkQueue();
            }
        } catch (IOException e) {
            console.println("i/o exception happened: " + e.getMessage());
            reconnect(null);
        }
    }

    private void reconnect(SelectionKey key) {
        try {
            key.cancel();
            sc.close();
        } catch (IOException | NullPointerException ignored) {
        }
        connected = false;
        new Thread(() -> {
            try {
                Thread.sleep(1000);
                if (running && !connected) {
                    try_connect();
                }
            } catch (InterruptedException ignored) {
            }
        }).start();
    }

    private void try_connect() {
        try {
            sc = SocketChannel.open();
            sc.configureBlocking(false);
            sc.register(selector, SelectionKey.OP_CONNECT, sc);
            sc.connect(new InetSocketAddress(port));
        } catch (IOException e) {
            console.println("couldnt reach the server, retrying...");
            if (running && !connected) {
                new Thread(() -> {
                    try {
                        Thread.sleep(1000);
                        if (running && !connected) {
                            try_connect();
                        }
                    } catch (InterruptedException ignored) {
                    }
                }).start();
            }
        }
    }

    private void connect(SelectionKey key) throws IOException {
        SocketChannel socketChannel = (SocketChannel) key.attachment();
        try {
            if (socketChannel.finishConnect()) {
                connected = true;
                console.println("connected to server on " + socketChannel.getRemoteAddress() + "\nstarting console thread...");
                InteractiveInputModule ioMod = new InteractiveInputModule(console, commandList, queue);
                Thread ioThread = new Thread(ioMod);
                ioThread.setDaemon(true);
                ioThread.start();
                update(key);
            } else console.println("failed to finalize connection");
        } catch (IOException e) {
            console.println("failed to connect: " + e.getMessage() + ", retrying...");
            reconnect(key);
        }
    }

    private ByteBuffer tryRead(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.attachment();
        try {
            int read = sc.read(buffer);
            if (read == -1) {
                connected = false;
                throw new IOException("connection closed");
            } else return buffer;
        } catch (IOException e) {
            throw new IOException("connection closed");
        }
    }


    private void read(SelectionKey key) throws IOException {
        try {
            if (!key.isValid()) return;
            ByteBuffer lenBuffer = ByteBuffer.allocate(4);
            int len = tryRead(lenBuffer, key).flip().getInt();
            ByteBuffer readBuffer = ByteBuffer.allocate(len);
            tryRead(readBuffer, key).flip();
            byte[] data = new byte[len];
            readBuffer.get(data);
            try {
                Response resp = (Response) serial.deserialize(data);
                if (resp != null) {
                    respQueue.offer(resp);
                    if (resp.isSuccess()) {
                        console.println(resp.getMessage());
                        if (resp.getData() != null && !resp.getData().isBlank()) {
                            console.println(resp.getData());
                        }
                    } else {
                        console.println(resp.getMessage());
                    }
                }
            } catch (ClassNotFoundException e) {
                System.out.println("failed to deserialize: " + e.getMessage());
            }
            key.interestOpsAnd(~SelectionKey.OP_READ);
            selector.wakeup();
        } catch (IOException e) { // <-- Обработка потери соединения
            console.println("connection lost during read: " + e.getMessage() + ", retrying...");
            reconnect(key);
        }
    }

    private void write(SelectionKey key) throws IOException {
        try {
            if (!key.isValid()) return;
            Request req = queue.remove();
            byte[] data = serial.serialize(req);
            ByteBuffer writeBuffer = ByteBuffer.allocate(4 + data.length);
            writeBuffer.clear().putInt(data.length).put(data).flip();
            while (writeBuffer.hasRemaining()) {
                int written = sc.write(writeBuffer);
                if (written == -1) {
                    connected = false;
                }
            }
            key.interestOpsAnd(~SelectionKey.OP_WRITE);
            selector.wakeup();
        } catch (IOException e) {
            console.println("connection lost during write: " + e.getMessage() + ", retrying...");
            reconnect(key);
        }
    }

    private void update(SelectionKey key) throws IOException {
        if (!key.isValid()) return;
        int ops = SelectionKey.OP_READ;
        if (queue.peek() != null) {
            ops |= SelectionKey.OP_WRITE;
        }
        key.interestOps(ops);
        selector.wakeup();
    }

    private void checkQueue() {
        if (queue.peek() == null) return;
        for (SelectionKey key : selector.keys()) {
            if (key.attachment() != sc) continue;
            if (!key.isValid()) continue;
            key.interestOpsOr(SelectionKey.OP_WRITE);
            selector.wakeup();
            break;
        }
    }

    public BlockingQueue<Response> getRespQueue() {
        return respQueue;
    }

    public boolean isConnected() {
        return connected;
    }

    public void sendRequest(Request request) {
        if (!connected) {
            console.println("not connected to server");
            return;
        }
        queue.offer(request);
        selector.wakeup();
    }

    public CommandList getCommandList() {
        return commandList;
    }

    public void stop() {
        running = false;
        selector.wakeup();
    }
}