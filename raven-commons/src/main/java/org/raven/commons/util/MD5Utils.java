package org.raven.commons.util;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

/**
 * @author yi.liang
 * @since JDK1.8
 * date 2021.01.13 20:32
 */
@Slf4j
public class MD5Utils {

    private MD5Utils() {
    }

    /**
     * @param str original string
     * @return encrypt string
     */
    public static byte[] encrypt(@NonNull String str) {

        return encrypt(str, StringUtils.EMPTY);
    }

    /**
     * @param str  original string
     * @param slat slat
     * @return encrypt string
     */
    public static byte[] encrypt(@NonNull String str, @NonNull String slat) {
        byte[] s = null;
        try {

            str = str + slat;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(str.getBytes(StandardCharsets.UTF_8));
            s = m.digest();

        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
        }

        return s;
    }

    /**
     * @param str original string
     * @return encrypt string
     */
    public static String encryptToHexString(@NonNull String str) {
        return encryptToHexString(str, StringUtils.EMPTY);
    }

    /**
     * @param str  original string
     * @param slat slat
     * @return encrypt string
     */
    public static String encryptToHexString(@NonNull String str, @NonNull String slat) {

        byte[] data = encrypt(str, slat);
        StringBuilder sb = new StringBuilder(data.length >> 1);
        String temp;
        for (int i = 0; i < data.length; i++) {
            temp = Integer.toHexString(0xFF & data[i]);
            if (temp.length() < 2)
                sb.append(0);
            sb.append(temp.toUpperCase());
        }
        return sb.toString();
    }

}
