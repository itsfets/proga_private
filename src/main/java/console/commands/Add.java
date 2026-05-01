package console.commands;

import console.ExecutionResponse;
import console.standard.Ask;
import console.standard.Asker;
import console.standard.Console;
import core.managers.standard.CollectionManager;
import core.models.standard.StudyGroup;
import core.validators.standard.StudyGroupValidator;

public class Add extends Command {
    private final Console console;
    private final Ask ask;
    private final CollectionManager collectionManager;
    private final StudyGroupValidator validator;

    public Add(Console console, Ask ask, CollectionManager collectionManager, StudyGroupValidator validator) {
        super("add {StudyGroup}", "Adds a new StudyGroup to the Collection");
        this.console = console;
        this.ask = ask;
        this.collectionManager = collectionManager;
        this.validator = validator;
    }

    @Override
    public ExecutionResponse apply(String[] argument) {
        Asker asker = new Asker(ask);
        try {
            if (!argument[1].isBlank()) return new ExecutionResponse().noArgExecutionMessage();
            int id = collectionManager.getFreeId();
            StudyGroup SG = asker.askStudyGroup(validator, id);
            boolean res = collectionManager.add(SG);
            if (res) return new ExecutionResponse("StudyGroup was added successfully");
            else return new ExecutionResponse("StudyGroup was not added!", false);
        } catch (Exception e) {
            return new ExecutionResponse(e.getMessage(), false);
        }
    }
}
