package commands;

import console.Ask;
import console.Asker;
import dto.Commands;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Request;

public class AddIfMin extends Command {
    private final Ask ask;
    private final StandardValidator validator;

    public AddIfMin(Ask ask, StandardValidator validator) {
        super("add_if_min {StudyGroup}", "Adds a new StudyGroup to the Collection if its averageMark is lower than the minimum averageMark in the Collection");
        this.ask = ask;
        this.validator = validator;
    }

    @Override
    public Request apply(String[] argument) {
        Asker asker = new Asker(ask);
        StudyGroup sg = asker.askStudyGroup(validator, 1);
        return new Request(Commands.ADDIFMIN, sg);
    }
}
