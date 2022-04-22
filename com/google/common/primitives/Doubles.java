package com.google.common.primitives;

import java.util.regex.*;
import com.google.common.base.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import java.io.*;

@GwtCompatible(emulated = true)
public final class Doubles
{
    public static final int BYTES = 8;
    @GwtIncompatible("regular expressions")
    static final Pattern FLOATING_POINT_PATTERN;
    
    private Doubles() {
    }
    
    public static int hashCode(final double n) {
        return Double.valueOf(n).hashCode();
    }
    
    public static int compare(final double n, final double n2) {
        return Double.compare(n, n2);
    }
    
    public static boolean isFinite(final double n) {
        return Double.NEGATIVE_INFINITY < n & n < Double.POSITIVE_INFINITY;
    }
    
    public static boolean contains(final double[] array, final double n) {
        while (0 < array.length) {
            if (array[0] == n) {
                return true;
            }
            int n2 = 0;
            ++n2;
        }
        return false;
    }
    
    public static int indexOf(final double[] array, final double n) {
        return indexOf(array, n, 0, array.length);
    }
    
    private static int indexOf(final double[] array, final double n, final int n2, final int n3) {
        for (int i = n2; i < n3; ++i) {
            if (array[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    public static int indexOf(final double[] array, final double[] array2) {
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
    
    public static int lastIndexOf(final double[] array, final double n) {
        return lastIndexOf(array, n, 0, array.length);
    }
    
    private static int lastIndexOf(final double[] array, final double n, final int n2, final int n3) {
        for (int i = n3 - 1; i >= n2; --i) {
            if (array[i] == n) {
                return i;
            }
        }
        return -1;
    }
    
    public static double min(final double... array) {
        Preconditions.checkArgument(array.length > 0);
        double min = array[0];
        while (1 < array.length) {
            min = Math.min(min, array[1]);
            int n = 0;
            ++n;
        }
        return min;
    }
    
    public static double max(final double... array) {
        Preconditions.checkArgument(array.length > 0);
        double max = array[0];
        while (1 < array.length) {
            max = Math.max(max, array[1]);
            int n = 0;
            ++n;
        }
        return max;
    }
    
    public static double[] concat(final double[]... array) {
        final int length = array.length;
        while (0 < 0) {
            final int n = 0 + array[0].length;
            int n2 = 0;
            ++n2;
        }
        final double[] array2 = new double[0];
        while (0 < array.length) {
            final double[] array3 = array[0];
            System.arraycopy(array3, 0, array2, 0, array3.length);
            final int n3 = 0 + array3.length;
            int n4 = 0;
            ++n4;
        }
        return array2;
    }
    
    @Beta
    public static Converter stringConverter() {
        return DoubleConverter.INSTANCE;
    }
    
    public static double[] ensureCapacity(final double[] array, final int n, final int n2) {
        Preconditions.checkArgument(n >= 0, "Invalid minLength: %s", n);
        Preconditions.checkArgument(n2 >= 0, "Invalid padding: %s", n2);
        return (array.length < n) ? copyOf(array, n + n2) : array;
    }
    
    private static double[] copyOf(final double[] array, final int n) {
        final double[] array2 = new double[n];
        System.arraycopy(array, 0, array2, 0, Math.min(array.length, n));
        return array2;
    }
    
    public static String join(final String s, final double... array) {
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
    
    public static double[] toArray(final Collection collection) {
        if (collection instanceof DoubleArrayAsList) {
            return ((DoubleArrayAsList)collection).toDoubleArray();
        }
        final Object[] array = collection.toArray();
        final int length = array.length;
        final double[] array2 = new double[length];
        while (0 < length) {
            array2[0] = ((Number)Preconditions.checkNotNull(array[0])).doubleValue();
            int n = 0;
            ++n;
        }
        return array2;
    }
    
    public static List asList(final double... array) {
        if (array.length == 0) {
            return Collections.emptyList();
        }
        return new DoubleArrayAsList(array);
    }
    
    @GwtIncompatible("regular expressions")
    private static Pattern fpPattern() {
        return Pattern.compile("[+-]?(?:NaN|Infinity|" + ("(?:\\d++(?:\\.\\d*+)?|\\.\\d++)" + "(?:[eE][+-]?\\d++)?[fFdD]?") + "|" + ("0[xX]" + "(?:\\p{XDigit}++(?:\\.\\p{XDigit}*+)?|\\.\\p{XDigit}++)" + "[pP][+-]?\\d++[fFdD]?") + ")");
    }
    
    @Nullable
    @GwtIncompatible("regular expressions")
    @Beta
    public static Double tryParse(final String s) {
        if (Doubles.FLOATING_POINT_PATTERN.matcher(s).matches()) {
            return Double.parseDouble(s);
        }
        return null;
    }
    
    static int access$000(final double[] array, final double n, final int n2, final int n3) {
        return indexOf(array, n, n2, n3);
    }
    
    static int access$100(final double[] array, final double n, final int n2, final int n3) {
        return lastIndexOf(array, n, n2, n3);
    }
    
    static {
        FLOATING_POINT_PATTERN = fpPattern();
    }
    
    @GwtCompatible
    private static class DoubleArrayAsList extends AbstractList implements RandomAccess, Serializable
    {
        final double[] array;
        final int start;
        final int end;
        private static final long serialVersionUID = 0L;
        
        DoubleArrayAsList(final double[] array) {
            this(array, 0, array.length);
        }
        
        DoubleArrayAsList(final double[] array, final int start, final int end) {
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
        public Double get(final int n) {
            Preconditions.checkElementIndex(n, this.size());
            return this.array[this.start + n];
        }
        
        @Override
        public boolean contains(final Object o) {
            return o instanceof Double && Doubles.access$000(this.array, (double)o, this.start, this.end) != -1;
        }
        
        @Override
        public int indexOf(final Object o) {
            if (o instanceof Double) {
                final int access$000 = Doubles.access$000(this.array, (double)o, this.start, this.end);
                if (access$000 >= 0) {
                    return access$000 - this.start;
                }
            }
            return -1;
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            if (o instanceof Double) {
                final int access$100 = Doubles.access$100(this.array, (double)o, this.start, this.end);
                if (access$100 >= 0) {
                    return access$100 - this.start;
                }
            }
            return -1;
        }
        
        public Double set(final int n, final Double n2) {
            Preconditions.checkElementIndex(n, this.size());
            final double n3 = this.array[this.start + n];
            this.array[this.start + n] = (double)Preconditions.checkNotNull(n2);
            return n3;
        }
        
        @Override
        public List subList(final int n, final int n2) {
            Preconditions.checkPositionIndexes(n, n2, this.size());
            if (n == n2) {
                return Collections.emptyList();
            }
            return new DoubleArrayAsList(this.array, this.start + n, this.start + n2);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof DoubleArrayAsList)) {
                return super.equals(o);
            }
            final DoubleArrayAsList list = (DoubleArrayAsList)o;
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
                final int n = 31 + Doubles.hashCode(this.array[i]);
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
        
        double[] toDoubleArray() {
            final int size = this.size();
            final double[] array = new double[size];
            System.arraycopy(this.array, this.start, array, 0, size);
            return array;
        }
        
        @Override
        public Object set(final int n, final Object o) {
            return this.set(n, (Double)o);
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
        
        public int compare(final double[] array, final double[] array2) {
            while (0 < Math.min(array.length, array2.length)) {
                final int compare = Doubles.compare(array[0], array2[0]);
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
            return this.compare((double[])o, (double[])o2);
        }
        
        static {
            $VALUES = new LexicographicalComparator[] { LexicographicalComparator.INSTANCE };
        }
    }
    
    private static final class DoubleConverter extends Converter implements Serializable
    {
        static final DoubleConverter INSTANCE;
        private static final long serialVersionUID = 1L;
        
        protected Double doForward(final String s) {
            return Double.valueOf(s);
        }
        
        protected String doBackward(final Double n) {
            return n.toString();
        }
        
        @Override
        public String toString() {
            return "Doubles.stringConverter()";
        }
        
        private Object readResolve() {
            return DoubleConverter.INSTANCE;
        }
        
        @Override
        protected Object doBackward(final Object o) {
            return this.doBackward((Double)o);
        }
        
        @Override
        protected Object doForward(final Object o) {
            return this.doForward((String)o);
        }
        
        static {
            INSTANCE = new DoubleConverter();
        }
    }
}
