package console;

import java.util.function.BiConsumer;
import java.util.function.Function;

public interface AskTemplate {
    <B, T> B askThing(String prompt, B builder, BiConsumer<B, T> setter, Function<String, T> parser, Function<T, String> configRule);

    class AskBreak extends RuntimeException {
        @Override
        public String getMessage() {
            return "Exiting without saving...";
        }
    }
}
