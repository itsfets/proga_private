package commands;

import console.Ask;
import console.Asker;
import core.Session;
import dto.Commands;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Request;

public class AddIfMax extends Command {
    private final Ask ask;
    private final StandardValidator validator;
    private final Session userSession;

    public AddIfMax(Session session, Ask ask, StandardValidator validator) {
        super("add_if_max {StudyGroup}", "adds a new studyGroup to the Collection if its averageMark is higher than the maximum averageMark in the Collection");
        this.userSession = session;
        this.ask = ask;
        this.validator = validator;
    }

    @Override
    public Request apply(String[] argument) {
        Asker asker = new Asker(ask);
        StudyGroup sg = asker.askStudyGroup(validator, 1);
        if (sg == null) return new Request(null, null, userSession.getLogin(), userSession.getPassword());
        return new Request(Commands.ADDIFMAX, sg, userSession.getLogin(), userSession.getPassword());

    }
}
