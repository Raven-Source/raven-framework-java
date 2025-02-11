package org.raven.serializer.withJacksonMsgpack;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.msgpack.jackson.dataformat.MessagePackFactory;
import org.raven.serializer.withJackson.JacksonSerializer;
import org.raven.serializer.withJackson.ObjectMapperConfig;
import org.raven.serializer.withJackson.SerializerSetting;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2021.08.24 01:41
 */
public class JacksonMsgpackSerializer extends JacksonSerializer {


    public JacksonMsgpackSerializer() {
        this((SerializerSetting) null);
    }

    /**
     * 构造函数
     *
     * @param setting SerializerSetting {@link org.raven.serializer.withJackson.SerializerSetting}
     */
    public JacksonMsgpackSerializer(SerializerSetting setting) {

        super(ObjectMapperConfig.getObjectMapper(setting != null ? setting : SerializerSetting.getDefault(), new MessagePackFactory()));
    }

    public JacksonMsgpackSerializer(ObjectMapper mapper) {
        super(mapper);
    }
}
