//package org.raven.commons.data;
//
//import org.junit.Assert;
//import org.junit.Test;
//
//public class ValueTypeUtilsTest {
//
//
//    @Test
//    public void valueOf() {
//        Assert.assertEquals(
//            ValueTypeUtils.valueOf(DBType.class, DBType.Mongodb.getValue()), DBType.Mongodb
//        );
//
//        Assert.assertEquals(
//            ValueTypeUtils.valueOf(DBType.class, DBType.Mysql.getValue()), DBType.Mysql
//        );
//
//        Assert.assertEquals(
//            ValueTypeUtils.valueOf(Gender.class, 1), Gender.x
//        );
//
//        Assert.assertEquals(
//            ValueTypeUtils.valueOf(Gender.class, 2), Gender.y
//        );
//
//        Assert.assertEquals(
//            ValueTypeUtils.valueOf(Gender.class, 3).getValue(), Integer.valueOf(30)
//        );
//
//    }
//
//}
