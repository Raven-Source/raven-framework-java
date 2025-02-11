package org.raven.api.codegen;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Set;

@Getter
@Setter
@ToString
public class ApiConfig {

    private boolean enable = true;

    private String name;

    private String url;

    private String systemPath;

    private String defaultTemplateRoot = "/template";

    private String outputRoot;

    private String language = Language.Java.name();

    private Set<String> styles;

    private boolean useDefaultCodeBuilderOptions = true;

    private List<String> ignoreClasses;

    private List<String> classNameReplaces;

    private List<String> packageNameReplaces;

    private List<TemplateConfig> templates;

//    private boolean useDefaultTemplate = true;

}
