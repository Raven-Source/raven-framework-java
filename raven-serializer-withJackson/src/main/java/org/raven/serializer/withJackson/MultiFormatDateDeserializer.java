package org.raven.serializer.withJackson;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import org.apache.commons.lang3.time.DateUtils;
import org.raven.commons.util.StringUtils;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.06.28 16:49
 */
public class MultiFormatDateDeserializer extends JsonDeserializer<Date>
        implements java.io.Serializable {

    private String[] deserializeDateFormatString;
    private static Class _valueClass = Date.class;

    public MultiFormatDateDeserializer(String[] deserializeDateFormatString) {
        super();

        this.deserializeDateFormatString = deserializeDateFormatString;
    }

    @Override
    public Date deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {

        switch (p.currentTokenId()) {
            case JsonTokenId.ID_STRING:
                return _parseDate(p.getText().trim(), ctxt);
            case JsonTokenId.ID_NUMBER_INT: {
                long ts;
                try {
                    ts = p.getLongValue();
                } catch (JsonParseException e) {
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


    protected java.util.Date _parseDate(String value, DeserializationContext ctxt)
            throws IOException {
        try {
            // Take empty Strings to mean 'empty' Value, usually 'null':
            if (StringUtils.isBlank(value)) {
                return (java.util.Date) getNullValue(ctxt);
            }
            return DateUtils.parseDate(value, deserializeDateFormatString);
        } catch (IllegalArgumentException iae) {
            return (java.util.Date) ctxt.handleWeirdStringValue(_valueClass, value,
                    "not a valid representation (error: %s)", iae.getMessage());
        }catch (ParseException iae) {
            return (java.util.Date) ctxt.handleWeirdStringValue(_valueClass, value,
                    "not a valid representation (error: %s)", iae.getMessage());
        }
    }

}
