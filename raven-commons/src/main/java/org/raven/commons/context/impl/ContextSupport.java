package org.raven.commons.context.impl;

import lombok.Getter;
import org.raven.commons.context.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * @author by yanfeng
 * date 2021/10/3 21:22
 */
public class ContextSupport implements Context {

    @Getter
    private final Map<String, Object> attributes;

    public ContextSupport() {
        attributes = new HashMap<>();
    }

    @Override
    public Object getAttribute(String name) {
        return attributes.get(name);
    }

    @Override
    public void setAttribute(String name, Object object) {
        attributes.put(name, object);
    }
}
