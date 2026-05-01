package console;

public class ExecutionResponse {
    private String message;
    private boolean status;

    public ExecutionResponse(String message, boolean success) {
        this.message = message;
        this.status = success;
    }

    public ExecutionResponse() {}

    public ExecutionResponse(String message) {
        this(message, true);
    }

    public ExecutionResponse cancelExecutionMessage() {
        return new ExecutionResponse("The operation was cancelled", false);
    }

    public ExecutionResponse fileNotFoundMessage() {return new ExecutionResponse("File doesn't exist!", false);}

    public ExecutionResponse noArgExecutionMessage() {return new ExecutionResponse("This command doesn't support arguments!", false);}

    public ExecutionResponse wrongArgCountMessage() {return new ExecutionResponse("Wrong number of arguments provided!", false);}

    public boolean getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public String toString() {
        return status +"; "+message;
    }
}
