package org.raven.commons.data.mybatis.utils;


import java.io.InputStream;
import java.net.URL;

public final class ClassLoaderUtils {
    private ClassLoaderUtils() {
        throw new UnsupportedOperationException("ClassLoaderUtils is not instantiable!");
    }

    public static ClassLoader getClassLoader() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return classLoader != null ? classLoader : ClassLoaderUtils.class.getClassLoader();
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> loadClass(String className) throws ClassNotFoundException {
        return (Class<T>) getClassLoader().loadClass(className);
    }

    @SuppressWarnings("unchecked")
    public static boolean findClass(String className) {
        try {
            return getClassLoader().loadClass(className) != null;
        } catch (ClassNotFoundException e) {
            return false;
        } catch (NoClassDefFoundError e) {
            return false;
        }
    }

    public static URL getResource(String resourceName) {
        return getClassLoader().getResource(resourceName);
    }

    public static InputStream getResourceAsStream(String resourceName) {
        return getClassLoader().getResourceAsStream(resourceName);
    }
}

