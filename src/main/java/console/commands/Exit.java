package console.commands;

import console.StandardConsole;
import core.managers.StandardCollectionManager;
import core.models.ExecutionResponse;

public class Exit extends Command {
    private final StandardConsole console;

    public Exit(StandardConsole console) {
        super("exit", "exits the program WITHOUT SAVING THE COLLECTION");
        this.console = console;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse().noArgExecutionMessage();
        return new ExecutionResponse("exit");
    }
}
