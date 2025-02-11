package org.raven.spring.api.schema;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MutableDescribable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServiceType implements MutableDescribable {

    private String type;

    private String description;

    private List<OperationType> operations = new ArrayList<>();
}
