package org.raven.api.codegen;

import org.raven.api.codegen.util.GenerationUtils;
import org.dom4j.Element;
import org.raven.commons.util.StringUtils;

import java.util.*;

/**
 * @author liangyi
 * @date 2018/9/3
 */
public class ModelStructureParser extends AbstractStructureParser {

//    private final Map<String, String> typeConvert;

    public ModelStructureParser(Map<String, String> classNameReplaceMap, Map<String, String> packageNameReplaceMap) {
        super(classNameReplaceMap, packageNameReplaceMap);
    }

    /**
     * @param modelEle
     * @return
     */
    public ModelDescribe loadModel(Element modelEle) {

        //model type
        String type = modelEle.attributeValue(Label.type);
        boolean isEnum = Objects.equals("true", modelEle.attributeValue(Label.isEnum));

        ModelDescribe modelDescribe = new ModelDescribe();

        setClassName(modelDescribe, type);
        setPackageName(modelDescribe, type);

        setDeclaration(modelEle, modelDescribe);

        //model extend
        Element extendsEle = modelEle.element(Label.extend);
        extracted(extendsEle, modelDescribe, false);

        if (isEnum) {

            modelDescribe.setModelType(ModelType.EnumModel);
            String enumType = modelEle.attributeValue(Label.enumType);

//            modelDescribe.getImports().add(enumType);
            addImports(modelDescribe, enumType);

            modelDescribe.setEnumType(GenerationUtils.extractClassName(enumType));
            modelDescribe.setEnumIsString(Objects.equals("true", modelEle.attributeValue(Label.isString)));

            Element implsEle = modelEle.element(Label.implement);
            extracted(implsEle, modelDescribe, true);

            loadEnumValues(modelDescribe, modelEle);
        } else {

            String genericTypeName = processGenericType(modelDescribe, "", modelEle);
            modelDescribe.setGenericTypeName(GenerationUtils.extractClassName(genericTypeName));

            loadModelMember(modelDescribe, modelEle);
        }

        return modelDescribe;

    }

    private void extracted(Element extendsEle, ModelDescribe modelDescribe, boolean isImplement) {

        if (extendsEle != null) {

            String extend = extendsEle.attributeValue(Label.type);

            if (!StringUtils.isEmpty(extend)) {

//                modelDescribe.getImports().add(extend);
                addImports(modelDescribe, extend);
                String extendName = processGenericType(modelDescribe, GenerationUtils.extractClassName(extend), extendsEle);
                if (isImplement) {
                    modelDescribe.setImplement(extendName);
                } else {
                    modelDescribe.setExtend(extendName);
                }

            }
        }
    }

    private void loadEnumValues(ModelDescribe modelDescribe, Element element) {

        String enumType = element.attributeValue(Label.enumType);
        List<Element> enumsELeList = element.elements(Label.enums);
        List<EnumDescribe> enumsList = new ArrayList<>();
        for (Element itemEle : enumsELeList) {
            EnumDescribe enumDescribe = new EnumDescribe();

            enumDescribe.setName(itemEle.attributeValue(Label.name));
            enumDescribe.setValue(itemEle.attributeValue(Label.value));

            setDeclaration(itemEle, enumDescribe);

            enumsList.add(enumDescribe);
        }

        modelDescribe.setEnums(enumsList);
    }

    /**
     * @param modelDescribe
     * @param element
     */
    private void loadModelMember(ModelDescribe modelDescribe, Element element) {

        List<Element> memberELeList = element.elements(Label.member);
        List<MemberDescribe> members = new ArrayList<>();

        for (Element memberEle : memberELeList) {
            MemberDescribe memberDescribe = new MemberDescribe();

            String name = memberEle.attributeValue(Label.name);
            memberDescribe.setFiled(GenerationUtils.camelCase(name));
            memberDescribe.setProperty(GenerationUtils.pascalCase(name));

            String type = memberEle.attributeValue(Label.type);
            String isArray = memberEle.attributeValue(Label.isArray);

            String typeName = processGenericType(modelDescribe, type, memberEle);

//            if (Objects.equals(isArray, "true")) {
//                typeName += "[]";
//            }

            memberDescribe.setType(
                    processTypeName(typeName)
            );

//            modelDescribe.getImports().add(type);
            addImports(modelDescribe, type);

            setDeclaration(memberEle, memberDescribe);
            setConstraint(memberEle, memberDescribe);

            members.add(memberDescribe);
        }

        modelDescribe.setMembers(members);

    }


}
