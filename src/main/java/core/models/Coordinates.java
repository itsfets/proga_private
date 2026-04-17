package core.models;

import misc.Validatable;

import java.util.Objects;

public class Coordinates implements Validatable {
    private int x; //Значение поля должно быть больше -740
    private int y;

    public Coordinates() {}

    public Coordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean validate() {
        return x > -740;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        if (x > -740) { this.x = x; }
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Coordinates{" + "\"x\":" + x + ", \"y\":" + y + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Coordinates that)) return false;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
