package org.raven.spring.web.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.raven.spring.commons.config.JacksonConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.boot.web.codec.CodecCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.codec.CodecConfigurer;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.MimeType;

/**
 * @author by yanfeng
 * date 2021/9/3 16:58
 */
@Configuration
@AutoConfigureBefore({CodecsAutoConfiguration.class, JacksonAutoConfiguration.class})
@AutoConfigureAfter(JacksonConfiguration.class)
public class ConverterConfiguration {

    private static final MimeType[] EMPTY_MIME_TYPES = {};

    @Bean
    @Order(0)
    @ConditionalOnClass({ObjectMapper.class})
    @ConditionalOnProperty(name = "raven.spring.web.converter.enable", havingValue = "true",
            matchIfMissing = true)
    public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        // ApiListingReferenceScanner
        return new MappingJackson2HttpMessageConverter(objectMapper);
    }


    @Bean
    @Order(0)
    @ConditionalOnBean(ObjectMapper.class)
    CodecCustomizer jacksonCodecCustomizer(ObjectMapper objectMapper) {
        return (configurer) -> {
            CodecConfigurer.DefaultCodecs defaults = configurer.defaultCodecs();
            defaults.jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper, EMPTY_MIME_TYPES));
            defaults.jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper, EMPTY_MIME_TYPES));
        };
    }
}
