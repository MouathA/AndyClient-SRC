package com.google.common.util.concurrent;

import java.util.*;
import java.math.*;
import com.google.common.math.*;
import java.util.concurrent.locks.*;
import com.google.common.annotations.*;
import java.util.concurrent.*;
import com.google.common.collect.*;
import com.google.common.base.*;
import java.util.concurrent.atomic.*;
import java.lang.ref.*;

@Beta
public abstract class Striped
{
    private static final int LARGE_LAZY_CUTOFF = 1024;
    private static final Supplier READ_WRITE_LOCK_SUPPLIER;
    private static final int ALL_SET = -1;
    
    private Striped() {
    }
    
    public abstract Object get(final Object p0);
    
    public abstract Object getAt(final int p0);
    
    abstract int indexFor(final Object p0);
    
    public abstract int size();
    
    public Iterable bulkGet(final Iterable iterable) {
        final Object[] array = Iterables.toArray(iterable, Object.class);
        if (array.length == 0) {
            return ImmutableList.of();
        }
        final int[] array2 = new int[array.length];
        while (0 < array.length) {
            array2[0] = this.indexFor(array[0]);
            int n = 0;
            ++n;
        }
        Arrays.sort(array2);
        int n = array2[0];
        array[0] = this.getAt(0);
        while (1 < array.length) {
            final int n2 = array2[1];
            if (n2 == 0) {
                array[1] = array[0];
            }
            else {
                array[1] = this.getAt(n2);
                n = n2;
            }
            int n3 = 0;
            ++n3;
        }
        return Collections.unmodifiableList((List<?>)Arrays.asList(array));
    }
    
    public static Striped lock(final int n) {
        return new CompactStriped(n, new Supplier() {
            @Override
            public Lock get() {
                return new PaddedLock();
            }
            
            @Override
            public Object get() {
                return this.get();
            }
        }, null);
    }
    
    public static Striped lazyWeakLock(final int n) {
        return lazy(n, new Supplier() {
            @Override
            public Lock get() {
                return new ReentrantLock(false);
            }
            
            @Override
            public Object get() {
                return this.get();
            }
        });
    }
    
    private static Striped lazy(final int n, final Supplier supplier) {
        return (n < 1024) ? new SmallLazyStriped(n, supplier) : new LargeLazyStriped(n, supplier);
    }
    
    public static Striped semaphore(final int n, final int n2) {
        return new CompactStriped(n, new Supplier(n2) {
            final int val$permits;
            
            @Override
            public Semaphore get() {
                return new PaddedSemaphore(this.val$permits);
            }
            
            @Override
            public Object get() {
                return this.get();
            }
        }, null);
    }
    
    public static Striped lazyWeakSemaphore(final int n, final int n2) {
        return lazy(n, new Supplier(n2) {
            final int val$permits;
            
            @Override
            public Semaphore get() {
                return new Semaphore(this.val$permits, false);
            }
            
            @Override
            public Object get() {
                return this.get();
            }
        });
    }
    
    public static Striped readWriteLock(final int n) {
        return new CompactStriped(n, Striped.READ_WRITE_LOCK_SUPPLIER, null);
    }
    
    public static Striped lazyWeakReadWriteLock(final int n) {
        return lazy(n, Striped.READ_WRITE_LOCK_SUPPLIER);
    }
    
    private static int ceilToPowerOfTwo(final int n) {
        return 1 << IntMath.log2(n, RoundingMode.CEILING);
    }
    
    private static int smear(int n) {
        n ^= (n >>> 20 ^ n >>> 12);
        return n ^ n >>> 7 ^ n >>> 4;
    }
    
    Striped(final Striped$1 supplier) {
        this();
    }
    
    static int access$200(final int n) {
        return ceilToPowerOfTwo(n);
    }
    
    static int access$300(final int n) {
        return smear(n);
    }
    
    static {
        READ_WRITE_LOCK_SUPPLIER = new Supplier() {
            @Override
            public ReadWriteLock get() {
                return new ReentrantReadWriteLock();
            }
            
            @Override
            public Object get() {
                return this.get();
            }
        };
    }
    
    private static class PaddedSemaphore extends Semaphore
    {
        long q1;
        long q2;
        long q3;
        
        PaddedSemaphore(final int n) {
            super(n, false);
        }
    }
    
    private static class PaddedLock extends ReentrantLock
    {
        long q1;
        long q2;
        long q3;
        
        PaddedLock() {
            super(false);
        }
    }
    
    @VisibleForTesting
    static class LargeLazyStriped extends PowerOfTwoStriped
    {
        final ConcurrentMap locks;
        final Supplier supplier;
        final int size;
        
        LargeLazyStriped(final int n, final Supplier supplier) {
            super(n);
            this.size = ((this.mask == -1) ? Integer.MAX_VALUE : (this.mask + 1));
            this.supplier = supplier;
            this.locks = new MapMaker().weakValues().makeMap();
        }
        
        @Override
        public Object getAt(final int n) {
            if (this.size != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(n, this.size());
            }
            final Object value = this.locks.get(n);
            if (value != null) {
                return value;
            }
            final Object value2 = this.supplier.get();
            return Objects.firstNonNull(this.locks.putIfAbsent(n, value2), value2);
        }
        
        @Override
        public int size() {
            return this.size;
        }
    }
    
    private abstract static class PowerOfTwoStriped extends Striped
    {
        final int mask;
        
        PowerOfTwoStriped(final int n) {
            super(null);
            Preconditions.checkArgument(n > 0, (Object)"Stripes must be positive");
            this.mask = ((n > 1073741824) ? -1 : (Striped.access$200(n) - 1));
        }
        
        @Override
        final int indexFor(final Object o) {
            return Striped.access$300(o.hashCode()) & this.mask;
        }
        
        @Override
        public final Object get(final Object o) {
            return this.getAt(this.indexFor(o));
        }
    }
    
    @VisibleForTesting
    static class SmallLazyStriped extends PowerOfTwoStriped
    {
        final AtomicReferenceArray locks;
        final Supplier supplier;
        final int size;
        final ReferenceQueue queue;
        
        SmallLazyStriped(final int n, final Supplier supplier) {
            super(n);
            this.queue = new ReferenceQueue();
            this.size = ((this.mask == -1) ? Integer.MAX_VALUE : (this.mask + 1));
            this.locks = new AtomicReferenceArray(this.size);
            this.supplier = supplier;
        }
        
        @Override
        public Object getAt(final int n) {
            if (this.size != Integer.MAX_VALUE) {
                Preconditions.checkElementIndex(n, this.size());
            }
            ArrayReference arrayReference = this.locks.get(n);
            final Object o = (arrayReference == null) ? null : arrayReference.get();
            if (o != null) {
                return o;
            }
            final Object value = this.supplier.get();
            while (!this.locks.compareAndSet(n, arrayReference, new ArrayReference(value, n, this.queue))) {
                arrayReference = this.locks.get(n);
                final Object o2 = (arrayReference == null) ? null : arrayReference.get();
                if (o2 != null) {
                    return o2;
                }
            }
            this.drainQueue();
            return value;
        }
        
        private void drainQueue() {
            Reference poll;
            while ((poll = this.queue.poll()) != null) {
                final ArrayReference arrayReference = (ArrayReference)poll;
                this.locks.compareAndSet(arrayReference.index, arrayReference, null);
            }
        }
        
        @Override
        public int size() {
            return this.size;
        }
        
        private static final class ArrayReference extends WeakReference
        {
            final int index;
            
            ArrayReference(final Object o, final int index, final ReferenceQueue referenceQueue) {
                super(o, referenceQueue);
                this.index = index;
            }
        }
    }
    
    private static class CompactStriped extends PowerOfTwoStriped
    {
        private final Object[] array;
        
        private CompactStriped(final int n, final Supplier supplier) {
            super(n);
            Preconditions.checkArgument(n <= 1073741824, (Object)"Stripes must be <= 2^30)");
            this.array = new Object[this.mask + 1];
            while (0 < this.array.length) {
                this.array[0] = supplier.get();
                int n2 = 0;
                ++n2;
            }
        }
        
        @Override
        public Object getAt(final int n) {
            return this.array[n];
        }
        
        @Override
        public int size() {
            return this.array.length;
        }
        
        CompactStriped(final int n, final Supplier supplier, final Striped$1 supplier2) {
            this(n, supplier);
        }
    }
}
