import commands.*;
import console.Ask;
import console.CommandList;
import console.Console;
import core.Runner;
import core.Session;
import modelworks.StandardValidator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final int DEFAULT_PORT = 42069;

        if (args.length != 0) {
            System.out.println("Detected args! They were ignored!");
        }

        var validator = new StandardValidator();
        var console = new Console();
        var ask = new Ask(console);
        var commands = new CommandList();
        Session userSession = new Session();
        try {
            Runner runner = new Runner(DEFAULT_PORT, commands, console);
            commands.registerCommand("add", new Add(userSession, ask, validator))
                    .registerCommand("add_if_max", new AddIfMax(userSession, ask, validator))
                    .registerCommand("add_if_min", new AddIfMin(userSession, ask, validator))
                    .registerCommand("clear", new Clear(userSession))
                    .registerCommand("execute_script", new ExecuteScript(userSession, console, runner))
                    .registerCommand("exit", new Exit(userSession))
                    .registerCommand("filter_contains_name", new FilterHasName(userSession))
                    .registerCommand("group_counting_by_average_mark", new GroupByAvgMark(userSession))
                    .registerCommand("help", new Help(userSession))
                    .registerCommand("info", new Info(userSession))
                    .registerCommand("remove_by_id", new RemoveById(userSession, console))
                    .registerCommand("show", new Show(userSession))
                    .registerCommand("sum_of_students_count", new SumStudsCount(userSession))
                    .registerCommand("set_login", new SetLogin(userSession))
                    .registerCommand("set_password", new SetPassword(userSession))
                    .registerCommand("remove_lower", new RemoveLower(userSession, ask, validator))
                    .registerCommand("update_id", new UpdateId(userSession, ask, validator));
            runner.run();
        } catch (IOException e) {
            console.printErr("failed to start the runner: " + e.getMessage());
        }
    }

}
