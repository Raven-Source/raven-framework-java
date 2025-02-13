package org.raven.commons.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public final class Maps {

    private Maps() {
        // do not instantiate
    }

    public static <K, V> HashMap<K, V> newHashMap() {
        return new HashMap<>();
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1) {
        HashMap<K, V> map = newHashMap();
        putAll(map, k1, v1);

        return map;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2) {
        HashMap<K, V> map = newHashMap();
        putAll(map, k1, v1, k2, v2);

        return map;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3) {
        HashMap<K, V> map = newHashMap();
        putAll(map, k1, v1, k2, v2, k3, v3);

        return map;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4) {
        HashMap<K, V> map = newHashMap();
        putAll(map, k1, v1, k2, v2, k3, v3, k4, v4);

        return map;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5) {
        HashMap<K, V> map = newHashMap();
        putAll(map, k1, v1, k2, v2, k3, v3, k4, v4, k5, v5);

        return map;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5,
                                                  K k6, V v6) {
        HashMap<K, V> map = newHashMap();
        putAll(map, k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6);

        return map;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5,
                                                  K k6, V v6, K k7, V v7) {
        HashMap<K, V> map = newHashMap();
        putAll(map, k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7);

        return map;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5,
                                                  K k6, V v6, K k7, V v7, K k8, V v8) {
        HashMap<K, V> map = newHashMap();
        putAll(map, k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8);

        return map;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5,
                                                  K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9) {
        HashMap<K, V> map = newHashMap();
        putAll(map, k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9);

        return map;
    }

    public static <K, V> HashMap<K, V> newHashMap(K k1, V v1, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5,
                                                  K k6, V v6, K k7, V v7, K k8, V v8, K k9, V v9, K k10, V v10) {
        HashMap<K, V> map = newHashMap();
        putAll(map, k1, v1, k2, v2, k3, v3, k4, v4, k5, v5, k6, v6, k7, v7, k8, v8, k9, v9, k10, v10);

        return map;
    }

    public static <K, V> LinkedHashMap<K, V> newLinkedHashMap() {
        return new LinkedHashMap<>();
    }

    public static <K, V> void putAll(Map<K, V> map, Object... input) {

        Objects.requireNonNull(map);

        if ((input.length & 1) != 0) { // implicit nullcheck of input
            throw new InternalError("length is odd");
        }

        for (int i = 0; i < input.length; i += 2) {
            @SuppressWarnings("unchecked")
            K k = Objects.requireNonNull((K) input[i]);
            @SuppressWarnings("unchecked")
            V v = Objects.requireNonNull((V) input[i + 1]);

            map.put(k, v);
        }
    }

}
