package com.google.common.primitives;

import com.google.common.base.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import java.io.*;

@GwtCompatible(emulated = true)
public final class Ints
{
    public static final int BYTES = 4;
    public static final int MAX_POWER_OF_TWO = 1073741824;
    private static final byte[] asciiDigits;
    
    private Ints() {
    }
    
    public static int hashCode(final int n) {
        return n;
    }
    
    public static int checkedCast(final long n) {
        final int n2 = (int)n;
        if (n2 != n) {
            throw new IllegalArgumentException("Out of range: " + n);
        }
        return n2;
    }
    
    public static int saturatedCast(final long n) {
        if (n > 2147483647L) {
            return Integer.MAX_VALUE;
        }
        if (n < -2147483648L) {
            return Integer.MIN_VALUE;
        }
        return (int)n;
    }
    
    public static int compare(final int n, final int n2) {
        return (n < n2) ? -1 : ((n > n2) ? 1 : 0);
    }
    
    public static boolean contains(final int[] array, final int n) {
        while (0 < array.length) {
            if (array[0] == n) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    public static int indexOf(final int[] array, final int n) {
        return indexOf(array, n, 0, array.length);
    }
    
    private static int indexOf(final int[] array, final int n, final int n2, final int n3) {
        for (int i = n2; i < n3; ++i) {
            if (array[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(final int[] array, final int[] array2) {
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
    
    public static int lastIndexOf(final int[] array, final int n) {
        return lastIndexOf(array, n, 0, array.length);
    }
    
    private static int lastIndexOf(final int[] array, final int n, final int n2, final int n3) {
        for (int i = n3 - 1; i >= n2; --i) {
            if (array[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    public static int min(final int... array) {
        Preconditions.checkArgument(array.length > 0);
        int n = array[0];
        while (1 < array.length) {
            if (array[1] < n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static int max(final int... array) {
        Preconditions.checkArgument(array.length > 0);
        int n = array[0];
        while (1 < array.length) {
            if (array[1] > n) {
                n = array[1];
            }
            int n2 = 0;
            ++n2;
        }
        return n;
    }
    
    public static int[] concat(final int[]... array) {
        final int length = array.length;
        while (0 < 0) {
            final int n = 0 + array[0].length;
            int n2 = 0;
            ++n2;
        }
        final int[] array2 = new int[0];
        while (0 < array.length) {
            final int[] array3 = array[0];
            System.arraycopy(array3, 0, array2, 0, array3.length);
            final int n3 = 0 + array3.length;
            int n4 = 0;
            ++n4;
        }
        return array2;
    }
    
    @GwtIncompatible("doesn't work")
    public static byte[] toByteArray(final int n) {
        return new byte[] { (byte)(n >> 24), (byte)(n >> 16), (byte)(n >> 8), (byte)n };
    }
    
    @GwtIncompatible("doesn't work")
    public static int fromByteArray(final byte[] array) {
        Preconditions.checkArgument(array.length >= 4, "array too small: %s < %s", array.length, 4);
        return fromBytes(array[0], array[1], array[2], array[3]);
    }
    
    @GwtIncompatible("doesn't work")
    public static int fromBytes(final byte b, final byte b2, final byte b3, final byte b4) {
        return b << 24 | (b2 & 0xFF) << 16 | (b3 & 0xFF) << 8 | (b4 & 0xFF);
    }
    
    @Beta
    public static Converter stringConverter() {
        return IntConverter.INSTANCE;
    }
    
    public static int[] ensureCapacity(final int[] array, final int n, final int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return (array.length < n) ? copyOf(array, n + n2) : array;
    }
    
    private static int[] copyOf(final int[] array, final int n) {
        final int[] array2 = new int[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static String join(final String s, final int... array) {
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
    
    public static int[] toArray(final Collection collection) {
        if (collection instanceof IntArrayAsList) {
            return ((IntArrayAsList)collection).toIntArray();
        }
        final Object[] array = collection.toArray();
        final int length = array.length;
        final int[] array2 = new int[length];
        while (0 < length) {
            array2[0] = ((Number)Preconditions.checkNotNull(array[0])).intValue();
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static List asList(final int... array) {
        if (array.length == 0) {
            return Collections.emptyList();
        }
        return new IntArrayAsList(array);
    }
    
    private static int digit(final char c) {
        return (c < '\u0080') ? Ints.asciiDigits[c] : -1;
    }
    
    @CheckForNull
    @Beta
    @GwtIncompatible("TODO")
    public static Integer tryParse(final String s) {
        return tryParse(s, 10);
    }
    
    @CheckForNull
    @GwtIncompatible("TODO")
    static Integer tryParse(final String s, final int n) {
        if (((String)Preconditions.checkNotNull(s)).isEmpty()) {
            return null;
        }
        if (n < 2 || n > 36) {
            throw new IllegalArgumentException("radix must be between MIN_RADIX and MAX_RADIX but was " + n);
        }
        int i;
        final int n2 = i = ((s.charAt(0) == 45) ? 1 : 0);
        if (i == s.length()) {
            return null;
        }
        final int digit = digit(s.charAt(i++));
        if (digit < 0 || digit >= n) {
            return null;
        }
        int n3 = -digit;
        final int n4 = Integer.MIN_VALUE / n;
        while (i < s.length()) {
            final int digit2 = digit(s.charAt(i++));
            if (digit2 < 0 || digit2 >= n || n3 < n4) {
                return null;
            }
            final int n5 = n3 * n;
            if (n5 < Integer.MIN_VALUE + digit2) {
                return null;
            }
            n3 = n5 - digit2;
        }
        if (n2 != 0) {
            return n3;
        }
        if (n3 == Integer.MIN_VALUE) {
            return null;
        }
        return -n3;
    }
    
    static int access$000(final int[] array, final int n, final int n2, final int n3) {
        return indexOf(array, n, n2, n3);
    }
    
    static int access$100(final int[] array, final int n, final int n2, final int n3) {
        return lastIndexOf(array, n, n2, n3);
    }
    
    static {
        Arrays.fill(asciiDigits = new byte[128], (byte)(-1));
        int n = 0;
        while (0 <= 9) {
            Ints.asciiDigits[48] = 0;
            ++n;
        }
        while (0 <= 26) {
            Ints.asciiDigits[65] = 10;
            Ints.asciiDigits[97] = 10;
            ++n;
        }
    }
    
    @GwtCompatible
    private static class IntArrayAsList extends AbstractList implements RandomAccess, Serializable
    {
        final int[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;
        
        IntArrayAsList(final int[] array) {
            this(array, 0, array.length);
        }
        
        IntArrayAsList(final int[] array, final int start, final int end) {
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
        public Integer get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }
        
        @Override
        public boolean contains(final Object o) {
            return o instanceof Integer && Ints.access$000(this.array, (int)o, this.start, this.end) != -1;
        }
        
        @Override
        public int indexOf(final Object o) {
            if (o instanceof Integer) {
                final int access$000 = Ints.access$000(this.array, (int)o, this.start, this.end);
                if (access$000 >= 0) {
                    return access$000 - this.start;
                }
            }
            return -1;
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            if (o instanceof Integer) {
                final int access$100 = Ints.access$100(this.array, (int)o, this.start, this.end);
                if (access$100 >= 0) {
                    return access$100 - this.start;
                }
            }
            return -1;
        }
        
        public Integer set(final int n, final Integer n2) {
            Preconditions.checkElementIndex(n, this.size());
            final int n3 = this.array[this.start + n];
            this.array[this.start + n] = (int)Preconditions.checkNotNull(n2);
            return n3;
        }
        
        @Override
        public List subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            return new IntArrayAsList(this.array, this.start + n, this.start + n2);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof IntArrayAsList)) {
                return super.equals(o);
            }
            final IntArrayAsList list = (IntArrayAsList)o;
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
                final int n = 31 + Ints.hashCode(this.array[i]);
            }
            return 1;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(this.size() * 5);
            sb.append('[').append(this.array[this.start]);
            for (int i = this.start + 1; i < this.end; ++i) {
                sb.append(", ").append(this.array[i]);
            }
            return sb.append(']').toString();
        }
        
        int[] toIntArray() {
            final int size = this.size();
            final int[] array = new int[size];
            System.arraycopy(this.array, this.start, array, 0, size);
            return array;
        }
        
        @Override
        public Object set(final int n, final Object o) {
            return this.set(n, (Integer)o);
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
        
        public int compare(final int[] array, final int[] array2) {
            while (0 < Math.min(array.length, array2.length)) {
                final int compare = Ints.compare(array[0], array2[0]);
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
            return this.compare((int[])o, (int[])o2);
        }
        
        static {
            $VALUES = new LexicographicalComparator[] { LexicographicalComparator.INSTANCE };
        }
    }
    
    private static final class IntConverter extends Converter implements Serializable
    {
        static final IntConverter INSTANCE;
        private static final long serialVersionUID = 1L;
        
        protected Integer doForward(final String s) {
            return Integer.decode(s);
        }
        
        protected String doBackward(final Integer n) {
            return n.toString();
        }
        
        @Override
        public String toString() {
            return "Ints.stringConverter()";
        }
        
        private Object readResolve() {
            return IntConverter.INSTANCE;
        }
        
        @Override
        protected Object doBackward(final Object o) {
            return this.doBackward((Integer)o);
        }
        
        @Override
        protected Object doForward(final Object o) {
            return this.doForward((String)o);
        }
        
        static {
            INSTANCE = new IntConverter();
        }
    }
}
