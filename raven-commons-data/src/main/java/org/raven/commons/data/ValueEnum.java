package org.raven.commons.data;

/**
 * @author yi.liang
 * @date 2018.9.25
 * @since JDK1.8
 */
public interface ValueEnum {

    int getValue();

    default String getDesc() {
        return null;
    }
}
