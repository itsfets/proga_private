package core.models;

import console.StandardConsole;
import misc.Serializable;
import misc.Validatable;

import java.time.LocalDateTime;
import java.util.Objects;

public class StudyGroup extends Element implements Validatable, Serializable, Comparable<StudyGroup>{

    private int id; //Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; //Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; //Поле не может быть null
    private java.time.LocalDateTime creationDate; //Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private long studentsCount; //Значение поля должно быть больше 0
    private long transferredStudents; //Значение поля должно быть больше 0
    private double averageMark; //Значение поля должно быть больше 0
    private FormOfEducation formOfEducation; //Поле не может быть null
    private Person groupAdmin; //Поле может быть null

    public StudyGroup() {}

    public StudyGroup(int id, String name, Coordinates coordinates, long studentsCount, long transferredStudents, double averageMark, FormOfEducation formOfEducation, Person groupAdmin ) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = LocalDateTime.now();
        this.studentsCount = studentsCount;
        this.transferredStudents = transferredStudents;
        this.averageMark = averageMark;
        this.formOfEducation = formOfEducation;
        this.groupAdmin = groupAdmin;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return "StudyGroup{\"id: " + id + ", "
                + "\"name\": \"" + name + "\", "
                + "\"coordinates\": " + coordinates.toString() + ", "
                + "\"creationDate\": \"" + creationDate + "\", "
                + "\"studentsCount\": " + studentsCount + ", "
                + "\"transferredStudents\": " + transferredStudents + ", "
                + "\"averageMark\": " + averageMark + ", "
                + "\"formOfEducation\": " + formOfEducation + ", "
                + "\"groupAdmin\": " + groupAdmin.toString() + "}";
    }

    public int compareTo(StudyGroup studyGroup) {
        return (int) (averageMark - studyGroup.getAverageMark());
    }

    public boolean validate() {
        if (name == null || name.isEmpty()) return false;
        if (!coordinates.validate()) return false;
        if (studentsCount <= 0) return false;
        if (transferredStudents <= 0) return false;
        if (averageMark <= 0) return false;
        if (formOfEducation == null) return false;
        return groupAdmin.validate();
    }

    public Person getGroupAdmin() {
        return groupAdmin;
    }

    public void setGroupAdmin(Person groupAdmin) {
        if (groupAdmin != null) { this.groupAdmin = groupAdmin; }
    }

    public FormOfEducation getFormOfEducation() {
        return formOfEducation;
    }

    public void setFormOfEducation(FormOfEducation formOfEducation) {
        if (formOfEducation != null) { this.formOfEducation = formOfEducation; }
    }

    public double getAverageMark() {
        return averageMark;
    }

    public void setAverageMark(double averageMark) {
        this.averageMark = averageMark;
    }

    public long getTransferredStudents() {
        return transferredStudents;
    }

    public void setTransferredStudents(long transferredStudents) {
        this.transferredStudents = transferredStudents;
    }

    public long getStudentsCount() {
        return studentsCount;
    }

    public void setStudentsCount(long studentsCount) {
        this.studentsCount = studentsCount;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) { this.creationDate = creationDate; }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        if (coordinates != null) { this.coordinates = coordinates; }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) { this.name = name; }
    }

    public void setId(int id) { this.id = id; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        StudyGroup that = (StudyGroup) o;
        return id == that.id && studentsCount == that.studentsCount && transferredStudents == that.transferredStudents && Double.compare(averageMark, that.averageMark) == 0 && Objects.equals(name, that.name) && Objects.equals(coordinates, that.coordinates) && Objects.equals(creationDate, that.creationDate) && formOfEducation == that.formOfEducation && Objects.equals(groupAdmin, that.groupAdmin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, studentsCount, transferredStudents, averageMark, formOfEducation, groupAdmin);
    }
}
