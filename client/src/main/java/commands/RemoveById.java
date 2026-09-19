package commands;

import console.Console;
import core.Session;
import dto.Commands;
import network.Request;

public class RemoveById extends Command {
    private final Console console;
    private final Session userSession;

    public RemoveById(Session session, Console console) {
        super("remove_by_id <id>", "Removes StudyGroup with given ID");
        this.userSession = session;
        this.console = console;
    }

    @Override
    public Request apply(String[] arguments) {
        try {
            return new Request(Commands.REMOVEBYID, Integer.parseInt(arguments[1]), userSession.getLogin(), userSession.getPassword());
        } catch (NumberFormatException e) {
            console.println("couldn't cast given ID to int, please try again!");
            return null;
        }
    }
}
