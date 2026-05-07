package console.standard;

import core.builders.StandardBuilder;
import core.configs.StandardConfig;
import core.managers.standard.CollectionManager;
import core.models.standard.Color;
import core.models.standard.FormOfEducation;
import core.models.standard.StudyGroup;
import core.validators.standard.StudyGroupValidator;

import java.util.Arrays;

public class Asker {
    private final Ask ask;

    public Asker(Ask ask) {
        this.ask = ask;
    }

    public StudyGroup askStudyGroup(StudyGroupValidator validator, int id) {
        StandardBuilder builder = new StandardBuilder();
        ask.askThing("name: ", builder, StandardBuilder::name, s -> s, StandardConfig.NAME);
        ask.askThing("coordinates.x: ", builder, StandardBuilder::coordinates_x, Integer::valueOf, StandardConfig.COORDINATES_X);
        ask.askThing("coordinates.y: ", builder, StandardBuilder::coordinates_y, Integer::valueOf, StandardConfig.COORDINATES_Y);
        ask.askThing("studentsCount: ", builder, StandardBuilder::studentsCount, Long::valueOf, StandardConfig.STUDENTS_COUNT);
        ask.askThing("transferredStudents: ", builder, StandardBuilder::transferredStudents, Long::valueOf, StandardConfig.STUDENTS_COUNT);
        ask.askThing("averageMark: ", builder, StandardBuilder::averageMark, Double::valueOf, StandardConfig.AVERAGE_MARK);
        ask.askThing("formOfEducation (" + Arrays.toString(FormOfEducation.values()) + ") : ", builder, StandardBuilder::formOfEducation, FormOfEducation::valueOf, StandardConfig.FORM_OF_EDUCATION);
        ask.askThing("Has groupAdmin? (True for Yes, anything else is treated as No): ", builder, StandardBuilder::hasGroupAdmin, Boolean::valueOf, StandardConfig.HAS_GROUP_ADMIN);
        if (builder.get_hasGroupAdmin()) {
            ask.askThing("groupAdmin.name: ", builder, StandardBuilder::groupAdmin_name, s -> s, StandardConfig.PERSON_NAME);
            ask.askThing("groupAdmin.height: ", builder, StandardBuilder::groupAdmin_height, Double::valueOf, StandardConfig.PERSON_HEIGHT);
            ask.askThing("groupAdmin.eyeColor (" + Arrays.toString(Color.values()) + "): ", builder, StandardBuilder::groupAdmin_eyeColor, Color::valueOf, StandardConfig.PERSON_COLOR);
            ask.askThing("groupAdmin.location.x: ", builder, StandardBuilder::groupAdmin_location_x, Float::valueOf, StandardConfig.LOCATION_X);
            ask.askThing("groupAdmin.location.y: ", builder, StandardBuilder::groupAdmin_location_y, Double::valueOf, StandardConfig.LOCATION_Y);
            ask.askThing("groupAdmin.location.z: ", builder, StandardBuilder::groupAdmin_location_z, Long::valueOf, StandardConfig.LOCATION_Z);
            ask.askThing("groupAdmin.location.name: ", builder, StandardBuilder::groupAdmin_location_name, s -> s, StandardConfig.LOCATION_NAME);
        }
        return builder.build(validator, id);
    }


}
