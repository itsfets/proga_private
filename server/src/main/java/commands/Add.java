package commands;

import core.StandardCollection;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Response;

import java.time.LocalDateTime;

public class Add extends Command {
    private final StandardCollection standardCollection;
    private final StandardValidator standardValidator;

    public Add(StandardCollection collectionManager, StandardValidator validator) {
        super("add {StudyGroup}", "adds a new studyGroup to the Collection");
        this.standardCollection = collectionManager;
        this.standardValidator = validator;
    }

    @Override
    public Response apply(Object request_data) {
        if (!(request_data instanceof StudyGroup studyGroup)) {
            return new Response(false, Response.WRONG_TYPE() + "studyGroup");
        }
        var validation_res = standardValidator.validate(studyGroup);
        if (validation_res.isValid()) {
            studyGroup.setId(standardCollection.getFreeId());
            studyGroup.setCreationDate(LocalDateTime.now());
            if (standardCollection.add(studyGroup)) {
                return new Response(true, "studyGroup has been added successfully!");
            } else {
                return new Response(false, "studyGroup has not been added!");
            }
        } else return new Response(false, Response.INVALIG_SG());
    }
}
