package org.raven.serializer.withMsgpack;

import org.msgpack.MessagePack;
import org.raven.serializer.BasicSerializer;
import org.raven.serializer.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * MessagePack序列化类
 *
 * @author yi.liang
 * @since JDK1.8
 * date 2018/1/3 14:00:00
 */
public class MsgpackSerializer extends BasicSerializer
        implements Serializer {

    private MessagePack msgpack;


    public MsgpackSerializer() {

        msgpack = MessagePackFactory.create();
    }

    /**
     * @param obj obj
     * @return byte[]
     * @throws IOException IOException
     */
    public byte[] serialize(Object obj) throws IOException {
        return msgpack.write(obj);
    }

    /**
     * @param obj          obj
     * @param outputStream outputStream
     * @throws IOException IOException
     */
    public void serialize(Object obj, OutputStream outputStream) throws IOException {
        msgpack.write(outputStream, obj);
    }

    /**
     *
     */
    public <T> T deserialize(Class<T> clazz, byte[] data) throws IOException {
        return msgpack.read(data, clazz);
    }

    /**
     *
     */
    public <T> T deserialize(Class<T> clazz, byte[] data, int index, int count) throws IOException {
        return msgpack.read(data, index, count, clazz);
    }

    /**
     *
     */
    public <T> T deserialize(Class<T> clazz, InputStream inputStream) throws IOException {
        return msgpack.read(inputStream, clazz);
    }

}
