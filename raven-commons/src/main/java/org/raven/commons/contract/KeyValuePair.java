package org.raven.commons.contract;

import lombok.*;

/**
 * @author yi.liang
 * @since JDK1.8
 * created by 2018/1/3 14:00:00
 */
@ToString
@EqualsAndHashCode
@Getter
@Setter
public class KeyValuePair<TKey, TValue> {

    private TKey key;

    private TValue value;

    /**
     * @param key key
     * @param value valueMemberFormatUtils
     */
    public KeyValuePair(@NonNull TKey key, TValue value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @param key key
     * @param value value
     * @param <TKey> TKey
     * @param <TValue> TValue
     * @return the KeyValue
     */
    public static <TKey, TValue> KeyValuePair<TKey, TValue> of(@NonNull TKey key, TValue value) {
        return new KeyValuePair<>(key, value);
    }
}
