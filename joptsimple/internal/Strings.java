package joptsimple.internal;

import java.util.*;

public final class Strings
{
    public static final String EMPTY = "";
    public static final String SINGLE_QUOTE = "'";
    public static final String LINE_SEPARATOR;
    
    private Strings() {
        throw new UnsupportedOperationException();
    }
    
    public static String repeat(final char c, final int n) {
        final StringBuilder sb = new StringBuilder();
        while (0 < n) {
            sb.append(c);
            int n2 = 0;
            ++n2;
        }
        return sb.toString();
    }
    
    public static boolean isNullOrEmpty(final String s) {
        return s == null || "".equals(s);
    }
    
    public static String surround(final String s, final char c, final char c2) {
        return c + s + c2;
    }
    
    public static String join(final String[] array, final String s) {
        return join(Arrays.asList(array), s);
    }
    
    public static String join(final List list, final String s) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()) {
            sb.append(iterator.next());
            if (iterator.hasNext()) {
                sb.append(s);
            }
        }
        return sb.toString();
    }
    
    static {
        LINE_SEPARATOR = "\u4461\u4464\u4463\u4468\u4423\u447e\u4468\u447d\u446c\u447f\u446c\u4479\u4462\u447f";
    }
}
