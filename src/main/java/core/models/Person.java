package core.models;

import misc.Validatable;

import java.util.Objects;

public class Person implements Validatable {
    private String name; //Поле не может быть null, Строка не может быть пустой
    private double height; //Значение поля должно быть больше 0
    private Color eyeColor; //Поле может быть null
    private Location location; //Поле не может быть null

    public Person(String name, double height, Color eyeColor, Location location) {
        this.name = name;
        this.height = height;
        this.eyeColor = eyeColor;
        this.location = location;
    }

    public boolean validate() {
        if (name == null || name.isEmpty()) return false;
        if (height <= 0) return false;
        if (eyeColor == null) return false;
        return location != null;
    }

    @Override
    public String toString() {
        return "Person{\"name\": \"" + name + "\", "
                + "\"height\": \"" + height + "\", "
                + "\"eyeColor\": \"" + eyeColor + "\", "
                + "\"location\": " + location.toString() + "}";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (!name.isEmpty()) { this.name = name; }
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        if (height > 0) { this.height = height; }
    }

    public Color getEyeColor() {
        return eyeColor;
    }

    public void setEyeColor(Color eyeColor) {
        if (eyeColor != null) { this.eyeColor = eyeColor; }
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        if (location != null) { this.location = location; }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Double.compare(height, person.height) == 0 && Objects.equals(name, person.name) && eyeColor == person.eyeColor && Objects.equals(location, person.location);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, height, eyeColor, location);
    }
}
