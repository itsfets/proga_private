package core.validators.standard;

import core.models.standard.Location;
import core.validators.ValidationResult;
import core.validators.ValidatorTemplate;

public class LocationValidator implements ValidatorTemplate<Location> {
    @Override
    public ValidationResult validate(Location location) {

        if (location == null) return ValidationResult.fail("location cannot be null!");

        if (location.getX() == null) return ValidationResult.fail("location.x cannot be null!");

        if (location.getY() == null) return ValidationResult.fail("location.y cannot be null!");

        if (location.getZ() == null) return ValidationResult.fail("location.z cannot be null!");

        return ValidationResult.success();
    }
}
