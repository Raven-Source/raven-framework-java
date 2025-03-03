package org.raven.spring.commons.util;


import org.raven.commons.context.ContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author by yanfeng
 */
@Component
public class ContextHolderProvider {

    private static ContextHolder contextHolder;

    private ContextHolderProvider() {
    }

    @Autowired
    private void setContextHolder(ContextHolder contextHolder) {
        ContextHolderProvider.contextHolder = contextHolder;
    }

    public static ContextHolder getContextHolder() {
        return ContextHolderProvider.contextHolder;
    }

}
