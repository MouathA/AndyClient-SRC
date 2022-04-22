package org.apache.http.util;

import java.util.*;

public class Args
{
    public static void check(final boolean b, final String s) {
        if (!b) {
            throw new IllegalArgumentException(s);
        }
    }
    
    public static void check(final boolean b, final String s, final Object... array) {
        if (!b) {
            throw new IllegalArgumentException(String.format(s, array));
        }
    }
    
    public static Object notNull(final Object o, final String s) {
        if (o == null) {
            throw new IllegalArgumentException(s + " may not be null");
        }
        return o;
    }
    
    public static CharSequence notEmpty(final CharSequence charSequence, final String s) {
        if (charSequence == null) {
            throw new IllegalArgumentException(s + " may not be null");
        }
        if (TextUtils.isEmpty(charSequence)) {
            throw new IllegalArgumentException(s + " may not be empty");
        }
        return charSequence;
    }
    
    public static CharSequence notBlank(final CharSequence charSequence, final String s) {
        if (charSequence == null) {
            throw new IllegalArgumentException(s + " may not be null");
        }
        if (TextUtils.isBlank(charSequence)) {
            throw new IllegalArgumentException(s + " may not be blank");
        }
        return charSequence;
    }
    
    public static Collection notEmpty(final Collection collection, final String s) {
        if (collection == null) {
            throw new IllegalArgumentException(s + " may not be null");
        }
        if (collection.isEmpty()) {
            throw new IllegalArgumentException(s + " may not be empty");
        }
        return collection;
    }
    
    public static int positive(final int n, final String s) {
        if (n <= 0) {
            throw new IllegalArgumentException(s + " may not be negative or zero");
        }
        return n;
    }
    
    public static long positive(final long n, final String s) {
        if (n <= 0L) {
            throw new IllegalArgumentException(s + " may not be negative or zero");
        }
        return n;
    }
    
    public static int notNegative(final int n, final String s) {
        if (n < 0) {
            throw new IllegalArgumentException(s + " may not be negative");
        }
        return n;
    }
    
    public static long notNegative(final long n, final String s) {
        if (n < 0L) {
            throw new IllegalArgumentException(s + " may not be negative");
        }
        return n;
    }
}
