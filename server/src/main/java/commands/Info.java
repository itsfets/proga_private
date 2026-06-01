package commands;

import core.StandardCollection;
import network.Response;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Info extends Command {
    private final StandardCollection collectionManager;

    public Info(StandardCollection collectionManager) {
        super("info", "returns information about the Collection (type, size, last initialization date)");
        this.collectionManager = collectionManager;
    }

    private String handleInitTime(LocalDateTime initTime) {
        if (initTime == null) return "collection hasn't been initialized during this session";
        else return initTime.toLocalDate().toString() + " " + initTime.toLocalTime().toString();
    }

    @Override
    public Response apply(Object request_data) {
        String s = "[INFORMATION ABOUT THE COLLECTION]\n" + "Type: " + collectionManager.getCollection().getClass() + "\n" +
                "Size: " + collectionManager.getCollection().size() + "\n" +
                handleInitTime(collectionManager.getInitTime().truncatedTo(ChronoUnit.SECONDS));
        return new Response(true, "command executed successfully!", s);
    }
}
