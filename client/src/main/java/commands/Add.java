package commands;


import console.Ask;
import console.Asker;
import dto.Commands;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Request;

public class Add extends Command {
    private final Ask ask;
    private final StandardValidator standardValidator;

    public Add(Ask ask, StandardValidator standardValidator) {
        super("add {StudyGroup}", "adds a new studyGroup to the Collection");
        this.ask = ask;
        this.standardValidator = standardValidator;
    }

    @Override
    public Request apply(String[] arguments) {
        try {
            Asker asker = new Asker(ask);
            StudyGroup sg = asker.askStudyGroup(standardValidator, 1);
            return new Request(Commands.ADD, sg);
        } catch (Ask.AskBreak e) {
        throw new IllegalStateException("CREATION_CANCELLED");
    }
    }
}
