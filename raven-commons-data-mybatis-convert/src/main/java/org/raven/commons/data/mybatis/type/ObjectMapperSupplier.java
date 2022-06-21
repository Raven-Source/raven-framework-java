package org.raven.commons.data.mybatis.type;

import com.fasterxml.jackson.databind.ObjectMapper;

public interface ObjectMapperSupplier {
    ObjectMapper get();
}
