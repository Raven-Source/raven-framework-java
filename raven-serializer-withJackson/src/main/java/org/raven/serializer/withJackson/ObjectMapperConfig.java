package org.raven.serializer.withJackson;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.JavaTimeSerializerModule;

import java.text.SimpleDateFormat;

/**
 * ObjectMapper config
 *
 * @author yi.liang
 * date 2018/3/20 14:00:00
 * @since JDK1.8
 */
public class ObjectMapperConfig {

    /**
     * @return ObjectMapper
     */
    public static ObjectMapper getObjectMapper() {
        return getObjectMapper(SerializerSetting.getDefault());
    }


    public static ObjectMapper getObjectMapper(SerializerSetting setting) {
        return getObjectMapper(setting, null);
    }

    /**
     * get ObjectMapper
     *
     * @param setting     SerializerSetting
     * @param jsonFactory JsonFactory
     * @return ObjectMapper
     */
    public static ObjectMapper getObjectMapper(SerializerSetting setting, JsonFactory jsonFactory) {

//        DefaultDeserializationContext deserializationContext =
//                new DefaultDeserializationContext.Impl(BeanDeserializerFactoryWarp.instance(setting));

        ObjectMapper mapper = new ObjectMapper(jsonFactory);
        //mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new ModifySerializer()));

        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        if (setting.getTimeZone() != null) {
            mapper.setTimeZone(setting.getTimeZone());
        }

        if (setting.getDateFormatString() != null) {
            mapper.setDateFormat(new SimpleDateFormat(setting.getDateFormatString()));
        }

        mapper.registerModules(new JavaTimeModule());
        mapper.registerModules(new Jdk8Module());
        mapper.registerModules(new MultiFormatDateModule(setting));
        mapper.registerModules(new SerializableTypeModule(setting));

        //Override JavaTimeModule
        mapper.registerModule(new JavaTimeSerializerModule(setting));


        mapper.setAnnotationIntrospector(new AnnotationIntrospectorWarp());
        mapper.setPropertyNamingStrategy(new PropertyNamingStrategyWarp());

        return mapper;
    }
}
