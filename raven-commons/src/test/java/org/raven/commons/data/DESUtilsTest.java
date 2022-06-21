package org.raven.commons.data;

import org.junit.Assert;
import org.junit.Test;
import org.raven.commons.util.DESUtils;


public class DESUtilsTest {


    @Test
    public void genKeyPairTest() throws Exception {

        rsaTest("dvMvuG-eP_EQowA5xjcyulUNZrTKN3KpxWRWi1_T_PQW");
    }

    private void rsaTest(String password) throws Exception {

        //加密字符串
        String message = "{\"code\":\"\",\"data\":[{\"id\":1,\"name\":\"闪购活动\",\"remark\":\"闪购活动\"},{\"id\":2,\"name\":\"超值优惠\",\"remark\":\"超值优惠活动\"},{\"id\":3,\"name\":\"清仓活动\",\"remark\":\"清仓活动\"},{\"id\":4,\"name\":\"满减活动\",\"remark\":\"满减活动\"},{\"id\":5,\"name\":\"App专享价\",\"remark\":\"App专享活动\"},{\"id\":6,\"name\":\"加价换购\",\"remark\":\"加价换购活动\"},{\"id\":7,\"name\":\"打包价格\",\"remark\":\"打包价活动\"},{\"id\":8,\"name\":\"赠品活动\",\"remark\":\"赠品活动\"}],\"extension\":{},\"success\":true,\"msg\":\"操作成功\"}";

        System.out.println("密码为:" + password);
        String messageEn = DESUtils.encrypt2String(message, password);
        System.out.println(message + "\t加密后的字符串为:" + messageEn);
        String messageDe = DESUtils.decrypt2String(messageEn, password);
        System.out.println("还原后的字符串为:" + messageDe);

        Assert.assertEquals(message, messageDe);

    }

}
