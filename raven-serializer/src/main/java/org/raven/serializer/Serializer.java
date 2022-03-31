package org.raven.serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author yi.liang
 * date 2018.9.25
 * @since JDK1.8
 */
public interface Serializer {

    byte[] serialize(Object obj) throws IOException;

    void serialize(Object obj, OutputStream outputStream) throws IOException;

    <T> T deserialize(Class<T> clazz, byte[] data) throws IOException;

    <T> T deserialize(Class<T> clazz, byte[] data, int index, int count) throws IOException;

    <T> T deserialize(Class<T> clazz, InputStream inputStream) throws IOException;

}
