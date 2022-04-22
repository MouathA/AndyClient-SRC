package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.util.concurrent.atomic.*;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;

public final class IntArrays
{
    public static final int[] EMPTY_ARRAY;
    public static final int[] DEFAULT_EMPTY_ARRAY;
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;
    private static final int MERGESORT_NO_REC = 16;
    private static final int DIGIT_BITS = 8;
    private static final int DIGIT_MASK = 255;
    private static final int DIGITS_PER_ELEMENT = 4;
    private static final int RADIXSORT_NO_REC = 1024;
    private static final int PARALLEL_RADIXSORT_NO_FORK = 1024;
    static final int RADIX_SORT_MIN_THRESHOLD = 2000;
    protected static final Segment POISON_PILL;
    public static final Hash.Strategy HASH_STRATEGY;
    
    private IntArrays() {
    }
    
    public static int[] forceCapacity(final int[] array, final int n, final int n2) {
        final int[] array2 = new int[n];
        System.arraycopy(array, 0, array2, 0, n2);
        return array2;
    }
    
    public static int[] ensureCapacity(final int[] array, final int n) {
        return ensureCapacity(array, n, array.length);
    }
    
    public static int[] ensureCapacity(final int[] array, final int n, final int n2) {
        return (n > array.length) ? forceCapacity(array, n, n2) : array;
    }
    
    public static int[] grow(final int[] array, final int n) {
        return grow(array, n, array.length);
    }
    
    public static int[] grow(final int[] array, final int n, final int n2) {
        if (n > array.length) {
            final int[] array2 = new int[(int)Math.max(Math.min(array.length + (long)(array.length >> 1), 2147483639L), n)];
            System.arraycopy(array, 0, array2, 0, n2);
            return array2;
        }
        return array;
    }
    
    public static int[] trim(final int[] array, final int n) {
        if (n >= array.length) {
            return array;
        }
        final int[] array2 = (n == 0) ? IntArrays.EMPTY_ARRAY : new int[n];
        System.arraycopy(array, 0, array2, 0, n);
        return array2;
    }
    
    public static int[] setLength(final int[] array, final int n) {
        if (n == array.length) {
            return array;
        }
        if (n < array.length) {
            return trim(array, n);
        }
        return ensureCapacity(array, n);
    }
    
    public static int[] copy(final int[] array, final int n, final int n2) {
        ensureOffsetLength(array, n, n2);
        final int[] array2 = (n2 == 0) ? IntArrays.EMPTY_ARRAY : new int[n2];
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public static int[] copy(final int[] array) {
        return array.clone();
    }
    
    @Deprecated
    public static void fill(final int[] array, final int n) {
        int length = array.length;
        while (length-- != 0) {
            array[length] = n;
        }
    }
    
    @Deprecated
    public static void fill(final int[] array, final int n, int n2, final int n3) {
        ensureFromTo(array, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                array[n2] = n3;
            }
        }
        else {
            for (int i = n; i < n2; ++i) {
                array[i] = n3;
            }
        }
    }
    
    @Deprecated
    public static boolean equals(final int[] array, final int[] array2) {
        int length = array.length;
        if (length != array2.length) {
            return false;
        }
        while (length-- != 0) {
            if (array[length] != array2[length]) {
                return false;
            }
        }
        return true;
    }
    
    public static void ensureFromTo(final int[] array, final int n, final int n2) {
        Arrays.ensureFromTo(array.length, n, n2);
    }
    
    public static void ensureOffsetLength(final int[] array, final int n, final int n2) {
        Arrays.ensureOffsetLength(array.length, n, n2);
    }
    
    public static void ensureSameLength(final int[] array, final int[] array2) {
        if (array.length != array2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + array.length + " != " + array2.length);
        }
    }
    
    private static ForkJoinPool getPool() {
        final ForkJoinPool pool = ForkJoinTask.getPool();
        return (pool == null) ? ForkJoinPool.commonPool() : pool;
    }
    
    public static void swap(final int[] array, final int n, final int n2) {
        final int n3 = array[n];
        array[n] = array[n2];
        array[n2] = n3;
    }
    
    public static void swap(final int[] array, int n, int n2, final int n3) {
        for (int i = 0; i < n3; ++i, ++n, ++n2) {
            swap(array, n, n2);
        }
    }
    
    private static int med3(final int[] array, final int n, final int n2, final int n3, final IntComparator intComparator) {
        final int compare = intComparator.compare(array[n], array[n2]);
        final int compare2 = intComparator.compare(array[n], array[n3]);
        final int compare3 = intComparator.compare(array[n2], array[n3]);
        return (compare < 0) ? ((compare3 < 0) ? n2 : ((compare2 < 0) ? n3 : n)) : ((compare3 > 0) ? n2 : ((compare2 > 0) ? n3 : n));
    }
    
    private static void selectionSort(final int[] array, final int n, final int n2, final IntComparator intComparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (intComparator.compare(array[j], array[n3]) < 0) {
                    n3 = j;
                }
            }
            if (n3 != i) {
                final int n4 = array[i];
                array[i] = array[n3];
                array[n3] = n4;
            }
        }
    }
    
    private static void insertionSort(final int[] array, final int n, final int n2, final IntComparator intComparator) {
        int n3 = n;
        while (++n3 < n2) {
            final int n4 = array[n3];
            int n5 = n3;
            for (int n6 = array[n5 - 1]; intComparator.compare(n4, n6) < 0; n6 = array[--n5 - 1]) {
                array[n5] = n6;
                if (n == n5 - 1) {
                    --n5;
                    break;
                }
            }
            array[n5] = n4;
        }
    }
    
    public static void quickSort(final int[] array, final int n, final int n2, final IntComparator intComparator) {
        final int n3 = n2 - n;
        if (n3 < 16) {
            selectionSort(array, n, n2, intComparator);
            return;
        }
        int med3 = n + n3 / 2;
        int med4 = n;
        int med5 = n2 - 1;
        if (n3 > 128) {
            final int n4 = n3 / 8;
            med4 = med3(array, med4, med4 + n4, med4 + 2 * n4, intComparator);
            med3 = med3(array, med3 - n4, med3, med3 + n4, intComparator);
            med5 = med3(array, med5 - 2 * n4, med5 - n4, med5, intComparator);
        }
        final int n5 = array[med3(array, med4, med3, med5, intComparator)];
        int n7;
        int n6 = n7 = n;
        int n9;
        int n8 = n9 = n2 - 1;
        while (true) {
            final int compare;
            if (n7 <= n8 && (compare = intComparator.compare(array[n7], n5)) <= 0) {
                if (compare == 0) {
                    swap(array, n6++, n7);
                }
                ++n7;
            }
            else {
                int compare2;
                while (n8 >= n7 && (compare2 = intComparator.compare(array[n8], n5)) >= 0) {
                    if (compare2 == 0) {
                        swap(array, n8, n9--);
                    }
                    --n8;
                }
                if (n7 > n8) {
                    break;
                }
                swap(array, n7++, n8--);
            }
        }
        final int min = Math.min(n6 - n, n7 - n6);
        swap(array, n, n7 - min, min);
        final int min2 = Math.min(n9 - n8, n2 - n9 - 1);
        swap(array, n7, n2 - min2, min2);
        final int n10;
        if ((n10 = n7 - n6) > 1) {
            quickSort(array, n, n + n10, intComparator);
        }
        final int n11;
        if ((n11 = n9 - n8) > 1) {
            quickSort(array, n2 - n11, n2, intComparator);
        }
    }
    
    public static void quickSort(final int[] array, final IntComparator intComparator) {
        quickSort(array, 0, array.length, intComparator);
    }
    
    public static void parallelQuickSort(final int[] array, final int n, final int n2, final IntComparator intComparator) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 8192 || pool.getParallelism() == 1) {
            quickSort(array, n, n2, intComparator);
        }
        else {
            pool.invoke((ForkJoinTask<Object>)new ForkJoinQuickSortComp(array, n, n2, intComparator));
        }
    }
    
    public static void parallelQuickSort(final int[] array, final IntComparator intComparator) {
        parallelQuickSort(array, 0, array.length, intComparator);
    }
    
    private static int med3(final int[] array, final int n, final int n2, final int n3) {
        final int compare = Integer.compare(array[n], array[n2]);
        final int compare2 = Integer.compare(array[n], array[n3]);
        final int compare3 = Integer.compare(array[n2], array[n3]);
        return (compare < 0) ? ((compare3 < 0) ? n2 : ((compare2 < 0) ? n3 : n)) : ((compare3 > 0) ? n2 : ((compare2 > 0) ? n3 : n));
    }
    
    private static void selectionSort(final int[] array, final int n, final int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (array[j] < array[n3]) {
                    n3 = j;
                }
            }
            if (n3 != i) {
                final int n4 = array[i];
                array[i] = array[n3];
                array[n3] = n4;
            }
        }
    }
    
    private static void insertionSort(final int[] array, final int n, final int n2) {
        int n3 = n;
        while (++n3 < n2) {
            final int i = array[n3];
            int n4 = n3;
            for (int n5 = array[n4 - 1]; i < n5; n5 = array[--n4 - 1]) {
                array[n4] = n5;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
            }
            array[n4] = i;
        }
    }
    
    public static void quickSort(final int[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 < 16) {
            selectionSort(array, n, n2);
            return;
        }
        int med3 = n + n3 / 2;
        int med4 = n;
        int med5 = n2 - 1;
        if (n3 > 128) {
            final int n4 = n3 / 8;
            med4 = med3(array, med4, med4 + n4, med4 + 2 * n4);
            med3 = med3(array, med3 - n4, med3, med3 + n4);
            med5 = med3(array, med5 - 2 * n4, med5 - n4, med5);
        }
        final int n5 = array[med3(array, med4, med3, med5)];
        int n7;
        int n6 = n7 = n;
        int n9;
        int n8 = n9 = n2 - 1;
        while (true) {
            final int compare;
            if (n7 <= n8 && (compare = Integer.compare(array[n7], n5)) <= 0) {
                if (compare == 0) {
                    swap(array, n6++, n7);
                }
                ++n7;
            }
            else {
                int compare2;
                while (n8 >= n7 && (compare2 = Integer.compare(array[n8], n5)) >= 0) {
                    if (compare2 == 0) {
                        swap(array, n8, n9--);
                    }
                    --n8;
                }
                if (n7 > n8) {
                    break;
                }
                swap(array, n7++, n8--);
            }
        }
        final int min = Math.min(n6 - n, n7 - n6);
        swap(array, n, n7 - min, min);
        final int min2 = Math.min(n9 - n8, n2 - n9 - 1);
        swap(array, n7, n2 - min2, min2);
        final int n10;
        if ((n10 = n7 - n6) > 1) {
            quickSort(array, n, n + n10);
        }
        final int n11;
        if ((n11 = n9 - n8) > 1) {
            quickSort(array, n2 - n11, n2);
        }
    }
    
    public static void quickSort(final int[] array) {
        quickSort(array, 0, array.length);
    }
    
    public static void parallelQuickSort(final int[] array, final int n, final int n2) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 8192 || pool.getParallelism() == 1) {
            quickSort(array, n, n2);
        }
        else {
            pool.invoke((ForkJoinTask<Object>)new ForkJoinQuickSort(array, n, n2));
        }
    }
    
    public static void parallelQuickSort(final int[] array) {
        parallelQuickSort(array, 0, array.length);
    }
    
    private static int med3Indirect(final int[] array, final int[] array2, final int n, final int n2, final int n3) {
        final int n4 = array2[array[n]];
        final int n5 = array2[array[n2]];
        final int n6 = array2[array[n3]];
        final int compare = Integer.compare(n4, n5);
        final int compare2 = Integer.compare(n4, n6);
        final int compare3 = Integer.compare(n5, n6);
        return (compare < 0) ? ((compare3 < 0) ? n2 : ((compare2 < 0) ? n3 : n)) : ((compare3 > 0) ? n2 : ((compare2 > 0) ? n3 : n));
    }
    
    private static void insertionSortIndirect(final int[] array, final int[] array2, final int n, final int n2) {
        int n3 = n;
        while (++n3 < n2) {
            final int n4 = array[n3];
            int n5 = n3;
            for (int n6 = array[n5 - 1]; array2[n4] < array2[n6]; n6 = array[--n5 - 1]) {
                array[n5] = n6;
                if (n == n5 - 1) {
                    --n5;
                    break;
                }
            }
            array[n5] = n4;
        }
    }
    
    public static void quickSortIndirect(final int[] array, final int[] array2, final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 < 16) {
            insertionSortIndirect(array, array2, n, n2);
            return;
        }
        int med3Indirect = n + n3 / 2;
        int med3Indirect2 = n;
        int med3Indirect3 = n2 - 1;
        if (n3 > 128) {
            final int n4 = n3 / 8;
            med3Indirect2 = med3Indirect(array, array2, med3Indirect2, med3Indirect2 + n4, med3Indirect2 + 2 * n4);
            med3Indirect = med3Indirect(array, array2, med3Indirect - n4, med3Indirect, med3Indirect + n4);
            med3Indirect3 = med3Indirect(array, array2, med3Indirect3 - 2 * n4, med3Indirect3 - n4, med3Indirect3);
        }
        final int n5 = array2[array[med3Indirect(array, array2, med3Indirect2, med3Indirect, med3Indirect3)]];
        int n7;
        int n6 = n7 = n;
        int n9;
        int n8 = n9 = n2 - 1;
        while (true) {
            final int compare;
            if (n7 <= n8 && (compare = Integer.compare(array2[array[n7]], n5)) <= 0) {
                if (compare == 0) {
                    swap(array, n6++, n7);
                }
                ++n7;
            }
            else {
                int compare2;
                while (n8 >= n7 && (compare2 = Integer.compare(array2[array[n8]], n5)) >= 0) {
                    if (compare2 == 0) {
                        swap(array, n8, n9--);
                    }
                    --n8;
                }
                if (n7 > n8) {
                    break;
                }
                swap(array, n7++, n8--);
            }
        }
        final int min = Math.min(n6 - n, n7 - n6);
        swap(array, n, n7 - min, min);
        final int min2 = Math.min(n9 - n8, n2 - n9 - 1);
        swap(array, n7, n2 - min2, min2);
        final int n10;
        if ((n10 = n7 - n6) > 1) {
            quickSortIndirect(array, array2, n, n + n10);
        }
        final int n11;
        if ((n11 = n9 - n8) > 1) {
            quickSortIndirect(array, array2, n2 - n11, n2);
        }
    }
    
    public static void quickSortIndirect(final int[] array, final int[] array2) {
        quickSortIndirect(array, array2, 0, array2.length);
    }
    
    public static void parallelQuickSortIndirect(final int[] array, final int[] array2, final int n, final int n2) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 8192 || pool.getParallelism() == 1) {
            quickSortIndirect(array, array2, n, n2);
        }
        else {
            pool.invoke((ForkJoinTask<Object>)new ForkJoinQuickSortIndirect(array, array2, n, n2));
        }
    }
    
    public static void parallelQuickSortIndirect(final int[] array, final int[] array2) {
        parallelQuickSortIndirect(array, array2, 0, array2.length);
    }
    
    public static void stabilize(final int[] array, final int[] array2, final int n, final int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (array2[array[i]] != array2[array[n3]]) {
                if (i - n3 > 1) {
                    parallelQuickSort(array, n3, i);
                }
                n3 = i;
            }
        }
        if (n2 - n3 > 1) {
            parallelQuickSort(array, n3, n2);
        }
    }
    
    public static void stabilize(final int[] array, final int[] array2) {
        stabilize(array, array2, 0, array.length);
    }
    
    private static int med3(final int[] array, final int[] array2, final int n, final int n2, final int n3) {
        final int compare;
        final int n4 = ((compare = Integer.compare(array[n], array[n2])) == 0) ? Integer.compare(array2[n], array2[n2]) : compare;
        final int compare2;
        final int n5 = ((compare2 = Integer.compare(array[n], array[n3])) == 0) ? Integer.compare(array2[n], array2[n3]) : compare2;
        final int compare3;
        final int n6 = ((compare3 = Integer.compare(array[n2], array[n3])) == 0) ? Integer.compare(array2[n2], array2[n3]) : compare3;
        return (n4 < 0) ? ((n6 < 0) ? n2 : ((n5 < 0) ? n3 : n)) : ((n6 > 0) ? n2 : ((n5 > 0) ? n3 : n));
    }
    
    private static void swap(final int[] array, final int[] array2, final int n, final int n2) {
        final int n3 = array[n];
        final int n4 = array2[n];
        array[n] = array[n2];
        array2[n] = array2[n2];
        array[n2] = n3;
        array2[n2] = n4;
    }
    
    private static void swap(final int[] array, final int[] array2, int n, int n2, final int n3) {
        for (int i = 0; i < n3; ++i, ++n, ++n2) {
            swap(array, array2, n, n2);
        }
    }
    
    private static void selectionSort(final int[] array, final int[] array2, final int n, final int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                final int compare;
                if ((compare = Integer.compare(array[j], array[n3])) < 0 || (compare == 0 && array2[j] < array2[n3])) {
                    n3 = j;
                }
            }
            if (n3 != i) {
                final int n4 = array[i];
                array[i] = array[n3];
                array[n3] = n4;
                final int n5 = array2[i];
                array2[i] = array2[n3];
                array2[n3] = n5;
            }
        }
    }
    
    public static void quickSort(final int[] array, final int[] array2, final int n, final int n2) {
        final int n3 = n2 - n;
        if (n3 < 16) {
            selectionSort(array, array2, n, n2);
            return;
        }
        int med3 = n + n3 / 2;
        int med4 = n;
        int med5 = n2 - 1;
        if (n3 > 128) {
            final int n4 = n3 / 8;
            med4 = med3(array, array2, med4, med4 + n4, med4 + 2 * n4);
            med3 = med3(array, array2, med3 - n4, med3, med3 + n4);
            med5 = med3(array, array2, med5 - 2 * n4, med5 - n4, med5);
        }
        final int med6 = med3(array, array2, med4, med3, med5);
        final int n5 = array[med6];
        final int n6 = array2[med6];
        int n8;
        int n7 = n8 = n;
        int n10;
        int n9 = n10 = n2 - 1;
        while (true) {
            final int compare;
            final int n11;
            if (n8 <= n9 && (n11 = (((compare = Integer.compare(array[n8], n5)) == 0) ? Integer.compare(array2[n8], n6) : compare)) <= 0) {
                if (n11 == 0) {
                    swap(array, array2, n7++, n8);
                }
                ++n8;
            }
            else {
                int compare2;
                int n12;
                while (n9 >= n8 && (n12 = (((compare2 = Integer.compare(array[n9], n5)) == 0) ? Integer.compare(array2[n9], n6) : compare2)) >= 0) {
                    if (n12 == 0) {
                        swap(array, array2, n9, n10--);
                    }
                    --n9;
                }
                if (n8 > n9) {
                    break;
                }
                swap(array, array2, n8++, n9--);
            }
        }
        final int min = Math.min(n7 - n, n8 - n7);
        swap(array, array2, n, n8 - min, min);
        final int min2 = Math.min(n10 - n9, n2 - n10 - 1);
        swap(array, array2, n8, n2 - min2, min2);
        final int n13;
        if ((n13 = n8 - n7) > 1) {
            quickSort(array, array2, n, n + n13);
        }
        final int n14;
        if ((n14 = n10 - n9) > 1) {
            quickSort(array, array2, n2 - n14, n2);
        }
    }
    
    public static void quickSort(final int[] array, final int[] array2) {
        ensureSameLength(array, array2);
        quickSort(array, array2, 0, array.length);
    }
    
    public static void parallelQuickSort(final int[] array, final int[] array2, final int n, final int n2) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 8192 || pool.getParallelism() == 1) {
            quickSort(array, array2, n, n2);
        }
        else {
            pool.invoke((ForkJoinTask<Object>)new ForkJoinQuickSort2(array, array2, n, n2));
        }
    }
    
    public static void parallelQuickSort(final int[] array, final int[] array2) {
        ensureSameLength(array, array2);
        parallelQuickSort(array, array2, 0, array.length);
    }
    
    public static void unstableSort(final int[] array, final int n, final int n2) {
        if (n2 - n >= 2000) {
            radixSort(array, n, n2);
        }
        else {
            quickSort(array, n, n2);
        }
    }
    
    public static void unstableSort(final int[] array) {
        unstableSort(array, 0, array.length);
    }
    
    public static void unstableSort(final int[] array, final int n, final int n2, final IntComparator intComparator) {
        quickSort(array, n, n2, intComparator);
    }
    
    public static void unstableSort(final int[] array, final IntComparator intComparator) {
        unstableSort(array, 0, array.length, intComparator);
    }
    
    public static void mergeSort(final int[] array, final int n, final int n2, int[] copy) {
        final int n3 = n2 - n;
        if (n3 < 16) {
            insertionSort(array, n, n2);
            return;
        }
        if (copy == null) {
            copy = java.util.Arrays.copyOf(array, n2);
        }
        final int n4 = n + n2 >>> 1;
        mergeSort(copy, n, n4, array);
        mergeSort(copy, n4, n2, array);
        if (copy[n4 - 1] <= copy[n4]) {
            System.arraycopy(copy, n, array, n, n3);
            return;
        }
        int i = n;
        int n5 = n;
        int n6 = n4;
        while (i < n2) {
            if (n6 >= n2 || (n5 < n4 && copy[n5] <= copy[n6])) {
                array[i] = copy[n5++];
            }
            else {
                array[i] = copy[n6++];
            }
            ++i;
        }
    }
    
    public static void mergeSort(final int[] array, final int n, final int n2) {
        mergeSort(array, n, n2, (int[])null);
    }
    
    public static void mergeSort(final int[] array) {
        mergeSort(array, 0, array.length);
    }
    
    public static void mergeSort(final int[] array, final int n, final int n2, final IntComparator intComparator, int[] copy) {
        final int n3 = n2 - n;
        if (n3 < 16) {
            insertionSort(array, n, n2, intComparator);
            return;
        }
        if (copy == null) {
            copy = java.util.Arrays.copyOf(array, n2);
        }
        final int n4 = n + n2 >>> 1;
        mergeSort(copy, n, n4, intComparator, array);
        mergeSort(copy, n4, n2, intComparator, array);
        if (intComparator.compare(copy[n4 - 1], copy[n4]) <= 0) {
            System.arraycopy(copy, n, array, n, n3);
            return;
        }
        int i = n;
        int n5 = n;
        int n6 = n4;
        while (i < n2) {
            if (n6 >= n2 || (n5 < n4 && intComparator.compare(copy[n5], copy[n6]) <= 0)) {
                array[i] = copy[n5++];
            }
            else {
                array[i] = copy[n6++];
            }
            ++i;
        }
    }
    
    public static void mergeSort(final int[] array, final int n, final int n2, final IntComparator intComparator) {
        mergeSort(array, n, n2, intComparator, null);
    }
    
    public static void mergeSort(final int[] array, final IntComparator intComparator) {
        mergeSort(array, 0, array.length, intComparator);
    }
    
    public static void stableSort(final int[] array, final int n, final int n2) {
        unstableSort(array, n, n2);
    }
    
    public static void stableSort(final int[] array) {
        stableSort(array, 0, array.length);
    }
    
    public static void stableSort(final int[] array, final int n, final int n2, final IntComparator intComparator) {
        mergeSort(array, n, n2, intComparator);
    }
    
    public static void stableSort(final int[] array, final IntComparator intComparator) {
        stableSort(array, 0, array.length, intComparator);
    }
    
    public static int binarySearch(final int[] array, int i, int n, final int n2) {
        --n;
        while (i <= n) {
            final int n3 = i + n >>> 1;
            final int n4 = array[n3];
            if (n4 < n2) {
                i = n3 + 1;
            }
            else {
                if (n4 <= n2) {
                    return n3;
                }
                n = n3 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static int binarySearch(final int[] array, final int n) {
        return binarySearch(array, 0, array.length, n);
    }
    
    public static int binarySearch(final int[] array, int i, int n, final int n2, final IntComparator intComparator) {
        --n;
        while (i <= n) {
            final int n3 = i + n >>> 1;
            final int compare = intComparator.compare(array[n3], n2);
            if (compare < 0) {
                i = n3 + 1;
            }
            else {
                if (compare <= 0) {
                    return n3;
                }
                n = n3 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static int binarySearch(final int[] array, final int n, final IntComparator intComparator) {
        return binarySearch(array, 0, array.length, n, intComparator);
    }
    
    public static void radixSort(final int[] array) {
        radixSort(array, 0, array.length);
    }
    
    public static void radixSort(final int[] array, final int n, final int n2) {
        if (n2 - n < 1024) {
            quickSort(array, n, n2);
            return;
        }
        int i = 0;
        final int[] array2 = new int[766];
        final int[] array3 = new int[766];
        final int[] array4 = new int[766];
        array3[i] = n2 - (array2[i] = n);
        array4[i++] = 0;
        final int[] array5 = new int[256];
        final int[] array6 = new int[256];
        while (i > 0) {
            final int n3 = array2[--i];
            final int n4 = array3[i];
            final int n5 = array4[i];
            final int n6 = (n5 % 4 == 0) ? 128 : 0;
            final int n7 = (3 - n5 % 4) * 8;
            int n8 = n3 + n4;
            while (n8-- != n3) {
                final int[] array7 = array5;
                final int n9 = (array[n8] >>> n7 & 0xFF) ^ n6;
                ++array7[n9];
            }
            int n10 = -1;
            int j = 0;
            int n11 = n3;
            while (j < 256) {
                if (array5[j] != 0) {
                    n10 = j;
                }
                n11 = (array6[j] = n11 + array5[j]);
                ++j;
            }
            int n14;
            for (int n12 = n3 + n4 - array5[n10], k = n3; k <= n12; k += array5[n14], array5[n14] = 0) {
                int n13 = array[k];
                n14 = ((n13 >>> n7 & 0xFF) ^ n6);
                if (k < n12) {
                    while (true) {
                        final int[] array8 = array6;
                        final int n15 = n14;
                        final int n16 = array8[n15] - 1;
                        array8[n15] = n16;
                        final int n17;
                        if ((n17 = n16) <= k) {
                            break;
                        }
                        final int n18 = n13;
                        n13 = array[n17];
                        array[n17] = n18;
                        n14 = ((n13 >>> n7 & 0xFF) ^ n6);
                    }
                    array[k] = n13;
                }
                if (n5 < 3 && array5[n14] > 1) {
                    if (array5[n14] < 1024) {
                        quickSort(array, k, k + array5[n14]);
                    }
                    else {
                        array2[i] = k;
                        array3[i] = array5[n14];
                        array4[i++] = n5 + 1;
                    }
                }
            }
        }
    }
    
    public static void parallelRadixSort(final int[] array, final int n, final int n2) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 1024 || pool.getParallelism() == 1) {
            quickSort(array, n, n2);
            return;
        }
        final LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        final int parallelism = pool.getParallelism();
        final ExecutorCompletionService executorCompletionService = new ExecutorCompletionService<Void>(pool);
        int n3 = parallelism;
        while (n3-- != 0) {
            executorCompletionService.submit(IntArrays::lambda$parallelRadixSort$0);
        }
        Throwable cause = null;
        int n4 = parallelism;
        while (n4-- != 0) {
            try {
                executorCompletionService.take().get();
            }
            catch (Exception ex) {
                cause = ex.getCause();
            }
        }
        if (cause != null) {
            throw (cause instanceof RuntimeException) ? cause : new RuntimeException(cause);
        }
    }
    
    public static void parallelRadixSort(final int[] array) {
        parallelRadixSort(array, 0, array.length);
    }
    
    public static void radixSortIndirect(final int[] array, final int[] array2, final boolean b) {
        radixSortIndirect(array, array2, 0, array.length, b);
    }
    
    public static void radixSortIndirect(final int[] array, final int[] array2, final int n, final int n2, final boolean b) {
        if (n2 - n < 1024) {
            insertionSortIndirect(array, array2, n, n2);
            return;
        }
        int i = 0;
        final int[] array3 = new int[766];
        final int[] array4 = new int[766];
        final int[] array5 = new int[766];
        array4[i] = n2 - (array3[i] = n);
        array5[i++] = 0;
        final int[] array6 = new int[256];
        final int[] array7 = new int[256];
        final int[] array8 = (int[])(b ? new int[array.length] : null);
        while (i > 0) {
            final int n3 = array3[--i];
            final int n4 = array4[i];
            final int n5 = array5[i];
            final int n6 = (n5 % 4 == 0) ? 128 : 0;
            final int n7 = (3 - n5 % 4) * 8;
            int n8 = n3 + n4;
            while (n8-- != n3) {
                final int[] array9 = array6;
                final int n9 = (array2[array[n8]] >>> n7 & 0xFF) ^ n6;
                ++array9[n9];
            }
            int n10 = -1;
            int j = 0;
            int n11 = b ? 0 : n3;
            while (j < 256) {
                if (array6[j] != 0) {
                    n10 = j;
                }
                n11 = (array7[j] = n11 + array6[j]);
                ++j;
            }
            if (b) {
                int n12 = n3 + n4;
                while (n12-- != n3) {
                    final int[] array10 = array8;
                    final int[] array11 = array7;
                    final int n13 = (array2[array[n12]] >>> n7 & 0xFF) ^ n6;
                    array10[--array11[n13]] = array[n12];
                }
                System.arraycopy(array8, 0, array, n3, n4);
                int k = 0;
                int n14 = n3;
                while (k <= n10) {
                    if (n5 < 3 && array6[k] > 1) {
                        if (array6[k] < 1024) {
                            insertionSortIndirect(array, array2, n14, n14 + array6[k]);
                        }
                        else {
                            array3[i] = n14;
                            array4[i] = array6[k];
                            array5[i++] = n5 + 1;
                        }
                    }
                    n14 += array6[k];
                    ++k;
                }
                java.util.Arrays.fill(array6, 0);
            }
            else {
                int n17;
                for (int n15 = n3 + n4 - array6[n10], l = n3; l <= n15; l += array6[n17], array6[n17] = 0) {
                    int n16 = array[l];
                    n17 = ((array2[n16] >>> n7 & 0xFF) ^ n6);
                    if (l < n15) {
                        while (true) {
                            final int[] array12 = array7;
                            final int n18 = n17;
                            final int n19 = array12[n18] - 1;
                            array12[n18] = n19;
                            final int n20;
                            if ((n20 = n19) <= l) {
                                break;
                            }
                            final int n21 = n16;
                            n16 = array[n20];
                            array[n20] = n21;
                            n17 = ((array2[n16] >>> n7 & 0xFF) ^ n6);
                        }
                        array[l] = n16;
                    }
                    if (n5 < 3 && array6[n17] > 1) {
                        if (array6[n17] < 1024) {
                            insertionSortIndirect(array, array2, l, l + array6[n17]);
                        }
                        else {
                            array3[i] = l;
                            array4[i] = array6[n17];
                            array5[i++] = n5 + 1;
                        }
                    }
                }
            }
        }
    }
    
    public static void parallelRadixSortIndirect(final int[] array, final int[] array2, final int n, final int n2, final boolean b) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 1024 || pool.getParallelism() == 1) {
            radixSortIndirect(array, array2, n, n2, b);
            return;
        }
        final LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        final int parallelism = pool.getParallelism();
        final ExecutorCompletionService executorCompletionService = new ExecutorCompletionService<Void>(pool);
        final int[] array3 = (int[])(b ? new int[array.length] : null);
        int n3 = parallelism;
        while (n3-- != 0) {
            executorCompletionService.submit(IntArrays::lambda$parallelRadixSortIndirect$1);
        }
        Throwable cause = null;
        int n4 = parallelism;
        while (n4-- != 0) {
            try {
                executorCompletionService.take().get();
            }
            catch (Exception ex) {
                cause = ex.getCause();
            }
        }
        if (cause != null) {
            throw (cause instanceof RuntimeException) ? cause : new RuntimeException(cause);
        }
    }
    
    public static void parallelRadixSortIndirect(final int[] array, final int[] array2, final boolean b) {
        parallelRadixSortIndirect(array, array2, 0, array2.length, b);
    }
    
    public static void radixSort(final int[] array, final int[] array2) {
        ensureSameLength(array, array2);
        radixSort(array, array2, 0, array.length);
    }
    
    public static void radixSort(final int[] array, final int[] array2, final int n, final int n2) {
        if (n2 - n < 1024) {
            selectionSort(array, array2, n, n2);
            return;
        }
        int i = 0;
        final int[] array3 = new int[1786];
        final int[] array4 = new int[1786];
        final int[] array5 = new int[1786];
        array4[i] = n2 - (array3[i] = n);
        array5[i++] = 0;
        final int[] array6 = new int[256];
        final int[] array7 = new int[256];
        while (i > 0) {
            final int n3 = array3[--i];
            final int n4 = array4[i];
            final int n5 = array5[i];
            final int n6 = (n5 % 4 == 0) ? 128 : 0;
            final int[] array8 = (n5 < 4) ? array : array2;
            final int n7 = (3 - n5 % 4) * 8;
            int n8 = n3 + n4;
            while (n8-- != n3) {
                final int[] array9 = array6;
                final int n9 = (array8[n8] >>> n7 & 0xFF) ^ n6;
                ++array9[n9];
            }
            int n10 = -1;
            int j = 0;
            int n11 = n3;
            while (j < 256) {
                if (array6[j] != 0) {
                    n10 = j;
                }
                n11 = (array7[j] = n11 + array6[j]);
                ++j;
            }
            int n15;
            for (int n12 = n3 + n4 - array6[n10], k = n3; k <= n12; k += array6[n15], array6[n15] = 0) {
                int n13 = array[k];
                int n14 = array2[k];
                n15 = ((array8[k] >>> n7 & 0xFF) ^ n6);
                if (k < n12) {
                    while (true) {
                        final int[] array10 = array7;
                        final int n16 = n15;
                        final int n17 = array10[n16] - 1;
                        array10[n16] = n17;
                        final int n18;
                        if ((n18 = n17) <= k) {
                            break;
                        }
                        n15 = ((array8[n18] >>> n7 & 0xFF) ^ n6);
                        final int n19 = n13;
                        n13 = array[n18];
                        array[n18] = n19;
                        final int n20 = n14;
                        n14 = array2[n18];
                        array2[n18] = n20;
                    }
                    array[k] = n13;
                    array2[k] = n14;
                }
                if (n5 < 7 && array6[n15] > 1) {
                    if (array6[n15] < 1024) {
                        selectionSort(array, array2, k, k + array6[n15]);
                    }
                    else {
                        array3[i] = k;
                        array4[i] = array6[n15];
                        array5[i++] = n5 + 1;
                    }
                }
            }
        }
    }
    
    public static void parallelRadixSort(final int[] array, final int[] array2, final int n, final int n2) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 1024 || pool.getParallelism() == 1) {
            quickSort(array, array2, n, n2);
            return;
        }
        if (array.length != array2.length) {
            throw new IllegalArgumentException("Array size mismatch.");
        }
        final LinkedBlockingQueue<Segment> linkedBlockingQueue = new LinkedBlockingQueue<Segment>();
        linkedBlockingQueue.add(new Segment(n, n2 - n, 0));
        final AtomicInteger atomicInteger = new AtomicInteger(1);
        final int parallelism = pool.getParallelism();
        final ExecutorCompletionService executorCompletionService = new ExecutorCompletionService<Void>(pool);
        int n3 = parallelism;
        while (n3-- != 0) {
            executorCompletionService.submit(IntArrays::lambda$parallelRadixSort$2);
        }
        Throwable cause = null;
        int n4 = parallelism;
        while (n4-- != 0) {
            try {
                executorCompletionService.take().get();
            }
            catch (Exception ex) {
                cause = ex.getCause();
            }
        }
        if (cause != null) {
            throw (cause instanceof RuntimeException) ? cause : new RuntimeException(cause);
        }
    }
    
    public static void parallelRadixSort(final int[] array, final int[] array2) {
        ensureSameLength(array, array2);
        parallelRadixSort(array, array2, 0, array.length);
    }
    
    private static void insertionSortIndirect(final int[] array, final int[] array2, final int[] array3, final int n, final int n2) {
        int n3 = n;
        while (++n3 < n2) {
            final int n4 = array[n3];
            int n5 = n3;
            for (int n6 = array[n5 - 1]; array2[n4] < array2[n6] || (array2[n4] == array2[n6] && array3[n4] < array3[n6]); n6 = array[--n5 - 1]) {
                array[n5] = n6;
                if (n == n5 - 1) {
                    --n5;
                    break;
                }
            }
            array[n5] = n4;
        }
    }
    
    public static void radixSortIndirect(final int[] array, final int[] array2, final int[] array3, final boolean b) {
        ensureSameLength(array2, array3);
        radixSortIndirect(array, array2, array3, 0, array2.length, b);
    }
    
    public static void radixSortIndirect(final int[] array, final int[] array2, final int[] array3, final int n, final int n2, final boolean b) {
        if (n2 - n < 1024) {
            insertionSortIndirect(array, array2, array3, n, n2);
            return;
        }
        int i = 0;
        final int[] array4 = new int[1786];
        final int[] array5 = new int[1786];
        final int[] array6 = new int[1786];
        array5[i] = n2 - (array4[i] = n);
        array6[i++] = 0;
        final int[] array7 = new int[256];
        final int[] array8 = new int[256];
        final int[] array9 = (int[])(b ? new int[array.length] : null);
        while (i > 0) {
            final int n3 = array4[--i];
            final int n4 = array5[i];
            final int n5 = array6[i];
            final int n6 = (n5 % 4 == 0) ? 128 : 0;
            final int[] array10 = (n5 < 4) ? array2 : array3;
            final int n7 = (3 - n5 % 4) * 8;
            int n8 = n3 + n4;
            while (n8-- != n3) {
                final int[] array11 = array7;
                final int n9 = (array10[array[n8]] >>> n7 & 0xFF) ^ n6;
                ++array11[n9];
            }
            int n10 = -1;
            int j = 0;
            int n11 = b ? 0 : n3;
            while (j < 256) {
                if (array7[j] != 0) {
                    n10 = j;
                }
                n11 = (array8[j] = n11 + array7[j]);
                ++j;
            }
            if (b) {
                int n12 = n3 + n4;
                while (n12-- != n3) {
                    final int[] array12 = array9;
                    final int[] array13 = array8;
                    final int n13 = (array10[array[n12]] >>> n7 & 0xFF) ^ n6;
                    array12[--array13[n13]] = array[n12];
                }
                System.arraycopy(array9, 0, array, n3, n4);
                int k = 0;
                int n14 = n3;
                while (k < 256) {
                    if (n5 < 7 && array7[k] > 1) {
                        if (array7[k] < 1024) {
                            insertionSortIndirect(array, array2, array3, n14, n14 + array7[k]);
                        }
                        else {
                            array4[i] = n14;
                            array5[i] = array7[k];
                            array6[i++] = n5 + 1;
                        }
                    }
                    n14 += array7[k];
                    ++k;
                }
                java.util.Arrays.fill(array7, 0);
            }
            else {
                int n17;
                for (int n15 = n3 + n4 - array7[n10], l = n3; l <= n15; l += array7[n17], array7[n17] = 0) {
                    int n16 = array[l];
                    n17 = ((array10[n16] >>> n7 & 0xFF) ^ n6);
                    if (l < n15) {
                        while (true) {
                            final int[] array14 = array8;
                            final int n18 = n17;
                            final int n19 = array14[n18] - 1;
                            array14[n18] = n19;
                            final int n20;
                            if ((n20 = n19) <= l) {
                                break;
                            }
                            final int n21 = n16;
                            n16 = array[n20];
                            array[n20] = n21;
                            n17 = ((array10[n16] >>> n7 & 0xFF) ^ n6);
                        }
                        array[l] = n16;
                    }
                    if (n5 < 7 && array7[n17] > 1) {
                        if (array7[n17] < 1024) {
                            insertionSortIndirect(array, array2, array3, l, l + array7[n17]);
                        }
                        else {
                            array4[i] = l;
                            array5[i] = array7[n17];
                            array6[i++] = n5 + 1;
                        }
                    }
                }
            }
        }
    }
    
    private static void selectionSort(final int[][] array, final int n, final int n2, final int n3) {
        final int length = array.length;
        final int n4 = n3 / 4;
        for (int i = n; i < n2 - 1; ++i) {
            int n5 = i;
            for (int j = i + 1; j < n2; ++j) {
                for (int k = n4; k < length; ++k) {
                    if (array[k][j] < array[k][n5]) {
                        n5 = j;
                        break;
                    }
                    if (array[k][j] > array[k][n5]) {
                        break;
                    }
                }
            }
            if (n5 != i) {
                int n6 = length;
                while (n6-- != 0) {
                    final int n7 = array[n6][i];
                    array[n6][i] = array[n6][n5];
                    array[n6][n5] = n7;
                }
            }
        }
    }
    
    public static void radixSort(final int[][] array) {
        radixSort(array, 0, array[0].length);
    }
    
    public static void radixSort(final int[][] array, final int n, final int n2) {
        if (n2 - n < 1024) {
            selectionSort(array, n, n2, 0);
            return;
        }
        final int length = array.length;
        final int n3 = 4 * length - 1;
        int n4 = length;
        final int length2 = array[0].length;
        while (n4-- != 0) {
            if (array[n4].length != length2) {
                throw new IllegalArgumentException("The array of index " + n4 + " has not the same length of the array of index 0.");
            }
        }
        final int n5 = 255 * (length * 4 - 1) + 1;
        int i = 0;
        final int[] array2 = new int[n5];
        final int[] array3 = new int[n5];
        final int[] array4 = new int[n5];
        array3[i] = n2 - (array2[i] = n);
        array4[i++] = 0;
        final int[] array5 = new int[256];
        final int[] array6 = new int[256];
        final int[] array7 = new int[length];
        while (i > 0) {
            final int n6 = array2[--i];
            final int n7 = array3[i];
            final int n8 = array4[i];
            final int n9 = (n8 % 4 == 0) ? 128 : 0;
            final int[] array8 = array[n8 / 4];
            final int n10 = (3 - n8 % 4) * 8;
            int n11 = n6 + n7;
            while (n11-- != n6) {
                final int[] array9 = array5;
                final int n12 = (array8[n11] >>> n10 & 0xFF) ^ n9;
                ++array9[n12];
            }
            int n13 = -1;
            int j = 0;
            int n14 = n6;
            while (j < 256) {
                if (array5[j] != 0) {
                    n13 = j;
                }
                n14 = (array6[j] = n14 + array5[j]);
                ++j;
            }
            int n17;
            for (int n15 = n6 + n7 - array5[n13], k = n6; k <= n15; k += array5[n17], array5[n17] = 0) {
                int n16 = length;
                while (n16-- != 0) {
                    array7[n16] = array[n16][k];
                }
                n17 = ((array8[k] >>> n10 & 0xFF) ^ n9);
                if (k < n15) {
                    while (true) {
                        final int[] array10 = array6;
                        final int n18 = n17;
                        final int n19 = array10[n18] - 1;
                        array10[n18] = n19;
                        final int n20;
                        if ((n20 = n19) <= k) {
                            break;
                        }
                        n17 = ((array8[n20] >>> n10 & 0xFF) ^ n9);
                        int n21 = length;
                        while (n21-- != 0) {
                            final int n22 = array7[n21];
                            array7[n21] = array[n21][n20];
                            array[n21][n20] = n22;
                        }
                    }
                    int n23 = length;
                    while (n23-- != 0) {
                        array[n23][k] = array7[n23];
                    }
                }
                if (n8 < n3 && array5[n17] > 1) {
                    if (array5[n17] < 1024) {
                        selectionSort(array, k, k + array5[n17], n8 + 1);
                    }
                    else {
                        array2[i] = k;
                        array3[i] = array5[n17];
                        array4[i++] = n8 + 1;
                    }
                }
            }
        }
    }
    
    public static int[] shuffle(final int[] array, final int n, final int n2, final Random random) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            final int nextInt = random.nextInt(n3 + 1);
            final int n4 = array[n + n3];
            array[n + n3] = array[n + nextInt];
            array[n + nextInt] = n4;
        }
        return array;
    }
    
    public static int[] shuffle(final int[] array, final Random random) {
        int length = array.length;
        while (length-- != 0) {
            final int nextInt = random.nextInt(length + 1);
            final int n = array[length];
            array[length] = array[nextInt];
            array[nextInt] = n;
        }
        return array;
    }
    
    public static int[] reverse(final int[] array) {
        final int length = array.length;
        int n = length / 2;
        while (n-- != 0) {
            final int n2 = array[length - n - 1];
            array[length - n - 1] = array[n];
            array[n] = n2;
        }
        return array;
    }
    
    public static int[] reverse(final int[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            final int n5 = array[n + n3 - n4 - 1];
            array[n + n3 - n4 - 1] = array[n + n4];
            array[n + n4] = n5;
        }
        return array;
    }
    
    private static Void lambda$parallelRadixSort$2(final AtomicInteger atomicInteger, final int n, final LinkedBlockingQueue linkedBlockingQueue, final int[] array, final int[] array2) throws Exception {
        final int[] array3 = new int[256];
        final int[] array4 = new int[256];
        while (true) {
            if (atomicInteger.get() == 0) {
                int n2 = n;
                while (n2-- != 0) {
                    linkedBlockingQueue.add(IntArrays.POISON_PILL);
                }
            }
            final Segment segment = linkedBlockingQueue.take();
            if (segment == IntArrays.POISON_PILL) {
                break;
            }
            final int offset = segment.offset;
            final int length = segment.length;
            final int level = segment.level;
            final int n3 = (level % 4 == 0) ? 128 : 0;
            final int[] array5 = (level < 4) ? array : array2;
            final int n4 = (3 - level % 4) * 8;
            int n5 = offset + length;
            while (n5-- != offset) {
                final int[] array6 = array3;
                final int n6 = (array5[n5] >>> n4 & 0xFF) ^ n3;
                ++array6[n6];
            }
            int n7 = -1;
            int i = 0;
            int n8 = offset;
            while (i < 256) {
                if (array3[i] != 0) {
                    n7 = i;
                }
                n8 = (array4[i] = n8 + array3[i]);
                ++i;
            }
            int n12;
            for (int n9 = offset + length - array3[n7], j = offset; j <= n9; j += array3[n12], array3[n12] = 0) {
                int n10 = array[j];
                int n11 = array2[j];
                n12 = ((array5[j] >>> n4 & 0xFF) ^ n3);
                if (j < n9) {
                    while (true) {
                        final int[] array7 = array4;
                        final int n13 = n12;
                        final int n14 = array7[n13] - 1;
                        array7[n13] = n14;
                        final int n15;
                        if ((n15 = n14) <= j) {
                            break;
                        }
                        n12 = ((array5[n15] >>> n4 & 0xFF) ^ n3);
                        final int n16 = n10;
                        final int n17 = n11;
                        n10 = array[n15];
                        n11 = array2[n15];
                        array[n15] = n16;
                        array2[n15] = n17;
                    }
                    array[j] = n10;
                    array2[j] = n11;
                }
                if (level < 7 && array3[n12] > 1) {
                    if (array3[n12] < 1024) {
                        quickSort(array, array2, j, j + array3[n12]);
                    }
                    else {
                        atomicInteger.incrementAndGet();
                        linkedBlockingQueue.add(new Segment(j, array3[n12], level + 1));
                    }
                }
            }
            atomicInteger.decrementAndGet();
        }
        return null;
    }
    
    private static Void lambda$parallelRadixSortIndirect$1(final AtomicInteger atomicInteger, final int n, final LinkedBlockingQueue linkedBlockingQueue, final int[] array, final int[] array2, final boolean b, final int[] array3) throws Exception {
        final int[] array4 = new int[256];
        final int[] array5 = new int[256];
        while (true) {
            if (atomicInteger.get() == 0) {
                int n2 = n;
                while (n2-- != 0) {
                    linkedBlockingQueue.add(IntArrays.POISON_PILL);
                }
            }
            final Segment segment = linkedBlockingQueue.take();
            if (segment == IntArrays.POISON_PILL) {
                break;
            }
            final int offset = segment.offset;
            final int length = segment.length;
            final int level = segment.level;
            final int n3 = (level % 4 == 0) ? 128 : 0;
            final int n4 = (3 - level % 4) * 8;
            int n5 = offset + length;
            while (n5-- != offset) {
                final int[] array6 = array4;
                final int n6 = (array[array2[n5]] >>> n4 & 0xFF) ^ n3;
                ++array6[n6];
            }
            int n7 = -1;
            int i = 0;
            int n8 = offset;
            while (i < 256) {
                if (array4[i] != 0) {
                    n7 = i;
                }
                n8 = (array5[i] = n8 + array4[i]);
                ++i;
            }
            if (b) {
                int n9 = offset + length;
                while (n9-- != offset) {
                    final int[] array7 = array5;
                    final int n10 = (array[array2[n9]] >>> n4 & 0xFF) ^ n3;
                    array3[--array7[n10]] = array2[n9];
                }
                System.arraycopy(array3, offset, array2, offset, length);
                int j = 0;
                int n11 = offset;
                while (j <= n7) {
                    if (level < 3 && array4[j] > 1) {
                        if (array4[j] < 1024) {
                            radixSortIndirect(array2, array, n11, n11 + array4[j], b);
                        }
                        else {
                            atomicInteger.incrementAndGet();
                            linkedBlockingQueue.add(new Segment(n11, array4[j], level + 1));
                        }
                    }
                    n11 += array4[j];
                    ++j;
                }
                java.util.Arrays.fill(array4, 0);
            }
            else {
                int n14;
                for (int n12 = offset + length - array4[n7], k = offset; k <= n12; k += array4[n14], array4[n14] = 0) {
                    int n13 = array2[k];
                    n14 = ((array[n13] >>> n4 & 0xFF) ^ n3);
                    if (k < n12) {
                        while (true) {
                            final int[] array8 = array5;
                            final int n15 = n14;
                            final int n16 = array8[n15] - 1;
                            array8[n15] = n16;
                            final int n17;
                            if ((n17 = n16) <= k) {
                                break;
                            }
                            final int n18 = n13;
                            n13 = array2[n17];
                            array2[n17] = n18;
                            n14 = ((array[n13] >>> n4 & 0xFF) ^ n3);
                        }
                        array2[k] = n13;
                    }
                    if (level < 3 && array4[n14] > 1) {
                        if (array4[n14] < 1024) {
                            radixSortIndirect(array2, array, k, k + array4[n14], b);
                        }
                        else {
                            atomicInteger.incrementAndGet();
                            linkedBlockingQueue.add(new Segment(k, array4[n14], level + 1));
                        }
                    }
                }
            }
            atomicInteger.decrementAndGet();
        }
        return null;
    }
    
    private static Void lambda$parallelRadixSort$0(final AtomicInteger atomicInteger, final int n, final LinkedBlockingQueue linkedBlockingQueue, final int[] array) throws Exception {
        final int[] array2 = new int[256];
        final int[] array3 = new int[256];
        while (true) {
            if (atomicInteger.get() == 0) {
                int n2 = n;
                while (n2-- != 0) {
                    linkedBlockingQueue.add(IntArrays.POISON_PILL);
                }
            }
            final Segment segment = linkedBlockingQueue.take();
            if (segment == IntArrays.POISON_PILL) {
                break;
            }
            final int offset = segment.offset;
            final int length = segment.length;
            final int level = segment.level;
            final int n3 = (level % 4 == 0) ? 128 : 0;
            final int n4 = (3 - level % 4) * 8;
            int n5 = offset + length;
            while (n5-- != offset) {
                final int[] array4 = array2;
                final int n6 = (array[n5] >>> n4 & 0xFF) ^ n3;
                ++array4[n6];
            }
            int n7 = -1;
            int i = 0;
            int n8 = offset;
            while (i < 256) {
                if (array2[i] != 0) {
                    n7 = i;
                }
                n8 = (array3[i] = n8 + array2[i]);
                ++i;
            }
            int n11;
            for (int n9 = offset + length - array2[n7], j = offset; j <= n9; j += array2[n11], array2[n11] = 0) {
                int n10 = array[j];
                n11 = ((n10 >>> n4 & 0xFF) ^ n3);
                if (j < n9) {
                    while (true) {
                        final int[] array5 = array3;
                        final int n12 = n11;
                        final int n13 = array5[n12] - 1;
                        array5[n12] = n13;
                        final int n14;
                        if ((n14 = n13) <= j) {
                            break;
                        }
                        final int n15 = n10;
                        n10 = array[n14];
                        array[n14] = n15;
                        n11 = ((n10 >>> n4 & 0xFF) ^ n3);
                    }
                    array[j] = n10;
                }
                if (level < 3 && array2[n11] > 1) {
                    if (array2[n11] < 1024) {
                        quickSort(array, j, j + array2[n11]);
                    }
                    else {
                        atomicInteger.incrementAndGet();
                        linkedBlockingQueue.add(new Segment(j, array2[n11], level + 1));
                    }
                }
            }
            atomicInteger.decrementAndGet();
        }
        return null;
    }
    
    static int access$000(final int[] array, final int n, final int n2, final int n3, final IntComparator intComparator) {
        return med3(array, n, n2, n3, intComparator);
    }
    
    static int access$100(final int[] array, final int n, final int n2, final int n3) {
        return med3(array, n, n2, n3);
    }
    
    static int access$200(final int[] array, final int[] array2, final int n, final int n2, final int n3) {
        return med3Indirect(array, array2, n, n2, n3);
    }
    
    static int access$300(final int[] array, final int[] array2, final int n, final int n2, final int n3) {
        return med3(array, array2, n, n2, n3);
    }
    
    static void access$400(final int[] array, final int[] array2, final int n, final int n2) {
        swap(array, array2, n, n2);
    }
    
    static void access$500(final int[] array, final int[] array2, final int n, final int n2, final int n3) {
        swap(array, array2, n, n2, n3);
    }
    
    static {
        EMPTY_ARRAY = new int[0];
        DEFAULT_EMPTY_ARRAY = new int[0];
        POISON_PILL = new Segment(-1, -1, -1);
        HASH_STRATEGY = new ArrayHashStrategy(null);
    }
    
    protected static class ForkJoinQuickSortComp extends RecursiveAction
    {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] x;
        private final IntComparator comp;
        
        public ForkJoinQuickSortComp(final int[] x, final int from, final int to, final IntComparator comp) {
            this.from = from;
            this.to = to;
            this.x = x;
            this.comp = comp;
        }
        
        @Override
        protected void compute() {
            final int[] x = this.x;
            final int n = this.to - this.from;
            if (n < 8192) {
                IntArrays.quickSort(x, this.from, this.to, this.comp);
                return;
            }
            final int n2 = this.from + n / 2;
            final int from = this.from;
            final int n3 = this.to - 1;
            final int n4 = n / 8;
            final int n5 = x[IntArrays.access$000(x, IntArrays.access$000(x, from, from + n4, from + 2 * n4, this.comp), IntArrays.access$000(x, n2 - n4, n2, n2 + n4, this.comp), IntArrays.access$000(x, n3 - 2 * n4, n3 - n4, n3, this.comp), this.comp)];
            int from2;
            int n6 = from2 = this.from;
            int n8;
            int n7 = n8 = this.to - 1;
            while (true) {
                final int compare;
                if (from2 <= n7 && (compare = this.comp.compare(x[from2], n5)) <= 0) {
                    if (compare == 0) {
                        IntArrays.swap(x, n6++, from2);
                    }
                    ++from2;
                }
                else {
                    int compare2;
                    while (n7 >= from2 && (compare2 = this.comp.compare(x[n7], n5)) >= 0) {
                        if (compare2 == 0) {
                            IntArrays.swap(x, n7, n8--);
                        }
                        --n7;
                    }
                    if (from2 > n7) {
                        break;
                    }
                    IntArrays.swap(x, from2++, n7--);
                }
            }
            final int min = Math.min(n6 - this.from, from2 - n6);
            IntArrays.swap(x, this.from, from2 - min, min);
            final int min2 = Math.min(n8 - n7, this.to - n8 - 1);
            IntArrays.swap(x, from2, this.to - min2, min2);
            final int n9 = from2 - n6;
            final int n10 = n8 - n7;
            if (n9 > 1 && n10 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + n9, this.comp), new ForkJoinQuickSortComp(x, this.to - n10, this.to, this.comp));
            }
            else if (n9 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + n9, this.comp));
            }
            else {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortComp(x, this.to - n10, this.to, this.comp));
            }
        }
    }
    
    protected static class ForkJoinQuickSort extends RecursiveAction
    {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] x;
        
        public ForkJoinQuickSort(final int[] x, final int from, final int to) {
            this.from = from;
            this.to = to;
            this.x = x;
        }
        
        @Override
        protected void compute() {
            final int[] x = this.x;
            final int n = this.to - this.from;
            if (n < 8192) {
                IntArrays.quickSort(x, this.from, this.to);
                return;
            }
            final int n2 = this.from + n / 2;
            final int from = this.from;
            final int n3 = this.to - 1;
            final int n4 = n / 8;
            final int n5 = x[IntArrays.access$100(x, IntArrays.access$100(x, from, from + n4, from + 2 * n4), IntArrays.access$100(x, n2 - n4, n2, n2 + n4), IntArrays.access$100(x, n3 - 2 * n4, n3 - n4, n3))];
            int from2;
            int n6 = from2 = this.from;
            int n8;
            int n7 = n8 = this.to - 1;
            while (true) {
                final int compare;
                if (from2 <= n7 && (compare = Integer.compare(x[from2], n5)) <= 0) {
                    if (compare == 0) {
                        IntArrays.swap(x, n6++, from2);
                    }
                    ++from2;
                }
                else {
                    int compare2;
                    while (n7 >= from2 && (compare2 = Integer.compare(x[n7], n5)) >= 0) {
                        if (compare2 == 0) {
                            IntArrays.swap(x, n7, n8--);
                        }
                        --n7;
                    }
                    if (from2 > n7) {
                        break;
                    }
                    IntArrays.swap(x, from2++, n7--);
                }
            }
            final int min = Math.min(n6 - this.from, from2 - n6);
            IntArrays.swap(x, this.from, from2 - min, min);
            final int min2 = Math.min(n8 - n7, this.to - n8 - 1);
            IntArrays.swap(x, from2, this.to - min2, min2);
            final int n9 = from2 - n6;
            final int n10 = n8 - n7;
            if (n9 > 1 && n10 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort(x, this.from, this.from + n9), new ForkJoinQuickSort(x, this.to - n10, this.to));
            }
            else if (n9 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort(x, this.from, this.from + n9));
            }
            else {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort(x, this.to - n10, this.to));
            }
        }
    }
    
    protected static class ForkJoinQuickSortIndirect extends RecursiveAction
    {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final int[] x;
        
        public ForkJoinQuickSortIndirect(final int[] perm, final int[] x, final int from, final int to) {
            this.from = from;
            this.to = to;
            this.x = x;
            this.perm = perm;
        }
        
        @Override
        protected void compute() {
            final int[] x = this.x;
            final int n = this.to - this.from;
            if (n < 8192) {
                IntArrays.quickSortIndirect(this.perm, x, this.from, this.to);
                return;
            }
            final int n2 = this.from + n / 2;
            final int from = this.from;
            final int n3 = this.to - 1;
            final int n4 = n / 8;
            final int n5 = x[this.perm[IntArrays.access$200(this.perm, x, IntArrays.access$200(this.perm, x, from, from + n4, from + 2 * n4), IntArrays.access$200(this.perm, x, n2 - n4, n2, n2 + n4), IntArrays.access$200(this.perm, x, n3 - 2 * n4, n3 - n4, n3))]];
            int from2;
            int n6 = from2 = this.from;
            int n8;
            int n7 = n8 = this.to - 1;
            while (true) {
                final int compare;
                if (from2 <= n7 && (compare = Integer.compare(x[this.perm[from2]], n5)) <= 0) {
                    if (compare == 0) {
                        IntArrays.swap(this.perm, n6++, from2);
                    }
                    ++from2;
                }
                else {
                    int compare2;
                    while (n7 >= from2 && (compare2 = Integer.compare(x[this.perm[n7]], n5)) >= 0) {
                        if (compare2 == 0) {
                            IntArrays.swap(this.perm, n7, n8--);
                        }
                        --n7;
                    }
                    if (from2 > n7) {
                        break;
                    }
                    IntArrays.swap(this.perm, from2++, n7--);
                }
            }
            final int min = Math.min(n6 - this.from, from2 - n6);
            IntArrays.swap(this.perm, this.from, from2 - min, min);
            final int min2 = Math.min(n8 - n7, this.to - n8 - 1);
            IntArrays.swap(this.perm, from2, this.to - min2, min2);
            final int n9 = from2 - n6;
            final int n10 = n8 - n7;
            if (n9 > 1 && n10 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + n9), new ForkJoinQuickSortIndirect(this.perm, x, this.to - n10, this.to));
            }
            else if (n9 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + n9));
            }
            else {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.to - n10, this.to));
            }
        }
    }
    
    protected static class ForkJoinQuickSort2 extends RecursiveAction
    {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] x;
        private final int[] y;
        
        public ForkJoinQuickSort2(final int[] x, final int[] y, final int from, final int to) {
            this.from = from;
            this.to = to;
            this.x = x;
            this.y = y;
        }
        
        @Override
        protected void compute() {
            final int[] x = this.x;
            final int[] y = this.y;
            final int n = this.to - this.from;
            if (n < 8192) {
                IntArrays.quickSort(x, y, this.from, this.to);
                return;
            }
            final int n2 = this.from + n / 2;
            final int from = this.from;
            final int n3 = this.to - 1;
            final int n4 = n / 8;
            final int access$300 = IntArrays.access$300(x, y, IntArrays.access$300(x, y, from, from + n4, from + 2 * n4), IntArrays.access$300(x, y, n2 - n4, n2, n2 + n4), IntArrays.access$300(x, y, n3 - 2 * n4, n3 - n4, n3));
            final int n5 = x[access$300];
            final int n6 = y[access$300];
            int from2;
            int n7 = from2 = this.from;
            int n9;
            int n8 = n9 = this.to - 1;
            while (true) {
                final int compare;
                final int n10;
                if (from2 <= n8 && (n10 = (((compare = Integer.compare(x[from2], n5)) == 0) ? Integer.compare(y[from2], n6) : compare)) <= 0) {
                    if (n10 == 0) {
                        IntArrays.access$400(x, y, n7++, from2);
                    }
                    ++from2;
                }
                else {
                    int compare2;
                    int n11;
                    while (n8 >= from2 && (n11 = (((compare2 = Integer.compare(x[n8], n5)) == 0) ? Integer.compare(y[n8], n6) : compare2)) >= 0) {
                        if (n11 == 0) {
                            IntArrays.access$400(x, y, n8, n9--);
                        }
                        --n8;
                    }
                    if (from2 > n8) {
                        break;
                    }
                    IntArrays.access$400(x, y, from2++, n8--);
                }
            }
            final int min = Math.min(n7 - this.from, from2 - n7);
            IntArrays.access$500(x, y, this.from, from2 - min, min);
            final int min2 = Math.min(n9 - n8, this.to - n9 - 1);
            IntArrays.access$500(x, y, from2, this.to - min2, min2);
            final int n12 = from2 - n7;
            final int n13 = n9 - n8;
            if (n12 > 1 && n13 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + n12), new ForkJoinQuickSort2(x, y, this.to - n13, this.to));
            }
            else if (n12 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + n12));
            }
            else {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort2(x, y, this.to - n13, this.to));
            }
        }
    }
    
    protected static final class Segment
    {
        protected final int offset;
        protected final int length;
        protected final int level;
        
        protected Segment(final int offset, final int length, final int level) {
            this.offset = offset;
            this.length = length;
            this.level = level;
        }
        
        @Override
        public String toString() {
            return "Segment [offset=" + this.offset + ", length=" + this.length + ", level=" + this.level + "]";
        }
    }
    
    private static final class ArrayHashStrategy implements Hash.Strategy, Serializable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        private ArrayHashStrategy() {
        }
        
        public int hashCode(final int[] array) {
            return java.util.Arrays.hashCode(array);
        }
        
        public boolean equals(final int[] array, final int[] array2) {
            return java.util.Arrays.equals(array, array2);
        }
        
        @Override
        public boolean equals(final Object o, final Object o2) {
            return this.equals((int[])o, (int[])o2);
        }
        
        @Override
        public int hashCode(final Object o) {
            return this.hashCode((int[])o);
        }
        
        ArrayHashStrategy(final IntArrays$1 object) {
            this();
        }
    }
}
