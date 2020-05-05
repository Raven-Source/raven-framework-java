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

    public static Type[] getInterfacesGenericTypes(Class target, Class interfaceClass) {
        if (interfaceClass.isInterface()) {
            for (Type genericInterface : target.getGenericInterfaces()) {
                if (((ParameterizedType) genericInterface).getRawType().equals(interfaceClass)) {
                    return ((ParameterizedType) genericInterface).getActualTypeArguments();
                }
            }
        }

        return empty;
    }
}
