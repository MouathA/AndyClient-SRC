package org.apache.commons.codec.binary;

import java.nio.charset.*;
import org.apache.commons.codec.*;
import java.io.*;

public class StringUtils
{
    private static byte[] getBytes(final String s, final Charset charset) {
        if (s == null) {
            return null;
        }
        return s.getBytes(charset);
    }
    
    public static byte[] getBytesIso8859_1(final String s) {
        return getBytes(s, Charsets.ISO_8859_1);
    }
    
    public static byte[] getBytesUnchecked(final String s, final String s2) {
        if (s == null) {
            return null;
        }
        return s.getBytes(s2);
    }
    
    public static byte[] getBytesUsAscii(final String s) {
        return getBytes(s, Charsets.US_ASCII);
    }
    
    public static byte[] getBytesUtf16(final String s) {
        return getBytes(s, Charsets.UTF_16);
    }
    
    public static byte[] getBytesUtf16Be(final String s) {
        return getBytes(s, Charsets.UTF_16BE);
    }
    
    public static byte[] getBytesUtf16Le(final String s) {
        return getBytes(s, Charsets.UTF_16LE);
    }
    
    public static byte[] getBytesUtf8(final String s) {
        return getBytes(s, Charsets.UTF_8);
    }
    
    private static IllegalStateException newIllegalStateException(final String s, final UnsupportedEncodingException ex) {
        return new IllegalStateException(s + ": " + ex);
    }
    
    private static String newString(final byte[] array, final Charset charset) {
        return (array == null) ? null : new String(array, charset);
    }
    
    public static String newString(final byte[] array, final String s) {
        if (array == null) {
            return null;
        }
        return new String(array, s);
    }
    
    public static String newStringIso8859_1(final byte[] array) {
        return new String(array, Charsets.ISO_8859_1);
    }
    
    public static String newStringUsAscii(final byte[] array) {
        return new String(array, Charsets.US_ASCII);
    }
    
    public static String newStringUtf16(final byte[] array) {
        return new String(array, Charsets.UTF_16);
    }
    
    public static String newStringUtf16Be(final byte[] array) {
        return new String(array, Charsets.UTF_16BE);
    }
    
    public static String newStringUtf16Le(final byte[] array) {
        return new String(array, Charsets.UTF_16LE);
    }
    
    public static String newStringUtf8(final byte[] array) {
        return newString(array, Charsets.UTF_8);
    }
}
