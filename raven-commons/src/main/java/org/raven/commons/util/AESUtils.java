package org.raven.commons.util;

import lombok.NonNull;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;

public class AESUtils {
    private AESUtils() {
    }

    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "AES";

    private static final int KEY_SIZE = 128;

    /**
     * 生成key
     *
     * @return
     * @throws Exception
     */
    public static String generateKey()
            throws Exception {

        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(KEY_SIZE, new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64.getEncoder().encodeToString(secretKey.getEncoded());

    }


    public static byte[] encrypt(@NonNull String data, @NonNull String password) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(password), ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        byte[] encryptedBytes = cipher.doFinal(data.getBytes());
        return encryptedBytes;
    }

    /**
     * DES加密字符串
     *
     * @param data     待加密字符串
     * @param password 加密密码，长度不能够小于8位
     * @return 加密后内容
     */
    public static String encrypt2String(String data, String password)
            throws Exception {

        String outStr = new String(Base64Utils.encode(encrypt(data, password)));
        return outStr;
    }

    public static byte[] decrypt(@NonNull String encryptedData, @NonNull String password) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        SecretKeySpec secretKeySpec = new SecretKeySpec(Base64.getDecoder().decode(password), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        byte[] decryptedBytes = cipher.doFinal(Base64Utils.decode(encryptedData));
        return decryptedBytes;
    }

    /**
     * DES解密字符串
     *
     * @param password 解密密码，长度不能够小于8位
     * @param data     待解密字符串
     * @return 解密后内容
     */
    public static String decrypt2String(String data, String password)
            throws Exception {

        String outStr = new String(decrypt(data, password), StandardCharsets.UTF_8);
        return outStr;

    }

}
