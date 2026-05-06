package core.models.standard;

public abstract class Element {
    abstract public int getId();
    abstract public String getName();
    abstract public Coordinates getCoordinates();
    abstract public java.time.LocalDateTime getCreationDate();
    abstract public long getStudentsCount();
    abstract public long getTransferredStudents();
    abstract public double getAverageMark();
    abstract public FormOfEducation getFormOfEducation();
    abstract public Person getGroupAdmin();
}
