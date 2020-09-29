package org.raven.commons.data;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.07.02 00:20
 */
public interface SerializableType<V> {
    V getValue();

    default boolean equalsValue(Object obj) {

        if (obj == null) {
            return false;
        }

        if (obj instanceof SerializableType) {

            if (getValue() == null && ((SerializableType) obj).getValue() == null) {
                return true;
            }

            return getValue().equals(((SerializableType) obj).getValue());
        }

        return false;
    }
}
