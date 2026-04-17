package console.commands;

import console.StandardConsole;
import core.managers.StandardCommandManager;
import core.models.ExecutionResponse;

import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Help extends Command {
    private final StandardCommandManager standardCommandManager;

    public Help(StandardConsole standardConsole, StandardCommandManager standardCommandManager) {
        super("help","Prints out a list of available commands and their descriptions");
        this.standardCommandManager = standardCommandManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isEmpty()) return new ExecutionResponse().noArgExecutionMessage();
        String s = "Available commands:\n";
        s += standardCommandManager.getCommands().values().stream().map(command -> String.format("%1$30s - %2$sn", command.getName(), command.getDesc())).collect(Collectors.joining("\n"));
        return new ExecutionResponse(s);
    }
}
