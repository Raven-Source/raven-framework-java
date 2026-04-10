package org.raven.serializer.withJackson;


import tools.jackson.databind.json.JsonMapper;

public class JsonMapperProvider {

    private JsonMapperProvider() {
    }

    private volatile static JsonMapper jsonMapper;

    public static JsonMapper getJsonMapper() {

        if (jsonMapper == null) {
            jsonMapper = initObjectMapper();
        }
        return jsonMapper;
    }

    private static synchronized JsonMapper initObjectMapper() {
        if (jsonMapper == null) {
            jsonMapper = JsonMapperFactory.getJsonMapper();
        }
        return jsonMapper;
    }

    public static void setJsonMapper(JsonMapper jsonMapper) {
        JsonMapperProvider.jsonMapper = jsonMapper;
    }

}
