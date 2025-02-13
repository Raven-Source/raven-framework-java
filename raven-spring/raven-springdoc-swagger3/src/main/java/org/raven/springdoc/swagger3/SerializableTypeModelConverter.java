package org.raven.springdoc.swagger3;

import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.converter.AnnotatedType;
import io.swagger.v3.core.converter.ModelConverter;
import io.swagger.v3.core.converter.ModelConverterContext;
import io.swagger.v3.core.util.PrimitiveType;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.media.Schema;
import lombok.extern.slf4j.Slf4j;
import org.raven.commons.data.Describable;
import org.raven.commons.data.SerializableType;
import org.raven.commons.data.SerializableTypeUtils;
import org.raven.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * date 2022/9/27 19:02
 */
@SuppressWarnings("unchecked")
@Slf4j
public class SerializableTypeModelConverter implements ModelConverter {

    @Override
    public Schema resolve(AnnotatedType type, ModelConverterContext context, Iterator<ModelConverter> chain) {
        if (chain.hasNext()) {
            Schema schema = chain.next().resolve(type, context, chain);

            if (type.getType() instanceof JavaType) {

                Class<?> clazz = ((JavaType) type.getType()).getRawClass();
                if (isSerializableTypeClass(clazz)) {
                    try {

                        Class<? extends SerializableType> serializableTypeClass = (Class<? extends SerializableType>) clazz;
                        Class<?> genericType = SerializableTypeUtils.getGenericType(serializableTypeClass);

                        SerializableType[] values = SerializableTypeUtils.enumerationValues(serializableTypeClass);

                        List<Object> enums = new ArrayList<>();
                        StringBuilder desc = new StringBuilder();
                        for (SerializableType value : values) {
                            enums.add(value.getValue());
                            desc.append(value.getValue());
                            if (value instanceof Describable) {
                                desc.append(" - ");
                                desc.append(((Describable) value).getDescription());
                            }
                            desc.append("; ");
                        }

                        String name = schema.get$ref().replace(Components.COMPONENTS_SCHEMAS_REF, "");

                        Schema enumSchema = PrimitiveType.fromType(genericType)
                                .createProperty();

                        enumSchema.setEnum(enums);
                        if (StringUtils.isBlank(schema.getDescription())) {
                            enumSchema.setDescription(desc.toString());
                        }

                        Schema original = context.getDefinedModels().get(name);
                        if (original != null) {
                            enumSchema.setTitle(original.getTitle());
                            enumSchema.setDescription(original.getDescription());
                        }

                        context.defineModel(name, enumSchema);


                    } catch (Exception ex) {
                        log.error(ex.getMessage(), ex);
                    }
                }

            }

            return schema;
        } else {
            return null;
        }
    }

    private boolean isSerializableTypeClass(Class<?> clazz) {

        return SerializableType.class.isAssignableFrom(clazz);
    }
}
