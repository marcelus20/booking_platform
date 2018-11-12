package models.tuples;

import java.util.Objects;

public class TupleOf3Elements <A, B, C> extends Tuple {

    private C _3;

    private TupleOf3Elements(A _1, B _2, C _3) {
        super(_1, _2);
        this._3 = _3;
    }

    public static <A,B,C> TupleOf3Elements <A,B,C> tupleOf3Elements(A _1, B _2, C _3){
        return new TupleOf3Elements<A,B,C>(_1,_2,_3);
    }

    public C get_3() {
        return _3;
    }

    public TupleOf3Elements<A, B, C> with_3(C _3){
        return new TupleOf3Elements(get_1(), get_2(),_3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TupleOf3Elements<?, ?, ?> that = (TupleOf3Elements<?, ?, ?>) o;
        return Objects.equals(_3, that._3);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), _3);
    }

    @Override
    public String toString() {
        return "(" + get_1()+","+get_2()+","+_3 +')';
    }
}
