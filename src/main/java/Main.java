import console.StandardConsole;
import console.commands.*;
import core.StandardRunner;
import core.managers.StandardCollectionManager;
import core.managers.StandardCommandManager;
import core.managers.StandardDumpManager;


public class Main {
    public static void main(String[] args) {
        var console = new StandardConsole();

        if (args.length == 0) {
            console.println("");
            System.exit(1);
        }

        var dumpManager = new StandardDumpManager(args[0], console);
        var collectionManager = new StandardCollectionManager(dumpManager);
        if (!collectionManager.init()) {
            System.exit(1);
        }

        var commandManager = new StandardCommandManager() {{
            registerCommand("help", new Help(console, this));
            registerCommand("info", new Info(console, collectionManager));
            registerCommand("show", new Show(console, collectionManager));
            registerCommand("add", new Add(console, collectionManager));
            registerCommand("update_id", new UpdateId(console, collectionManager));
            registerCommand("remove_by_id", new RemoveById(console, collectionManager));
            registerCommand("clear", new Clear(console, collectionManager));
            registerCommand("save", new Save(console, collectionManager));
            registerCommand("execute_script", new ExecuteScript(console));
            registerCommand("exit", new Exit(console));
            registerCommand("add_if_max", new AddIfMax(console, collectionManager));
            registerCommand("add_if_min", new AddIfMin(console, collectionManager));
            registerCommand("remove_lower", new RemoveLower(console, collectionManager));
            registerCommand("sum_of_students_count", new SumStudsCount(console, collectionManager));
            registerCommand("group_counting_by_average_mark", new GroupByAvgMark(console, collectionManager));
            registerCommand("filter_contains_name", new FilterHasName(console, collectionManager));
        }};

        new StandardRunner(console, commandManager).interactiveMode();
    }
}
