package network;

import dto.Commands;

import java.io.Serial;
import java.io.Serializable;

public record Request(Commands command, Object data, String login, String password) implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public Request(Commands command, String login, String password) {
        this(command, null, login, password);
    }

    @Override
    public String toString() {
        return "Request[command=" + command + ", " +
                "data=" + data + ", " +
                "login=" + login + ", " +
                "password=" + password + "]";
    }
}
