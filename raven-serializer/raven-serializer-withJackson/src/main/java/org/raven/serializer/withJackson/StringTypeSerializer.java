package org.raven.serializer.withJackson;

import org.raven.commons.data.StringType;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.io.IOException;

/**
 * @author yi.liang
 * date 2018/1/9 23:00:00
 * @since JDK1.8
 */
public class StringTypeSerializer extends ValueSerializer<StringType> {

    static final StringTypeSerializer INSTANCE = new StringTypeSerializer();

    /**
     *
     */
    @Override
    public void serialize(StringType value, JsonGenerator generator, SerializationContext ctxt) throws JacksonException {

        if (value == null) {
            generator.writeNull();
        } else {
            generator.writeString(value.getValue());
        }

    }

}
