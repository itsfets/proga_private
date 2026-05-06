package core.validators.standard;

import core.models.standard.Coordinates;
import core.validators.ValidationResult;
import core.validators.ValidatorTemplate;

public class CoordinatesValidator implements ValidatorTemplate<Coordinates> {
    @Override
    public ValidationResult validate(Coordinates coordinates) {

        if (coordinates == null) { return ValidationResult.fail("coordinates cannot be null!"); }

        if (coordinates.getX() <= -740) { return ValidationResult.fail("coordinates.x cannot be less than -740!"); }

        return ValidationResult.success();
    }
}
