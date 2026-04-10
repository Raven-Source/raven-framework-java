package org.raven.spring.commons.config;

import org.raven.commons.context.ContextHolder;
import org.raven.commons.context.impl.ContextHolderSupport;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;

/**
 * @author yanfeng
 */
@AutoConfiguration
@ConditionalOnProperty(name = "raven.context.enabled", havingValue = "true",
        matchIfMissing = true)
public class ContextConfiguration {

    @ConditionalOnMissingBean(ContextHolder.class)
    @Bean
    public ContextHolder contextHolder() {
        return new ContextHolderSupport();
    }
}
