package commands;

import console.Console;
import dto.Commands;
import network.Request;

public class Exit extends Command {
    private final Console console;

    public Exit(Console console) {
        super("exit", "exits the client");
        this.console = console;
    }

    @Override
    public Request apply(String[] arguments) {
        return new Request(Commands.EXIT);
    }
}
