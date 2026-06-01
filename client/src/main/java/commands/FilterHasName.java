package commands;

import dto.Commands;
import network.Request;

public class FilterHasName extends Command {

    public FilterHasName() {
        super("filter_contains_name <name>", "returns all StudyGroups, who have set substring in their name field");
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.FILTERHASNAME, arguments[1]);
    }
}
