package org.raven.serializer;

import org.junit.Test;

public class DataSerializerTest {

    @Test
    public void SerializerTypeTest(){

        for(SerializerType serializerType : SerializerType.values()){

            System.out.println(serializerType);
        }

    }
}
