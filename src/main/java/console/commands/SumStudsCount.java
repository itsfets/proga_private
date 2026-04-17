package console.commands;

import console.StandardConsole;
import core.managers.StandardCollectionManager;
import core.models.ExecutionResponse;

public class SumStudsCount extends Command {
    private final StandardConsole console;
    private final StandardCollectionManager collectionManager;

    public SumStudsCount(StandardConsole console, StandardCollectionManager collectionManager) {
        super("sum_of_students_count", "Returns the sum of studentsCount for all StudyGroups in the Collection");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] argument) {
        if (!argument[1].isEmpty()) return new ExecutionResponse().noArgExecutionMessage();
        return new ExecutionResponse(String.valueOf(collectionManager.getSumStudsCount()));
    }
}
