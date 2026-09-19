package core;

import commands.Command;
import database.Manager;
import network.Request;
import network.Response;
import network.Serializator;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ForkJoinPool;


public class Runner implements Runnable {
    private final int port;
    private final ServerSocketChannel serverSocket;
    private final Selector selector;
    private final Serializator serial = new Serializator();
    private final CommandList commands;
    private volatile boolean running = true;
    private final Manager dbManager;
    private final ForkJoinPool forkJoinPool = new ForkJoinPool();

    public Runner(int port, CommandList commands, Manager dbManager) throws IOException {
        this.port = port;
        this.commands = commands;
        this.dbManager = dbManager;
        this.serverSocket = ServerSocketChannel.open();
        this.selector = Selector.open();
    }

    @Override
    public void run() {
        try {
            serverSocket.configureBlocking(false);
            serverSocket.bind(new InetSocketAddress("localhost", port));
            serverSocket.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("server started and waiting for connection on port: " + port);
            while (running) {
                selector.select();
                var keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    keyIterator.remove();
                    try {
                        if (key.isAcceptable()) accept(key);
                        if (key.isReadable()) readAsynchronously(key);
                    } catch (IOException e) {
                        System.out.println("Error handling key: " + e.getMessage());
                        close(key);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            stop();
        }
    }

    private void accept(SelectionKey key) throws IOException {
        ServerSocketChannel server = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = server.accept();
        if (socketChannel != null) {
            socketChannel.configureBlocking(false);
            System.out.println("accepted connection from client: " + socketChannel.getRemoteAddress());
            socketChannel.register(selector, SelectionKey.OP_READ, socketChannel);
        }
    }

    private void readAsynchronously(SelectionKey key) throws IOException {
        key.interestOps(key.interestOps() & ~SelectionKey.OP_READ);
        forkJoinPool.submit(() -> {
            SocketChannel sc = (SocketChannel) key.channel();
            try {
                ByteBuffer lenBuffer = read(sc, 4);
                lenBuffer.flip();
                int len = lenBuffer.getInt();
                if (len <= 0 || len > 10_000_000) {
                    throw new IOException("Invalid data length: " + len);
                }
                ByteBuffer dataBuffer = read(sc, len);
                dataBuffer.flip();
                byte[] data = new byte[len];
                dataBuffer.get(data);
                Request req = (Request) serial.deserialize(data);
                if (req == null) {
                    throw new IOException("Failed to deserialize request");
                }
                System.out.println("Received request: " + req);
                Thread processThread = new Thread(() -> {
                    try {
                        Command command = commands.getCommands().get(req.command().getString());
                        Response response;
                        if (command == null) {
                            response = new Response(false, "Unknown command");
                        } else {
                            response = dbManager.tryAuth(req.login(), req.password())
                                    ? command.apply(req.data(), req.login())
                                    : new Response(false, Response.INVALIG_AUTH);
                        }
                        Thread sendThread = new Thread(() -> {
                            try {
                                write(sc, response);
                                key.interestOps(key.interestOps() | SelectionKey.OP_READ);
                                selector.wakeup();
                            } catch (IOException e) {
                                System.out.println("Failed to send response: " + e.getMessage());
                                close(key);
                            }
                        });
                        sendThread.start();
                    } catch (Exception e) {
                        System.out.println("Processing error: " + e.getMessage());
                        close(key);
                    }
                });
                processThread.start();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Read/Deserialize error: " + e.getMessage());
                close(key);
            }
        });
    }

    private ByteBuffer read(SocketChannel sc, int capacity) throws IOException {
        ByteBuffer buffer = ByteBuffer.allocate(capacity);
        while (buffer.hasRemaining()) {
            int read = sc.read(buffer);
            if (read == -1) {
                throw new IOException("Connection closed by client");
            }
            if (read == 0) {
                Thread.yield();
            }
        }
        return buffer;
    }


    private void write(SocketChannel sc, Response response) throws IOException {
        byte[] data = serial.serialize(response);
        ByteBuffer writeBuffer = ByteBuffer.allocate(4 + data.length);
        writeBuffer.putInt(data.length).put(data).flip();
        while (writeBuffer.hasRemaining()) {
            int written = sc.write(writeBuffer);
            if (written == -1) {
                throw new IOException("Connection closed during write");
            }
        }
    }

    private ByteBuffer tryRead(ByteBuffer buffer, SelectionKey key) throws IOException {
        SocketChannel sc = (SocketChannel) key.attachment();
        try {
            int read = sc.read(buffer);
            if (read == -1) {
                throw new IOException("connection closed");
            } else return buffer;
        } catch (IOException e) {
            throw new IOException("connection closed");
        }
    }

    private void close(SelectionKey key) {
        SocketChannel sc = (SocketChannel) key.channel();
        try {
            System.out.println("Connection closed: " + sc.getRemoteAddress());
        } catch (IOException e) {
            System.out.println("Failed to get remote address on close");
        } finally {
            key.cancel();
            try {
                sc.close();
            } catch (IOException e) {
                System.out.println("Failed to close socket channel: " + e.getMessage());
            }
        }
    }

    public void stop() {
        running = false;
        selector.wakeup();
        forkJoinPool.shutdown();
        try {
            serverSocket.close();
            selector.close();
        } catch (IOException e) {
            System.out.println("Failed to close server resources: " + e.getMessage());
        }
    }
}