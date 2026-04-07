package org.raven.serializer.withJackson;

import org.raven.commons.data.StringType;
import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.DeserializationConfig;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.Deserializers;
import tools.jackson.databind.jsontype.TypeDeserializer;
import tools.jackson.databind.type.ReferenceType;


/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.06.29 01:35
 */
public class StringTypeDeserializers extends Deserializers.Base {

    @Override
    @SuppressWarnings("unchecked")
    public ValueDeserializer<?> findEnumDeserializer(JavaType type, DeserializationConfig config, BeanDescription.Supplier beanDescRef) {

        Class<?> refType = type.getRawClass();
        if (StringType.class.isAssignableFrom(refType)) {
            return new StringTypeDeserializer(refType);
        } else {
            return super.findEnumDeserializer(type, config, beanDescRef);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ValueDeserializer<?> findReferenceDeserializer(ReferenceType refType, DeserializationConfig config,
                                                          BeanDescription.Supplier beanDescRef,
                                                          TypeDeserializer contentTypeDeserializer,
                                                          ValueDeserializer<?> contentDeserializer) {
        if (StringType.class.isAssignableFrom(refType.getRawClass())) {
            return new StringTypeDeserializer(refType.getRawClass());
        } else {
            return super.findReferenceDeserializer(refType, config, beanDescRef, contentTypeDeserializer, contentDeserializer);
        }
    }

    @Override
    public boolean hasDeserializerFor(DeserializationConfig config, Class<?> valueType) {
        return valueType == String.class;
    }
}
