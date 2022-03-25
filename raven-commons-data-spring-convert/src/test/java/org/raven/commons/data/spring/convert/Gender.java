package org.raven.commons.data.spring.convert;

import org.raven.commons.data.GenericUtils;
import org.raven.commons.data.NumberType;
import org.raven.commons.data.ValueType;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.04.23 01:00
 */
public class Gender extends NumberType<Integer, Gender> {


    protected Gender(int value) {
        super(value);
    }

    public final static Gender man = new Gender(1);
    public final static Gender woman = new Gender(2);

    public static Gender valueOf(Integer i) {
        return new Gender(i.intValue());
    }

    @Override
    public Gender[] values() {
        return new Gender[]{man, woman};
    }
}
