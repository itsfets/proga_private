package console.commands;

import console.Console;
import console.ExecutionResponse;
import core.managers.standard.CollectionManager;

public class Show extends Command {
    private final CollectionManager collectionManager;

    public Show(Console console, CollectionManager collectionManager) {
        super("show", "Prints out string representation of every StudyGroup in the Collection");
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isBlank()) return new ExecutionResponse().noArgExecutionMessage();
        String s = collectionManager.toString();
        return new ExecutionResponse(s);
    }
}
