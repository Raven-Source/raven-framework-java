package org.raven.commons.util;

import org.apache.commons.lang3.StringUtils;

/**
 * @author yi.liang
 * @date 2019.04.04 17:38
 * @since JDK1.8
 */
public class HexUtil {

    private HexUtil() {
    }

    /**
     * To byte array byte [ ].
     *
     * @param hexString the hex string
     * @return the byte [ ]
     */
    public static byte[] toByteArray(String hexString) {
        if (StringUtils.isEmpty(hexString))
            return null;
        hexString = hexString.toLowerCase();
        final byte[] byteArray = new byte[hexString.length() >> 1];
        int index = 0;
        for (int i = 0; i < hexString.length(); i++) {
            if (index > hexString.length() - 1)
                return byteArray;
            byte highDit = (byte) (Character.digit(hexString.charAt(index), 16) & 0xFF);
            byte lowDit = (byte) (Character.digit(hexString.charAt(index + 1), 16) & 0xFF);
            byteArray[i] = (byte) (highDit << 4 | lowDit);
            index += 2;
        }
        return byteArray;
    }

    /**
     * byte[] to Hex string.
     *
     * @param byteArray the byte array
     * @return the string
     */

    public static String toHexString(byte[] byteArray) {
        if (byteArray == null || byteArray.length <= 0)
            return null;

        final StringBuilder res = new StringBuilder(byteArray.length >> 1);
        String hex;
        for (int i = 0; i < byteArray.length; i++) {
            hex = Integer.toHexString(byteArray[i] & 0xFF);
            if (hex.length() < 2) {
                res.append(0);
            }
            res.append(hex);
        }
        return res.toString();
    }

}
