package org.raven.serializer.withJackson;

import org.raven.serializer.withJackson.format.JsonPropertyFormatHelper;
import tools.jackson.databind.PropertyNamingStrategy;
import tools.jackson.databind.cfg.MapperConfig;
import tools.jackson.databind.introspect.AnnotatedField;
import tools.jackson.databind.introspect.AnnotatedMethod;

/**
 * @author yi.liang
 * date 2018.9.25
 * @since JDK1.8
 */
public class PropertyNamingStrategyWarp extends PropertyNamingStrategy {

    /**
     * for deserialize
     */
    @Override
    public String nameForSetterMethod(MapperConfig<?> config,
                                      AnnotatedMethod method, String defaultName) {
        return JsonPropertyFormatHelper.format(method, defaultName);
    }

    /**
     * for serialize
     */
    @Override
    public String nameForGetterMethod(MapperConfig<?> config,
                                      AnnotatedMethod method, String defaultName) {
        return JsonPropertyFormatHelper.format(method, defaultName);
    }

    @Override
    public String nameForField(MapperConfig<?> config, AnnotatedField field, String defaultName) {
        return JsonPropertyFormatHelper.format(field, defaultName);
    }
}
