package org.raven.logger;

import net.logstash.logback.decorate.MapperBuilderDecorator;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.cfg.MapperBuilder;

/**
 * @author yi.liang
 * date 2019.12.02 14:18
 */
public class LogJsonMapperBuilderDecorator<M extends ObjectMapper, B extends MapperBuilder<M, B>>
        implements MapperBuilderDecorator<M, B> {

    @Override
    public B decorate(B mapperBuilder) {
        return mapperBuilder;
    }
}