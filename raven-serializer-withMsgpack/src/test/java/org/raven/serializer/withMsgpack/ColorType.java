package org.raven.serializer.withMsgpack;

import org.raven.commons.data.ValueType;

public enum ColorType implements ValueType<Integer> {

    A(1),
    B(2),
    D(4),
    C(3);

    private int value;

    @Override
    public Integer getValue() {
        return value;
    }

    ColorType(int val) {
        this.value = val;
    }

}
