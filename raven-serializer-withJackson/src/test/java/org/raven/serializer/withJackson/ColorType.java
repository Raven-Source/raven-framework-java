package org.raven.serializer.withJackson;

import org.raven.commons.data.ValueType;

//@JsonFormat(shape = JsonFormat.Shape.NUMBER)
//@JsonDeserialize(using = ValueEnumTypeDeserializer.class)
public enum ColorType implements ValueType<Integer> {

    A(1),
    B(2),
    D(4),
    C(3);

    private int val;

    //@JsonValue
    @Override
    public Integer getValue() {
        return val;
    }

    //@JsonCreator
    public ColorType getItem(int code) {
        for (ColorType item : values()) {
            if (item.getValue() == code) {
                return item;
            }
        }
        return null;
    }

    private ColorType(int val) {
        this.val = val;
    }

}

