package commands;


import console.Ask;
import console.Asker;
import core.Session;
import dto.Commands;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Request;

public class Add extends Command {
    private final Ask ask;
    private final StandardValidator standardValidator;
    private final Session userSession;

    public Add(Session session, Ask ask, StandardValidator standardValidator) {
        super("add {StudyGroup}", "adds a new studyGroup to the Collection");
        this.userSession = session;
        this.ask = ask;
        this.standardValidator = standardValidator;
    }

    @Override
    public Request apply(String[] arguments) {
        Asker asker = new Asker(ask);
        StudyGroup sg = asker.askStudyGroup(standardValidator, 1);
        if (sg == null) return new Request(null, null, userSession.getLogin(), userSession.getPassword());
        return new Request(Commands.ADD, sg, userSession.getLogin(), userSession.getPassword());
    }
}
