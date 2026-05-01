import console.standard.Ask;
import console.standard.Console;
import console.commands.*;
import core.builders.StandardBuilder;
import core.runners.standard.StandardInteractiveRunner;
import core.managers.standard.CollectionManager;
import core.managers.standard.CommandManager;
import core.managers.standard.DumpManager;
import core.validators.standard.StudyGroupValidator;


public class Main {
    public static void main(String[] args) {
            var console = new Console();
            var builder = new StandardBuilder();
            var ask = new Ask(console, builder);
            var validator = new StudyGroupValidator();

            if (args.length != 0) {
                console.printError("Detected arguments! They were ignored!");
                System.exit(1);
            }

            var dumpManager = new DumpManager(console);
            var collectionManager = new CollectionManager(dumpManager);
            if (!collectionManager.init()) {
                System.exit(1);
            }

            var commandManager = new CommandManager() {{
                registerCommand("help", new Help(console, this));
                registerCommand("info", new Info(console, collectionManager));
                registerCommand("show", new Show(console, collectionManager));
                registerCommand("add", new Add(console, ask, collectionManager, validator));
                registerCommand("update_id", new UpdateId(console, ask, collectionManager, validator));
                registerCommand("remove_by_id", new RemoveById(console, collectionManager));
                registerCommand("clear", new Clear(console, collectionManager));
                registerCommand("save", new Save(console, collectionManager));
                registerCommand("exit", new Exit(console));
                registerCommand("add_if_max", new AddIfMax(console, ask, collectionManager, validator));
                registerCommand("add_if_min", new AddIfMin(console, ask, collectionManager, validator));
                registerCommand("remove_lower", new RemoveLower(console, ask, collectionManager, validator));
                registerCommand("sum_of_students_count", new SumStudsCount(console, collectionManager));
                registerCommand("group_counting_by_average_mark", new GroupByAvgMark(console, collectionManager));
                registerCommand("filter_contains_name", new FilterHasName(console, collectionManager));
            }};
            commandManager.registerCommand("execute_script", new ExecuteScript(console, commandManager));

            new StandardInteractiveRunner(console, commandManager).run();
    }
}
