package org.raven.api.codegen;

import lombok.Getter;
import lombok.Setter;
import org.raven.commons.data.MutableDescribable;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class OperationDescribe implements MutableDescribable {

    private String name;
    private Set<String> paths;
    private Set<String> methods;

    private String resultType;

    private String description;

    private List<ParamDescribe> params = new ArrayList<>();

    @Getter
    @Setter
    public static class ParamDescribe {

        private String name;

        private String type;

    }

}
