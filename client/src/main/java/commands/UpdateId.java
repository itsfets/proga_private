package commands;

import console.Ask;
import console.Asker;
import dto.Commands;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Request;

public class UpdateId extends Command {
    private final Ask ask;
    private final StandardValidator validator;

    public UpdateId(Ask ask, StandardValidator validator) {
        super("update_id {element}", "Updates values of a StudyGroup with the given ID in the Collection");
        this.ask = ask;
        this.validator = validator;
    }

    @Override
    public Request apply(String[] arguments) {
        Asker asker = new Asker(ask);
        StudyGroup sg = asker.askStudyGroup(validator);
        return new Request(Commands.ADDIFMAX, sg);
    }
}
