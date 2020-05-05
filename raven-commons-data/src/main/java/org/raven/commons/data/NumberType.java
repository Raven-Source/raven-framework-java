package org.raven.commons.data;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.04.26 22:33
 */
public abstract class NumberType<V extends Number, T extends NumberType> implements ValueType<V> {

    private final transient Class<T> target;
    private V value;

    protected NumberType(V value) {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        target = (Class<T>) params[1];

        this.value = value;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            return value.equals(obj);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
