package org.raven.serializer.withJackson;

import org.raven.commons.data.ValueType;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.04.23 00:58
 */

public abstract class EnumValue<T> extends Number implements ValueType<Integer> {

    private int value;

    public EnumValue(int value) {
        this.value = value;
    }

    @Override
    public int intValue() {
        return value;
    }

    @Override
    public long longValue() {
        return value;
    }

    @Override
    public float floatValue() {
        return value;
    }

    @Override
    public double doubleValue() {
        return value;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof ValueType) {
            return ((ValueType) obj).getValue().equals(value);
        }
        return false;
    }
}
