package org.raven.serializer.withMsgpack;

import org.msgpack.template.Template;
import org.msgpack.template.TemplateRegistry;
import org.raven.commons.data.SerializableType;
import org.raven.commons.data.StringType;
import org.raven.commons.data.ValueType;

import java.lang.reflect.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018/2/11 18:00:00
 */
public class CustomTemplateRegistry extends TemplateRegistry {

    private Set<Type> typeCache;

    /**
     *
     */
    public CustomTemplateRegistry() {
        super(null);
        typeCache = new HashSet<>();
    }

    /**
     * @param targetType targetType
     * @return Template
     */
    @Override
    @SuppressWarnings("unchecked")
    public synchronized Template lookup(Type targetType) {

        if (targetType instanceof ParameterizedType
                || targetType instanceof GenericArrayType
                || targetType instanceof WildcardType
                || targetType instanceof TypeVariable
                || typeCache.contains(targetType)
                || !SerializableType.class.isAssignableFrom((Class<?>) targetType)) {
        } else if (SerializableType.class.isAssignableFrom((Class<?>) targetType)) {
            super.register(targetType, new SerializableTypeTemplate((Class<?>) targetType));
            typeCache.add(targetType);
        }

        return super.lookup(targetType);
    }
}
