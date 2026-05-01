package console.commands;

import console.Console;
import console.ExecutionResponse;
import core.managers.standard.CollectionManager;

public class SumStudsCount extends Command {
    private final Console console;
    private final CollectionManager collectionManager;

    public SumStudsCount(Console console, CollectionManager collectionManager) {
        super("sum_of_students_count", "Returns the sum of studentsCount for all StudyGroups in the Collection");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] argument) {
        if (!argument[1].isBlank()) return new ExecutionResponse().noArgExecutionMessage();
        return new ExecutionResponse(String.valueOf(collectionManager.getSumStudsCount()));
    }
}
