package core;

import commands.Command;
import dto.Commands;

import java.util.HashMap;
import java.util.Map;

public class CommandList {
    private final Map<String, Command> commandMap = new HashMap<>();

    public void registerCommand(String name, Command command) {
        commandMap.put(name, command);
    }

    public Command getCommand(Commands commandName) {
        return commandMap.get(commandName.name());
    }

    public Map<String, Command> getCommands() {
        return commandMap;
    }
}
