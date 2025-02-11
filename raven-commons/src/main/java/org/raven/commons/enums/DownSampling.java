package org.raven.commons.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.raven.commons.data.ValueType;

@Getter
@RequiredArgsConstructor
public enum DownSampling implements ValueType<Integer> {

    None(0, "无"),
    Second(1, "秒"),
    Minute(2, "分钟"),
    Hour(3, "小时"),
    Day(4, "天"),

    Week(5, "周"),
    Month(6, "月"),
    Quarter(7, "季度"),
    Year(8, "年"),
    ;

    private final Integer value;
    private final String description;

    public static DownSampling of(Integer value) {
        if (value != null) {
            for (DownSampling downSampling : values()) {
                if (downSampling.getValue().equals(value)) {
                    return downSampling;
                }
            }

        }
        return null;
    }
}
