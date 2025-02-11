package org.raven.serializer.withJackson;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.raven.commons.util.Args;
import org.raven.serializer.BasicSerializer;
import org.raven.serializer.Serializer;
import org.raven.serializer.StringSerializer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Jackson json
 *
 * @author yi.liang
 * date 2018/1/3 14:00:00
 */
public class JacksonSerializer extends BasicSerializer
        implements Serializer, StringSerializer {

    @Getter
    private final ObjectMapper mapper;

    public JacksonSerializer() {
        this((SerializerSetting) null);
    }

    /**
     * 构造函数
     *
     * @param setting SerializerSetting {@link org.raven.serializer.withJackson.SerializerSetting}
     */
    public JacksonSerializer(SerializerSetting setting) {

        this.mapper = ObjectMapperConfig.getObjectMapper(setting != null ? setting : SerializerSetting.getDefault());
    }

    /**
     * 构造函数
     *
     * @param mapper ObjectMapper {@link com.fasterxml.jackson.databind.ObjectMapper}
     */
    public JacksonSerializer(ObjectMapper mapper) {

        this.mapper = mapper;
    }

    /**
     *
     */
    @Override
    public String serializeToString(Object obj) throws IOException {

        Args.notNull(obj, "obj");

        if (obj instanceof String) return (String) obj;
        return mapper.writeValueAsString(obj);
    }

    /**
     *
     */
    @Override
    public byte[] serialize(Object obj) throws IOException {

        Args.notNull(obj, "obj");

        byte[] res = trySerialize(obj);
        if (res != null) {
            return res;
        } else {
            return mapper.writeValueAsBytes(obj);
        }
    }

    /**
     *
     */
    @Override
    public void serialize(Object obj, OutputStream outputStream) throws IOException {

        Args.notNull(obj, "obj");

        byte[] res = trySerialize(obj);
        if (res != null) {
            outputStream.write(res);
        } else {
            mapper.writeValue(outputStream, obj);
        }
    }

    /**
     *
     */
    @Override
    public <T> T deserialize(Class<T> clazz, byte[] data) throws IOException {

        Args.notNull(data, "data");

        T res = tryDeserialize(clazz, data);
        if (res != null) {
            return res;
        } else {
            return mapper.readValue(data, clazz);
        }

    }

    /**
     *
     */
    @Override
    public <T> T deserialize(Class<T> clazz, byte[] data, int index, int count) throws IOException {
        Args.notNull(data, "data");

        T res = tryDeserialize(clazz, data, index, count);
        if (res != null) {
            return res;
        } else {
            return mapper.readValue(data, index, count, clazz);
        }
    }

    /**
     *
     */
    @Override
    public <T> T deserialize(Class<T> clazz, InputStream inputStream) throws IOException {
        Args.notNull(inputStream, "inputStream");

        T res = tryDeserialize(clazz, inputStream);
        if (res != null) {
            return res;
        } else {
            return mapper.readValue(inputStream, clazz);
        }
    }

}
