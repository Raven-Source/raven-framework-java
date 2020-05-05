package org.raven.commons.data;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.04.23 01:00
 */
public class Gender extends NumberType<Integer, Gender> {

    protected Gender(Integer value) {
        super(value);
    }

    public final static Gender x = new Gender(1);
    public final static Gender y = new Gender(2);

    public static Gender valueOf(Integer i) {
        return new Gender(i.intValue());
    }

    public static void main(String[] args) {

        Type type = Gender.class.getGenericSuperclass();

        System.out.println(GenericUtils.getInterfacesGenericTypes(Gender.class, ValueType.class)[0]);


        for (Field declaredField : Gender.class.getDeclaredFields()) {
            System.out.println(declaredField.getName());
            System.out.println(declaredField.getType().getName());
        }


    }

}
