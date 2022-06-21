package org.raven.commons.data;

import org.junit.Assert;
import org.junit.Test;
import org.raven.commons.util.Base64Utils;

/**
 * @author yanfeng
 */
public class Base64UtilsTest {

    @Test
    public void encodeTest() throws Exception {

        String src = "ascg>s1osi<nALK2143065945HOIndsjMZLVcxgfhAOIH(!*34798!^@&#^*@+2";

        byte[] bytes = Base64Utils.encode(src.getBytes());
        System.out.println(new String(bytes));
        String res = new String(Base64Utils.decode(bytes));

        Assert.assertEquals(src, res);

    }

}
