package org.raven.commons.contract;

import lombok.NonNull;

import java.util.ArrayList;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.05.15 01:55
 */
public class Extension extends ArrayList<KeyValue<String, String>> {

    public boolean add(@NonNull String key, String value) {
        return super.add(new KeyValue(key, value));
    }
}
