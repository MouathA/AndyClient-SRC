package net.minecraft.util;

import java.util.regex.*;

public class StringUtils
{
    private static final Pattern patternControlCode;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00001501";
        patternControlCode = Pattern.compile("(?i)\\u00A7[0-9A-FK-OR]");
    }
    
    public static String ticksToElapsedTime(final int n) {
        final int n2 = n / 20;
        final int n3 = n2 / 60;
        final int n4 = n2 % 60;
        return (n4 < 10) ? (String.valueOf(n3) + ":0" + n4) : (String.valueOf(n3) + ":" + n4);
    }
    
    public static String stripControlCodes(final String s) {
        return StringUtils.patternControlCode.matcher(s).replaceAll("");
    }
    
    public static boolean isNullOrEmpty(final String s) {
        return org.apache.commons.lang3.StringUtils.isEmpty(s);
    }
}
