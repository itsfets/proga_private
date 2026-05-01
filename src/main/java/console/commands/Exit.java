package console.commands;

import console.ExecutionResponse;
import console.standard.Console;

public class Exit extends Command {
    private final Console console;

    public Exit(Console console) {
        super("exit", "exits the program WITHOUT SAVING THE COLLECTION");
        this.console = console;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isBlank()) return new ExecutionResponse().noArgExecutionMessage();
        return new ExecutionResponse("Exiting...");
    }
}
