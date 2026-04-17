package console.commands;

import console.Ask;
import console.StandardConsole;
import core.managers.StandardCollectionManager;
import core.models.ExecutionResponse;
import core.models.StudyGroup;

public class RemoveLower extends Command {
    private final StandardConsole console;
    private final StandardCollectionManager collectionManager;

    public RemoveLower(StandardConsole console, StandardCollectionManager collectionManager) {
        super("remove_lower {element}", "Removes all StudyGroups with the averageMark value lower than the averageMark value of inputted StudyGroup");
        this.console = console;
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        try {
            if (!arguments[1].isEmpty()) return new ExecutionResponse().noArgExecutionMessage();
            console.print("Enter a StudyGroup to be used for filtering, it won't be added to the Collection:");
            StudyGroup studyGroup = Ask.askStudyGroup(console, collectionManager.getFreeId());
            if (studyGroup != null && studyGroup.validate()) {
                double controlAvgMark = studyGroup.getAverageMark();
                for (StudyGroup collectionElement : collectionManager.getCollection()) {
                    if (collectionElement.getAverageMark() < controlAvgMark) {
                        collectionManager.remove(collectionElement.getId());
                    }
                }
                return new ExecutionResponse("Elements were removed successfully.");
            } else {
                return new ExecutionResponse("Invalid argument fields! Nothing was removed", false);
            }

        } catch (Ask.AskBreak e) {
            return new ExecutionResponse().cancelExecutionMessage();
        }
    }
}
