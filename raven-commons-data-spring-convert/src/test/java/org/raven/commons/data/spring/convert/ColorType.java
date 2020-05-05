package org.raven.commons.data.spring.convert;


import org.raven.commons.data.ValueType;

public enum ColorType implements ValueType<Integer> {

    A(1),
    B(2),
    D(4),
    C(3);

    private int val;

    ColorType(int val) {
        this.val = val;
    }

    @Override
    public Integer getValue() {
        return val;
    }


}

