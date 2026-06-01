package dto;

public enum Commands {
    ADD("add"),
    ADDIFMAX("add_if_max"),
    ADDIFMIN("add_if_min"),
    CLEAR("clear"),
    EXECUTESCRIPT("execute_script"),
    EXIT("exit"),
    FILTERHASNAME("filter_contains_name"),
    GROUPBYAVGMARK("group_counting_by_average_mark"),
    HELP("help"),
    INFO("info"),
    REMOVEBYID("remove_by_id"),
    REMOVELOWER("remove_lower"),
    SHOW("show"),
    SUMSTUDSCOUNT("sum_of_students_count"),
    UPDATEID("update_id");

    private final String command;

    Commands(String command) {
        this.command = command;
    }

    public static Commands fromString(String name) {
        for (Commands commands : Commands.values()) {
            if (commands.command.equalsIgnoreCase(name)) {
                return commands;
            }
        }
        throw new IllegalArgumentException("Invalid command: " + name);
    }

    public String getString() {
        return command;
    }
}
