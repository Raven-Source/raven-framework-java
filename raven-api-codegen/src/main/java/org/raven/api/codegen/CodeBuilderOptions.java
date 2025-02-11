package org.raven.api.codegen;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.Set;

@Getter
@Setter
public class CodeBuilderOptions {

    private Set<String> ignoreClassSet;
    private Map<String, String> classNameReplaceMap;
    private Map<String, String> packageNameReplaceMap;
    private TemplateProvision templateProvision;
    private CodeGenerator codeGenerator;

}
