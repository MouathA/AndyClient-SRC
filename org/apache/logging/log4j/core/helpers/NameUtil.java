package org.apache.logging.log4j.core.helpers;

import java.security.*;

public final class NameUtil
{
    private static final int MASK = 255;
    
    private NameUtil() {
    }
    
    public static String getSubName(final String s) {
        if (s.isEmpty()) {
            return null;
        }
        final int lastIndex = s.lastIndexOf(46);
        return (lastIndex > 0) ? s.substring(0, lastIndex) : "";
    }
    
    public static String md5(final String s) {
        final MessageDigest instance = MessageDigest.getInstance("MD5");
        instance.update(s.getBytes());
        final byte[] digest = instance.digest();
        final StringBuilder sb = new StringBuilder();
        final byte[] array = digest;
        while (0 < array.length) {
            final String hexString = Integer.toHexString(0xFF & array[0]);
            if (hexString.length() == 1) {
                sb.append('0');
            }
            sb.append(hexString);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
}
