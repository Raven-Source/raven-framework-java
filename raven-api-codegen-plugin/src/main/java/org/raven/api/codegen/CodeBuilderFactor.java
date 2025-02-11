package org.raven.api.codegen;

import org.raven.api.codegen.java.JavaCodeBuilder;
import org.raven.api.codegen.typescript.TypeScriptCodeBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public abstract class CodeBuilderFactor {

    public static CodeBuilder createCodeBuilder(ApiConfig apiConfig) {

        CodeBuilderOptions builderOptions = CodeBuilderConfig.builder(apiConfig);
        builderOptions.setTemplateProvision(new CodeTemplateProvision(apiConfig));
        Language language = Language.fromString(apiConfig.getLanguage());

        builderOptions.setCodeGenerator(CodeGeneratorFactor.createCodeGenerator(language, apiConfig.getOutputRoot()));

        switch (language) {

            case Java:
                return new JavaCodeBuilder(builderOptions);
            case TypeScript:
                return new TypeScriptCodeBuilder(builderOptions);
            default:
                return null;
        }
    }
}
