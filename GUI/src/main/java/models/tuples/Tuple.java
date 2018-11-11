package models.tuples;

import java.util.Objects;

/**
 * This is the most generic creation of Tuples of two elements
 * This project will use Tuples a lot, such as for creating JCOmponents of JLabel and JButton,
 * or JLabel and TextFields, etc. This is not limited just for JComponents, but for everything.
 * @param <T>
 * @param <G>
 */

public class Tuple<T, G> {
    private final T x;
    private final G y;

    //private constructor: the creation of the object is by using the Factory static method tuple()
    private Tuple(final T x, final G y) {
        this.x = x;
        this.y = y;
    }

    //this is the factory method
    public static<T,G>
    Tuple<T, G> tuple(final T x, final G y){
        return new Tuple<T, G>(x, y);
    }

    //getters
    public T getX() {
        return x;
    }

    public G getY() {
        return y;
    }


    //immutable setters (as attributes are final, these methods will return a new Tuple with different value)
    public Tuple withX(T x){
        return new Tuple<T, G>(x, this.y);
    }

    public Tuple withY(G y){
        return new Tuple<T, G>(this.x, y);
    }


    //equals, hashcode and toString
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
                "x=" + x +
                ", y=" + y +
                ')';
    }
}
