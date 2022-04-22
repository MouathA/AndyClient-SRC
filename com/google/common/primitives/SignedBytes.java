package com.google.common.primitives;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible
public final class SignedBytes
{
    public static final byte MAX_POWER_OF_TWO = 64;
    
    private SignedBytes() {
    }
    
    public static byte checkedCast(final long n) {
        final byte b = (byte)n;
        if (b != n) {
            throw new IllegalArgumentException("Out of range: " + n);
        }
        return b;
    }
    
    public static byte saturatedCast(final long n) {
        if (n > 127L) {
            return 127;
        }
        if (n < -128L) {
            return -128;
        }
        return (byte)n;
    }
    
    public static int compare(final byte b, final byte b2) {
        return b - b2;
    }
    
    public static byte min(final byte... array) {
        Preconditions.checkArgument(array.length > 0);
        byte b = array[0];
        while (1 < array.length) {
            if (array[1] < b) {
                b = array[1];
            }
            int n = 0;
            ++n;
        }
        return b;
    }
    
    public static byte max(final byte... array) {
        Preconditions.checkArgument(array.length > 0);
        byte b = array[0];
        while (1 < array.length) {
            if (array[1] > b) {
                b = array[1];
            }
            int n = 0;
            ++n;
        }
        return b;
    }
    
    public static String join(final String s, final byte... array) {
        Preconditions.checkNotNull(s);
        if (array.length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(array.length * 5);
        sb.append(array[0]);
        while (1 < array.length) {
            sb.append(s).append(array[1]);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static Comparator lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }
    
    private enum LexicographicalComparator implements Comparator
    {
        INSTANCE("INSTANCE", 0);
        
        private static final LexicographicalComparator[] $VALUES;
        
        private LexicographicalComparator(final String s, final int n) {
        }
        
        public int compare(final byte[] array, final byte[] array2) {
            while (0 < Math.min(array.length, array2.length)) {
                final int compare = SignedBytes.compare(array[0], array2[0]);
                if (compare != 0) {
                    return compare;
                }
                int n = 0;
                ++n;
            }
            return array.length - array2.length;
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((byte[])o, (byte[])o2);
        }
        
        static {
            $VALUES = new LexicographicalComparator[] { LexicographicalComparator.INSTANCE };
        }
    }
}
