package core.managers.standard;

import console.commands.Command;
import core.managers.CommandManagerTemplate;

import java.util.LinkedHashMap;
import java.util.Map;

public class CommandManager implements CommandManagerTemplate {
    private final Map<String, Command> commands = new LinkedHashMap<>();

    @Override
    public void registerCommand(String name, Command command) {
        commands.put(name, command);
    }

    @Override
    public Map<String, Command> getCommands() {
        return commands;
    }
}
