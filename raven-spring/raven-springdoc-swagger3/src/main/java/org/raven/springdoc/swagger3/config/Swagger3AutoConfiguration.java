package org.raven.springdoc.swagger3.config;

import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.jackson.ModelResolver;
import org.raven.springdoc.swagger3.CustomTypeNameResolver;
import org.raven.springdoc.swagger3.SerializableTypeModelConverter;
import org.raven.serializer.withJackson.ObjectMapperConfig;
import org.raven.serializer.withJackson.SerializerSetting;
import org.springdoc.core.SpringDocConfigProperties;
import org.springdoc.core.providers.ObjectMapperProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.*;

import javax.annotation.PostConstruct;

/**
 * date 2022/8/29 19:31
 */
@Lazy(false)
@Configuration(
        proxyBeanMethods = false
)
@ConditionalOnProperty(
        prefix = "raven.swagger3",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@ConditionalOnWebApplication
public class Swagger3AutoConfiguration {

    @PostConstruct
    public void init() {
        ModelResolver.enumsAsRef = true;
    }

    @Bean
    public ModelConverter modelResolver(ObjectMapperProvider objectMapperProvider) {
        return new ModelResolver(objectMapperProvider.jsonMapper(), new CustomTypeNameResolver());
    }

    @Bean
    @DependsOn("modelResolver")
    public ModelConverter serializableTypeModelConverter(ObjectMapperProvider objectMapperProvider) {
        return new SerializableTypeModelConverter();
    }

    @Primary
    @Bean
    public ObjectMapperProvider objectMapperProvider(SpringDocConfigProperties springDocConfigProperties
            , SerializerSetting setting) {

        SpringDocConfigProperties.ApiDocs.OpenApiVersion openApiVersion = springDocConfigProperties.getApiDocs().getVersion();
//        if (openApiVersion == SpringDocConfigProperties.ApiDocs.OpenApiVersion.OPENAPI_3_1) {
//            this.jsonMapper = Json31.mapper();
//            this.yamlMapper = Yaml31.mapper();
//        } else {
//            this.jsonMapper = Json.mapper();
//            this.yamlMapper = Yaml.mapper();
//        }

        // convert ObjectMapper
        ObjectMapperProvider objectMapperProvider = new ObjectMapperProvider(springDocConfigProperties);
        ObjectMapperConfig.registerDefaultModules(setting, objectMapperProvider.jsonMapper());
        ObjectMapperConfig.registerDefaultModules(setting, objectMapperProvider.yamlMapper());

        return objectMapperProvider;
    }

}
