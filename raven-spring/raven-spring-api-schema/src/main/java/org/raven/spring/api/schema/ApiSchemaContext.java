package org.raven.spring.api.schema;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;
import org.raven.commons.data.MutableDescribable;
import org.raven.commons.util.CollectionUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * date 2022/9/27 16:09
 */
@Slf4j
public class ApiSchemaContext {

    @Getter
    private final ApiSchema apiSchema;

    private final Document apiStructureDocument;

    private final Map<Class<?>, String> classNameReplaceMap = new HashMap<Class<?>, String>() {{

//        put(String.class, "String");
//        put(List.class, "List");
//        put(Collection.class, "List");
//        put(Set.class, "List");
//        put(Map.class, "Map");
//        put(BigInteger.class, "Int128");
//        put(Long.class, "Int64");
//        put(long.class, "Int64");
//        put(Integer.class, "Int32");
//        put(int.class, "Int32");
//        put(Short.class, "Int16");
//        put(short.class, "Int32");
//        put(Byte.class, "Int8");
//        put(byte.class, "Int8");
//        put(Double.class, "Double");
//        put(double.class, "Double");
//        put(Float.class, "Float");
//        put(float.class, "Float");
//        put(BigDecimal.class, "Decimal");

    }};

    public ApiSchemaContext(SchemaProducer schemaProducer) {
        this.apiSchema = schemaProducer.toSchema();
        this.apiStructureDocument = buildDocument(this.apiSchema);
    }

    private String classNameConvert(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        return classNameReplaceMap.getOrDefault(clazz, clazz.getName());
    }

    private final OutputFormat format = OutputFormat.createPrettyPrint();

    private Document buildDocument(ApiSchema apiSchema) {

        Document document = DocumentHelper.createDocument();
        Element structureEle = document.addElement(Label.structure);
        structureEle.addAttribute(Label.version, apiSchema.getVersion());

        Element apisEle = structureEle.addElement(Label.apis);
        Element modelsEle = structureEle.addElement(Label.models);

        for (ServiceType api : apiSchema.getServiceTypes()) {

            Element serviceEle = apisEle.addElement(Label.service);
            serviceEle.addAttribute(Label.type, api.getType());

            setElementDeclaration(serviceEle, api);

            for (OperationType operation : api.getOperations()) {

                Element operationEle = serviceEle.addElement(Label.operation);
                operationEle.addAttribute(Label.name, operation.getName());

                setElementDeclaration(operationEle, operation);

                if (CollectionUtils.isNotEmpty(operation.getPaths())) {
                    operationEle.addAttribute(Label.paths, String.join(",", operation.getPaths()));
                }

                if (CollectionUtils.isNotEmpty(operation.getMethods())) {
                    operationEle.addAttribute(Label.methods, String.join(",", operation.getMethods()));
                }

                if (operation.getResult() != null) {
                    Element resultEle = operationEle.addElement(Label.result);
                    setElementBasicType(resultEle, operation.getResult());
                }

                if (operation.getParam() != null) {
                    Element paramEle = operationEle.addElement(Label.param);
                    paramEle.addAttribute(Label.name, operation.getParam().getName());
                    setElementBasicType(paramEle, operation.getParam());
                }
            }
        }

        for (ClassType classType : apiSchema.getModelTypesMap().values()) {

            Element modelEle = modelsEle.addElement(Label.model);
            modelEle.addAttribute(Label.type, classNameConvert(classType.getType()));

            if (classType instanceof ModelType) {
                ModelType modelType = ((ModelType) classType);

                setElementBasicType(modelEle, modelType);

                setElementDeclaration(modelEle, modelType);

//                if (modelType.getExtend() != null) {
//                    modelEle.addAttribute(Label.extend, classNameConvert(modelType.getExtend()));
//                }
//
//                if (modelType.getGenericTypeName() != null) {
//                    modelEle.addAttribute(Label.genericType, modelType.getGenericTypeName());
//                }

                for (MemberType memberType : modelType.getMembers().values()) {

                    Element memberEle = modelEle.addElement(Label.member);
                    memberEle.addAttribute(Label.name, memberType.getName());
                    memberEle.addAttribute(Label.type, classNameConvert(memberType.getType()));
                    memberEle.addAttribute(Label.genericType, memberType.getGenericTypeName());

                    setElementBasicType(memberEle, memberType);
                    setElementDeclaration(memberEle, memberType);
                    setElementConstraint(memberEle, memberType);
                }

            } else if (classType instanceof EnumType) {
                EnumType enumType = ((EnumType) classType);

                modelEle.addAttribute(Label.isEnum, String.valueOf(true));
                modelEle.addAttribute(Label.enumType, classNameConvert(enumType.getEnumType()));
                modelEle.addAttribute(Label.isString, String.valueOf(enumType.isString()));

                setElementDeclaration(modelEle, enumType);

                if (enumType.getImplement() != null) {
                    Element implEle = modelEle.addElement(Label.implement);
                    setElementBasicType(implEle, enumType.getImplement());
                }
                if (enumType.getExtend() != null) {
                    Element extendEle = modelEle.addElement(Label.extend);
                    setElementBasicType(extendEle, enumType.getExtend());
                }

//                modelEle.addAttribute(Label.implement, classNameConvert(enumType.getImplement()));

                for (EnumType.EnumValue entry : enumType.getEnums().values()) {

                    Element enumsEle = modelEle.addElement(Label.enums);
                    enumsEle.addAttribute(Label.name, entry.getName());
                    enumsEle.addAttribute(Label.value, entry.getValue().toString());

                    setElementDeclaration(enumsEle, entry);
                }
            }

        }

        return document;
    }


    /**
     * 转换为xml
     *
     * @return xml
     */
    public String convertToXml() {

        String content = null;

        //核心为document  XMLWriter
        XMLWriter xmlWriter = null;
        try (StringWriter strWriter = new StringWriter()) {

            xmlWriter = new XMLWriter(strWriter, format);
            //作用:构建XML文件结构
            xmlWriter.write(this.apiStructureDocument);
            content = strWriter.toString();

            xmlWriter.close();

        } catch (IOException ex) {
            log.error(ex.getMessage(), ex);
        } finally {
            if (xmlWriter != null) {
                try {
                    xmlWriter.close();
                } catch (IOException ignored) {
                }
            }
        }

        return content;
    }

    private void setElementBasicType(Element element, BasicType typeInfo) {

        if (typeInfo.getType() != null) {
            element.addAttribute(Label.type, classNameConvert(typeInfo.getType()));
        }

        if (typeInfo.getExtend() != null) {
            Element extendEle = element.addElement(Label.extend);
            setElementBasicType(extendEle, typeInfo.getExtend());
//            element.addAttribute(Label.extend, classNameConvert(typeInfo.getExtend()));
        }

        if (typeInfo instanceof MemberType) {
            MemberType memberInfo = (MemberType) typeInfo;

            if (memberInfo.getIsArray()) {
                element.addAttribute(Label.isArray, String.valueOf(true));
            }
        }

        element.addAttribute(Label.genericType, typeInfo.getGenericTypeName());

        if (typeInfo.getGenericTypes() != null) {

            for (BasicType generic : typeInfo.getGenericTypes()) {

                Element genericEle = element.addElement(Label.generic);
                setElementBasicType(genericEle, generic);
            }

        }
    }

    private void setElementDeclaration(Element element, MutableDescribable declaration) {

        if (StringUtils.isNotBlank(declaration.getDescription())) {

            element.addElement(Label.description)
                    .addText(declaration.getDescription());
        }
    }

    private void setElementConstraint(Element element, MemberType memberType) {

        ConstraintType constraint = memberType.getConstraint();
        if (constraint != null) {
            element.addAttribute(Label.nullable, String.valueOf(constraint.getNullable()));
        }
    }
}
