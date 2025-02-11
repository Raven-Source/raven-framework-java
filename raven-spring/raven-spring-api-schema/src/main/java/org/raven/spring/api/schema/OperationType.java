package org.raven.spring.api.schema;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MutableDescribable;

import java.util.Set;

@Getter
@Setter
public class OperationType implements MutableDescribable {

    private String name;
    private Set<String> paths;
    private Set<String> methods;

    private MemberType param;
    private MemberType result;

    private String description;
}
