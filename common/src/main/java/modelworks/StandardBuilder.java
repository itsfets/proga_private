package modelworks;

import dto.*;


public class StandardBuilder {
    private int id;
    private String name;
    private int coordinates_x;
    private int coordinates_y;
    private long studentsCount;
    private long transferredStudents;
    private double averageMark;
    private FormOfEducation formOfEducation;
    private boolean hasGroupAdmin;
    private String groupAdmin_name;
    private double groupAdmin_height;
    private Color groupAdmin_color;
    private Float groupAdmin_location_x;
    private double groupAdmin_location_y;
    private long groupAdmin_location_z;
    private String groupAdmin_location_name;

    public StandardBuilder id(int v) {
        this.id = v;
        return this;
    }

    public StandardBuilder name(String v) {
        this.name = v;
        return this;
    }

    public StandardBuilder coordinates_x(int v) {
        this.coordinates_x = v;
        return this;
    }

    public StandardBuilder coordinates_y(int v) {
        this.coordinates_y = v;
        return this;
    }

    public StandardBuilder studentsCount(long v) {
        this.studentsCount = v;
        return this;
    }

    public StandardBuilder transferredStudents(long v) {
        this.transferredStudents = v;
        return this;
    }

    public StandardBuilder averageMark(Double v) {
        this.averageMark = v;
        return this;
    }

    public StandardBuilder formOfEducation(FormOfEducation v) {
        this.formOfEducation = v;
        return this;
    }

    public StandardBuilder hasGroupAdmin(boolean v) {
        this.hasGroupAdmin = v;
        return this;
    }

    public StandardBuilder groupAdmin_name(String v) {
        this.groupAdmin_name = v;
        return this;
    }

    public StandardBuilder groupAdmin_height(double v) {
        this.groupAdmin_height = v;
        return this;
    }

    public StandardBuilder groupAdmin_eyeColor(Color v) {
        this.groupAdmin_color = v;
        return this;
    }

    public StandardBuilder groupAdmin_location_x(Float v) {
        this.groupAdmin_location_x = v;
        return this;
    }

    public StandardBuilder groupAdmin_location_y(Double v) {
        this.groupAdmin_location_y = v;
        return this;
    }

    public StandardBuilder groupAdmin_location_z(Long v) {
        this.groupAdmin_location_z = v;
        return this;
    }

    public StandardBuilder groupAdmin_location_name(String v) {
        this.groupAdmin_location_name = v;
        return this;
    }

    public boolean get_hasGroupAdmin() {
        return this.hasGroupAdmin;
    }

    public StudyGroup build(StandardValidator validator, int id) {
        StudyGroup group = buildRaw(id);
        ValidationResult result = validator.validate(group);
        if (!result.isValid()) {
            throw new IllegalArgumentException("StudyGroup wasn't created!\n  - " + String.join("\n  - ", result.errors()));
        }
        return group;
    }

    public StudyGroup buildRaw(int id) {
        Coordinates coordinates = new Coordinates(coordinates_x, coordinates_y);
        if (hasGroupAdmin) {
            return new StudyGroup(id, name, coordinates, studentsCount, transferredStudents, averageMark, formOfEducation, new Person(groupAdmin_name, groupAdmin_height, groupAdmin_color,
                    new Location(groupAdmin_location_x, groupAdmin_location_y, groupAdmin_location_z, groupAdmin_location_name)));
        }
        return new StudyGroup(id, name, coordinates, studentsCount, transferredStudents, averageMark, formOfEducation, null);
    }

    public StudyGroup build(StandardValidator validator) {
        StudyGroup group = buildRaw(id);
        ValidationResult result = validator.validate(group);
        if (!result.isValid()) {
            throw new IllegalArgumentException("StudyGroup wasn't created!\n  - " + String.join("\n  - ", result.errors()));
        }
        return group;
    }
}