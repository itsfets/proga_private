package commands;

import dto.Commands;
import network.Request;

public class GroupByAvgMark extends Command {

    public GroupByAvgMark() {
        super("group_counting_by_average_mark", "Groups StudyGroups by value of averageMark field and returns size of each group");
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.GROUPBYAVGMARK);
    }
}
