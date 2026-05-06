package console;

import java.util.NoSuchElementException;
import java.util.Scanner;

public interface Console {
    void print(Object obj);

    void println(Object obj);

    void printError(Object obj);

    String readln() throws NoSuchElementException, IllegalStateException;

    boolean isCanReadln() throws IllegalStateException;

    void prompt();

    String getPrompt();

    void selectFileScanner(Scanner scanner);

    void selectConsoleScanner();
}
