package network;

import dto.Commands;

import java.io.Serial;
import java.io.Serializable;

public class Request implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private final Commands command;
    private final java.lang.Object data;

    public Request(Commands command) {
        this.command = command;
        this.data = null;
    }

    public Request(Commands command, java.lang.Object data) {
        this.command = command;
        this.data = data;
    }

    public Commands getCommand() {
        return command;
    }

    public java.lang.Object getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Request[command=" + command + ", " +
                "data=" + data + ']';
    }
}
