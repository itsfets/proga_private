package console.commands;

import console.ExecutionResponse;
import console.standard.Console;
import core.managers.CollectionManager;

public class Clear extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public Clear(Console console, CollectionManager collectionManager) {
        super("clear", "Clears the Collection");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] argument) {
        if (!argument[1].isBlank()) return new ExecutionResponse().noArgExecutionMessage();
        collectionManager.clearCollection();
        return new ExecutionResponse("Successfully cleared collection!");
    }
}
