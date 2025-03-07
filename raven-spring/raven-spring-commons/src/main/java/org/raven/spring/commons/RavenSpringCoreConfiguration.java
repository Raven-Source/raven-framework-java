package org.raven.spring.commons;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * date 2022/7/19 11:20
 */
@Configuration
@ComponentScan(basePackages = "org.raven.spring.commons")
@ConditionalOnProperty(
        prefix = "raven.spring",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
public class RavenSpringCoreConfiguration {
}
