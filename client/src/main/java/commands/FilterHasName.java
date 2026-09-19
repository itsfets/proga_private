package commands;

import core.Session;
import dto.Commands;
import network.Request;

public class FilterHasName extends Command {
    private final Session userSession;

    public FilterHasName(Session userSession) {
        super("filter_contains_name <name>", "returns all StudyGroups, who have set substring in their name field");
        this.userSession = userSession;
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.FILTERHASNAME, arguments[1], userSession.getLogin(), userSession.getPassword());
    }
}
