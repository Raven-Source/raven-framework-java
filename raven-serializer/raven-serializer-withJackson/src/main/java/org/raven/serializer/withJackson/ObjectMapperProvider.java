package org.raven.serializer.withJackson;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperProvider {

    private ObjectMapperProvider() {
    }

    private volatile static ObjectMapper objectMapper;

    public static ObjectMapper getObjectMapper() {

        if (objectMapper == null) {
            objectMapper = initObjectMapper();
        }
        return objectMapper;
    }

    private static synchronized ObjectMapper initObjectMapper() {
        if (objectMapper == null) {
            objectMapper = ObjectMapperFactory.getObjectMapper();
        }
        return objectMapper;
    }

    public static void setObjectMapper(ObjectMapper objectMapper) {
        ObjectMapperProvider.objectMapper = objectMapper;
    }

}
