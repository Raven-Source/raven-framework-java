package org.raven.spring.api.schema.config;

import org.raven.commons.data.annotation.SchemaIgnore;
import org.raven.commons.util.Assert;
import org.raven.commons.util.Sets;
import org.raven.spring.api.schema.ApiSchemaContext;
import org.raven.spring.api.schema.ApiSchemaProperties;
import org.raven.spring.api.schema.SchemaProducer;
import org.raven.spring.api.schema.SchemaProducerBuilder;
import org.raven.spring.api.schema.spi.ConstraintProvide;
import org.raven.spring.api.schema.spi.JavadocProvide;
import org.raven.spring.api.schema.spi.ValidationConstraintTypeProvide;
import org.raven.spring.api.schema.spi.WebHandlerProvide;
import org.raven.spring.api.schema.controller.ApiSchemaController;
import org.raven.commons.data.SerializableType;
import org.raven.commons.data.annotation.Ignore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Set;


/**
 * date 2022/9/27 16:07
 */
@EnableConfigurationProperties({ApiSchemaProperties.class})
@Configuration
@ComponentScan(basePackages = "org.raven.spring.api.schema")
public class ApiSchemaConfiguration {

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "raven.api.schema.enabled", havingValue = "true")
    public ApiSchemaContext apiStructureContext(@Autowired(required = false) WebHandlerProvide webHandlerInfoProvide,
                                                @Autowired(required = false) JavadocProvide javadocProvide,
                                                @Autowired(required = false) ConstraintProvide constraintProvide,
                                                ApiSchemaProperties apiSchemaProperties) {

        Assert.hasText(apiSchemaProperties.getPackageRoot(), "packageRoot must not be null");

        Set<String> excludeClassSet = Sets.newHashSet(
                ApiSchemaController.class.getName()
                , "org.raven.spring.web.commons.controller.HeartbeatController"
        );

        if (apiSchemaProperties.getExcludeClassSet() != null && !apiSchemaProperties.getExcludeClassSet().isEmpty()) {
            excludeClassSet.addAll(apiSchemaProperties.getExcludeClassSet());
        }

        SchemaProducer schemaProducer
                = new SchemaProducerBuilder()
                .loadPackageRoot(apiSchemaProperties.getPackageRoot())
                .webHandlerInfoProvide(webHandlerInfoProvide)
                .javadocProvide(javadocProvide)
                .constraintProvide(constraintProvide)
                .serviceFindByAnnotationSet(Sets.newHashSet(RestController.class, Controller.class))
                .operationFilterByAnnotationSet(Sets.newHashSet(
                                PostMapping.class, GetMapping.class,
                                DeleteMapping.class, PutMapping.class,
                                RequestMapping.class
                        )
                )
                .operationIgnoreByAnnotationSet(Sets.newHashSet(SchemaIgnore.class))
                .memberIgnoreByAnnotationSet(Sets.newHashSet(Ignore.class, SchemaIgnore.class))
                .paramFilterByAnnotationSet(Sets.newHashSet(RequestBody.class))
                .enumInterface(SerializableType.class)
                .excludeClassSet(excludeClassSet)
                .build();

        return new ApiSchemaContext(schemaProducer);


    }

    @ConditionalOnBean(ApiSchemaContext.class)
    @Bean
    public ApiSchemaController apiStructureController(ApiSchemaContext apiSchemaContext) {
        return new ApiSchemaController(apiSchemaContext);
    }

    @ConditionalOnMissingBean(ConstraintProvide.class)
    @Bean
    public ConstraintProvide constraintProvide() {
        return new ValidationConstraintTypeProvide();
    }

}
