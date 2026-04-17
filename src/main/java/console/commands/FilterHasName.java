package console.commands;

import console.StandardConsole;
import core.managers.StandardCollectionManager;
import core.models.ExecutionResponse;

public class FilterHasName extends Command {
    private final StandardCollectionManager collectionManager;

    public FilterHasName(StandardConsole console, StandardCollectionManager collectionManager) {
        super("filter_contains_name <name>", "returns all StudyGroups, who have set substring in their name field");
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (arguments[1].isEmpty()) return new ExecutionResponse().wrongArgCountMessage();
        String filter =  arguments[1];
        StringBuilder s = new StringBuilder("Result:\n");
        for (var group : collectionManager.getCollection()) {
            if (group.getName().toLowerCase().contains(filter.toLowerCase())) {
                s.append(group).append("\n");
            }
        } return new ExecutionResponse(s.toString());
    }
}
