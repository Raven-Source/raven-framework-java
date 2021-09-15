package org.raven.commons.data;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

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


    @Test
    public void multiThreadTest() throws Exception {

        int seed = 100;
        List<Thread> futures = new ArrayList<>(seed);

        for (int i = 0; i < seed; i++) {

            Thread thread = new Thread(() -> {

                DBType dbType = SerializableTypeUtils.valueOf(DBType.class, 1);
                System.out.println(dbType);

            });

            futures.add(thread);
        }

        for (Thread future : futures) {
            future.start();
        }

        for (Thread future : futures) {
            future.join();

        }

        System.out.println("ggg");


    }

}
