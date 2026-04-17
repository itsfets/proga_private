package console.commands;

import console.StandardConsole;
import core.managers.StandardCollectionManager;
import core.models.ExecutionResponse;

public class RemoveById extends Command {
    private final StandardConsole console;
    private final StandardCollectionManager collectionManager;

    public RemoveById(StandardConsole console, StandardCollectionManager collectionManager) {
        super("remove_by_id <id>", "Removes StudyGroup with given ID");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (arguments[1].isEmpty()) return new ExecutionResponse().wrongArgCountMessage();
        try {
            collectionManager.remove(Integer.parseInt(arguments[1]));
        } catch (NumberFormatException e) {
            return new ExecutionResponse("This command's argument requires an integer!", false);
        }
        return new ExecutionResponse("Removed StudyGroup successfully!", true);
    }
}
