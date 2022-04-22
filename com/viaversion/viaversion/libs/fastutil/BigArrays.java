package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.bytes.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.concurrent.atomic.*;
import com.viaversion.viaversion.libs.fastutil.longs.*;
import com.viaversion.viaversion.libs.fastutil.doubles.*;
import com.viaversion.viaversion.libs.fastutil.booleans.*;
import com.viaversion.viaversion.libs.fastutil.shorts.*;
import com.viaversion.viaversion.libs.fastutil.chars.*;
import com.viaversion.viaversion.libs.fastutil.floats.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.*;

public class BigArrays
{
    public static final int SEGMENT_SHIFT = 27;
    public static final int SEGMENT_SIZE = 134217728;
    public static final int SEGMENT_MASK = 134217727;
    private static final int SMALL = 7;
    private static final int MEDIUM = 40;
    
    protected BigArrays() {
    }
    
    public static int segment(final long n) {
        return (int)(n >>> 27);
    }
    
    public static int displacement(final long n) {
        return (int)(n & 0x7FFFFFFL);
    }
    
    public static long start(final int n) {
        return (long)n << 27;
    }
    
    public static long nearestSegmentStart(final long n, final long n2, final long n3) {
        final long start = start(segment(n));
        final long start2 = start(segment(n) + 1);
        if (start2 >= n3) {
            if (start < n2) {
                return n;
            }
            return start;
        }
        else {
            if (start < n2) {
                return start2;
            }
            return (n <= start + (start2 - start >> 1)) ? start : start2;
        }
    }
    
    public static long index(final int n, final int n2) {
        return start(n) + n2;
    }
    
    public static void ensureFromTo(final long n, final long n2, final long n3) {
        if (n2 < 0L) {
            throw new ArrayIndexOutOfBoundsException("Start index (" + n2 + ") is negative");
        }
        if (n2 > n3) {
            throw new IllegalArgumentException("Start index (" + n2 + ") is greater than end index (" + n3 + ")");
        }
        if (n3 > n) {
            throw new ArrayIndexOutOfBoundsException("End index (" + n3 + ") is greater than big-array length (" + n + ")");
        }
    }
    
    public static void ensureOffsetLength(final long n, final long n2, final long n3) {
        if (n2 < 0L) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n3 < 0L) {
            throw new IllegalArgumentException("Length (" + n3 + ") is negative");
        }
        if (n2 + n3 > n) {
            throw new ArrayIndexOutOfBoundsException("Last index (" + (n2 + n3) + ") is greater than big-array length (" + n + ")");
        }
    }
    
    public static void ensureLength(final long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("Negative big-array size: " + n);
        }
        if (n >= 288230376017494016L) {
            throw new IllegalArgumentException("Big-array size too big: " + n);
        }
    }
    
    private static void inPlaceMerge(final long n, long n2, final long n3, final LongComparator longComparator, final BigSwapper bigSwapper) {
        if (n >= n2 || n2 >= n3) {
            return;
        }
        if (n3 - n == 2L) {
            if (longComparator.compare(n2, n) < 0) {
                bigSwapper.swap(n, n2);
            }
            return;
        }
        long upperBound;
        long lowerBound;
        if (n2 - n > n3 - n2) {
            upperBound = n + (n2 - n) / 2L;
            lowerBound = lowerBound(n2, n3, upperBound, longComparator);
        }
        else {
            lowerBound = n2 + (n3 - n2) / 2L;
            upperBound = upperBound(n, n2, lowerBound, longComparator);
        }
        final long n4 = upperBound;
        final long n5 = n2;
        final long n6 = lowerBound;
        if (n5 != n4 && n5 != n6) {
            long n7 = n4;
            long n8 = n5;
            while (n7 < --n8) {
                bigSwapper.swap(n7++, n8);
            }
            long n9 = n5;
            long n10 = n6;
            while (n9 < --n10) {
                bigSwapper.swap(n9++, n10);
            }
            long n11 = n4;
            long n12 = n6;
            while (n11 < --n12) {
                bigSwapper.swap(n11++, n12);
            }
        }
        n2 = upperBound + (lowerBound - n2);
        inPlaceMerge(n, upperBound, n2, longComparator, bigSwapper);
        inPlaceMerge(n2, lowerBound, n3, longComparator, bigSwapper);
    }
    
    private static long lowerBound(long n, final long n2, final long n3, final LongComparator longComparator) {
        long n4 = n2 - n;
        while (n4 > 0L) {
            final long n5 = n4 / 2L;
            final long n6 = n + n5;
            if (longComparator.compare(n6, n3) < 0) {
                n = n6 + 1L;
                n4 -= n5 + 1L;
            }
            else {
                n4 = n5;
            }
        }
        return n;
    }
    
    private static long med3(final long n, final long n2, final long n3, final LongComparator longComparator) {
        final int compare = longComparator.compare(n, n2);
        final int compare2 = longComparator.compare(n, n3);
        final int compare3 = longComparator.compare(n2, n3);
        return (compare < 0) ? ((compare3 < 0) ? n2 : ((compare2 < 0) ? n3 : n)) : ((compare3 > 0) ? n2 : ((compare2 > 0) ? n3 : n));
    }
    
    public static void mergeSort(final long n, final long n2, final LongComparator longComparator, final BigSwapper bigSwapper) {
        if (n2 - n < 7L) {
            for (long n3 = n; n3 < n2; ++n3) {
                for (long n4 = n3; n4 > n && longComparator.compare(n4 - 1L, n4) > 0; --n4) {
                    bigSwapper.swap(n4, n4 - 1L);
                }
            }
            return;
        }
        final long n5 = n + n2 >>> 1;
        mergeSort(n, n5, longComparator, bigSwapper);
        mergeSort(n5, n2, longComparator, bigSwapper);
        if (longComparator.compare(n5 - 1L, n5) <= 0) {
            return;
        }
        inPlaceMerge(n, n5, n2, longComparator, bigSwapper);
    }
    
    public static void quickSort(final long n, final long n2, final LongComparator longComparator, final BigSwapper bigSwapper) {
        final long n3 = n2 - n;
        if (n3 < 7L) {
            for (long n4 = n; n4 < n2; ++n4) {
                for (long n5 = n4; n5 > n && longComparator.compare(n5 - 1L, n5) > 0; --n5) {
                    bigSwapper.swap(n5, n5 - 1L);
                }
            }
            return;
        }
        long n6 = n + n3 / 2L;
        if (n3 > 7L) {
            long med3 = n;
            long med4 = n2 - 1L;
            if (n3 > 40L) {
                final long n7 = n3 / 8L;
                med3 = med3(med3, med3 + n7, med3 + 2L * n7, longComparator);
                n6 = med3(n6 - n7, n6, n6 + n7, longComparator);
                med4 = med3(med4 - 2L * n7, med4 - n7, med4, longComparator);
            }
            n6 = med3(med3, n6, med4, longComparator);
        }
        long n9;
        long n8 = n9 = n;
        long n11;
        long n10 = n11 = n2 - 1L;
        while (true) {
            final int compare;
            if (n9 <= n10 && (compare = longComparator.compare(n9, n6)) <= 0) {
                if (compare == 0) {
                    if (n8 == n6) {
                        n6 = n9;
                    }
                    else if (n9 == n6) {
                        n6 = n8;
                    }
                    bigSwapper.swap(n8++, n9);
                }
                ++n9;
            }
            else {
                int compare2;
                while (n10 >= n9 && (compare2 = longComparator.compare(n10, n6)) >= 0) {
                    if (compare2 == 0) {
                        if (n10 == n6) {
                            n6 = n11;
                        }
                        else if (n11 == n6) {
                            n6 = n10;
                        }
                        bigSwapper.swap(n10, n11--);
                    }
                    --n10;
                }
                if (n9 > n10) {
                    break;
                }
                if (n9 == n6) {
                    n6 = n11;
                }
                else if (n10 == n6) {
                    n6 = n10;
                }
                bigSwapper.swap(n9++, n10--);
            }
        }
        final long n12 = n + n3;
        final long min = Math.min(n8 - n, n9 - n8);
        vecSwap(bigSwapper, n, n9 - min, min);
        final long min2 = Math.min(n11 - n10, n12 - n11 - 1L);
        vecSwap(bigSwapper, n9, n12 - min2, min2);
        final long n13;
        if ((n13 = n9 - n8) > 1L) {
            quickSort(n, n + n13, longComparator, bigSwapper);
        }
        final long n14;
        if ((n14 = n11 - n10) > 1L) {
            quickSort(n12 - n14, n12, longComparator, bigSwapper);
        }
    }
    
    private static long upperBound(long n, final long n2, final long n3, final LongComparator longComparator) {
        long n4 = n2 - n;
        while (n4 > 0L) {
            final long n5 = n4 / 2L;
            final long n6 = n + n5;
            if (longComparator.compare(n3, n6) < 0) {
                n4 = n5;
            }
            else {
                n = n6 + 1L;
                n4 -= n5 + 1L;
            }
        }
        return n;
    }
    
    private static void vecSwap(final BigSwapper bigSwapper, long n, long n2, final long n3) {
        while (0 < n3) {
            bigSwapper.swap(n, n2);
            int n4 = 0;
            ++n4;
            ++n;
            ++n2;
        }
    }
    
    public static byte get(final byte[][] array, final long n) {
        return array[segment(n)][displacement(n)];
    }
    
    public static void set(final byte[][] array, final long n, final byte b) {
        array[segment(n)][displacement(n)] = b;
    }
    
    public static void swap(final byte[][] array, final long n, final long n2) {
        final byte b = array[segment(n)][displacement(n)];
        array[segment(n)][displacement(n)] = array[segment(n2)][displacement(n2)];
        array[segment(n2)][displacement(n2)] = b;
    }
    
    public static byte[][] reverse(final byte[][] array) {
        final long length = length(array);
        long n = length / 2L;
        while (n-- != 0L) {
            swap(array, n, length - n - 1L);
        }
        return array;
    }
    
    public static void add(final byte[][] array, final long n, final byte b) {
        final byte[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] += b;
    }
    
    public static void mul(final byte[][] array, final long n, final byte b) {
        final byte[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] *= b;
    }
    
    public static void incr(final byte[][] array, final long n) {
        final byte[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        ++array2[displacement];
    }
    
    public static void decr(final byte[][] array, final long n) {
        final byte[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        --array2[displacement];
    }
    
    public static long length(final byte[][] array) {
        final int length = array.length;
        return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length);
    }
    
    public static void copy(final byte[][] array, final long n, final byte[][] array2, final long n2, long n3) {
        if (n2 <= n) {
            int segment = segment(n);
            int segment2 = segment(n2);
            displacement(n);
            displacement(n2);
            while (n3 > 0L) {
                final int n4 = (int)Math.min(n3, Math.min(array[segment].length - 134217728, array2[segment2].length - 134217728));
                if (n4 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment], 134217728, array2[segment2], 134217728, n4);
                if (134217728 + n4 == 134217728) {
                    ++segment;
                }
                if (134217728 + n4 == 134217728) {
                    ++segment2;
                }
                n3 -= n4;
            }
        }
        else {
            int segment3 = segment(n + n3);
            int segment4 = segment(n2 + n3);
            displacement(n + n3);
            displacement(n2 + n3);
            while (n3 > 0L) {
                if (134217728 == 0) {
                    --segment3;
                }
                if (134217728 == 0) {
                    --segment4;
                }
                final int n5 = (int)Math.min(n3, Math.min(134217728, 134217728));
                if (n5 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment3], 134217728 - n5, array2[segment4], 134217728 - n5, n5);
                n3 -= n5;
            }
        }
    }
    
    public static void copyFromBig(final byte[][] array, final long n, final byte[] array2, int n2, int i) {
        int segment = segment(n);
        displacement(n);
        while (i > 0) {
            final int min = Math.min(array[segment].length - 0, i);
            if (min == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array[segment], 0, array2, n2, min);
            if (0 + min == 134217728) {
                ++segment;
            }
            n2 += min;
            i -= min;
        }
    }
    
    public static void copyToBig(final byte[] array, int n, final byte[][] array2, final long n2, long n3) {
        int segment = segment(n2);
        displacement(n2);
        while (n3 > 0L) {
            final int n4 = (int)Math.min(array2[segment].length - 0, n3);
            if (n4 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array, n, array2[segment], 0, n4);
            if (0 + n4 == 134217728) {
                ++segment;
            }
            n += n4;
            n3 -= n4;
        }
    }
    
    public static byte[][] wrap(final byte[] array) {
        if (array.length == 0) {
            return ByteBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new byte[][] { array };
        }
        final byte[][] bigArray = ByteBigArrays.newBigArray((long)array.length);
        while (0 < bigArray.length) {
            System.arraycopy(array, (int)start(0), bigArray[0], 0, bigArray[0].length);
            int n = 0;
            ++n;
        }
        return bigArray;
    }
    
    public static byte[][] ensureCapacity(final byte[][] array, final long n) {
        return ensureCapacity(array, n, length(array));
    }
    
    public static byte[][] forceCapacity(final byte[][] array, final long n, final long n2) {
        ensureLength(n);
        final int n3 = array.length - ((array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728)) ? 1 : 0);
        final int n4 = (int)(n + 134217727L >>> 27);
        final byte[][] array2 = Arrays.copyOf(array, n4);
        final int n5 = (int)(n & 0x7FFFFFFL);
        if (n5 != 0) {
            for (int i = n3; i < n4 - 1; ++i) {
                array2[i] = new byte[134217728];
            }
            array2[n4 - 1] = new byte[n5];
        }
        else {
            for (int j = n3; j < n4; ++j) {
                array2[j] = new byte[134217728];
            }
        }
        if (n2 - n3 * 134217728L > 0L) {
            copy(array, n3 * 134217728L, array2, n3 * 134217728L, n2 - n3 * 134217728L);
        }
        return array2;
    }
    
    public static byte[][] ensureCapacity(final byte[][] array, final long n, final long n2) {
        return (n > length(array)) ? forceCapacity(array, n, n2) : array;
    }
    
    public static byte[][] grow(final byte[][] array, final long n) {
        final long length = length(array);
        return (n > length) ? grow(array, n, length) : array;
    }
    
    public static byte[][] grow(final byte[][] array, final long n, final long n2) {
        final long length = length(array);
        return (n > length) ? ensureCapacity(array, Math.max(length + (length >> 1), n), n2) : array;
    }
    
    public static byte[][] trim(final byte[][] array, final long n) {
        ensureLength(n);
        if (n >= length(array)) {
            return array;
        }
        final int n2 = (int)(n + 134217727L >>> 27);
        final byte[][] array2 = Arrays.copyOf(array, n2);
        final int n3 = (int)(n & 0x7FFFFFFL);
        if (n3 != 0) {
            array2[n2 - 1] = ByteArrays.trim(array2[n2 - 1], n3);
        }
        return array2;
    }
    
    public static byte[][] setLength(final byte[][] array, final long n) {
        final long length = length(array);
        if (n == length) {
            return array;
        }
        if (n < length) {
            return trim(array, n);
        }
        return ensureCapacity(array, n);
    }
    
    public static byte[][] copy(final byte[][] array, final long n, final long n2) {
        ensureOffsetLength(array, n, n2);
        final byte[][] bigArray = ByteBigArrays.newBigArray(n2);
        copy(array, n, bigArray, 0L, n2);
        return bigArray;
    }
    
    public static byte[][] copy(final byte[][] array) {
        final byte[][] array2 = array.clone();
        int length = array2.length;
        while (length-- != 0) {
            array2[length] = array[length].clone();
        }
        return array2;
    }
    
    public static void fill(final byte[][] array, final byte b) {
        int length = array.length;
        while (length-- != 0) {
            Arrays.fill(array[length], b);
        }
    }
    
    public static void fill(final byte[][] array, final long n, final long n2, final byte b) {
        final long length = length(array);
        ensureFromTo(length, n, n2);
        if (length == 0L) {
            return;
        }
        final int segment = segment(n);
        int segment2 = segment(n2);
        final int displacement = displacement(n);
        final int displacement2 = displacement(n2);
        if (segment == segment2) {
            Arrays.fill(array[segment], displacement, displacement2, b);
            return;
        }
        if (displacement2 != 0) {
            Arrays.fill(array[segment2], 0, displacement2, b);
        }
        while (--segment2 > segment) {
            Arrays.fill(array[segment2], b);
        }
        Arrays.fill(array[segment], displacement, 134217728, b);
    }
    
    public static boolean equals(final byte[][] array, final byte[][] array2) {
        if (length(array) != length(array2)) {
            return false;
        }
        int length = array.length;
        while (length-- != 0) {
            final byte[] array3 = array[length];
            final byte[] array4 = array2[length];
            int length2 = array3.length;
            while (length2-- != 0) {
                if (array3[length2] != array4[length2]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String toString(final byte[][] array) {
        if (array == null) {
            return "null";
        }
        final long n = length(array) - 1L;
        if (n == -1L) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        long n2 = 0L;
        while (true) {
            sb.append(String.valueOf(get(array, n2)));
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static void ensureFromTo(final byte[][] array, final long n, final long n2) {
        ensureFromTo(length(array), n, n2);
    }
    
    public static void ensureOffsetLength(final byte[][] array, final long n, final long n2) {
        ensureOffsetLength(length(array), n, n2);
    }
    
    public static void ensureSameLength(final byte[][] array, final byte[][] array2) {
        if (length(array) != length(array2)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(array) + " != " + length(array2));
        }
    }
    
    public static byte[][] shuffle(final byte[][] array, final long n, final long n2, final Random random) {
        long n3 = n2 - n;
        while (n3-- != 0L) {
            final long n4 = (random.nextLong() & Long.MAX_VALUE) % (n3 + 1L);
            final byte value = get(array, n + n3);
            set(array, n + n3, get(array, n + n4));
            set(array, n + n4, value);
        }
        return array;
    }
    
    public static byte[][] shuffle(final byte[][] array, final Random random) {
        long length = length(array);
        while (length-- != 0L) {
            final long n = (random.nextLong() & Long.MAX_VALUE) % (length + 1L);
            final byte value = get(array, length);
            set(array, length, get(array, n));
            set(array, n, value);
        }
        return array;
    }
    
    public static int get(final int[][] array, final long n) {
        return array[segment(n)][displacement(n)];
    }
    
    public static void set(final int[][] array, final long n, final int n2) {
        array[segment(n)][displacement(n)] = n2;
    }
    
    public static long length(final AtomicIntegerArray[] array) {
        final int length = array.length;
        return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length());
    }
    
    public static int get(final AtomicIntegerArray[] array, final long n) {
        return array[segment(n)].get(displacement(n));
    }
    
    public static void set(final AtomicIntegerArray[] array, final long n, final int n2) {
        array[segment(n)].set(displacement(n), n2);
    }
    
    public static int getAndSet(final AtomicIntegerArray[] array, final long n, final int n2) {
        return array[segment(n)].getAndSet(displacement(n), n2);
    }
    
    public static int getAndAdd(final AtomicIntegerArray[] array, final long n, final int n2) {
        return array[segment(n)].getAndAdd(displacement(n), n2);
    }
    
    public static int addAndGet(final AtomicIntegerArray[] array, final long n, final int n2) {
        return array[segment(n)].addAndGet(displacement(n), n2);
    }
    
    public static int getAndIncrement(final AtomicIntegerArray[] array, final long n) {
        return array[segment(n)].getAndDecrement(displacement(n));
    }
    
    public static int incrementAndGet(final AtomicIntegerArray[] array, final long n) {
        return array[segment(n)].incrementAndGet(displacement(n));
    }
    
    public static int getAndDecrement(final AtomicIntegerArray[] array, final long n) {
        return array[segment(n)].getAndDecrement(displacement(n));
    }
    
    public static int decrementAndGet(final AtomicIntegerArray[] array, final long n) {
        return array[segment(n)].decrementAndGet(displacement(n));
    }
    
    public static boolean compareAndSet(final AtomicIntegerArray[] array, final long n, final int n2, final int n3) {
        return array[segment(n)].compareAndSet(displacement(n), n2, n3);
    }
    
    public static void swap(final int[][] array, final long n, final long n2) {
        final int n3 = array[segment(n)][displacement(n)];
        array[segment(n)][displacement(n)] = array[segment(n2)][displacement(n2)];
        array[segment(n2)][displacement(n2)] = n3;
    }
    
    public static int[][] reverse(final int[][] array) {
        final long length = length(array);
        long n = length / 2L;
        while (n-- != 0L) {
            swap(array, n, length - n - 1L);
        }
        return array;
    }
    
    public static void add(final int[][] array, final long n, final int n2) {
        final int[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] += n2;
    }
    
    public static void mul(final int[][] array, final long n, final int n2) {
        final int[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] *= n2;
    }
    
    public static void incr(final int[][] array, final long n) {
        final int[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        ++array2[displacement];
    }
    
    public static void decr(final int[][] array, final long n) {
        final int[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        --array2[displacement];
    }
    
    public static long length(final int[][] array) {
        final int length = array.length;
        return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length);
    }
    
    public static void copy(final int[][] array, final long n, final int[][] array2, final long n2, long n3) {
        if (n2 <= n) {
            int segment = segment(n);
            int segment2 = segment(n2);
            displacement(n);
            displacement(n2);
            while (n3 > 0L) {
                final int n4 = (int)Math.min(n3, Math.min(array[segment].length - 134217728, array2[segment2].length - 134217728));
                if (n4 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment], 134217728, array2[segment2], 134217728, n4);
                if (134217728 + n4 == 134217728) {
                    ++segment;
                }
                if (134217728 + n4 == 134217728) {
                    ++segment2;
                }
                n3 -= n4;
            }
        }
        else {
            int segment3 = segment(n + n3);
            int segment4 = segment(n2 + n3);
            displacement(n + n3);
            displacement(n2 + n3);
            while (n3 > 0L) {
                if (134217728 == 0) {
                    --segment3;
                }
                if (134217728 == 0) {
                    --segment4;
                }
                final int n5 = (int)Math.min(n3, Math.min(134217728, 134217728));
                if (n5 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment3], 134217728 - n5, array2[segment4], 134217728 - n5, n5);
                n3 -= n5;
            }
        }
    }
    
    public static void copyFromBig(final int[][] array, final long n, final int[] array2, int n2, int i) {
        int segment = segment(n);
        displacement(n);
        while (i > 0) {
            final int min = Math.min(array[segment].length - 0, i);
            if (min == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array[segment], 0, array2, n2, min);
            if (0 + min == 134217728) {
                ++segment;
            }
            n2 += min;
            i -= min;
        }
    }
    
    public static void copyToBig(final int[] array, int n, final int[][] array2, final long n2, long n3) {
        int segment = segment(n2);
        displacement(n2);
        while (n3 > 0L) {
            final int n4 = (int)Math.min(array2[segment].length - 0, n3);
            if (n4 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array, n, array2[segment], 0, n4);
            if (0 + n4 == 134217728) {
                ++segment;
            }
            n += n4;
            n3 -= n4;
        }
    }
    
    public static int[][] wrap(final int[] array) {
        if (array.length == 0) {
            return IntBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new int[][] { array };
        }
        final int[][] bigArray = IntBigArrays.newBigArray((long)array.length);
        while (0 < bigArray.length) {
            System.arraycopy(array, (int)start(0), bigArray[0], 0, bigArray[0].length);
            int n = 0;
            ++n;
        }
        return bigArray;
    }
    
    public static int[][] ensureCapacity(final int[][] array, final long n) {
        return ensureCapacity(array, n, length(array));
    }
    
    public static int[][] forceCapacity(final int[][] array, final long n, final long n2) {
        ensureLength(n);
        final int n3 = array.length - ((array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728)) ? 1 : 0);
        final int n4 = (int)(n + 134217727L >>> 27);
        final int[][] array2 = Arrays.copyOf(array, n4);
        final int n5 = (int)(n & 0x7FFFFFFL);
        if (n5 != 0) {
            for (int i = n3; i < n4 - 1; ++i) {
                array2[i] = new int[134217728];
            }
            array2[n4 - 1] = new int[n5];
        }
        else {
            for (int j = n3; j < n4; ++j) {
                array2[j] = new int[134217728];
            }
        }
        if (n2 - n3 * 134217728L > 0L) {
            copy(array, n3 * 134217728L, array2, n3 * 134217728L, n2 - n3 * 134217728L);
        }
        return array2;
    }
    
    public static int[][] ensureCapacity(final int[][] array, final long n, final long n2) {
        return (n > length(array)) ? forceCapacity(array, n, n2) : array;
    }
    
    public static int[][] grow(final int[][] array, final long n) {
        final long length = length(array);
        return (n > length) ? grow(array, n, length) : array;
    }
    
    public static int[][] grow(final int[][] array, final long n, final long n2) {
        final long length = length(array);
        return (n > length) ? ensureCapacity(array, Math.max(length + (length >> 1), n), n2) : array;
    }
    
    public static int[][] trim(final int[][] array, final long n) {
        ensureLength(n);
        if (n >= length(array)) {
            return array;
        }
        final int n2 = (int)(n + 134217727L >>> 27);
        final int[][] array2 = Arrays.copyOf(array, n2);
        final int n3 = (int)(n & 0x7FFFFFFL);
        if (n3 != 0) {
            array2[n2 - 1] = IntArrays.trim(array2[n2 - 1], n3);
        }
        return array2;
    }
    
    public static int[][] setLength(final int[][] array, final long n) {
        final long length = length(array);
        if (n == length) {
            return array;
        }
        if (n < length) {
            return trim(array, n);
        }
        return ensureCapacity(array, n);
    }
    
    public static int[][] copy(final int[][] array, final long n, final long n2) {
        ensureOffsetLength(array, n, n2);
        final int[][] bigArray = IntBigArrays.newBigArray(n2);
        copy(array, n, bigArray, 0L, n2);
        return bigArray;
    }
    
    public static int[][] copy(final int[][] array) {
        final int[][] array2 = array.clone();
        int length = array2.length;
        while (length-- != 0) {
            array2[length] = array[length].clone();
        }
        return array2;
    }
    
    public static void fill(final int[][] array, final int n) {
        int length = array.length;
        while (length-- != 0) {
            Arrays.fill(array[length], n);
        }
    }
    
    public static void fill(final int[][] array, final long n, final long n2, final int n3) {
        final long length = length(array);
        ensureFromTo(length, n, n2);
        if (length == 0L) {
            return;
        }
        final int segment = segment(n);
        int segment2 = segment(n2);
        final int displacement = displacement(n);
        final int displacement2 = displacement(n2);
        if (segment == segment2) {
            Arrays.fill(array[segment], displacement, displacement2, n3);
            return;
        }
        if (displacement2 != 0) {
            Arrays.fill(array[segment2], 0, displacement2, n3);
        }
        while (--segment2 > segment) {
            Arrays.fill(array[segment2], n3);
        }
        Arrays.fill(array[segment], displacement, 134217728, n3);
    }
    
    public static boolean equals(final int[][] array, final int[][] array2) {
        if (length(array) != length(array2)) {
            return false;
        }
        int length = array.length;
        while (length-- != 0) {
            final int[] array3 = array[length];
            final int[] array4 = array2[length];
            int length2 = array3.length;
            while (length2-- != 0) {
                if (array3[length2] != array4[length2]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String toString(final int[][] array) {
        if (array == null) {
            return "null";
        }
        final long n = length(array) - 1L;
        if (n == -1L) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        long n2 = 0L;
        while (true) {
            sb.append(String.valueOf(get(array, n2)));
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static void ensureFromTo(final int[][] array, final long n, final long n2) {
        ensureFromTo(length(array), n, n2);
    }
    
    public static void ensureOffsetLength(final int[][] array, final long n, final long n2) {
        ensureOffsetLength(length(array), n, n2);
    }
    
    public static void ensureSameLength(final int[][] array, final int[][] array2) {
        if (length(array) != length(array2)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(array) + " != " + length(array2));
        }
    }
    
    public static int[][] shuffle(final int[][] array, final long n, final long n2, final Random random) {
        long n3 = n2 - n;
        while (n3-- != 0L) {
            final long n4 = (random.nextLong() & Long.MAX_VALUE) % (n3 + 1L);
            final int value = get(array, n + n3);
            set(array, n + n3, get(array, n + n4));
            set(array, n + n4, value);
        }
        return array;
    }
    
    public static int[][] shuffle(final int[][] array, final Random random) {
        long length = length(array);
        while (length-- != 0L) {
            final long n = (random.nextLong() & Long.MAX_VALUE) % (length + 1L);
            final int value = get(array, length);
            set(array, length, get(array, n));
            set(array, n, value);
        }
        return array;
    }
    
    public static long get(final long[][] array, final long n) {
        return array[segment(n)][displacement(n)];
    }
    
    public static void set(final long[][] array, final long n, final long n2) {
        array[segment(n)][displacement(n)] = n2;
    }
    
    public static long length(final AtomicLongArray[] array) {
        final int length = array.length;
        return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length());
    }
    
    public static long get(final AtomicLongArray[] array, final long n) {
        return array[segment(n)].get(displacement(n));
    }
    
    public static void set(final AtomicLongArray[] array, final long n, final long n2) {
        array[segment(n)].set(displacement(n), n2);
    }
    
    public static long getAndSet(final AtomicLongArray[] array, final long n, final long n2) {
        return array[segment(n)].getAndSet(displacement(n), n2);
    }
    
    public static long getAndAdd(final AtomicLongArray[] array, final long n, final long n2) {
        return array[segment(n)].getAndAdd(displacement(n), n2);
    }
    
    public static long addAndGet(final AtomicLongArray[] array, final long n, final long n2) {
        return array[segment(n)].addAndGet(displacement(n), n2);
    }
    
    public static long getAndIncrement(final AtomicLongArray[] array, final long n) {
        return array[segment(n)].getAndDecrement(displacement(n));
    }
    
    public static long incrementAndGet(final AtomicLongArray[] array, final long n) {
        return array[segment(n)].incrementAndGet(displacement(n));
    }
    
    public static long getAndDecrement(final AtomicLongArray[] array, final long n) {
        return array[segment(n)].getAndDecrement(displacement(n));
    }
    
    public static long decrementAndGet(final AtomicLongArray[] array, final long n) {
        return array[segment(n)].decrementAndGet(displacement(n));
    }
    
    public static boolean compareAndSet(final AtomicLongArray[] array, final long n, final long n2, final long n3) {
        return array[segment(n)].compareAndSet(displacement(n), n2, n3);
    }
    
    public static void swap(final long[][] array, final long n, final long n2) {
        final long n3 = array[segment(n)][displacement(n)];
        array[segment(n)][displacement(n)] = array[segment(n2)][displacement(n2)];
        array[segment(n2)][displacement(n2)] = n3;
    }
    
    public static long[][] reverse(final long[][] array) {
        final long length = length(array);
        long n = length / 2L;
        while (n-- != 0L) {
            swap(array, n, length - n - 1L);
        }
        return array;
    }
    
    public static void add(final long[][] array, final long n, final long n2) {
        final long[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] += n2;
    }
    
    public static void mul(final long[][] array, final long n, final long n2) {
        final long[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] *= n2;
    }
    
    public static void incr(final long[][] array, final long n) {
        final long[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        ++array2[displacement];
    }
    
    public static void decr(final long[][] array, final long n) {
        final long[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        --array2[displacement];
    }
    
    public static long length(final long[][] array) {
        final int length = array.length;
        return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length);
    }
    
    public static void copy(final long[][] array, final long n, final long[][] array2, final long n2, long n3) {
        if (n2 <= n) {
            int segment = segment(n);
            int segment2 = segment(n2);
            displacement(n);
            displacement(n2);
            while (n3 > 0L) {
                final int n4 = (int)Math.min(n3, Math.min(array[segment].length - 134217728, array2[segment2].length - 134217728));
                if (n4 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment], 134217728, array2[segment2], 134217728, n4);
                if (134217728 + n4 == 134217728) {
                    ++segment;
                }
                if (134217728 + n4 == 134217728) {
                    ++segment2;
                }
                n3 -= n4;
            }
        }
        else {
            int segment3 = segment(n + n3);
            int segment4 = segment(n2 + n3);
            displacement(n + n3);
            displacement(n2 + n3);
            while (n3 > 0L) {
                if (134217728 == 0) {
                    --segment3;
                }
                if (134217728 == 0) {
                    --segment4;
                }
                final int n5 = (int)Math.min(n3, Math.min(134217728, 134217728));
                if (n5 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment3], 134217728 - n5, array2[segment4], 134217728 - n5, n5);
                n3 -= n5;
            }
        }
    }
    
    public static void copyFromBig(final long[][] array, final long n, final long[] array2, int n2, int i) {
        int segment = segment(n);
        displacement(n);
        while (i > 0) {
            final int min = Math.min(array[segment].length - 0, i);
            if (min == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array[segment], 0, array2, n2, min);
            if (0 + min == 134217728) {
                ++segment;
            }
            n2 += min;
            i -= min;
        }
    }
    
    public static void copyToBig(final long[] array, int n, final long[][] array2, final long n2, long n3) {
        int segment = segment(n2);
        displacement(n2);
        while (n3 > 0L) {
            final int n4 = (int)Math.min(array2[segment].length - 0, n3);
            if (n4 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array, n, array2[segment], 0, n4);
            if (0 + n4 == 134217728) {
                ++segment;
            }
            n += n4;
            n3 -= n4;
        }
    }
    
    public static long[][] wrap(final long[] array) {
        if (array.length == 0) {
            return LongBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new long[][] { array };
        }
        final long[][] bigArray = LongBigArrays.newBigArray((long)array.length);
        while (0 < bigArray.length) {
            System.arraycopy(array, (int)start(0), bigArray[0], 0, bigArray[0].length);
            int n = 0;
            ++n;
        }
        return bigArray;
    }
    
    public static long[][] ensureCapacity(final long[][] array, final long n) {
        return ensureCapacity(array, n, length(array));
    }
    
    public static long[][] forceCapacity(final long[][] array, final long n, final long n2) {
        ensureLength(n);
        final int n3 = array.length - ((array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728)) ? 1 : 0);
        final int n4 = (int)(n + 134217727L >>> 27);
        final long[][] array2 = Arrays.copyOf(array, n4);
        final int n5 = (int)(n & 0x7FFFFFFL);
        if (n5 != 0) {
            for (int i = n3; i < n4 - 1; ++i) {
                array2[i] = new long[134217728];
            }
            array2[n4 - 1] = new long[n5];
        }
        else {
            for (int j = n3; j < n4; ++j) {
                array2[j] = new long[134217728];
            }
        }
        if (n2 - n3 * 134217728L > 0L) {
            copy(array, n3 * 134217728L, array2, n3 * 134217728L, n2 - n3 * 134217728L);
        }
        return array2;
    }
    
    public static long[][] ensureCapacity(final long[][] array, final long n, final long n2) {
        return (n > length(array)) ? forceCapacity(array, n, n2) : array;
    }
    
    public static long[][] grow(final long[][] array, final long n) {
        final long length = length(array);
        return (n > length) ? grow(array, n, length) : array;
    }
    
    public static long[][] grow(final long[][] array, final long n, final long n2) {
        final long length = length(array);
        return (n > length) ? ensureCapacity(array, Math.max(length + (length >> 1), n), n2) : array;
    }
    
    public static long[][] trim(final long[][] array, final long n) {
        ensureLength(n);
        if (n >= length(array)) {
            return array;
        }
        final int n2 = (int)(n + 134217727L >>> 27);
        final long[][] array2 = Arrays.copyOf(array, n2);
        final int n3 = (int)(n & 0x7FFFFFFL);
        if (n3 != 0) {
            array2[n2 - 1] = LongArrays.trim(array2[n2 - 1], n3);
        }
        return array2;
    }
    
    public static long[][] setLength(final long[][] array, final long n) {
        final long length = length(array);
        if (n == length) {
            return array;
        }
        if (n < length) {
            return trim(array, n);
        }
        return ensureCapacity(array, n);
    }
    
    public static long[][] copy(final long[][] array, final long n, final long n2) {
        ensureOffsetLength(array, n, n2);
        final long[][] bigArray = LongBigArrays.newBigArray(n2);
        copy(array, n, bigArray, 0L, n2);
        return bigArray;
    }
    
    public static long[][] copy(final long[][] array) {
        final long[][] array2 = array.clone();
        int length = array2.length;
        while (length-- != 0) {
            array2[length] = array[length].clone();
        }
        return array2;
    }
    
    public static void fill(final long[][] array, final long n) {
        int length = array.length;
        while (length-- != 0) {
            Arrays.fill(array[length], n);
        }
    }
    
    public static void fill(final long[][] array, final long n, final long n2, final long n3) {
        final long length = length(array);
        ensureFromTo(length, n, n2);
        if (length == 0L) {
            return;
        }
        final int segment = segment(n);
        int segment2 = segment(n2);
        final int displacement = displacement(n);
        final int displacement2 = displacement(n2);
        if (segment == segment2) {
            Arrays.fill(array[segment], displacement, displacement2, n3);
            return;
        }
        if (displacement2 != 0) {
            Arrays.fill(array[segment2], 0, displacement2, n3);
        }
        while (--segment2 > segment) {
            Arrays.fill(array[segment2], n3);
        }
        Arrays.fill(array[segment], displacement, 134217728, n3);
    }
    
    public static boolean equals(final long[][] array, final long[][] array2) {
        if (length(array) != length(array2)) {
            return false;
        }
        int length = array.length;
        while (length-- != 0) {
            final long[] array3 = array[length];
            final long[] array4 = array2[length];
            int length2 = array3.length;
            while (length2-- != 0) {
                if (array3[length2] != array4[length2]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String toString(final long[][] array) {
        if (array == null) {
            return "null";
        }
        final long n = length(array) - 1L;
        if (n == -1L) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        long n2 = 0L;
        while (true) {
            sb.append(String.valueOf(get(array, n2)));
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static void ensureFromTo(final long[][] array, final long n, final long n2) {
        ensureFromTo(length(array), n, n2);
    }
    
    public static void ensureOffsetLength(final long[][] array, final long n, final long n2) {
        ensureOffsetLength(length(array), n, n2);
    }
    
    public static void ensureSameLength(final long[][] array, final long[][] array2) {
        if (length(array) != length(array2)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(array) + " != " + length(array2));
        }
    }
    
    public static long[][] shuffle(final long[][] array, final long n, final long n2, final Random random) {
        long n3 = n2 - n;
        while (n3-- != 0L) {
            final long n4 = (random.nextLong() & Long.MAX_VALUE) % (n3 + 1L);
            final long value = get(array, n + n3);
            set(array, n + n3, get(array, n + n4));
            set(array, n + n4, value);
        }
        return array;
    }
    
    public static long[][] shuffle(final long[][] array, final Random random) {
        long length = length(array);
        while (length-- != 0L) {
            final long n = (random.nextLong() & Long.MAX_VALUE) % (length + 1L);
            final long value = get(array, length);
            set(array, length, get(array, n));
            set(array, n, value);
        }
        return array;
    }
    
    public static double get(final double[][] array, final long n) {
        return array[segment(n)][displacement(n)];
    }
    
    public static void set(final double[][] array, final long n, final double n2) {
        array[segment(n)][displacement(n)] = n2;
    }
    
    public static void swap(final double[][] array, final long n, final long n2) {
        final double n3 = array[segment(n)][displacement(n)];
        array[segment(n)][displacement(n)] = array[segment(n2)][displacement(n2)];
        array[segment(n2)][displacement(n2)] = n3;
    }
    
    public static double[][] reverse(final double[][] array) {
        final long length = length(array);
        long n = length / 2L;
        while (n-- != 0L) {
            swap(array, n, length - n - 1L);
        }
        return array;
    }
    
    public static void add(final double[][] array, final long n, final double n2) {
        final double[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] += n2;
    }
    
    public static void mul(final double[][] array, final long n, final double n2) {
        final double[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] *= n2;
    }
    
    public static void incr(final double[][] array, final long n) {
        final double[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        ++array2[displacement];
    }
    
    public static void decr(final double[][] array, final long n) {
        final double[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        --array2[displacement];
    }
    
    public static long length(final double[][] array) {
        final int length = array.length;
        return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length);
    }
    
    public static void copy(final double[][] array, final long n, final double[][] array2, final long n2, long n3) {
        if (n2 <= n) {
            int segment = segment(n);
            int segment2 = segment(n2);
            displacement(n);
            displacement(n2);
            while (n3 > 0L) {
                final int n4 = (int)Math.min(n3, Math.min(array[segment].length - 134217728, array2[segment2].length - 134217728));
                if (n4 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment], 134217728, array2[segment2], 134217728, n4);
                if (134217728 + n4 == 134217728) {
                    ++segment;
                }
                if (134217728 + n4 == 134217728) {
                    ++segment2;
                }
                n3 -= n4;
            }
        }
        else {
            int segment3 = segment(n + n3);
            int segment4 = segment(n2 + n3);
            displacement(n + n3);
            displacement(n2 + n3);
            while (n3 > 0L) {
                if (134217728 == 0) {
                    --segment3;
                }
                if (134217728 == 0) {
                    --segment4;
                }
                final int n5 = (int)Math.min(n3, Math.min(134217728, 134217728));
                if (n5 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment3], 134217728 - n5, array2[segment4], 134217728 - n5, n5);
                n3 -= n5;
            }
        }
    }
    
    public static void copyFromBig(final double[][] array, final long n, final double[] array2, int n2, int i) {
        int segment = segment(n);
        displacement(n);
        while (i > 0) {
            final int min = Math.min(array[segment].length - 0, i);
            if (min == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array[segment], 0, array2, n2, min);
            if (0 + min == 134217728) {
                ++segment;
            }
            n2 += min;
            i -= min;
        }
    }
    
    public static void copyToBig(final double[] array, int n, final double[][] array2, final long n2, long n3) {
        int segment = segment(n2);
        displacement(n2);
        while (n3 > 0L) {
            final int n4 = (int)Math.min(array2[segment].length - 0, n3);
            if (n4 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array, n, array2[segment], 0, n4);
            if (0 + n4 == 134217728) {
                ++segment;
            }
            n += n4;
            n3 -= n4;
        }
    }
    
    public static double[][] wrap(final double[] array) {
        if (array.length == 0) {
            return DoubleBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new double[][] { array };
        }
        final double[][] bigArray = DoubleBigArrays.newBigArray((long)array.length);
        while (0 < bigArray.length) {
            System.arraycopy(array, (int)start(0), bigArray[0], 0, bigArray[0].length);
            int n = 0;
            ++n;
        }
        return bigArray;
    }
    
    public static double[][] ensureCapacity(final double[][] array, final long n) {
        return ensureCapacity(array, n, length(array));
    }
    
    public static double[][] forceCapacity(final double[][] array, final long n, final long n2) {
        ensureLength(n);
        final int n3 = array.length - ((array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728)) ? 1 : 0);
        final int n4 = (int)(n + 134217727L >>> 27);
        final double[][] array2 = Arrays.copyOf(array, n4);
        final int n5 = (int)(n & 0x7FFFFFFL);
        if (n5 != 0) {
            for (int i = n3; i < n4 - 1; ++i) {
                array2[i] = new double[134217728];
            }
            array2[n4 - 1] = new double[n5];
        }
        else {
            for (int j = n3; j < n4; ++j) {
                array2[j] = new double[134217728];
            }
        }
        if (n2 - n3 * 134217728L > 0L) {
            copy(array, n3 * 134217728L, array2, n3 * 134217728L, n2 - n3 * 134217728L);
        }
        return array2;
    }
    
    public static double[][] ensureCapacity(final double[][] array, final long n, final long n2) {
        return (n > length(array)) ? forceCapacity(array, n, n2) : array;
    }
    
    public static double[][] grow(final double[][] array, final long n) {
        final long length = length(array);
        return (n > length) ? grow(array, n, length) : array;
    }
    
    public static double[][] grow(final double[][] array, final long n, final long n2) {
        final long length = length(array);
        return (n > length) ? ensureCapacity(array, Math.max(length + (length >> 1), n), n2) : array;
    }
    
    public static double[][] trim(final double[][] array, final long n) {
        ensureLength(n);
        if (n >= length(array)) {
            return array;
        }
        final int n2 = (int)(n + 134217727L >>> 27);
        final double[][] array2 = Arrays.copyOf(array, n2);
        final int n3 = (int)(n & 0x7FFFFFFL);
        if (n3 != 0) {
            array2[n2 - 1] = DoubleArrays.trim(array2[n2 - 1], n3);
        }
        return array2;
    }
    
    public static double[][] setLength(final double[][] array, final long n) {
        final long length = length(array);
        if (n == length) {
            return array;
        }
        if (n < length) {
            return trim(array, n);
        }
        return ensureCapacity(array, n);
    }
    
    public static double[][] copy(final double[][] array, final long n, final long n2) {
        ensureOffsetLength(array, n, n2);
        final double[][] bigArray = DoubleBigArrays.newBigArray(n2);
        copy(array, n, bigArray, 0L, n2);
        return bigArray;
    }
    
    public static double[][] copy(final double[][] array) {
        final double[][] array2 = array.clone();
        int length = array2.length;
        while (length-- != 0) {
            array2[length] = array[length].clone();
        }
        return array2;
    }
    
    public static void fill(final double[][] array, final double n) {
        int length = array.length;
        while (length-- != 0) {
            Arrays.fill(array[length], n);
        }
    }
    
    public static void fill(final double[][] array, final long n, final long n2, final double n3) {
        final long length = length(array);
        ensureFromTo(length, n, n2);
        if (length == 0L) {
            return;
        }
        final int segment = segment(n);
        int segment2 = segment(n2);
        final int displacement = displacement(n);
        final int displacement2 = displacement(n2);
        if (segment == segment2) {
            Arrays.fill(array[segment], displacement, displacement2, n3);
            return;
        }
        if (displacement2 != 0) {
            Arrays.fill(array[segment2], 0, displacement2, n3);
        }
        while (--segment2 > segment) {
            Arrays.fill(array[segment2], n3);
        }
        Arrays.fill(array[segment], displacement, 134217728, n3);
    }
    
    public static boolean equals(final double[][] array, final double[][] array2) {
        if (length(array) != length(array2)) {
            return false;
        }
        int length = array.length;
        while (length-- != 0) {
            final double[] array3 = array[length];
            final double[] array4 = array2[length];
            int length2 = array3.length;
            while (length2-- != 0) {
                if (Double.doubleToLongBits(array3[length2]) != Double.doubleToLongBits(array4[length2])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String toString(final double[][] array) {
        if (array == null) {
            return "null";
        }
        final long n = length(array) - 1L;
        if (n == -1L) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        long n2 = 0L;
        while (true) {
            sb.append(String.valueOf(get(array, n2)));
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static void ensureFromTo(final double[][] array, final long n, final long n2) {
        ensureFromTo(length(array), n, n2);
    }
    
    public static void ensureOffsetLength(final double[][] array, final long n, final long n2) {
        ensureOffsetLength(length(array), n, n2);
    }
    
    public static void ensureSameLength(final double[][] array, final double[][] array2) {
        if (length(array) != length(array2)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(array) + " != " + length(array2));
        }
    }
    
    public static double[][] shuffle(final double[][] array, final long n, final long n2, final Random random) {
        long n3 = n2 - n;
        while (n3-- != 0L) {
            final long n4 = (random.nextLong() & Long.MAX_VALUE) % (n3 + 1L);
            final double value = get(array, n + n3);
            set(array, n + n3, get(array, n + n4));
            set(array, n + n4, value);
        }
        return array;
    }
    
    public static double[][] shuffle(final double[][] array, final Random random) {
        long length = length(array);
        while (length-- != 0L) {
            final long n = (random.nextLong() & Long.MAX_VALUE) % (length + 1L);
            final double value = get(array, length);
            set(array, length, get(array, n));
            set(array, n, value);
        }
        return array;
    }
    
    public static boolean get(final boolean[][] array, final long n) {
        return array[segment(n)][displacement(n)];
    }
    
    public static void set(final boolean[][] array, final long n, final boolean b) {
        array[segment(n)][displacement(n)] = b;
    }
    
    public static void swap(final boolean[][] array, final long n, final long n2) {
        final boolean b = array[segment(n)][displacement(n)];
        array[segment(n)][displacement(n)] = array[segment(n2)][displacement(n2)];
        array[segment(n2)][displacement(n2)] = b;
    }
    
    public static boolean[][] reverse(final boolean[][] array) {
        final long length = length(array);
        long n = length / 2L;
        while (n-- != 0L) {
            swap(array, n, length - n - 1L);
        }
        return array;
    }
    
    public static long length(final boolean[][] array) {
        final int length = array.length;
        return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length);
    }
    
    public static void copy(final boolean[][] array, final long n, final boolean[][] array2, final long n2, long n3) {
        if (n2 <= n) {
            int segment = segment(n);
            int segment2 = segment(n2);
            displacement(n);
            displacement(n2);
            while (n3 > 0L) {
                final int n4 = (int)Math.min(n3, Math.min(array[segment].length - 134217728, array2[segment2].length - 134217728));
                if (n4 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment], 134217728, array2[segment2], 134217728, n4);
                if (134217728 + n4 == 134217728) {
                    ++segment;
                }
                if (134217728 + n4 == 134217728) {
                    ++segment2;
                }
                n3 -= n4;
            }
        }
        else {
            int segment3 = segment(n + n3);
            int segment4 = segment(n2 + n3);
            displacement(n + n3);
            displacement(n2 + n3);
            while (n3 > 0L) {
                if (134217728 == 0) {
                    --segment3;
                }
                if (134217728 == 0) {
                    --segment4;
                }
                final int n5 = (int)Math.min(n3, Math.min(134217728, 134217728));
                if (n5 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment3], 134217728 - n5, array2[segment4], 134217728 - n5, n5);
                n3 -= n5;
            }
        }
    }
    
    public static void copyFromBig(final boolean[][] array, final long n, final boolean[] array2, int n2, int i) {
        int segment = segment(n);
        displacement(n);
        while (i > 0) {
            final int min = Math.min(array[segment].length - 0, i);
            if (min == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array[segment], 0, array2, n2, min);
            if (0 + min == 134217728) {
                ++segment;
            }
            n2 += min;
            i -= min;
        }
    }
    
    public static void copyToBig(final boolean[] array, int n, final boolean[][] array2, final long n2, long n3) {
        int segment = segment(n2);
        displacement(n2);
        while (n3 > 0L) {
            final int n4 = (int)Math.min(array2[segment].length - 0, n3);
            if (n4 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array, n, array2[segment], 0, n4);
            if (0 + n4 == 134217728) {
                ++segment;
            }
            n += n4;
            n3 -= n4;
        }
    }
    
    public static boolean[][] wrap(final boolean[] array) {
        if (array.length == 0) {
            return BooleanBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new boolean[][] { array };
        }
        final boolean[][] bigArray = BooleanBigArrays.newBigArray((long)array.length);
        while (0 < bigArray.length) {
            System.arraycopy(array, (int)start(0), bigArray[0], 0, bigArray[0].length);
            int n = 0;
            ++n;
        }
        return bigArray;
    }
    
    public static boolean[][] ensureCapacity(final boolean[][] array, final long n) {
        return ensureCapacity(array, n, length(array));
    }
    
    public static boolean[][] forceCapacity(final boolean[][] array, final long n, final long n2) {
        ensureLength(n);
        final int n3 = array.length - ((array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728)) ? 1 : 0);
        final int n4 = (int)(n + 134217727L >>> 27);
        final boolean[][] array2 = Arrays.copyOf(array, n4);
        final int n5 = (int)(n & 0x7FFFFFFL);
        if (n5 != 0) {
            for (int i = n3; i < n4 - 1; ++i) {
                array2[i] = new boolean[134217728];
            }
            array2[n4 - 1] = new boolean[n5];
        }
        else {
            for (int j = n3; j < n4; ++j) {
                array2[j] = new boolean[134217728];
            }
        }
        if (n2 - n3 * 134217728L > 0L) {
            copy(array, n3 * 134217728L, array2, n3 * 134217728L, n2 - n3 * 134217728L);
        }
        return array2;
    }
    
    public static boolean[][] ensureCapacity(final boolean[][] array, final long n, final long n2) {
        return (n > length(array)) ? forceCapacity(array, n, n2) : array;
    }
    
    public static boolean[][] grow(final boolean[][] array, final long n) {
        final long length = length(array);
        return (n > length) ? grow(array, n, length) : array;
    }
    
    public static boolean[][] grow(final boolean[][] array, final long n, final long n2) {
        final long length = length(array);
        return (n > length) ? ensureCapacity(array, Math.max(length + (length >> 1), n), n2) : array;
    }
    
    public static boolean[][] trim(final boolean[][] array, final long n) {
        ensureLength(n);
        if (n >= length(array)) {
            return array;
        }
        final int n2 = (int)(n + 134217727L >>> 27);
        final boolean[][] array2 = Arrays.copyOf(array, n2);
        final int n3 = (int)(n & 0x7FFFFFFL);
        if (n3 != 0) {
            array2[n2 - 1] = BooleanArrays.trim(array2[n2 - 1], n3);
        }
        return array2;
    }
    
    public static boolean[][] setLength(final boolean[][] array, final long n) {
        final long length = length(array);
        if (n == length) {
            return array;
        }
        if (n < length) {
            return trim(array, n);
        }
        return ensureCapacity(array, n);
    }
    
    public static boolean[][] copy(final boolean[][] array, final long n, final long n2) {
        ensureOffsetLength(array, n, n2);
        final boolean[][] bigArray = BooleanBigArrays.newBigArray(n2);
        copy(array, n, bigArray, 0L, n2);
        return bigArray;
    }
    
    public static boolean[][] copy(final boolean[][] array) {
        final boolean[][] array2 = array.clone();
        int length = array2.length;
        while (length-- != 0) {
            array2[length] = array[length].clone();
        }
        return array2;
    }
    
    public static void fill(final boolean[][] array, final boolean b) {
        int length = array.length;
        while (length-- != 0) {
            Arrays.fill(array[length], b);
        }
    }
    
    public static void fill(final boolean[][] array, final long n, final long n2, final boolean b) {
        final long length = length(array);
        ensureFromTo(length, n, n2);
        if (length == 0L) {
            return;
        }
        final int segment = segment(n);
        int segment2 = segment(n2);
        final int displacement = displacement(n);
        final int displacement2 = displacement(n2);
        if (segment == segment2) {
            Arrays.fill(array[segment], displacement, displacement2, b);
            return;
        }
        if (displacement2 != 0) {
            Arrays.fill(array[segment2], 0, displacement2, b);
        }
        while (--segment2 > segment) {
            Arrays.fill(array[segment2], b);
        }
        Arrays.fill(array[segment], displacement, 134217728, b);
    }
    
    public static boolean equals(final boolean[][] array, final boolean[][] array2) {
        if (length(array) != length(array2)) {
            return false;
        }
        int length = array.length;
        while (length-- != 0) {
            final boolean[] array3 = array[length];
            final boolean[] array4 = array2[length];
            int length2 = array3.length;
            while (length2-- != 0) {
                if (array3[length2] != array4[length2]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String toString(final boolean[][] array) {
        if (array == null) {
            return "null";
        }
        final long n = length(array) - 1L;
        if (n == -1L) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        long n2 = 0L;
        while (true) {
            sb.append(String.valueOf(get(array, n2)));
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static void ensureFromTo(final boolean[][] array, final long n, final long n2) {
        ensureFromTo(length(array), n, n2);
    }
    
    public static void ensureOffsetLength(final boolean[][] array, final long n, final long n2) {
        ensureOffsetLength(length(array), n, n2);
    }
    
    public static void ensureSameLength(final boolean[][] array, final boolean[][] array2) {
        if (length(array) != length(array2)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(array) + " != " + length(array2));
        }
    }
    
    public static boolean[][] shuffle(final boolean[][] array, final long n, final long n2, final Random random) {
        long n3 = n2 - n;
        while (n3-- != 0L) {
            final long n4 = (random.nextLong() & Long.MAX_VALUE) % (n3 + 1L);
            final boolean value = get(array, n + n3);
            set(array, n + n3, get(array, n + n4));
            set(array, n + n4, value);
        }
        return array;
    }
    
    public static boolean[][] shuffle(final boolean[][] array, final Random random) {
        long length = length(array);
        while (length-- != 0L) {
            final long n = (random.nextLong() & Long.MAX_VALUE) % (length + 1L);
            final boolean value = get(array, length);
            set(array, length, get(array, n));
            set(array, n, value);
        }
        return array;
    }
    
    public static short get(final short[][] array, final long n) {
        return array[segment(n)][displacement(n)];
    }
    
    public static void set(final short[][] array, final long n, final short n2) {
        array[segment(n)][displacement(n)] = n2;
    }
    
    public static void swap(final short[][] array, final long n, final long n2) {
        final short n3 = array[segment(n)][displacement(n)];
        array[segment(n)][displacement(n)] = array[segment(n2)][displacement(n2)];
        array[segment(n2)][displacement(n2)] = n3;
    }
    
    public static short[][] reverse(final short[][] array) {
        final long length = length(array);
        long n = length / 2L;
        while (n-- != 0L) {
            swap(array, n, length - n - 1L);
        }
        return array;
    }
    
    public static void add(final short[][] array, final long n, final short n2) {
        final short[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] += n2;
    }
    
    public static void mul(final short[][] array, final long n, final short n2) {
        final short[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] *= n2;
    }
    
    public static void incr(final short[][] array, final long n) {
        final short[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        ++array2[displacement];
    }
    
    public static void decr(final short[][] array, final long n) {
        final short[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        --array2[displacement];
    }
    
    public static long length(final short[][] array) {
        final int length = array.length;
        return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length);
    }
    
    public static void copy(final short[][] array, final long n, final short[][] array2, final long n2, long n3) {
        if (n2 <= n) {
            int segment = segment(n);
            int segment2 = segment(n2);
            displacement(n);
            displacement(n2);
            while (n3 > 0L) {
                final int n4 = (int)Math.min(n3, Math.min(array[segment].length - 134217728, array2[segment2].length - 134217728));
                if (n4 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment], 134217728, array2[segment2], 134217728, n4);
                if (134217728 + n4 == 134217728) {
                    ++segment;
                }
                if (134217728 + n4 == 134217728) {
                    ++segment2;
                }
                n3 -= n4;
            }
        }
        else {
            int segment3 = segment(n + n3);
            int segment4 = segment(n2 + n3);
            displacement(n + n3);
            displacement(n2 + n3);
            while (n3 > 0L) {
                if (134217728 == 0) {
                    --segment3;
                }
                if (134217728 == 0) {
                    --segment4;
                }
                final int n5 = (int)Math.min(n3, Math.min(134217728, 134217728));
                if (n5 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment3], 134217728 - n5, array2[segment4], 134217728 - n5, n5);
                n3 -= n5;
            }
        }
    }
    
    public static void copyFromBig(final short[][] array, final long n, final short[] array2, int n2, int i) {
        int segment = segment(n);
        displacement(n);
        while (i > 0) {
            final int min = Math.min(array[segment].length - 0, i);
            if (min == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array[segment], 0, array2, n2, min);
            if (0 + min == 134217728) {
                ++segment;
            }
            n2 += min;
            i -= min;
        }
    }
    
    public static void copyToBig(final short[] array, int n, final short[][] array2, final long n2, long n3) {
        int segment = segment(n2);
        displacement(n2);
        while (n3 > 0L) {
            final int n4 = (int)Math.min(array2[segment].length - 0, n3);
            if (n4 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array, n, array2[segment], 0, n4);
            if (0 + n4 == 134217728) {
                ++segment;
            }
            n += n4;
            n3 -= n4;
        }
    }
    
    public static short[][] wrap(final short[] array) {
        if (array.length == 0) {
            return ShortBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new short[][] { array };
        }
        final short[][] bigArray = ShortBigArrays.newBigArray((long)array.length);
        while (0 < bigArray.length) {
            System.arraycopy(array, (int)start(0), bigArray[0], 0, bigArray[0].length);
            int n = 0;
            ++n;
        }
        return bigArray;
    }
    
    public static short[][] ensureCapacity(final short[][] array, final long n) {
        return ensureCapacity(array, n, length(array));
    }
    
    public static short[][] forceCapacity(final short[][] array, final long n, final long n2) {
        ensureLength(n);
        final int n3 = array.length - ((array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728)) ? 1 : 0);
        final int n4 = (int)(n + 134217727L >>> 27);
        final short[][] array2 = Arrays.copyOf(array, n4);
        final int n5 = (int)(n & 0x7FFFFFFL);
        if (n5 != 0) {
            for (int i = n3; i < n4 - 1; ++i) {
                array2[i] = new short[134217728];
            }
            array2[n4 - 1] = new short[n5];
        }
        else {
            for (int j = n3; j < n4; ++j) {
                array2[j] = new short[134217728];
            }
        }
        if (n2 - n3 * 134217728L > 0L) {
            copy(array, n3 * 134217728L, array2, n3 * 134217728L, n2 - n3 * 134217728L);
        }
        return array2;
    }
    
    public static short[][] ensureCapacity(final short[][] array, final long n, final long n2) {
        return (n > length(array)) ? forceCapacity(array, n, n2) : array;
    }
    
    public static short[][] grow(final short[][] array, final long n) {
        final long length = length(array);
        return (n > length) ? grow(array, n, length) : array;
    }
    
    public static short[][] grow(final short[][] array, final long n, final long n2) {
        final long length = length(array);
        return (n > length) ? ensureCapacity(array, Math.max(length + (length >> 1), n), n2) : array;
    }
    
    public static short[][] trim(final short[][] array, final long n) {
        ensureLength(n);
        if (n >= length(array)) {
            return array;
        }
        final int n2 = (int)(n + 134217727L >>> 27);
        final short[][] array2 = Arrays.copyOf(array, n2);
        final int n3 = (int)(n & 0x7FFFFFFL);
        if (n3 != 0) {
            array2[n2 - 1] = ShortArrays.trim(array2[n2 - 1], n3);
        }
        return array2;
    }
    
    public static short[][] setLength(final short[][] array, final long n) {
        final long length = length(array);
        if (n == length) {
            return array;
        }
        if (n < length) {
            return trim(array, n);
        }
        return ensureCapacity(array, n);
    }
    
    public static short[][] copy(final short[][] array, final long n, final long n2) {
        ensureOffsetLength(array, n, n2);
        final short[][] bigArray = ShortBigArrays.newBigArray(n2);
        copy(array, n, bigArray, 0L, n2);
        return bigArray;
    }
    
    public static short[][] copy(final short[][] array) {
        final short[][] array2 = array.clone();
        int length = array2.length;
        while (length-- != 0) {
            array2[length] = array[length].clone();
        }
        return array2;
    }
    
    public static void fill(final short[][] array, final short n) {
        int length = array.length;
        while (length-- != 0) {
            Arrays.fill(array[length], n);
        }
    }
    
    public static void fill(final short[][] array, final long n, final long n2, final short n3) {
        final long length = length(array);
        ensureFromTo(length, n, n2);
        if (length == 0L) {
            return;
        }
        final int segment = segment(n);
        int segment2 = segment(n2);
        final int displacement = displacement(n);
        final int displacement2 = displacement(n2);
        if (segment == segment2) {
            Arrays.fill(array[segment], displacement, displacement2, n3);
            return;
        }
        if (displacement2 != 0) {
            Arrays.fill(array[segment2], 0, displacement2, n3);
        }
        while (--segment2 > segment) {
            Arrays.fill(array[segment2], n3);
        }
        Arrays.fill(array[segment], displacement, 134217728, n3);
    }
    
    public static boolean equals(final short[][] array, final short[][] array2) {
        if (length(array) != length(array2)) {
            return false;
        }
        int length = array.length;
        while (length-- != 0) {
            final short[] array3 = array[length];
            final short[] array4 = array2[length];
            int length2 = array3.length;
            while (length2-- != 0) {
                if (array3[length2] != array4[length2]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String toString(final short[][] array) {
        if (array == null) {
            return "null";
        }
        final long n = length(array) - 1L;
        if (n == -1L) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        long n2 = 0L;
        while (true) {
            sb.append(String.valueOf(get(array, n2)));
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static void ensureFromTo(final short[][] array, final long n, final long n2) {
        ensureFromTo(length(array), n, n2);
    }
    
    public static void ensureOffsetLength(final short[][] array, final long n, final long n2) {
        ensureOffsetLength(length(array), n, n2);
    }
    
    public static void ensureSameLength(final short[][] array, final short[][] array2) {
        if (length(array) != length(array2)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(array) + " != " + length(array2));
        }
    }
    
    public static short[][] shuffle(final short[][] array, final long n, final long n2, final Random random) {
        long n3 = n2 - n;
        while (n3-- != 0L) {
            final long n4 = (random.nextLong() & Long.MAX_VALUE) % (n3 + 1L);
            final short value = get(array, n + n3);
            set(array, n + n3, get(array, n + n4));
            set(array, n + n4, value);
        }
        return array;
    }
    
    public static short[][] shuffle(final short[][] array, final Random random) {
        long length = length(array);
        while (length-- != 0L) {
            final long n = (random.nextLong() & Long.MAX_VALUE) % (length + 1L);
            final short value = get(array, length);
            set(array, length, get(array, n));
            set(array, n, value);
        }
        return array;
    }
    
    public static char get(final char[][] array, final long n) {
        return array[segment(n)][displacement(n)];
    }
    
    public static void set(final char[][] array, final long n, final char c) {
        array[segment(n)][displacement(n)] = c;
    }
    
    public static void swap(final char[][] array, final long n, final long n2) {
        final char c = array[segment(n)][displacement(n)];
        array[segment(n)][displacement(n)] = array[segment(n2)][displacement(n2)];
        array[segment(n2)][displacement(n2)] = c;
    }
    
    public static char[][] reverse(final char[][] array) {
        final long length = length(array);
        long n = length / 2L;
        while (n-- != 0L) {
            swap(array, n, length - n - 1L);
        }
        return array;
    }
    
    public static void add(final char[][] array, final long n, final char c) {
        final char[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] += c;
    }
    
    public static void mul(final char[][] array, final long n, final char c) {
        final char[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] *= c;
    }
    
    public static void incr(final char[][] array, final long n) {
        final char[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        ++array2[displacement];
    }
    
    public static void decr(final char[][] array, final long n) {
        final char[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        --array2[displacement];
    }
    
    public static long length(final char[][] array) {
        final int length = array.length;
        return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length);
    }
    
    public static void copy(final char[][] array, final long n, final char[][] array2, final long n2, long n3) {
        if (n2 <= n) {
            int segment = segment(n);
            int segment2 = segment(n2);
            displacement(n);
            displacement(n2);
            while (n3 > 0L) {
                final int n4 = (int)Math.min(n3, Math.min(array[segment].length - 134217728, array2[segment2].length - 134217728));
                if (n4 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment], 134217728, array2[segment2], 134217728, n4);
                if (134217728 + n4 == 134217728) {
                    ++segment;
                }
                if (134217728 + n4 == 134217728) {
                    ++segment2;
                }
                n3 -= n4;
            }
        }
        else {
            int segment3 = segment(n + n3);
            int segment4 = segment(n2 + n3);
            displacement(n + n3);
            displacement(n2 + n3);
            while (n3 > 0L) {
                if (134217728 == 0) {
                    --segment3;
                }
                if (134217728 == 0) {
                    --segment4;
                }
                final int n5 = (int)Math.min(n3, Math.min(134217728, 134217728));
                if (n5 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment3], 134217728 - n5, array2[segment4], 134217728 - n5, n5);
                n3 -= n5;
            }
        }
    }
    
    public static void copyFromBig(final char[][] array, final long n, final char[] array2, int n2, int i) {
        int segment = segment(n);
        displacement(n);
        while (i > 0) {
            final int min = Math.min(array[segment].length - 0, i);
            if (min == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array[segment], 0, array2, n2, min);
            if (0 + min == 134217728) {
                ++segment;
            }
            n2 += min;
            i -= min;
        }
    }
    
    public static void copyToBig(final char[] array, int n, final char[][] array2, final long n2, long n3) {
        int segment = segment(n2);
        displacement(n2);
        while (n3 > 0L) {
            final int n4 = (int)Math.min(array2[segment].length - 0, n3);
            if (n4 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array, n, array2[segment], 0, n4);
            if (0 + n4 == 134217728) {
                ++segment;
            }
            n += n4;
            n3 -= n4;
        }
    }
    
    public static char[][] wrap(final char[] array) {
        if (array.length == 0) {
            return CharBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new char[][] { array };
        }
        final char[][] bigArray = CharBigArrays.newBigArray((long)array.length);
        while (0 < bigArray.length) {
            System.arraycopy(array, (int)start(0), bigArray[0], 0, bigArray[0].length);
            int n = 0;
            ++n;
        }
        return bigArray;
    }
    
    public static char[][] ensureCapacity(final char[][] array, final long n) {
        return ensureCapacity(array, n, length(array));
    }
    
    public static char[][] forceCapacity(final char[][] array, final long n, final long n2) {
        ensureLength(n);
        final int n3 = array.length - ((array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728)) ? 1 : 0);
        final int n4 = (int)(n + 134217727L >>> 27);
        final char[][] array2 = Arrays.copyOf(array, n4);
        final int n5 = (int)(n & 0x7FFFFFFL);
        if (n5 != 0) {
            for (int i = n3; i < n4 - 1; ++i) {
                array2[i] = new char[134217728];
            }
            array2[n4 - 1] = new char[n5];
        }
        else {
            for (int j = n3; j < n4; ++j) {
                array2[j] = new char[134217728];
            }
        }
        if (n2 - n3 * 134217728L > 0L) {
            copy(array, n3 * 134217728L, array2, n3 * 134217728L, n2 - n3 * 134217728L);
        }
        return array2;
    }
    
    public static char[][] ensureCapacity(final char[][] array, final long n, final long n2) {
        return (n > length(array)) ? forceCapacity(array, n, n2) : array;
    }
    
    public static char[][] grow(final char[][] array, final long n) {
        final long length = length(array);
        return (n > length) ? grow(array, n, length) : array;
    }
    
    public static char[][] grow(final char[][] array, final long n, final long n2) {
        final long length = length(array);
        return (n > length) ? ensureCapacity(array, Math.max(length + (length >> 1), n), n2) : array;
    }
    
    public static char[][] trim(final char[][] array, final long n) {
        ensureLength(n);
        if (n >= length(array)) {
            return array;
        }
        final int n2 = (int)(n + 134217727L >>> 27);
        final char[][] array2 = Arrays.copyOf(array, n2);
        final int n3 = (int)(n & 0x7FFFFFFL);
        if (n3 != 0) {
            array2[n2 - 1] = CharArrays.trim(array2[n2 - 1], n3);
        }
        return array2;
    }
    
    public static char[][] setLength(final char[][] array, final long n) {
        final long length = length(array);
        if (n == length) {
            return array;
        }
        if (n < length) {
            return trim(array, n);
        }
        return ensureCapacity(array, n);
    }
    
    public static char[][] copy(final char[][] array, final long n, final long n2) {
        ensureOffsetLength(array, n, n2);
        final char[][] bigArray = CharBigArrays.newBigArray(n2);
        copy(array, n, bigArray, 0L, n2);
        return bigArray;
    }
    
    public static char[][] copy(final char[][] array) {
        final char[][] array2 = array.clone();
        int length = array2.length;
        while (length-- != 0) {
            array2[length] = array[length].clone();
        }
        return array2;
    }
    
    public static void fill(final char[][] array, final char c) {
        int length = array.length;
        while (length-- != 0) {
            Arrays.fill(array[length], c);
        }
    }
    
    public static void fill(final char[][] array, final long n, final long n2, final char c) {
        final long length = length(array);
        ensureFromTo(length, n, n2);
        if (length == 0L) {
            return;
        }
        final int segment = segment(n);
        int segment2 = segment(n2);
        final int displacement = displacement(n);
        final int displacement2 = displacement(n2);
        if (segment == segment2) {
            Arrays.fill(array[segment], displacement, displacement2, c);
            return;
        }
        if (displacement2 != 0) {
            Arrays.fill(array[segment2], 0, displacement2, c);
        }
        while (--segment2 > segment) {
            Arrays.fill(array[segment2], c);
        }
        Arrays.fill(array[segment], displacement, 134217728, c);
    }
    
    public static boolean equals(final char[][] array, final char[][] array2) {
        if (length(array) != length(array2)) {
            return false;
        }
        int length = array.length;
        while (length-- != 0) {
            final char[] array3 = array[length];
            final char[] array4 = array2[length];
            int length2 = array3.length;
            while (length2-- != 0) {
                if (array3[length2] != array4[length2]) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String toString(final char[][] array) {
        if (array == null) {
            return "null";
        }
        final long n = length(array) - 1L;
        if (n == -1L) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        long n2 = 0L;
        while (true) {
            sb.append(String.valueOf(get(array, n2)));
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static void ensureFromTo(final char[][] array, final long n, final long n2) {
        ensureFromTo(length(array), n, n2);
    }
    
    public static void ensureOffsetLength(final char[][] array, final long n, final long n2) {
        ensureOffsetLength(length(array), n, n2);
    }
    
    public static void ensureSameLength(final char[][] array, final char[][] array2) {
        if (length(array) != length(array2)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(array) + " != " + length(array2));
        }
    }
    
    public static char[][] shuffle(final char[][] array, final long n, final long n2, final Random random) {
        long n3 = n2 - n;
        while (n3-- != 0L) {
            final long n4 = (random.nextLong() & Long.MAX_VALUE) % (n3 + 1L);
            final char value = get(array, n + n3);
            set(array, n + n3, get(array, n + n4));
            set(array, n + n4, value);
        }
        return array;
    }
    
    public static char[][] shuffle(final char[][] array, final Random random) {
        long length = length(array);
        while (length-- != 0L) {
            final long n = (random.nextLong() & Long.MAX_VALUE) % (length + 1L);
            final char value = get(array, length);
            set(array, length, get(array, n));
            set(array, n, value);
        }
        return array;
    }
    
    public static float get(final float[][] array, final long n) {
        return array[segment(n)][displacement(n)];
    }
    
    public static void set(final float[][] array, final long n, final float n2) {
        array[segment(n)][displacement(n)] = n2;
    }
    
    public static void swap(final float[][] array, final long n, final long n2) {
        final float n3 = array[segment(n)][displacement(n)];
        array[segment(n)][displacement(n)] = array[segment(n2)][displacement(n2)];
        array[segment(n2)][displacement(n2)] = n3;
    }
    
    public static float[][] reverse(final float[][] array) {
        final long length = length(array);
        long n = length / 2L;
        while (n-- != 0L) {
            swap(array, n, length - n - 1L);
        }
        return array;
    }
    
    public static void add(final float[][] array, final long n, final float n2) {
        final float[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] += n2;
    }
    
    public static void mul(final float[][] array, final long n, final float n2) {
        final float[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        array2[displacement] *= n2;
    }
    
    public static void incr(final float[][] array, final long n) {
        final float[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        ++array2[displacement];
    }
    
    public static void decr(final float[][] array, final long n) {
        final float[] array2 = array[segment(n)];
        final int displacement = displacement(n);
        --array2[displacement];
    }
    
    public static long length(final float[][] array) {
        final int length = array.length;
        return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length);
    }
    
    public static void copy(final float[][] array, final long n, final float[][] array2, final long n2, long n3) {
        if (n2 <= n) {
            int segment = segment(n);
            int segment2 = segment(n2);
            displacement(n);
            displacement(n2);
            while (n3 > 0L) {
                final int n4 = (int)Math.min(n3, Math.min(array[segment].length - 134217728, array2[segment2].length - 134217728));
                if (n4 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment], 134217728, array2[segment2], 134217728, n4);
                if (134217728 + n4 == 134217728) {
                    ++segment;
                }
                if (134217728 + n4 == 134217728) {
                    ++segment2;
                }
                n3 -= n4;
            }
        }
        else {
            int segment3 = segment(n + n3);
            int segment4 = segment(n2 + n3);
            displacement(n + n3);
            displacement(n2 + n3);
            while (n3 > 0L) {
                if (134217728 == 0) {
                    --segment3;
                }
                if (134217728 == 0) {
                    --segment4;
                }
                final int n5 = (int)Math.min(n3, Math.min(134217728, 134217728));
                if (n5 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment3], 134217728 - n5, array2[segment4], 134217728 - n5, n5);
                n3 -= n5;
            }
        }
    }
    
    public static void copyFromBig(final float[][] array, final long n, final float[] array2, int n2, int i) {
        int segment = segment(n);
        displacement(n);
        while (i > 0) {
            final int min = Math.min(array[segment].length - 0, i);
            if (min == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array[segment], 0, array2, n2, min);
            if (0 + min == 134217728) {
                ++segment;
            }
            n2 += min;
            i -= min;
        }
    }
    
    public static void copyToBig(final float[] array, int n, final float[][] array2, final long n2, long n3) {
        int segment = segment(n2);
        displacement(n2);
        while (n3 > 0L) {
            final int n4 = (int)Math.min(array2[segment].length - 0, n3);
            if (n4 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array, n, array2[segment], 0, n4);
            if (0 + n4 == 134217728) {
                ++segment;
            }
            n += n4;
            n3 -= n4;
        }
    }
    
    public static float[][] wrap(final float[] array) {
        if (array.length == 0) {
            return FloatBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            return new float[][] { array };
        }
        final float[][] bigArray = FloatBigArrays.newBigArray((long)array.length);
        while (0 < bigArray.length) {
            System.arraycopy(array, (int)start(0), bigArray[0], 0, bigArray[0].length);
            int n = 0;
            ++n;
        }
        return bigArray;
    }
    
    public static float[][] ensureCapacity(final float[][] array, final long n) {
        return ensureCapacity(array, n, length(array));
    }
    
    public static float[][] forceCapacity(final float[][] array, final long n, final long n2) {
        ensureLength(n);
        final int n3 = array.length - ((array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728)) ? 1 : 0);
        final int n4 = (int)(n + 134217727L >>> 27);
        final float[][] array2 = Arrays.copyOf(array, n4);
        final int n5 = (int)(n & 0x7FFFFFFL);
        if (n5 != 0) {
            for (int i = n3; i < n4 - 1; ++i) {
                array2[i] = new float[134217728];
            }
            array2[n4 - 1] = new float[n5];
        }
        else {
            for (int j = n3; j < n4; ++j) {
                array2[j] = new float[134217728];
            }
        }
        if (n2 - n3 * 134217728L > 0L) {
            copy(array, n3 * 134217728L, array2, n3 * 134217728L, n2 - n3 * 134217728L);
        }
        return array2;
    }
    
    public static float[][] ensureCapacity(final float[][] array, final long n, final long n2) {
        return (n > length(array)) ? forceCapacity(array, n, n2) : array;
    }
    
    public static float[][] grow(final float[][] array, final long n) {
        final long length = length(array);
        return (n > length) ? grow(array, n, length) : array;
    }
    
    public static float[][] grow(final float[][] array, final long n, final long n2) {
        final long length = length(array);
        return (n > length) ? ensureCapacity(array, Math.max(length + (length >> 1), n), n2) : array;
    }
    
    public static float[][] trim(final float[][] array, final long n) {
        ensureLength(n);
        if (n >= length(array)) {
            return array;
        }
        final int n2 = (int)(n + 134217727L >>> 27);
        final float[][] array2 = Arrays.copyOf(array, n2);
        final int n3 = (int)(n & 0x7FFFFFFL);
        if (n3 != 0) {
            array2[n2 - 1] = FloatArrays.trim(array2[n2 - 1], n3);
        }
        return array2;
    }
    
    public static float[][] setLength(final float[][] array, final long n) {
        final long length = length(array);
        if (n == length) {
            return array;
        }
        if (n < length) {
            return trim(array, n);
        }
        return ensureCapacity(array, n);
    }
    
    public static float[][] copy(final float[][] array, final long n, final long n2) {
        ensureOffsetLength(array, n, n2);
        final float[][] bigArray = FloatBigArrays.newBigArray(n2);
        copy(array, n, bigArray, 0L, n2);
        return bigArray;
    }
    
    public static float[][] copy(final float[][] array) {
        final float[][] array2 = array.clone();
        int length = array2.length;
        while (length-- != 0) {
            array2[length] = array[length].clone();
        }
        return array2;
    }
    
    public static void fill(final float[][] array, final float n) {
        int length = array.length;
        while (length-- != 0) {
            Arrays.fill(array[length], n);
        }
    }
    
    public static void fill(final float[][] array, final long n, final long n2, final float n3) {
        final long length = length(array);
        ensureFromTo(length, n, n2);
        if (length == 0L) {
            return;
        }
        final int segment = segment(n);
        int segment2 = segment(n2);
        final int displacement = displacement(n);
        final int displacement2 = displacement(n2);
        if (segment == segment2) {
            Arrays.fill(array[segment], displacement, displacement2, n3);
            return;
        }
        if (displacement2 != 0) {
            Arrays.fill(array[segment2], 0, displacement2, n3);
        }
        while (--segment2 > segment) {
            Arrays.fill(array[segment2], n3);
        }
        Arrays.fill(array[segment], displacement, 134217728, n3);
    }
    
    public static boolean equals(final float[][] array, final float[][] array2) {
        if (length(array) != length(array2)) {
            return false;
        }
        int length = array.length;
        while (length-- != 0) {
            final float[] array3 = array[length];
            final float[] array4 = array2[length];
            int length2 = array3.length;
            while (length2-- != 0) {
                if (Float.floatToIntBits(array3[length2]) != Float.floatToIntBits(array4[length2])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String toString(final float[][] array) {
        if (array == null) {
            return "null";
        }
        final long n = length(array) - 1L;
        if (n == -1L) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        long n2 = 0L;
        while (true) {
            sb.append(String.valueOf(get(array, n2)));
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static void ensureFromTo(final float[][] array, final long n, final long n2) {
        ensureFromTo(length(array), n, n2);
    }
    
    public static void ensureOffsetLength(final float[][] array, final long n, final long n2) {
        ensureOffsetLength(length(array), n, n2);
    }
    
    public static void ensureSameLength(final float[][] array, final float[][] array2) {
        if (length(array) != length(array2)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(array) + " != " + length(array2));
        }
    }
    
    public static float[][] shuffle(final float[][] array, final long n, final long n2, final Random random) {
        long n3 = n2 - n;
        while (n3-- != 0L) {
            final long n4 = (random.nextLong() & Long.MAX_VALUE) % (n3 + 1L);
            final float value = get(array, n + n3);
            set(array, n + n3, get(array, n + n4));
            set(array, n + n4, value);
        }
        return array;
    }
    
    public static float[][] shuffle(final float[][] array, final Random random) {
        long length = length(array);
        while (length-- != 0L) {
            final long n = (random.nextLong() & Long.MAX_VALUE) % (length + 1L);
            final float value = get(array, length);
            set(array, length, get(array, n));
            set(array, n, value);
        }
        return array;
    }
    
    public static Object get(final Object[][] array, final long n) {
        return array[segment(n)][displacement(n)];
    }
    
    public static void set(final Object[][] array, final long n, final Object o) {
        array[segment(n)][displacement(n)] = o;
    }
    
    public static void swap(final Object[][] array, final long n, final long n2) {
        final Object o = array[segment(n)][displacement(n)];
        array[segment(n)][displacement(n)] = array[segment(n2)][displacement(n2)];
        array[segment(n2)][displacement(n2)] = o;
    }
    
    public static Object[][] reverse(final Object[][] array) {
        final long length = length(array);
        long n = length / 2L;
        while (n-- != 0L) {
            swap(array, n, length - n - 1L);
        }
        return array;
    }
    
    public static long length(final Object[][] array) {
        final int length = array.length;
        return (length == 0) ? 0L : (start(length - 1) + array[length - 1].length);
    }
    
    public static void copy(final Object[][] array, final long n, final Object[][] array2, final long n2, long n3) {
        if (n2 <= n) {
            int segment = segment(n);
            int segment2 = segment(n2);
            displacement(n);
            displacement(n2);
            while (n3 > 0L) {
                final int n4 = (int)Math.min(n3, Math.min(array[segment].length - 134217728, array2[segment2].length - 134217728));
                if (n4 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment], 134217728, array2[segment2], 134217728, n4);
                if (134217728 + n4 == 134217728) {
                    ++segment;
                }
                if (134217728 + n4 == 134217728) {
                    ++segment2;
                }
                n3 -= n4;
            }
        }
        else {
            int segment3 = segment(n + n3);
            int segment4 = segment(n2 + n3);
            displacement(n + n3);
            displacement(n2 + n3);
            while (n3 > 0L) {
                if (134217728 == 0) {
                    --segment3;
                }
                if (134217728 == 0) {
                    --segment4;
                }
                final int n5 = (int)Math.min(n3, Math.min(134217728, 134217728));
                if (n5 == 0) {
                    throw new ArrayIndexOutOfBoundsException();
                }
                System.arraycopy(array[segment3], 134217728 - n5, array2[segment4], 134217728 - n5, n5);
                n3 -= n5;
            }
        }
    }
    
    public static void copyFromBig(final Object[][] array, final long n, final Object[] array2, int n2, int i) {
        int segment = segment(n);
        displacement(n);
        while (i > 0) {
            final int min = Math.min(array[segment].length - 0, i);
            if (min == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array[segment], 0, array2, n2, min);
            if (0 + min == 134217728) {
                ++segment;
            }
            n2 += min;
            i -= min;
        }
    }
    
    public static void copyToBig(final Object[] array, int n, final Object[][] array2, final long n2, long n3) {
        int segment = segment(n2);
        displacement(n2);
        while (n3 > 0L) {
            final int n4 = (int)Math.min(array2[segment].length - 0, n3);
            if (n4 == 0) {
                throw new ArrayIndexOutOfBoundsException();
            }
            System.arraycopy(array, n, array2[segment], 0, n4);
            if (0 + n4 == 134217728) {
                ++segment;
            }
            n += n4;
            n3 -= n4;
        }
    }
    
    public static Object[][] wrap(final Object[] array) {
        if (array.length == 0 && array.getClass() == Object[].class) {
            return ObjectBigArrays.EMPTY_BIG_ARRAY;
        }
        if (array.length <= 134217728) {
            final Object[][] array2 = (Object[][])Array.newInstance(array.getClass(), 1);
            array2[0] = array;
            return array2;
        }
        final Object[][] bigArray = ObjectBigArrays.newBigArray((Class)array.getClass(), (long)array.length);
        while (0 < bigArray.length) {
            System.arraycopy(array, (int)start(0), bigArray[0], 0, bigArray[0].length);
            int n = 0;
            ++n;
        }
        return bigArray;
    }
    
    public static Object[][] ensureCapacity(final Object[][] array, final long n) {
        return ensureCapacity(array, n, length(array));
    }
    
    public static Object[][] forceCapacity(final Object[][] array, final long n, final long n2) {
        ensureLength(n);
        final int n3 = array.length - ((array.length != 0 && (array.length <= 0 || array[array.length - 1].length != 134217728)) ? 1 : 0);
        final int n4 = (int)(n + 134217727L >>> 27);
        final Object[][] array2 = Arrays.copyOf(array, n4);
        final Class<?> componentType = array.getClass().getComponentType();
        final int n5 = (int)(n & 0x7FFFFFFL);
        if (n5 != 0) {
            for (int i = n3; i < n4 - 1; ++i) {
                array2[i] = (Object[])Array.newInstance(componentType.getComponentType(), 134217728);
            }
            array2[n4 - 1] = (Object[])Array.newInstance(componentType.getComponentType(), n5);
        }
        else {
            for (int j = n3; j < n4; ++j) {
                array2[j] = (Object[])Array.newInstance(componentType.getComponentType(), 134217728);
            }
        }
        if (n2 - n3 * 134217728L > 0L) {
            copy(array, n3 * 134217728L, array2, n3 * 134217728L, n2 - n3 * 134217728L);
        }
        return array2;
    }
    
    public static Object[][] ensureCapacity(final Object[][] array, final long n, final long n2) {
        return (n > length(array)) ? forceCapacity(array, n, n2) : array;
    }
    
    public static Object[][] grow(final Object[][] array, final long n) {
        final long length = length(array);
        return (n > length) ? grow(array, n, length) : array;
    }
    
    public static Object[][] grow(final Object[][] array, final long n, final long n2) {
        final long length = length(array);
        return (n > length) ? ensureCapacity(array, Math.max(length + (length >> 1), n), n2) : array;
    }
    
    public static Object[][] trim(final Object[][] array, final long n) {
        ensureLength(n);
        if (n >= length(array)) {
            return array;
        }
        final int n2 = (int)(n + 134217727L >>> 27);
        final Object[][] array2 = Arrays.copyOf(array, n2);
        final int n3 = (int)(n & 0x7FFFFFFL);
        if (n3 != 0) {
            array2[n2 - 1] = ObjectArrays.trim(array2[n2 - 1], n3);
        }
        return array2;
    }
    
    public static Object[][] setLength(final Object[][] array, final long n) {
        final long length = length(array);
        if (n == length) {
            return array;
        }
        if (n < length) {
            return trim(array, n);
        }
        return ensureCapacity(array, n);
    }
    
    public static Object[][] copy(final Object[][] array, final long n, final long n2) {
        ensureOffsetLength(array, n, n2);
        final Object[][] bigArray = ObjectBigArrays.newBigArray(array, n2);
        copy(array, n, bigArray, 0L, n2);
        return bigArray;
    }
    
    public static Object[][] copy(final Object[][] array) {
        final Object[][] array2 = array.clone();
        int length = array2.length;
        while (length-- != 0) {
            array2[length] = array[length].clone();
        }
        return array2;
    }
    
    public static void fill(final Object[][] array, final Object o) {
        int length = array.length;
        while (length-- != 0) {
            Arrays.fill(array[length], o);
        }
    }
    
    public static void fill(final Object[][] array, final long n, final long n2, final Object o) {
        final long length = length(array);
        ensureFromTo(length, n, n2);
        if (length == 0L) {
            return;
        }
        final int segment = segment(n);
        int segment2 = segment(n2);
        final int displacement = displacement(n);
        final int displacement2 = displacement(n2);
        if (segment == segment2) {
            Arrays.fill(array[segment], displacement, displacement2, o);
            return;
        }
        if (displacement2 != 0) {
            Arrays.fill(array[segment2], 0, displacement2, o);
        }
        while (--segment2 > segment) {
            Arrays.fill(array[segment2], o);
        }
        Arrays.fill(array[segment], displacement, 134217728, o);
    }
    
    public static boolean equals(final Object[][] array, final Object[][] array2) {
        if (length(array) != length(array2)) {
            return false;
        }
        int length = array.length;
        while (length-- != 0) {
            final Object[] array3 = array[length];
            final Object[] array4 = array2[length];
            int length2 = array3.length;
            while (length2-- != 0) {
                if (!Objects.equals(array3[length2], array4[length2])) {
                    return false;
                }
            }
        }
        return true;
    }
    
    public static String toString(final Object[][] array) {
        if (array == null) {
            return "null";
        }
        final long n = length(array) - 1L;
        if (n == -1L) {
            return "[]";
        }
        final StringBuilder sb = new StringBuilder();
        sb.append('[');
        long n2 = 0L;
        while (true) {
            sb.append(String.valueOf(get(array, n2)));
            if (n2 == n) {
                break;
            }
            sb.append(", ");
            ++n2;
        }
        return sb.append(']').toString();
    }
    
    public static void ensureFromTo(final Object[][] array, final long n, final long n2) {
        ensureFromTo(length(array), n, n2);
    }
    
    public static void ensureOffsetLength(final Object[][] array, final long n, final long n2) {
        ensureOffsetLength(length(array), n, n2);
    }
    
    public static void ensureSameLength(final Object[][] array, final Object[][] array2) {
        if (length(array) != length(array2)) {
            throw new IllegalArgumentException("Array size mismatch: " + length(array) + " != " + length(array2));
        }
    }
    
    public static Object[][] shuffle(final Object[][] array, final long n, final long n2, final Random random) {
        long n3 = n2 - n;
        while (n3-- != 0L) {
            final long n4 = (random.nextLong() & Long.MAX_VALUE) % (n3 + 1L);
            final Object value = get(array, n + n3);
            set(array, n + n3, get(array, n + n4));
            set(array, n + n4, value);
        }
        return array;
    }
    
    public static Object[][] shuffle(final Object[][] array, final Random random) {
        long length = length(array);
        while (length-- != 0L) {
            final long n = (random.nextLong() & Long.MAX_VALUE) % (length + 1L);
            final Object value = get(array, length);
            set(array, length, get(array, n));
            set(array, n, value);
        }
        return array;
    }
    
    public static void main(final String[] array) {
        final int[][] bigArray = IntBigArrays.newBigArray(1L << Integer.parseInt(array[0]));
        while (true) {
            final int n = 10;
            int n2 = 0;
            --n2;
            if (n == 0) {
                return;
            }
            final long n3 = -System.currentTimeMillis();
            long n4 = 0L;
            long length = length(bigArray);
            while (length-- != 0L) {
                n4 ^= (length ^ (long)get(bigArray, length));
            }
            if (n4 == 0L) {
                System.err.println();
            }
            System.out.println("Single loop: " + (n3 + System.currentTimeMillis()) + "ms");
            final long n5 = -System.currentTimeMillis();
            long n6 = 0L;
            int length2 = bigArray.length;
            while (length2-- != 0) {
                final int[] array2 = bigArray[length2];
                int length3 = array2.length;
                while (length3-- != 0) {
                    n6 ^= ((long)array2[length3] ^ index(length2, length3));
                }
            }
            if (n6 == 0L) {
                System.err.println();
            }
            if (n4 != n6) {
                throw new AssertionError();
            }
            System.out.println("Double loop: " + (n5 + System.currentTimeMillis()) + "ms");
            final long n7 = 0L;
            long length4 = length(bigArray);
            int length5 = bigArray.length;
            while (length5-- != 0) {
                final int[] array3 = bigArray[length5];
                int length6 = array3.length;
                while (length6-- != 0) {
                    n6 ^= ((long)array3[length6] ^ --length4);
                }
            }
            if (n7 == 0L) {
                System.err.println();
            }
            if (n4 != n7) {
                throw new AssertionError();
            }
            System.out.println("Double loop (with additional index): " + (n5 + System.currentTimeMillis()) + "ms");
        }
    }
}
