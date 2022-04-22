package oshi.util;

import java.math.*;

public abstract class FormatUtil
{
    private static final long kibiByte = 1024L;
    private static final long mebiByte = 1048576L;
    private static final long gibiByte = 1073741824L;
    private static final long tebiByte = 1099511627776L;
    private static final long pebiByte = 1125899906842624L;
    
    public static String formatBytes(final long n) {
        if (n == 1L) {
            return String.format("%d byte", n);
        }
        if (n < 1024L) {
            return String.format("%d bytes", n);
        }
        if (n < 1048576L && n % 1024L == 0L) {
            return String.format("%.0f KB", n / 1024.0);
        }
        if (n < 1048576L) {
            return String.format("%.1f KB", n / 1024.0);
        }
        if (n < 1073741824L && n % 1048576L == 0L) {
            return String.format("%.0f MB", n / 1048576.0);
        }
        if (n < 1073741824L) {
            return String.format("%.1f MB", n / 1048576.0);
        }
        if (n % 1073741824L == 0L && n < 1099511627776L) {
            return String.format("%.0f GB", n / 1.073741824E9);
        }
        if (n < 1099511627776L) {
            return String.format("%.1f GB", n / 1.073741824E9);
        }
        if (n % 1099511627776L == 0L && n < 1125899906842624L) {
            return String.format("%.0f TiB", n / 1.099511627776E12);
        }
        if (n < 1125899906842624L) {
            return String.format("%.1f TiB", n / 1.099511627776E12);
        }
        return String.format("%d bytes", n);
    }
    
    public static float round(final float n, final int n2) {
        return new BigDecimal(Float.toString(n)).setScale(n2, 4).floatValue();
    }
}
