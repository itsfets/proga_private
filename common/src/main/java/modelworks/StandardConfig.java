package modelworks;

import dto.Color;
import dto.FormOfEducation;

import java.util.function.Function;

public class StandardConfig {

    public static final Function<Integer, String> ID = s
            -> (s <= 0) ? "id must be higher than 0!" : null;

    public static final Function<String, String> NAME = s
            -> (s == null || s.isBlank()) ? "name cannot be empty or null!" : null;
    public static final Function<Long, String> STUDENTS_COUNT = s
            -> (s <= 0) ? "studentsCount must be higher than 0!" : null;
    public static final Function<Long, String> TRANSFERRED_STUDENTS = s
            -> (s <= 0) ? "transferredStudentsCount must be higher than 0!" : null;
    public static final Function<Double, String> AVERAGE_MARK = s
            -> (s <= 0) ? "averageMark must be higher than 0!" : null;
    public static final Function<FormOfEducation, String> FORM_OF_EDUCATION = s
            -> (s == null) ? "formOfEducation cannot be null!" : null;

    public static final Function<Integer, String> COORDINATES_X = s
            -> (s <= -740) ? "coordinates.x must be higher than -740!" : null;
    public static final Function<Integer, String> COORDINATES_Y = s
            -> null;

    public static final Function<Boolean, String> HAS_GROUP_ADMIN = s
            -> null;

    public static final Function<String, String> PERSON_NAME = s
            -> (s == null || s.isBlank()) ? "groupAdmin.name cannot be empty or null!" : null;
    public static final Function<Double, String> PERSON_HEIGHT = s
            -> (s <= 0) ? "groupAdmin.height must be higher than 0!" : null;
    public static final Function<Color, String> PERSON_COLOR = s
            -> null;

    public static final Function<Float, String> LOCATION_X = s
            -> (s == null) ? "groupAdmin.location.x cannot be null" : null;
    public static final Function<Double, String> LOCATION_Y = s
            -> (s == null) ? "groupAdmin.location.y cannot be null" : null;
    public static final Function<Long, String> LOCATION_Z = s
            -> (s == null) ? "groupAdmin.location.z cannot be null" : null;
    public static final Function<String, String> LOCATION_NAME = s
            -> null;
}
