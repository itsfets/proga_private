package commands;


import dto.Commands;
import network.Request;

public class SumStudsCount extends Command {
    public SumStudsCount() {
        super("sum_of_students_count", "Returns the sum of studentsCount for all StudyGroups in the Collection");
    }

    @Override
    public Request apply(String[] argument) {
        return new Request(Commands.SUMSTUDSCOUNT);
    }
}
