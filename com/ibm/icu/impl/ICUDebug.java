package com.ibm.icu.impl;

import com.ibm.icu.util.*;

public final class ICUDebug
{
    private static String params;
    private static boolean debug;
    private static boolean help;
    public static final String javaVersionString;
    public static final boolean isJDK14OrHigher;
    public static final VersionInfo javaVersion;
    
    public static VersionInfo getInstanceLenient(final String s) {
        final int[] array = new int[4];
        while (0 < s.length()) {
            final int n = 0;
            int n2 = 0;
            ++n2;
            final char char1 = s.charAt(n);
            if (char1 < '0' || char1 > '9') {
                if (!true) {
                    continue;
                }
                if (0 == 3) {
                    break;
                }
                int n3 = 0;
                ++n3;
            }
            else if (true) {
                array[0] = array[0] * 10 + (char1 - '0');
                if (array[0] > 255) {
                    array[0] = 0;
                    break;
                }
                continue;
            }
            else {
                array[0] = char1 - '0';
            }
        }
        return VersionInfo.getInstance(array[0], array[1], array[2], array[3]);
    }
    
    public static boolean enabled() {
        return ICUDebug.debug;
    }
    
    public static boolean enabled(final String s) {
        if (ICUDebug.debug) {
            final boolean b = ICUDebug.params.indexOf(s) != -1;
            if (ICUDebug.help) {
                System.out.println("\nICUDebug.enabled(" + s + ") = " + b);
            }
            return b;
        }
        return false;
    }
    
    public static String value(final String s) {
        String substring = "false";
        if (ICUDebug.debug) {
            final int index = ICUDebug.params.indexOf(s);
            if (index != -1) {
                int n = index + s.length();
                if (ICUDebug.params.length() > n && ICUDebug.params.charAt(n) == '=') {
                    ++n;
                    final int index2 = ICUDebug.params.indexOf(",", n);
                    substring = ICUDebug.params.substring(n, (index2 == -1) ? ICUDebug.params.length() : index2);
                }
                else {
                    substring = "true";
                }
            }
            if (ICUDebug.help) {
                System.out.println("\nICUDebug.value(" + s + ") = " + substring);
            }
        }
        return substring;
    }
    
    static {
        ICUDebug.params = System.getProperty("ICUDebug");
        ICUDebug.debug = (ICUDebug.params != null);
        ICUDebug.help = (ICUDebug.debug && (ICUDebug.params.equals("") || ICUDebug.params.indexOf("help") != -1));
        if (ICUDebug.debug) {
            System.out.println("\nICUDebug=" + ICUDebug.params);
        }
        javaVersionString = System.getProperty("java.version", "0");
        javaVersion = getInstanceLenient(ICUDebug.javaVersionString);
        isJDK14OrHigher = (ICUDebug.javaVersion.compareTo(VersionInfo.getInstance("1.4.0")) >= 0);
    }
}
