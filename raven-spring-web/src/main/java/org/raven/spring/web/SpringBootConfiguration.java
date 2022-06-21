package org.raven.spring.web;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.raven.spring.web")
@ConditionalOnProperty(
        prefix = "raven.spring.web",
        name = {"enable"},
        havingValue = "true",
        matchIfMissing = true
)
public class SpringBootConfiguration {
}
