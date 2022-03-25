package org.raven.commons.data;

import lombok.val;
import org.raven.commons.data.annotation.Create;

import java.lang.reflect.Field;
import java.lang.reflect.Type;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.04.23 01:00
 */
public class Gender extends NumberType<Integer, Gender> {

    private Gender(Integer value) {
        super(value);
    }

    public final static Gender x = new Gender(10);

    public final static Gender y = new Gender(20);

//
//    public static Gender valueOf1(Integer i) {
//        return new Gender(i.intValue());
//    }

    @Create
    public static Gender valueOf2(Integer i) {
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

    @Override
    public Gender[] values() {
        return new Gender[]{x, y};
    }
}
