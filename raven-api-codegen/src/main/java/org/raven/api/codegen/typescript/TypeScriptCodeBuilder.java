package org.raven.api.codegen.typescript;

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

public class TypeScriptCodeBuilder implements CodeBuilder {

    private final Map<String, Template> templateCache = new HashMap<>();
    @Getter
    private final SchemaParser schemaParser;
    private final TemplateProvision templateProvision;
    private final CodeGenerator codeGenerator;

    private final Language language = Language.TypeScript;

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

        add("void");
        add("bigint");
        add("string");
        add("number");
        add("Array");
        add("Set");
        add("Map");

    }};

    private final static Set<String> enumImportSet = new LinkedHashSet<String>() {{
    }};

    private final static Map<String, String> typeConvertSet = new HashMap<String, String>() {{
        put(Boolean.class.getSimpleName(), "");
        put(boolean.class.getSimpleName(), "");
        put(Short.class.getSimpleName(), "");
        put(Short.class.getSimpleName(), "");
        put(Integer.class.getSimpleName(), "");
        put(int.class.getSimpleName(), "");
        put(Long.class.getSimpleName(), "BigInt");
        put(long.class.getSimpleName(), "BigInt");
        put(Float.class.getSimpleName(), "");
        put(float.class.getSimpleName(), "");
        put(Double.class.getSimpleName(), "");
        put(double.class.getSimpleName(), "");
        put(BigInteger.class.getSimpleName(), "BigInt");
        put(BigDecimal.class.getSimpleName(), "new Decimal");
    }};

    private final Set<String> ignoreClassSet;

    private final Map<String, String> defaultClassNameReplaceMap = new LinkedHashMap<String, String>() {{
        put(Void.class.getName(), "void");
        put(String.class.getName(), "string");
        put(List.class.getName(), "Array");
        put(Set.class.getName(), "Set");
        put(Map.class.getName(), "Map");
        put(Boolean.class.getName(), "boolean");
        put(boolean.class.getName(), "boolean");
        put(Integer.class.getName(), "number");
        put(int.class.getName(), "number");
        put(Integer.class.getName() + "\\[\\]", "number[]");
        put(int.class.getName() + "\\[\\]", "number[]");
        put(Long.class.getName(), "bigint");
        put(long.class.getName(), "bigint");
        put(Long.class.getName() + "\\[\\]", "bigint[]");
        put(long.class.getName() + "\\[\\]", "bigint[]");
        put(BigInteger.class.getName(), "bigint");
        put(BigDecimal.class.getName(), "number");
        put(BigInteger.class.getName() + "\\[\\]", "bigint[]");
        put(BigDecimal.class.getName() + "\\[\\]", "number[]");
    }};
//
//    private final Map<String, String> packageNameReplaceMap;

    public TypeScriptCodeBuilder(CodeBuilderOptions builderOptions) {
        this(builderOptions.getIgnoreClassSet()
                , builderOptions.getClassNameReplaceMap()
                , builderOptions.getPackageNameReplaceMap()
                , builderOptions.getTemplateProvision()
                , builderOptions.getCodeGenerator());
    }

    public TypeScriptCodeBuilder(Set<String> ignoreClassSet,
                                 Map<String, String> classNameReplaceMap,
                                 Map<String, String> packageNameReplaceMap,
                                 TemplateProvision templateProvision,
                                 CodeGenerator codeGenerator) {

        this.ignoreClassSet = ignoreClassSet;
//        this.classNameReplaceMap = classNameReplaceMap;
//        this.packageNameReplaceMap = packageNameReplaceMap;

        for (Map.Entry<String, String> entry : defaultClassNameReplaceMap.entrySet()) {
            if (!classNameReplaceMap.containsKey(entry.getKey())) {
                classNameReplaceMap.put(entry.getKey(), entry.getValue());
            }
        }

        this.schemaParser = new SchemaParser(classNameReplaceMap, packageNameReplaceMap);
        this.templateProvision = templateProvision;
        this.codeGenerator = codeGenerator;
    }

    @Override
    public void loadStructure(String xmlContent) throws DocumentException {
        schemaParser.clear();
        schemaParser.loadStructure(xmlContent);
    }

    @Override
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

        String tempName = modelDescribe.getModelType().toString() + "TypeScriptTemp";
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
