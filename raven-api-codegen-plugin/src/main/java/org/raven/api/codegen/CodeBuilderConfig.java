package org.raven.api.codegen;

import lombok.NonNull;
import org.raven.commons.util.Args;
import org.raven.commons.util.CollectionUtils;
import org.raven.commons.util.StringUtils;

import java.util.HashSet;
import java.util.LinkedHashMap;

public abstract class CodeBuilderConfig {

    public static CodeBuilderOptions builder(@NonNull ApiConfig apiConfig) {

        CodeBuilderOptions config = new CodeBuilderOptions();
        config.setIgnoreClassSet(new HashSet<>());
        config.setClassNameReplaceMap(new LinkedHashMap<>());
        config.setPackageNameReplaceMap(new LinkedHashMap<>());

        Language language = Language.fromString(apiConfig.getLanguage());
        Args.check(language != null, "language is empty or wrong");

        if (apiConfig.isUseDefaultCodeBuilderOptions()) {
            CodeBuilderOptions defaultOptions = DefaultCodeBuilderOptions.getOptions(language);

            config.getIgnoreClassSet().addAll(defaultOptions.getIgnoreClassSet());
            config.getClassNameReplaceMap().putAll(defaultOptions.getClassNameReplaceMap());
            config.getPackageNameReplaceMap().putAll(defaultOptions.getPackageNameReplaceMap());
        }

        if (CollectionUtils.isNotEmpty(apiConfig.getIgnoreClasses())) {
            config.getIgnoreClassSet().addAll(apiConfig.getIgnoreClasses());
        }

        if (CollectionUtils.isNotEmpty(apiConfig.getClassNameReplaces())) {

            for (String classNameReplace : apiConfig.getClassNameReplaces()) {
                String[] split = classNameReplace.split(",");
                if (split.length == 2 && StringUtils.isNotBlank(split[0]) && StringUtils.isNotBlank(split[1])) {
                    config.getClassNameReplaceMap().put(split[0].trim(), split[1].trim());
                }
            }
        }

        if (CollectionUtils.isNotEmpty(apiConfig.getPackageNameReplaces())) {
            for (String packageNameReplace : apiConfig.getPackageNameReplaces()) {
                String[] split = packageNameReplace.split(",");
                if (split.length == 2 && StringUtils.isNotBlank(split[0]) && StringUtils.isNotBlank(split[1])) {
                    config.getPackageNameReplaceMap().put(split[0].trim(), split[1].trim());
                }
            }

        }

        return config;

    }
}
