package org.raven.serializer.withJackson;


import lombok.extern.slf4j.Slf4j;
import org.raven.commons.data.ValueType;
import org.raven.commons.data.SerializableTypeUtils;
import org.raven.commons.util.StringUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonTokenId;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

/**
 * @author yi.liang
 * date 2018/1/9 23:00:00
 * @since JDK1.8
 */
@Slf4j
public class ValueTypeDeserializer<T extends ValueType> extends StdDeserializer<T> {

    /**
     * @param target target
     */
    public ValueTypeDeserializer(final Class<T> target) {
        super(target);
    }

    /**
     * @return boolean
     */
    @Override
    public boolean isCachable() {
        return true;
    }

    /**
     *
     */
    @Override
    @SuppressWarnings("unchecked")
    public T deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {

        int tokenId = p.currentTokenId();
        if (tokenId == JsonTokenId.ID_NUMBER_INT) {
            return SerializableTypeUtils.valueOf((Class<T>) _valueClass, p.getIntValue());
        } else if (tokenId == JsonTokenId.ID_NUMBER_FLOAT) {
            return SerializableTypeUtils.valueOf((Class<T>) _valueClass, p.getFloatValue());
        } else {
            String source = p.getValueAsString();
            if (StringUtils.isNumeric(source)) {
                return SerializableTypeUtils.stringValueOf((Class<T>) _valueClass, source);
            }

            return (T) SerializableTypeUtils.nameOf((Class) _valueClass, source);
        }

    }

}
