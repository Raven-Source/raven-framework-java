package org.raven.serializer.withMsgpack;

import org.raven.commons.data.StringType;

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
