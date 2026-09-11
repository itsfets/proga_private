package commands;

import core.StandardCollection;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Response;

import java.time.LocalDateTime;

public class AddIfMax extends Command {
    private final StandardCollection standardCollection;
    private final StandardValidator standardValidator;

    public AddIfMax(StandardCollection standardCollection, StandardValidator validator) {
        super("add_if_max {StudyGroup}", "adds a new studyGroup to the Collection if its averageMark is higher than the maximum averageMark in the Collection");
        this.standardCollection = standardCollection;
        this.standardValidator = validator;
    }

    @Override
    public Response apply(Object request_data, String login) {
        if (!(request_data instanceof StudyGroup studyGroup))
            return new Response(false, Response.WRONG_TYPE + "studyGroup");
        if (!(standardValidator.validate(studyGroup).isValid())) return new Response(false, Response.INVALIG_SG);
        var temp = standardCollection.getCollection().stream().max(StudyGroup::compareTo).map(StudyGroup::getAverageMark).orElse(Double.MIN_VALUE);
        if (studyGroup.getAverageMark() > temp) {
            studyGroup.setCreationDate(LocalDateTime.now());
            studyGroup.setCreatedBy(login);
            return (standardCollection.add(studyGroup)) ? new Response(true, "studyGroup has been added successfully!")
                    : new Response(false, "command failed and studyGroup has not been added!");
        }
        return new Response(true, "studyGroup wasn't added because it didn't pass the filter!");
    }
}
