package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;
import java.util.*;
import java.util.function.*;

public final class ObjectIterators
{
    public static final EmptyIterator EMPTY_ITERATOR;
    
    private ObjectIterators() {
    }
    
    public static ObjectIterator emptyIterator() {
        return ObjectIterators.EMPTY_ITERATOR;
    }
    
    public static ObjectListIterator singleton(final Object o) {
        return new SingletonIterator(o);
    }
    
    public static ObjectListIterator wrap(final Object[] array, final int n, final int n2) {
        ObjectArrays.ensureOffsetLength(array, n, n2);
        return new ArrayIterator(array, n, n2);
    }
    
    public static ObjectListIterator wrap(final Object[] array) {
        return new ArrayIterator(array, 0, array.length);
    }
    
    public static int unwrap(final Iterator iterator, final Object[] array, int n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && iterator.hasNext()) {
            array[n++] = iterator.next();
        }
        return n2 - n3 - 1;
    }
    
    public static int unwrap(final Iterator iterator, final Object[] array) {
        return unwrap(iterator, array, 0, array.length);
    }
    
    public static Object[] unwrap(final Iterator iterator, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        Object[] grow = new Object[16];
        while (n-- != 0 && iterator.hasNext()) {
            if (0 == grow.length) {
                grow = ObjectArrays.grow(grow, 1);
            }
            final Object[] array = grow;
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            array[n2] = iterator.next();
        }
        return ObjectArrays.trim(grow, 0);
    }
    
    public static Object[] unwrap(final Iterator iterator) {
        return unwrap(iterator, Integer.MAX_VALUE);
    }
    
    public static long unwrap(final Iterator iterator, final Object[][] array, long n, final long n2) {
        if (n2 < 0L) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0L || n + n2 > BigArrays.length(array)) {
            throw new IllegalArgumentException();
        }
        long n3 = n2;
        while (n3-- != 0L && iterator.hasNext()) {
            BigArrays.set(array, n++, iterator.next());
        }
        return n2 - n3 - 1L;
    }
    
    public static long unwrap(final Iterator iterator, final Object[][] array) {
        return unwrap(iterator, array, 0L, BigArrays.length(array));
    }
    
    public static int unwrap(final Iterator iterator, final ObjectCollection collection, final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && iterator.hasNext()) {
            collection.add(iterator.next());
        }
        return n - n2 - 1;
    }
    
    public static Object[][] unwrapBig(final Iterator iterator, long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        Object[][] array = ObjectBigArrays.newBigArray(16L);
        long n2 = 0L;
        while (n-- != 0L && iterator.hasNext()) {
            if (n2 == BigArrays.length(array)) {
                array = BigArrays.grow(array, n2 + 1L);
            }
            BigArrays.set(array, n2++, iterator.next());
        }
        return BigArrays.trim(array, n2);
    }
    
    public static Object[][] unwrapBig(final Iterator iterator) {
        return unwrapBig(iterator, Long.MAX_VALUE);
    }
    
    public static long unwrap(final Iterator iterator, final ObjectCollection collection) {
        long n = 0L;
        while (iterator.hasNext()) {
            collection.add(iterator.next());
            ++n;
        }
        return n;
    }
    
    public static int pour(final Iterator iterator, final ObjectCollection collection, final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && iterator.hasNext()) {
            collection.add(iterator.next());
        }
        return n - n2 - 1;
    }
    
    public static int pour(final Iterator iterator, final ObjectCollection collection) {
        return pour(iterator, collection, Integer.MAX_VALUE);
    }
    
    public static ObjectList pour(final Iterator iterator, final int n) {
        final ObjectArrayList list = new ObjectArrayList();
        pour(iterator, list, n);
        list.trim();
        return list;
    }
    
    public static ObjectList pour(final Iterator iterator) {
        return pour(iterator, Integer.MAX_VALUE);
    }
    
    public static ObjectIterator asObjectIterator(final Iterator iterator) {
        if (iterator instanceof ObjectIterator) {
            return (ObjectIterator)iterator;
        }
        return new IteratorWrapper(iterator);
    }
    
    public static ObjectListIterator asObjectIterator(final ListIterator listIterator) {
        if (listIterator instanceof ObjectListIterator) {
            return (ObjectListIterator)listIterator;
        }
        return new ListIteratorWrapper(listIterator);
    }
    
    public static boolean any(final Iterator iterator, final Predicate predicate) {
        return indexOf(iterator, predicate) != -1;
    }
    
    public static boolean all(final Iterator iterator, final Predicate predicate) {
        Objects.requireNonNull(predicate);
        while (iterator.hasNext()) {
            if (!predicate.test(iterator.next())) {
                return false;
            }
        }
        return true;
    }
    
    public static int indexOf(final Iterator iterator, final Predicate predicate) {
        Objects.requireNonNull(predicate);
        while (iterator.hasNext()) {
            if (predicate.test(iterator.next())) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    @SafeVarargs
    public static ObjectIterator concat(final ObjectIterator... array) {
        return concat(array, 0, array.length);
    }
    
    public static ObjectIterator concat(final ObjectIterator[] array, final int n, final int n2) {
        return new IteratorConcatenator(array, n, n2);
    }
    
    public static ObjectIterator unmodifiable(final ObjectIterator objectIterator) {
        return (ObjectIterator)new ObjectIterators.UnmodifiableIterator(objectIterator);
    }
    
    public static ObjectBidirectionalIterator unmodifiable(final ObjectBidirectionalIterator objectBidirectionalIterator) {
        return (ObjectBidirectionalIterator)new ObjectIterators.UnmodifiableBidirectionalIterator(objectBidirectionalIterator);
    }
    
    public static ObjectListIterator unmodifiable(final ObjectListIterator objectListIterator) {
        return (ObjectListIterator)new ObjectIterators.UnmodifiableListIterator(objectListIterator);
    }
    
    static {
        EMPTY_ITERATOR = new EmptyIterator();
    }
    
    public static class EmptyIterator implements ObjectListIterator, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptyIterator() {
        }
        
        @Override
        public boolean hasNext() {
            return false;
        }
        
        @Override
        public boolean hasPrevious() {
            return false;
        }
        
        @Override
        public Object next() {
            throw new NoSuchElementException();
        }
        
        @Override
        public Object previous() {
            throw new NoSuchElementException();
        }
        
        @Override
        public int nextIndex() {
            return 0;
        }
        
        @Override
        public int previousIndex() {
            return -1;
        }
        
        @Override
        public int skip(final int n) {
            return 0;
        }
        
        @Override
        public int back(final int n) {
            return 0;
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
        }
        
        public Object clone() {
            return ObjectIterators.EMPTY_ITERATOR;
        }
        
        private Object readResolve() {
            return ObjectIterators.EMPTY_ITERATOR;
        }
    }
    
    private static class SingletonIterator implements ObjectListIterator
    {
        private final Object element;
        private byte curr;
        
        public SingletonIterator(final Object element) {
            this.element = element;
        }
        
        @Override
        public boolean hasNext() {
            return this.curr == 0;
        }
        
        @Override
        public boolean hasPrevious() {
            return this.curr == 1;
        }
        
        @Override
        public Object next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }
        
        @Override
        public Object previous() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = 0;
            return this.element;
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            Objects.requireNonNull(consumer);
            if (this.curr == 0) {
                consumer.accept(this.element);
                this.curr = 1;
            }
        }
        
        @Override
        public int nextIndex() {
            return this.curr;
        }
        
        @Override
        public int previousIndex() {
            return this.curr - 1;
        }
        
        @Override
        public int back(final int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n == 0 || this.curr < 1) {
                return 0;
            }
            this.curr = 1;
            return 1;
        }
        
        @Override
        public int skip(final int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n == 0 || this.curr > 0) {
                return 0;
            }
            this.curr = 0;
            return 1;
        }
    }
    
    private static class ArrayIterator implements ObjectListIterator
    {
        private final Object[] array;
        private final int offset;
        private final int length;
        private int curr;
        
        public ArrayIterator(final Object[] array, final int offset, final int length) {
            this.array = array;
            this.offset = offset;
            this.length = length;
        }
        
        @Override
        public Object next() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: if_icmpge       12
            //     4: new             Ljava/util/NoSuchElementException;
            //     7: dup            
            //     8: invokespecial   java/util/NoSuchElementException.<init>:()V
            //    11: athrow         
            //    12: aload_0        
            //    13: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$ArrayIterator.array:[Ljava/lang/Object;
            //    16: aload_0        
            //    17: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$ArrayIterator.offset:I
            //    20: aload_0        
            //    21: dup            
            //    22: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$ArrayIterator.curr:I
            //    25: dup_x1         
            //    26: iconst_1       
            //    27: iadd           
            //    28: putfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectIterators$ArrayIterator.curr:I
            //    31: iadd           
            //    32: aaload         
            //    33: areturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        @Override
        public Object previous() {
            if (this > 0) {
                throw new NoSuchElementException();
            }
            final Object[] array = this.array;
            final int offset = this.offset;
            final int curr = this.curr - 1;
            this.curr = curr;
            return array[offset + curr];
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
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n <= this.length - this.curr) {
                this.curr += n;
                return n;
            }
            n = this.length - this.curr;
            this.curr = this.length;
            return n;
        }
        
        @Override
        public int back(int curr) {
            if (curr < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + curr);
            }
            if (curr <= this.curr) {
                this.curr -= curr;
                return curr;
            }
            curr = this.curr;
            this.curr = 0;
            return curr;
        }
        
        @Override
        public int nextIndex() {
            return this.curr;
        }
        
        @Override
        public int previousIndex() {
            return this.curr - 1;
        }
    }
    
    private static class IteratorWrapper implements ObjectIterator
    {
        final Iterator i;
        
        public IteratorWrapper(final Iterator i) {
            this.i = i;
        }
        
        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
        
        @Override
        public void remove() {
            this.i.remove();
        }
        
        @Override
        public Object next() {
            return this.i.next();
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            this.i.forEachRemaining(consumer);
        }
    }
    
    private static class ListIteratorWrapper implements ObjectListIterator
    {
        final ListIterator i;
        
        public ListIteratorWrapper(final ListIterator i) {
            this.i = i;
        }
        
        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
        
        @Override
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }
        
        @Override
        public int nextIndex() {
            return this.i.nextIndex();
        }
        
        @Override
        public int previousIndex() {
            return this.i.previousIndex();
        }
        
        @Override
        public void set(final Object o) {
            this.i.set(o);
        }
        
        @Override
        public void add(final Object o) {
            this.i.add(o);
        }
        
        @Override
        public void remove() {
            this.i.remove();
        }
        
        @Override
        public Object next() {
            return this.i.next();
        }
        
        @Override
        public Object previous() {
            return this.i.previous();
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            this.i.forEachRemaining(consumer);
        }
    }
    
    private static class IteratorConcatenator implements ObjectIterator
    {
        final ObjectIterator[] a;
        int offset;
        int length;
        int lastOffset;
        
        public IteratorConcatenator(final ObjectIterator[] a, final int offset, final int length) {
            this.lastOffset = -1;
            this.a = a;
            this.offset = offset;
            this.length = length;
            this.advance();
        }
        
        private void advance() {
            while (this.length != 0 && !this.a[this.offset].hasNext()) {
                --this.length;
                ++this.offset;
            }
        }
        
        @Override
        public boolean hasNext() {
            return this.length > 0;
        }
        
        @Override
        public Object next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            final ObjectIterator[] a = this.a;
            final int offset = this.offset;
            this.lastOffset = offset;
            final Object next = a[offset].next();
            this.advance();
            return next;
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            while (this.length > 0) {
                final ObjectIterator[] a = this.a;
                final int offset = this.offset;
                this.lastOffset = offset;
                a[offset].forEachRemaining(consumer);
                this.advance();
            }
        }
        
        @Override
        public void remove() {
            if (this.lastOffset == -1) {
                throw new IllegalStateException();
            }
            this.a[this.lastOffset].remove();
        }
        
        @Override
        public int skip(final int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            this.lastOffset = -1;
            while (0 < n && this.length != 0) {
                final int n2 = 0 + this.a[this.offset].skip(n - 0);
                if (this.a[this.offset].hasNext()) {
                    break;
                }
                --this.length;
                ++this.offset;
            }
            return 0;
        }
    }
    
    public abstract static class AbstractIndexBasedListIterator extends AbstractIndexBasedIterator implements ObjectListIterator
    {
        protected AbstractIndexBasedListIterator(final int n, final int n2) {
            super(n, n2);
        }
        
        protected abstract void add(final int p0, final Object p1);
        
        protected abstract void set(final int p0, final Object p1);
        
        @Override
        public boolean hasPrevious() {
            return this.pos > this.minPos;
        }
        
        @Override
        public Object previous() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            final int n = this.pos - 1;
            this.pos = n;
            this.lastReturned = n;
            return this.get(n);
        }
        
        @Override
        public int nextIndex() {
            return this.pos;
        }
        
        @Override
        public int previousIndex() {
            return this.pos - 1;
        }
        
        @Override
        public void add(final Object o) {
            this.add(this.pos++, o);
            this.lastReturned = -1;
        }
        
        @Override
        public void set(final Object o) {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            this.set(this.lastReturned, o);
        }
        
        @Override
        public int back(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            final int n2 = this.pos - this.minPos;
            if (n < n2) {
                this.pos -= n;
            }
            else {
                n = n2;
                this.pos = this.minPos;
            }
            this.lastReturned = this.pos;
            return n;
        }
    }
    
    public abstract static class AbstractIndexBasedIterator extends AbstractObjectIterator
    {
        protected final int minPos;
        protected int pos;
        protected int lastReturned;
        
        protected AbstractIndexBasedIterator(final int minPos, final int pos) {
            this.minPos = minPos;
            this.pos = pos;
        }
        
        protected abstract Object get(final int p0);
        
        protected abstract void remove(final int p0);
        
        protected abstract int getMaxPos();
        
        @Override
        public boolean hasNext() {
            return this.pos < this.getMaxPos();
        }
        
        @Override
        public Object next() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            final int lastReturned = this.pos++;
            this.lastReturned = lastReturned;
            return this.get(lastReturned);
        }
        
        @Override
        public void remove() {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            this.remove(this.lastReturned);
            if (this.lastReturned < this.pos) {
                --this.pos;
            }
            this.lastReturned = -1;
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            while (this.pos < this.getMaxPos()) {
                final int lastReturned = this.pos++;
                this.lastReturned = lastReturned;
                consumer.accept(this.get(lastReturned));
            }
        }
        
        @Override
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            final int maxPos = this.getMaxPos();
            final int n2 = maxPos - this.pos;
            if (n < n2) {
                this.pos += n;
            }
            else {
                n = n2;
                this.pos = maxPos;
            }
            this.lastReturned = this.pos - 1;
            return n;
        }
    }
}
