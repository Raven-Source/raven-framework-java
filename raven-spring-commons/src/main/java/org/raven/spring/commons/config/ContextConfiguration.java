package org.raven.spring.commons.config;

import org.raven.commons.context.ContextHolder;
import org.raven.commons.context.impl.ContextHolderSupport;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2021.11.01 17:35
 */
@Configuration
public class ContextConfiguration {

    @ConditionalOnMissingBean(ContextHolder.class)
    @Bean
    public ContextHolder contextHolder() {
        return new ContextHolderSupport();
    }
}
