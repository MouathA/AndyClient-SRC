package com.viaversion.viaversion.libs.fastutil.objects;

import java.lang.reflect.*;
import com.viaversion.viaversion.libs.fastutil.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.*;
import java.util.concurrent.*;
import java.io.*;

public final class ObjectArrays
{
    public static final Object[] EMPTY_ARRAY;
    public static final Object[] DEFAULT_EMPTY_ARRAY;
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;
    private static final int MERGESORT_NO_REC = 16;
    public static final Hash.Strategy HASH_STRATEGY;
    
    private ObjectArrays() {
    }
    
    private static Object[] newArray(final Object[] array, final int n) {
        final Class<? extends Object[]> class1 = array.getClass();
        if (class1 == Object[].class) {
            return (n == 0) ? ObjectArrays.EMPTY_ARRAY : new Object[n];
        }
        return (Object[])Array.newInstance(class1.getComponentType(), n);
    }
    
    public static Object[] forceCapacity(final Object[] array, final int n, final int n2) {
        final Object[] array2 = newArray(array, n);
        System.arraycopy(array, 0, array2, 0, n2);
        return array2;
    }
    
    public static Object[] ensureCapacity(final Object[] array, final int n) {
        return ensureCapacity(array, n, array.length);
    }
    
    public static Object[] ensureCapacity(final Object[] array, final int n, final int n2) {
        return (n > array.length) ? forceCapacity(array, n, n2) : array;
    }
    
    public static Object[] grow(final Object[] array, final int n) {
        return grow(array, n, array.length);
    }
    
    public static Object[] grow(final Object[] array, final int n, final int n2) {
        if (n > array.length) {
            final Object[] array2 = newArray(array, (int)Math.max(Math.min(array.length + (long)(array.length >> 1), 2147483639L), n));
            System.arraycopy(array, 0, array2, 0, n2);
            return array2;
        }
        return array;
    }
    
    public static Object[] trim(final Object[] array, final int n) {
        if (n >= array.length) {
            return array;
        }
        final Object[] array2 = newArray(array, n);
        System.arraycopy(array, 0, array2, 0, n);
        return array2;
    }
    
    public static Object[] setLength(final Object[] array, final int n) {
        if (n == array.length) {
            return array;
        }
        if (n < array.length) {
            return trim(array, n);
        }
        return ensureCapacity(array, n);
    }
    
    public static Object[] copy(final Object[] array, final int n, final int n2) {
        ensureOffsetLength(array, n, n2);
        final Object[] array2 = newArray(array, n2);
        System.arraycopy(array, n, array2, 0, n2);
        return array2;
    }
    
    public static Object[] copy(final Object[] array) {
        return array.clone();
    }
    
    @Deprecated
    public static void fill(final Object[] array, final Object o) {
        int length = array.length;
        while (length-- != 0) {
            array[length] = o;
        }
    }
    
    @Deprecated
    public static void fill(final Object[] array, final int n, int n2, final Object o) {
        ensureFromTo(array, n, n2);
        if (n == 0) {
            while (n2-- != 0) {
                array[n2] = o;
            }
        }
        else {
            for (int i = n; i < n2; ++i) {
                array[i] = o;
            }
        }
    }
    
    @Deprecated
    public static boolean equals(final Object[] array, final Object[] array2) {
        int length = array.length;
        if (length != array2.length) {
            return false;
        }
        while (length-- != 0) {
            if (!Objects.equals(array[length], array2[length])) {
                return false;
            }
        }
        return true;
    }
    
    public static void ensureFromTo(final Object[] array, final int n, final int n2) {
        Arrays.ensureFromTo(array.length, n, n2);
    }
    
    public static void ensureOffsetLength(final Object[] array, final int n, final int n2) {
        Arrays.ensureOffsetLength(array.length, n, n2);
    }
    
    public static void ensureSameLength(final Object[] array, final Object[] array2) {
        if (array.length != array2.length) {
            throw new IllegalArgumentException("Array size mismatch: " + array.length + " != " + array2.length);
        }
    }
    
    private static ForkJoinPool getPool() {
        final ForkJoinPool pool = ForkJoinTask.getPool();
        return (pool == null) ? ForkJoinPool.commonPool() : pool;
    }
    
    public static void swap(final Object[] array, final int n, final int n2) {
        final Object o = array[n];
        array[n] = array[n2];
        array[n2] = o;
    }
    
    public static void swap(final Object[] array, int n, int n2, final int n3) {
        while (0 < n3) {
            swap(array, n, n2);
            int n4 = 0;
            ++n4;
            ++n;
            ++n2;
        }
    }
    
    private static int med3(final Object[] array, final int n, final int n2, final int n3, final Comparator comparator) {
        final int compare = comparator.compare(array[n], array[n2]);
        final int compare2 = comparator.compare(array[n], array[n3]);
        final int compare3 = comparator.compare(array[n2], array[n3]);
        return (compare < 0) ? ((compare3 < 0) ? n2 : ((compare2 < 0) ? n3 : n)) : ((compare3 > 0) ? n2 : ((compare2 > 0) ? n3 : n));
    }
    
    private static void selectionSort(final Object[] array, final int n, final int n2, final Comparator comparator) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (comparator.compare(array[j], array[n3]) < 0) {
                    n3 = j;
                }
            }
            if (n3 != i) {
                final Object o = array[i];
                array[i] = array[n3];
                array[n3] = o;
            }
        }
    }
    
    private static void insertionSort(final Object[] array, final int n, final int n2, final Comparator comparator) {
        int n3 = n;
        while (++n3 < n2) {
            final Object o = array[n3];
            int n4 = n3;
            for (Object o2 = array[n4 - 1]; comparator.compare(o, o2) < 0; o2 = array[--n4 - 1]) {
                array[n4] = o2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
            }
            array[n4] = o;
        }
    }
    
    public static void quickSort(final Object[] array, final int n, final int n2, final Comparator comparator) {
        final int n3 = n2 - n;
        if (n3 < 16) {
            selectionSort(array, n, n2, comparator);
            return;
        }
        int med3 = n + n3 / 2;
        int med4 = n;
        int med5 = n2 - 1;
        if (n3 > 128) {
            final int n4 = n3 / 8;
            med4 = med3(array, med4, med4 + n4, med4 + 2 * n4, comparator);
            med3 = med3(array, med3 - n4, med3, med3 + n4, comparator);
            med5 = med3(array, med5 - 2 * n4, med5 - n4, med5, comparator);
        }
        final Object o = array[med3(array, med4, med3, med5, comparator)];
        int n6;
        int n5 = n6 = n;
        int n8;
        int n7 = n8 = n2 - 1;
        while (true) {
            final int compare;
            if (n6 <= n7 && (compare = comparator.compare(array[n6], o)) <= 0) {
                if (compare == 0) {
                    swap(array, n5++, n6);
                }
                ++n6;
            }
            else {
                int compare2;
                while (n7 >= n6 && (compare2 = comparator.compare(array[n7], o)) >= 0) {
                    if (compare2 == 0) {
                        swap(array, n7, n8--);
                    }
                    --n7;
                }
                if (n6 > n7) {
                    break;
                }
                swap(array, n6++, n7--);
            }
        }
        final int min = Math.min(n5 - n, n6 - n5);
        swap(array, n, n6 - min, min);
        final int min2 = Math.min(n8 - n7, n2 - n8 - 1);
        swap(array, n6, n2 - min2, min2);
        final int n9;
        if ((n9 = n6 - n5) > 1) {
            quickSort(array, n, n + n9, comparator);
        }
        final int n10;
        if ((n10 = n8 - n7) > 1) {
            quickSort(array, n2 - n10, n2, comparator);
        }
    }
    
    public static void quickSort(final Object[] array, final Comparator comparator) {
        quickSort(array, 0, array.length, comparator);
    }
    
    public static void parallelQuickSort(final Object[] array, final int n, final int n2, final Comparator comparator) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 8192 || pool.getParallelism() == 1) {
            quickSort(array, n, n2, comparator);
        }
        else {
            pool.invoke((ForkJoinTask<Object>)new ForkJoinQuickSortComp(array, n, n2, comparator));
        }
    }
    
    public static void parallelQuickSort(final Object[] array, final Comparator comparator) {
        parallelQuickSort(array, 0, array.length, comparator);
    }
    
    private static int med3(final Object[] array, final int n, final int n2, final int n3) {
        final int compareTo = ((Comparable)array[n]).compareTo(array[n2]);
        final int compareTo2 = ((Comparable)array[n]).compareTo(array[n3]);
        final int compareTo3 = ((Comparable)array[n2]).compareTo(array[n3]);
        return (compareTo < 0) ? ((compareTo3 < 0) ? n2 : ((compareTo2 < 0) ? n3 : n)) : ((compareTo3 > 0) ? n2 : ((compareTo2 > 0) ? n3 : n));
    }
    
    private static void selectionSort(final Object[] array, final int n, final int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                if (((Comparable)array[j]).compareTo(array[n3]) < 0) {
                    n3 = j;
                }
            }
            if (n3 != i) {
                final Object o = array[i];
                array[i] = array[n3];
                array[n3] = o;
            }
        }
    }
    
    private static void insertionSort(final Object[] array, final int n, final int n2) {
        int n3 = n;
        while (++n3 < n2) {
            final Object o = array[n3];
            int n4 = n3;
            for (Object o2 = array[n4 - 1]; ((Comparable<Object>)o).compareTo(o2) < 0; o2 = array[--n4 - 1]) {
                array[n4] = o2;
                if (n == n4 - 1) {
                    --n4;
                    break;
                }
            }
            array[n4] = o;
        }
    }
    
    public static void quickSort(final Object[] array, final int n, final int n2) {
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
        final Object o = array[med3(array, med4, med3, med5)];
        int n6;
        int n5 = n6 = n;
        int n8;
        int n7 = n8 = n2 - 1;
        while (true) {
            final int compareTo;
            if (n6 <= n7 && (compareTo = ((Comparable)array[n6]).compareTo(o)) <= 0) {
                if (compareTo == 0) {
                    swap(array, n5++, n6);
                }
                ++n6;
            }
            else {
                int compareTo2;
                while (n7 >= n6 && (compareTo2 = ((Comparable)array[n7]).compareTo(o)) >= 0) {
                    if (compareTo2 == 0) {
                        swap(array, n7, n8--);
                    }
                    --n7;
                }
                if (n6 > n7) {
                    break;
                }
                swap(array, n6++, n7--);
            }
        }
        final int min = Math.min(n5 - n, n6 - n5);
        swap(array, n, n6 - min, min);
        final int min2 = Math.min(n8 - n7, n2 - n8 - 1);
        swap(array, n6, n2 - min2, min2);
        final int n9;
        if ((n9 = n6 - n5) > 1) {
            quickSort(array, n, n + n9);
        }
        final int n10;
        if ((n10 = n8 - n7) > 1) {
            quickSort(array, n2 - n10, n2);
        }
    }
    
    public static void quickSort(final Object[] array) {
        quickSort(array, 0, array.length);
    }
    
    public static void parallelQuickSort(final Object[] array, final int n, final int n2) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 8192 || pool.getParallelism() == 1) {
            quickSort(array, n, n2);
        }
        else {
            pool.invoke((ForkJoinTask<Object>)new ForkJoinQuickSort(array, n, n2));
        }
    }
    
    public static void parallelQuickSort(final Object[] array) {
        parallelQuickSort(array, 0, array.length);
    }
    
    private static int med3Indirect(final int[] array, final Object[] array2, final int n, final int n2, final int n3) {
        final Object o = array2[array[n]];
        final Object o2 = array2[array[n2]];
        final Object o3 = array2[array[n3]];
        final int compareTo = ((Comparable<Object>)o).compareTo(o2);
        final int compareTo2 = ((Comparable<Object>)o).compareTo(o3);
        final int compareTo3 = ((Comparable<Object>)o2).compareTo(o3);
        return (compareTo < 0) ? ((compareTo3 < 0) ? n2 : ((compareTo2 < 0) ? n3 : n)) : ((compareTo3 > 0) ? n2 : ((compareTo2 > 0) ? n3 : n));
    }
    
    private static void insertionSortIndirect(final int[] array, final Object[] array2, final int n, final int n2) {
        int n3 = n;
        while (++n3 < n2) {
            final int n4 = array[n3];
            int n5 = n3;
            for (int n6 = array[n5 - 1]; ((Comparable)array2[n4]).compareTo(array2[n6]) < 0; n6 = array[--n5 - 1]) {
                array[n5] = n6;
                if (n == n5 - 1) {
                    --n5;
                    break;
                }
            }
            array[n5] = n4;
        }
    }
    
    public static void quickSortIndirect(final int[] array, final Object[] array2, final int n, final int n2) {
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
        final Object o = array2[array[med3Indirect(array, array2, med3Indirect2, med3Indirect, med3Indirect3)]];
        int n6;
        int n5 = n6 = n;
        int n8;
        int n7 = n8 = n2 - 1;
        while (true) {
            final int compareTo;
            if (n6 <= n7 && (compareTo = ((Comparable)array2[array[n6]]).compareTo(o)) <= 0) {
                if (compareTo == 0) {
                    IntArrays.swap(array, n5++, n6);
                }
                ++n6;
            }
            else {
                int compareTo2;
                while (n7 >= n6 && (compareTo2 = ((Comparable)array2[array[n7]]).compareTo(o)) >= 0) {
                    if (compareTo2 == 0) {
                        IntArrays.swap(array, n7, n8--);
                    }
                    --n7;
                }
                if (n6 > n7) {
                    break;
                }
                IntArrays.swap(array, n6++, n7--);
            }
        }
        final int min = Math.min(n5 - n, n6 - n5);
        IntArrays.swap(array, n, n6 - min, min);
        final int min2 = Math.min(n8 - n7, n2 - n8 - 1);
        IntArrays.swap(array, n6, n2 - min2, min2);
        final int n9;
        if ((n9 = n6 - n5) > 1) {
            quickSortIndirect(array, array2, n, n + n9);
        }
        final int n10;
        if ((n10 = n8 - n7) > 1) {
            quickSortIndirect(array, array2, n2 - n10, n2);
        }
    }
    
    public static void quickSortIndirect(final int[] array, final Object[] array2) {
        quickSortIndirect(array, array2, 0, array2.length);
    }
    
    public static void parallelQuickSortIndirect(final int[] array, final Object[] array2, final int n, final int n2) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 8192 || pool.getParallelism() == 1) {
            quickSortIndirect(array, array2, n, n2);
        }
        else {
            pool.invoke((ForkJoinTask<Object>)new ForkJoinQuickSortIndirect(array, array2, n, n2));
        }
    }
    
    public static void parallelQuickSortIndirect(final int[] array, final Object[] array2) {
        parallelQuickSortIndirect(array, array2, 0, array2.length);
    }
    
    public static void stabilize(final int[] array, final Object[] array2, final int n, final int n2) {
        int n3 = n;
        for (int i = n + 1; i < n2; ++i) {
            if (array2[array[i]] != array2[array[n3]]) {
                if (i - n3 > 1) {
                    IntArrays.parallelQuickSort(array, n3, i);
                }
                n3 = i;
            }
        }
        if (n2 - n3 > 1) {
            IntArrays.parallelQuickSort(array, n3, n2);
        }
    }
    
    public static void stabilize(final int[] array, final Object[] array2) {
        stabilize(array, array2, 0, array.length);
    }
    
    private static int med3(final Object[] array, final Object[] array2, final int n, final int n2, final int n3) {
        final int compareTo;
        final int n4 = ((compareTo = ((Comparable)array[n]).compareTo(array[n2])) == 0) ? ((Comparable)array2[n]).compareTo(array2[n2]) : compareTo;
        final int compareTo2;
        final int n5 = ((compareTo2 = ((Comparable)array[n]).compareTo(array[n3])) == 0) ? ((Comparable)array2[n]).compareTo(array2[n3]) : compareTo2;
        final int compareTo3;
        final int n6 = ((compareTo3 = ((Comparable)array[n2]).compareTo(array[n3])) == 0) ? ((Comparable)array2[n2]).compareTo(array2[n3]) : compareTo3;
        return (n4 < 0) ? ((n6 < 0) ? n2 : ((n5 < 0) ? n3 : n)) : ((n6 > 0) ? n2 : ((n5 > 0) ? n3 : n));
    }
    
    private static void swap(final Object[] array, final Object[] array2, final int n, final int n2) {
        final Object o = array[n];
        final Object o2 = array2[n];
        array[n] = array[n2];
        array2[n] = array2[n2];
        array[n2] = o;
        array2[n2] = o2;
    }
    
    private static void swap(final Object[] array, final Object[] array2, int n, int n2, final int n3) {
        while (0 < n3) {
            swap(array, array2, n, n2);
            int n4 = 0;
            ++n4;
            ++n;
            ++n2;
        }
    }
    
    private static void selectionSort(final Object[] array, final Object[] array2, final int n, final int n2) {
        for (int i = n; i < n2 - 1; ++i) {
            int n3 = i;
            for (int j = i + 1; j < n2; ++j) {
                final int compareTo;
                if ((compareTo = ((Comparable)array[j]).compareTo(array[n3])) < 0 || (compareTo == 0 && ((Comparable)array2[j]).compareTo(array2[n3]) < 0)) {
                    n3 = j;
                }
            }
            if (n3 != i) {
                final Object o = array[i];
                array[i] = array[n3];
                array[n3] = o;
                final Object o2 = array2[i];
                array2[i] = array2[n3];
                array2[n3] = o2;
            }
        }
    }
    
    public static void quickSort(final Object[] array, final Object[] array2, final int n, final int n2) {
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
        final Object o = array[med6];
        final Object o2 = array2[med6];
        int n6;
        int n5 = n6 = n;
        int n8;
        int n7 = n8 = n2 - 1;
        while (true) {
            final int compareTo;
            final int n9;
            if (n6 <= n7 && (n9 = (((compareTo = ((Comparable)array[n6]).compareTo(o)) == 0) ? ((Comparable)array2[n6]).compareTo(o2) : compareTo)) <= 0) {
                if (n9 == 0) {
                    swap(array, array2, n5++, n6);
                }
                ++n6;
            }
            else {
                int compareTo2;
                int n10;
                while (n7 >= n6 && (n10 = (((compareTo2 = ((Comparable)array[n7]).compareTo(o)) == 0) ? ((Comparable)array2[n7]).compareTo(o2) : compareTo2)) >= 0) {
                    if (n10 == 0) {
                        swap(array, array2, n7, n8--);
                    }
                    --n7;
                }
                if (n6 > n7) {
                    break;
                }
                swap(array, array2, n6++, n7--);
            }
        }
        final int min = Math.min(n5 - n, n6 - n5);
        swap(array, array2, n, n6 - min, min);
        final int min2 = Math.min(n8 - n7, n2 - n8 - 1);
        swap(array, array2, n6, n2 - min2, min2);
        final int n11;
        if ((n11 = n6 - n5) > 1) {
            quickSort(array, array2, n, n + n11);
        }
        final int n12;
        if ((n12 = n8 - n7) > 1) {
            quickSort(array, array2, n2 - n12, n2);
        }
    }
    
    public static void quickSort(final Object[] array, final Object[] array2) {
        ensureSameLength(array, array2);
        quickSort(array, array2, 0, array.length);
    }
    
    public static void parallelQuickSort(final Object[] array, final Object[] array2, final int n, final int n2) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 8192 || pool.getParallelism() == 1) {
            quickSort(array, array2, n, n2);
        }
        else {
            pool.invoke((ForkJoinTask<Object>)new ForkJoinQuickSort2(array, array2, n, n2));
        }
    }
    
    public static void parallelQuickSort(final Object[] array, final Object[] array2) {
        ensureSameLength(array, array2);
        parallelQuickSort(array, array2, 0, array.length);
    }
    
    public static void unstableSort(final Object[] array, final int n, final int n2) {
        quickSort(array, n, n2);
    }
    
    public static void unstableSort(final Object[] array) {
        unstableSort(array, 0, array.length);
    }
    
    public static void unstableSort(final Object[] array, final int n, final int n2, final Comparator comparator) {
        quickSort(array, n, n2, comparator);
    }
    
    public static void unstableSort(final Object[] array, final Comparator comparator) {
        unstableSort(array, 0, array.length, comparator);
    }
    
    public static void mergeSort(final Object[] array, final int n, final int n2, Object[] copy) {
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
        if (((Comparable)copy[n4 - 1]).compareTo(copy[n4]) <= 0) {
            System.arraycopy(copy, n, array, n, n3);
            return;
        }
        int i = n;
        int n5 = n;
        int n6 = n4;
        while (i < n2) {
            if (n6 >= n2 || (n5 < n4 && ((Comparable)copy[n5]).compareTo(copy[n6]) <= 0)) {
                array[i] = copy[n5++];
            }
            else {
                array[i] = copy[n6++];
            }
            ++i;
        }
    }
    
    public static void mergeSort(final Object[] array, final int n, final int n2) {
        mergeSort(array, n, n2, (Object[])null);
    }
    
    public static void mergeSort(final Object[] array) {
        mergeSort(array, 0, array.length);
    }
    
    public static void mergeSort(final Object[] array, final int n, final int n2, final Comparator comparator, Object[] copy) {
        final int n3 = n2 - n;
        if (n3 < 16) {
            insertionSort(array, n, n2, comparator);
            return;
        }
        if (copy == null) {
            copy = java.util.Arrays.copyOf(array, n2);
        }
        final int n4 = n + n2 >>> 1;
        mergeSort(copy, n, n4, comparator, array);
        mergeSort(copy, n4, n2, comparator, array);
        if (comparator.compare(copy[n4 - 1], copy[n4]) <= 0) {
            System.arraycopy(copy, n, array, n, n3);
            return;
        }
        int i = n;
        int n5 = n;
        int n6 = n4;
        while (i < n2) {
            if (n6 >= n2 || (n5 < n4 && comparator.compare(copy[n5], copy[n6]) <= 0)) {
                array[i] = copy[n5++];
            }
            else {
                array[i] = copy[n6++];
            }
            ++i;
        }
    }
    
    public static void mergeSort(final Object[] array, final int n, final int n2, final Comparator comparator) {
        mergeSort(array, n, n2, comparator, null);
    }
    
    public static void mergeSort(final Object[] array, final Comparator comparator) {
        mergeSort(array, 0, array.length, comparator);
    }
    
    public static void stableSort(final Object[] array, final int n, final int n2) {
        java.util.Arrays.sort(array, n, n2);
    }
    
    public static void stableSort(final Object[] array) {
        stableSort(array, 0, array.length);
    }
    
    public static void stableSort(final Object[] array, final int n, final int n2, final Comparator comparator) {
        java.util.Arrays.sort(array, n, n2, comparator);
    }
    
    public static void stableSort(final Object[] array, final Comparator comparator) {
        stableSort(array, 0, array.length, comparator);
    }
    
    public static int binarySearch(final Object[] array, int i, int n, final Object o) {
        --n;
        while (i <= n) {
            final int n2 = i + n >>> 1;
            final int compareTo = ((Comparable<Object>)array[n2]).compareTo(o);
            if (compareTo < 0) {
                i = n2 + 1;
            }
            else {
                if (compareTo <= 0) {
                    return n2;
                }
                n = n2 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static int binarySearch(final Object[] array, final Object o) {
        return binarySearch(array, 0, array.length, o);
    }
    
    public static int binarySearch(final Object[] array, int i, int n, final Object o, final Comparator comparator) {
        --n;
        while (i <= n) {
            final int n2 = i + n >>> 1;
            final int compare = comparator.compare(array[n2], o);
            if (compare < 0) {
                i = n2 + 1;
            }
            else {
                if (compare <= 0) {
                    return n2;
                }
                n = n2 - 1;
            }
        }
        return -(i + 1);
    }
    
    public static int binarySearch(final Object[] array, final Object o, final Comparator comparator) {
        return binarySearch(array, 0, array.length, o, comparator);
    }
    
    public static Object[] shuffle(final Object[] array, final int n, final int n2, final Random random) {
        int n3 = n2 - n;
        while (n3-- != 0) {
            final int nextInt = random.nextInt(n3 + 1);
            final Object o = array[n + n3];
            array[n + n3] = array[n + nextInt];
            array[n + nextInt] = o;
        }
        return array;
    }
    
    public static Object[] shuffle(final Object[] array, final Random random) {
        int length = array.length;
        while (length-- != 0) {
            final int nextInt = random.nextInt(length + 1);
            final Object o = array[length];
            array[length] = array[nextInt];
            array[nextInt] = o;
        }
        return array;
    }
    
    public static Object[] reverse(final Object[] array) {
        final int length = array.length;
        int n = length / 2;
        while (n-- != 0) {
            final Object o = array[length - n - 1];
            array[length - n - 1] = array[n];
            array[n] = o;
        }
        return array;
    }
    
    public static Object[] reverse(final Object[] array, final int n, final int n2) {
        final int n3 = n2 - n;
        int n4 = n3 / 2;
        while (n4-- != 0) {
            final Object o = array[n + n3 - n4 - 1];
            array[n + n3 - n4 - 1] = array[n + n4];
            array[n + n4] = o;
        }
        return array;
    }
    
    static int access$000(final Object[] array, final int n, final int n2, final int n3, final Comparator comparator) {
        return med3(array, n, n2, n3, comparator);
    }
    
    static int access$100(final Object[] array, final int n, final int n2, final int n3) {
        return med3(array, n, n2, n3);
    }
    
    static int access$200(final int[] array, final Object[] array2, final int n, final int n2, final int n3) {
        return med3Indirect(array, array2, n, n2, n3);
    }
    
    static int access$300(final Object[] array, final Object[] array2, final int n, final int n2, final int n3) {
        return med3(array, array2, n, n2, n3);
    }
    
    static void access$400(final Object[] array, final Object[] array2, final int n, final int n2) {
        swap(array, array2, n, n2);
    }
    
    static void access$500(final Object[] array, final Object[] array2, final int n, final int n2, final int n3) {
        swap(array, array2, n, n2, n3);
    }
    
    static {
        EMPTY_ARRAY = new Object[0];
        DEFAULT_EMPTY_ARRAY = new Object[0];
        HASH_STRATEGY = new ArrayHashStrategy(null);
    }
    
    protected static class ForkJoinQuickSortComp extends RecursiveAction
    {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final Object[] x;
        private final Comparator comp;
        
        public ForkJoinQuickSortComp(final Object[] x, final int from, final int to, final Comparator comp) {
            this.from = from;
            this.to = to;
            this.x = x;
            this.comp = comp;
        }
        
        @Override
        protected void compute() {
            final Object[] x = this.x;
            final int n = this.to - this.from;
            if (n < 8192) {
                ObjectArrays.quickSort(x, this.from, this.to, this.comp);
                return;
            }
            final int n2 = this.from + n / 2;
            final int from = this.from;
            final int n3 = this.to - 1;
            final int n4 = n / 8;
            final Object o = x[ObjectArrays.access$000(x, ObjectArrays.access$000(x, from, from + n4, from + 2 * n4, this.comp), ObjectArrays.access$000(x, n2 - n4, n2, n2 + n4, this.comp), ObjectArrays.access$000(x, n3 - 2 * n4, n3 - n4, n3, this.comp), this.comp)];
            int from2;
            int n5 = from2 = this.from;
            int n7;
            int n6 = n7 = this.to - 1;
            while (true) {
                final int compare;
                if (from2 <= n6 && (compare = this.comp.compare(x[from2], o)) <= 0) {
                    if (compare == 0) {
                        ObjectArrays.swap(x, n5++, from2);
                    }
                    ++from2;
                }
                else {
                    int compare2;
                    while (n6 >= from2 && (compare2 = this.comp.compare(x[n6], o)) >= 0) {
                        if (compare2 == 0) {
                            ObjectArrays.swap(x, n6, n7--);
                        }
                        --n6;
                    }
                    if (from2 > n6) {
                        break;
                    }
                    ObjectArrays.swap(x, from2++, n6--);
                }
            }
            final int min = Math.min(n5 - this.from, from2 - n5);
            ObjectArrays.swap(x, this.from, from2 - min, min);
            final int min2 = Math.min(n7 - n6, this.to - n7 - 1);
            ObjectArrays.swap(x, from2, this.to - min2, min2);
            final int n8 = from2 - n5;
            final int n9 = n7 - n6;
            if (n8 > 1 && n9 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + n8, this.comp), new ForkJoinQuickSortComp(x, this.to - n9, this.to, this.comp));
            }
            else if (n8 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortComp(x, this.from, this.from + n8, this.comp));
            }
            else {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortComp(x, this.to - n9, this.to, this.comp));
            }
        }
    }
    
    protected static class ForkJoinQuickSort extends RecursiveAction
    {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final Object[] x;
        
        public ForkJoinQuickSort(final Object[] x, final int from, final int to) {
            this.from = from;
            this.to = to;
            this.x = x;
        }
        
        @Override
        protected void compute() {
            final Object[] x = this.x;
            final int n = this.to - this.from;
            if (n < 8192) {
                ObjectArrays.quickSort(x, this.from, this.to);
                return;
            }
            final int n2 = this.from + n / 2;
            final int from = this.from;
            final int n3 = this.to - 1;
            final int n4 = n / 8;
            final Object o = x[ObjectArrays.access$100(x, ObjectArrays.access$100(x, from, from + n4, from + 2 * n4), ObjectArrays.access$100(x, n2 - n4, n2, n2 + n4), ObjectArrays.access$100(x, n3 - 2 * n4, n3 - n4, n3))];
            int from2;
            int n5 = from2 = this.from;
            int n7;
            int n6 = n7 = this.to - 1;
            while (true) {
                final int compareTo;
                if (from2 <= n6 && (compareTo = ((Comparable)x[from2]).compareTo(o)) <= 0) {
                    if (compareTo == 0) {
                        ObjectArrays.swap(x, n5++, from2);
                    }
                    ++from2;
                }
                else {
                    int compareTo2;
                    while (n6 >= from2 && (compareTo2 = ((Comparable)x[n6]).compareTo(o)) >= 0) {
                        if (compareTo2 == 0) {
                            ObjectArrays.swap(x, n6, n7--);
                        }
                        --n6;
                    }
                    if (from2 > n6) {
                        break;
                    }
                    ObjectArrays.swap(x, from2++, n6--);
                }
            }
            final int min = Math.min(n5 - this.from, from2 - n5);
            ObjectArrays.swap(x, this.from, from2 - min, min);
            final int min2 = Math.min(n7 - n6, this.to - n7 - 1);
            ObjectArrays.swap(x, from2, this.to - min2, min2);
            final int n8 = from2 - n5;
            final int n9 = n7 - n6;
            if (n8 > 1 && n9 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort(x, this.from, this.from + n8), new ForkJoinQuickSort(x, this.to - n9, this.to));
            }
            else if (n8 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort(x, this.from, this.from + n8));
            }
            else {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort(x, this.to - n9, this.to));
            }
        }
    }
    
    protected static class ForkJoinQuickSortIndirect extends RecursiveAction
    {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final int[] perm;
        private final Object[] x;
        
        public ForkJoinQuickSortIndirect(final int[] perm, final Object[] x, final int from, final int to) {
            this.from = from;
            this.to = to;
            this.x = x;
            this.perm = perm;
        }
        
        @Override
        protected void compute() {
            final Object[] x = this.x;
            final int n = this.to - this.from;
            if (n < 8192) {
                ObjectArrays.quickSortIndirect(this.perm, x, this.from, this.to);
                return;
            }
            final int n2 = this.from + n / 2;
            final int from = this.from;
            final int n3 = this.to - 1;
            final int n4 = n / 8;
            final Object o = x[this.perm[ObjectArrays.access$200(this.perm, x, ObjectArrays.access$200(this.perm, x, from, from + n4, from + 2 * n4), ObjectArrays.access$200(this.perm, x, n2 - n4, n2, n2 + n4), ObjectArrays.access$200(this.perm, x, n3 - 2 * n4, n3 - n4, n3))]];
            int from2;
            int n5 = from2 = this.from;
            int n7;
            int n6 = n7 = this.to - 1;
            while (true) {
                final int compareTo;
                if (from2 <= n6 && (compareTo = ((Comparable)x[this.perm[from2]]).compareTo(o)) <= 0) {
                    if (compareTo == 0) {
                        IntArrays.swap(this.perm, n5++, from2);
                    }
                    ++from2;
                }
                else {
                    int compareTo2;
                    while (n6 >= from2 && (compareTo2 = ((Comparable)x[this.perm[n6]]).compareTo(o)) >= 0) {
                        if (compareTo2 == 0) {
                            IntArrays.swap(this.perm, n6, n7--);
                        }
                        --n6;
                    }
                    if (from2 > n6) {
                        break;
                    }
                    IntArrays.swap(this.perm, from2++, n6--);
                }
            }
            final int min = Math.min(n5 - this.from, from2 - n5);
            IntArrays.swap(this.perm, this.from, from2 - min, min);
            final int min2 = Math.min(n7 - n6, this.to - n7 - 1);
            IntArrays.swap(this.perm, from2, this.to - min2, min2);
            final int n8 = from2 - n5;
            final int n9 = n7 - n6;
            if (n8 > 1 && n9 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + n8), new ForkJoinQuickSortIndirect(this.perm, x, this.to - n9, this.to));
            }
            else if (n8 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.from, this.from + n8));
            }
            else {
                ForkJoinTask.invokeAll(new ForkJoinQuickSortIndirect(this.perm, x, this.to - n9, this.to));
            }
        }
    }
    
    protected static class ForkJoinQuickSort2 extends RecursiveAction
    {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final Object[] x;
        private final Object[] y;
        
        public ForkJoinQuickSort2(final Object[] x, final Object[] y, final int from, final int to) {
            this.from = from;
            this.to = to;
            this.x = x;
            this.y = y;
        }
        
        @Override
        protected void compute() {
            final Object[] x = this.x;
            final Object[] y = this.y;
            final int n = this.to - this.from;
            if (n < 8192) {
                ObjectArrays.quickSort(x, y, this.from, this.to);
                return;
            }
            final int n2 = this.from + n / 2;
            final int from = this.from;
            final int n3 = this.to - 1;
            final int n4 = n / 8;
            final int access$300 = ObjectArrays.access$300(x, y, ObjectArrays.access$300(x, y, from, from + n4, from + 2 * n4), ObjectArrays.access$300(x, y, n2 - n4, n2, n2 + n4), ObjectArrays.access$300(x, y, n3 - 2 * n4, n3 - n4, n3));
            final Object o = x[access$300];
            final Object o2 = y[access$300];
            int from2;
            int n5 = from2 = this.from;
            int n7;
            int n6 = n7 = this.to - 1;
            while (true) {
                final int compareTo;
                final int n8;
                if (from2 <= n6 && (n8 = (((compareTo = ((Comparable)x[from2]).compareTo(o)) == 0) ? ((Comparable)y[from2]).compareTo(o2) : compareTo)) <= 0) {
                    if (n8 == 0) {
                        ObjectArrays.access$400(x, y, n5++, from2);
                    }
                    ++from2;
                }
                else {
                    int compareTo2;
                    int n9;
                    while (n6 >= from2 && (n9 = (((compareTo2 = ((Comparable)x[n6]).compareTo(o)) == 0) ? ((Comparable)y[n6]).compareTo(o2) : compareTo2)) >= 0) {
                        if (n9 == 0) {
                            ObjectArrays.access$400(x, y, n6, n7--);
                        }
                        --n6;
                    }
                    if (from2 > n6) {
                        break;
                    }
                    ObjectArrays.access$400(x, y, from2++, n6--);
                }
            }
            final int min = Math.min(n5 - this.from, from2 - n5);
            ObjectArrays.access$500(x, y, this.from, from2 - min, min);
            final int min2 = Math.min(n7 - n6, this.to - n7 - 1);
            ObjectArrays.access$500(x, y, from2, this.to - min2, min2);
            final int n10 = from2 - n5;
            final int n11 = n7 - n6;
            if (n10 > 1 && n11 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + n10), new ForkJoinQuickSort2(x, y, this.to - n11, this.to));
            }
            else if (n10 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort2(x, y, this.from, this.from + n10));
            }
            else {
                ForkJoinTask.invokeAll(new ForkJoinQuickSort2(x, y, this.to - n11, this.to));
            }
        }
    }
    
    private static final class ArrayHashStrategy implements Hash.Strategy, Serializable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        private ArrayHashStrategy() {
        }
        
        public int hashCode(final Object[] array) {
            return java.util.Arrays.hashCode(array);
        }
        
        public boolean equals(final Object[] array, final Object[] array2) {
            return java.util.Arrays.equals(array, array2);
        }
        
        @Override
        public boolean equals(final Object o, final Object o2) {
            return this.equals((Object[])o, (Object[])o2);
        }
        
        @Override
        public int hashCode(final Object o) {
            return this.hashCode((Object[])o);
        }
        
        ArrayHashStrategy(final ObjectArrays$1 object) {
            this();
        }
    }
}
