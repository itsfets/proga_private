package commands;

import core.StandardCollection;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Response;

import java.time.LocalDateTime;

public class AddIfMin extends Command {
    private final StandardCollection standardCollection;
    private final StandardValidator standardValidator;

    public AddIfMin(StandardCollection standardCollection, StandardValidator validator) {
        super("add_if_min {StudyGroup}", "adds a new studyGroup to the Collection if its averageMark is lower than the minimum averageMark in the Collection");
        this.standardCollection = standardCollection;
        this.standardValidator = validator;
    }

    @Override
    public Response apply(Object request_data) {
        if (!(request_data instanceof StudyGroup studyGroup))
            return new Response(false, Response.WRONG_TYPE() + "studyGroup");
        if (!(standardValidator.validate(studyGroup).isValid())) return new Response(false, Response.INVALIG_SG());
        var temp = standardCollection.getCollection().stream().min(StudyGroup::compareTo).map(StudyGroup::getAverageMark).orElse(Double.MAX_VALUE);
        if (studyGroup.getAverageMark() > temp) {
            studyGroup.setId(standardCollection.getFreeId());
            studyGroup.setCreationDate(LocalDateTime.now());
            standardCollection.add(studyGroup);
            return new Response(true, "studyGroup has been added successfully!");
        } else return new Response(false, "studyGroup wasn't added because it didn't pass the filter!");
    }
}
