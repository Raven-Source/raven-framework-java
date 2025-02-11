package org.raven.spring.commons.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.raven.commons.constant.DateFormatString;
import org.raven.serializer.withJackson.ObjectMapperConfig;
import org.raven.serializer.withJackson.SerializerSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.codec.CodecsAutoConfiguration;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * @author yanfeng
 */
@Configuration
@AutoConfigureBefore(CodecsAutoConfiguration.class)
@ConditionalOnProperty(name = "lydian.spring.jackson.enable", havingValue = "true",
        matchIfMissing = true)
@EnableConfigurationProperties(JacksonProperties.class)
public class JacksonConfiguration {

    private final JacksonProperties jacksonProperties;

    public JacksonConfiguration(@Autowired JacksonProperties jacksonProperties) {
        this.jacksonProperties = jacksonProperties;
    }

    @Order(Ordered.HIGHEST_PRECEDENCE)
    @Primary
    @Bean("jsonObjectMapper")
    public ObjectMapper objectMapper(SerializerSetting setting) {
        //ApiListingReferenceScanner

        ObjectMapper mapper = ObjectMapperConfig.getObjectMapper(setting);

        if (jacksonProperties.getDateFormat() != null) {
            mapper.setDateFormat(new SimpleDateFormat(jacksonProperties.getDateFormat()));
        }
        if (jacksonProperties.getTimeZone() != null) {
            mapper.setTimeZone(jacksonProperties.getTimeZone());
        }
        return mapper;
    }

    @Bean
    @ConditionalOnMissingBean
    public SerializerSetting serializerSetting() {

        SerializerSetting setting = new SerializerSetting();
        setting.setDateFormatString(DateFormatString.ISO_OFFSET_DATE_TIME);
        setting.setDeserializeDateFormatString(DateFormatString.DESERIALIZE_DATE_FORMAT_STRING);
        setting.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        return setting;
    }
}