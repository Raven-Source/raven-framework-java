package org.raven.serializer.withJackson;

import tools.jackson.databind.BeanDescription;
import tools.jackson.databind.DeserializationConfig;
import tools.jackson.databind.JavaType;
import tools.jackson.databind.ValueDeserializer;
import tools.jackson.databind.deser.Deserializers;
import tools.jackson.databind.jsontype.TypeDeserializer;
import tools.jackson.databind.type.ReferenceType;
import org.raven.commons.data.StringType;
import org.raven.commons.data.ValueType;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.06.29 01:35
 */
public class SerializableTypeDeserializers extends Deserializers.Base {

    @Override
    @SuppressWarnings("unchecked")
    public ValueDeserializer<?> findEnumDeserializer(JavaType type,
                                                     DeserializationConfig config,
                                                     BeanDescription.Supplier beanDescRef) {

        Class<?> refType = type.getRawClass();
        if (StringType.class.isAssignableFrom(refType)) {
            return new StringTypeDeserializer(refType);
        } else if (ValueType.class.isAssignableFrom(refType)) {
            return new ValueTypeDeserializer(refType);
        } else {
            return super.findEnumDeserializer(type, config, beanDescRef);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public ValueDeserializer<?> findReferenceDeserializer(ReferenceType refType,
                                                          DeserializationConfig config,
                                                          BeanDescription.Supplier beanDescRef,
                                                          TypeDeserializer contentTypeDeserializer,
                                                          ValueDeserializer<?> contentDeserializer) {

        if (StringType.class.isAssignableFrom(refType.getRawClass())) {
            return new StringTypeDeserializer(refType.getRawClass());
        } else if (ValueType.class.isAssignableFrom(refType.getRawClass())) {
            return new ValueTypeDeserializer(refType.getRawClass());
        } else {
            return super.findReferenceDeserializer(refType, config, beanDescRef, contentTypeDeserializer, contentDeserializer);
        }
    }

    @Override
    public boolean hasDeserializerFor(DeserializationConfig config, Class<?> valueType) {
        return String.class == valueType
                || Number.class.isAssignableFrom(valueType);
    }
}
