package core.validators;

public interface ValidatorTemplate<T>{
    ValidationResult validate(T input);
}
