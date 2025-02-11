package org.raven.commons.extensions;

import lombok.extern.slf4j.Slf4j;
import org.raven.commons.data.*;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author yanfeng
 * @since JDK11
 * date 2021.07.21 16:01
 */
@Slf4j
public abstract class SerializableTypeExtension {

    public static <T extends ValueType<K> & Describable, K extends Number> Map<K, String> convertValueTypeToDict(Class<T> target) {

        Map<K, String> dict = new LinkedHashMap<>();

        try {
            T[] values = SerializableTypeUtils.enumerationValues(target);
            for (T value : values) {
                dict.put(value.getValue(), value.getDescription());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return dict;
    }

    public static <T extends StringType & Describable, K extends Number> Map<String, String> convertStringTypeToDict(Class<T> target) {

        Map<String, String> dict = new LinkedHashMap<>();

        try {
            T[] values = SerializableTypeUtils.enumerationValues(target);
            for (T value : values) {
                dict.put(value.getValue(), value.getDescription());
            }
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return dict;
    }
}
