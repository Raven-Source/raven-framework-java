package org.raven.commons.data.mybatis.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.raven.commons.data.mybatis.type.ObjectMapperSupplier;
import org.raven.commons.data.mybatis.type.ObjectMapperWrapper;
import org.raven.commons.data.mybatis.utils.ClassLoaderUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;
import java.util.function.Supplier;

@Slf4j
@SuppressWarnings("unchecked")
public class Configuration {

    public static final Configuration INSTANCE = new Configuration();
    public static final String PROPERTIES_FILE_PATH = "mybatis-types.properties.path";
    public static final String PROPERTIES_FILE_NAME = "mybatis-types.properties";
    private final Properties properties = getProperties();

    public Configuration() {
        this.load();

    }

    private void load() {
        InputStream propertiesInputStream = null;

        try {
            propertiesInputStream = this.propertiesInputStream();
            if (propertiesInputStream != null) {
                this.properties.load(propertiesInputStream);
            }
        } catch (IOException e) {
            log.error("Can't load properties", e);
        } finally {
            try {
                if (propertiesInputStream != null) {
                    propertiesInputStream.close();
                }
            } catch (IOException e) {
                log.error("Can't close the properties InputStream", e);
            }

        }

    }

    private InputStream propertiesInputStream() throws IOException {
        String propertiesFilePath = System.getProperty(PROPERTIES_FILE_PATH);
        URL propertiesFileUrl = null;
        if (propertiesFilePath != null) {
            try {
                propertiesFileUrl = new URL(propertiesFilePath);
            } catch (MalformedURLException var7) {
                propertiesFileUrl = ClassLoaderUtils.getResource(propertiesFilePath);
                if (propertiesFileUrl == null) {
                    File f = new File(propertiesFilePath);
                    if (f.exists() && f.isFile()) {
                        try {
                            propertiesFileUrl = f.toURI().toURL();
                        } catch (MalformedURLException var6) {
                            log.error("The property " + propertiesFilePath + " can't be resolved to either a URL, a classpath resource or a File");
                        }
                    }
                }
            }

            if (propertiesFileUrl != null) {
                return propertiesFileUrl.openStream();
            }
        }

        return ClassLoaderUtils.getResourceAsStream(PROPERTIES_FILE_NAME);
    }

    public static Properties getProperties() {
        Properties copy = new Properties();
        return copy;
    }

    public ObjectMapperWrapper getObjectMapperWrapper() {
        Object objectMapperPropertyInstance = instantiateClass(PropertyKey.JACKSON_OBJECT_MAPPER);

        ObjectMapperWrapper objectMapperWrapper = new ObjectMapperWrapper();

        if (objectMapperPropertyInstance != null) {
            if(objectMapperPropertyInstance instanceof ObjectMapperSupplier) {
                ObjectMapper objectMapper = ((ObjectMapperSupplier) objectMapperPropertyInstance).get();
                if(objectMapper != null) {
                    objectMapperWrapper = new ObjectMapperWrapper(objectMapper);
                }
            }
            else if (objectMapperPropertyInstance instanceof Supplier) {
                Supplier<ObjectMapper> objectMapperSupplier = (Supplier<ObjectMapper>) objectMapperPropertyInstance;
                objectMapperWrapper = new ObjectMapperWrapper(objectMapperSupplier.get());
            }
            else if (objectMapperPropertyInstance instanceof ObjectMapper) {
                ObjectMapper objectMapper = (ObjectMapper) objectMapperPropertyInstance;
                objectMapperWrapper = new ObjectMapperWrapper(objectMapper);
            }
        }

        return objectMapperWrapper;
    }

    private <T> T instantiateClass(PropertyKey propertyKey) {
        T object = null;
        String property = properties.getProperty(propertyKey.getKey());
        if (property != null) {
            try {
                Class<T> clazz = ClassLoaderUtils.loadClass(property);
                log.debug("Instantiate {}", clazz);
                object = clazz.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                log.error("Couldn't load the " + property + " class given by the " + propertyKey + " property", e);
            }
        }
        return object;
    }

    public static enum PropertyKey {
        JACKSON_OBJECT_MAPPER("mybatis.types.jackson.object.mapper");

        private final String key;

        private PropertyKey(String key) {
            this.key = key;
        }

        public String getKey() {
            return this.key;
        }
    }
}
