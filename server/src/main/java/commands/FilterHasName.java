package commands;


import core.StandardCollection;
import network.Response;

public class FilterHasName extends Command {
    private final StandardCollection standardCollection;

    public FilterHasName(StandardCollection standardCollection) {
        super("filter_contains_name <name>", "returns all studyGroups, who have <name> in their Name field");
        this.standardCollection = standardCollection;
    }

    @Override
    public Response apply(Object request_data) {
        String filter = request_data.toString();
        StringBuilder s = new StringBuilder("Result:\n");
        standardCollection.getCollection().stream().filter(group -> group.getName().equalsIgnoreCase(filter)).forEach(group -> s.append(group).append("\n"));
        return new Response(true, "command executed successfuly!", s.toString());
    }
}
