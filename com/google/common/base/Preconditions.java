package com.google.common.base;

import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible
public final class Preconditions
{
    private Preconditions() {
    }
    
    public static void checkArgument(final boolean b) {
        if (!b) {
            throw new IllegalArgumentException();
        }
    }
    
    public static void checkArgument(final boolean b, @Nullable final Object o) {
        if (!b) {
            throw new IllegalArgumentException(String.valueOf(o));
        }
    }
    
    public static void checkArgument(final boolean b, @Nullable final String s, @Nullable final Object... array) {
        if (!b) {
            throw new IllegalArgumentException(format(s, array));
        }
    }
    
    public static void checkState(final boolean b) {
        if (!b) {
            throw new IllegalStateException();
        }
    }
    
    public static void checkState(final boolean b, @Nullable final Object o) {
        if (!b) {
            throw new IllegalStateException(String.valueOf(o));
        }
    }
    
    public static void checkState(final boolean b, @Nullable final String s, @Nullable final Object... array) {
        if (!b) {
            throw new IllegalStateException(format(s, array));
        }
    }
    
    public static Object checkNotNull(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        return o;
    }
    
    public static Object checkNotNull(final Object o, @Nullable final Object o2) {
        if (o == null) {
            throw new NullPointerException(String.valueOf(o2));
        }
        return o;
    }
    
    public static Object checkNotNull(final Object o, @Nullable final String s, @Nullable final Object... array) {
        if (o == null) {
            throw new NullPointerException(format(s, array));
        }
        return o;
    }
    
    public static int checkElementIndex(final int n, final int n2) {
        return checkElementIndex(n, n2, "index");
    }
    
    public static int checkElementIndex(final int n, final int n2, @Nullable final String s) {
        if (n < 0 || n >= n2) {
            throw new IndexOutOfBoundsException(badElementIndex(n, n2, s));
        }
        return n;
    }
    
    private static String badElementIndex(final int n, final int n2, final String s) {
        if (n < 0) {
            return format("%s (%s) must not be negative", s, n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("negative size: " + n2);
        }
        return format("%s (%s) must be less than size (%s)", s, n, n2);
    }
    
    public static int checkPositionIndex(final int n, final int n2) {
        return checkPositionIndex(n, n2, "index");
    }
    
    public static int checkPositionIndex(final int n, final int n2, @Nullable final String s) {
        if (n < 0 || n > n2) {
            throw new IndexOutOfBoundsException(badPositionIndex(n, n2, s));
        }
        return n;
    }
    
    private static String badPositionIndex(final int n, final int n2, final String s) {
        if (n < 0) {
            return format("%s (%s) must not be negative", s, n);
        }
        if (n2 < 0) {
            throw new IllegalArgumentException("negative size: " + n2);
        }
        return format("%s (%s) must not be greater than size (%s)", s, n, n2);
    }
    
    public static void checkPositionIndexes(final int n, final int n2, final int n3) {
        if (n < 0 || n2 < n || n2 > n3) {
            throw new IndexOutOfBoundsException(badPositionIndexes(n, n2, n3));
        }
    }
    
    private static String badPositionIndexes(final int n, final int n2, final int n3) {
        if (n < 0 || n > n3) {
            return badPositionIndex(n, n3, "start index");
        }
        if (n2 < 0 || n2 > n3) {
            return badPositionIndex(n2, n3, "end index");
        }
        return format("end index (%s) must not be less than start index (%s)", n2, n);
    }
    
    static String format(String value, @Nullable final Object... array) {
        value = String.valueOf(value);
        final StringBuilder sb = new StringBuilder(value.length() + 16 * array.length);
        int n2 = 0;
        while (0 < array.length) {
            final int index = value.indexOf("%s", 0);
            if (index == -1) {
                break;
            }
            sb.append(value.substring(0, index));
            final StringBuilder sb2 = sb;
            final int n = 0;
            ++n2;
            sb2.append(array[n]);
        }
        sb.append(value.substring(0));
        if (0 < array.length) {
            sb.append(" [");
            final StringBuilder sb3 = sb;
            final int n3 = 0;
            ++n2;
            sb3.append(array[n3]);
            while (0 < array.length) {
                sb.append(", ");
                final StringBuilder sb4 = sb;
                final int n4 = 0;
                ++n2;
                sb4.append(array[n4]);
            }
            sb.append(']');
        }
        return sb.toString();
    }
}
