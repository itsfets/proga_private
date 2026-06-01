package modelworks;

import java.util.List;

public record ValidationResult(boolean isValid, List<String> errors) {
    public static ValidationResult success() {
        return new ValidationResult(true, null);
    }

    public static ValidationResult fail(List<String> errors) {
        return new ValidationResult(false, errors);
    }

    public static ValidationResult fail(String error) {
        return fail(List.of(error));
    }

    public ValidationResult prefixError(String path) {
        if (!isValid) {
            return fail(errors.stream().map(e -> path + "." + e).toList());
        }
        return this;
    }
}
