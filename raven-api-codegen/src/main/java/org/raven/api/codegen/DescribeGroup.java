package org.raven.api.codegen;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Getter
@Setter
public class DescribeGroup {

    protected ModelType modelType;

    protected String className;

    protected String packageName;

    protected Set<ImportDescribe> imports = new TreeSet<>();

    private List<AbstractDescribe> describes;


}
