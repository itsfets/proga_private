package network;

import java.io.Serializable;

public class Response implements Serializable {
    private static final long serialVersionUID = 1L;
    private final boolean success;
    private final String message;
    private final String data;

    public Response(boolean success, String message) {
        this(success, message, null);
    }

    public Response(boolean success, String message, String data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static String WRONG_TYPE() {
        return "data provided is not of type ";
    }

    public static String INVALIG_SG() {
        return "studyGroup is not valid!";
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }

    public String getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Response[success=" + success + ", " +
                "message=" + message + ", " +
                "data=" + data + "]";
    }
}
