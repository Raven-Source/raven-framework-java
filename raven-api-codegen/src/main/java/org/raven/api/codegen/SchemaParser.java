package org.raven.api.codegen;

import lombok.Getter;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.Reader;
import java.io.StringReader;
import java.util.*;

/**
 * date 2018/9/3
 *
 * @author liangyi
 */
@Getter
public class SchemaParser {

//    private Set<Class> baseClazzSet;

    private final ModelSchemaParser modelStructure;

    private final ServiceSchemaParser serviceStructure;

    private final Map<String, ModelDescribe> modelDescribeMap;

    private final Map<String, ServiceDescribe> serviceDescribeMap;

    public SchemaParser(Map<String, String> classNameReplaceMap, Map<String, String> packageNameReplaceMap) {
        this.modelDescribeMap = new LinkedHashMap<>();
        this.serviceDescribeMap = new LinkedHashMap<>();

        this.modelStructure = new ModelSchemaParser(classNameReplaceMap, packageNameReplaceMap);
        this.serviceStructure = new ServiceSchemaParser(classNameReplaceMap, packageNameReplaceMap);

//        this.baseClazzSet = new LinkedHashSet<Class>() {{
//            add(Object.class);
//            add(String.class);
//            add(byte.class);
//            add(Byte.class);
//            add(short.class);
//            add(Short.class);
//            add(int.class);
//            add(Integer.class);
//            add(boolean.class);
//            add(Boolean.class);
//            add(long.class);
//            add(Long.class);
//            add(double.class);
//            add(Double.class);
//            add(float.class);
//            add(Float.class);
//            add(BigDecimal.class);
//            add(HashMap.class);
//            add(ArrayList.class);
//        }};
    }

    public void loadStructure(String content)
            throws DocumentException {

        SAXReader saxReader = new SAXReader();

        Reader reader = new StringReader(content);
        Document document = saxReader.read(reader);

        Element element = document.getRootElement();

        loadModel(element);
        loadService(element);

    }

    public void clear() {
        modelDescribeMap.clear();
        serviceDescribeMap.clear();
    }

    private void loadModel(Element element) {

        List<Element> modelELeList = element.element(Label.models).elements(Label.model);

        for (Element modelEle : modelELeList) {

            //model type
            String type = modelEle.attributeValue(Label.type);
            if (!modelDescribeMap.containsKey(type)) {
                ModelDescribe modelDescribe = modelStructure.loadModel(modelEle);
                modelDescribeMap.put(modelDescribe.getFullName(), modelDescribe);
            }

        }

    }

    private void loadService(Element element) {

        List<Element> serviceEleList = element.element(Label.apis).elements(Label.service);

        for (Element serviceEle : serviceEleList) {

            String type = serviceEle.attributeValue(Label.type);
            ServiceDescribe serviceDescribe = serviceStructure.loadService(serviceEle);

            serviceDescribeMap.put(type, serviceDescribe);
        }

    }

}
