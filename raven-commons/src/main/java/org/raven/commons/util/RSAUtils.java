package org.raven.commons.util;


import lombok.NonNull;
import org.raven.commons.contract.KeyPairString;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * @author yanfeng
 */
public final class RSAUtils {

    private RSAUtils() {
    }

    /**
     * create KeyPairString
     *
     * @param keySize eg:512\1024\2048
     * @return KeyPairString
     * @throws NoSuchAlgorithmException ex
     */
    public static KeyPairString genKeyPair(int keySize) throws NoSuchAlgorithmException {

        // KeyPairGenerator类用于生成公钥和私钥对，基于RSA算法生成对象
        KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA");

        // 初始化密钥对生成器，密钥大小
        keyPairGen.initialize(keySize, new SecureRandom());

        // 生成一个密钥对，保存在keyPair中
        KeyPair keyPair = keyPairGen.generateKeyPair();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();   // 得到私钥
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();  // 得到公钥

        String publicKeyString = new String(Base64Utils.encode(publicKey.getEncoded(), true));
        // 得到私钥字符串
        String privateKeyString = new String(Base64Utils.encode((privateKey.getEncoded()), true));

        return new KeyPairString(publicKeyString, privateKeyString);
    }

    /**
     * RSA公钥加密
     *
     * @param data      加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static byte[] encrypt(@NonNull String data, @NonNull String publicKey) throws Exception {
        //base64编码的公钥
        byte[] decoded = Base64Utils.decode(publicKey);
        RSAPublicKey pubKey = (RSAPublicKey) KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(decoded));
        //RSA加密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.ENCRYPT_MODE, pubKey);
        byte[] res = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));
        return res;
    }

    /**
     * RSA公钥加密
     *
     * @param data      加密字符串
     * @param publicKey 公钥
     * @return 密文
     * @throws Exception 加密过程中的异常信息
     */
    public static String encrypt2String(String data, String publicKey) throws Exception {

        String outStr = new String(Base64Utils.encode(encrypt(data, publicKey)));
        return outStr;
    }

    /**
     * RSA私钥解密
     *
     * @param data       加密字符串
     * @param privateKey 私钥
     * @return 密文
     * @throws Exception 解密过程中的异常信息
     */
    public static byte[] decrypt(@NonNull String data, @NonNull String privateKey) throws Exception {
        //64位解码加密后的字符串
        byte[] inputByte = Base64Utils.decode(data.getBytes(StandardCharsets.UTF_8));
        //base64编码的私钥
        byte[] decoded = Base64Utils.decode(privateKey);
        RSAPrivateKey priKey = (RSAPrivateKey) KeyFactory.getInstance("RSA").generatePrivate(new PKCS8EncodedKeySpec(decoded));
        //RSA解密
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.DECRYPT_MODE, priKey);
        byte[] res = cipher.doFinal(inputByte);
        return res;
    }

    /**
     * @param data       加密字符串
     * @param privateKey 私钥
     * @return String
     * @throws Exception 解密过程中的异常信息
     */
    public static String decrypt2String(String data, String privateKey) throws Exception {

        String outStr = new String(decrypt(data, privateKey), StandardCharsets.UTF_8);
        return outStr;
    }
}
