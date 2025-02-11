package org.raven.spring.api.schema.spi;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class WebHandlerDescribable {

    private Set<String> paths;
    private Set<String> methods;

}
