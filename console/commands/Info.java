package console.commands;

import console.Console;
import console.ExecutionResponse;
import core.managers.standard.CollectionManager;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Info extends Command {
    private final CollectionManager collectionManager;

    public Info(Console console, CollectionManager collectionManager) {
        super("info", "Returns information about the Collection (type, size, last initialization date)");
        this.collectionManager = collectionManager;
    }

    public ExecutionResponse apply(String[] arguments) {
        if (!arguments[1].isBlank()) return new ExecutionResponse().noArgExecutionMessage();
        String s = "[INFORMATION ABOUT THE COLLECTION]\n";
        s += "Type: " + collectionManager.getCollection().getClass() + "\n";
        s += "Size: " + collectionManager.getCollection().size() + "\n";
        s += handleInitTime(collectionManager.getInitTime().truncatedTo(ChronoUnit.SECONDS));
        return new ExecutionResponse(s);
    }

    private String handleInitTime(LocalDateTime initTime) {
        if  (initTime == null) return "Collection hasn't been initialized during this session";
        else return initTime.toLocalDate().toString() + " " + initTime.toLocalTime().toString();
    }
}
