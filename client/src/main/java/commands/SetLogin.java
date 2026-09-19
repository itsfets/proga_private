package commands;

import core.Session;
import network.Request;

public class SetLogin extends Command {
    private final Session userSession;

    public SetLogin(Session userSession) {
        super("set_login <login>", "Set your login for executing commands");
        this.userSession = userSession;
    }

    @Override
    public Request apply(String[] argument) {
        userSession.setLogin(argument[1]);
        return null;
    }
}
