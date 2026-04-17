package core.managers;

import console.commands.Command;

import java.util.LinkedHashMap;
import java.util.Map;

public class StandardCommandManager {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    public void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    public Map<String, Command> getCommands() {
        return commands;
    }
}
