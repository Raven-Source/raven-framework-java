package org.raven.serializer.withJackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.raven.commons.data.SerializableTypeUtils;
import org.raven.commons.data.ValueType;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author yi.liang
 * date 2018/1/9 23:00:00
 * @since JDK1.8
 */
public class ValueTypeSerializer extends JsonSerializer<ValueType>
    implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    static final ValueTypeSerializer INSTANCE = new ValueTypeSerializer();

    /**
     * @param value value
     * @param generator generator
     * @param provider provider
     * @throws IOException IOException
     */
    @Override
    public void serialize(ValueType value, JsonGenerator generator, SerializerProvider provider) throws IOException {

        if (value == null) {
            generator.writeNull();
        } else {

            Class<?> clazz = SerializableTypeUtils.getGenericType(value.getClass());

            if (clazz.equals(Integer.class)) {
                generator.writeNumber(value.getValue().intValue());
            } else if (clazz.equals(Long.class)) {
                generator.writeNumber(value.getValue().longValue());
            } else if (clazz.equals(BigInteger.class)) {
                generator.writeNumber((BigInteger) value.getValue());
            } else if (clazz.equals(Double.class)) {
                generator.writeNumber(value.getValue().doubleValue());
            } else if (clazz.equals(Float.class)) {
                generator.writeNumber(value.getValue().floatValue());
            } else if (clazz.equals(BigDecimal.class)) {
                generator.writeNumber((BigDecimal) value.getValue());
            } else {
                generator.writeNumber(value.getValue().intValue());
            }
        }

    }

}
