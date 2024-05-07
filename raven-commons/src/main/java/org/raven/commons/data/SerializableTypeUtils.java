package org.raven.commons.data;

import lombok.extern.slf4j.Slf4j;
import org.raven.commons.data.annotation.Create;
import org.raven.commons.data.annotation.Values;
import org.raven.commons.util.StringUtils;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.9.23
 */
@Slf4j
@SuppressWarnings("unchecked")
public class SerializableTypeUtils {

    private final static Object NULL = new Object();

    private SerializableTypeUtils() {
    }

    private static final Map<Class, ConcurrentMap<Object, ? extends SerializableType>> serializableTypeCache = new HashMap<>();


    /**
     * @param target SerializableType class
     * @param <T>    SerializableType type
     * @return enum
     */
    private static <T extends SerializableType> ConcurrentMap<Object, T> getValueMap(Class<T> target) {
        if (target == null) {
            throw new IllegalArgumentException("clazz may not be null");
        }

        ConcurrentMap<Object, T> map = (ConcurrentMap<Object, T>) serializableTypeCache.get(target);
        if (!Objects.isNull(map)) {
            return map;
        }

        // if SerializableType maybe add enumeration items on runtime, serializableTypeCache items must the only
        // so must use synchronized
        synchronized (serializableTypeCache) {
            map = (ConcurrentMap<Object, T>) serializableTypeCache.get(target);
            if (Objects.isNull(map)) {

                // SerializableType maybe add enumeration items on runtime, so must use ConcurrentHashMap
                map = new ConcurrentHashMap<>();
                try {
                    SerializableType[] inter = null;

                    inter = enumerationValues(target);

                    for (int i = 0; i < inter.length; i++) {
                        SerializableType serializableType = inter[i];
                        map.put(serializableType.getValue(), (T) serializableType);
                    }
                    serializableTypeCache.put(target, map);
                    return map;

                } catch (NoSuchMethodException | SecurityException ex) {
                    log.info(ex.getMessage());
                } catch (Exception ex) {
                    log.error(ex.getMessage(), ex);
                }

            }
            return map;
        }

    }

    public static <T extends SerializableType> T[] enumerationValues(Class<T> target)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {

        T[] inter = null;
        if (target.isEnum()) {
            inter = target.getEnumConstants();
        } else {

            Method method = target.getDeclaredMethod("values");
            //is enum-type, or user-defined values Method
            for (Method declaredMethod : target.getDeclaredMethods()) {
                if (declaredMethod.getAnnotation(Values.class) != null) {
                    method = declaredMethod;
                    break;
                }
            }

            if (Modifier.isStatic(method.getModifiers())) {
                inter = (T[]) method.invoke(null);
            } else {
                List<T> list = new ArrayList<>();
                for (Field declaredField : target.getDeclaredFields()) {
                    if (Modifier.isStatic(declaredField.getModifiers()) && declaredField.getType().equals(target)) {
                        T valueType = (T) declaredField.get(declaredField);
                        list.add(valueType);
                    }
                }
                if (!list.isEmpty()) {
                    inter = (T[]) list.toArray(new SerializableType[0]);
                }
            }
        }
        return inter;
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
            ConcurrentMap<Object, T> map = getValueMap(target);
            ret = map.get(value);

            if (ret != null) {
                return (T) ret;
            } else {

                // enum will not be empty, cannot be created dynamically
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
     * @param target      target
     * @param stringValue stringValue
     * @param <T>         T
     * @return SerializableType
     */
    public static <T extends SerializableType> T stringValueOf(Class<T> target, String stringValue) {

        if (StringUtils.isEmpty(stringValue)) {
            return null;
        }

        if (StringType.class.isAssignableFrom(target)) {
            return valueOf(target, stringValue);
        }
        // ValueType
        else if (ValueType.class.isAssignableFrom(target) && StringUtils.isNumeric(stringValue)) {

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

    private static final Map<Class, Object> initMethodCache = new ConcurrentHashMap<>();

    private static <T extends SerializableType> T createByMethod(Class<T> target, Object value, ConcurrentMap<Object, T> map)
            throws Exception {

        Object methodObject = initMethodCache.get(target);
        if (methodObject == NULL) {
            return null;
        }

        Method initMethod = (Method) methodObject;

        if (initMethod == null) {
            for (Method method : target.getDeclaredMethods()) {
                if (Modifier.isStatic(method.getModifiers())
                        && method.getReturnType().equals(target)
                        && method.getParameterCount() == 1
                        && method.getParameterTypes()[0].isAssignableFrom(value.getClass())) {

                    if (!method.isAccessible()) {
                        method.setAccessible(true);
                    }

                    // @Create is high priority
                    if (initMethod == null || method.getAnnotation(Create.class) != null) {
                        initMethod = method;
                    }

                }
            }

            //Prevent repeated calls, initMethod may be null
            if (initMethod != null) {
                initMethodCache.putIfAbsent(target, initMethod);
            } else {
                initMethodCache.putIfAbsent(target, NULL);
            }
        }

        if (initMethod != null) {
            T ret = (T) initMethod.invoke(null, value);
            map.putIfAbsent(value, ret);

            return ret;
        }

        return null;
    }

    private static final Map<Class, Constructor> constructorCache = new ConcurrentHashMap<>();

    private static <T extends SerializableType> T createByConstructor(Class<T> target, Object value, ConcurrentMap<Object, T> map)
            throws Exception {

        Constructor constructor = constructorCache.get(target);

        if (constructor == null) {
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

            //Prevent repeated calls, constructor may be null
            constructorCache.putIfAbsent(target, constructor);
        }

        if (constructor != null) {
            T ret = (T) constructor.newInstance(value);
//            if (!map.containsKey(value)) {
//                synchronized (map) {
//                    map.put(value, ret);
//                }
//            }
            map.putIfAbsent(value, ret);

            return ret;
        }

        return null;
    }

    private static final Map<Class<? extends SerializableType>, Class<?>> genericCache = new ConcurrentHashMap<>();

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
        genericCache.putIfAbsent(typeClass, clazz);

        return clazz;

    }
}
