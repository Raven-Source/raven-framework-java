package org.raven.spring.api.schema;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MutableDescribable;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
@Setter
public class ModelType extends BasicType implements MutableDescribable {

    private String description;

    //    private String typeParametersName;
    private Map<String, MemberType> members = new LinkedHashMap<>();

}
