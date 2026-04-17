package console.commands;

import console.StandardConsole;
import core.managers.StandardCollectionManager;
import core.models.ExecutionResponse;

public class Show extends Command {
    private final StandardCollectionManager collectionManager;

    public Show(StandardConsole console, StandardCollectionManager collectionManager) {
        super("show", "Prints out string representation of every StudyGroup in the Collection");
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse().noArgExecutionMessage();
        String s = collectionManager.toString();
        return new ExecutionResponse(s);
    }
}
