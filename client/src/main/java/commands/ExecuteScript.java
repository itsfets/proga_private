package commands;

import console.Console;
import core.Runner;
import core.ScriptRunner;
import dto.Commands;
import network.Request;

import java.io.File;

public class ExecuteScript extends Command {
    private final Console console;
    private final Runner runnerInstance;

    public ExecuteScript(Console console, Runner runnerInstance) {
        super("execute_script <file_name>", "reads and executes commands from a given script file. Commands must be written in the same way you would write them here");
        this.console = console;
        this.runnerInstance = runnerInstance;
    }

    @Override
    public Request apply(String[] arguments) {
        if (arguments[1].isBlank()) {
            console.println("this command requires an argument!");
            return null;
        }
        String fileName = arguments[1].trim();
        File scriptFile = new File(fileName);
        if (!scriptFile.exists() || !scriptFile.canRead()) {
            console.println("file not found or is unaccessible: " + fileName);
        }
        new Thread(() -> {
            ScriptRunner scriptRunner = new ScriptRunner(runnerInstance, console);
            scriptRunner.execute(fileName);
        }, "ScriptExecutor-" + fileName).start();
        return new Request(Commands.EXECUTESCRIPT, scriptFile);
    }
}