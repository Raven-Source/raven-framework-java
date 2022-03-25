package org.raven.serializer.withJackson;

import org.raven.commons.data.ValueType;

//@JsonFormat(shape = JsonFormat.Shape.NUMBER)
//@JsonDeserialize(using = ValueEnumTypeDeserializer.class)
//@JsonSerialize(using = ValueEnumTypeSerializer.class)
public enum ColorType2 implements ValueType<Integer> {
    A(1),
    D(4),
    C(3),
    B(2);

    private int val;

    private ColorType2(int val) {
        this.val = val;
    }

    //@JsonValue
    public Integer getValue() {
        return val;
    }

    /*@JsonCreator
    public static ColorType2 create(int value) {
        for (ColorType2 type : ColorType2.values()) {
            if (type.getValue() == value) {
                return type;
            }
        }
        return null;
    }*/
}
