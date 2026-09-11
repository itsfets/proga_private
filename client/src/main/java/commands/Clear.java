package commands;

import core.Session;
import dto.Commands;
import network.Request;

public class Clear extends Command {
    private final Session userSession;

    public Clear(Session session) {
        super("clear", "Clears the Collection");
        this.userSession = session;
    }

    @Override
    public Request apply(String[] argument) {
        return new Request(Commands.CLEAR, userSession.getLogin(), userSession.getPassword());
    }
}
