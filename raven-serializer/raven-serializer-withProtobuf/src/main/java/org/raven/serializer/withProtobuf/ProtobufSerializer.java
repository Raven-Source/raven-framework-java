package org.raven.serializer.withProtobuf;

import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.CodedOutputStream;
import org.raven.serializer.BasicSerializer;
import org.raven.serializer.Serializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2019.08.22 19:35
 */
@SuppressWarnings("unchecked")
public class ProtobufSerializer extends BasicSerializer
        implements Serializer {


    @Override
    public byte[] serialize(Object obj) throws IOException {

        Codec codec = ProtobufProxy.create(obj.getClass());
        return codec.encode(obj);

    }

    @Override
    public void serialize(Object obj, OutputStream outputStream) throws IOException {

        Codec codec = ProtobufProxy.create(obj.getClass());
        CodedOutputStream codedOutputStream = CodedOutputStream.newInstance(outputStream);
        codec.writeTo(obj, codedOutputStream);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] data) throws IOException {

        Codec<T> codec = ProtobufProxy.create(clazz);
        return codec.decode(data);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] data, int index, int count) throws IOException {

        Codec<T> codec = ProtobufProxy.create(clazz);
        CodedInputStream codedInputStream = CodedInputStream.newInstance(data, index, count);

        return codec.readFrom(codedInputStream);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, InputStream inputStream) throws IOException {

        Codec<T> codec = ProtobufProxy.create(clazz);
        CodedInputStream codedInputStream = CodedInputStream.newInstance(inputStream);
        return codec.readFrom(codedInputStream);

    }
}
