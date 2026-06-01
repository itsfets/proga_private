package modelworks;

import dto.StudyGroup;

public class StandardValidator {

    public ValidationResult validate(StudyGroup group) {
        if (group == null) return ValidationResult.fail("studyGroup cannot be null!");

        String error;

        error = StandardConfig.NAME.apply(group.getName());
        if (error != null) return ValidationResult.fail(error);

        if (group.getCreationDate() == null) return ValidationResult.fail("creationDate cannot be null!");

        error = StandardConfig.FORM_OF_EDUCATION.apply(group.getFormOfEducation());
        if (error != null) return ValidationResult.fail(error);

        if (group.getCoordinates() == null) return ValidationResult.fail("coordinates cannot be null!");
        error = StandardConfig.COORDINATES_X.apply(group.getCoordinates().getX());
        if (error != null) return ValidationResult.fail(error);
        error = StandardConfig.COORDINATES_Y.apply(group.getCoordinates().getY());
        if (error != null) return ValidationResult.fail(error);

        error = StandardConfig.STUDENTS_COUNT.apply(group.getStudentsCount());
        if (error != null) return ValidationResult.fail(error);

        error = StandardConfig.TRANSFERRED_STUDENTS.apply(group.getTransferredStudents());
        if (error != null) return ValidationResult.fail(error);

        error = StandardConfig.AVERAGE_MARK.apply(group.getAverageMark());
        if (error != null) return ValidationResult.fail(error);

        if (group.getGroupAdmin() != null) {
            var admin = group.getGroupAdmin();

            error = StandardConfig.PERSON_NAME.apply(admin.getName());
            if (error != null) return ValidationResult.fail(error);

            error = StandardConfig.PERSON_HEIGHT.apply(admin.getHeight());
            if (error != null) return ValidationResult.fail(error);

            error = StandardConfig.PERSON_COLOR.apply(admin.getEyeColor());
            if (error != null) return ValidationResult.fail(error);

            if (admin.getLocation() == null) return ValidationResult.fail("groupAdmin.location cannot be null!");
            var loc = admin.getLocation();

            error = StandardConfig.LOCATION_X.apply(loc.getX());
            if (error != null) return ValidationResult.fail(error);

            error = StandardConfig.LOCATION_Y.apply(loc.getY());
            if (error != null) return ValidationResult.fail(error);

            error = StandardConfig.LOCATION_Z.apply(loc.getZ());
            if (error != null) return ValidationResult.fail(error);

            error = StandardConfig.LOCATION_NAME.apply(loc.getName());
            if (error != null) return ValidationResult.fail(error);
        }

        return ValidationResult.success();
    }
}
