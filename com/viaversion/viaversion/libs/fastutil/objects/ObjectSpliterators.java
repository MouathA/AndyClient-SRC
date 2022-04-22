package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.function.*;
import java.io.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public final class ObjectSpliterators
{
    static final int BASE_SPLITERATOR_CHARACTERISTICS = 0;
    public static final int COLLECTION_SPLITERATOR_CHARACTERISTICS = 64;
    public static final int LIST_SPLITERATOR_CHARACTERISTICS = 16464;
    public static final int SET_SPLITERATOR_CHARACTERISTICS = 65;
    private static final int SORTED_CHARACTERISTICS = 20;
    public static final int SORTED_SET_SPLITERATOR_CHARACTERISTICS = 85;
    public static final EmptySpliterator EMPTY_SPLITERATOR;
    
    private ObjectSpliterators() {
    }
    
    public static ObjectSpliterator emptySpliterator() {
        return ObjectSpliterators.EMPTY_SPLITERATOR;
    }
    
    public static ObjectSpliterator singleton(final Object o) {
        return new SingletonSpliterator(o);
    }
    
    public static ObjectSpliterator singleton(final Object o, final Comparator comparator) {
        return new SingletonSpliterator(o, comparator);
    }
    
    public static ObjectSpliterator wrap(final Object[] array, final int n, final int n2) {
        ObjectArrays.ensureOffsetLength(array, n, n2);
        return new ArraySpliterator(array, n, n2, 0);
    }
    
    public static ObjectSpliterator wrap(final Object[] array) {
        return new ArraySpliterator(array, 0, array.length, 0);
    }
    
    public static ObjectSpliterator wrap(final Object[] array, final int n, final int n2, final int n3) {
        ObjectArrays.ensureOffsetLength(array, n, n2);
        return new ArraySpliterator(array, n, n2, n3);
    }
    
    public static ObjectSpliterator wrapPreSorted(final Object[] array, final int n, final int n2, final int n3, final Comparator comparator) {
        ObjectArrays.ensureOffsetLength(array, n, n2);
        return new ArraySpliteratorWithComparator(array, n, n2, n3, comparator);
    }
    
    public static ObjectSpliterator wrapPreSorted(final Object[] array, final int n, final int n2, final Comparator comparator) {
        return wrapPreSorted(array, n, n2, 0, comparator);
    }
    
    public static ObjectSpliterator wrapPreSorted(final Object[] array, final Comparator comparator) {
        return wrapPreSorted(array, 0, array.length, comparator);
    }
    
    public static ObjectSpliterator asObjectSpliterator(final Spliterator spliterator) {
        if (spliterator instanceof ObjectSpliterator) {
            return (ObjectSpliterator)spliterator;
        }
        return new SpliteratorWrapper(spliterator);
    }
    
    public static ObjectSpliterator asObjectSpliterator(final Spliterator spliterator, final Comparator comparator) {
        if (spliterator instanceof ObjectSpliterator) {
            throw new IllegalArgumentException("Cannot override comparator on instance that is already a " + ObjectSpliterator.class.getSimpleName());
        }
        return new SpliteratorWrapperWithComparator(spliterator, comparator);
    }
    
    public static void onEachMatching(final Spliterator spliterator, final Predicate predicate, final Consumer consumer) {
        Objects.requireNonNull(predicate);
        Objects.requireNonNull(consumer);
        spliterator.forEachRemaining(ObjectSpliterators::lambda$onEachMatching$0);
    }
    
    @SafeVarargs
    public static ObjectSpliterator concat(final ObjectSpliterator... array) {
        return concat(array, 0, array.length);
    }
    
    public static ObjectSpliterator concat(final ObjectSpliterator[] array, final int n, final int n2) {
        return new SpliteratorConcatenator(array, n, n2);
    }
    
    public static ObjectSpliterator asSpliterator(final ObjectIterator objectIterator, final long n, final int n2) {
        return new SpliteratorFromIterator(objectIterator, n, n2);
    }
    
    public static ObjectSpliterator asSpliteratorFromSorted(final ObjectIterator objectIterator, final long n, final int n2, final Comparator comparator) {
        return new SpliteratorFromIteratorWithComparator(objectIterator, n, n2, comparator);
    }
    
    public static ObjectSpliterator asSpliteratorUnknownSize(final ObjectIterator objectIterator, final int n) {
        return new SpliteratorFromIterator(objectIterator, n);
    }
    
    public static ObjectSpliterator asSpliteratorFromSortedUnknownSize(final ObjectIterator objectIterator, final int n, final Comparator comparator) {
        return new SpliteratorFromIteratorWithComparator(objectIterator, n, comparator);
    }
    
    public static ObjectIterator asIterator(final ObjectSpliterator objectSpliterator) {
        return new IteratorFromSpliterator(objectSpliterator);
    }
    
    private static void lambda$onEachMatching$0(final Predicate predicate, final Consumer consumer, final Object o) {
        if (predicate.test(o)) {
            consumer.accept(o);
        }
    }
    
    static {
        EMPTY_SPLITERATOR = new EmptySpliterator();
    }
    
    public static class EmptySpliterator implements ObjectSpliterator, Serializable, Cloneable
    {
        private static final long serialVersionUID = 8379247926738230492L;
        private static final int CHARACTERISTICS = 16448;
        
        protected EmptySpliterator() {
        }
        
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            return false;
        }
        
        @Override
        public ObjectSpliterator trySplit() {
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
        public void forEachRemaining(final Consumer consumer) {
        }
        
        public Object clone() {
            return ObjectSpliterators.EMPTY_SPLITERATOR;
        }
        
        private Object readResolve() {
            return ObjectSpliterators.EMPTY_SPLITERATOR;
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class SingletonSpliterator implements ObjectSpliterator
    {
        private final Object element;
        private final Comparator comparator;
        private boolean consumed;
        private static final int CHARACTERISTICS = 17493;
        
        public SingletonSpliterator(final Object o) {
            this(o, null);
        }
        
        public SingletonSpliterator(final Object element, final Comparator comparator) {
            this.consumed = false;
            this.element = element;
            this.comparator = comparator;
        }
        
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            Objects.requireNonNull(consumer);
            if (this.consumed) {
                return false;
            }
            this.consumed = true;
            consumer.accept(this.element);
            return true;
        }
        
        @Override
        public ObjectSpliterator trySplit() {
            return null;
        }
        
        @Override
        public long estimateSize() {
            return this.consumed ? 0 : 1;
        }
        
        @Override
        public int characteristics() {
            return 0x4455 | ((this.element != null) ? 256 : 0);
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            Objects.requireNonNull(consumer);
            if (!this.consumed) {
                this.consumed = true;
                consumer.accept(this.element);
            }
        }
        
        @Override
        public Comparator getComparator() {
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
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class ArraySpliterator implements ObjectSpliterator
    {
        private static final int BASE_CHARACTERISTICS = 16464;
        final Object[] array;
        private final int offset;
        private int length;
        private int curr;
        final int characteristics;
        
        public ArraySpliterator(final Object[] array, final int offset, final int length, final int n) {
            this.array = array;
            this.offset = offset;
            this.length = length;
            this.characteristics = (0x4050 | n);
        }
        
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            if (this.curr >= this.length) {
                return false;
            }
            Objects.requireNonNull(consumer);
            consumer.accept(this.array[this.offset + this.curr++]);
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
        public ObjectSpliterator trySplit() {
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
        public void forEachRemaining(final Consumer consumer) {
            Objects.requireNonNull(consumer);
            while (this.curr < this.length) {
                consumer.accept(this.array[this.offset + this.curr]);
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
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class ArraySpliteratorWithComparator extends ArraySpliterator
    {
        private final Comparator comparator;
        
        public ArraySpliteratorWithComparator(final Object[] array, final int n, final int n2, final int n3, final Comparator comparator) {
            super(array, n, n2, n3 | 0x14);
            this.comparator = comparator;
        }
        
        @Override
        protected ArraySpliteratorWithComparator makeForSplit(final int n, final int n2) {
            return new ArraySpliteratorWithComparator(this.array, n, n2, this.characteristics, this.comparator);
        }
        
        @Override
        public Comparator getComparator() {
            return this.comparator;
        }
        
        @Override
        protected ArraySpliterator makeForSplit(final int n, final int n2) {
            return this.makeForSplit(n, n2);
        }
    }
    
    private static class SpliteratorWrapper implements ObjectSpliterator
    {
        final Spliterator i;
        
        public SpliteratorWrapper(final Spliterator i) {
            this.i = i;
        }
        
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            return this.i.tryAdvance(consumer);
        }
        
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
        public Comparator getComparator() {
            return ObjectComparators.asObjectComparator(this.i.getComparator());
        }
        
        @Override
        public ObjectSpliterator trySplit() {
            final Spliterator trySplit = this.i.trySplit();
            if (trySplit == null) {
                return null;
            }
            return new SpliteratorWrapper(trySplit);
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class SpliteratorWrapperWithComparator extends SpliteratorWrapper
    {
        final Comparator comparator;
        
        public SpliteratorWrapperWithComparator(final Spliterator spliterator, final Comparator comparator) {
            super(spliterator);
            this.comparator = comparator;
        }
        
        @Override
        public Comparator getComparator() {
            return this.comparator;
        }
        
        @Override
        public ObjectSpliterator trySplit() {
            final Spliterator trySplit = this.i.trySplit();
            if (trySplit == null) {
                return null;
            }
            return new SpliteratorWrapperWithComparator(trySplit, this.comparator);
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class SpliteratorConcatenator implements ObjectSpliterator
    {
        private static final int EMPTY_CHARACTERISTICS = 16448;
        private static final int CHARACTERISTICS_NOT_SUPPORTED_WHILE_MULTIPLE = 5;
        final ObjectSpliterator[] a;
        int offset;
        int length;
        long remainingEstimatedExceptCurrent;
        int characteristics;
        
        public SpliteratorConcatenator(final ObjectSpliterator[] a, final int offset, final int length) {
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
        public boolean tryAdvance(final Consumer consumer) {
            while (this.length > 0 && !this.a[this.offset].tryAdvance(consumer)) {
                this.advanceNextSpliterator();
            }
            return true;
        }
        
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
        public Comparator getComparator() {
            if (this.length == 1 && (this.characteristics & 0x4) != 0x0) {
                return this.a[this.offset].getComparator();
            }
            throw new IllegalStateException();
        }
        
        @Override
        public ObjectSpliterator trySplit() {
            switch (this.length) {
                case 0: {
                    return null;
                }
                case 1: {
                    final ObjectSpliterator trySplit = this.a[this.offset].trySplit();
                    this.characteristics = this.a[this.offset].characteristics();
                    return trySplit;
                }
                case 2: {
                    final ObjectSpliterator objectSpliterator = this.a[this.offset++];
                    --this.length;
                    this.characteristics = this.a[this.offset].characteristics();
                    this.remainingEstimatedExceptCurrent = 0L;
                    return objectSpliterator;
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
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class SpliteratorFromIterator implements ObjectSpliterator
    {
        private static final int BATCH_INCREMENT_SIZE = 1024;
        private static final int BATCH_MAX_SIZE = 33554432;
        private final ObjectIterator iter;
        final int characteristics;
        private final boolean knownSize;
        private long size;
        private int nextBatchSize;
        private ObjectSpliterator delegate;
        
        SpliteratorFromIterator(final ObjectIterator iter, final int n) {
            this.size = Long.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = iter;
            this.characteristics = (0x0 | n);
            this.knownSize = false;
        }
        
        SpliteratorFromIterator(final ObjectIterator iter, final long size, final int n) {
            this.size = Long.MAX_VALUE;
            this.nextBatchSize = 1024;
            this.delegate = null;
            this.iter = iter;
            this.knownSize = true;
            this.size = size;
            if ((n & 0x1000) != 0x0) {
                this.characteristics = (0x0 | n);
            }
            else {
                this.characteristics = (0x4040 | n);
            }
        }
        
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            if (this.delegate != null) {
                final boolean tryAdvance = this.delegate.tryAdvance(consumer);
                if (!tryAdvance) {
                    this.delegate = null;
                }
                return tryAdvance;
            }
            if (!this.iter.hasNext()) {
                return false;
            }
            --this.size;
            consumer.accept(this.iter.next());
            return true;
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            if (this.delegate != null) {
                this.delegate.forEachRemaining(consumer);
                this.delegate = null;
            }
            this.iter.forEachRemaining(consumer);
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
        
        protected ObjectSpliterator makeForSplit(final Object[] array, final int n) {
            return ObjectSpliterators.wrap(array, 0, n, this.characteristics);
        }
        
        @Override
        public ObjectSpliterator trySplit() {
            if (!this.iter.hasNext()) {
                return null;
            }
            final int n = (this.knownSize && this.size > 0L) ? ((int)Math.min(this.nextBatchSize, this.size)) : this.nextBatchSize;
            Object[] copy = new Object[n];
            int n3 = 0;
            while (0 < n && this.iter.hasNext()) {
                final Object[] array = copy;
                final int n2 = 0;
                ++n3;
                array[n2] = this.iter.next();
                --this.size;
            }
            if (n < this.nextBatchSize && this.iter.hasNext()) {
                copy = Arrays.copyOf(copy, this.nextBatchSize);
                while (this.iter.hasNext() && 0 < this.nextBatchSize) {
                    final Object[] array2 = copy;
                    final int n4 = 0;
                    ++n3;
                    array2[n4] = this.iter.next();
                    --this.size;
                }
            }
            this.nextBatchSize = Math.min(33554432, this.nextBatchSize + 1024);
            final ObjectSpliterator forSplit = this.makeForSplit(copy, 0);
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
            if (this.iter instanceof ObjectBigListIterator) {
                final long skip = ((ObjectBigListIterator)this.iter).skip(n);
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
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    private static class SpliteratorFromIteratorWithComparator extends SpliteratorFromIterator
    {
        private final Comparator comparator;
        
        SpliteratorFromIteratorWithComparator(final ObjectIterator objectIterator, final int n, final Comparator comparator) {
            super(objectIterator, n | 0x14);
            this.comparator = comparator;
        }
        
        SpliteratorFromIteratorWithComparator(final ObjectIterator objectIterator, final long n, final int n2, final Comparator comparator) {
            super(objectIterator, n, n2 | 0x14);
            this.comparator = comparator;
        }
        
        @Override
        public Comparator getComparator() {
            return this.comparator;
        }
        
        @Override
        protected ObjectSpliterator makeForSplit(final Object[] array, final int n) {
            return ObjectSpliterators.wrapPreSorted(array, 0, n, this.characteristics, this.comparator);
        }
    }
    
    private static final class IteratorFromSpliterator implements ObjectIterator, Consumer
    {
        private final ObjectSpliterator spliterator;
        private Object holder;
        private boolean hasPeeked;
        
        IteratorFromSpliterator(final ObjectSpliterator spliterator) {
            this.holder = null;
            this.hasPeeked = false;
            this.spliterator = spliterator;
        }
        
        @Override
        public void accept(final Object holder) {
            this.holder = holder;
        }
        
        @Override
        public boolean hasNext() {
            return this.hasPeeked || (this.spliterator.tryAdvance(this) && (this.hasPeeked = true));
        }
        
        @Override
        public Object next() {
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
        public void forEachRemaining(final Consumer consumer) {
            if (this.hasPeeked) {
                this.hasPeeked = false;
                consumer.accept(this.holder);
            }
            this.spliterator.forEachRemaining(consumer);
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
        public ObjectSpliterator trySplit() {
            final ObjectSpliterator trySplit = super.trySplit();
            if (!this.maxPosFixed && trySplit != null) {
                this.maxPos = this.getMaxPosFromBackingStore();
                this.maxPosFixed = true;
            }
            return trySplit;
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
    
    public abstract static class AbstractIndexBasedSpliterator extends AbstractObjectSpliterator
    {
        protected int pos;
        
        protected AbstractIndexBasedSpliterator(final int pos) {
            this.pos = pos;
        }
        
        protected abstract Object get(final int p0);
        
        protected abstract int getMaxPos();
        
        protected abstract ObjectSpliterator makeForSplit(final int p0, final int p1);
        
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
            return 16464;
        }
        
        @Override
        public long estimateSize() {
            return this.getMaxPos() - (long)this.pos;
        }
        
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            if (this.pos >= this.getMaxPos()) {
                return false;
            }
            consumer.accept(this.get(this.pos++));
            return true;
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            while (this.pos < this.getMaxPos()) {
                consumer.accept(this.get(this.pos));
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
        public ObjectSpliterator trySplit() {
            final int maxPos = this.getMaxPos();
            final int computeSplitPoint = this.computeSplitPoint();
            if (computeSplitPoint == this.pos || computeSplitPoint == maxPos) {
                return null;
            }
            this.splitPointCheck(computeSplitPoint, maxPos);
            final ObjectSpliterator forSplit = this.makeForSplit(this.pos, computeSplitPoint);
            if (forSplit != null) {
                this.pos = computeSplitPoint;
            }
            return forSplit;
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
