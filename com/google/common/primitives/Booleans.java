package com.google.common.primitives;

import com.google.common.base.*;
import com.google.common.annotations.*;
import java.util.*;
import java.io.*;

@GwtCompatible
public final class Booleans
{
    private Booleans() {
    }
    
    public static int hashCode(final boolean b) {
        return b ? 1231 : 1237;
    }
    
    public static int compare(final boolean b, final boolean b2) {
        return (b == b2) ? 0 : (b ? 1 : -1);
    }
    
    public static boolean contains(final boolean[] array, final boolean b) {
        while (0 < array.length) {
            if (array[0] == b) {
                return true;
            }
            int n = 0;
            ++n;
        }
        return false;
    }
    
    public static int indexOf(final boolean[] array, final boolean b) {
        return indexOf(array, b, 0, array.length);
    }
    
    private static int indexOf(final boolean[] array, final boolean b, final int n, final int n2) {
        for (int i = n; i < n2; ++i) {
            if (array[i] == b) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(final boolean[] array, final boolean[] array2) {
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
    
    public static int lastIndexOf(final boolean[] array, final boolean b) {
        return lastIndexOf(array, b, 0, array.length);
    }
    
    private static int lastIndexOf(final boolean[] array, final boolean b, final int n, final int n2) {
        for (int i = n2 - 1; i >= n; --i) {
            if (array[i] == b) {
                return i;
            }
        }
        return -1;
    }
    
    public static boolean[] concat(final boolean[]... array) {
        final int length = array.length;
        while (0 < 0) {
            final int n = 0 + array[0].length;
            int n2 = 0;
            ++n2;
        }
        final boolean[] array2 = new boolean[0];
        while (0 < array.length) {
            final boolean[] array3 = array[0];
            System.arraycopy(array3, 0, array2, 0, array3.length);
            final int n3 = 0 + array3.length;
            int n4 = 0;
            ++n4;
        }
        return array2;
    }
    
    public static boolean[] ensureCapacity(final boolean[] array, final int n, final int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return (array.length < n) ? copyOf(array, n + n2) : array;
    }
    
    private static boolean[] copyOf(final boolean[] array, final int n) {
        final boolean[] array2 = new boolean[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static String join(final String s, final boolean... array) {
        Preconditions.checkNotNull(s);
        if (array.length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(array.length * 7);
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
    
    public static boolean[] toArray(final Collection collection) {
        if (collection instanceof BooleanArrayAsList) {
            return ((BooleanArrayAsList)collection).toBooleanArray();
        }
        final Object[] array = collection.toArray();
        final int length = array.length;
        final boolean[] array2 = new boolean[length];
        while (0 < length) {
            array2[0] = (boolean)Preconditions.checkNotNull(array[0]);
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static List asList(final boolean... array) {
        if (array.length == 0) {
            return Collections.emptyList();
        }
        return new BooleanArrayAsList(array);
    }
    
    @Beta
    public static int countTrue(final boolean... array) {
        while (0 < array.length) {
            if (array[0]) {
                int n = 0;
                ++n;
            }
            int n2 = 0;
            ++n2;
        }
        return 0;
    }
    
    static int access$000(final boolean[] array, final boolean b, final int n, final int n2) {
        return indexOf(array, b, n, n2);
    }
    
    static int access$100(final boolean[] array, final boolean b, final int n, final int n2) {
        return lastIndexOf(array, b, n, n2);
    }
    
    @GwtCompatible
    private static class BooleanArrayAsList extends AbstractList implements RandomAccess, Serializable
    {
        final boolean[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;
        
        BooleanArrayAsList(final boolean[] array) {
            this(array, 0, array.length);
        }
        
        BooleanArrayAsList(final boolean[] array, final int start, final int end) {
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
        public Boolean get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }
        
        @Override
        public boolean contains(final Object o) {
            return o instanceof Boolean && Booleans.access$000(this.array, (boolean)o, this.start, this.end) != -1;
        }
        
        @Override
        public int indexOf(final Object o) {
            if (o instanceof Boolean) {
                final int access$000 = Booleans.access$000(this.array, (boolean)o, this.start, this.end);
                if (access$000 >= 0) {
                    return access$000 - this.start;
                }
            }
            return -1;
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            if (o instanceof Boolean) {
                final int access$100 = Booleans.access$100(this.array, (boolean)o, this.start, this.end);
                if (access$100 >= 0) {
                    return access$100 - this.start;
                }
            }
            return -1;
        }
        
        public Boolean set(final int n, final Boolean b) {
            Preconditions.checkElementIndex(n, this.size());
            final boolean b2 = this.array[this.start + n];
            this.array[this.start + n] = (boolean)Preconditions.checkNotNull(b);
            return b2;
        }
        
        @Override
        public List subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            return new BooleanArrayAsList(this.array, this.start + n, this.start + n2);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof BooleanArrayAsList)) {
                return super.equals(o);
            }
            final BooleanArrayAsList list = (BooleanArrayAsList)o;
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
                final int n = 31 + Booleans.hashCode(this.array[i]);
            }
            return 1;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(this.size() * 7);
            sb.append(this.array[this.start] ? "[true" : "[false");
            for (int i = this.start + 1; i < this.end; ++i) {
                sb.append(this.array[i] ? ", true" : ", false");
            }
            return sb.append(']').toString();
        }
        
        boolean[] toBooleanArray() {
            final int size = this.size();
            final boolean[] array = new boolean[size];
            System.arraycopy(this.array, this.start, array, 0, size);
            return array;
        }
        
        @Override
        public Object set(final int n, final Object o) {
            return this.set(n, (Boolean)o);
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
        
        public int compare(final boolean[] array, final boolean[] array2) {
            while (0 < Math.min(array.length, array2.length)) {
                final int compare = Booleans.compare(array[0], array2[0]);
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
            return this.compare((boolean[])o, (boolean[])o2);
        }
        
        static {
            $VALUES = new LexicographicalComparator[] { LexicographicalComparator.INSTANCE };
        }
    }
}
