package com.google.common.primitives;

import com.google.common.base.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;
import java.io.*;

@GwtCompatible(emulated = true)
public final class Floats
{
    public static final int BYTES = 4;
    
    private Floats() {
    }
    
    public static int hashCode(final float n) {
        return Float.valueOf(n).hashCode();
    }
    
    public static int compare(final float n, final float n2) {
        return Float.compare(n, n2);
    }
    
    public static boolean isFinite(final float n) {
        return Float.NEGATIVE_INFINITY < n & n < Float.POSITIVE_INFINITY;
    }
    
    public static boolean contains(final float[] array, final float n) {
        while (0 < array.length) {
            if (array[0] == n) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    public static int indexOf(final float[] array, final float n) {
        return indexOf(array, n, 0, array.length);
    }
    
    private static int indexOf(final float[] array, final float n, final int n2, final int n3) {
        for (int i = n2; i < n3; ++i) {
            if (array[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(final float[] array, final float[] array2) {
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
    
    public static int lastIndexOf(final float[] array, final float n) {
        return lastIndexOf(array, n, 0, array.length);
    }
    
    private static int lastIndexOf(final float[] array, final float n, final int n2, final int n3) {
        for (int i = n3 - 1; i >= n2; --i) {
            if (array[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    public static float min(final float... array) {
        Preconditions.checkArgument(array.length > 0);
        float min = array[0];
        while (1 < array.length) {
            min = Math.min(min, array[1]);
            int n = 0;
            ++n;
        }
        return min;
    }
    
    public static float max(final float... array) {
        Preconditions.checkArgument(array.length > 0);
        float max = array[0];
        while (1 < array.length) {
            max = Math.max(max, array[1]);
            int n = 0;
            ++n;
        }
        return max;
    }
    
    public static float[] concat(final float[]... array) {
        final int length = array.length;
        while (0 < 0) {
            final int n = 0 + array[0].length;
            int n2 = 0;
            ++n2;
        }
        final float[] array2 = new float[0];
        while (0 < array.length) {
            final float[] array3 = array[0];
            System.arraycopy(array3, 0, array2, 0, array3.length);
            final int n3 = 0 + array3.length;
            int n4 = 0;
            ++n4;
        }
        return array2;
    }
    
    @Beta
    public static Converter stringConverter() {
        return FloatConverter.INSTANCE;
    }
    
    public static float[] ensureCapacity(final float[] array, final int n, final int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return (array.length < n) ? copyOf(array, n + n2) : array;
    }
    
    private static float[] copyOf(final float[] array, final int n) {
        final float[] array2 = new float[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static String join(final String s, final float... array) {
        Preconditions.checkNotNull(s);
        if (array.length == 0) {
            return "";
        }
        final StringBuilder sb = new StringBuilder(array.length * 12);
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
    
    public static float[] toArray(final Collection collection) {
        if (collection instanceof FloatArrayAsList) {
            return ((FloatArrayAsList)collection).toFloatArray();
        }
        final Object[] array = collection.toArray();
        final int length = array.length;
        final float[] array2 = new float[length];
        while (0 < length) {
            array2[0] = ((Number)Preconditions.checkNotNull(array[0])).floatValue();
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static List asList(final float... array) {
        if (array.length == 0) {
            return Collections.emptyList();
        }
        return new FloatArrayAsList(array);
    }
    
    @Nullable
    @GwtIncompatible("regular expressions")
    @Beta
    public static Float tryParse(final String s) {
        if (Doubles.FLOATING_POINT_PATTERN.matcher(s).matches()) {
            return Float.parseFloat(s);
        }
        return null;
    }
    
    static int access$000(final float[] array, final float n, final int n2, final int n3) {
        return indexOf(array, n, n2, n3);
    }
    
    static int access$100(final float[] array, final float n, final int n2, final int n3) {
        return lastIndexOf(array, n, n2, n3);
    }
    
    @GwtCompatible
    private static class FloatArrayAsList extends AbstractList implements RandomAccess, Serializable
    {
        final float[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;
        
        FloatArrayAsList(final float[] array) {
            this(array, 0, array.length);
        }
        
        FloatArrayAsList(final float[] array, final int start, final int end) {
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
        public Float get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }
        
        @Override
        public boolean contains(final Object o) {
            return o instanceof Float && Floats.access$000(this.array, (float)o, this.start, this.end) != -1;
        }
        
        @Override
        public int indexOf(final Object o) {
            if (o instanceof Float) {
                final int access$000 = Floats.access$000(this.array, (float)o, this.start, this.end);
                if (access$000 >= 0) {
                    return access$000 - this.start;
                }
            }
            return -1;
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            if (o instanceof Float) {
                final int access$100 = Floats.access$100(this.array, (float)o, this.start, this.end);
                if (access$100 >= 0) {
                    return access$100 - this.start;
                }
            }
            return -1;
        }
        
        public Float set(final int n, final Float n2) {
            Preconditions.checkElementIndex(n, this.size());
            final float n3 = this.array[this.start + n];
            this.array[this.start + n] = (float)Preconditions.checkNotNull(n2);
            return n3;
        }
        
        @Override
        public List subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            return new FloatArrayAsList(this.array, this.start + n, this.start + n2);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof FloatArrayAsList)) {
                return super.equals(o);
            }
            final FloatArrayAsList list = (FloatArrayAsList)o;
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
                final int n = 31 + Floats.hashCode(this.array[i]);
            }
            return 1;
        }
        
        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(this.size() * 12);
            sb.append('[').append(this.array[this.start]);
            for (int i = this.start + 1; i < this.end; ++i) {
                sb.append(", ").append(this.array[i]);
            }
            return sb.append(']').toString();
        }
        
        float[] toFloatArray() {
            final int size = this.size();
            final float[] array = new float[size];
            System.arraycopy(this.array, this.start, array, 0, size);
            return array;
        }
        
        @Override
        public Object set(final int n, final Object o) {
            return this.set(n, (Float)o);
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
        
        public int compare(final float[] array, final float[] array2) {
            while (0 < Math.min(array.length, array2.length)) {
                final int compare = Floats.compare(array[0], array2[0]);
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
            return this.compare((float[])o, (float[])o2);
        }
        
        static {
            $VALUES = new LexicographicalComparator[] { LexicographicalComparator.INSTANCE };
        }
    }
    
    private static final class FloatConverter extends Converter implements Serializable
    {
        static final FloatConverter INSTANCE;
        private static final long serialVersionUID = 1L;
        
        protected Float doForward(final String s) {
            return Float.valueOf(s);
        }
        
        protected String doBackward(final Float n) {
            return n.toString();
        }
        
        @Override
        public String toString() {
            return "Floats.stringConverter()";
        }
        
        private Object readResolve() {
            return FloatConverter.INSTANCE;
        }
        
        @Override
        protected Object doBackward(final Object o) {
            return this.doBackward((Float)o);
        }
        
        @Override
        protected Object doForward(final Object o) {
            return this.doForward((String)o);
        }
        
        static {
            INSTANCE = new FloatConverter();
        }
    }
}
