package org.raven.spring.commons.config;

import org.raven.commons.constant.DateFormatString;
import org.raven.commons.util.StringUtils;
import org.raven.serializer.withJackson.JsonMapperFactory;
import org.raven.serializer.withJackson.SerializerSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.jackson.autoconfigure.JacksonAutoConfiguration;
import org.springframework.boot.jackson.autoconfigure.JacksonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jackson.autoconfigure.JsonMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.util.TimeZone;

/**
 * @author yanfeng
 */
@AutoConfiguration
@AutoConfigureBefore(JacksonAutoConfiguration.class)
@ConditionalOnProperty(name = "raven.jackson.enabled", havingValue = "true",
        matchIfMissing = true)
@EnableConfigurationProperties(JacksonProperties.class)
public class JacksonConfiguration {

    private final JacksonProperties jacksonProperties;

    public JacksonConfiguration(@Autowired JacksonProperties jacksonProperties) {
        this.jacksonProperties = jacksonProperties;
    }

//    @Order(Ordered.HIGHEST_PRECEDENCE)
//    @Primary
//    @Bean("jsonMapper")
//    public JsonMapper jsonMapper(SerializerSetting setting) {
//        //ApiListingReferenceScanner
//
//        JsonMapper mapper = ObjectMapperFactory.getJsonMapperBuilder(setting).build();
//
//        ObjectMapperProvider.setObjectMapper(mapper);
//        return mapper;
//    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Primary
    @Bean
    public JsonMapperBuilderCustomizer jsonMapperBuilderCustomizer(SerializerSetting setting) {
        return builder -> {
            JsonMapperFactory.registerDefaultModules(setting,  builder);
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public SerializerSetting serializerSetting() {

        SerializerSetting setting = new SerializerSetting();
        if (StringUtils.isNotBlank(jacksonProperties.getDateFormat())) {
            setting.setDateFormatString(jacksonProperties.getDateFormat());
        } else {
            setting.setDateFormatString(DateFormatString.ISO_OFFSET_DATE_TIME);
        }

        setting.setDeserializeDateFormatString(DateFormatString.DESERIALIZE_DATE_FORMAT_STRING);
        if (jacksonProperties.getTimeZone() != null) {
            setting.setTimeZone(jacksonProperties.getTimeZone());
        } else {
            setting.setTimeZone(TimeZone.getTimeZone("GMT+8"));
        }

        return setting;
    }
}