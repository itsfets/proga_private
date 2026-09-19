package commands;

import network.Response;

public class SetLogin extends Command {
    public SetLogin() {
        super("set_login <login>", "Set your login for executing commands");
    }

    @Override
    public Response apply(Object request_data, String login) {
        return null;
    }
}
