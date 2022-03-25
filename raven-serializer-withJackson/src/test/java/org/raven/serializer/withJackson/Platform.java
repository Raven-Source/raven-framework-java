package org.raven.serializer.withJackson;

import org.raven.commons.data.StringType;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.10.23 11:39
 */
public enum Platform implements StringType {

    WX("wx"),
    Ali("ali");

    private final String value;

    Platform(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }

}
