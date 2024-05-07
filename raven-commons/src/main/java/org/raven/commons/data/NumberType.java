package org.raven.commons.data;


import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yi.liang
 * date 2020.04.26 22:33
 */
@SuppressWarnings("unchecked")
public abstract class NumberType<V extends Number> implements ValueType<V> {

    private final V value;

    protected NumberType(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public static <T> T[] values() {
        throw new RuntimeException("method must implement");
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (this.getClass().equals(obj.getClass())) {
            return equalsValue(obj);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
