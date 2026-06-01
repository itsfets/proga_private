package core;

import commands.Command;
import console.CommandList;
import console.Console;
import dto.Commands;
import network.Request;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ScriptRunner {
    private final Runner runner;
    private final Console console;
    private final CommandList commands;

    public ScriptRunner(Runner runner, Console console) {
        this.runner = runner;
        this.console = console;
        this.commands = runner.getCommandList();
    }

    public void execute(String fileName) {
        File scriptFile = new File(fileName);
        if (!scriptFile.exists() || !scriptFile.canRead()) {
            console.println("file not found or is unaccessible: " + fileName);
            return;
        }
        Scanner fileScanner = null;
        try {
            fileScanner = new Scanner(scriptFile);
            console.selectFileSC(fileScanner);
            int lineCounter = 0;
            console.println("executing script: " + fileName);
            while (console.isCanReadln() && runner.isConnected()) {
                lineCounter++;
                String line = console.readln().trim();
                if (line.isEmpty() || line.startsWith("#")) continue;
                console.println("[" + lineCounter + "] " + line);
                String[] parts = line.split(" ", 2);
                String cmdName = parts[0].toLowerCase();
                String args = parts.length > 1 ? parts[1].trim() : "";
                Command cmd = commands.getCommandMap().get(cmdName);
                if (cmd == null) {
                    console.println("unknown command: " + cmdName + " (line " + lineCounter + ")");
                    continue;
                }
                try {
                    Request req = cmd.apply(new String[]{cmdName, args});
                    if (req != null) {
                        runner.sendRequest(req);
                    }
                    if ("exit".equalsIgnoreCase(cmdName)) {
                        console.println("execution was stopped by exit command");
                        return;
                    }
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException ignored) {
                    }
                } catch (IllegalStateException e) {
                    if ("CREATION_CANCELLED".equals(e.getMessage())) {
                        console.println("exit command executed");
                        continue;
                    }
                    if ("END_OF_INPUT".equals(e.getMessage())) {
                        console.println("script file ended unexpectedly. stopping...");
                        return;
                    }
                    console.println("error in command '" + cmdName + "' (line " + lineCounter + "): " + e.getMessage());
                    console.println("continuing script execution...");
                } catch (Exception e) {
                    console.println("error in command '" + cmdName + "' (line " + lineCounter + "): " + e.getMessage());
                    console.println("continuing script execution...");
                }
            }

            if (!runner.isConnected()) {
                console.println("connection lost");
            } else {
                console.println("script executed successfully");
            }

        } catch (Exception e) {
            console.println("failed to execute script: " + e.getMessage());
        } finally {
            console.selectConsoleSC();
            if (fileScanner != null) fileScanner.close();
        }
    }
}
