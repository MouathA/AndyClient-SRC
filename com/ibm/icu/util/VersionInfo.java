package com.ibm.icu.util;

import java.util.concurrent.*;

public final class VersionInfo implements Comparable
{
    public static final VersionInfo UNICODE_1_0;
    public static final VersionInfo UNICODE_1_0_1;
    public static final VersionInfo UNICODE_1_1_0;
    public static final VersionInfo UNICODE_1_1_5;
    public static final VersionInfo UNICODE_2_0;
    public static final VersionInfo UNICODE_2_1_2;
    public static final VersionInfo UNICODE_2_1_5;
    public static final VersionInfo UNICODE_2_1_8;
    public static final VersionInfo UNICODE_2_1_9;
    public static final VersionInfo UNICODE_3_0;
    public static final VersionInfo UNICODE_3_0_1;
    public static final VersionInfo UNICODE_3_1_0;
    public static final VersionInfo UNICODE_3_1_1;
    public static final VersionInfo UNICODE_3_2;
    public static final VersionInfo UNICODE_4_0;
    public static final VersionInfo UNICODE_4_0_1;
    public static final VersionInfo UNICODE_4_1;
    public static final VersionInfo UNICODE_5_0;
    public static final VersionInfo UNICODE_5_1;
    public static final VersionInfo UNICODE_5_2;
    public static final VersionInfo UNICODE_6_0;
    public static final VersionInfo UNICODE_6_1;
    public static final VersionInfo UNICODE_6_2;
    public static final VersionInfo ICU_VERSION;
    @Deprecated
    public static final String ICU_DATA_VERSION_PATH = "51b";
    @Deprecated
    public static final VersionInfo ICU_DATA_VERSION;
    public static final VersionInfo UCOL_RUNTIME_VERSION;
    public static final VersionInfo UCOL_BUILDER_VERSION;
    public static final VersionInfo UCOL_TAILORINGS_VERSION;
    private static VersionInfo javaVersion;
    private static final VersionInfo UNICODE_VERSION;
    private int m_version_;
    private static final ConcurrentHashMap MAP_;
    private static final int LAST_BYTE_MASK_ = 255;
    private static final String INVALID_VERSION_NUMBER_ = "Invalid version number: Version number may be negative or greater than 255";
    
    public static VersionInfo getInstance(final String s) {
        final int length = s.length();
        final int[] array = { 0, 0, 0, 0 };
        int n = 0;
        while (0 < length) {
            s.charAt(0);
            n = (char)(-48);
            final int[] array2 = array;
            final int n2 = 0;
            array2[n2] *= 10;
            final int[] array3 = array;
            final int n3 = 0;
            array3[n3] += 0;
            int n4 = 0;
            ++n4;
        }
        if (length != 0) {
            throw new IllegalArgumentException("Invalid version number: String '" + s + "' exceeds version format");
        }
        while (array[0] >= 0 && array[0] <= 255) {
            ++n;
        }
        throw new IllegalArgumentException("Invalid version number: Version number may be negative or greater than 255");
    }
    
    public static VersionInfo getInstance(final int n, final int n2, final int n3, final int n4) {
        if (n < 0 || n > 255 || n2 < 0 || n2 > 255 || n3 < 0 || n3 > 255 || n4 < 0 || n4 > 255) {
            throw new IllegalArgumentException("Invalid version number: Version number may be negative or greater than 255");
        }
        final int int1 = getInt(n, n2, n3, n4);
        final Integer value = int1;
        VersionInfo versionInfo = VersionInfo.MAP_.get(value);
        if (versionInfo == null) {
            versionInfo = new VersionInfo(int1);
            final VersionInfo versionInfo2 = VersionInfo.MAP_.putIfAbsent(value, versionInfo);
            if (versionInfo2 != null) {
                versionInfo = versionInfo2;
            }
        }
        return versionInfo;
    }
    
    public static VersionInfo getInstance(final int n, final int n2, final int n3) {
        return getInstance(n, n2, n3, 0);
    }
    
    public static VersionInfo getInstance(final int n, final int n2) {
        return getInstance(n, n2, 0, 0);
    }
    
    public static VersionInfo getInstance(final int n) {
        return getInstance(n, 0, 0, 0);
    }
    
    @Deprecated
    public static VersionInfo javaVersion() {
        if (VersionInfo.javaVersion == null) {
            final Class<VersionInfo> clazz = VersionInfo.class;
            final Class<VersionInfo> clazz2 = VersionInfo.class;
            // monitorenter(clazz)
            if (VersionInfo.javaVersion == null) {
                final char[] charArray = System.getProperty("java.version").toCharArray();
                while (0 < charArray.length) {
                    final char[] array = charArray;
                    final int n = 0;
                    int n2 = 0;
                    ++n2;
                    final char c = array[n];
                    if (c < '0' || c > '9') {
                        final char[] array2 = charArray;
                        final int n3 = 0;
                        int n4 = 0;
                        ++n4;
                        array2[n3] = '.';
                        int n5 = 0;
                        ++n5;
                    }
                    else {
                        final char[] array3 = charArray;
                        final int n6 = 0;
                        int n4 = 0;
                        ++n4;
                        array3[n6] = c;
                    }
                }
                VersionInfo.javaVersion = getInstance(new String(charArray, 0, 0));
            }
        }
        // monitorexit(clazz2)
        return VersionInfo.javaVersion;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(7);
        sb.append(this.getMajor());
        sb.append('.');
        sb.append(this.getMinor());
        sb.append('.');
        sb.append(this.getMilli());
        sb.append('.');
        sb.append(this.getMicro());
        return sb.toString();
    }
    
    public int getMajor() {
        return this.m_version_ >> 24 & 0xFF;
    }
    
    public int getMinor() {
        return this.m_version_ >> 16 & 0xFF;
    }
    
    public int getMilli() {
        return this.m_version_ >> 8 & 0xFF;
    }
    
    public int getMicro() {
        return this.m_version_ & 0xFF;
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this;
    }
    
    public int compareTo(final VersionInfo versionInfo) {
        return this.m_version_ - versionInfo.m_version_;
    }
    
    private VersionInfo(final int version_) {
        this.m_version_ = version_;
    }
    
    private static int getInt(final int n, final int n2, final int n3, final int n4) {
        return n << 24 | n2 << 16 | n3 << 8 | n4;
    }
    
    public static void main(final String[] array) {
        String s;
        if (VersionInfo.ICU_VERSION.getMajor() <= 4) {
            if (VersionInfo.ICU_VERSION.getMinor() % 2 != 0) {
                int major = VersionInfo.ICU_VERSION.getMajor();
                int n = VersionInfo.ICU_VERSION.getMinor() + 1;
                if (n >= 10) {
                    n -= 10;
                    ++major;
                }
                s = "" + major + "." + n + "M" + VersionInfo.ICU_VERSION.getMilli();
            }
            else {
                s = VersionInfo.ICU_VERSION.getVersionString(2, 2);
            }
        }
        else if (VersionInfo.ICU_VERSION.getMinor() == 0) {
            s = "" + VersionInfo.ICU_VERSION.getMajor() + "M" + VersionInfo.ICU_VERSION.getMilli();
        }
        else {
            s = VersionInfo.ICU_VERSION.getVersionString(2, 2);
        }
        System.out.println("International Components for Unicode for Java " + s);
        System.out.println("");
        System.out.println("Implementation Version: " + VersionInfo.ICU_VERSION.getVersionString(2, 4));
        System.out.println("Unicode Data Version:   " + VersionInfo.UNICODE_VERSION.getVersionString(2, 4));
        System.out.println("CLDR Data Version:      " + LocaleData.getCLDRVersion().getVersionString(2, 4));
        System.out.println("Time Zone Data Version: " + TimeZone.getTZDataVersion());
    }
    
    private String getVersionString(final int n, final int n2) {
        if (n < 1 || n2 < 1 || n > 4 || n2 > 4 || n > n2) {
            throw new IllegalArgumentException("Invalid min/maxDigits range");
        }
        int[] array;
        int n3;
        for (array = new int[] { this.getMajor(), this.getMinor(), this.getMilli(), this.getMicro() }, n3 = n2; n3 > n && array[n3 - 1] == 0; --n3) {}
        final StringBuilder sb = new StringBuilder(7);
        sb.append(array[0]);
        while (1 < n3) {
            sb.append(".");
            sb.append(array[1]);
            int n4 = 0;
            ++n4;
        }
        return sb.toString();
    }
    
    public int compareTo(final Object o) {
        return this.compareTo((VersionInfo)o);
    }
    
    static {
        MAP_ = new ConcurrentHashMap();
        UNICODE_1_0 = getInstance(1, 0, 0, 0);
        UNICODE_1_0_1 = getInstance(1, 0, 1, 0);
        UNICODE_1_1_0 = getInstance(1, 1, 0, 0);
        UNICODE_1_1_5 = getInstance(1, 1, 5, 0);
        UNICODE_2_0 = getInstance(2, 0, 0, 0);
        UNICODE_2_1_2 = getInstance(2, 1, 2, 0);
        UNICODE_2_1_5 = getInstance(2, 1, 5, 0);
        UNICODE_2_1_8 = getInstance(2, 1, 8, 0);
        UNICODE_2_1_9 = getInstance(2, 1, 9, 0);
        UNICODE_3_0 = getInstance(3, 0, 0, 0);
        UNICODE_3_0_1 = getInstance(3, 0, 1, 0);
        UNICODE_3_1_0 = getInstance(3, 1, 0, 0);
        UNICODE_3_1_1 = getInstance(3, 1, 1, 0);
        UNICODE_3_2 = getInstance(3, 2, 0, 0);
        UNICODE_4_0 = getInstance(4, 0, 0, 0);
        UNICODE_4_0_1 = getInstance(4, 0, 1, 0);
        UNICODE_4_1 = getInstance(4, 1, 0, 0);
        UNICODE_5_0 = getInstance(5, 0, 0, 0);
        UNICODE_5_1 = getInstance(5, 1, 0, 0);
        UNICODE_5_2 = getInstance(5, 2, 0, 0);
        UNICODE_6_0 = getInstance(6, 0, 0, 0);
        UNICODE_6_1 = getInstance(6, 1, 0, 0);
        UNICODE_6_2 = getInstance(6, 2, 0, 0);
        ICU_VERSION = getInstance(51, 2, 0, 0);
        ICU_DATA_VERSION = getInstance(51, 2, 0, 0);
        UNICODE_VERSION = VersionInfo.UNICODE_6_2;
        UCOL_RUNTIME_VERSION = getInstance(7);
        UCOL_BUILDER_VERSION = getInstance(8);
        UCOL_TAILORINGS_VERSION = getInstance(1);
    }
}
