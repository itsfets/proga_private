package commands;

import console.Ask;
import console.Asker;
import core.Session;
import dto.Commands;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Request;

public class UpdateId extends Command {
    private final Ask ask;
    private final StandardValidator validator;
    private final Session userSession;

    public UpdateId(Session session, Ask ask, StandardValidator validator) {
        super("update_id {element}", "Updates values of a StudyGroup with the given ID in the Collection");
        this.userSession = session;
        this.ask = ask;
        this.validator = validator;
    }

    @Override
    public Request apply(String[] arguments) {
        Asker asker = new Asker(ask);
        StudyGroup sg = asker.askStudyGroup(validator);
        if (sg == null) return new Request(null, null, userSession.getLogin(), userSession.getPassword());
        return new Request(Commands.UPDATEID, sg, userSession.getLogin(), userSession.getPassword());
    }
}
