package org.raven.spring.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.raven.serializer.withJackson.ObjectMapperConfig;
import org.raven.serializer.withJackson.SerializerSetting;
import org.raven.spring.constant.DateFormatStringConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jackson.JacksonProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.TimeZone;

/**
 * @author yi.liang
 * @date 2018.12.31 22:44
 * @since JDK1.8
 */
@Configuration
@ConditionalOnProperty(name = "raven.spring.jackson-configuration", havingValue = "true",
        matchIfMissing = true)
@EnableConfigurationProperties(JacksonProperties.class)
public class JacksonConfiguration {

    @Autowired
    private JacksonProperties jacksonProperties;

    @Bean
    public ObjectMapper objectMapper() {
        //ApiListingReferenceScanner

        SerializerSetting setting = new SerializerSetting();
        setting.setDateFormatString(DateFormatStringConstant.DATE_FORMAT_STRING);
        setting.setDeserializeDateFormatString(DateFormatStringConstant.DESERIALIZE_DATE_FORMAT_STRING);
        setting.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        ObjectMapper mapper = ObjectMapperConfig.getObjectMapper(setting);

//        if (jacksonProperties.getDateFormat() != null) {
//            mapper.setDateFormat(new SimpleDateFormat(jacksonProperties.getDateFormat()));
//        }
        if (jacksonProperties.getTimeZone() != null) {
            mapper.setTimeZone(jacksonProperties.getTimeZone());
        }
        return mapper;
    }

}
