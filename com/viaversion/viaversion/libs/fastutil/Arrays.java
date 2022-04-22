package com.viaversion.viaversion.libs.fastutil;

import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.concurrent.*;

public class Arrays
{
    public static final int MAX_ARRAY_SIZE = 2147483639;
    private static final int MERGESORT_NO_REC = 16;
    private static final int QUICKSORT_NO_REC = 16;
    private static final int PARALLEL_QUICKSORT_NO_FORK = 8192;
    private static final int QUICKSORT_MEDIAN_OF_9 = 128;
    
    private Arrays() {
    }
    
    public static void ensureFromTo(final int n, final int n2, final int n3) {
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Start index (" + n2 + ") is negative");
        }
        if (n2 > n3) {
            throw new IllegalArgumentException("Start index (" + n2 + ") is greater than end index (" + n3 + ")");
        }
        if (n3 > n) {
            throw new ArrayIndexOutOfBoundsException("End index (" + n3 + ") is greater than array length (" + n + ")");
        }
    }
    
    public static void ensureOffsetLength(final int n, final int n2, final int n3) {
        if (n2 < 0) {
            throw new ArrayIndexOutOfBoundsException("Offset (" + n2 + ") is negative");
        }
        if (n3 < 0) {
            throw new IllegalArgumentException("Length (" + n3 + ") is negative");
        }
        if (n2 + n3 > n) {
            throw new ArrayIndexOutOfBoundsException("Last index (" + (n2 + n3) + ") is greater than array length (" + n + ")");
        }
    }
    
    private static void inPlaceMerge(final int n, int n2, final int n3, final IntComparator intComparator, final Swapper swapper) {
        if (n >= n2 || n2 >= n3) {
            return;
        }
        if (n3 - n == 2) {
            if (intComparator.compare(n2, n) < 0) {
                swapper.swap(n, n2);
            }
            return;
        }
        int upperBound;
        int lowerBound;
        if (n2 - n > n3 - n2) {
            upperBound = n + (n2 - n) / 2;
            lowerBound = lowerBound(n2, n3, upperBound, intComparator);
        }
        else {
            lowerBound = n2 + (n3 - n2) / 2;
            upperBound = upperBound(n, n2, lowerBound, intComparator);
        }
        final int n4 = upperBound;
        final int n5 = n2;
        final int n6 = lowerBound;
        if (n5 != n4 && n5 != n6) {
            int i = n4;
            int n7 = n5;
            while (i < --n7) {
                swapper.swap(i++, n7);
            }
            int j = n5;
            int n8 = n6;
            while (j < --n8) {
                swapper.swap(j++, n8);
            }
            int k = n4;
            int n9 = n6;
            while (k < --n9) {
                swapper.swap(k++, n9);
            }
        }
        n2 = upperBound + (lowerBound - n2);
        inPlaceMerge(n, upperBound, n2, intComparator, swapper);
        inPlaceMerge(n2, lowerBound, n3, intComparator, swapper);
    }
    
    private static int lowerBound(int n, final int n2, final int n3, final IntComparator intComparator) {
        int i = n2 - n;
        while (i > 0) {
            final int n4 = i / 2;
            final int n5 = n + n4;
            if (intComparator.compare(n5, n3) < 0) {
                n = n5 + 1;
                i -= n4 + 1;
            }
            else {
                i = n4;
            }
        }
        return n;
    }
    
    private static int upperBound(int n, final int n2, final int n3, final IntComparator intComparator) {
        int i = n2 - n;
        while (i > 0) {
            final int n4 = i / 2;
            final int n5 = n + n4;
            if (intComparator.compare(n3, n5) < 0) {
                i = n4;
            }
            else {
                n = n5 + 1;
                i -= n4 + 1;
            }
        }
        return n;
    }
    
    private static int med3(final int n, final int n2, final int n3, final IntComparator intComparator) {
        final int compare = intComparator.compare(n, n2);
        final int compare2 = intComparator.compare(n, n3);
        final int compare3 = intComparator.compare(n2, n3);
        return (compare < 0) ? ((compare3 < 0) ? n2 : ((compare2 < 0) ? n3 : n)) : ((compare3 > 0) ? n2 : ((compare2 > 0) ? n3 : n));
    }
    
    private static ForkJoinPool getPool() {
        final ForkJoinPool pool = ForkJoinTask.getPool();
        return (pool == null) ? ForkJoinPool.commonPool() : pool;
    }
    
    public static void mergeSort(final int n, final int n2, final IntComparator intComparator, final Swapper swapper) {
        if (n2 - n < 16) {
            for (int i = n; i < n2; ++i) {
                for (int n3 = i; n3 > n && intComparator.compare(n3 - 1, n3) > 0; --n3) {
                    swapper.swap(n3, n3 - 1);
                }
            }
            return;
        }
        final int n4 = n + n2 >>> 1;
        mergeSort(n, n4, intComparator, swapper);
        mergeSort(n4, n2, intComparator, swapper);
        if (intComparator.compare(n4 - 1, n4) <= 0) {
            return;
        }
        inPlaceMerge(n, n4, n2, intComparator, swapper);
    }
    
    protected static void swap(final Swapper swapper, int n, int n2, final int n3) {
        while (0 < n3) {
            swapper.swap(n, n2);
            int n4 = 0;
            ++n4;
            ++n;
            ++n2;
        }
    }
    
    public static void parallelQuickSort(final int n, final int n2, final IntComparator intComparator, final Swapper swapper) {
        final ForkJoinPool pool = getPool();
        if (n2 - n < 8192 || pool.getParallelism() == 1) {
            quickSort(n, n2, intComparator, swapper);
        }
        else {
            pool.invoke((ForkJoinTask<Object>)new ForkJoinGenericQuickSort(n, n2, intComparator, swapper));
        }
    }
    
    public static void quickSort(final int n, final int n2, final IntComparator intComparator, final Swapper swapper) {
        final int n3 = n2 - n;
        if (n3 < 16) {
            for (int i = n; i < n2; ++i) {
                for (int n4 = i; n4 > n && intComparator.compare(n4 - 1, n4) > 0; --n4) {
                    swapper.swap(n4, n4 - 1);
                }
            }
            return;
        }
        int med3 = n + n3 / 2;
        int med4 = n;
        int med5 = n2 - 1;
        if (n3 > 128) {
            final int n5 = n3 / 8;
            med4 = med3(med4, med4 + n5, med4 + 2 * n5, intComparator);
            med3 = med3(med3 - n5, med3, med3 + n5, intComparator);
            med5 = med3(med5 - 2 * n5, med5 - n5, med5, intComparator);
        }
        int med6 = med3(med4, med3, med5, intComparator);
        int n7;
        int n6 = n7 = n;
        int n9;
        int n8 = n9 = n2 - 1;
        while (true) {
            final int compare;
            if (n7 <= n8 && (compare = intComparator.compare(n7, med6)) <= 0) {
                if (compare == 0) {
                    if (n6 == med6) {
                        med6 = n7;
                    }
                    else if (n7 == med6) {
                        med6 = n6;
                    }
                    swapper.swap(n6++, n7);
                }
                ++n7;
            }
            else {
                int compare2;
                while (n8 >= n7 && (compare2 = intComparator.compare(n8, med6)) >= 0) {
                    if (compare2 == 0) {
                        if (n8 == med6) {
                            med6 = n9;
                        }
                        else if (n9 == med6) {
                            med6 = n8;
                        }
                        swapper.swap(n8, n9--);
                    }
                    --n8;
                }
                if (n7 > n8) {
                    break;
                }
                if (n7 == med6) {
                    med6 = n9;
                }
                else if (n8 == med6) {
                    med6 = n8;
                }
                swapper.swap(n7++, n8--);
            }
        }
        final int min = Math.min(n6 - n, n7 - n6);
        swap(swapper, n, n7 - min, min);
        final int min2 = Math.min(n9 - n8, n2 - n9 - 1);
        swap(swapper, n7, n2 - min2, min2);
        final int n10;
        if ((n10 = n7 - n6) > 1) {
            quickSort(n, n + n10, intComparator, swapper);
        }
        final int n11;
        if ((n11 = n9 - n8) > 1) {
            quickSort(n2 - n11, n2, intComparator, swapper);
        }
    }
    
    static int access$000(final int n, final int n2, final int n3, final IntComparator intComparator) {
        return med3(n, n2, n3, intComparator);
    }
    
    protected static class ForkJoinGenericQuickSort extends RecursiveAction
    {
        private static final long serialVersionUID = 1L;
        private final int from;
        private final int to;
        private final IntComparator comp;
        private final Swapper swapper;
        
        public ForkJoinGenericQuickSort(final int from, final int to, final IntComparator comp, final Swapper swapper) {
            this.from = from;
            this.to = to;
            this.comp = comp;
            this.swapper = swapper;
        }
        
        @Override
        protected void compute() {
            final int n = this.to - this.from;
            if (n < 8192) {
                Arrays.quickSort(this.from, this.to, this.comp, this.swapper);
                return;
            }
            final int n2 = this.from + n / 2;
            final int from = this.from;
            final int n3 = this.to - 1;
            final int n4 = n / 8;
            int access$000 = Arrays.access$000(Arrays.access$000(from, from + n4, from + 2 * n4, this.comp), Arrays.access$000(n2 - n4, n2, n2 + n4, this.comp), Arrays.access$000(n3 - 2 * n4, n3 - n4, n3, this.comp), this.comp);
            int from2;
            int n5 = from2 = this.from;
            int n7;
            int n6 = n7 = this.to - 1;
            while (true) {
                final int compare;
                if (from2 <= n6 && (compare = this.comp.compare(from2, access$000)) <= 0) {
                    if (compare == 0) {
                        if (n5 == access$000) {
                            access$000 = from2;
                        }
                        else if (from2 == access$000) {
                            access$000 = n5;
                        }
                        this.swapper.swap(n5++, from2);
                    }
                    ++from2;
                }
                else {
                    int compare2;
                    while (n6 >= from2 && (compare2 = this.comp.compare(n6, access$000)) >= 0) {
                        if (compare2 == 0) {
                            if (n6 == access$000) {
                                access$000 = n7;
                            }
                            else if (n7 == access$000) {
                                access$000 = n6;
                            }
                            this.swapper.swap(n6, n7--);
                        }
                        --n6;
                    }
                    if (from2 > n6) {
                        break;
                    }
                    if (from2 == access$000) {
                        access$000 = n7;
                    }
                    else if (n6 == access$000) {
                        access$000 = n6;
                    }
                    this.swapper.swap(from2++, n6--);
                }
            }
            final int min = Math.min(n5 - this.from, from2 - n5);
            Arrays.swap(this.swapper, this.from, from2 - min, min);
            final int min2 = Math.min(n7 - n6, this.to - n7 - 1);
            Arrays.swap(this.swapper, from2, this.to - min2, min2);
            final int n8 = from2 - n5;
            final int n9 = n7 - n6;
            if (n8 > 1 && n9 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinGenericQuickSort(this.from, this.from + n8, this.comp, this.swapper), new ForkJoinGenericQuickSort(this.to - n9, this.to, this.comp, this.swapper));
            }
            else if (n8 > 1) {
                ForkJoinTask.invokeAll(new ForkJoinGenericQuickSort(this.from, this.from + n8, this.comp, this.swapper));
            }
            else {
                ForkJoinTask.invokeAll(new ForkJoinGenericQuickSort(this.to - n9, this.to, this.comp, this.swapper));
            }
        }
    }
}
