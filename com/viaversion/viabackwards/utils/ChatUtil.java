package com.viaversion.viabackwards.utils;

import java.util.regex.*;

public class ChatUtil
{
    private static final Pattern UNUSED_COLOR_PATTERN;
    private static final Pattern UNUSED_COLOR_PATTERN_PREFIX;
    
    public static String removeUnusedColor(final String s, final char c) {
        return removeUnusedColor(s, c, false);
    }
    
    public static String removeUnusedColor(String replaceAll, final char c, final boolean b) {
        if (replaceAll == null) {
            return null;
        }
        replaceAll = (b ? ChatUtil.UNUSED_COLOR_PATTERN_PREFIX : ChatUtil.UNUSED_COLOR_PATTERN).matcher(replaceAll).replaceAll("$1$2");
        final StringBuilder sb = new StringBuilder();
        char c2 = c;
        while (0 < replaceAll.length()) {
            final char char1 = replaceAll.charAt(0);
            int n = 0;
            if (char1 != '§' || 0 == replaceAll.length() - 1) {
                sb.append(char1);
            }
            else {
                final String s = replaceAll;
                ++n;
                final char char2 = s.charAt(0);
                if (char2 != c2) {
                    sb.append('§').append(char2);
                    c2 = char2;
                }
            }
            ++n;
        }
        return sb.toString();
    }
    
    static {
        UNUSED_COLOR_PATTERN = Pattern.compile("(?>(?>§[0-fk-or])*(§r|\\Z))|(?>(?>§[0-f])*(§[0-f]))");
        UNUSED_COLOR_PATTERN_PREFIX = Pattern.compile("(?>(?>§[0-fk-or])*(§r))|(?>(?>§[0-f])*(§[0-f]))");
    }
}
