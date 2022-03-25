package org.raven.serializer.withJackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.raven.commons.data.StringType;
import org.raven.commons.util.StringUtils;

import java.io.IOException;

/**
 * @author yi.liang
 * date 2018/1/9 23:00:00
 * @since JDK1.8
 */
public class StringTypeSerializer extends JsonSerializer<StringType>
        implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    static final StringTypeSerializer INSTANCE = new StringTypeSerializer();

    /**
     *
     */
    @Override
    public void serialize(StringType value, JsonGenerator generator, SerializerProvider provider) throws IOException {

        if (value == null) {
            generator.writeNull();
        } else {
            generator.writeString(value.getValue());
        }

    }

}
