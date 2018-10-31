package utils;

import java.util.Objects;

public class Tuple <X, Y>{

    private final X x;
    private final Y y;

    private Tuple(final X x, final Y y) {
        this.x = x;
        this.y = y;
    }

    public static <X, Y> Tuple <X, Y> tuple(X x, Y y){
        return new Tuple<X,Y>(x, y);
    }

    public X getX() {
        return x;
    }

    public Y getY() {
        return y;
    }

    public Tuple<X, Y>withX(final X x){
        return new Tuple<X, Y>(x, y);
    }

    public Tuple<X, Y>withY(final Y y){
        return new Tuple<X, Y>(x, y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(x, tuple.x) &&
                Objects.equals(y, tuple.y);
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" +
                ""+ x +
                ", " + y +
                ')';
    }
}
