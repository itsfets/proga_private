package commands;

import core.Session;
import network.Request;

public class SetPassword extends Command {
    private final Session userSession;

    public SetPassword(Session session) {
        super("set_password <password>", "Set your password for executing commands");
        this.userSession = session;
    }

    @Override
    public Request apply(String[] argument) {
        userSession.setPassword(argument[1]);
        return null;
    }
}
