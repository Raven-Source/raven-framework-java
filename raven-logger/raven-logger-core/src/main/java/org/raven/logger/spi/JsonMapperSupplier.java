package org.raven.logger.spi;

import tools.jackson.databind.json.JsonMapper;

public interface JsonMapperSupplier {
    JsonMapper get();
}
