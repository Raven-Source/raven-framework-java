package org.raven.commons.function;

import lombok.NonNull;

import java.util.*;
import java.util.function.Function;

/**
 * date 2022/8/29 20:37
 */
public class Tuple2<T1, T2> implements Iterable<Object> {
    final T1 t1;
    final T2 t2;

    Tuple2(@NonNull T1 t1, @NonNull T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }

    public T1 getT1() {
        return this.t1;
    }

    public T2 getT2() {
        return this.t2;
    }

    public Object get(int index) {
        switch (index) {
            case 0:
                return this.t1;
            case 1:
                return this.t2;
            default:
                return null;
        }
    }

    public List<Object> toList() {
        return Arrays.asList(this.toArray());
    }

    public Object[] toArray() {
        return new Object[]{this.t1, this.t2};
    }

    public Iterator<Object> iterator() {
        return Collections.unmodifiableList(this.toList()).iterator();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Tuple2<?, ?> tuple2 = (Tuple2) o;
            return this.t1.equals(tuple2.t1) && this.t2.equals(tuple2.t2);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.size();
        result = 31 * result + this.t1.hashCode();
        result = 31 * result + this.t2.hashCode();
        return result;
    }

    public int size() {
        return 2;
    }

    public final String toString() {
        return Tuples.tupleStringRepresentation(this.toArray()).insert(0, '[').append(']').toString();
    }
}
