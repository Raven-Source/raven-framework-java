package org.raven.commons.data;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018.9.25
 */
public interface ValueEnum {

    int getValue();

    default String getDesc() {
        return null;
    }
}
