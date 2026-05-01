package console.commands;

import console.ExecutionResponse;
import console.standard.Ask;
import console.standard.Asker;
import console.standard.Console;
import core.managers.CollectionManager;
import core.models.standard.StudyGroup;
import core.validators.standard.StudyGroupValidator;

import java.util.ArrayList;
import java.util.List;

public class RemoveLower extends Command {
    private final Console console;
    private final Ask ask;
    private final CollectionManager collectionManager;
    private final StudyGroupValidator validator;

    public RemoveLower(Console console, Ask ask, CollectionManager collectionManager, StudyGroupValidator validator) {
        super("remove_lower {StudyGroup}", "Removes all StudyGroups with the averageMark value lower than the averageMark value of inputted StudyGroup");
        this.console = console;
        this.ask = ask;
        this.collectionManager = collectionManager;
        this.validator = validator;
    }

    public ExecutionResponse apply(String[] arguments) {
        Asker asker = new Asker(ask);
        try {
            if (!arguments[1].isBlank()) {
                return new ExecutionResponse().noArgExecutionMessage();
            }
            console.print("Enter a StudyGroup to be used for filtering, it won't be added to the Collection\n");
            int tmp = collectionManager.getFreeId();
            StudyGroup SG = asker.askStudyGroup(validator, tmp);
            if (SG != null) {
                double controlAvgMark = SG.getAverageMark();
                List<Integer> idsToRemove = new ArrayList<>();
                for (StudyGroup element : collectionManager.getCollection()) {
                    if (element.getAverageMark() < controlAvgMark) {
                        idsToRemove.add(element.getId());
                    }
                }
                for (int id : idsToRemove) {
                    collectionManager.remove(id);
                }
                if (idsToRemove.isEmpty()) {
                    return new ExecutionResponse("No elements found with averageMark lower than " + controlAvgMark);
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
