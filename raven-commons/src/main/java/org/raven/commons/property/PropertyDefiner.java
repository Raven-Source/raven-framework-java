package org.raven.commons.property;

import org.raven.commons.util.PropertiesUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class PropertyDefiner {
    private static final String PROPERTIES_FILE = "META-INF/raven-commons.properties";

    public static final Map<String, String> PROPERTIES = new HashMap<>() {{
        put("contract.responseModel.message", PropertiesUtil.getString(PROPERTIES_FILE, "contract.responseModel.message", "msg"));
        put("contract.responseModel.code", PropertiesUtil.getString(PROPERTIES_FILE, "contract.responseModel.code", "code"));
        put("contract.responseModel.data", PropertiesUtil.getString(PROPERTIES_FILE, "contract.responseModel.data", "data"));
        put("contract.responseModel.success", PropertiesUtil.getString(PROPERTIES_FILE, "contract.responseModel.success", "success"));
        put("contract.responseModel.extension", PropertiesUtil.getString(PROPERTIES_FILE, "contract.responseModel.extension", "ext"));
    }};

}
