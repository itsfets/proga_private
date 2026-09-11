package commands;

import network.Response;

public class SetPassword extends Command {
    public SetPassword() {
        super("set_password <password>", "Set your password for executing commands");
    }

    @Override
    public Response apply(Object request_data, String login) {
        return null;
    }
}
