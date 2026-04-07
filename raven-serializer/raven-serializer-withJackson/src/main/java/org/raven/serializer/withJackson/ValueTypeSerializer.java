package org.raven.serializer.withJackson;

import org.raven.commons.data.SerializableTypeUtils;
import org.raven.commons.data.ValueType;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonGenerator;
import tools.jackson.databind.SerializationContext;
import tools.jackson.databind.ValueSerializer;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author yi.liang
 * date 2018/1/9 23:00:00
 * @since JDK1.8
 */
public class ValueTypeSerializer extends ValueSerializer<ValueType> {

    static final ValueTypeSerializer INSTANCE = new ValueTypeSerializer();

    /**
     * @param value value
     * @param generator generator
     * @throws JacksonException jacksonException
     */
    @Override
    public void serialize(ValueType value, JsonGenerator generator, SerializationContext ctxt) throws JacksonException {

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
