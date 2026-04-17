package core.models;

import misc.Validatable;

import java.util.Objects;

public class Location implements Validatable {
    private Float x; //Поле не может быть null
    private Double y; //Поле не может быть null
    private Long z; //Поле не может быть null
    private String name; //Поле может быть null

    public Location() {}

    public Location(Float x, Double y, Long z, String name) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = name;
    }

    public boolean validate() {
        if (x == null) return false;
        if (y == null) return false;
        if (z == null) return false;
        return name != null;
    }

    @Override
    public String toString() {
        return "Location{\"x\": " + x +
                ", \"y\": " + y +
                ", \"z\": " + z +
                ", \"name\": \"" + name + "\"}";
    }

    public Float getX() {
        return x;
    }

    public void setX(Float x) {
        if (x != null) { this.x = x; }
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        if (y != null) { this.y = y; }
    }

    public Long getZ() {
        return z;
    }

    public void setZ(Long z) {
        if (z != null) { this.z = z; }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        if (name != null) { this.name = name; }
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(x, location.x) && Objects.equals(y, location.y) && Objects.equals(z, location.z) && Objects.equals(name, location.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, name);
    }
}
