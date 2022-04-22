package com.google.common.primitives;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;
import java.io.*;

@GwtCompatible
public final class Longs
{
    public static final int BYTES = 8;
    public static final long MAX_POWER_OF_TWO = 4611686018427387904L;
    
    private Longs() {
    }
    
    public static int hashCode(final long n) {
        return (int)(n ^ n >>> 32);
    }
    
    public static int compare(final long n, final long n2) {
        return (n < n2) ? -1 : ((n > n2) ? 1 : 0);
    }
    
    public static boolean contains(final long[] array, final long n) {
        while (0 < array.length) {
            if (array[0] == n) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    public static int indexOf(final long[] array, final long n) {
        return indexOf(array, n, 0, array.length);
    }
    
    private static int indexOf(final long[] array, final long n, final int n2, final int n3) {
        for (int i = n2; i < n3; ++i) {
            if (array[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(final long[] array, final long[] array2) {
        Preconditions.checkNotNull(array, (Object)"array");
        Preconditions.checkNotNull(array2, (Object)"target");
        if (array2.length == 0) {
            return 0;
        }
    Label_0021:
        while (0 < array.length - array2.length + 1) {
            while (0 < array2.length) {
                if (array[0] != array2[0]) {
                    int n = 0;
                    ++n;
                    continue Label_0021;
                }
                int n2 = 0;
                ++n2;
            }
            return 0;
        }
        return -1;
    }
    
    public static int lastIndexOf(final long[] array, final long n) {
        return lastIndexOf(array, n, 0, array.length);
    }
    
    private static int lastIndexOf(final long[] array, final long n, final int n2, final int n3) {
        for (int i = n3 - 1; i >= n2; --i) {
            if (array[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    public static long min(final long... array) {
        Preconditions.checkArgument(array.length > 0);
        long n = array[0];
        while (1 < array.length) {
            if (array[1] < n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static long max(final long... array) {
        Preconditions.checkArgument(array.length > 0);
        long n = array[0];
        while (1 < array.length) {
            if (array[1] > n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static long[] concat(final long[]... array) {
        final int length = array.length;
        while (0 < 0) {
            final int n = 0 + array[0].length;
            int n2 = 0;
            ++n2;
        }
        final long[] array2 = new long[0];
        while (0 < array.length) {
            final long[] array3 = array[0];
            System.arraycopy(array3, 0, array2, 0, array3.length);
            final int n3 = 0 + array3.length;
            int n4 = 0;
            ++n4;
        }
        return array2;
    }
    
    public static byte[] toByteArray(long n) {
        final byte[] array = new byte[8];
        while (7 >= 0) {
            array[7] = (byte)(n & 0xFFL);
            n >>= 8;
            int n2 = 0;
            --n2;
        }
        return array;
    }
    
    public static long fromByteArray(final byte[] array) {
        Preconditions.checkArgument(array.length >= 8, "array too small: %s < %s", array.length, 8);
        return fromBytes(array[0], array[1], array[2], array[3], array[4], array[5], array[6], array[7]);
    }
    
    public static long fromBytes(final byte b, final byte b2, final byte b3, final byte b4, final byte b5, final byte b6, final byte b7, final byte b8) {
        return ((long)b & 0xFFL) << 56 | ((long)b2 & 0xFFL) << 48 | ((long)b3 & 0xFFL) << 40 | ((long)b4 & 0xFFL) << 32 | ((long)b5 & 0xFFL) << 24 | ((long)b6 & 0xFFL) << 16 | ((long)b7 & 0xFFL) << 8 | ((long)b8 & 0xFFL);
    }
    
    @Beta
    public static Long tryParse(final String s) {
        if (((String)Preconditions.checkNotNull(s)).isEmpty()) {
            return null;
        }
        int i;
        final int n = i = ((s.charAt(0) == 45) ? 1 : 0);
        if (i == s.length()) {
            return null;
        }
        final int n2 = s.charAt(i++) - '0';
        if (n2 < 0 || n2 > 9) {
            return null;
        }
        long n3 = -n2;
        while (i < s.length()) {
            final int n4 = s.charAt(i++) - '0';
            if (n4 < 0 || n4 > 9 || n3 < -922337203685477580L) {
                return null;
            }
            final long n5 = n3 * 10L;
            if (n5 < Long.MIN_VALUE + n4) {
                return null;
            }
            n3 = n5 - n4;
        }
        if (n != 0) {
            return n3;
        }
        if (n3 == Long.MIN_VALUE) {
            return null;
        }
        return -n3;
    }
    
    @Beta
    public static Converter stringConverter() {
        return LongConverter.INSTANCE;
    }
    
    public static long[] ensureCapacity(final long[] array, final int n, final int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return (array.length < n) ? copyOf(array, n + n2) : array;
    }
    
    private static long[] copyOf(final long[] array, final int n) {
        final long[] array2 = new long[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static String join(final String s, final long... array) {
        Preconditions.checkNotNull(s);
        if (array.length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(array.length * 10);
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
    
    public static long[] toArray(final Collection collection) {
        if (collection instanceof LongArrayAsList) {
            return ((LongArrayAsList)collection).toLongArray();
        }
        final Object[] array = collection.toArray();
        final int length = array.length;
        final long[] array2 = new long[length];
        while (0 < length) {
            array2[0] = ((Number)Preconditions.checkNotNull(array[0])).longValue();
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static List asList(final long... array) {
        if (array.length == 0) {
            return Collections.emptyList();
        }
        return new LongArrayAsList(array);
    }
    
    static int access$000(final long[] array, final long n, final int n2, final int n3) {
        return indexOf(array, n, n2, n3);
    }
    
    static int access$100(final long[] array, final long n, final int n2, final int n3) {
        return lastIndexOf(array, n, n2, n3);
    }
    
    @GwtCompatible
    private static class LongArrayAsList extends AbstractList implements RandomAccess, Serializable
    {
        final long[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;
        
        LongArrayAsList(final long[] array) {
            this(array, 0, array.length);
        }
        
        LongArrayAsList(final long[] array, final int start, final int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }
        
        @Override
        public int size() {
            return this.end - this.start;
        }
        
        @Override
        public boolean isEmpty() {
            return false;
        }
        
        @Override
        public Long get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }
        
        @Override
        public boolean contains(final Object o) {
            return o instanceof Long && Longs.access$000(this.array, (long)o, this.start, this.end) != -1;
        }
        
        @Override
        public int indexOf(final Object o) {
            if (o instanceof Long) {
                final int access$000 = Longs.access$000(this.array, (long)o, this.start, this.end);
                if (access$000 >= 0) {
                    return access$000 - this.start;
                }
            }
            return -1;
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            if (o instanceof Long) {
                final int access$100 = Longs.access$100(this.array, (long)o, this.start, this.end);
                if (access$100 >= 0) {
                    return access$100 - this.start;
                }
            }
            return -1;
        }
        
        public Long set(final int n, final Long n2) {
            Preconditions.checkElementIndex(n, this.size());
            final long n3 = this.array[this.start + n];
            this.array[this.start + n] = (long)Preconditions.checkNotNull(n2);
            return n3;
        }
        
        @Override
        public List subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            return new LongArrayAsList(this.array, this.start + n, this.start + n2);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof LongArrayAsList)) {
                return super.equals(o);
            }
            final LongArrayAsList list = (LongArrayAsList)o;
            final int size = this.size();
            if (list.size() != size) {
                return false;
            }
            while (0 < size) {
                if (this.array[this.start + 0] != list.array[list.start + 0]) {
                    return false;
                }
                int n = 0;
                ++n;
            }
            return true;
        }
        
        @Override
        public int hashCode() {
            for (int i = this.start; i < this.end; ++i) {
                final int n = 31 + Longs.hashCode(this.array[i]);
            }
            return 1;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(this.size() * 10);
            sb.append('[').append(this.array[this.start]);
            for (int i = this.start + 1; i < this.end; ++i) {
                sb.append(", ").append(this.array[i]);
            }
            return sb.append(']').toString();
        }
        
        long[] toLongArray() {
            final int size = this.size();
            final long[] array = new long[size];
            System.arraycopy(this.array, this.start, array, 0, size);
            return array;
        }
        
        @Override
        public Object set(final int n, final Object o) {
            return this.set(n, (Long)o);
        }
        
        @Override
        public Object get(final int n) {
            return this.get(n);
        }
    }
    
    private enum LexicographicalComparator implements Comparator
    {
        INSTANCE("INSTANCE", 0);
        
        private static final LexicographicalComparator[] $VALUES;
        
        private LexicographicalComparator(final String s, final int n) {
        }
        
        public int compare(final long[] array, final long[] array2) {
            while (0 < Math.min(array.length, array2.length)) {
                final int compare = Longs.compare(array[0], array2[0]);
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
            return this.compare((long[])o, (long[])o2);
        }
        
        static {
            $VALUES = new LexicographicalComparator[] { LexicographicalComparator.INSTANCE };
        }
    }
    
    private static final class LongConverter extends Converter implements Serializable
    {
        static final LongConverter INSTANCE;
        private static final long serialVersionUID = 1L;
        
        protected Long doForward(final String s) {
            return Long.decode(s);
        }
        
        protected String doBackward(final Long n) {
            return n.toString();
        }
        
        @Override
        public String toString() {
            return "Longs.stringConverter()";
        }
        
        private Object readResolve() {
            return LongConverter.INSTANCE;
        }
        
        @Override
        protected Object doBackward(final Object o) {
            return this.doBackward((Long)o);
        }
        
        @Override
        protected Object doForward(final Object o) {
            return this.doForward((String)o);
        }
        
        static {
            INSTANCE = new LongConverter();
        }
    }
}
