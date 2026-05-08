package console.standard;

import core.builders.BuilderTemplate;
import core.builders.StandardBuilder;
import core.models.standard.*;

import java.util.function.BiConsumer;
import java.util.function.Function;


public class Ask implements console.AskTemplate {
    private final Console console;
    private final BuilderTemplate<StudyGroup> builder;

    public Ask(Console console, BuilderTemplate<StudyGroup> builder) {
        this.console = console;
        this.builder = new StandardBuilder();
    }

    @Override
    public <B, T> B askThing(String prompt, B builder, BiConsumer<B, T> setter, Function<String, T> parser, Function<T, String> configRule) {
        while (true) {
            console.print(prompt);
            try {
                String line = console.readln().trim();
                if (line.equals("exit")) {
                    throw new AskBreak();
                }
                T value = parser.apply(line);
                String err = configRule.apply(value);
                if (err == null) {
                    setter.accept(builder, value);
                    return builder;
                } else {
                    console.println(err);
                }
            } catch (AskBreak e) {
                throw e;
            } catch (Exception e) {
                console.println("Data format exception (Unconvertable type/Number too big/Invalid choice)! Please try again.");
            }
        }
    }
}