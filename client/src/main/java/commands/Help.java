package commands;

import core.Session;
import dto.Commands;
import network.Request;

public class Help extends Command {
    private final Session userSession;

    public Help(Session session) {
        super("help", "Prints out a list of available commands and their descriptions");
        this.userSession = session;
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.HELP, userSession.getLogin(), userSession.getPassword());
    }
}
