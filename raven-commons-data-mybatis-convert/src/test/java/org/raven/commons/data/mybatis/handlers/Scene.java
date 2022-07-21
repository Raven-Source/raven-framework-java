package org.raven.commons.data.mybatis.handlers;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 消息场景
 */
@Getter
@Setter
@Accessors(chain = true)
public class Scene {

    private Long id;

    /**
     * 场景编码
     */
    private String code;

    /**
     * 场景参数
     */
    private Map<String, Arguments> args;

}
