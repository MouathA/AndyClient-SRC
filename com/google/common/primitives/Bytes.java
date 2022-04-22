package com.google.common.primitives;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;
import java.io.*;

@GwtCompatible
public final class Bytes
{
    private Bytes() {
    }
    
    public static int hashCode(final byte b) {
        return b;
    }
    
    public static boolean contains(final byte[] array, final byte b) {
        while (0 < array.length) {
            if (array[0] == b) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static int indexOf(final byte[] array, final byte b) {
        return indexOf(array, b, 0, array.length);
    }
    
    private static int indexOf(final byte[] array, final byte b, final int n, final int n2) {
        for (int i = n; i < n2; ++i) {
            if (array[i] == b) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(final byte[] array, final byte[] array2) {
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
    
    public static int lastIndexOf(final byte[] array, final byte b) {
        return lastIndexOf(array, b, 0, array.length);
    }
    
    private static int lastIndexOf(final byte[] array, final byte b, final int n, final int n2) {
        for (int i = n2 - 1; i >= n; --i) {
            if (array[i] == b) {
                return i;
            }
        }
        return -1;
    }
    
    public static byte[] concat(final byte[]... array) {
        final int length = array.length;
        while (0 < 0) {
            final int n = 0 + array[0].length;
            int n2 = 0;
            ++n2;
        }
        final byte[] array2 = new byte[0];
        while (0 < array.length) {
            final byte[] array3 = array[0];
            System.arraycopy(array3, 0, array2, 0, array3.length);
            final int n3 = 0 + array3.length;
            int n4 = 0;
            ++n4;
        }
        return array2;
    }
    
    public static byte[] ensureCapacity(final byte[] array, final int n, final int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return (array.length < n) ? copyOf(array, n + n2) : array;
    }
    
    private static byte[] copyOf(final byte[] array, final int n) {
        final byte[] array2 = new byte[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static byte[] toArray(final Collection collection) {
        if (collection instanceof ByteArrayAsList) {
            return ((ByteArrayAsList)collection).toByteArray();
        }
        final Object[] array = collection.toArray();
        final int length = array.length;
        final byte[] array2 = new byte[length];
        while (0 < length) {
            array2[0] = ((Number)Preconditions.checkNotNull(array[0])).byteValue();
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static List asList(final byte... array) {
        if (array.length == 0) {
            return Collections.emptyList();
        }
        return new ByteArrayAsList(array);
    }
    
    static int access$000(final byte[] array, final byte b, final int n, final int n2) {
        return indexOf(array, b, n, n2);
    }
    
    static int access$100(final byte[] array, final byte b, final int n, final int n2) {
        return lastIndexOf(array, b, n, n2);
    }
    
    @GwtCompatible
    private static class ByteArrayAsList extends AbstractList implements RandomAccess, Serializable
    {
        final byte[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;
        
        ByteArrayAsList(final byte[] array) {
            this(array, 0, array.length);
        }
        
        ByteArrayAsList(final byte[] array, final int start, final int end) {
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
        public Byte get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }
        
        @Override
        public boolean contains(final Object o) {
            return o instanceof Byte && Bytes.access$000(this.array, (byte)o, this.start, this.end) != -1;
        }
        
        @Override
        public int indexOf(final Object o) {
            if (o instanceof Byte) {
                final int access$000 = Bytes.access$000(this.array, (byte)o, this.start, this.end);
                if (access$000 >= 0) {
                    return access$000 - this.start;
                }
            }
            return -1;
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            if (o instanceof Byte) {
                final int access$100 = Bytes.access$100(this.array, (byte)o, this.start, this.end);
                if (access$100 >= 0) {
                    return access$100 - this.start;
                }
            }
            return -1;
        }
        
        public Byte set(final int n, final Byte b) {
            Preconditions.checkElementIndex(n, this.size());
            final byte b2 = this.array[this.start + n];
            this.array[this.start + n] = (byte)Preconditions.checkNotNull(b);
            return b2;
        }
        
        @Override
        public List subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            return new ByteArrayAsList(this.array, this.start + n, this.start + n2);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof ByteArrayAsList)) {
                return super.equals(o);
            }
            final ByteArrayAsList list = (ByteArrayAsList)o;
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
                final int n = 31 + Bytes.hashCode(this.array[i]);
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
        
        byte[] toByteArray() {
            final int size = this.size();
            final byte[] array = new byte[size];
            System.arraycopy(this.array, this.start, array, 0, size);
            return array;
        }
        
        @Override
        public Object set(final int n, final Object o) {
            return this.set(n, (Byte)o);
        }
        
        @Override
        public Object get(final int n) {
            return this.get(n);
        }
    }
}
