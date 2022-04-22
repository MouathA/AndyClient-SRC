package org.apache.commons.codec.digest;

import org.apache.commons.codec.*;

public class Crypt
{
    public static String crypt(final byte[] array) {
        return crypt(array, null);
    }
    
    public static String crypt(final byte[] array, final String s) {
        if (s == null) {
            return Sha2Crypt.sha512Crypt(array);
        }
        if (s.startsWith("$6$")) {
            return Sha2Crypt.sha512Crypt(array, s);
        }
        if (s.startsWith("$5$")) {
            return Sha2Crypt.sha256Crypt(array, s);
        }
        if (s.startsWith("$1$")) {
            return Md5Crypt.md5Crypt(array, s);
        }
        return UnixCrypt.crypt(array, s);
    }
    
    public static String crypt(final String s) {
        return crypt(s, null);
    }
    
    public static String crypt(final String s, final String s2) {
        return crypt(s.getBytes(Charsets.UTF_8), s2);
    }
}
