package com.google.common.primitives;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@Beta
@GwtCompatible
public final class UnsignedInts
{
    static final long INT_MASK = 4294967295L;
    
    private UnsignedInts() {
    }
    
    static int flip(final int n) {
        return n ^ Integer.MIN_VALUE;
    }
    
    public static int compare(final int n, final int n2) {
        return Ints.compare(flip(n), flip(n2));
    }
    
    public static long toLong(final int n) {
        return (long)n & 0xFFFFFFFFL;
    }
    
    public static int min(final int... array) {
        Preconditions.checkArgument(array.length > 0);
        int flip = flip(array[0]);
        while (1 < array.length) {
            final int flip2 = flip(array[1]);
            if (flip2 < flip) {
                flip = flip2;
            }
            int n = 0;
            ++n;
        }
        return flip(flip);
    }
    
    public static int max(final int... array) {
        Preconditions.checkArgument(array.length > 0);
        int flip = flip(array[0]);
        while (1 < array.length) {
            final int flip2 = flip(array[1]);
            if (flip2 > flip) {
                flip = flip2;
            }
            int n = 0;
            ++n;
        }
        return flip(flip);
    }
    
    public static String join(final String s, final int... array) {
        Preconditions.checkNotNull(s);
        if (array.length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(array.length * 5);
        sb.append(toString(array[0]));
        while (1 < array.length) {
            sb.append(s).append(toString(array[1]));
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static Comparator lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }
    
    public static int divide(final int n, final int n2) {
        return (int)(toLong(n) / toLong(n2));
    }
    
    public static int remainder(final int n, final int n2) {
        return (int)(toLong(n) % toLong(n2));
    }
    
    public static int decode(final String s) {
        final ParseRequest fromString = ParseRequest.fromString(s);
        return parseUnsignedInt(fromString.rawValue, fromString.radix);
    }
    
    public static int parseUnsignedInt(final String s) {
        return parseUnsignedInt(s, 10);
    }
    
    public static int parseUnsignedInt(final String s, final int n) {
        Preconditions.checkNotNull(s);
        final long long1 = Long.parseLong(s, n);
        if ((long1 & 0xFFFFFFFFL) != long1) {
            throw new NumberFormatException("Input " + s + " in base " + n + " is not in the range of an unsigned integer");
        }
        return (int)long1;
    }
    
    public static String toString(final int n) {
        return toString(n, 10);
    }
    
    public static String toString(final int n, final int n2) {
        return Long.toString((long)n & 0xFFFFFFFFL, n2);
    }
    
    enum LexicographicalComparator implements Comparator
    {
        INSTANCE("INSTANCE", 0);
        
        private static final LexicographicalComparator[] $VALUES;
        
        private LexicographicalComparator(final String s, final int n) {
        }
        
        public int compare(final int[] array, final int[] array2) {
            while (0 < Math.min(array.length, array2.length)) {
                if (array[0] != array2[0]) {
                    return UnsignedInts.compare(array[0], array2[0]);
                }
                int n = 0;
                ++n;
            }
            return array.length - array2.length;
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((int[])o, (int[])o2);
        }
        
        static {
            $VALUES = new LexicographicalComparator[] { LexicographicalComparator.INSTANCE };
        }
    }
}
