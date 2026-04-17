package console.commands;

import console.StandardConsole;
import core.models.ExecutionResponse;

import java.nio.file.Files;
import java.nio.file.Paths;

public class ExecuteScript extends Command {
    private final StandardConsole console;

    public ExecuteScript(StandardConsole console) {
        super("execute_script <file_name>", "Reads and executes commands from a given script file. Commands must be written in the same way you would write them here");
        this.console = console;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (arguments[1].isEmpty()) return new ExecutionResponse().wrongArgCountMessage();
        if (!Files.isRegularFile(Paths.get(System.getenv(arguments[1])))) return new ExecutionResponse("Provided environment variable is a directory! Script wasnt executed!");
        return new ExecutionResponse("Executing script from " + arguments[1] + " system variable...");
    }
}