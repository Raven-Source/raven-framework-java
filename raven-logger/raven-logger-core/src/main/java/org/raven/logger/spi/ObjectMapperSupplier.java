package org.raven.logger.spi;

import tools.jackson.databind.ObjectMapper;

public interface ObjectMapperSupplier {

    ObjectMapper get();

}
