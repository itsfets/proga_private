package core.runners.standard;

import console.Console;
import core.managers.standard.CommandManager;
import console.ExecutionResponse;
import core.runners.RunnerTemplate;

public class StandardInteractiveRunner implements RunnerTemplate {
    private boolean running = true;
    private final Console console;
    private final CommandManager commandManager;

    public StandardInteractiveRunner(Console console, CommandManager commandManager) {
        this.console = console;
        this.commandManager = commandManager;
    }

    @Override
    public void run() {
        String[] command = {"", ""};
        while (running) {
            console.prompt();
            if (!console.isCanReadln()) {
                console.println("Recieved close signal! Exiting without saving!");
                stop();
                break;
            }
            String line = console.readln().trim() + " ";
            if (line.isBlank()) {
                continue;
            }
            command = (line.split(" ", 2));
            command[1] = command[1].trim();
            try {
                var commandObj = commandManager.getCommands().get(command[0]);
                if (commandObj != null) {
                    ExecutionResponse res = commandObj.apply(command);
                    if (commandObj.getName().equalsIgnoreCase("exit")) {
                        running = false;
                    }
                    console.println(res.getMessage());
                }
                else {console.println("No command " + command[0] + " exists! Use " + '"' + "help" + '"' + " for a list of available commands.");}
            } catch (RuntimeException e) {
                console.println("Unexpected error when executing the command: " + e.getMessage());
            }
        }
        System.exit(0);
    }

    private void stop() {
        this.running = false;
    }
}