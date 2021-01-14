package org.raven.commons.data;

import lombok.extern.slf4j.Slf4j;
import org.raven.commons.data.annotation.Create;
import org.raven.commons.data.annotation.Values;
import org.raven.commons.util.StringUtil;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.9.23
 */
@Slf4j
@SuppressWarnings("unchecked")
public class SerializableTypeUtils {
    private SerializableTypeUtils() {
    }

    private static final Map<Class, HashMap<Object, ? extends SerializableType>> serializableTypeCache = new HashMap<>();


    /**
     * @param target enum class
     * @param <T>    enum type
     * @return enum
     */
    private static <T extends SerializableType> Map<Object, T> getValueMap(Class<T> target) {

        if (target == null) {
            throw new IllegalArgumentException("clazz may not be null");
        }

        if (serializableTypeCache.containsKey(target)) {
            return (Map<Object, T>) serializableTypeCache.get(target);
        }

        HashMap<Object, T> map = new HashMap<>();
        synchronized (serializableTypeCache) {
            if (!serializableTypeCache.containsKey(target)) {
                map = new HashMap<>();
                serializableTypeCache.put(target, map);
            }
        }
        try {
            SerializableType[] inter = null;

            if (target.isEnum()) {
                inter = target.getEnumConstants();
            } else {

                Method method = target.getDeclaredMethod("values");
                //is enum-type, or user-defined values Method
                if (method == null) {
                    for (Method declaredMethod : target.getDeclaredMethods()) {
                        if (declaredMethod.getAnnotation(Values.class) != null) {
                            method = declaredMethod;
                            break;
                        }
                    }
                }

                if (method != null && Modifier.isStatic(method.getModifiers())) {
                    inter = (SerializableType[]) method.invoke(null);
                } else {
                    List<SerializableType> list = new ArrayList<>();
                    for (Field declaredField : target.getDeclaredFields()) {
                        if (Modifier.isStatic(declaredField.getModifiers()) && declaredField.getType().equals(target)) {
                            SerializableType valueType = (SerializableType) declaredField.get(declaredField);
                            list.add(valueType);
                        }
                    }
                    if (!list.isEmpty()) {
                        inter = (SerializableType[]) list.toArray();
                    }
                }
            }

            for (int i = 0; i < inter.length; i++) {
                SerializableType serializableType = inter[i];
                map.put(serializableType.getValue(), (T) serializableType);
            }

        } catch (NoSuchMethodException ex) {
            log.info(ex.getMessage());
        } catch (SecurityException ex) {
            log.info(ex.getMessage());
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return map;
    }

    /**
     * @param target enum class
     * @param value  enum value
     * @param <T>    enum type
     * @return SerializableType
     */
    public static <T extends SerializableType> T valueOf(Class<T> target, Object value) {

        T ret = null;

        try {
            Map<Object, T> map = getValueMap(target);
            if (map.containsKey(value)) {
                return (T) map.get(value);
            } else {

                if (target.isEnum()) {
                    return null;
                }

                //Created by static methods
                ret = createByMethod(target, value, map);
                if (ret != null) return ret;

                //Created by a constructor
                ret = createByConstructor(target, value, map);

                return ret;

            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return ret;
    }


    /**
     * @param target target
     * @param stringValue stringValue
     * @param <T> T
     * @return SerializableType
     */
    public static <T extends SerializableType> T stringValueOf(Class<T> target, String stringValue) {

        if (StringUtil.isEmpty(stringValue)) {
            return null;
        }

        if (StringType.class.isAssignableFrom(target)) {
            return valueOf(target, stringValue);
        }
        // ValueType
        else if (ValueType.class.isAssignableFrom(target) && StringUtil.isNumeric(stringValue)) {

            Number value = null;
            try {

                Class genericType = getGenericType(target);
                if (genericType.equals(Integer.class)) {
                    value = Integer.parseInt(stringValue);
                } else if (genericType.equals(Long.class)) {
                    value = Long.parseLong(stringValue);
                } else if (genericType.equals(BigInteger.class)) {
                    value = new BigInteger(stringValue);
                } else if (genericType.equals(Double.class)) {
                    value = Double.parseDouble(stringValue);
                } else if (genericType.equals(Float.class)) {
                    value = Float.parseFloat(stringValue);
                } else if (genericType.equals(BigDecimal.class)) {
                    value = new BigDecimal(stringValue);
                }

                if (value != null) {
                    return valueOf(target, value);
                } else
                    return null;
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        } else if (target.isEnum()) {
            Class<Enum> clazz = (Class) target;
            return (T) nameOf(clazz, stringValue);
        }

        return null;

    }

    /**
     * @param target enum class
     * @param name   enum name
     * @param <T>    enum type
     * @return enum
     */
    public static <T extends Enum<T>> T nameOf(Class<T> target, String name) {

        if (name == null || name.length() == 0) {
            return null;
        }

        try {
            if (target.isEnum()) {
                return Enum.valueOf(target, name);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    private static final Map<Class, Method> methodCache = new HashMap<>();

    private static <T extends SerializableType> T createByMethod(Class<T> target, Object value, Map<Object, T> map)
            throws Exception {
        Method initMethod = null;

        if (methodCache.containsKey(target)) {
            initMethod = methodCache.get(target);
        } else {
            for (Method method : target.getDeclaredMethods()) {
                if (Modifier.isStatic(method.getModifiers())
                        && method.getReturnType().equals(target)
                        && method.getParameterCount() == 1
                        && method.getParameterTypes()[0].isAssignableFrom(value.getClass())) {

                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }

                    if (initMethod == null || method.getAnnotation(Create.class) != null) {
                        initMethod = method;
                    }
//                    break;

                }
            }

            synchronized (methodCache) {
                //Prevent repeated calls, initMethod may be null
                methodCache.put(target, initMethod);
            }
        }

        if (initMethod != null) {
            T ret = (T) initMethod.invoke(null, value);
            if (!map.containsKey(value)) {
                synchronized (map) {
                    map.put(value, ret);
                }
            }

            return ret;
        }

        return null;
    }

    private static final Map<Class, Constructor> constructorCache = new HashMap<>();

    private static <T extends SerializableType> T createByConstructor(Class<T> target, Object value, Map<Object, T> map)
            throws Exception {

        Constructor constructor = null;

        if (constructorCache.containsKey(target)) {
            constructor = constructorCache.get(target);
        } else {
            for (Constructor<?> constr : target.getDeclaredConstructors()) {
                if (constr.getParameterCount() == 1
                        && constr.getParameterTypes()[0].isAssignableFrom(value.getClass())) {
                    if (!constr.isAccessible()) {
                        constr.setAccessible(true);
                    }
                    constructor = constr;
                    break;

                }
            }

            synchronized (constructorCache) {
                //Prevent repeated calls, constructor may be null
                constructorCache.put(target, constructor);
            }
        }

        if (constructor != null) {
            T ret = (T) constructor.newInstance(value);
            if (!map.containsKey(value)) {
                synchronized (map) {
                    map.put(value, ret);
                }
            }

            return ret;
        }

        return null;
    }

    private static final Map<Class<? extends SerializableType>, Class<?>> genericCache = new HashMap<>();

    /**
     * @param typeClass typeClass
     * @return the genericType
     */
    public static Class<?> getGenericType(Class<? extends SerializableType> typeClass) {

        Class<?> clazz = genericCache.get(typeClass);
        if (clazz != null)
            return clazz;

        Type[] types = null;
        if (StringType.class.isAssignableFrom(typeClass)) {
            clazz = String.class;
        } else if (NumberType.class.isAssignableFrom(typeClass)) {
            types = GenericUtils.getInterfacesGenericTypes(typeClass, NumberType.class);
            clazz = (Class<?>) types[0];
        } else if (ValueType.class.isAssignableFrom(typeClass)) {
            types = GenericUtils.getInterfacesGenericTypes(typeClass, ValueType.class);
            clazz = (Class<?>) types[0];
        }
        synchronized (genericCache) {
            if (!genericCache.containsKey(typeClass)) {
                genericCache.put(typeClass, clazz);
            }
        }

        return clazz;

    }
}
