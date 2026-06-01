package commands;

import network.Response;

public class ExecuteScript extends Command {

    public ExecuteScript() {
        super("execute_script <file_name>", "reads and executes commands from a given script file. commands must be written in the same way you would write them here");
    }

    @Override
    public Response apply(Object request_data) {
        return null;
    }
}