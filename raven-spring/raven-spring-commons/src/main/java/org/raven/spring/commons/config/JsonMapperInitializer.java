package org.raven.spring.commons.config;

import org.raven.serializer.withJackson.JsonMapperProvider;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;
import tools.jackson.databind.json.JsonMapper;

@Component
public class JsonMapperInitializer implements SmartInitializingSingleton {
    private final JsonMapper jsonMapper;

    public JsonMapperInitializer(JsonMapper jsonMapper) {
        this.jsonMapper = jsonMapper;
    }

    @Override
    public void afterSingletonsInstantiated() {
        JsonMapperProvider.setJsonMapper(jsonMapper);
    }

}
