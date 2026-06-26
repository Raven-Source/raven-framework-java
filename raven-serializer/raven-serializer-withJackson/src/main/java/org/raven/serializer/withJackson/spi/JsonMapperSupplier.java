package org.raven.serializer.withJackson.spi;

import tools.jackson.databind.json.JsonMapper;

public interface JsonMapperSupplier {

    JsonMapper get();

    String name();
}
