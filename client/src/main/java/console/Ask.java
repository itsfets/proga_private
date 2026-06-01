package console;

import java.util.function.BiConsumer;
import java.util.function.Function;


public class Ask {
    private final Console console;

    public Ask(Console console) {
        this.console = console;
    }

    public <B, T> Ask askThing(String prompt, B builder, BiConsumer<B, T> setter, Function<String, T> parser, Function<T, String> configRule) {
        while (true) {
            console.print(prompt);
            try {
                String line = console.readln().trim();
                if (line.equalsIgnoreCase("exit")) {
                    throw new AskBreak();
                }
                T value = parser.apply(line);
                String err = configRule.apply(value);
                if (err == null) {
                    setter.accept(builder, value);
                    return this;
                } else {
                    console.println(err);
                }
            } catch (AskBreak e) {
                throw e;
            } catch (java.util.NoSuchElementException e) {
                throw new IllegalStateException("END_OF_INPUT");
            } catch (Exception e) {
                console.println("Data format exception (Unconvertable type/Number too big/Invalid choice)! Please try again.");
            }
        }
    }

    public static class AskBreak extends RuntimeException {
        @Override
        public String getMessage() {
            return "Exiting without saving...";
        }
    }
}