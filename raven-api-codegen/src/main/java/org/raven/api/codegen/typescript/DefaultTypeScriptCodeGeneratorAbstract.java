package org.raven.api.codegen.typescript;

import org.raven.api.codegen.*;
import org.raven.api.codegen.util.GenerationUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;

@Slf4j
public class DefaultTypeScriptCodeGeneratorAbstract extends AbstractFileCodeGenerator implements CodeGenerator {

    public DefaultTypeScriptCodeGeneratorAbstract(String outputRoot) {
        super(outputRoot);
    }

    @Override
    public void init(Map<String, ServiceDescribe> serviceDescribeMap, Map<String, ModelDescribe> modelDescribeMap) {
        GenerationUtils.deleteDir(new File(outputRoot));
    }

    @Override
    public void write(String content, AbstractDescribe describe) {
        try {

            String fileName;
            switch (describe.getModelType()) {

                case EnumModel:
                    fileName = "constants.ts";
                    break;
                case ServiceModel:
                    fileName = describe.getClassName().toLowerCase() + "/index.ts";
                    break;
                case InteriorModel:
                default:
                    fileName = "typings.d.ts";
                    break;

            }

//            String path = outputRoot + describe.getPackageName().replaceAll("\\.", "/")
//                    + "/" + fileName;

            String path = Paths.get(outputRoot,
                            describe.getPackageName().replaceAll("\\.", "/"),
                            fileName)
                    .toString();

            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            if (!file.exists()) {
                file.createNewFile();
            }

            Files.write(Paths.get(path), content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.APPEND);

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

    }

}
