package org.raven.spring.api.schema;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class ApiSchema {

    private final String version;

    private final List<ServiceType> serviceTypes;

    private final Map<String, ClassType> modelTypesMap;
}
