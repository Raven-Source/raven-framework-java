package org.raven.api.codegen;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MutableDescribable;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class ServiceDescribe extends AbstractDescribe implements MutableDescribable {

    private List<OperationDescribe> operations = new ArrayList<>();

    private String description;

}
