package org.raven.springfox.swagger2.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.raven.springfox.swagger2.JsonSerializerRewrite;
import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.json.JacksonModuleRegistrar;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * @author yanfeng
 */
@Configuration
@ConditionalOnProperty(
        prefix = "raven.swagger2",
        name = {"enabled"},
        havingValue = "true",
        matchIfMissing = true
)
@EnableSwagger2
@EnableConfigurationProperties({SwaggerAutoConfiguration.SwaggerProperties.class})
public class SwaggerAutoConfiguration {

    private static final String PREFIX = "spring.swagger";

    @Bean
    public JsonSerializerRewrite jsonSerializerRewrite(ObjectMapper objectMapper,
                                                       List<JacksonModuleRegistrar> moduleRegistrars,
                                                       JsonSerializer jsonSerializer) {
        return new JsonSerializerRewrite(objectMapper, moduleRegistrars);
    }

    @Bean
    public Docket createRestApi(SwaggerProperties swaggerProperties) {

        Docket docket = (new Docket(DocumentationType.SWAGGER_2))
                .enable(swaggerProperties.enabled)
                .pathMapping(swaggerProperties.path)
                .apiInfo(this.apiInfo(swaggerProperties))
                .select().apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage()))
                .paths(PathSelectors.any())
                .build();


        return docket;
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return (new ApiInfoBuilder())
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl())
                .contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail()))
                .version(swaggerProperties.getVersion())
                .license(swaggerProperties.getLicense())
                .licenseUrl(swaggerProperties.getLicenseUrl())
                .build();
    }

    @ConfigurationProperties(prefix = PREFIX)
    @Data
    public static class SwaggerProperties {

        private String path = "/";
        private boolean enabled = false;
        private String title;
        private String description;
        private String termsOfServiceUrl;
        private String contactName;
        private String contactUrl;
        private String contactEmail;
        private String license;
        private String licenseUrl;
        private String version;
        private String basePackage = "none";
    }
}
