package console.standard;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;
import java.lang.IllegalStateException;

public class Console implements console.Console {
    private static final int MAX_DEPTH = 16;
    private final PrintStream err, out;
    private final Scanner baseScanner;
    private final String prompt;
    private final Deque<Scanner> scannerStack = new ArrayDeque<>(MAX_DEPTH);

    public Console() {
        this(System.in, System.out, System.err, "> ");
    }

    public Console(InputStream in, PrintStream out, PrintStream err, String prompt) {
        this.baseScanner = new Scanner(in);
        scannerStack.push(baseScanner);
        this.out = out;
        this.err = err;
        this.prompt = prompt != null ? prompt : "> ";
    }

    @Override
    public void print(Object obj) {
        out.print(obj);
    }

    @Override
    public void println(Object obj) {
        out.println(obj);
    }

    @Override
    public void printError(Object obj) {
        err.println("Error: " + obj);
    }

    @Override
    public String readln() throws NoSuchElementException, IllegalStateException {
        Scanner scanner = scannerStack.peek();
        if (!scanner.hasNextLine()) throw new NoSuchElementException();
        return scanner.nextLine();
    }

    @Override
    public boolean isCanReadln() throws IllegalStateException {
        return scannerStack.peek().hasNextLine();
    }

    @Override
    public void prompt() {
        print(prompt);
    }

    @Override
    public String getPrompt() {
        return prompt;
    }

    @Override
    public void selectFileScanner(Scanner scanner) {
        if (scannerStack.size() >= MAX_DEPTH) {
            if (scanner != null) scanner.close();
            throw new IllegalStateException("Maximum recursion depth (" + MAX_DEPTH + ") reached!");
        }
        scannerStack.push(scanner);
    }

    @Override
    public void selectConsoleScanner() {
        if (scannerStack.size() <= 1) return;
        Scanner scanner = scannerStack.pop();
        if (scanner != baseScanner) scanner.close();
    }
}