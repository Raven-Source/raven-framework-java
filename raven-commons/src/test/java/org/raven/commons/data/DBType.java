package org.raven.commons.data;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.04.30 22:57
 */
public enum DBType implements ValueType<Integer>{

    Mysql(1),
    Mongodb(2);

    private int value;

    DBType(int i) {
        value = i;
    }

    @Override
    public Integer getValue() {
        return value;
    }

    public static void main(String[] args) throws Exception {
        Constructor[] constructors = DBType.class.getDeclaredConstructors();

        for (Constructor constructor : constructors) {
            System.out.println(constructor.getName());
        }

        constructors[0].newInstance(new Object[]{1});

    }
}
