package console.commands;

import console.Console;
import console.ExecutionResponse;
import core.managers.standard.CommandManager;

import java.util.stream.Collectors;

public class Help extends Command {
    private final CommandManager commandManager;

    public Help(Console console, CommandManager commandManager) {
        super("help","Prints out a list of available commands and their descriptions");
        this.commandManager = commandManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isBlank()) return new ExecutionResponse().noArgExecutionMessage();
        String s = "Available commands:\n";
        s += commandManager.getCommands().values().stream().map(command -> String.format("%1$30s - %2$s", command.getName(), command.getDesc())).collect(Collectors.joining("\n"));
        return new ExecutionResponse(s);
    }
}
