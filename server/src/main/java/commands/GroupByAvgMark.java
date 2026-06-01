package commands;

import core.StandardCollection;
import network.Response;

import java.util.HashMap;
import java.util.Map;

public class GroupByAvgMark extends Command {
    private final StandardCollection standardCollection;

    public GroupByAvgMark(StandardCollection standardCollection) {
        super("group_counting_by_average_mark", "groups studyGroups by value of averageMark field and returns size of each group");
        this.standardCollection = standardCollection;
    }

    @Override
    public Response apply(Object request_data) {
        Map<String, Integer> dict = new HashMap<>();
        standardCollection.getCollection().forEach(group -> dict.merge(Double.toString(group.getAverageMark()), 1, Integer::sum));
        StringBuilder s = new StringBuilder("There are following avgMark groups in the Collection:\n");
        dict.forEach((key, value) -> s.append(String.format("%1$10s - %2$d group(s)%n", key, value)));
        return new Response(true, "command executed successfuly!", s.toString());
    }
}
