package core;

import console.StandardConsole;
import core.managers.StandardCommandManager;
import core.models.ExecutionResponse;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class StandardRunner {
    private final StandardConsole console;
    private final StandardCommandManager standardCommandManager;
    private final List<String> commandStack = new ArrayList<>();
    private final int MAX_RECURSION_DEPTH = 3;

    public StandardRunner(StandardConsole console, StandardCommandManager standardCommandManager) {
        this.console = console;
        this.standardCommandManager = standardCommandManager;
    }

    public void interactiveMode() {
        try {
            ExecutionResponse status;
            String[] input;
            while (true) {
                console.prompt();
                input = (console.readln().trim() + " ").split(" ", 2);
                input[1] = input[1].trim();
                status = executeCommand(input);
                if (status.getMessage().equals("exit")) break;
                console.println(status.getMessage());
            }
        } catch (NoSuchElementException e) {
            console.println("Failed reading user input");
        } catch (IllegalStateException e) {
            console.println("Unexpected error!");
        }
    }

    private boolean checkRecursionLevel(String scriptPath) {
        if (!commandStack.isEmpty() && commandStack.get(commandStack.size() - 1).equals(scriptPath)) {
            console.printError("Direct recursion detected for script: " + scriptPath);
            return false;
        }

        if (commandStack.size() >= MAX_RECURSION_DEPTH) {
            console.printError("Maximum script recursion depth (" + MAX_RECURSION_DEPTH + ") exceeded!");
            return false;
        }
        return true;
    }

    private ExecutionResponse scriptMode(String arg) {
        var path = System.getenv(arg);
        String[] input = {"", ""};
        StringBuilder executionOutput = new StringBuilder();
        if (!new File(path).exists()) return new ExecutionResponse().fileNotFoundMessage();
        if (!Files.isReadable(Paths.get(path))) return new ExecutionResponse("No permission to read the file!", false);
        commandStack.add(path);
        try (Scanner scriptScanner = new Scanner(new File(path))) {
            ExecutionResponse scriptExec;
            if (!scriptScanner.hasNext()) throw new NoSuchElementException();
            console.selectFileScanner(scriptScanner);
            do {
                input = (console.readln().trim() + " ").split(" ", 2);
                input[1] = input[1].trim();
                while (console.isCanReadln() && input[0].isEmpty()) {
                    input = (console.readln().trim() + " ").split(" ", 2);
                    input[1] = input[1].trim();
                }
                executionOutput.append(console.getPrompt()).append(String.join(" ", input)).append("\n");
                var needLaunch = true;
                if (input[0].equals("execute_script")) {
                    needLaunch = checkRecursionLevel(input[1]);
                }
                scriptExec = needLaunch ? executeCommand(input) : new ExecutionResponse(
                        "Recursion level (" + MAX_RECURSION_DEPTH + ") was exceeded! Stopping execution", false);
                if (Objects.equals(input[0], "execute_script")) {
                    console.selectFileScanner(scriptScanner);
                }
                executionOutput.append(scriptExec.getMessage()).append("\n");
            } while (scriptExec.getStatus() && !scriptExec.getMessage().equals("exit") && console.isCanReadln());
                console.selectConsoleScanner();
                if (!scriptExec.getStatus() && !(input[0].equals("execute_script") && !input[1].isEmpty())) {
                    executionOutput.append("Incorrect data in the script! Please try again.\n");
                }
            return new ExecutionResponse(executionOutput.toString(), scriptExec.getStatus());
        } catch (FileNotFoundException e) {
            return new ExecutionResponse().fileNotFoundMessage();
        } catch (NoSuchElementException e) {
            return new ExecutionResponse("Script file is empty!", false);
        } catch (IllegalStateException e) {
            console.println("Unexpected error!");
            System.exit(1);
        } finally {
            commandStack.remove(commandStack.size() - 1);
            console.selectConsoleScanner();
        }
        return new ExecutionResponse("");
    }

    private ExecutionResponse executeCommand(String[] input) {
        if (input[0].isEmpty()) { return new ExecutionResponse(""); }
        var command = standardCommandManager.getCommands().get(input[0]);
        if (command == null) { return new ExecutionResponse("Command " + input[0] + " not found! Please use help command for a list of available commands.", false); }
        if (input[0].equals("execute_script")) {
            ExecutionResponse tmp = standardCommandManager.getCommands().get("execute_script").apply(input);
            if (!tmp.getStatus()) return tmp;
            ExecutionResponse tmp2 = scriptMode(input[1]);
            return new ExecutionResponse(tmp.getMessage() + "\n" + tmp2.getMessage(), tmp2.getStatus());
        } else {
            return command.apply(input);
        }
    }
}
