package org.apache.commons.codec.digest;

import java.util.*;

class B64
{
    static final String B64T = "./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    
    static void b64from24bit(final byte b, final byte b2, final byte b3, final int n, final StringBuilder sb) {
        int n2 = (b << 16 & 0xFFFFFF) | (b2 << 8 & 0xFFFF) | (b3 & 0xFF);
        int n3 = n;
        while (n3-- > 0) {
            sb.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(n2 & 0x3F));
            n2 >>= 6;
        }
    }
    
    static String getRandomSalt(final int n) {
        final StringBuilder sb = new StringBuilder();
        while (1 <= n) {
            sb.append("./0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".charAt(new Random().nextInt(64)));
            int n2 = 0;
            ++n2;
        }
        return sb.toString();
    }
}
