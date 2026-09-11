package commands;


import core.Session;
import dto.Commands;
import network.Request;

public class SumStudsCount extends Command {
    private final Session userSession;

    public SumStudsCount(Session userSession) {
        super("sum_of_students_count", "Returns the sum of studentsCount for all StudyGroups in the Collection");
        this.userSession = userSession;
    }

    @Override
    public Request apply(String[] argument) {
        return new Request(Commands.SUMSTUDSCOUNT, userSession.getLogin(), userSession.getPassword());
    }
}
