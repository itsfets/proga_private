package commands;

import core.StandardCollection;
import network.Response;

public class Clear extends Command {
    private final StandardCollection standardCollection;

    public Clear(StandardCollection standardCollection) {
        super("clear", "clears the Collection");
        this.standardCollection = standardCollection;
    }

    @Override
    public Response apply(Object request_data) {
        standardCollection.clearCollection();
        return new Response(true, "collection has been cleared successfully!");
    }
}
