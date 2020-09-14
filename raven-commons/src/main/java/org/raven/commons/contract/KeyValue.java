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
public class KeyValue<TKey, TValue> {

    private TKey key;

    private TValue value;

    /**
     * @param key
     * @param value
     */
    public KeyValue(@NonNull TKey key, TValue value) {
        this.key = key;
        this.value = value;
    }

    /**
     * @param key
     * @param value
     * @param <TKey>
     * @param <TValue>
     * @return
     */
    public static <TKey, TValue> KeyValue<TKey, TValue> of(@NonNull TKey key, TValue value) {
        return new KeyValue<>(key, value);
    }
}
