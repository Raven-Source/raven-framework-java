package org.raven.commons.data;

import org.junit.Assert;
import org.junit.Test;
import org.raven.commons.contract.KeyPairString;
import org.raven.commons.util.RSAUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yanfeng
 */
public class RSAUtilsTest {

    @Test
    public void genKeyPairTest() throws Exception {

        for (Integer integer : new Integer[]{512, 1024, 2048}) {
            System.out.println("keysize: " + integer);
            KeyPairString keyPairString = RSAUtils.genKeyPair(integer);
            rsaTest(keyPairString);
        }
    }

    private void rsaTest(KeyPairString keyPairString) throws Exception {

        //加密字符串
        String message = "{\"status\":\"UP\",\"groups\":[\"神秘的国家\"]}";

        System.out.println("随机生成的公钥为:" + keyPairString.getPublicKey());
        System.out.println("随机生成的私钥为:" + keyPairString.getPrivateKey());
        String messageEn = RSAUtils.encrypt2String(message, keyPairString.getPublicKey());
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = RSAUtils.decrypt2String(messageEn, keyPairString.getPrivateKey());
        System.out.println("还原后的字符串为:" + messageDe);

        Assert.assertEquals(message, messageDe);

    }

}
