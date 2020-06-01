package org.raven.commons.data;

import lombok.extern.slf4j.Slf4j;
import org.raven.commons.data.annotation.Create;
import org.raven.commons.util.StringUtils;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.9.25
 */
@Slf4j
@SuppressWarnings("unchecked")
public class ValueTypeUtils {

    private ValueTypeUtils() {
    }

    private static final HashMap<Class, HashMap<Number, ValueType>> valueTypeCache = new HashMap<>();

    /**
     * @param target enum class
     * @param <T>    enum type
     * @return enum
     */
    private static <T extends ValueType> HashMap<Number, ValueType> getValueMap(Class<T> target) {

        if (target == null) {
            throw new IllegalArgumentException("clazz may not be null");
        }

        if (valueTypeCache.containsKey(target)) {
            return valueTypeCache.get(target);
        }

        HashMap<Number, ValueType> map = new HashMap<>();
        synchronized (valueTypeCache) {
            if (!valueTypeCache.containsKey(target)) {
                map = new HashMap<>();
                valueTypeCache.put(target, map);
            }
        }
        try {
            Method method = target.getMethod("values");
            //is enum-type, or user-defined values Method
            if (method != null) {

                ValueType[] inter = (ValueType[]) method.invoke(null);
                for (int i = 0; i < inter.length; i++) {
                    ValueType valueType = inter[i];
                    map.put(valueType.getValue(), valueType);
                }
            } else {
                for (Field declaredField : target.getDeclaredFields()) {
                    if (Modifier.isStatic(declaredField.getModifiers()) && declaredField.getType().equals(target)) {
                        ValueType valueType = (ValueType) declaredField.get(declaredField);
                        map.put(valueType.getValue(), valueType);
                    }
                }
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
     * @return enum
     */
    public static <T extends ValueType> T valueOf(Class<T> target, Number value) {

        T ret = null;

        try {
            HashMap<Number, ValueType> map = getValueMap(target);
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

    private static final HashMap<Class, Method> methodCache = new HashMap<>();

    private static <T extends ValueType> T createByMethod(Class<T> target, Number value, HashMap<Number, ValueType> map)
        throws Exception {
        Method initMethod = null;

        if (methodCache.containsKey(target)) {
            initMethod = methodCache.get(target);
        } else {
            for (Method method : target.getDeclaredMethods()) {
                if (Modifier.isStatic(method.getModifiers())
                    && method.getReturnType().equals(target)
                    && method.getParameterCount() == 1
                    && Number.class.isAssignableFrom(method.getParameterTypes()[0])) {

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

    private static final HashMap<Class, Constructor> constructorCache = new HashMap<>();

    private static <T extends ValueType> T createByConstructor(Class<T> target, Number value, HashMap<Number, ValueType> map)
        throws Exception {

        Constructor constructor = null;

        if (constructorCache.containsKey(target)) {
            constructor = constructorCache.get(target);
        } else {
            for (Constructor<?> constr : target.getDeclaredConstructors()) {
                if (constr.getParameterCount() == 1
                    && Number.class.isAssignableFrom(constr.getParameterTypes()[0])) {
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

    /**
     * @param target
     * @param stringValue
     * @param <T>
     * @return ValueType
     */
    public static <T extends ValueType> T valueOf(Class<T> target, String stringValue) {

        Number value = null;
        if (StringUtils.isNumeric(stringValue)) {
            try {

                Class genericType = ValueTypeUtils.getGenericType(target);
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

            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
        }

        if (value != null) {
            return valueOf(target, value);
        } else
            return null;
    }

    /**
     * @param clazz enum class
     * @param name  enum name
     * @param <T>   enum type
     * @return enum
     */
    public static <T extends Enum<T> & ValueType> T nameOf(Class<T> clazz, String name) {

        if (name == null || name.length() == 0) {
            return null;
        }

        try {
            if (clazz.isEnum()) {
                return Enum.valueOf(clazz, name);
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return null;
    }

    private static final HashMap<Class<? extends ValueType>, Class<? extends Number>> genericCache = new HashMap<>();

    /**
     * @param valueTypeClass
     * @return
     */
    public static Class<? extends Number> getGenericType(Class<? extends ValueType> valueTypeClass) {

        Class<? extends Number> clazz = genericCache.get(valueTypeClass);
        if (clazz != null)
            return clazz;
        Type[] types = null;
        if (NumberType.class.isAssignableFrom(valueTypeClass)) {
            types = GenericUtils.getInterfacesGenericTypes(valueTypeClass, NumberType.class);
        } else {
            types = GenericUtils.getInterfacesGenericTypes(valueTypeClass, ValueType.class);
        }
        clazz = (Class<? extends Number>) types[0];
        synchronized (genericCache) {
            if (!genericCache.containsKey(valueTypeClass)) {
                genericCache.put(valueTypeClass, clazz);
            }
        }

        return clazz;

    }


}
