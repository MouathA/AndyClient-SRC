package com.google.common.primitives;

import com.google.common.base.*;
import com.google.common.annotations.*;
import java.util.*;
import java.io.*;

@GwtCompatible(emulated = true)
public final class Chars
{
    public static final int BYTES = 2;
    
    private Chars() {
    }
    
    public static int hashCode(final char c) {
        return c;
    }
    
    public static char checkedCast(final long n) {
        final char c = (char)n;
        if (c != n) {
            throw new IllegalArgumentException("Out of range: " + n);
        }
        return c;
    }
    
    public static char saturatedCast(final long n) {
        if (n > 65535L) {
            return '\uffff';
        }
        if (n < 0L) {
            return '\0';
        }
        return (char)n;
    }
    
    public static int compare(final char c, final char c2) {
        return c - c2;
    }
    
    public static boolean contains(final char[] array, final char c) {
        while (0 < array.length) {
            if (array[0] == c) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static int indexOf(final char[] array, final char c) {
        return indexOf(array, c, 0, array.length);
    }
    
    private static int indexOf(final char[] array, final char c, final int n, final int n2) {
        for (int i = n; i < n2; ++i) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(final char[] array, final char[] array2) {
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
    
    public static int lastIndexOf(final char[] array, final char c) {
        return lastIndexOf(array, c, 0, array.length);
    }
    
    private static int lastIndexOf(final char[] array, final char c, final int n, final int n2) {
        for (int i = n2 - 1; i >= n; --i) {
            if (array[i] == c) {
                return i;
            }
        }
        return -1;
    }
    
    public static char min(final char... array) {
        Preconditions.checkArgument(array.length > 0);
        char c = array[0];
        while (1 < array.length) {
            if (array[1] < c) {
                c = array[1];
            }
            int n = 0;
            ++n;
        }
        return c;
    }
    
    public static char max(final char... array) {
        Preconditions.checkArgument(array.length > 0);
        char c = array[0];
        while (1 < array.length) {
            if (array[1] > c) {
                c = array[1];
            }
            int n = 0;
            ++n;
        }
        return c;
    }
    
    public static char[] concat(final char[]... array) {
        final int length = array.length;
        while (0 < 0) {
            final int n = 0 + array[0].length;
            int n2 = 0;
            ++n2;
        }
        final char[] array2 = new char[0];
        while (0 < array.length) {
            final char[] array3 = array[0];
            System.arraycopy(array3, 0, array2, 0, array3.length);
            final int n3 = 0 + array3.length;
            int n4 = 0;
            ++n4;
        }
        return array2;
    }
    
    @GwtIncompatible("doesn't work")
    public static byte[] toByteArray(final char c) {
        return new byte[] { (byte)(c >> 8), (byte)c };
    }
    
    @GwtIncompatible("doesn't work")
    public static char fromByteArray(final byte[] array) {
        Preconditions.checkArgument(array.length >= 2, "array too small: %s < %s", array.length, 2);
        return fromBytes(array[0], array[1]);
    }
    
    @GwtIncompatible("doesn't work")
    public static char fromBytes(final byte b, final byte b2) {
        return (char)(b << 8 | (b2 & 0xFF));
    }
    
    public static char[] ensureCapacity(final char[] array, final int n, final int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return (array.length < n) ? copyOf(array, n + n2) : array;
    }
    
    private static char[] copyOf(final char[] array, final int n) {
        final char[] array2 = new char[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static String join(final String s, final char... array) {
        Preconditions.checkNotNull(s);
        final int length = array.length;
        if (length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(length + s.length() * (length - 1));
        sb.append(array[0]);
        while (1 < length) {
            sb.append(s).append(array[1]);
            int n = 0;
            ++n;
        }
        return sb.toString();
    }
    
    public static Comparator lexicographicalComparator() {
        return LexicographicalComparator.INSTANCE;
    }
    
    public static char[] toArray(final Collection collection) {
        if (collection instanceof CharArrayAsList) {
            return ((CharArrayAsList)collection).toCharArray();
        }
        final Object[] array = collection.toArray();
        final int length = array.length;
        final char[] array2 = new char[length];
        while (0 < length) {
            array2[0] = (char)Preconditions.checkNotNull(array[0]);
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static List asList(final char... array) {
        if (array.length == 0) {
            return Collections.emptyList();
        }
        return new CharArrayAsList(array);
    }
    
    static int access$000(final char[] array, final char c, final int n, final int n2) {
        return indexOf(array, c, n, n2);
    }
    
    static int access$100(final char[] array, final char c, final int n, final int n2) {
        return lastIndexOf(array, c, n, n2);
    }
    
    @GwtCompatible
    private static class CharArrayAsList extends AbstractList implements RandomAccess, Serializable
    {
        final char[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;
        
        CharArrayAsList(final char[] array) {
            this(array, 0, array.length);
        }
        
        CharArrayAsList(final char[] array, final int start, final int end) {
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
        public Character get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }
        
        @Override
        public boolean contains(final Object o) {
            return o instanceof Character && Chars.access$000(this.array, (char)o, this.start, this.end) != -1;
        }
        
        @Override
        public int indexOf(final Object o) {
            if (o instanceof Character) {
                final int access$000 = Chars.access$000(this.array, (char)o, this.start, this.end);
                if (access$000 >= 0) {
                    return access$000 - this.start;
                }
            }
            return -1;
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            if (o instanceof Character) {
                final int access$100 = Chars.access$100(this.array, (char)o, this.start, this.end);
                if (access$100 >= 0) {
                    return access$100 - this.start;
                }
            }
            return -1;
        }
        
        public Character set(final int n, final Character c) {
            Preconditions.checkElementIndex(n, this.size());
            final char c2 = this.array[this.start + n];
            this.array[this.start + n] = (char)Preconditions.checkNotNull(c);
            return c2;
        }
        
        @Override
        public List subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            return new CharArrayAsList(this.array, this.start + n, this.start + n2);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof CharArrayAsList)) {
                return super.equals(o);
            }
            final CharArrayAsList list = (CharArrayAsList)o;
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
                final int n = 31 + Chars.hashCode(this.array[i]);
            }
            return 1;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(this.size() * 3);
            sb.append('[').append(this.array[this.start]);
            for (int i = this.start + 1; i < this.end; ++i) {
                sb.append(", ").append(this.array[i]);
            }
            return sb.append(']').toString();
        }
        
        char[] toCharArray() {
            final int size = this.size();
            final char[] array = new char[size];
            System.arraycopy(this.array, this.start, array, 0, size);
            return array;
        }
        
        @Override
        public Object set(final int n, final Object o) {
            return this.set(n, (Character)o);
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
        
        public int compare(final char[] array, final char[] array2) {
            while (0 < Math.min(array.length, array2.length)) {
                final int compare = Chars.compare(array[0], array2[0]);
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
            return this.compare((char[])o, (char[])o2);
        }
        
        static {
            $VALUES = new LexicographicalComparator[] { LexicographicalComparator.INSTANCE };
        }
    }
}
