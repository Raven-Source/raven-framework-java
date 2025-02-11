package org.raven.api.codegen;

import org.raven.api.codegen.java.DefaultJavaCodeGeneratorAbstract;
import org.raven.api.codegen.typescript.DefaultTypeScriptCodeGeneratorAbstract;

public abstract class CodeGeneratorFactor {

    public static CodeGenerator createCodeGenerator(Language language, String outputRoot) {

        switch (language) {

            case Java:
                return new DefaultJavaCodeGeneratorAbstract(outputRoot);

            case TypeScript:
                return new DefaultTypeScriptCodeGeneratorAbstract(outputRoot);

            default:
                return null;
        }

    }

}
