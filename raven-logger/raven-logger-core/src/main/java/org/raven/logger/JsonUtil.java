package org.raven.logger;

import lombok.extern.slf4j.Slf4j;
import org.raven.commons.util.StringUtils;
import org.raven.logger.spi.JsonMapperSupplier;
import org.raven.serializer.withJackson.JsonMapperProvider;
import tools.jackson.databind.json.JsonMapper;

import java.util.ServiceLoader;

@Slf4j
public final class JsonUtil {

    static JsonMapperSupplier jsonMapperSupplier;

    private JsonUtil() {
    }

    static JsonMapper mapper;

    static {
        mapper = JsonMapperProvider.getJsonMapper();

        ServiceLoader.load(JsonMapperSupplier.class).forEach(supplier -> {
            jsonMapperSupplier = supplier;
        });
    }

    public static void setMapper(JsonMapper mapper) {
        JsonUtil.mapper = mapper;
    }

    public static JsonMapper getMapper() {
        if (jsonMapperSupplier != null) {
            return jsonMapperSupplier.get();
        } else {
            return mapper;
        }
    }

    public static String toJson(Object obj) {
        try {
            return getMapper().writeValueAsString(obj);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return StringUtils.EMPTY;
        }
    }

}