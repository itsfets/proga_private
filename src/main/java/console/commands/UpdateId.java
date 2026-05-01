package console.commands;

import console.ExecutionResponse;
import console.standard.Ask;
import console.standard.Asker;
import console.standard.Console;
import core.managers.CollectionManager;
import core.models.standard.StudyGroup;
import core.validators.standard.StudyGroupValidator;

public class UpdateId extends Command {
    private final Console console;
    private final Ask ask;
    private final CollectionManager collectionManager;
    private final StudyGroupValidator validator;
    private final Asker asker;

    public UpdateId(Console console, Ask ask, CollectionManager collectionManager, StudyGroupValidator validator) {
        super("update_id {element}", "Updates values of a StudyGroup with the given ID in the Collection");
        this.console = console;
        this.ask = ask;
        this.collectionManager = collectionManager;
        this.validator = validator;
        this.asker = new Asker(ask);
    }

    public ExecutionResponse apply(String[] arguments) {
        try {
            if (arguments[1].isBlank()) return new ExecutionResponse().wrongArgCountMessage();
            int id;
            try {
                id = Integer.parseInt(arguments[1]);
            } catch (NumberFormatException e) {
                return new ExecutionResponse("This command requires an integer!", false);
            }
            if (collectionManager.byId(id) == null) return new ExecutionResponse("No StudyGroup with given ID in the collection!", false);
            StudyGroup studyGroup = asker.askStudyGroup(validator, id);
            if (studyGroup != null) {
                studyGroup.setId(id);
                studyGroup.setCreationDate(collectionManager.byId(id).getCreationDate());
                collectionManager.update(studyGroup);
            }
            return new ExecutionResponse("Successfully updated StudyGroup values!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse().cancelExecutionMessage();
        }
    }
}
