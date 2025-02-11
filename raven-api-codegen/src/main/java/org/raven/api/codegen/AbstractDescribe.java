package org.raven.api.codegen;

import lombok.Getter;
import lombok.Setter;

import java.util.*;

@Getter
@Setter
public abstract class AbstractDescribe {

    public String getFullName() {
        return packageName + "." + className;
    }

    protected ModelType modelType = ModelType.InteriorModel;

    protected String className;

    protected String packageName;

    protected Set<String> imports = new TreeSet<>();

    protected Set<String> styles = new HashSet<>();

//    protected Set<String> metadata = new HashSet<>();
}
