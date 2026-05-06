package core.managers;

import console.commands.Command;

import java.util.Map;

public interface CommandManagerTemplate {
    void registerCommand(String name, Command command);

    Map<String, Command> getCommands();
}
