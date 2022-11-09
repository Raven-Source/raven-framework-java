package org.raven.commons.function;

import lombok.Getter;
import lombok.NonNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * date 2022/8/29 20:37
 */
@Getter
public class Tuple3<T1, T2, T3> implements Iterable<Object> {
    private final T1 t1;
    private final T2 t2;
    private final T3 t3;

    public Tuple3(@NonNull T1 t1, @NonNull T2 t2, @NonNull T3 t3) {
        this.t1 = t1;
        this.t2 = t2;
        this.t3 = t3;
    }

    public T1 getT1() {
        return this.t1;
    }

    public T2 getT2() {
        return this.t2;
    }

    public T3 getT3() {
        return this.t3;
    }

    public Object get(int index) {
        switch (index) {
            case 0:
                return this.t1;
            case 1:
                return this.t2;
            case 2:
                return this.t3;
            default:
                return null;
        }
    }

    public List<Object> toList() {
        return Arrays.asList(this.toArray());
    }

    public Object[] toArray() {
        return new Object[]{this.t1, this.t2, this.t3};
    }

    public Iterator<Object> iterator() {
        return Collections.unmodifiableList(this.toList()).iterator();
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else if (o != null && this.getClass() == o.getClass()) {
            Tuple3<?, ?, ?> tuple3 = (Tuple3) o;
            return this.t1.equals(tuple3.t1) && this.t2.equals(tuple3.t2) && this.t3.equals(tuple3.t3);
        } else {
            return false;
        }
    }

    public int hashCode() {
        int result = this.size();
        result = 31 * result + this.t1.hashCode();
        result = 31 * result + this.t2.hashCode();
        result = 31 * result + this.t3.hashCode();
        return result;
    }

    public int size() {
        return 3;
    }

    public final String toString() {
        return Tuples.tupleStringRepresentation(this.toArray()).insert(0, '[').append(']').toString();
    }
}
