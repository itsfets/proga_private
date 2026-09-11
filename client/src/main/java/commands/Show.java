package commands;

import core.Session;
import dto.Commands;
import network.Request;

public class Show extends Command {
    private final Session userSession;

    public Show(Session session) {
        super("show", "Prints out string representation of every StudyGroup in the Collection");
        this.userSession = session;
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.SHOW, userSession.getLogin(), userSession.getPassword());
    }
}
