package console.commands;

import console.ExecutionResponse;
import console.standard.Ask;
import console.Console;
import console.standard.Asker;
import core.managers.standard.CollectionManager;
import core.models.standard.StudyGroup;
import core.validators.standard.StudyGroupValidator;

public class AddIfMin extends Command {
    private final Console console;
    private final Ask ask;
    private final CollectionManager collectionManager;
    private final StudyGroupValidator validator;

    public AddIfMin(Console console, Ask ask, CollectionManager collectionManager, StudyGroupValidator validator) {
        super("add_if_min {StudyGroup}", "Adds a new StudyGroup to the Collection if its averageMark is lower than the minimum averageMark in the Collection");
        this.console = console;
        this.ask = ask;
        this.collectionManager = collectionManager;
        this.validator = validator;
    }

    public ExecutionResponse apply(String[] argument) {
        Asker asker = new Asker(ask);
        try {
            if (!argument[1].isBlank()) return new ExecutionResponse().noArgExecutionMessage();
            int id = collectionManager.getFreeId();
            StudyGroup SG = asker.askStudyGroup(validator, id);
            if (SG != null && SG.compareTo(collectionManager.getMinAvgMark()) < 0) {
                boolean res = collectionManager.add(SG);
                if (res) return new ExecutionResponse("StudyGroup was added successfully");
                else return new ExecutionResponse("StudyGroup was not added!", false);
            } else {
                return new ExecutionResponse("StudyGroup wasn't added because its averageMark is higher than the minimum value in the Collection!", false);
            }
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse().cancelExecutionMessage();
        }
    }
}
