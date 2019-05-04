package org.raven.commons.data.spring.convert;


import org.raven.commons.data.ValueEnum;

public enum ColorType implements ValueEnum {

    A(1),
    B(2),
    D(4),
    C(3);

    private int val;

    ColorType(int val) {
        this.val = val;
    }

    @Override
    public int getValue() {
        return val;
    }


}

