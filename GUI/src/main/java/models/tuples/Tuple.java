package models.tuples;

import java.util.Objects;

/**
 * This is the most generic creation of Tuples of two elements
 * This project will use Tuples a lot, such as for creating JCOmponents of JLabel and JButton,
 * or JLabel and TextFields, etc. This is not limited just for JComponents, but for everything.
 * @param <T> any type that extends object
 * @param <G> any type that extends objects that can be different from T type
 */

public class Tuple<T, G> {
    private final T _1;
    private final G _2;

    //private constructor: the creation of the object is by using the Factory static method tuple()
    protected Tuple(final T _1, final G _2) {
        this._1 = _1;
        this._2 = _2;
    }

    //this is the factory method
    public static<T,G>
    Tuple<T, G> tuple(final T x, final G y){
        return new Tuple<T, G>(x, y);
    }

    //getters
    public T get_1() {
        return _1;
    }

    public G get_2() {
        return _2;
    }


    //immutable setters (as attributes are final, these methods will return a new Tuple with different value)
    public Tuple withX(T x){
        return new Tuple<T, G>(x, this._2);
    }

    public Tuple withY(G y){
        return new Tuple<T, G>(this._1, y);
    }


    //equals, hashcode and toString
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) o;
        return Objects.equals(_1, tuple._1) &&
                Objects.equals(_2, tuple._2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_1, _2);
    }

    @Override
    public String toString() {
        return "(" +_1 +","+_2+ ')';
    }
}
