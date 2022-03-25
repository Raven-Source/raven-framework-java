package org.raven.serializer;


import org.raven.commons.util.Args;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author yi.liang
 * date 2018.9.25
 * @since JDK1.8
 */
public abstract class BasicSerializer {

    protected static final Charset charset = StandardCharsets.UTF_8;

    protected static final Class<String> stringClazz = String.class;

    protected static final Class<byte[]> byteArrayClazz = byte[].class;

    public byte[] serializeString(String obj) {

        Args.notNull(obj, "obj");
        return obj.getBytes(charset);
    }

    public byte[] trySerialize(final Object obj)
            throws NullPointerException {

        Args.notNull(obj, "obj");

        if (obj instanceof byte[]) {
            return (byte[]) obj;
        } else if (obj instanceof String) {
            return serializeString((String) obj);
        }
        return null;
    }

    public <T> T tryDeserialize(final Class<T> clazz, final byte[] data)
            throws NullPointerException {
        Args.notNull(data, "data");

        return this.tryDeserialize(clazz, data, 0, data.length);
    }

    @SuppressWarnings("unchecked")
    public <T> T tryDeserialize(final Class<T> clazz, final byte[] data, final int index, final int count)
            throws NullPointerException {

        Args.notNull(data, "data");

        if (clazz.equals(byteArrayClazz)) {
            return (T) data;
        } else if (clazz.equals(stringClazz)) {
            return (T) new String(data, index, count, charset);
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public <T> T tryDeserialize(final Class<T> clazz, final InputStream inputStream)
            throws NullPointerException, IOException {

        Args.notNull(inputStream, "inputStream");

        if (clazz.equals(byteArrayClazz) || clazz.equals(stringClazz)) {
            int count = inputStream.available();
            byte[] data = new byte[count];
            inputStream.read(data);

            if (clazz.equals(byteArrayClazz)) {
                return (T) data;
            } else {
                return (T) new String(data, charset);
            }
        }
        return null;
    }

}
