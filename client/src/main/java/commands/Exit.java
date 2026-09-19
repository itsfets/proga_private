package commands;

import core.Session;
import dto.Commands;
import network.Request;

public class Exit extends Command {
    private final Session userSession;

    public Exit(Session userSession) {
        super("exit", "exits the client");
        this.userSession = userSession;
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.EXIT, userSession.getLogin(), userSession.getPassword());
    }
}
