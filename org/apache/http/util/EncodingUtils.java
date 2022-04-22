package org.apache.http.util;

import org.apache.http.*;

public final class EncodingUtils
{
    public static String getString(final byte[] array, final int n, final int n2, final String s) {
        Args.notNull(array, "Input");
        Args.notEmpty(s, "Charset");
        return new String(array, n, n2, s);
    }
    
    public static String getString(final byte[] array, final String s) {
        Args.notNull(array, "Input");
        return getString(array, 0, array.length, s);
    }
    
    public static byte[] getBytes(final String s, final String s2) {
        Args.notNull(s, "Input");
        Args.notEmpty(s2, "Charset");
        return s.getBytes(s2);
    }
    
    public static byte[] getAsciiBytes(final String s) {
        Args.notNull(s, "Input");
        return s.getBytes(Consts.ASCII.name());
    }
    
    public static String getAsciiString(final byte[] array, final int n, final int n2) {
        Args.notNull(array, "Input");
        return new String(array, n, n2, Consts.ASCII.name());
    }
    
    public static String getAsciiString(final byte[] array) {
        Args.notNull(array, "Input");
        return getAsciiString(array, 0, array.length);
    }
    
    private EncodingUtils() {
    }
}
