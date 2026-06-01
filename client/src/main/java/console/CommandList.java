package console;

import commands.Command;

import java.util.HashMap;
import java.util.Map;

public class CommandList {
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandList registerCommand(String name, Command command) {
        commandMap.put(name, command);
        return this;
    }

    public Map<String, Command> getCommandMap() {
        return commandMap;
    }
}
