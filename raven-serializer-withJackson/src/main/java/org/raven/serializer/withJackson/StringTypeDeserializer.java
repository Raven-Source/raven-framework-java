package org.raven.serializer.withJackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.raven.commons.data.SerializableTypeUtils;
import org.raven.commons.data.StringType;

import java.io.IOException;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.10.22 18:08
 */
public class StringTypeDeserializer<T extends StringType> extends StdDeserializer<T>
        implements java.io.Serializable {

    private static final long serialVersionUID = 1L;

    public StringTypeDeserializer(final Class<T> target) {
        super(target);
    }

    @Override
    public boolean isCachable() {
        return true;
    }


    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(JsonParser p, DeserializationContext ctxt)
            throws IOException {

        return SerializableTypeUtils.valueOf((Class<T>) _valueClass, p.getValueAsString());

    }

}
