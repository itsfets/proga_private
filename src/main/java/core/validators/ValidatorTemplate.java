package core.validators;

public interface ValidatorTemplate<T>{
    public ValidationResult validate(T input);
}
