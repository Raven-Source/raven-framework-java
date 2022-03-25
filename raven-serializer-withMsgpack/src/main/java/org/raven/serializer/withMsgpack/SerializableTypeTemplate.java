package org.raven.serializer.withMsgpack;

import org.msgpack.MessageTypeException;
import org.msgpack.packer.Packer;
import org.msgpack.template.AbstractTemplate;
import org.msgpack.unpacker.Unpacker;
import org.raven.commons.data.SerializableType;
import org.raven.commons.data.SerializableTypeUtils;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2018/2/11 18:00:00
 */
public class SerializableTypeTemplate<T extends SerializableType<?>> extends AbstractTemplate<T> {

    private final Class<T> target;

    /**
     * @param target target
     */
    public SerializableTypeTemplate(final Class<T> target) {
        this.target = target;
    }

    /**
     * @param pk       pk
     * @param target   target
     * @param required required
     * @throws IOException IOException
     */
    @Override
    public void write(Packer pk, T target, boolean required) throws IOException {

        if (target == null) {
            if (required) {
                throw new MessageTypeException("Attempted to write null");
            }
            pk.writeNil();
            return;
        }
        pk.write((target).getValue());
    }

    @Override
    public T read(Unpacker u, T to, boolean required) throws IOException {
        if (!required && u.trySkipNil()) {
            return null;
        }

        Class<?> genericType = SerializableTypeUtils.getGenericType(target);
        if (genericType.equals(String.class)) {
            return SerializableTypeUtils.valueOf(target, u.readString());
        }
        if (genericType.equals(Integer.class)) {
            return SerializableTypeUtils.valueOf(target, u.readInt());
        }
        if (genericType.equals(Long.class)) {
            return SerializableTypeUtils.valueOf(target, u.readLong());
        }
        if (genericType.equals(BigInteger.class)) {
            return SerializableTypeUtils.valueOf(target, u.readBigInteger());
        }
        if (genericType.equals(Double.class)) {
            return SerializableTypeUtils.valueOf(target, u.readDouble());
        }
        if (genericType.equals(Float.class)) {
            return SerializableTypeUtils.valueOf(target, u.readFloat());
        }
        if (genericType.equals(BigDecimal.class)) {
            return SerializableTypeUtils.valueOf(target, new BigDecimal(u.readString()));
        }

        return null;
    }
}
