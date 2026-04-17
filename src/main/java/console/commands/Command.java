package console.commands;

import core.models.ExecutionResponse;

public abstract class Command {
    private final String name;
    private final String desc;

    public Command(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }


    public abstract ExecutionResponse apply(String[] argument);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Command command = (Command) obj;
        return name.equals(command.name) && desc.equals(command.desc);
    }

    @Override
    public int hashCode() {
        return name.hashCode() + desc.hashCode();
    }

    @Override
    public String toString() {
        return name + " - " + desc;
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
