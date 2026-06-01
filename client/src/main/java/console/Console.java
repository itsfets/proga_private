package console;

import java.io.PrintStream;
import java.util.ArrayDeque;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Console {
    private static final int MAX_RECURSION = 16;
    private final PrintStream out = System.out;
    private final PrintStream err = System.err;
    private final Scanner sc;
    private final String prompt = " ";
    private final ArrayDeque<Scanner> scanners = new ArrayDeque<>(MAX_RECURSION);

    public Console() {
        this.sc = new Scanner(System.in);
        scanners.push(sc);
    }

    public void prompt() {
        print(prompt);
    }

    public String getPrompt() {
        return prompt;
    }

    public String readln() throws NoSuchElementException, IllegalStateException {
        Scanner s = scanners.peek();
        if (!s.hasNextLine()) throw new NoSuchElementException();
        return s.nextLine();
    }

    public boolean isCanReadln() {
        return sc.hasNextLine();
    }

    public void print(String string) {
        out.print(string);
    }

    public void println(String string) {
        out.println(string);
    }

    public void printErr(String string) {
        err.println("Error: " + string);
    }

    public void selectFileSC(Scanner scanner) {

    }

    public void selectConsoleSC() {
        if (scanners.size() <= 1) return;
        Scanner s = scanners.pop();
        if (s != sc) s.close();
    }
}
