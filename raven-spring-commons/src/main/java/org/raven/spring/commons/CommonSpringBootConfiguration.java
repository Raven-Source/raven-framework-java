package org.raven.spring.commons;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yanfeng
 */
@Configuration
@ComponentScan("org.raven.spring.commons")
@ConditionalOnProperty(
    prefix = "raven.spring",
    name = {"enable"},
    havingValue = "true",
    matchIfMissing = true
)
public class CommonSpringBootConfiguration {
}
