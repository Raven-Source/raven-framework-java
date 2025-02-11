package org.raven.api.codegen;

import org.dom4j.Element;
import org.raven.commons.util.Sets;
import org.raven.commons.util.StringUtils;

import java.util.List;
import java.util.Map;

public class ServiceSchemaParser extends AbstractSchemaParser {

    public ServiceSchemaParser(Map<String, String> classNameReplaceMap, Map<String, String> packageNameReplaceMap) {
        super(classNameReplaceMap, packageNameReplaceMap);
    }

    public ServiceDescribe loadService(Element serviceEle) {

        ServiceDescribe serviceDescribe = new ServiceDescribe();
        serviceDescribe.setModelType(ModelType.ServiceModel);

        String type = serviceEle.attributeValue(Label.type);

        setClassName(serviceDescribe, type);
        setPackageName(serviceDescribe, type);

        setDeclaration(serviceEle, serviceDescribe);

        loadOperation(serviceDescribe, serviceEle);

        return serviceDescribe;

    }

    private void loadOperation(ServiceDescribe serviceDescribe, Element modelEle) {

        List<Element> operationEleList = modelEle.elements(Label.operation);
        for (Element operationEle : operationEleList) {

            OperationDescribe operationDescribe = new OperationDescribe();

            String name = operationEle.attributeValue(Label.name);
            operationDescribe.setName(name);
            String paths = operationEle.attributeValue(Label.paths);
            if (StringUtils.isNotBlank(paths)) {
                operationDescribe.setPaths(Sets.newHashSet(paths.split(",")));
            }
            String methods = operationEle.attributeValue(Label.methods);
            if (StringUtils.isNotBlank(methods)) {
                operationDescribe.setMethods(Sets.newHashSet(methods.split(",")));
            }

            setDeclaration(operationEle, operationDescribe);

            serviceDescribe.getOperations().add(operationDescribe);

            Element resultEle = operationEle.element(Label.result);
            if (resultEle != null) {

                String type = resultEle.attributeValue(Label.type);
//                serviceDescribe.getImports().add(type);
                addImports(serviceDescribe, type);

                String typeName = processGenericType(serviceDescribe, type, resultEle);

                operationDescribe.setResultType(processTypeName(typeName));

            }

            List<Element> paramEleList = operationEle.elements(Label.param);
            for (Element paramEle : paramEleList) {

                OperationDescribe.ParamDescribe paramDescribe = new OperationDescribe.ParamDescribe();
                String paramName = paramEle.attributeValue(Label.name);

                paramDescribe.setName(paramName);

                String type = paramEle.attributeValue(Label.type);
                addImports(serviceDescribe, type);
//                serviceDescribe.getImports().add(type);

                String typeName = processGenericType(serviceDescribe, type, paramEle);

                paramDescribe.setType(processTypeName(typeName));

                operationDescribe.getParams().add(paramDescribe);
            }
        }
    }

}
