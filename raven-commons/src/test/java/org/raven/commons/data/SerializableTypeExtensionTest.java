package org.raven.commons.data;

import org.junit.Test;
import org.raven.commons.extensions.SerializableTypeExtension;

import java.util.Map;

/**
 * date 2022/7/22 16:18
 */
public class SerializableTypeExtensionTest {

    @Test
    public void test() {
        Map map = SerializableTypeExtension.convertStringTypeToDict(OperationEnum.class);
        System.out.println(map);
    }

}
