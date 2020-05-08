package org.raven.commons.data;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.05.04 23:25
 */
public class GenericUtils {

    private final static Class[] empty = new Class[0];

    private GenericUtils() {
    }

    public static Type[] getInterfacesGenericTypes(Class target, Class genericClass) {
        if (genericClass.isInterface()) {
            for (Type genericInterface : target.getGenericInterfaces()) {
                if (((ParameterizedType) genericInterface).getRawType().equals(genericClass)) {
                    return ((ParameterizedType) genericInterface).getActualTypeArguments();
                }
            }
        } else {
            Type genericSuperclass = target.getGenericSuperclass();
            if (((ParameterizedType) genericSuperclass).getRawType().equals(genericClass)) {
                return ((ParameterizedType) genericSuperclass).getActualTypeArguments();
            }
        }

        return empty;
    }
}
