package commands;

import core.StandardCollection;
import network.Response;

public class Show extends Command {
    private final StandardCollection collectionManager;

    public Show(StandardCollection collectionManager) {
        super("show", "prints out string representation of every studyGroup in the Collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Object request_data) {
        String s = collectionManager.toString();
        return new Response(true, "command executed successfully!", s);
    }
}
