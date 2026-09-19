package commands;

import core.Session;
import dto.Commands;
import network.Request;

public class Info extends Command {
    private final Session userSession;

    public Info(Session userSession) {
        super("info", "returns information about the Collection (type, size, last initialization date)");
        this.userSession = userSession;
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.INFO, userSession.getLogin(), userSession.getPassword());
    }
}
