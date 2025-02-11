package org.raven.spring.api.schema.config;

import org.raven.spring.api.schema.spi.WebHandlerProvide;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Configuration
public class StructureInfoProvideConfiguration {

    @Bean
    @Primary
    @ConditionalOnMissingBean(WebHandlerProvide.class)
    @ConditionalOnClass(RequestMappingHandlerMapping.class)
    public WebHandlerProvide webMvcWebHandlerProvide(
            @Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping handlerMapping) {

        return new WebMvcWebHandlerProvide(handlerMapping);
    }


}
