package commands;

import core.CommandList;
import network.Response;

import java.util.stream.Collectors;

public class Help extends Command {
    private final CommandList commandManager;

    public Help(CommandList commandManager) {
        super("help", "prints out a list of available commands and their descriptions");
        this.commandManager = commandManager;
    }

    public Response apply(Object request_data) {
        String s = "Available commands:\n";
        s += commandManager.getCommands().values().stream().map(command -> String.format("%1$30s - %2$s", command.getName(), command.getDesc())).collect(Collectors.joining("\n"));
        return new Response(true, "command executed successfuly!", s);
    }
}
