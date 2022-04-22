package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import com.viaversion.viaversion.libs.fastutil.bytes.*;
import com.viaversion.viaversion.libs.fastutil.shorts.*;
import com.viaversion.viaversion.libs.fastutil.chars.*;
import java.util.*;
import java.util.function.*;
import java.io.*;

public final class IntIterators
{
    public static final EmptyIterator EMPTY_ITERATOR;
    
    private IntIterators() {
    }
    
    public static IntListIterator singleton(final int n) {
        return new SingletonIterator(n);
    }
    
    public static IntListIterator wrap(final int[] array, final int n, final int n2) {
        IntArrays.ensureOffsetLength(array, n, n2);
        return new ArrayIterator(array, n, n2);
    }
    
    public static IntListIterator wrap(final int[] array) {
        return new ArrayIterator(array, 0, array.length);
    }
    
    public static int unwrap(final IntIterator intIterator, final int[] array, int n, final int n2) {
        if (n2 < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0 || n + n2 > array.length) {
            throw new IllegalArgumentException();
        }
        int n3 = n2;
        while (n3-- != 0 && intIterator.hasNext()) {
            array[n++] = intIterator.nextInt();
        }
        return n2 - n3 - 1;
    }
    
    public static int unwrap(final IntIterator intIterator, final int[] array) {
        return unwrap(intIterator, array, 0, array.length);
    }
    
    public static int[] unwrap(final IntIterator intIterator, int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int[] grow = new int[16];
        while (n-- != 0 && intIterator.hasNext()) {
            if (0 == grow.length) {
                grow = IntArrays.grow(grow, 1);
            }
            final int[] array = grow;
            final int n2 = 0;
            int n3 = 0;
            ++n3;
            array[n2] = intIterator.nextInt();
        }
        return IntArrays.trim(grow, 0);
    }
    
    public static int[] unwrap(final IntIterator intIterator) {
        return unwrap(intIterator, Integer.MAX_VALUE);
    }
    
    public static long unwrap(final IntIterator intIterator, final int[][] array, long n, final long n2) {
        if (n2 < 0L) {
            throw new IllegalArgumentException("The maximum number of elements (" + n2 + ") is negative");
        }
        if (n < 0L || n + n2 > BigArrays.length(array)) {
            throw new IllegalArgumentException();
        }
        long n3 = n2;
        while (n3-- != 0L && intIterator.hasNext()) {
            BigArrays.set(array, n++, intIterator.nextInt());
        }
        return n2 - n3 - 1L;
    }
    
    public static long unwrap(final IntIterator intIterator, final int[][] array) {
        return unwrap(intIterator, array, 0L, BigArrays.length(array));
    }
    
    public static int unwrap(final IntIterator intIterator, final IntCollection collection, final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && intIterator.hasNext()) {
            collection.add(intIterator.nextInt());
        }
        return n - n2 - 1;
    }
    
    public static int[][] unwrapBig(final IntIterator intIterator, long n) {
        if (n < 0L) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int[][] array = IntBigArrays.newBigArray(16L);
        long n2 = 0L;
        while (n-- != 0L && intIterator.hasNext()) {
            if (n2 == BigArrays.length(array)) {
                array = BigArrays.grow(array, n2 + 1L);
            }
            BigArrays.set(array, n2++, intIterator.nextInt());
        }
        return BigArrays.trim(array, n2);
    }
    
    public static int[][] unwrapBig(final IntIterator intIterator) {
        return unwrapBig(intIterator, Long.MAX_VALUE);
    }
    
    public static long unwrap(final IntIterator intIterator, final IntCollection collection) {
        long n = 0L;
        while (intIterator.hasNext()) {
            collection.add(intIterator.nextInt());
            ++n;
        }
        return n;
    }
    
    public static int pour(final IntIterator intIterator, final IntCollection collection, final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("The maximum number of elements (" + n + ") is negative");
        }
        int n2 = n;
        while (n2-- != 0 && intIterator.hasNext()) {
            collection.add(intIterator.nextInt());
        }
        return n - n2 - 1;
    }
    
    public static int pour(final IntIterator intIterator, final IntCollection collection) {
        return pour(intIterator, collection, Integer.MAX_VALUE);
    }
    
    public static IntList pour(final IntIterator intIterator, final int n) {
        final IntArrayList list = new IntArrayList();
        pour(intIterator, list, n);
        list.trim();
        return list;
    }
    
    public static IntList pour(final IntIterator intIterator) {
        return pour(intIterator, Integer.MAX_VALUE);
    }
    
    public static IntIterator asIntIterator(final Iterator iterator) {
        if (iterator instanceof IntIterator) {
            return (IntIterator)iterator;
        }
        if (iterator instanceof PrimitiveIterator.OfInt) {
            return new PrimitiveIteratorWrapper((PrimitiveIterator.OfInt)iterator);
        }
        return new IteratorWrapper(iterator);
    }
    
    public static IntListIterator asIntIterator(final ListIterator listIterator) {
        if (listIterator instanceof IntListIterator) {
            return (IntListIterator)listIterator;
        }
        return new ListIteratorWrapper(listIterator);
    }
    
    public static boolean any(final IntIterator intIterator, final IntPredicate intPredicate) {
        return indexOf(intIterator, intPredicate) != -1;
    }
    
    public static boolean all(final IntIterator intIterator, final IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        while (intIterator.hasNext()) {
            if (!intPredicate.test(intIterator.nextInt())) {
                return false;
            }
        }
        return true;
    }
    
    public static int indexOf(final IntIterator intIterator, final IntPredicate intPredicate) {
        Objects.requireNonNull(intPredicate);
        while (intIterator.hasNext()) {
            if (intPredicate.test(intIterator.nextInt())) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    public static IntListIterator fromTo(final int n, final int n2) {
        return new IntervalIterator(n, n2);
    }
    
    public static IntIterator concat(final IntIterator... array) {
        return concat(array, 0, array.length);
    }
    
    public static IntIterator concat(final IntIterator[] array, final int n, final int n2) {
        return new IteratorConcatenator(array, n, n2);
    }
    
    public static IntIterator unmodifiable(final IntIterator intIterator) {
        return (IntIterator)new IntIterators.UnmodifiableIterator(intIterator);
    }
    
    public static IntBidirectionalIterator unmodifiable(final IntBidirectionalIterator intBidirectionalIterator) {
        return (IntBidirectionalIterator)new IntIterators.UnmodifiableBidirectionalIterator(intBidirectionalIterator);
    }
    
    public static IntListIterator unmodifiable(final IntListIterator intListIterator) {
        return (IntListIterator)new IntIterators.UnmodifiableListIterator(intListIterator);
    }
    
    public static IntIterator wrap(final ByteIterator byteIterator) {
        return (IntIterator)new IntIterators.ByteIteratorWrapper(byteIterator);
    }
    
    public static IntIterator wrap(final ShortIterator shortIterator) {
        return (IntIterator)new IntIterators.ShortIteratorWrapper(shortIterator);
    }
    
    public static IntIterator wrap(final CharIterator charIterator) {
        return (IntIterator)new IntIterators.CharIteratorWrapper(charIterator);
    }
    
    static {
        EMPTY_ITERATOR = new EmptyIterator();
    }
    
    private static class SingletonIterator implements IntListIterator
    {
        private final int element;
        private byte curr;
        
        public SingletonIterator(final int element) {
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
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = 1;
            return this.element;
        }
        
        @Override
        public int previousInt() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = 0;
            return this.element;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            if (this.curr == 0) {
                intConsumer.accept(this.element);
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
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
    }
    
    private static class ArrayIterator implements IntListIterator
    {
        private final int[] array;
        private final int offset;
        private final int length;
        private int curr;
        
        public ArrayIterator(final int[] array, final int offset, final int length) {
            this.array = array;
            this.offset = offset;
            this.length = length;
        }
        
        @Override
        public boolean hasNext() {
            return this.curr < this.length;
        }
        
        @Override
        public boolean hasPrevious() {
            return this.curr > 0;
        }
        
        @Override
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.array[this.offset + this.curr++];
        }
        
        @Override
        public int previousInt() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            final int[] array = this.array;
            final int offset = this.offset;
            final int curr = this.curr - 1;
            this.curr = curr;
            return array[offset + curr];
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
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
    }
    
    private static class PrimitiveIteratorWrapper implements IntIterator
    {
        final PrimitiveIterator.OfInt i;
        
        public PrimitiveIteratorWrapper(final PrimitiveIterator.OfInt i) {
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
        public int nextInt() {
            return this.i.nextInt();
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            this.i.forEachRemaining(intConsumer);
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
    }
    
    private static class IteratorWrapper implements IntIterator
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
        public int nextInt() {
            return this.i.next();
        }
        
        @Override
        public void forEachRemaining(final com.viaversion.viaversion.libs.fastutil.ints.IntConsumer intConsumer) {
            this.i.forEachRemaining(intConsumer);
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            final Iterator i = this.i;
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
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
    }
    
    private static class ListIteratorWrapper implements IntListIterator
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
        public void set(final int n) {
            this.i.set(n);
        }
        
        @Override
        public void add(final int n) {
            this.i.add(n);
        }
        
        @Override
        public void remove() {
            this.i.remove();
        }
        
        @Override
        public int nextInt() {
            return this.i.next();
        }
        
        @Override
        public int previousInt() {
            return this.i.previous();
        }
        
        @Override
        public void forEachRemaining(final com.viaversion.viaversion.libs.fastutil.ints.IntConsumer intConsumer) {
            this.i.forEachRemaining(intConsumer);
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            Objects.requireNonNull(intConsumer);
            final ListIterator i = this.i;
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
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
    }
    
    private static class IntervalIterator implements IntListIterator
    {
        private final int from;
        private final int to;
        int curr;
        
        public IntervalIterator(final int n, final int to) {
            this.curr = n;
            this.from = n;
            this.to = to;
        }
        
        @Override
        public boolean hasNext() {
            return this.curr < this.to;
        }
        
        @Override
        public boolean hasPrevious() {
            return this.curr > this.from;
        }
        
        @Override
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            return this.curr++;
        }
        
        @Override
        public int previousInt() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            return --this.curr;
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
        public int nextIndex() {
            return this.curr - this.from;
        }
        
        @Override
        public int previousIndex() {
            return this.curr - this.from - 1;
        }
        
        @Override
        public int skip(int n) {
            if (n < 0) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.curr + n <= this.to) {
                this.curr += n;
                return n;
            }
            n = this.to - this.curr;
            this.curr = this.to;
            return n;
        }
        
        @Override
        public int back(int n) {
            if (this.curr - n >= this.from) {
                this.curr -= n;
                return n;
            }
            n = this.curr - this.from;
            this.curr = this.from;
            return n;
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
    }
    
    private static class IteratorConcatenator implements IntIterator
    {
        final IntIterator[] a;
        int offset;
        int length;
        int lastOffset;
        
        public IteratorConcatenator(final IntIterator[] a, final int offset, final int length) {
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
        public int nextInt() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            final IntIterator[] a = this.a;
            final int offset = this.offset;
            this.lastOffset = offset;
            final int nextInt = a[offset].nextInt();
            this.advance();
            return nextInt;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            while (this.length > 0) {
                final IntIterator[] a = this.a;
                final int offset = this.offset;
                this.lastOffset = offset;
                a[offset].forEachRemaining(intConsumer);
                this.advance();
            }
        }
        
        @Deprecated
        @Override
        public void forEachRemaining(final Consumer consumer) {
            while (this.length > 0) {
                final IntIterator[] a = this.a;
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
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
    }
    
    public static class EmptyIterator implements IntListIterator, Serializable, Cloneable
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
        public int nextInt() {
            throw new NoSuchElementException();
        }
        
        @Override
        public int previousInt() {
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
        public void forEachRemaining(final IntConsumer intConsumer) {
        }
        
        @Deprecated
        @Override
        public void forEachRemaining(final Consumer consumer) {
        }
        
        public Object clone() {
            return IntIterators.EMPTY_ITERATOR;
        }
        
        private Object readResolve() {
            return IntIterators.EMPTY_ITERATOR;
        }
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
    }
    
    public abstract static class AbstractIndexBasedListIterator extends AbstractIndexBasedIterator implements IntListIterator
    {
        protected AbstractIndexBasedListIterator(final int n, final int n2) {
            super(n, n2);
        }
        
        protected abstract void add(final int p0, final int p1);
        
        protected abstract void set(final int p0, final int p1);
        
        @Override
        public boolean hasPrevious() {
            return this.pos > this.minPos;
        }
        
        @Override
        public int previousInt() {
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
        public void add(final int n) {
            this.add(this.pos++, n);
            this.lastReturned = -1;
        }
        
        @Override
        public void set(final int n) {
            if (this.lastReturned == -1) {
                throw new IllegalStateException();
            }
            this.set(this.lastReturned, n);
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
    
    public abstract static class AbstractIndexBasedIterator extends AbstractIntIterator
    {
        protected final int minPos;
        protected int pos;
        protected int lastReturned;
        
        protected AbstractIndexBasedIterator(final int minPos, final int pos) {
            this.minPos = minPos;
            this.pos = pos;
        }
        
        protected abstract int get(final int p0);
        
        protected abstract void remove(final int p0);
        
        protected abstract int getMaxPos();
        
        @Override
        public boolean hasNext() {
            return this.pos < this.getMaxPos();
        }
        
        @Override
        public int nextInt() {
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
        public void forEachRemaining(final IntConsumer intConsumer) {
            while (this.pos < this.getMaxPos()) {
                final int lastReturned = this.pos++;
                this.lastReturned = lastReturned;
                intConsumer.accept(this.get(lastReturned));
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
        
        @Override
        public void forEachRemaining(final Object o) {
            this.forEachRemaining((IntConsumer)o);
        }
    }
}
