package org.raven.serializer.withJackson;

import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.DeserializationConfig;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.deser.Deserializers;
import com.fasterxml.jackson.databind.jsontype.TypeDeserializer;
import com.fasterxml.jackson.databind.type.ReferenceType;
import org.raven.commons.data.StringType;

import java.io.Serializable;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.06.29 01:35
 */
public class StringTypeDeserializers extends Deserializers.Base implements Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    @SuppressWarnings("unchecked")
    public JsonDeserializer<?> findEnumDeserializer(Class<?> refType, DeserializationConfig config, BeanDescription beanDesc) throws JsonMappingException {
        if (StringType.class.isAssignableFrom(refType)) {
            return new StringTypeDeserializer(refType);
        } else {
            return super.findEnumDeserializer(refType, config, beanDesc);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public JsonDeserializer<?> findReferenceDeserializer(ReferenceType refType, DeserializationConfig config, BeanDescription beanDesc, TypeDeserializer contentTypeDeserializer, JsonDeserializer<?> contentDeserializer) throws JsonMappingException {
        if (StringType.class.isAssignableFrom(refType.getRawClass())) {
            return new StringTypeDeserializer(refType.getRawClass());
        } else {
            return super.findReferenceDeserializer(refType, config, beanDesc, contentTypeDeserializer, contentDeserializer);
        }
    }
}
