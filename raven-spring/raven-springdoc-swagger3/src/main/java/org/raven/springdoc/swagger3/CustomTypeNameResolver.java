package org.raven.springdoc.swagger3;

import com.fasterxml.jackson.databind.JavaType;
import io.swagger.v3.core.jackson.TypeNameResolver;
import io.swagger.v3.core.util.PrimitiveType;
import org.apache.commons.lang3.text.WordUtils;

import java.util.Set;

public class CustomTypeNameResolver extends TypeNameResolver {

    @Override
    protected String nameForGenericType(JavaType type, Set<Options> options) {

        final StringBuilder generic = new StringBuilder(nameForClass(type, options));
        final int count = type.containedTypeCount();
        if (count > 0) {
            generic.append("<");
        }
        for (int i = 0; i < count; ++i) {
            final JavaType arg = type.containedType(i);
            final String argName = PrimitiveType.fromType(arg) != null ? nameForClass(arg, options) :
                    nameForType(arg, options);
            if (i > 0) {
                generic.append(", ");
            }
            generic.append(WordUtils.capitalize(argName));
        }
        if (count > 0) {
            generic.append(">");
        }
        return generic.toString();

    }
}
