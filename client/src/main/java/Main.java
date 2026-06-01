import commands.*;
import console.Ask;
import console.CommandList;
import console.Console;
import core.Runner;
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
        try {
            Runner runner = new Runner(DEFAULT_PORT, commands, console);
            commands.registerCommand("add", new Add(ask, validator))
                    .registerCommand("add_if_max", new AddIfMax(ask, validator))
                    .registerCommand("add_if_min", new AddIfMin(ask, validator))
                    .registerCommand("clear", new Clear())
                    .registerCommand("execute_script", new ExecuteScript(console, runner))
                    .registerCommand("exit", new Exit(console))
                    .registerCommand("filter_contains_name", new FilterHasName())
                    .registerCommand("group_counting_by_average_mark", new GroupByAvgMark())
                    .registerCommand("help", new Help())
                    .registerCommand("info", new Info())
                    .registerCommand("remove_by_id", new RemoveById(console))
                    .registerCommand("show", new Show())
                    .registerCommand("sum_of_students_count", new SumStudsCount())
                    .registerCommand("update_id", new UpdateId(ask, validator));
            runner.run();
        } catch (IOException e) {
            console.printErr("failed to start the runner: " + e.getMessage());
        }
    }

}
