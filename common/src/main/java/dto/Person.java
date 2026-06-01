package dto;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

public class Person implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String name; //Поле не может быть null, Строка не может быть пустой
    private double height; //Значение поля должно быть больше 0
    private Color color; //Поле может быть null
    private Location location; //Поле не может быть null

    public Person(String name, double height, Color color, Location location) {
        this.name = name;
        this.height = height;
        this.color = color;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public Color getEyeColor() {
        return color;
    }

    public void setEyeColor(Color color) {
        this.color = color;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Person{\"name\": \"" + name + "\", "
                + "\"height\": " + height + ", "
                + "\"color\": \"" + color + "\", "
                + "\"location\": " + location.toString() + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Double.compare(height, person.height) == 0 && Objects.equals(name, person.name) && color == person.color && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, color, location);
    }
}
