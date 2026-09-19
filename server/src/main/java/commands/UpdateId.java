package commands;

import core.StandardCollection;
import dto.StudyGroup;
import modelworks.StandardValidator;
import network.Response;

public class UpdateId extends Command {
    private final StandardCollection collectionManager;
    private final StandardValidator validator;

    public UpdateId(StandardCollection collectionManager, StandardValidator validator) {
        super("update_id {element}", "updates values of a studyGroup with the given ID in the Collection");
        this.collectionManager = collectionManager;
        this.validator = validator;
    }

    @Override
    public Response apply(Object request_data, String login) {
        StudyGroup studyGroup = (StudyGroup) request_data;
        studyGroup.setCreatedBy(login);
        if (collectionManager.byId(studyGroup.getId()) == null)
            return new Response(false, "no studyGroup with given ID in the collection! Collection was untouched!");
        if (!validator.validate(studyGroup).isValid()) return new Response(false, "invalid studyGroup!");
        boolean res = collectionManager.update(studyGroup, login);
        if (res) return new Response(true, "command executed successfully!");
        return new Response(false, "failed to update studyGroup!");
    }
}
