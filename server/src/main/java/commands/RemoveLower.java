package commands;

import core.StandardCollection;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Response;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class RemoveLower extends Command {
    private final StandardValidator standardValidator;
    private final StandardCollection standardCollection;

    public RemoveLower(StandardCollection standardCollection, StandardValidator standardValidator) {
        super("remove_lower {StudyGroup}", "removes all studyGroups with the averageMark value lower than the averageMark value of input studyGroup");
        this.standardCollection = standardCollection;
        this.standardValidator = standardValidator;
    }

    @Override
    public Response apply(Object request_data, String login) {
        if (!(request_data instanceof StudyGroup studyGroup))
            return new Response(false, Response.WRONG_TYPE + "studyGroup");
        if (!standardValidator.validate(studyGroup).isValid()) return new Response(false, Response.INVALIG_SG);
        double controlAvgMark = studyGroup.getAverageMark();
        List<Integer> idsToRemove = new ArrayList<>();
        standardCollection.getCollection().stream().filter(group -> group.getAverageMark() < controlAvgMark).filter(group -> Objects.equals(group.getCreatedBy(), login)).forEach(group -> {
            idsToRemove.add(group.getId());
        });
        if (idsToRemove.isEmpty()) return new Response(true, "no studyGroups passed the filter!");
        for (Integer id : idsToRemove) {
            standardCollection.remove(standardCollection.byId(id), login);
        }
        return new Response(true, "studyGroups were removed successfully");
    }
}
