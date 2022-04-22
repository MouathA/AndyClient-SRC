package org.apache.http.util;

public class Asserts
{
    public static void check(final boolean b, final String s) {
        if (!b) {
            throw new IllegalStateException(s);
        }
    }
    
    public static void check(final boolean b, final String s, final Object... array) {
        if (!b) {
            throw new IllegalStateException(String.format(s, array));
        }
    }
    
    public static void notNull(final Object o, final String s) {
        if (o == null) {
            throw new IllegalStateException(s + " is null");
        }
    }
    
    public static void notEmpty(final CharSequence charSequence, final String s) {
        if (TextUtils.isEmpty(charSequence)) {
            throw new IllegalStateException(s + " is empty");
        }
    }
    
    public static void notBlank(final CharSequence charSequence, final String s) {
        if (TextUtils.isBlank(charSequence)) {
            throw new IllegalStateException(s + " is blank");
        }
    }
}
