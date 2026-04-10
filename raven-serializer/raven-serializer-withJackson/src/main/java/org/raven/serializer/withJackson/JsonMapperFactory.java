package org.raven.serializer.withJackson;

import tools.jackson.core.json.JsonFactory;
import tools.jackson.databind.DeserializationFeature;
import tools.jackson.databind.MapperFeature;
import tools.jackson.databind.cfg.MapperBuilder;
import tools.jackson.databind.json.JsonMapper;

import java.text.SimpleDateFormat;

/**
 * ObjectMapper config
 *
 * @author yi.liang
 * date 2018/3/20 14:00:00
 * @since JDK21
 */
public class JsonMapperFactory {

    /**
     * @return ObjectMapper
     */
    public static JsonMapper getJsonMapper() {
        return getJsonMapper(SerializerSetting.getDefault());
    }


    public static JsonMapper getJsonMapper(SerializerSetting setting) {
        return getJsonMapper(setting, null);
    }

    /**
     * get ObjectMapper
     *
     * @param setting     SerializerSetting
     * @param jsonFactory JsonFactory
     * @return ObjectMapper
     */
    public static JsonMapper getJsonMapper(SerializerSetting setting, JsonFactory jsonFactory) {
        return getJsonMapperBuilder(setting, jsonFactory).build();
    }

    public static MapperBuilder<JsonMapper, JsonMapper.Builder> getJsonMapperBuilder(SerializerSetting setting) {
        return getJsonMapperBuilder(setting, null);
    }

    public static MapperBuilder<JsonMapper, JsonMapper.Builder> getJsonMapperBuilder(SerializerSetting setting,
                                                                                     JsonFactory jsonFactory) {

        JsonMapper.Builder builder = (jsonFactory != null) ?
                JsonMapper.builder(jsonFactory) :
                JsonMapper.builder();

        return registerDefaultModules(setting, builder);
    }


    public static MapperBuilder<JsonMapper, JsonMapper.Builder> registerDefaultModules(
            MapperBuilder<JsonMapper, JsonMapper.Builder> builder) {
        return registerDefaultModules(SerializerSetting.getDefault(), builder);
    }

    public static MapperBuilder<JsonMapper, JsonMapper.Builder> registerDefaultModules(
            SerializerSetting setting,
            MapperBuilder<JsonMapper, JsonMapper.Builder> builder) {

        builder.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        builder.disable(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY);

        if (setting.getTimeZone() != null) {
            builder.defaultTimeZone(setting.getTimeZone());
        }

        if (setting.getDateFormatString() != null) {
            builder.defaultDateFormat(new SimpleDateFormat(setting.getDateFormatString()));
        }

        builder.addModule(new MultiFormatDateModule(setting));
        builder.addModule(new SerializableTypeModule(setting));

        builder.annotationIntrospector(new AnnotationIntrospectWarp());
        builder.propertyNamingStrategy(new PropertyNamingStrategyWarp());

        return builder;
    }

//    public static void registerDefaultModules(SerializerSetting setting, ObjectMapper mapper) {
//
//        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
//        if (setting.getTimeZone() != null) {
//            mapper.setTimeZone(setting.getTimeZone());
//        }
//
//        if (setting.getDateFormatString() != null) {
//            mapper.setDateFormat(new SimpleDateFormat(setting.getDateFormatString()));
//        }
//
//        //Override JavaTimeModule
//        mapper.registerModule(new JavaTimeSerializerModule(setting));
//        mapper.registerModules(new JavaTimeModule());
//        mapper.registerModules(new Jdk8Module());
//        mapper.registerModules(new MultiFormatDateModule(setting));
//        mapper.registerModules(new SerializableTypeModule(setting));
//
//
//        mapper.setAnnotationIntrospector(new AnnotationIntrospectWarp());
//        mapper.setPropertyNamingStrategy(new PropertyNamingStrategyWarp());
//
//    }

}
