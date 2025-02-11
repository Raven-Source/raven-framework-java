package org.raven.spring.api.schema;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MutableDescribable;

@Getter
@Setter
public class MemberType extends BasicType implements MutableDescribable {

    private String name;
    private String description;

    private Boolean isArray = Boolean.FALSE;

    private ConstraintType constraint;

}
