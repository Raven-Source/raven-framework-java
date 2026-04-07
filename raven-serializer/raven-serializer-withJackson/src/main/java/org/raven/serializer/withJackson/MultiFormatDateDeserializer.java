package org.raven.serializer.withJackson;

import lombok.NonNull;
import org.raven.commons.util.DateTimeUtils;
import org.raven.commons.util.StringUtils;
import tools.jackson.core.JacksonException;
import tools.jackson.core.JsonParser;
import tools.jackson.core.JsonTokenId;
import tools.jackson.databind.DeserializationContext;
import tools.jackson.databind.ValueDeserializer;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Date;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.06.28 16:49
 */
public class MultiFormatDateDeserializer extends ValueDeserializer<Date>
        implements java.io.Serializable {

    //    private String[] deserializeDateFormatString;
    private final DateTimeFormatter[] deserializeDateTimeFormatters;
    private static final Class<Date> _valueClass = Date.class;

    public MultiFormatDateDeserializer(@NonNull String[] deserializeDateFormatString) {
        super();

        this.deserializeDateTimeFormatters = Arrays.stream(deserializeDateFormatString)
                .map(DateTimeFormatter::ofPattern)
                .toArray(DateTimeFormatter[]::new);
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws JacksonException {

        switch (p.currentTokenId()) {
            case JsonTokenId.ID_STRING:
                return _parseDate(p.getString(), ctxt);
            case JsonTokenId.ID_NUMBER_INT: {
                long ts;
                try {
                    ts = p.getLongValue();
                } catch (JacksonException e) {
                    Number v = (Number) ctxt.handleWeirdNumberValue(_valueClass, p.getNumberValue(),
                            "not a valid 64-bit long for creating `java.util.Date`");
                    ts = v.longValue();
                }
                return new java.util.Date(ts);
            }
            case JsonTokenId.ID_NULL:
                return (java.util.Date) getNullValue(ctxt);
//            case JsonTokenId.ID_START_ARRAY:
//                return _parseDateFromArray(p, ctxt);
        }

        return null;
    }


    protected java.util.Date _parseDate(String value, DeserializationContext ctxt) {
        try {
            // Take empty Strings to mean 'empty' Value, usually 'null':
            if (StringUtils.isBlank(value)) {
                return (java.util.Date) getNullValue(ctxt);
            }
            return DateTimeUtils.parse(value.trim(), deserializeDateTimeFormatters);
        } catch (DateTimeParseException | IllegalArgumentException iae) {
            return (java.util.Date) ctxt.handleWeirdStringValue(_valueClass, value,
                    "not a valid representation (error: %s)", iae.getMessage());
        }
    }

}
