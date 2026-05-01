package console.commands;

import console.ExecutionResponse;
import console.standard.Console;
import core.managers.CollectionManager;

public class Save extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Save(Console console, CollectionManager collectionManager) {
        super("save", "writes Collection to a file");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        try {
            if (!arguments[1].isBlank()) return new ExecutionResponse().noArgExecutionMessage();
            collectionManager.saveCollection();
            return new ExecutionResponse("Saved successfully!", true);
        } catch (NullPointerException e) {
            return new ExecutionResponse("No such environment variable found!", false);
        }
    }
}
