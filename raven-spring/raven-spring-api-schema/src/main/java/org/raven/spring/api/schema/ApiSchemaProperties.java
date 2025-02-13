package org.raven.spring.api.schema;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Set;

@ConfigurationProperties(prefix = "raven.api.schema")
@Getter
@Setter
public class ApiSchemaProperties {

    private String version;

    private String packageRoot;

    private Set<String> excludeClassSet;
}
