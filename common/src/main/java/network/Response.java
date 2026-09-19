package network;

import java.io.Serial;
import java.io.Serializable;

public record Response(boolean success, String message, String data) implements Serializable {
    public static final String INVALIG_AUTH = "you are not authorized! use commands 'set_login' and 'set_password' to set your credentials!";
    public static final String INVALIG_SG = "studyGroup is not valid!";
    public static final String WRONG_TYPE = "data provided is not of type ";
    @Serial
    private static final long serialVersionUID = 1L;

    public Response(boolean success, String message) {
        this(success, message, null);
    }

    @Override
    public String toString() {
        return "Response[success=" + success + ", " +
                "message=" + message + ", " +
                "data=" + data + "]";
    }
}
