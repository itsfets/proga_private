package core.validators.standard;

import core.models.standard.Coordinates;
import core.models.standard.Location;
import core.models.standard.Person;
import core.models.standard.StudyGroup;
import core.validators.ValidationResult;
import core.validators.ValidatorTemplate;

public class StudyGroupValidator implements ValidatorTemplate<StudyGroup> {
    private final ValidatorTemplate<Coordinates> coordinatesValidator = new  CoordinatesValidator();
    private final ValidatorTemplate<Location> locationValidator = new  LocationValidator();
    private final ValidatorTemplate<Person> personValidator = new PersonValidator(locationValidator);

    @Override
    public ValidationResult validate(StudyGroup studyGroup) {

        if (studyGroup == null) return ValidationResult.fail("studyGroup cannot be null!");

        if (studyGroup.getId() <= 0) return ValidationResult.fail("studyGroup.id must be greater than zero!");

        if (studyGroup.getName() == null || studyGroup.getName().isEmpty()) return ValidationResult.fail("studyGroup.name cannot be null or empty!");

        ValidationResult coordinatesResult = coordinatesValidator.validate(studyGroup.getCoordinates());
        if (!coordinatesResult.isValid()) return ValidationResult.fail(coordinatesResult.errors());

        if (studyGroup.getCreationDate() == null) return ValidationResult.fail("studyGroup.creationDate cannot be null!");

        if (studyGroup.getStudentsCount() <= 0) return ValidationResult.fail("studyGroup.studentsCount must be greater than zero!");

        if (studyGroup.getTransferredStudents() <= 0) return ValidationResult.fail("studyGroup.transferredStudents must be greater than zero!");

        if (studyGroup.getAverageMark() <= 0) return ValidationResult.fail("studyGroup.averageMark must be greater than zero!");

        if (studyGroup.getFormOfEducation() == null) return ValidationResult.fail("studyGroup.formOfEducation cannot be null!");

        if (studyGroup.getGroupAdmin() != null) {
            ValidationResult personResult = personValidator.validate(studyGroup.getGroupAdmin());
            if (!personResult.isValid()) return ValidationResult.fail(personResult.errors());
        }

        return ValidationResult.success();
    }
}
