package org.raven.spring;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2021.01.13 22:21
 */
@Configuration
@ComponentScan("org.raven.spring")
@ConditionalOnProperty(
    prefix = "raven.spring",
    name = {"enabled"},
    havingValue = "true",
    matchIfMissing = true
)
public class SpringBootConfiguration {
}
