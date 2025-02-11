package org.raven.api.codegen;

import org.raven.api.codegen.util.GenerationUtils;
import lombok.AllArgsConstructor;
import org.dom4j.Attribute;
import org.dom4j.Element;
import org.raven.commons.data.MutableDescribable;
import org.raven.commons.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author liangyi
 * @date 2018/9/3
 */
@AllArgsConstructor
public abstract class AbstractStructureParser {

    protected final Map<String, String> classNameReplaceMap;

    protected final Map<String, String> packageNameReplaceMap;

    protected void setClassName(AbstractDescribe describe, String type) {

        String className = GenerationUtils.replaceName(type, classNameReplaceMap);
        describe.setClassName(
                GenerationUtils.extractClassName(className)
        );
    }

    protected String processTypeName(String type) {

        String className = GenerationUtils.replaceName(type, classNameReplaceMap);
        return GenerationUtils.extractClassName(className);
    }

    protected void setPackageName(AbstractDescribe describe, String type) {

        String packageName = GenerationUtils.extractPackageName(type);
        describe.setPackageName(
                GenerationUtils.replaceName(packageName, packageNameReplaceMap)
        );
    }

    protected void addImports(AbstractDescribe describe, String... imports) {

        List<String> imps = new ArrayList<>();
        for (String imp : imports) {

            imp = GenerationUtils.replaceName(imp, classNameReplaceMap);
            String className = GenerationUtils.extractClassName(imp);

            String packageName = GenerationUtils.extractPackageName(imp);
            if (StringUtils.isNotBlank(packageName)) {
                packageName = GenerationUtils.replaceName(packageName, packageNameReplaceMap);
            }

            imps.add((StringUtils.isNotBlank(packageName) ? packageName + "." : "") + className);
        }
        describe.getImports().addAll(imps);
    }

    protected void setDeclaration(Element element, MutableDescribable declaration) {

        Element descriptionEle = element.element(Label.description);
        if (descriptionEle != null) {
            declaration.setDescription(descriptionEle.getText());
        }
    }

    protected void setConstraint(Element element, MemberDescribe describe) {

        ConstraintDescribe constraint = describe.getConstraint();
        if (describe.getConstraint() == null) {
            constraint = new ConstraintDescribe();
            describe.setConstraint(constraint);
        }

        Attribute nullableAttr = element.attribute(Label.nullable);
        if (nullableAttr != null && nullableAttr.getValue().equalsIgnoreCase("true")) {
            constraint.setNullable(true);
        }

    }

    /**
     * @param describe
     * @param value
     * @param element
     * @return
     */
    public String processGenericType(AbstractDescribe describe, String value, Element element) {
        if (element == null)
            return value;

        String genericType = element.attributeValue(Label.genericType);
        if (StringUtils.isNotBlank(genericType)) {
            return genericType;
        }

        List<Element> genericEleList = element.elements(Label.generic);
        if (genericEleList == null || genericEleList.isEmpty())
            return value;

        List<String> genericNames = new ArrayList<>();
        for (Element genericEle : genericEleList) {

            genericType = genericEle.attributeValue(Label.genericType);
            String type = genericEle.attributeValue(Label.type);

            //递归
            String name = processGenericType(
                    describe,
                    StringUtils.isNotBlank(type) ? type : genericType,
                    genericEle);

            if (StringUtils.isNotBlank(type)) {
                addImports(describe, type);
            }

            String className = GenerationUtils.replaceName(name, classNameReplaceMap);
            genericNames.add(GenerationUtils.extractClassName(className));
        }

        String res = GenerationUtils.replaceName(value, classNameReplaceMap) + "<" + String.join(", ", genericNames) + ">";
        return res;

    }

}
