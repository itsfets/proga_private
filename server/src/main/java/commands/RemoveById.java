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
    public Response apply(Object request_data, String login) {
        try {
            int id = Integer.parseInt(request_data.toString());
            boolean success = collectionManager.remove(collectionManager.byId(id), login);
            if (success) return new Response(true, "studyGroup successfully removed!");
            return new Response(false, "failed to remove studyGroup!");
        } catch (NumberFormatException e) {
            return new Response(false, Response.WRONG_TYPE + "Integer");
        }

    }

}
