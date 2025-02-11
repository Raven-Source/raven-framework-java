package org.raven.spring.api.schema;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MutableDescribable;

import java.util.Map;

@Getter
@Setter
public class EnumType extends ClassType implements MutableDescribable {

    private String description;

    protected BasicType implement;
    protected BasicType extend;

    private Class<?> enumType;

    private boolean string = true;

    private Map<String, EnumValue> enums;

    @Getter
    @Setter
    public static class EnumValue implements MutableDescribable {
        private String name;
        private String description;
        private Object value;
    }

}

