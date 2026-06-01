package commands;

import core.StandardCollection;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Response;

import java.util.ArrayList;
import java.util.List;

public class RemoveLower extends Command {
    private final StandardValidator standardValidator;
    private final StandardCollection standardCollection;

    public RemoveLower(StandardCollection standardCollection, StandardValidator standardValidator) {
        super("remove_lower {StudyGroup}", "removes all studyGroups with the averageMark value lower than the averageMark value of input studyGroup");
        this.standardCollection = standardCollection;
        this.standardValidator = standardValidator;
    }

    @Override
    public Response apply(Object request_data) {
        if (!(request_data instanceof StudyGroup studyGroup))
            return new Response(false, Response.WRONG_TYPE() + "studyGroup");
        if (standardValidator.validate(studyGroup).isValid()) {
            double controlAvgMark = studyGroup.getAverageMark();
            List<Integer> idsToRemove = new ArrayList<>();
            standardCollection.getCollection().stream().filter(group -> group.getAverageMark() < controlAvgMark).forEach(group -> {
                idsToRemove.add(group.getId());
            });
            if (!idsToRemove.isEmpty()) {
                idsToRemove.stream().map(standardCollection::remove);
                return new Response(true, "studyGroups were removed successfully");
            }
            return new Response(true, "no studyGroups passed the filter!");
        } else return new Response(false, Response.INVALIG_SG());
    }
}
