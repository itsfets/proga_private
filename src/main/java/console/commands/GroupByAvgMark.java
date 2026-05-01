package console.commands;

import console.Console;
import console.ExecutionResponse;
import core.managers.CollectionManager;

import java.util.HashMap;
import java.util.Map;

public class GroupByAvgMark extends Command {
    private final CollectionManager collectionManager;

    public GroupByAvgMark(Console console, CollectionManager collectionManager) {
        super("group_counting_by_average_mark", "Groups StudyGroups by value of averageMark field and returns size of each group");
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isBlank()) return new ExecutionResponse().noArgExecutionMessage();
        Map<String, Integer> dict = new HashMap<>();
        for (var group : collectionManager.getCollection()) {
            dict.merge(Double.toString(group.getAverageMark()), 1, Integer::sum);
        }
        StringBuilder s = new StringBuilder("There are following avgMark groups in the Collection: \n");
        for (var group : dict.entrySet()) {
            s.append(String.format("%1$10s - %2$d group(s)%n", group.getKey(), group.getValue()));
        }
        return new ExecutionResponse(s.toString());
    }
}
