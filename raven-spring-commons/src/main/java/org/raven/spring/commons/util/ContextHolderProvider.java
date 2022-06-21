package org.raven.spring.commons.util;


import org.raven.commons.context.ContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author by yanfeng
 * date 2021/10/28 18:38
 */
@Component
public class ContextHolderProvider {

    private static ContextHolder contextHolder;

    private ContextHolderProvider() {
    }

    @Autowired
    private void setContextHolder(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }

    public static ContextHolder getContextHolder() {
        return contextHolder;
    }

}
