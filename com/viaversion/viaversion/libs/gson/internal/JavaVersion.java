package com.viaversion.viaversion.libs.gson.internal;

public final class JavaVersion
{
    private static final int majorJavaVersion;
    
    private static int determineMajorJavaVersion() {
        return getMajorJavaVersion(System.getProperty("java.version"));
    }
    
    static int getMajorJavaVersion(final String s) {
        int n = parseDotted(s);
        if (n == -1) {
            n = extractBeginningInt(s);
        }
        if (n == -1) {
            return 6;
        }
        return n;
    }
    
    private static int parseDotted(final String s) {
        final String[] split = s.split("[._]");
        final int int1 = Integer.parseInt(split[0]);
        if (int1 == 1 && split.length > 1) {
            return Integer.parseInt(split[1]);
        }
        return int1;
    }
    
    private static int extractBeginningInt(final String s) {
        final StringBuilder sb = new StringBuilder();
        while (0 < s.length()) {
            final char char1 = s.charAt(0);
            if (!Character.isDigit(char1)) {
                break;
            }
            sb.append(char1);
            int n = 0;
            ++n;
        }
        return Integer.parseInt(sb.toString());
    }
    
    public static int getMajorJavaVersion() {
        return JavaVersion.majorJavaVersion;
    }
    
    public static boolean isJava9OrLater() {
        return JavaVersion.majorJavaVersion >= 9;
    }
    
    private JavaVersion() {
    }
    
    static {
        majorJavaVersion = determineMajorJavaVersion();
    }
}
