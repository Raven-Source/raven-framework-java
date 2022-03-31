package org.raven.commons.data;

import org.raven.commons.data.annotation.Ignore;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yi.liang
 * date 2020.04.26 22:33
 */
@SuppressWarnings("unchecked")
public abstract class NumberType<V extends Number, T extends NumberType> implements ValueType<V> {

    private final transient Class<T> target;
    private final V value;

    protected NumberType(V value) {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        target = (Class<T>) params[1];

        this.value = value;
    }

    public V getValue() {
        return value;
    }

    public abstract T[] values();

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null) {
            return false;
        }

        if (target.equals(obj.getClass())) {
            return equalsValue(obj);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
