package commands;

import console.Ask;
import console.Asker;
import dto.Commands;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Request;

public class RemoveLower extends Command {
    private final Ask ask;
    private final StandardValidator validator;

    public RemoveLower(Ask ask, StandardValidator validator) {
        super("remove_lower {StudyGroup}", "Removes all studyGroups with the averageMark value lower than the averageMark value of inputted studyGroup");
        this.ask = ask;
        this.validator = validator;
    }

    @Override
    public Request apply(String[] arguments) {
        Asker asker = new Asker(ask);
        StudyGroup sg = asker.askStudyGroup(validator, 1);
        return new Request(Commands.ADDIFMIN, sg);
    }
}
