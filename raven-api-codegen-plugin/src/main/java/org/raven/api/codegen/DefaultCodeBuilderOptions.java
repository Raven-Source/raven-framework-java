package org.raven.api.codegen;

import org.raven.commons.util.Maps;
import org.raven.commons.util.Sets;

import java.util.Collections;
import java.util.Map;

public abstract class DefaultCodeBuilderOptions {

    private static Map<Language, CodeBuilderOptions> defaultOptionsMap;

    public static CodeBuilderOptions getOptions(Language language) {
        return defaultOptionsMap.get(language);
    }

    static {

        CodeBuilderOptions options;
        Map<Language, CodeBuilderOptions> optionsMap = Maps.newHashMap();

        // Java
        options = new CodeBuilderOptions();
        options.setIgnoreClassSet(Collections.unmodifiableSet(Sets.newHashSet(
                "org.raven.common.*"
        )));
        options.setClassNameReplaceMap(Collections.unmodifiableMap(Maps.newHashMap(
                "(.*)Controller", "$1Api"
        )));
        options.setPackageNameReplaceMap(Collections.unmodifiableMap(Maps.newHashMap(
                "(.*).controller$", "$1.api"
        )));


        optionsMap.put(Language.Java, options);

        // TypeScript
        options = new CodeBuilderOptions();
        options.setIgnoreClassSet(Collections.emptySet());
        options.setClassNameReplaceMap(Maps.newHashMap(
                "(.*)Controller", "$1"
        ));
        options.setPackageNameReplaceMap(Collections.unmodifiableMap(Maps.newHashMap(
                "org.raven.common.contract(\\..*)?", "types",
                "org.raven.common.data(\\..*)?", "types"
        )));

        optionsMap.put(Language.TypeScript, options);

        //
        defaultOptionsMap = Collections.unmodifiableMap(optionsMap);
    }

//    public static void main(String[] args) {
//
//        String res =
//                GenerationUtils.replaceName("com.rome.store.inspection.app.webapi.controller",
//                        DefaultCodeBuilderOptions.getOptions(Language.Java).getPackageNameReplaceMap());
//
//        System.out.println(
//                res
//        );
//    }

}
