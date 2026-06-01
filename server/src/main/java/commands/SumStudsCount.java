package commands;

import core.StandardCollection;
import network.Response;

public class SumStudsCount extends Command {
    private final StandardCollection collectionManager;

    public SumStudsCount(StandardCollection collectionManager) {
        super("sum_of_students_count", "returns the sum of studentsCount for all studyGroups in the Collection");
        this.collectionManager = collectionManager;
    }

    @Override
    public Response apply(Object request_data) {
        long sum = 0;
        for (var studyGroup : collectionManager.getCollection()) {
            sum += studyGroup.getStudentsCount();
        }
        return new Response(true, "command executed successfully!", String.valueOf(sum));
    }
}
