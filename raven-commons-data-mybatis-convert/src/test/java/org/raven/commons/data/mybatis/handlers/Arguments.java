package org.raven.commons.data.mybatis.handlers;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 参数描述表
 */
@Getter
@Setter
@Accessors(chain = true)
public class Arguments {

    private Long id;

    /**
     * 参数名
     */
    private String name;

    /**
     * 参数显示名
     */
    private String title;

    /**
     * 描述
     */
    private String des;

    /**
     * 默认值
     */
    private Object defaultValue;

    /**
     * 示例值
     */
    private Object sampleValue;
}
