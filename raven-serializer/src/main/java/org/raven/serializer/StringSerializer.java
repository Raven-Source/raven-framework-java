package org.raven.serializer;

import java.io.IOException;

/**
 * @author yi.liang
 * date 2018.9.25
 * @since JDK1.8
 */
public interface StringSerializer {
    String serializeToString(Object obj) throws IOException;
}
