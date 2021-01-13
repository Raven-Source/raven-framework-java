package org.raven.spring.config;

import lombok.Data;
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
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.05.08 11:11
 */
@Configuration
@EnableSwagger2
@EnableConfigurationProperties({SwaggerAutoConfiguration.SwaggerProperties.class})
public class SwaggerAutoConfiguration {

    private static final String PREFIX = "spring.swagger";

    @Bean
    public Docket createRestApi(SwaggerProperties swaggerProperties) {

        Docket docket = (new Docket(DocumentationType.SWAGGER_2)).apiInfo(this.apiInfo(swaggerProperties)).select().apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasePackage())).paths(PathSelectors.any()).build();
        docket.enable(swaggerProperties.enable);

        return docket;
    }

    private ApiInfo apiInfo(SwaggerProperties swaggerProperties) {
        return (new ApiInfoBuilder()).title(swaggerProperties.getTitle()).description(swaggerProperties.getDescription()).termsOfServiceUrl(swaggerProperties.getTermsOfServiceUrl()).contact(new Contact(swaggerProperties.getContactName(), swaggerProperties.getContactUrl(), swaggerProperties.getContactEmail())).version(swaggerProperties.getVersion()).license(swaggerProperties.getLicense()).licenseUrl(swaggerProperties.getLicenseUrl()).build();
    }

    @ConfigurationProperties(prefix = PREFIX)
    @Data
    public static class SwaggerProperties {

        private boolean enable;
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
