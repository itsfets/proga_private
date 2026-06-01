package commands;

import dto.Commands;
import network.Request;

public class Clear extends Command {

    public Clear() {
        super("clear", "Clears the Collection");
    }

    @Override
    public Request apply(String[] argument) {
        return new Request(Commands.CLEAR);
    }
}
