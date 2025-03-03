package org.raven.commons.util;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.03.02 16:04
 */
public final class PropertiesUtils {


    private PropertiesUtils() {

    }

    private static final Map<String, Properties> propertiesMap;

    static {
        propertiesMap = new HashMap<>();
    }

    public static String getString(String propertiesPath, String key) {
        return getString(propertiesPath, key, StringUtils.EMPTY);
    }

    public static String getString(String propertiesPath, String key, String defaultValue) {


        Properties properties = propertiesMap.get(propertiesPath);
        if (properties == null) {
            properties = get(propertiesPath);
        }

        return properties.getProperty(key, defaultValue);
//        if (properties != null) {
//            return properties.getProperty(key, defaultValue);
//        } else {
//            return defaultValue;
//        }

    }

    private static synchronized Properties get(String propertiesPath) {
        Properties properties = propertiesMap.get(propertiesPath);
        if (properties != null) {
            return properties;
        }

        properties = load(propertiesPath);
        if (properties == null) {
            properties = new Properties();
        }

        propertiesMap.put(propertiesPath, properties);
        return properties;
    }

    private static Properties load(String propertiesPath) {

        InputStream in = null;
        try {
            in = Thread.currentThread().getContextClassLoader().getResourceAsStream(propertiesPath);

            if (in == null) {
                in = PropertiesUtils.class.getResourceAsStream(propertiesPath);
            }
            if (in != null) {
                Properties prop = new Properties();
                prop.load(in);
                return prop;
            }
            return null;
        } catch (Exception ignored) {
            return null;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (Exception ignored) {
                }
            }
        }
    }

}
