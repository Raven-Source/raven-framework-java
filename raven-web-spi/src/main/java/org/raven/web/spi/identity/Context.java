package org.raven.web.spi.identity;


import lombok.Getter;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class Context {

    private Map<String, Object> attributes = new HashMap<>();

}
