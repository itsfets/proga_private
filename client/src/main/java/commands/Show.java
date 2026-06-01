package commands;

import dto.Commands;
import network.Request;

public class Show extends Command {

    public Show() {
        super("show", "Prints out string representation of every StudyGroup in the Collection");
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.SHOW);
    }
}
