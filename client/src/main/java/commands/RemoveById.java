package commands;

import console.Console;
import dto.Commands;
import network.Request;

public class RemoveById extends Command {
    private final Console console;

    public RemoveById(Console console) {
        super("remove_by_id <id>", "Removes StudyGroup with given ID");
        this.console = console;
    }

    @Override
    public Request apply(String[] arguments) {
        try {
            return new Request(Commands.REMOVEBYID, Integer.parseInt(arguments[1]));
        } catch (NumberFormatException e) {
            console.println("couldn't cast given ID to int, please try again!");
            return null;
        }
    }
}
