package org.apache.http.util;

public final class LangUtils
{
    public static final int HASH_SEED = 17;
    public static final int HASH_OFFSET = 37;
    
    private LangUtils() {
    }
    
    public static int hashCode(final int n, final int n2) {
        return n * 37 + n2;
    }
    
    public static int hashCode(final int n, final boolean b) {
        return hashCode(n, b ? 1 : 0);
    }
    
    public static int hashCode(final int n, final Object o) {
        return hashCode(n, (o != null) ? o.hashCode() : 0);
    }
    
    public static boolean equals(final Object o, final Object o2) {
        return (o == null) ? (o2 == null) : o.equals(o2);
    }
    
    public static boolean equals(final Object[] array, final Object[] array2) {
        if (array == null) {
            return array2 == null;
        }
        if (array2 != null && array.length == array2.length) {
            while (0 < array.length) {
                if (!equals(array[0], array2[0])) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        return false;
    }
}
