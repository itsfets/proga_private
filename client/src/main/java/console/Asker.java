package console;

import dto.Color;
import dto.FormOfEducation;
import dto.StudyGroup;
import modelworks.StandardBuilder;
import modelworks.StandardConfig;
import modelworks.StandardValidator;

import java.util.Arrays;

public class Asker {
    private final Ask ask;

    public Asker(Ask ask) {
        this.ask = ask;
    }

    private void askMain(StandardBuilder builder) {
        ask.askThing("name: ", builder, StandardBuilder::name, s -> s, StandardConfig.NAME)
                .askThing("coordinates.x: ", builder, StandardBuilder::coordinates_x, Integer::valueOf, StandardConfig.COORDINATES_X)
                .askThing("coordinates.y: ", builder, StandardBuilder::coordinates_y, Integer::valueOf, StandardConfig.COORDINATES_Y)
                .askThing("studentsCount: ", builder, StandardBuilder::studentsCount, Long::valueOf, StandardConfig.STUDENTS_COUNT)
                .askThing("transferredStudents: ", builder, StandardBuilder::transferredStudents, Long::valueOf, StandardConfig.TRANSFERRED_STUDENTS)
                .askThing("averageMark: ", builder, StandardBuilder::averageMark, Double::valueOf, StandardConfig.AVERAGE_MARK)
                .askThing("formOfEducation (can't be null)\n" + Arrays.toString(FormOfEducation.values()) + ": ", builder, StandardBuilder::formOfEducation, FormOfEducation::valueOf, StandardConfig.FORM_OF_EDUCATION)
                .askThing("Has groupAdmin? (True for Yes, anything else is treated as No): ", builder, StandardBuilder::hasGroupAdmin, Boolean::valueOf, StandardConfig.HAS_GROUP_ADMIN);
        if (builder.get_hasGroupAdmin()) {
            ask.askThing("groupAdmin.name: ", builder, StandardBuilder::groupAdmin_name, s -> s, StandardConfig.PERSON_NAME)
                    .askThing("groupAdmin.height: ", builder, StandardBuilder::groupAdmin_height, Double::valueOf, StandardConfig.PERSON_HEIGHT)
                    .askThing("groupAdmin.eyeColor (can be null)\n" + Arrays.toString(Color.values()) + ": ", builder, StandardBuilder::groupAdmin_eyeColor, s -> s.isBlank() ? null : Color.valueOf(s.toUpperCase()), StandardConfig.PERSON_COLOR)
                    .askThing("groupAdmin.location.x: ", builder, StandardBuilder::groupAdmin_location_x, Float::valueOf, StandardConfig.LOCATION_X)
                    .askThing("groupAdmin.location.y: ", builder, StandardBuilder::groupAdmin_location_y, Double::valueOf, StandardConfig.LOCATION_Y)
                    .askThing("groupAdmin.location.z: ", builder, StandardBuilder::groupAdmin_location_z, Long::valueOf, StandardConfig.LOCATION_Z)
                    .askThing("groupAdmin.location.name: ", builder, StandardBuilder::groupAdmin_location_name, s -> s, StandardConfig.LOCATION_NAME);
        }
    }

    public StudyGroup askStudyGroup(StandardValidator validator, int id) {
        StandardBuilder builder = new StandardBuilder();
        askMain(builder);
        return builder.build(validator, id);
    }

    public StudyGroup askStudyGroup(StandardValidator validator) {
        StandardBuilder builder = new StandardBuilder();
        ask.askThing("id: ", builder, StandardBuilder::id, Integer::valueOf, StandardConfig.ID);
        askMain(builder);
        return builder.build(validator);
    }
}
