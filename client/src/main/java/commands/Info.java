package commands;

import dto.Commands;
import network.Request;

public class Info extends Command {
    public Info() {
        super("info", "returns information about the Collection (type, size, last initialization date)");
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.INFO);
    }
}
