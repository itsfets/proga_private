package core.validators.standard;

import core.models.standard.Color;
import core.models.standard.Location;
import core.models.standard.Person;
import core.validators.ValidationResult;
import core.validators.ValidatorTemplate;

import java.util.EnumSet;

public class PersonValidator implements ValidatorTemplate<Person> {
    private final ValidatorTemplate<Location> locationValidator;

    public PersonValidator(ValidatorTemplate<Location> locationValidator) {
        this.locationValidator = locationValidator;
    }

    @Override
    public ValidationResult validate(Person person) {

        if (person.getName() == null || person.getName().isEmpty()) return ValidationResult.fail("name cannot be null or empty!");

        if (person.getHeight() <= 0) return ValidationResult.fail("height must be more than 0!");

        if (person.getEyeColor() != null && !EnumSet.allOf(Color.class).contains(person.getEyeColor())) return ValidationResult.fail("eyeColor is not valid!");

        ValidationResult locationResult = locationValidator.validate(person.getLocation());
        if (!locationResult.isValid()) return ValidationResult.fail(locationResult.errors());

        return ValidationResult.success();
    }
}
