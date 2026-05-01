package console.commands;

import console.Console;
import console.ExecutionResponse;
import core.managers.CollectionManager;

public class RemoveById extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public RemoveById(Console console, CollectionManager collectionManager) {
        super("remove_by_id <id>", "Removes StudyGroup with given ID");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (arguments[1].isBlank()) return new ExecutionResponse().wrongArgCountMessage();
        Boolean success;
        try {
            success = collectionManager.remove(Integer.parseInt(arguments[1]));
        } catch (NumberFormatException e) {
            return new ExecutionResponse("This command's argument requires an integer!", false);
        }
        if (success) return new ExecutionResponse("Removed StudyGroup successfully!", true);
        else return new ExecutionResponse("Failed to remove StudyGroup as there is no StudyGroup with given ID!", false);
    }
}
