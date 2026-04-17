package console.commands;

import console.Ask;
import console.StandardConsole;
import core.managers.StandardCollectionManager;
import core.models.ExecutionResponse;
import core.models.StudyGroup;

public class Add extends Command {
    private final StandardConsole console;
    private final StandardCollectionManager collectionManager;

    public Add(StandardConsole console, StandardCollectionManager collectionManager) {
        super("add {StudyGroup}", "Adds a new StudyGroup to the Collection");
        this.console = console;
        this.collectionManager = collectionManager;
    }


    public ExecutionResponse apply(String[] argument) {
        try {
            if (!argument[1].isEmpty()) return new ExecutionResponse().noArgExecutionMessage();
            StudyGroup studyGroup = Ask.askStudyGroup(console, collectionManager.getFreeId());
            if (studyGroup != null && studyGroup.validate()) {
                collectionManager.add(studyGroup);
                return new ExecutionResponse("StudyGroup added successfully!");
            } else {
                return new ExecutionResponse("Invalid argument fields! StudyGroup wasn't added!", false);
            }
        } catch (Ask.AskBreak e) {
            return new ExecutionResponse().cancelExecutionMessage();
        }
    }
}
