package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.bytes.*;
import com.viaversion.viaversion.libs.fastutil.shorts.*;
import com.viaversion.viaversion.libs.fastutil.chars.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;
import java.util.*;
import java.io.*;

public final class IntSpliterators
{
    static final int BASE_SPLITERATOR_CHARACTERISTICS = 256;
    public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 320;
    public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16720;
    public static final int SET_SPLITERATOR_CHARACTERISTICS = 321;
    private static final int SORTED_CHARACTERISTICS = 20;
    public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 341;
    public static final EmptySpliterator EMPTY_SPLITERATOR;
    
    private IntSpliterators() {
    }
    
    public static IntSpliterator singleton(final int n) {
        return new SingletonSpliterator(n);
    }
    
    public static IntSpliterator singleton(final int n, final IntComparator intComparator) {
        return new SingletonSpliterator(n, intComparator);
    }
    
    public static IntSpliterator wrap(final int[] array, final int n, final int n2) {
        IntArrays.ensureOffsetLength(array, n, n2);
        return new ArraySpliterator(array, n, n2, 0);
    }
    
    public static IntSpliterator wrap(final int[] array) {
        return new ArraySpliterator(array, 0, array.length, 0);
    }
    
    public static IntSpliterator wrap(final int[] array, final int n, final int n2, final int n3) {
        IntArrays.ensureOffsetLength(array, n, n2);
        return new ArraySpliterator(array, n, n2, n3);
    }
    
    public static IntSpliterator wrapPreSorted(final int[] array, final int n, final int n2, final int n3, final IntComparator intComparator) {
        IntArrays.ensureOffsetLength(array, n, n2);
        return new ArraySpliteratorWithComparator(array, n, n2, n3, intComparator);
    }
    
    public static IntSpliterator wrapPreSorted(final int[] array, final int n, final int n2, final IntComparator intComparator) {
        return wrapPreSorted(array, n, n2, 0, intComparator);
    }
    
    public static IntSpliterator wrapPreSorted(final int[] array, final IntComparator intComparator) {
        return wrapPreSorted(array, 0, array.length, intComparator);
    }
    
    public static IntSpliterator asIntSpliterator(final Spliterator spliterator) {
        if (spliterator instanceof IntSpliterator) {
            return (IntSpliterator)spliterator;
        }
        if (spliterator instanceof Spliterator.OfInt) {
            return new PrimitiveSpliteratorWrapper((Spliterator.OfInt)spliterator);
        }
        return new SpliteratorWrapper(spliterator);
    }
    
    public static IntSpliterator asIntSpliterator(final Spliterator spliterator, final IntComparator intComparator) {
        if (spliterator instanceof IntSpliterator) {
            throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + IntSpliterator.class.getSimpleName());
        }
        if (spliterator instanceof Spliterator.OfInt) {
            return new PrimitiveSpliteratorWrapperWithComparator((Spliterator.OfInt)spliterator, intComparator);
        }
        return new SpliteratorWrapperWithComparator(spliterator, intComparator);
    }
    
    public static void onEachMatching(final IntSpliterator intSpliterator, final IntPredicate intPredicate, final IntConsumer intConsumer) {
        Objects.requireNonNull(intPredicate);
        Objects.requireNonNull(intConsumer);
        intSpliterator.forEachRemaining(IntSpliterators::lambda$onEachMatching$0);
    }
    
    public static IntSpliterator fromTo(final int n, final int n2) {
        return new IntervalSpliterator(n, n2);
    }
    
    public static IntSpliterator concat(final IntSpliterator... array) {
        return concat(array, 0, array.length);
    }
    
    public static IntSpliterator concat(final IntSpliterator[] array, final int n, final int n2) {
        return new SpliteratorConcatenator(array, n, n2);
    }
    
    public static IntSpliterator asSpliterator(final IntIterator intIterator, final long n, final int n2) {
        return new SpliteratorFromIterator(intIterator, n, n2);
    }
    
    public static IntSpliterator asSpliteratorFromSorted(final IntIterator intIterator, final long n, final int n2, final IntComparator intComparator) {
        return new SpliteratorFromIteratorWithComparator(intIterator, n, n2, intComparator);
    }
    
    public static IntSpliterator asSpliteratorUnknownSize(final IntIterator intIterator, final int n) {
        return new SpliteratorFromIterator(intIterator, n);
    }
    
    public static IntSpliterator asSpliteratorFromSortedUnknownSize(final IntIterator intIterator, final int n, final IntComparator intComparator) {
        return new SpliteratorFromIteratorWithComparator(intIterator, n, intComparator);
    }
    
    public static IntIterator asIterator(final IntSpliterator intSpliterator) {
        return new IteratorFromSpliterator(intSpliterator);
    }
    
    public static IntSpliterator wrap(final ByteSpliterator byteSpliterator) {
        return (IntSpliterator)new IntSpliterators.ByteSpliteratorWrapper(byteSpliterator);
    }
    
    public static IntSpliterator wrap(final ShortSpliterator shortSpliterator) {
        return (IntSpliterator)new IntSpliterators.ShortSpliteratorWrapper(shortSpliterator);
    }
    
    public static IntSpliterator wrap(final CharSpliterator charSpliterator) {
        return (IntSpliterator)new IntSpliterators.CharSpliteratorWrapper(charSpliterator);
    }
    
    private static void lambda$onEachMatching$0(final IntPredicate intPredicate, final IntConsumer intConsumer, final int n) {
        if (intPredicate.test(n)) {
            intConsumer.accept(n);
        }
    }
    
    static {
        EMPTY_SPLITERATOR = new EmptySpliterator();
    }
    
    private static class SingletonSpliterator implements IntSpliterator
    {
        private final int element;
        private final IntComparator comparator;
        private boolean consumed;
        private static final int CHARACTERISTICS = 17749;
        
        public SingletonSpliterator(final int n) {
            this(n, null);
        }
        
        public SingletonSpliterator(final int element, final IntComparator comparator) {
            this.consumed = false;
            this.element = element;
            this.comparator = comparator;
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            if (this.consumed) {
                return false;
            }
            this.consumed = true;
            intConsumer.accept(this.element);
            return true;
        }
        
        @Override
        public IntSpliterator trySplit() {
            return null;
        }
        
        @Override
        public long estimateSize() {
            return this.consumed ? 0 : 1;
        }
        
        @Override
        public int characteristics() {
            return 17749;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            if (!this.consumed) {
                this.consumed = true;
                intConsumer.accept(this.element);
            }
        }
        
        @Override
        public IntComparator getComparator() {
            return this.comparator;
        }
        
        @Override
        public long skip(final long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n == 0L || this.consumed) {
                return 0L;
            }
            this.consumed = true;
            return 1L;
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
        
        @Override
        public boolean tryAdvance(final Object o) {
            return this.tryAdvance((IntConsumer)o);
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class ArraySpliterator implements IntSpliterator
    {
        private static final int BASE_CHARACTERISTICS = 16720;
        final int[] array;
        private final int offset;
        private int length;
        private int curr;
        final int characteristics;
        
        public ArraySpliterator(final int[] array, final int offset, final int length, final int n) {
            this.array = array;
            this.offset = offset;
            this.length = length;
            this.characteristics = (0x4150 | n);
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            if (this.curr >= this.length) {
                return false;
            }
            Objects.requireNonNull(intConsumer);
            intConsumer.accept(this.array[this.offset + this.curr++]);
            return true;
        }
        
        @Override
        public long estimateSize() {
            return this.length - this.curr;
        }
        
        @Override
        public int characteristics() {
            return this.characteristics;
        }
        
        protected ArraySpliterator makeForSplit(final int n, final int n2) {
            return new ArraySpliterator(this.array, n, n2, this.characteristics);
        }
        
        @Override
        public IntSpliterator trySplit() {
            final int n = this.length - this.curr >> 1;
            if (n <= 1) {
                return null;
            }
            final int curr = this.curr + n;
            final int n2 = this.offset + this.curr;
            this.curr = curr;
            return this.makeForSplit(n2, n);
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            while (this.curr < this.length) {
                intConsumer.accept(this.array[this.offset + this.curr]);
                ++this.curr;
            }
        }
        
        @Override
        public long skip(long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.curr >= this.length) {
                return 0L;
            }
            final int n2 = this.length - this.curr;
            if (n < n2) {
                this.curr = SafeMath.safeLongToInt(this.curr + n);
                return n;
            }
            n = n2;
            this.curr = this.length;
            return n;
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
        
        @Override
        public boolean tryAdvance(final Object o) {
            return this.tryAdvance((IntConsumer)o);
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class ArraySpliteratorWithComparator extends ArraySpliterator
    {
        private final IntComparator comparator;
        
        public ArraySpliteratorWithComparator(final int[] array, final int n, final int n2, final int n3, final IntComparator comparator) {
            super(array, n, n2, n3 | 0x14);
            this.comparator = comparator;
        }
        
        @Override
        protected ArraySpliteratorWithComparator makeForSplit(final int n, final int n2) {
            return new ArraySpliteratorWithComparator(this.array, n, n2, this.characteristics, this.comparator);
        }
        
        @Override
        public IntComparator getComparator() {
            return this.comparator;
        }
        
        @Override
        protected ArraySpliterator makeForSplit(final int n, final int n2) {
            return this.makeForSplit(n, n2);
        }
        
        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }
    }
    
    private static class PrimitiveSpliteratorWrapper implements IntSpliterator
    {
        final Spliterator.OfInt i;
        
        public PrimitiveSpliteratorWrapper(final Spliterator.OfInt i) {
            this.i = i;
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            return this.i.tryAdvance(intConsumer);
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            this.i.forEachRemaining(intConsumer);
        }
        
        @Override
        public long estimateSize() {
            return this.i.estimateSize();
        }
        
        @Override
        public int characteristics() {
            return this.i.characteristics();
        }
        
        @Override
        public IntComparator getComparator() {
            return IntComparators.asIntComparator(this.i.getComparator());
        }
        
        @Override
        public IntSpliterator trySplit() {
            final Spliterator.OfInt trySplit = this.i.trySplit();
            if (trySplit == null) {
                return null;
            }
            return new PrimitiveSpliteratorWrapper(trySplit);
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
        
        @Override
        public boolean tryAdvance(final Object o) {
            return this.tryAdvance((IntConsumer)o);
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class SpliteratorWrapper implements IntSpliterator
    {
        final Spliterator i;
        
        public SpliteratorWrapper(final Spliterator i) {
            this.i = i;
        }
        
        @Override
        public boolean tryAdvance(final com.viaversion.viaversion.libs.fastutil.ints.IntConsumer intConsumer) {
            return this.i.tryAdvance(intConsumer);
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            final Spliterator i = this.i;
            Consumer<Integer> consumer;
            if (intConsumer instanceof Consumer) {
                consumer = (Consumer<Integer>)intConsumer;
            }
            else {
                Objects.requireNonNull(intConsumer);
                consumer = intConsumer::accept;
            }
            return i.tryAdvance(consumer);
        }
        
        @Deprecated
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            return this.i.tryAdvance(consumer);
        }
        
        @Override
        public void forEachRemaining(final com.viaversion.viaversion.libs.fastutil.ints.IntConsumer intConsumer) {
            this.i.forEachRemaining(intConsumer);
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            final Spliterator i = this.i;
            Consumer<Integer> consumer;
            if (intConsumer instanceof Consumer) {
                consumer = (Consumer<Integer>)intConsumer;
            }
            else {
                Objects.requireNonNull(intConsumer);
                consumer = intConsumer::accept;
            }
            i.forEachRemaining(consumer);
        }
        
        @Deprecated
        @Override
        public void forEachRemaining(final Consumer consumer) {
            this.i.forEachRemaining(consumer);
        }
        
        @Override
        public long estimateSize() {
            return this.i.estimateSize();
        }
        
        @Override
        public int characteristics() {
            return this.i.characteristics();
        }
        
        @Override
        public IntComparator getComparator() {
            return IntComparators.asIntComparator(this.i.getComparator());
        }
        
        @Override
        public IntSpliterator trySplit() {
            final Spliterator trySplit = this.i.trySplit();
            if (trySplit == null) {
                return null;
            }
            return new SpliteratorWrapper(trySplit);
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
        
        @Override
        public boolean tryAdvance(final Object o) {
            return this.tryAdvance((IntConsumer)o);
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class PrimitiveSpliteratorWrapperWithComparator extends PrimitiveSpliteratorWrapper
    {
        final IntComparator comparator;
        
        public PrimitiveSpliteratorWrapperWithComparator(final Spliterator.OfInt ofInt, final IntComparator comparator) {
            super(ofInt);
            this.comparator = comparator;
        }
        
        @Override
        public IntComparator getComparator() {
            return this.comparator;
        }
        
        @Override
        public IntSpliterator trySplit() {
            final Spliterator.OfInt trySplit = this.i.trySplit();
            if (trySplit == null) {
                return null;
            }
            return new PrimitiveSpliteratorWrapperWithComparator(trySplit, this.comparator);
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class SpliteratorWrapperWithComparator extends SpliteratorWrapper
    {
        final IntComparator comparator;
        
        public SpliteratorWrapperWithComparator(final Spliterator spliterator, final IntComparator comparator) {
            super(spliterator);
            this.comparator = comparator;
        }
        
        @Override
        public IntComparator getComparator() {
            return this.comparator;
        }
        
        @Override
        public IntSpliterator trySplit() {
            final Spliterator trySplit = this.i.trySplit();
            if (trySplit == null) {
                return null;
            }
            return new SpliteratorWrapperWithComparator(trySplit, this.comparator);
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class IntervalSpliterator implements IntSpliterator
    {
        private static final int DONT_SPLIT_THRESHOLD = 2;
        private static final int CHARACTERISTICS = 17749;
        private int curr;
        private int to;
        
        public IntervalSpliterator(final int curr, final int to) {
            this.curr = curr;
            this.to = to;
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            if (this.curr >= this.to) {
                return false;
            }
            intConsumer.accept(this.curr++);
            return true;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            while (this.curr < this.to) {
                intConsumer.accept(this.curr);
                ++this.curr;
            }
        }
        
        @Override
        public long estimateSize() {
            return this.to - (long)this.curr;
        }
        
        @Override
        public int characteristics() {
            return 17749;
        }
        
        @Override
        public IntComparator getComparator() {
            return null;
        }
        
        @Override
        public IntSpliterator trySplit() {
            final long n = this.to - this.curr;
            final int curr = (int)(this.curr + (n >> 1));
            if (n >= 0L && n <= 2L) {
                return null;
            }
            final int curr2 = this.curr;
            this.curr = curr;
            return new IntervalSpliterator(curr2, curr);
        }
        
        @Override
        public long skip(long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.curr >= this.to) {
                return 0L;
            }
            final long n2 = this.curr + n;
            if (n2 <= this.to && n2 >= this.curr) {
                this.curr = SafeMath.safeLongToInt(n2);
                return n;
            }
            n = this.to - this.curr;
            this.curr = this.to;
            return n;
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
        
        @Override
        public boolean tryAdvance(final Object o) {
            return this.tryAdvance((IntConsumer)o);
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class SpliteratorConcatenator implements IntSpliterator
    {
        private static final int EMPTY_CHARACTERISTICS = 16448;
        private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
        final IntSpliterator[] a;
        int offset;
        int length;
        long remainingEstimatedExceptCurrent;
        int characteristics;
        
        public SpliteratorConcatenator(final IntSpliterator[] a, final int offset, final int length) {
            this.remainingEstimatedExceptCurrent = Long.MAX_VALUE;
            this.characteristics = 0;
            this.a = a;
            this.offset = offset;
            this.length = length;
            this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
            this.characteristics = this.computeCharacteristics();
        }
        
        private long recomputeRemaining() {
            int i = this.length - 1;
            int n = this.offset + 1;
            long n2 = 0L;
            while (i > 0) {
                final long estimateSize = this.a[n++].estimateSize();
                --i;
                if (estimateSize == Long.MAX_VALUE) {
                    return Long.MAX_VALUE;
                }
                n2 += estimateSize;
                if (n2 == Long.MAX_VALUE || n2 < 0L) {
                    return Long.MAX_VALUE;
                }
            }
            return n2;
        }
        
        private int computeCharacteristics() {
            if (this.length <= 0) {
                return 16448;
            }
            int i = this.length;
            int offset = this.offset;
            if (i > 1) {}
            while (i > 0) {
                final int n = -1 & this.a[offset++].characteristics();
                --i;
            }
            return -1;
        }
        
        private void advanceNextSpliterator() {
            if (this.length <= 0) {
                throw new AssertionError((Object)"advanceNextSpliterator() called with none remaining");
            }
            ++this.offset;
            --this.length;
            this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            while (this.length > 0 && !this.a[this.offset].tryAdvance(intConsumer)) {
                this.advanceNextSpliterator();
            }
            return true;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            while (this.length > 0) {
                this.a[this.offset].forEachRemaining(intConsumer);
                this.advanceNextSpliterator();
            }
        }
        
        @Deprecated
        @Override
        public void forEachRemaining(final Consumer consumer) {
            while (this.length > 0) {
                this.a[this.offset].forEachRemaining(consumer);
                this.advanceNextSpliterator();
            }
        }
        
        @Override
        public long estimateSize() {
            if (this.length <= 0) {
                return 0L;
            }
            final long n = this.a[this.offset].estimateSize() + this.remainingEstimatedExceptCurrent;
            if (n < 0L) {
                return Long.MAX_VALUE;
            }
            return n;
        }
        
        @Override
        public int characteristics() {
            return this.characteristics;
        }
        
        @Override
        public IntComparator getComparator() {
            if (this.length == 1 && (this.characteristics & 0x4) != 0x0) {
                return this.a[this.offset].getComparator();
            }
            throw new IllegalStateException();
        }
        
        @Override
        public IntSpliterator trySplit() {
            switch (this.length) {
                case 0: {
                    return null;
                }
                case 1: {
                    final IntSpliterator trySplit = this.a[this.offset].trySplit();
                    this.characteristics = this.a[this.offset].characteristics();
                    return trySplit;
                }
                case 2: {
                    final IntSpliterator intSpliterator = this.a[this.offset++];
                    --this.length;
                    this.characteristics = this.a[this.offset].characteristics();
                    this.remainingEstimatedExceptCurrent = 0L;
                    return intSpliterator;
                }
                default: {
                    final int n = this.length >> 1;
                    final int offset = this.offset;
                    final int offset2 = this.offset + n;
                    final int n2 = n;
                    final int length = this.length - n;
                    this.offset = offset2;
                    this.length = length;
                    this.remainingEstimatedExceptCurrent = this.recomputeRemaining();
                    this.characteristics = this.computeCharacteristics();
                    return new SpliteratorConcatenator(this.a, offset, n2);
                }
            }
        }
        
        @Override
        public long skip(final long n) {
            long n2 = 0L;
            if (this.length <= 0) {
                return 0L;
            }
            while (n2 < n && this.length >= 0) {
                n2 += this.a[this.offset].skip(n - n2);
                if (n2 < n) {
                    this.advanceNextSpliterator();
                }
            }
            return n2;
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
        
        @Override
        public boolean tryAdvance(final Object o) {
            return this.tryAdvance((IntConsumer)o);
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class SpliteratorFromIterator implements IntSpliterator
    {
        private static final int BATCH_INCREMENT_SIZE = 1024;
        private static final int BATCH_MAX_SIZE = 33554432;
        private final IntIterator iter;
        final int characteristics;
        private final boolean knownSize;
        private long size;
        private int nextBatchSize;
        private IntSpliterator delegate;
        
        SpliteratorFromIterator(final IntIterator iter, final int n) {
            this.size = Long.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = iter;
            this.characteristics = (0x100 | n);
            this.knownSize = false;
        }
        
        SpliteratorFromIterator(final IntIterator iter, final long size, final int n) {
            this.size = Long.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = iter;
            this.knownSize = true;
            this.size = size;
            if ((n & 0x1000) != 0x0) {
                this.characteristics = (0x100 | n);
            }
            else {
                this.characteristics = (0x4140 | n);
            }
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            if (this.delegate != null) {
                final boolean tryAdvance = this.delegate.tryAdvance(intConsumer);
                if (!tryAdvance) {
                    this.delegate = null;
                }
                return tryAdvance;
            }
            if (!this.iter.hasNext()) {
                return false;
            }
            --this.size;
            intConsumer.accept(this.iter.nextInt());
            return true;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            if (this.delegate != null) {
                this.delegate.forEachRemaining(intConsumer);
                this.delegate = null;
            }
            this.iter.forEachRemaining(intConsumer);
            this.size = 0L;
        }
        
        @Override
        public long estimateSize() {
            if (this.delegate != null) {
                return this.delegate.estimateSize();
            }
            if (!this.iter.hasNext()) {
                return 0L;
            }
            return (this.knownSize && this.size >= 0L) ? this.size : Long.MAX_VALUE;
        }
        
        @Override
        public int characteristics() {
            return this.characteristics;
        }
        
        protected IntSpliterator makeForSplit(final int[] array, final int n) {
            return IntSpliterators.wrap(array, 0, n, this.characteristics);
        }
        
        @Override
        public IntSpliterator trySplit() {
            if (!this.iter.hasNext()) {
                return null;
            }
            final int n = (this.knownSize && this.size > 0L) ? ((int)Math.min(this.nextBatchSize, this.size)) : this.nextBatchSize;
            int[] copy = new int[n];
            int n3 = 0;
            while (0 < n && this.iter.hasNext()) {
                final int[] array = copy;
                final int n2 = 0;
                ++n3;
                array[n2] = this.iter.nextInt();
                --this.size;
            }
            if (n < this.nextBatchSize && this.iter.hasNext()) {
                copy = Arrays.copyOf(copy, this.nextBatchSize);
                while (this.iter.hasNext() && 0 < this.nextBatchSize) {
                    final int[] array2 = copy;
                    final int n4 = 0;
                    ++n3;
                    array2[n4] = this.iter.nextInt();
                    --this.size;
                }
            }
            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            final IntSpliterator forSplit = this.makeForSplit(copy, 0);
            if (!this.iter.hasNext()) {
                this.delegate = forSplit;
                return forSplit.trySplit();
            }
            return forSplit;
        }
        
        @Override
        public long skip(final long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.iter instanceof IntBigListIterator) {
                final long skip = ((IntBigListIterator)this.iter).skip(n);
                this.size -= skip;
                return skip;
            }
            long n2;
            int skip2;
            for (n2 = 0L; n2 < n && this.iter.hasNext(); n2 += skip2) {
                skip2 = this.iter.skip(SafeMath.safeLongToInt(Math.min(n, 2147483647L)));
                this.size -= skip2;
            }
            return n2;
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
        
        @Override
        public boolean tryAdvance(final Object o) {
            return this.tryAdvance((IntConsumer)o);
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class SpliteratorFromIteratorWithComparator extends SpliteratorFromIterator
    {
        private final IntComparator comparator;
        
        SpliteratorFromIteratorWithComparator(final IntIterator intIterator, final int n, final IntComparator comparator) {
            super(intIterator, n | 0x14);
            this.comparator = comparator;
        }
        
        SpliteratorFromIteratorWithComparator(final IntIterator intIterator, final long n, final int n2, final IntComparator comparator) {
            super(intIterator, n, n2 | 0x14);
            this.comparator = comparator;
        }
        
        @Override
        public IntComparator getComparator() {
            return this.comparator;
        }
        
        @Override
        protected IntSpliterator makeForSplit(final int[] array, final int n) {
            return IntSpliterators.wrapPreSorted(array, 0, n, this.characteristics, this.comparator);
        }
        
        @Override
        public Comparator getComparator() {
            return this.getComparator();
        }
    }
    
    private static final class IteratorFromSpliterator implements IntIterator, IntConsumer
    {
        private final IntSpliterator spliterator;
        private int holder;
        private boolean hasPeeked;
        
        IteratorFromSpliterator(final IntSpliterator spliterator) {
            this.holder = 0;
            this.hasPeeked = false;
            this.spliterator = spliterator;
        }
        
        @Override
        public void accept(final int holder) {
            this.holder = holder;
        }
        
        @Override
        public boolean hasNext() {
            return this.hasPeeked || (this.spliterator.tryAdvance(this) && (this.hasPeeked = true));
        }
        
        @Override
        public int nextInt() {
            if (this.hasPeeked) {
                this.hasPeeked = false;
                return this.holder;
            }
            if (!this.spliterator.tryAdvance(this)) {
                throw new NoSuchElementException();
            }
            return this.holder;
        }
        
        @Override
        public void forEachRemaining(final java.util.function.IntConsumer intConsumer) {
            if (this.hasPeeked) {
                this.hasPeeked = false;
                intConsumer.accept(this.holder);
            }
            this.spliterator.forEachRemaining(intConsumer);
        }
        
        @Override
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.hasPeeked) {
                this.hasPeeked = false;
                this.spliterator.skip(1L);
                int n2 = 0;
                ++n2;
                --n;
            }
            if (n > 0) {
                final int n2 = 0 + SafeMath.safeLongToInt(this.spliterator.skip(n));
            }
            return 0;
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((java.util.function.IntConsumer)o);
        }
    }
    
    public static class EmptySpliterator implements IntSpliterator, Serializable, Cloneable
    {
        private static final long serialVersionUID = 8379247926738230492L;
        private static final int CHARACTERISTICS = 16448;
        
        protected EmptySpliterator() {
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            return false;
        }
        
        @Deprecated
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            return false;
        }
        
        @Override
        public IntSpliterator trySplit() {
            return null;
        }
        
        @Override
        public long estimateSize() {
            return 0L;
        }
        
        @Override
        public int characteristics() {
            return 16448;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
        }
        
        @Deprecated
        @Override
        public void forEachRemaining(final Consumer consumer) {
        }
        
        public Object clone() {
            return IntSpliterators.EMPTY_SPLITERATOR;
        }
        
        private Object readResolve() {
            return IntSpliterators.EMPTY_SPLITERATOR;
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
        
        @Override
        public boolean tryAdvance(final Object o) {
            return this.tryAdvance((IntConsumer)o);
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    public abstract static class LateBindingSizeIndexBasedSpliterator extends AbstractIndexBasedSpliterator
    {
        protected int maxPos;
        private boolean maxPosFixed;
        
        protected LateBindingSizeIndexBasedSpliterator(final int n) {
            super(n);
            this.maxPos = -1;
            this.maxPosFixed = false;
        }
        
        protected LateBindingSizeIndexBasedSpliterator(final int n, final int maxPos) {
            super(n);
            this.maxPos = -1;
            this.maxPos = maxPos;
            this.maxPosFixed = true;
        }
        
        protected abstract int getMaxPosFromBackingStore();
        
        @Override
        protected final int getMaxPos() {
            return this.maxPosFixed ? this.maxPos : this.getMaxPosFromBackingStore();
        }
        
        @Override
        public IntSpliterator trySplit() {
            final IntSpliterator trySplit = super.trySplit();
            if (!this.maxPosFixed && trySplit != null) {
                this.maxPos = this.getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return trySplit;
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    public abstract static class AbstractIndexBasedSpliterator extends AbstractIntSpliterator
    {
        protected int pos;
        
        protected AbstractIndexBasedSpliterator(final int pos) {
            this.pos = pos;
        }
        
        protected abstract int get(final int p0);
        
        protected abstract int getMaxPos();
        
        protected abstract IntSpliterator makeForSplit(final int p0, final int p1);
        
        protected int computeSplitPoint() {
            return this.pos + (this.getMaxPos() - this.pos) / 2;
        }
        
        private void splitPointCheck(final int n, final int n2) {
            if (n < this.pos || n > n2) {
                throw new IndexOutOfBoundsException("splitPoint " + n + " outside of range of current position " + this.pos + " and range end " + n2);
            }
        }
        
        @Override
        public int characteristics() {
            return 16720;
        }
        
        @Override
        public long estimateSize() {
            return this.getMaxPos() - (long)this.pos;
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            if (this.pos >= this.getMaxPos()) {
                return false;
            }
            intConsumer.accept(this.get(this.pos++));
            return true;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            while (this.pos < this.getMaxPos()) {
                intConsumer.accept(this.get(this.pos));
                ++this.pos;
            }
        }
        
        @Override
        public long skip(long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            final int maxPos = this.getMaxPos();
            if (this.pos >= maxPos) {
                return 0L;
            }
            final int n2 = maxPos - this.pos;
            if (n < n2) {
                this.pos = SafeMath.safeLongToInt(this.pos + n);
                return n;
            }
            n = n2;
            this.pos = maxPos;
            return n;
        }
        
        @Override
        public IntSpliterator trySplit() {
            final int maxPos = this.getMaxPos();
            final int computeSplitPoint = this.computeSplitPoint();
            if (computeSplitPoint == this.pos || computeSplitPoint == maxPos) {
                return null;
            }
            this.splitPointCheck(computeSplitPoint, maxPos);
            final IntSpliterator forSplit = this.makeForSplit(this.pos, computeSplitPoint);
            if (forSplit != null) {
                this.pos = computeSplitPoint;
            }
            return forSplit;
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return this.trySplit();
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
        
        @Override
        public boolean tryAdvance(final Object o) {
            return this.tryAdvance((IntConsumer)o);
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    public abstract static class EarlyBindingSizeIndexBasedSpliterator extends AbstractIndexBasedSpliterator
    {
        protected final int maxPos;
        
        protected EarlyBindingSizeIndexBasedSpliterator(final int n, final int maxPos) {
            super(n);
            this.maxPos = maxPos;
        }
        
        @Override
        protected final int getMaxPos() {
            return this.maxPos;
        }
    }
}
