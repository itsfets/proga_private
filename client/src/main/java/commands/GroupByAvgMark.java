package commands;

import core.Session;
import dto.Commands;
import network.Request;

public class GroupByAvgMark extends Command {
    private final Session userSession;

    public GroupByAvgMark(Session userSession) {
        super("group_counting_by_average_mark", "Groups StudyGroups by value of averageMark field and returns size of each group");
        this.userSession = userSession;
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.GROUPBYAVGMARK, userSession.getLogin(), userSession.getPassword());
    }
}
