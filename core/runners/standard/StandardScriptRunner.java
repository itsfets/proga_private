package core.runners.standard;

import console.ExecutionResponse;
import console.standard.Console;
import core.managers.standard.CommandManager;
import core.runners.RunnerTemplate;

public class StandardScriptRunner implements RunnerTemplate {
    private final Console console;
    private final CommandManager commandManager;

    public StandardScriptRunner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        int lineCounter = 0;
        while (console.isCanReadln()) {
            lineCounter++;
            String line;
            try {
                line = console.readln().trim();
            } catch (Exception e) {
                console.println("Error while reading the line " + lineCounter + ": " + e.getMessage());
                continue;
            }
            if (line.isEmpty()) continue;
            console.println("[" + lineCounter + "] = " + line);
            String[] parts = line.split(" ", 2);
            String cmdName = parts[0];
            String args = parts.length > 1 ? parts[1].trim() : "";
            var command = commandManager.getCommands().get(cmdName);
            if (command != null) {
                try {
                    ExecutionResponse res = command.apply(new String[]{cmdName, args});

                    if ("exit".equalsIgnoreCase(cmdName)) {
                        console.println("Script was interupted by exit command!");
                        return;
                    }
                    if (res != null && res.getMessage() != null && !res.getMessage().isBlank()) {
                        console.println(res.getMessage());
                    }
                } catch (IllegalStateException e) {
                    throw e;
                } catch (RuntimeException e) {
                    console.println("Error in command " + '"' + cmdName + '"' +" (at line " + lineCounter + "): " + e.getMessage());
                }
            } else {
                console.printError("Unknown command: " + cmdName + " (at line " + lineCounter + ")");
            }
        }
    }
}