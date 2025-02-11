package org.raven.serializer.mybatis.type;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface ObjectMapperSupplier {
    ObjectMapper get();
}
