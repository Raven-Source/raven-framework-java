package org.raven.serializer.mybatis.type;

import tools.jackson.databind.ObjectMapper;

public interface ObjectMapperSupplier {
    ObjectMapper get();
}
