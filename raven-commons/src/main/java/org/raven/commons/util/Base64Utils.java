package org.raven.commons.util;

import java.nio.ByteBuffer;
import java.util.Base64;

/**
 *
 */
public class Base64Utils {

    private Base64Utils() {
    }

    private static Base64.Encoder encoder = Base64.getUrlEncoder().withoutPadding();
    private static Base64.Encoder encoderDoPadding = Base64.getUrlEncoder().withoutPadding();
    private static Base64.Decoder decoder = Base64.getUrlDecoder();

    public static byte[] encode(byte[] src) {
        return encode(src, false);
    }

    public static byte[] encode(byte[] src, boolean doPadding) {

        if (doPadding) {
            return encoderDoPadding.encode(src);
        } else {
            return encoder.encode(src);
        }
    }

    public static byte[] encode(long src, boolean doPadding) {

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putLong(src);
        return encode(buffer.array(), doPadding);
    }

    public static byte[] encode(long src) {
        return encode(src, false);
    }

    public static byte[] decode(byte[] src) {

        return decoder.decode(src);
    }

    public static byte[] decode(String src) {

        return decoder.decode(src);
    }

}
