package commands;

import core.StandardCollection;
import network.Response;

public class RemoveById extends Command {
    private final StandardCollection collectionManager;

    public RemoveById(StandardCollection collectionManager) {
        super("remove_by_id <id>", "removes studyGroup with given ID");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Object request_data) {
        try {
            int id = Integer.parseInt((String) request_data);
            boolean success = collectionManager.remove(id);
            if (success) {
                return new Response(true, "studyGroup successfully removed!");
            } else return new Response(false, "failed to remove studyGroup!");
        } catch (NumberFormatException e) {
            return new Response(false, Response.WRONG_TYPE() + "Integer");
        }

    }

}
