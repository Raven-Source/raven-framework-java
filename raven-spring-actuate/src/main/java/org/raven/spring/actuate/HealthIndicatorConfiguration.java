package org.raven.spring.actuate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.boot.actuate.jdbc.DataSourceHealthIndicator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ConditionalOnProperty(
        name = "raven.actuate.enable",
        havingValue = "true",
        matchIfMissing = true
)
public class HealthIndicatorConfiguration {

    @Bean
    @ConditionalOnBean(DataSource.class)
    public HealthIndicator dbHealthIndicator(@Autowired DataSource dataSource) {
        DataSourceHealthIndicator indicator = new DataSourceHealthIndicator(dataSource);
        indicator.setQuery("SELECT 1");
        return indicator;
    }
}
