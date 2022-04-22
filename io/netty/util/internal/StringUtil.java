package io.netty.util.internal;

import java.util.*;

public final class StringUtil
{
    public static final String NEWLINE;
    private static final String[] BYTE2HEX_NOPAD;
    private static final String EMPTY_STRING = "";
    static final boolean $assertionsDisabled;
    
    public static String[] split(final String s, final char c) {
        final int length = s.length();
        final ArrayList<String> list = new ArrayList<String>();
        while (0 < length) {
            if (s.charAt(0) == c) {
                list.add("");
            }
            int n = 0;
            ++n;
        }
        list.add(s);
        return list.toArray(new String[list.size()]);
    }
    
    public static String byteToHexStringPadded(final int n) {
        return StringUtil.BYTE2HEX_PAD[n & 0xFF];
    }
    
    public static Appendable byteToHexStringPadded(final Appendable appendable, final int n) {
        appendable.append(byteToHexStringPadded(n));
        return appendable;
    }
    
    public static String toHexStringPadded(final byte[] array) {
        return toHexStringPadded(array, 0, array.length);
    }
    
    public static String toHexStringPadded(final byte[] array, final int n, final int n2) {
        return ((StringBuilder)toHexStringPadded(new StringBuilder(n2 << 1), array, n, n2)).toString();
    }
    
    public static Appendable toHexStringPadded(final Appendable appendable, final byte[] array) {
        return toHexStringPadded(appendable, array, 0, array.length);
    }
    
    public static Appendable toHexStringPadded(final Appendable appendable, final byte[] array, final int n, final int n2) {
        for (int n3 = n + n2, i = n; i < n3; ++i) {
            byteToHexStringPadded(appendable, array[i]);
        }
        return appendable;
    }
    
    public static String byteToHexString(final int n) {
        return StringUtil.BYTE2HEX_NOPAD[n & 0xFF];
    }
    
    public static Appendable byteToHexString(final Appendable appendable, final int n) {
        appendable.append(byteToHexString(n));
        return appendable;
    }
    
    public static String toHexString(final byte[] array) {
        return toHexString(array, 0, array.length);
    }
    
    public static String toHexString(final byte[] array, final int n, final int n2) {
        return ((StringBuilder)toHexString(new StringBuilder(n2 << 1), array, n, n2)).toString();
    }
    
    public static Appendable toHexString(final Appendable appendable, final byte[] array) {
        return toHexString(appendable, array, 0, array.length);
    }
    
    public static Appendable toHexString(final Appendable appendable, final byte[] array, final int n, final int n2) {
        assert n2 >= 0;
        if (n2 == 0) {
            return appendable;
        }
        final int n3 = n + n2;
        int n4;
        int n5;
        for (n4 = n3 - 1, n5 = n; n5 < n4 && array[n5] == 0; ++n5) {}
        byteToHexString(appendable, array[n5++]);
        toHexStringPadded(appendable, array, n5, n3 - n5);
        return appendable;
    }
    
    public static String simpleClassName(final Object o) {
        if (o == null) {
            return "null_object";
        }
        return simpleClassName(o.getClass());
    }
    
    public static String simpleClassName(final Class clazz) {
        if (clazz == null) {
            return "null_class";
        }
        final Package package1 = clazz.getPackage();
        if (package1 != null) {
            return clazz.getName().substring(package1.getName().length() + 1);
        }
        return clazz.getName();
    }
    
    private StringUtil() {
    }
    
    static {
        $assertionsDisabled = !StringUtil.class.desiredAssertionStatus();
        StringUtil.BYTE2HEX_PAD = new String[256];
        BYTE2HEX_NOPAD = new String[256];
        NEWLINE = new Formatter().format("%n", new Object[0]).toString();
        while (true) {
            final StringBuilder sb = new StringBuilder(2);
            sb.append('0');
            sb.append(0);
            StringUtil.BYTE2HEX_PAD[0] = sb.toString();
            StringUtil.BYTE2HEX_NOPAD[0] = String.valueOf(0);
            int n = 0;
            ++n;
        }
    }
}
