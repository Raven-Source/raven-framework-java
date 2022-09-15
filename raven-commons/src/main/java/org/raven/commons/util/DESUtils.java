package org.raven.commons.util;

import lombok.NonNull;

import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;

public class DESUtils {
    private DESUtils() {
    }

    /**
     * 偏移变量，固定占8位字节
     */
    private final static String IV_PARAMETER = "12345678";
    /**
     * 密钥算法
     */
    private static final String ALGORITHM = "DES";
    /**
     * 加密/解密算法-工作模式-填充模式
     */
    private static final String CIPHER_ALGORITHM = "DES/CBC/PKCS5Padding";

    /**
     * create key
     *
     * @param password password
     * @return
     * @throws Exception
     */
    private static Key generateKey(String password)
            throws Exception {

        DESKeySpec dks = new DESKeySpec(password.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        return keyFactory.generateSecret(dks);

    }


    /**
     * DES加密字符串
     *
     * @param data     待加密字符串
     * @param password 加密密码，长度不能够小于8位
     * @return 加密后内容
     * @throws Exception Exception
     */
    public static byte[] encrypt(@NonNull String data, @NonNull String password)
            throws Exception {

        if (password.length() < 8) {
            throw new RuntimeException("加密失败，key不能小于8位");
        }

        Key secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

        return cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * DES加密字符串
     *
     * @param data     待加密字符串
     * @param password 加密密码，长度不能够小于8位
     * @return 加密后内容
     * @throws Exception Exception
     */
    public static String encrypt2String(String data, String password)
            throws Exception {

        return new String(Base64Utils.encode(encrypt(data, password)));
    }

    /**
     * DES解密字符串
     *
     * @param password 解密密码，长度不能够小于8位
     * @param data     待解密字符串
     * @return 解密后内容
     * @throws Exception ex
     */
    public static byte[] decrypt(@NonNull String data, @NonNull String password)
            throws Exception {
        if (password.length() < 8) {
            throw new RuntimeException("加密失败，key不能小于8位");
        }

        Key secretKey = generateKey(password);
        Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
        IvParameterSpec iv = new IvParameterSpec(IV_PARAMETER.getBytes(StandardCharsets.UTF_8));
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

        return cipher.doFinal(Base64Utils.decode(data.getBytes(StandardCharsets.UTF_8)));
    }

    /**
     * DES解密字符串
     *
     * @param password 解密密码，长度不能够小于8位
     * @param data     待解密字符串
     * @return 解密后内容
     * @throws Exception ex
     */
    public static String decrypt2String(String data, String password)
            throws Exception {

        return new String(decrypt(data, password), StandardCharsets.UTF_8);

    }

}
