package org.raven.serializer;

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author yi.liang
 * date 2018.9.25
 * @since JDK1.8
 */
@Slf4j
public class SerializerFactory {

    private final static Map<String, Serializer> _serializerDict = new HashMap<String, Serializer>();
    private final static EnumMap<SerializerType, String[]> _clazzNameDict = new EnumMap<SerializerType, String[]>(SerializerType.class) {
        {
            put(SerializerType.Jackson, new String[]{"org.raven.serializer.withJackson", "JacksonSerializer"});
            put(SerializerType.Msgpack, new String[]{"org.raven.serializer.withJacksonMsgpack", "JacksonMsgpackSerializer"});
//            put(SerializerType.Protobuf, new String[]{"org.raven.serializer.withProtobuf", "ProtobufSerializer"});
            put(SerializerType.MessagePack, new String[]{"org.raven.serializer.withMsgpack", "MsgpackSerializer"});
        }
    };

    /**
     * @param serializerType serializerType
     * @param args           args
     * @return Serializer
     */
    private static Serializer getDataSerializer(SerializerType serializerType, Object[] args) {

        String key = getKey(serializerType, args);

        Serializer serializer = _serializerDict.get(key);
        if (!Objects.isNull(serializer)) {
            return serializer;
        }

        synchronized (_serializerDict) {

            serializer = _serializerDict.get(key);
            if (!Objects.isNull(serializer)) {
                return serializer;
            }

            String[] clazzName = _clazzNameDict.get(serializerType);

            try {
                Class<?> clazz = Class.forName(String.join(".", clazzName[0], clazzName[1]));
                if (args == null || args.length == 0) {
                    return (Serializer) clazz.getDeclaredConstructor().newInstance();
                } else {
                    for (Constructor<?> constructor : clazz.getConstructors()) {

                        int count = constructor.getParameterCount();
                        if (count != args.length) continue;

                        Class<?>[] paramsTypes = constructor.getParameterTypes();
                        boolean applyTo = true;
                        for (int i = 0; i < args.length; i++) {
                            if (!args[i].getClass().isAssignableFrom(paramsTypes[i])) {
                                applyTo = false;
                                break;
                            }
                        }

                        if (applyTo) {
                            serializer = (Serializer) constructor.newInstance(args);
                            _serializerDict.put(key, serializer);
                            return serializer;
                        }
                    }

                    return null;
                }
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
                return null;
            }
        }

    }

    /**
     * @param serializerType serializerType
     * @param args           args
     * @return String
     */
    private static String getKey(SerializerType serializerType, Object[] args) {

        String argsStr;
        if (args == null) {
            argsStr = "";
        } else {
            StringJoiner joiner = new StringJoiner("_");
            Arrays.stream(args).map(Object::toString).forEach(joiner::add);
            argsStr = joiner.toString();
        }
        return MessageFormat.format("{0}:{1}", _clazzNameDict.get(serializerType)[1], argsStr);
    }

    /**
     * create Serializer
     *
     * @param serializerType serializerType
     * @return Serializer
     */
    public static Serializer create(SerializerType serializerType) {
        return create(serializerType, null);
    }

    /**
     * create Serializer
     *
     * @param serializerType serializerType
     * @param args           args
     * @return Serializer
     */
    public static Serializer create(SerializerType serializerType, Object[] args) {
        //Serializer serializer = (Serializer)Activator.CreateInstance(type, new object[] { });
        return getDataSerializer(serializerType, args);
    }

    /**
     * create Serializer
     *
     * @param serializerType serializerType
     * @return Serializer
     */
    public static Serializer create(String serializerType) {
        return create(serializerType, null);
    }

    /**
     * create Serializer
     *
     * @param serializerTypeStr serializerTypeString
     * @param args              args
     * @return Serializer
     */
    public static Serializer create(String serializerTypeStr, Object[] args) {
        SerializerType serializerType = Enum.valueOf(SerializerType.class, serializerTypeStr);
        return create(serializerType, args);
    }

}
