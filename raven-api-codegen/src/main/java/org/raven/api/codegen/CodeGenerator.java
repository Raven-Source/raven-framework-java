package org.raven.api.codegen;

import java.util.Map;

public interface CodeGenerator {

    default void init(Map<String, ServiceDescribe> serviceDescribeMap, Map<String, ModelDescribe> modelDescribeMap) {
    }

    void write(String content, AbstractDescribe describe);

}
