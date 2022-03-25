package org.raven.commons.data;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2020.6.14
 */
public interface StringType extends SerializableType<String> {

    @Override
    String getValue();
}
