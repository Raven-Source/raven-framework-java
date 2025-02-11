package org.raven.api.codegen.java;

import org.raven.api.codegen.*;
import org.raven.api.codegen.util.FreeMarkerUtils;
import org.raven.api.codegen.util.GenerationUtils;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import lombok.Getter;
import org.dom4j.DocumentException;
import org.raven.commons.util.StringUtils;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author liangyi
 * date 2018/9/3
 */
public class JavaCodeBuilder implements CodeBuilder {

    private final Map<String, Template> templateCache = new HashMap<>();
    @Getter
    private final SchemaParser schemaParser;
    private final TemplateProvision templateProvision;
    private final CodeGenerator codeGenerator;

    private final Language language = Language.Java;

    private final static Set<String> ignoreImportSet = new LinkedHashSet<String>() {{
        add("java.lang.*");
        add(void.class.getName());
        add(byte.class.getName());
        add(short.class.getName());
        add(int.class.getName());
        add(long.class.getName());
        add(float.class.getName());
        add(double.class.getName());
        add(boolean.class.getName());

    }};

    private final static Set<String> enumImportSet = new LinkedHashSet<String>() {{
        add("org.raven.commons.data.annotation.Create");
        add("org.raven.commons.data.annotation.Values");

    }};

    private final static Map<String, String> typeConvertSet = new HashMap<String, String>() {{
        put(Boolean.class.getSimpleName(), "");
        put(boolean.class.getSimpleName(), "");
        put(Byte.class.getSimpleName(), "java.lang.Byte.parseByte");
        put(byte.class.getSimpleName(), "java.lang.Byte.parseByte");
        put(Short.class.getSimpleName(), "java.lang.Short.parseByte");
        put(short.class.getSimpleName(), "java.lang.Short.parseByte");
        put(Integer.class.getSimpleName(), "");
        put(int.class.getSimpleName(), "");
        put(Long.class.getSimpleName(), "java.lang.Long.valueOf");
        put(long.class.getSimpleName(), "java.lang.Long.valueOf");
        put(Float.class.getSimpleName(), "");
        put(float.class.getSimpleName(), "");
        put(Double.class.getSimpleName(), "java.lang.Double.valueOf");
        put(double.class.getSimpleName(), "java.lang.Double.valueOf");
        put(BigInteger.class.getSimpleName(), "java.math.BigInteger.valueOf");
        put(BigDecimal.class.getSimpleName(), "java.math.BigDecimal.valueOf");
    }};

    private final Set<String> ignoreClassSet;

//    private final Map<String, String> classNameReplaceMap;

//    private final Map<String, String> packageNameReplaceMap;

    public JavaCodeBuilder(CodeBuilderOptions builderOptions) {
        this(builderOptions.getIgnoreClassSet()
                , builderOptions.getClassNameReplaceMap()
                , builderOptions.getPackageNameReplaceMap()
                , builderOptions.getTemplateProvision()
                , builderOptions.getCodeGenerator());
    }

    public JavaCodeBuilder(Set<String> ignoreClassSet,
                           Map<String, String> classNameReplaceMap,
                           Map<String, String> packageNameReplaceMap,
                           TemplateProvision templateProvision,
                           CodeGenerator codeGenerator) {

        this.ignoreClassSet = ignoreClassSet;
//        this.classNameReplaceMap = classNameReplaceMap;
//        this.packageNameReplaceMap = packageNameReplaceMap;

        this.schemaParser = new SchemaParser(classNameReplaceMap, packageNameReplaceMap);
        this.templateProvision = templateProvision;
        this.codeGenerator = codeGenerator;
    }

    @Override
    public void loadStructure(String xmlContent) throws DocumentException {
        schemaParser.loadStructure(xmlContent);
    }

    public void render()
            throws IOException, TemplateException {

        codeGenerator.init(schemaParser.getServiceDescribeMap(), schemaParser.getModelDescribeMap());

        for (Map.Entry<String, ModelDescribe> entry : schemaParser.getModelDescribeMap().entrySet()) {
            process(entry.getValue());
        }

        for (Map.Entry<String, ServiceDescribe> entry : schemaParser.getServiceDescribeMap().entrySet()) {
            process(entry.getValue());
        }
    }

    private void process(AbstractDescribe modelDescribe) throws IOException, TemplateException {

        if (GenerationUtils.match(modelDescribe.getFullName(), ignoreClassSet)) {
            return;
        }

        List<String> imps = new ArrayList<>();
        for (String imp : modelDescribe.getImports()) {
            if (GenerationUtils.match(imp, ignoreImportSet)) continue;
            imps.add(imp);
        }

        if (modelDescribe.getModelType() == ModelType.EnumModel) {
            imps.addAll(enumImportSet);

            String typeConvert = typeConvertSet.get(((ModelDescribe) modelDescribe).getEnumType());
            if (StringUtils.isNotBlank(typeConvert)) {
                ((ModelDescribe) modelDescribe).setEnumTypeConvert(typeConvert);
            }
        }

        modelDescribe.getImports().clear();
        modelDescribe.getImports().addAll(imps);

        String tempName = modelDescribe.getModelType().toString() + "JavaTemp";
        Template template = templateCache.get(tempName);
        if (template == null) {

            try (InputStream inputStream = templateProvision.getTemplate(modelDescribe, language)) {

                template = FreeMarkerUtils.getTemplate(inputStream, tempName);
                templateCache.put(tempName, template);
            }
        }

        String content = FreeMarkerUtils.render(template, modelDescribe);
        codeGenerator.write(content, modelDescribe);

    }

}
