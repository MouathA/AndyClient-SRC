package com.google.common.primitives;

import com.google.common.base.*;
import com.google.common.annotations.*;
import java.util.*;
import java.io.*;

@GwtCompatible(emulated = true)
public final class Shorts
{
    public static final int BYTES = 2;
    public static final short MAX_POWER_OF_TWO = 16384;
    
    private Shorts() {
    }
    
    public static int hashCode(final short n) {
        return n;
    }
    
    public static short checkedCast(final long n) {
        final short n2 = (short)n;
        if (n2 != n) {
            throw new IllegalArgumentException("Out of range: " + n);
        }
        return n2;
    }
    
    public static short saturatedCast(final long n) {
        if (n > 32767L) {
            return 32767;
        }
        if (n < -32768L) {
            return -32768;
        }
        return (short)n;
    }
    
    public static int compare(final short n, final short n2) {
        return n - n2;
    }
    
    public static boolean contains(final short[] array, final short n) {
        while (0 < array.length) {
            if (array[0] == n) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    public static int indexOf(final short[] array, final short n) {
        return indexOf(array, n, 0, array.length);
    }
    
    private static int indexOf(final short[] array, final short n, final int n2, final int n3) {
        for (int i = n2; i < n3; ++i) {
            if (array[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(final short[] array, final short[] array2) {
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
    
    public static int lastIndexOf(final short[] array, final short n) {
        return lastIndexOf(array, n, 0, array.length);
    }
    
    private static int lastIndexOf(final short[] array, final short n, final int n2, final int n3) {
        for (int i = n3 - 1; i >= n2; --i) {
            if (array[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    public static short min(final short... array) {
        Preconditions.checkArgument(array.length > 0);
        short n = array[0];
        while (1 < array.length) {
            if (array[1] < n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static short max(final short... array) {
        Preconditions.checkArgument(array.length > 0);
        short n = array[0];
        while (1 < array.length) {
            if (array[1] > n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static short[] concat(final short[]... array) {
        final int length = array.length;
        while (0 < 0) {
            final int n = 0 + array[0].length;
            int n2 = 0;
            ++n2;
        }
        final short[] array2 = new short[0];
        while (0 < array.length) {
            final short[] array3 = array[0];
            System.arraycopy(array3, 0, array2, 0, array3.length);
            final int n3 = 0 + array3.length;
            int n4 = 0;
            ++n4;
        }
        return array2;
    }
    
    @GwtIncompatible("doesn't work")
    public static byte[] toByteArray(final short n) {
        return new byte[] { (byte)(n >> 8), (byte)n };
    }
    
    @GwtIncompatible("doesn't work")
    public static short fromByteArray(final byte[] array) {
        Preconditions.checkArgument(array.length >= 2, "array too small: %s < %s", array.length, 2);
        return fromBytes(array[0], array[1]);
    }
    
    @GwtIncompatible("doesn't work")
    public static short fromBytes(final byte b, final byte b2) {
        return (short)(b << 8 | (b2 & 0xFF));
    }
    
    @Beta
    public static Converter stringConverter() {
        return ShortConverter.INSTANCE;
    }
    
    public static short[] ensureCapacity(final short[] array, final int n, final int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return (array.length < n) ? copyOf(array, n + n2) : array;
    }
    
    private static short[] copyOf(final short[] array, final int n) {
        final short[] array2 = new short[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static String join(final String s, final short... array) {
        Preconditions.checkNotNull(s);
        if (array.length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(array.length * 6);
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
    
    public static short[] toArray(final Collection collection) {
        if (collection instanceof ShortArrayAsList) {
            return ((ShortArrayAsList)collection).toShortArray();
        }
        final Object[] array = collection.toArray();
        final int length = array.length;
        final short[] array2 = new short[length];
        while (0 < length) {
            array2[0] = ((Number)Preconditions.checkNotNull(array[0])).shortValue();
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static List asList(final short... array) {
        if (array.length == 0) {
            return Collections.emptyList();
        }
        return new ShortArrayAsList(array);
    }
    
    static int access$000(final short[] array, final short n, final int n2, final int n3) {
        return indexOf(array, n, n2, n3);
    }
    
    static int access$100(final short[] array, final short n, final int n2, final int n3) {
        return lastIndexOf(array, n, n2, n3);
    }
    
    @GwtCompatible
    private static class ShortArrayAsList extends AbstractList implements RandomAccess, Serializable
    {
        final short[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;
        
        ShortArrayAsList(final short[] array) {
            this(array, 0, array.length);
        }
        
        ShortArrayAsList(final short[] array, final int start, final int end) {
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
        public Short get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }
        
        @Override
        public boolean contains(final Object o) {
            return o instanceof Short && Shorts.access$000(this.array, (short)o, this.start, this.end) != -1;
        }
        
        @Override
        public int indexOf(final Object o) {
            if (o instanceof Short) {
                final int access$000 = Shorts.access$000(this.array, (short)o, this.start, this.end);
                if (access$000 >= 0) {
                    return access$000 - this.start;
                }
            }
            return -1;
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            if (o instanceof Short) {
                final int access$100 = Shorts.access$100(this.array, (short)o, this.start, this.end);
                if (access$100 >= 0) {
                    return access$100 - this.start;
                }
            }
            return -1;
        }
        
        public Short set(final int n, final Short n2) {
            Preconditions.checkElementIndex(n, this.size());
            final short n3 = this.array[this.start + n];
            this.array[this.start + n] = (short)Preconditions.checkNotNull(n2);
            return n3;
        }
        
        @Override
        public List subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            return new ShortArrayAsList(this.array, this.start + n, this.start + n2);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof ShortArrayAsList)) {
                return super.equals(o);
            }
            final ShortArrayAsList list = (ShortArrayAsList)o;
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
                final int n = 31 + Shorts.hashCode(this.array[i]);
            }
            return 1;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(this.size() * 6);
            sb.append('[').append(this.array[this.start]);
            for (int i = this.start + 1; i < this.end; ++i) {
                sb.append(", ").append(this.array[i]);
            }
            return sb.append(']').toString();
        }
        
        short[] toShortArray() {
            final int size = this.size();
            final short[] array = new short[size];
            System.arraycopy(this.array, this.start, array, 0, size);
            return array;
        }
        
        @Override
        public Object set(final int n, final Object o) {
            return this.set(n, (Short)o);
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
        
        public int compare(final short[] array, final short[] array2) {
            while (0 < Math.min(array.length, array2.length)) {
                final int compare = Shorts.compare(array[0], array2[0]);
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
            return this.compare((short[])o, (short[])o2);
        }
        
        static {
            $VALUES = new LexicographicalComparator[] { LexicographicalComparator.INSTANCE };
        }
    }
    
    private static final class ShortConverter extends Converter implements Serializable
    {
        static final ShortConverter INSTANCE;
        private static final long serialVersionUID = 1L;
        
        protected Short doForward(final String s) {
            return Short.decode(s);
        }
        
        protected String doBackward(final Short n) {
            return n.toString();
        }
        
        @Override
        public String toString() {
            return "Shorts.stringConverter()";
        }
        
        private Object readResolve() {
            return ShortConverter.INSTANCE;
        }
        
        @Override
        protected Object doBackward(final Object o) {
            return this.doBackward((Short)o);
        }
        
        @Override
        protected Object doForward(final Object o) {
            return this.doForward((String)o);
        }
        
        static {
            INSTANCE = new ShortConverter();
        }
    }
}
