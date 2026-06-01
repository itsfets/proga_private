package core;

import commands.Command;
import console.CommandList;
import console.Console;
import network.Request;

import java.util.concurrent.BlockingQueue;

public class InteractiveInputModule implements Runnable {
    private final Console console;
    private final CommandList commandList;
    private final BlockingQueue<Request> queue;
    private volatile boolean running = true;

    public InteractiveInputModule(Console console, CommandList commandList, BlockingQueue<Request> queue) {
        this.console = console;
        this.commandList = commandList;
        this.queue = queue;
    }

    @Override
    public void run() {
        while (running) {
            try {
                console.prompt();
                String line = console.readln();
                if (line == null) continue;
                line = line.trim();
                if (line.isEmpty()) continue;
                if (line.equals("exit")) {
                    console.println("exiting...");
                    stop();
                    System.exit(0);
                }
                String[] parts = line.split(" ", 2);
                String cmdName = parts[0].trim();
                String args = parts.length > 1 ? parts[1].trim() : "";

                Command cmd = commandList.getCommandMap().get(cmdName);
                if (cmd != null) {
                    Request req = cmd.apply(new String[]{cmdName, args});
                    if (req != null) {
                        if (req.getCommand().getString().equalsIgnoreCase("execute_script")) {

                        }
                        queue.put(req);
                    }
                } else {
                    console.println("unknown command: " + cmdName + ". type 'help' for a list of available commands.");
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                console.println("interrupted.");
                break;
            }
        }
    }

    public void stop() {
        running = false;
    }
}
