package org.raven.commons.data;

import org.junit.Assert;
import org.junit.Test;

public class SerializableTypeUtilsTest {

    @Test
    public void valueOf() {
        Assert.assertEquals(
                SerializableTypeUtils.valueOf(DBType.class, DBType.Mongodb.getValue()), DBType.Mongodb
        );

        Assert.assertEquals(
                SerializableTypeUtils.valueOf(DBType.class, DBType.Mysql.getValue()), DBType.Mysql
        );

        Assert.assertEquals(
                SerializableTypeUtils.valueOf(Gender.class, 10), Gender.x
        );

        Assert.assertEquals(
                SerializableTypeUtils.stringValueOf(Gender.class, "10"), Gender.x
        );

        Assert.assertEquals(
                SerializableTypeUtils.valueOf(Gender.class, 20), Gender.y
        );

        Assert.assertEquals(
                SerializableTypeUtils.stringValueOf(Gender.class, "20"), Gender.y
        );

        Assert.assertEquals(
                SerializableTypeUtils.valueOf(Gender.class, 30).getValue(), Integer.valueOf(30)
        );


        Assert.assertEquals(
                SerializableTypeUtils.valueOf(OperationEnum.class, "gt").getValue(), OperationEnum.GT.getValue()
        );

        Assert.assertEquals(
                SerializableTypeUtils.valueOf(OperationEnum.class, "nin").getValue(), OperationEnum.NIN.getValue()
        );

    }

}
