package org.raven.example.springdoc.enums;

import lombok.Getter;
import org.raven.commons.data.StringType;
import org.raven.commons.data.annotation.Create;
import org.raven.commons.data.annotation.Values;

public class ChannelType implements StringType {

    @Getter
    private final String value;

    public final static ChannelType weixin = new ChannelType("weixin");

    public final static ChannelType taobao = new ChannelType("taobao");

    private ChannelType(String value) {
        this.value = value;
    }

    @Values
    public static ChannelType[] values() {
        return new ChannelType[]{weixin, taobao};
    }

    @Create
    private static ChannelType valueOf(String value) {
        for (ChannelType item : values()) {
            if (item.equalsValue(value)) {
                return item;
            }
        }
        return new ChannelType(value);
    }
}
