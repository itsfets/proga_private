package console.commands;

import console.standard.Console;
import console.ExecutionResponse;
import core.managers.standard.CommandManager;
import core.runners.standard.StandardScriptRunner;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ExecuteScript extends Command {
    private final Console console;
    private final CommandManager commandManager;

    public ExecuteScript(Console console, CommandManager commandManager) {
        super("execute_script <file_name>", "Reads and executes commands from a given script file. Commands must be written in the same way you would write them here");
        this.console = console;
        this.commandManager = commandManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (arguments[1].isBlank()) return new ExecutionResponse().wrongArgCountMessage();
        String fileName = arguments[1].trim();
        File scriptFile = new File(fileName);
        if (!scriptFile.exists() || !scriptFile.canRead()) {
            return new ExecutionResponse("File not found or is unaccessible: " + fileName, false);
        }
        try {
            console.selectFileScanner(new Scanner(scriptFile));
            StandardScriptRunner runner = new StandardScriptRunner(console, commandManager);
            runner.run();

            return new ExecutionResponse("Script executed successfully!");
        } catch (FileNotFoundException e) {
            return new ExecutionResponse("File not found or is unaccessible: " + fileName, false);
        } finally {
            console.selectConsoleScanner();
        }
    }
}