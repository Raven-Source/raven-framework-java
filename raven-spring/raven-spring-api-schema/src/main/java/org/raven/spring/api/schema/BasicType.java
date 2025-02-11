package org.raven.spring.api.schema;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BasicType extends ClassType {

    protected BasicType extend;
    protected List<BasicType> genericTypes;

    private String genericTypeName;

    public BasicType() {

    }

//    public static BasicType clone(BasicType basicType) {
//
//        BasicType clone = new BasicType();
//        clone.extend = basicType.extend;
//        clone.genericType = basicType.genericType;
//        clone.type = basicType.type;
//
//        return clone;
//    }
}
