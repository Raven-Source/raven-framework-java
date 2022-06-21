package org.raven.spring.webmvc;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("org.raven.spring.webmvc.*")
@ConditionalOnProperty(
        prefix = "raven.spring.webmvc",
        name = {"enable"},
        havingValue = "true",
        matchIfMissing = true
)
public class SpringBootConfiguration {
}
