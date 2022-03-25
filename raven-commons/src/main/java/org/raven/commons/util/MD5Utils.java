//package org.raven.commons.util;
//
//import lombok.NonNull;
//import lombok.extern.slf4j.Slf4j;
//
//import java.security.MessageDigest;
//
///**
// * @author yi.liang
// * @since JDK1.8
// * date 2021.01.13 20:32
// */
//@Slf4j
//public class MD5Utils {
//
//    private MD5Utils() {
//    }
//
//    //混淆码
//    private static final String slat = "JnPIgtx5tZPmXszdzdT6stVOM6ahXe8v";
//
//    /**
//     * @param str string
//     * @return md5 byte[]
//     */
//    public static byte[] encrypt(@NonNull String str) {
//        byte[] s = null;
//        try {
//
//            str = str + slat;
//            MessageDigest m = MessageDigest.getInstance("MD5");
//            m.update(str.getBytes("UTF8"));
//            s = m.digest();
//
//        } catch (Exception ex) {
//            log.error(ex.getMessage(), ex);
//        }
//
//        return s;
//    }
//
//
//    /**
//     * @param str string
//     * @return md5 hexString
//     */
//    public static String encryptToHexString(@NonNull String str) {
//
//        byte[] data = encrypt(str);
//        return HexUtils.toHexString(data);
//    }
//
//}
