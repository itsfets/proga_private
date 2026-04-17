package console.commands;

import console.Ask;
import console.StandardConsole;
import core.managers.StandardCollectionManager;
import core.models.ExecutionResponse;
import core.models.StudyGroup;

public class UpdateId extends Command {
    private final StandardConsole console;
    private final StandardCollectionManager collectionManager;

    public UpdateId(StandardConsole console, StandardCollectionManager collectionManager) {
        super("update id {element}", "Updates values of a StudyGroup with the given ID in the Collection");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        try {
            if (arguments[1].isEmpty()) return new ExecutionResponse().wrongArgCountMessage();
            int id;
            try {
                id = Integer.parseInt(arguments[1]);
            } catch (NumberFormatException e) {
                return new ExecutionResponse("This command requires an integer!", false);
            }
            if (collectionManager.byId(id) == null) return new ExecutionResponse("No StudyGroup with given ID in the collection!", false);
            StudyGroup studyGroup = Ask.askStudyGroup(console, id);
            if (studyGroup != null && studyGroup.validate()) {
                studyGroup.setCreationDate(collectionManager.byId(id).getCreationDate());
                collectionManager.update(studyGroup);
            }
            return new ExecutionResponse("Successfully updated StudyGroup values!");
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse().cancelExecutionMessage();
        }
    }
}
