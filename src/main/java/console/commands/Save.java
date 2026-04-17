package console.commands;

import console.StandardConsole;
import core.managers.StandardCollectionManager;
import core.models.ExecutionResponse;

public class Save extends Command {
    private final StandardConsole console;
    private final StandardCollectionManager collectionManager;

    public Save(StandardConsole console, StandardCollectionManager collectionManager) {
        super("save", "writes Collection to a file");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) return new ExecutionResponse().wrongArgCountMessage();
            collectionManager.saveCollection(arguments[1]);
        } catch (NullPointerException e) {
            console.println("No such environment variable found!");
        }
        return new ExecutionResponse("", true);
    }
}
