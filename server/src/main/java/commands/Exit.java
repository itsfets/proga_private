package commands;

import network.Response;

public class Exit extends Command {

    public Exit() {
        super("exit", "exits the client application");
    }

    @Override
    public Response apply(Object request) {
        return null;
    }
}
