import commands.*;
import core.CommandList;
import core.Runner;
import core.StandardCollection;
import core.StandardDumper;
import modelworks.StandardValidator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        final int DEFAULT_PORT = 42069;

        if (args.length != 0) {
            System.out.println("Detected args! They were ignored!");
        }

        var dumper = new StandardDumper();
        var collection = new StandardCollection(dumper);
        collection.init();
        var validator = new StandardValidator();

        var commandRepository = new CommandList() {{
            registerCommand("info", new Info(collection));
            registerCommand("show", new Show(collection));
            registerCommand("add", new Add(collection, validator));
            registerCommand("add_if_max", new AddIfMax(collection, validator));
            registerCommand("add_if_min", new AddIfMin(collection, validator));
            registerCommand("execute_script", new ExecuteScript());
            registerCommand("clear", new Clear(collection));
            registerCommand("exit", new Exit());
            registerCommand("filter_contains_name", new FilterHasName(collection));
            registerCommand("group_counting_by_average_mark", new GroupByAvgMark(collection));
            registerCommand("remove_by_id", new RemoveById(collection));
            registerCommand("sum_of_students_count", new SumStudsCount(collection));
            registerCommand("update_id", new UpdateId(collection, validator));
        }};
        commandRepository.registerCommand("help", new Help(commandRepository));

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("server shutting down...");
            collection.saveCollection();
        }));

        try {
            Runner runner = new Runner(DEFAULT_PORT, commandRepository);
            runner.run();
        } catch (IOException e) {
            System.err.println("failed to start the runner: " + e.getMessage());
        }

    }
}
