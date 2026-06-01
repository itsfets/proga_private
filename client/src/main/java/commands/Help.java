package commands;

import dto.Commands;
import network.Request;

public class Help extends Command {

    public Help() {
        super("help", "Prints out a list of available commands and their descriptions");
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.HELP);
    }
}
