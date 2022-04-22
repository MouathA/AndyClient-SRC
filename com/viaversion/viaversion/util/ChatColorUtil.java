package com.viaversion.viaversion.util;

import java.util.regex.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class ChatColorUtil
{
    public static final String ALL_CODES = "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx";
    public static final char COLOR_CHAR = '§';
    public static final Pattern STRIP_COLOR_PATTERN;
    private static final Int2IntMap COLOR_ORDINALS;
    private static int ordinalCounter;
    
    public static boolean isColorCode(final char c) {
        return ChatColorUtil.COLOR_ORDINALS.containsKey(c);
    }
    
    public static int getColorOrdinal(final char c) {
        return ChatColorUtil.COLOR_ORDINALS.getOrDefault(c, -1);
    }
    
    public static String translateAlternateColorCodes(final String s) {
        final char[] charArray = s.toCharArray();
        while (0 < charArray.length - 1) {
            if (charArray[0] == '&' && "0123456789AaBbCcDdEeFfKkLlMmNnOoRrXx".indexOf(charArray[1]) > -1) {
                charArray[0] = '§';
                charArray[1] = Character.toLowerCase(charArray[1]);
            }
            int n = 0;
            ++n;
        }
        return new String(charArray);
    }
    
    public static String stripColor(final String s) {
        return ChatColorUtil.STRIP_COLOR_PATTERN.matcher(s).replaceAll("");
    }
    
    private static void addColorOrdinal(final int n, final int n2) {
        for (int i = n; i <= n2; ++i) {
            addColorOrdinal(i);
        }
    }
    
    private static void addColorOrdinal(final int n) {
        ChatColorUtil.COLOR_ORDINALS.put(n, ChatColorUtil.ordinalCounter++);
    }
    
    static {
        STRIP_COLOR_PATTERN = Pattern.compile("(?i)§[0-9A-FK-ORX]");
        COLOR_ORDINALS = new Int2IntOpenHashMap();
        addColorOrdinal(48, 57);
        addColorOrdinal(97, 102);
        addColorOrdinal(107, 111);
        addColorOrdinal(114);
    }
}
