package core;

import commands.Command;
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


public class Runner implements Runnable {
    private final int port;
    private final ServerSocketChannel serverSocket = ServerSocketChannel.open();
    private final Selector selector = Selector.open();
    private final Serializator serial = new Serializator();
    private final CommandList commands;
    private volatile boolean running = true;


    public Runner(int port, CommandList commands) throws IOException {
        this.port = port;
        this.commands = commands;
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
                        if (key.isReadable()) read(key);
                    } catch (IOException e) {
                        close(key);
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void accept(SelectionKey key) throws IOException {
        SocketChannel socketChannel = ((ServerSocketChannel) key.channel()).accept();
        if (socketChannel != null) {
            socketChannel.configureBlocking(false);
            System.out.println("accepted connection from client: " + socketChannel.getRemoteAddress());
            socketChannel.register(selector, SelectionKey.OP_READ, socketChannel);
        }
    }

    private void read(SelectionKey key) throws IOException {
        ByteBuffer lenBuffer = ByteBuffer.allocate(4);
        int len = tryRead(lenBuffer, key).flip().getInt();
        ByteBuffer readBuffer = ByteBuffer.allocate(len);
        tryRead(readBuffer, key).flip();
        byte[] data = new byte[len];
        readBuffer.get(data);
        try {
            Request req = (Request) serial.deserialize(data);
            if (req != null) {
                System.out.println(req);
                Command command = commands.getCommands().get(req.getCommand().getString());
                if (command != null) {
                    Response response = command.apply(req.getData());
                    key.interestOps(SelectionKey.OP_WRITE);
                    write(key, response);
                    key.interestOps(SelectionKey.OP_READ);
                } else System.out.println("command is null");
            }
        } catch (ClassNotFoundException e) {
            System.out.println("failed to deserialize: " + e.getMessage());
        }
    }

    private void write(SelectionKey key, Response response) throws IOException {
        SocketChannel sc = (SocketChannel) key.attachment();
        byte[] data = serial.serialize(response);
        ByteBuffer writeBuffer = ByteBuffer.allocate(4 + data.length);
        writeBuffer.clear().putInt(data.length).put(data).flip();
        while (writeBuffer.hasRemaining()) {
            int written = sc.write(writeBuffer);
            if (written == -1) {
                throw new IOException("connection closed");
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
        SocketChannel sc = (SocketChannel) key.attachment();
        try {
            System.out.println("connection closed: " + sc.getRemoteAddress());
        } catch (IOException e) {
            System.out.println("failed to close: " + e.getMessage());
        } finally {
            key.cancel();
        }
    }

    public void stop() {
        running = false;
    }
}