package console.commands;

import console.StandardConsole;
import core.managers.StandardCollectionManager;
import core.models.ExecutionResponse;

public class Clear extends Command {
    private final StandardConsole console;
    private final StandardCollectionManager collectionManager;

    public Clear(StandardConsole console, StandardCollectionManager collectionManager) {
        super("clear", "Clears the Collection");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] argument) {
        if (!argument[1].isEmpty()) return new ExecutionResponse().noArgExecutionMessage();
        collectionManager.clearCollection();
        return new ExecutionResponse("Successfully cleared collection!");
    }
}
