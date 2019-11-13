package org.raven.commons.data;


import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.9.25
 */
public class ValueEnumHelper {

    private static final HashMap<String, HashMap<Integer, ValueEnum>> cache = new HashMap<>();

    /**
     * @param clazz enum class
     * @param <T>   enum type
     * @return enum
     */
    public static <T extends ValueEnum> HashMap<Integer, ValueEnum> getValueMap(Class<T> clazz) {

        if (clazz == null) {
            throw new IllegalArgumentException("clazz may not be null");
        }

        String key = clazz.getName();
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        try {
            Method method = clazz.getMethod("values");
            ValueEnum[] inter = (ValueEnum[]) method.invoke(null);
            HashMap<Integer, ValueEnum> map = new HashMap<>(inter.length);
            for (int i = 0; i < inter.length; i++) {
                ValueEnum valueEnumType = inter[i];
                map.put(valueEnumType.getValue(), valueEnumType);
            }

            synchronized (cache) {
                if (!cache.containsKey(key)) {
                    cache.put(key, map);
                }
            }
            return map;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }

    /**
     * @param clazz enum class
     * @param value enum value
     * @param <T>   enum type
     * @return enum
     */
    public static <T extends ValueEnum> T valueOf(Class<T> clazz, int value) {
        try {
            HashMap<Integer, ValueEnum> map = getValueMap(clazz);
            if (map != null && map.containsKey(value)) {
                return (T) map.get(value);
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

    /**
     * @param clazz enum class
     * @param name  enum name
     * @param <T>   enum type
     * @return enum
     */
    public static <T extends Enum<T> & ValueEnum> T nameOf(Class<T> clazz, String name) {

        if (name == null || name.length() == 0) {
            return null;
        }

        try {
            return Enum.valueOf(clazz, name);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return null;
    }

}
