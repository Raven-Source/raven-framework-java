package org.raven.api.codegen.java;

import org.raven.api.codegen.*;
import org.raven.api.codegen.util.GenerationUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Paths;
import java.util.Map;

@Slf4j
public class DefaultJavaCodeGeneratorAbstract extends AbstractFileCodeGenerator implements CodeGenerator {

    public DefaultJavaCodeGeneratorAbstract(String outputRoot) {
        super(outputRoot);
    }

    @Override
    public void init(Map<String, ServiceDescribe> serviceDescribeMap, Map<String, ModelDescribe> modelDescribeMap) {
        GenerationUtils.deleteDir(new File(outputRoot));
    }

    @Override
    public void write(String content, AbstractDescribe describe) {
        try {

            String fileName = describe.getClassName() + ".java";
//            String path = outputRoot
//                    + "/"
//                    + describe.getPackageName().replaceAll("\\.", "/")
//                    + "/" + fileName;

            String path = Paths.get(outputRoot,
                            describe.getPackageName().replaceAll("\\.", "/"),
                            fileName)
                    .toString();



            File file = new File(path);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            file.createNewFile();

            FileWriter fw = new FileWriter(file);
            fw.write(content);

            fw.close();

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

    }

}
