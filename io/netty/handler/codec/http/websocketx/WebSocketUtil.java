package io.netty.handler.codec.http.websocketx;

import java.security.*;
import io.netty.handler.codec.base64.*;
import io.netty.util.*;
import io.netty.buffer.*;

final class WebSocketUtil
{
    static byte[] md5(final byte[] array) {
        return MessageDigest.getInstance("MD5").digest(array);
    }
    
    static byte[] sha1(final byte[] array) {
        return MessageDigest.getInstance("SHA1").digest(array);
    }
    
    static String base64(final byte[] array) {
        final ByteBuf encode = Base64.encode(Unpooled.wrappedBuffer(array));
        final String string = encode.toString(CharsetUtil.UTF_8);
        encode.release();
        return string;
    }
    
    static byte[] randomBytes(final int n) {
        final byte[] array = new byte[n];
        while (0 < n) {
            array[0] = (byte)randomNumber(0, 255);
            int n2 = 0;
            ++n2;
        }
        return array;
    }
    
    static int randomNumber(final int n, final int n2) {
        return (int)(Math.random() * n2 + n);
    }
    
    private WebSocketUtil() {
    }
}
