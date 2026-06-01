package commands;

import network.Response;

public abstract class Command {
    private final String name;
    private final String desc;

    public Command(String name, String desc) {
        this.name = name;
        this.desc = desc;
    }

    public abstract Response apply(Object request_data);

    @Override
    public boolean equals(java.lang.Object obj) {
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
        return String.format("%1$30s - %2$s", name, desc);
    }

    public String getName() {
        return name;
    }

    public String getDesc() {
        return desc;
    }
}
