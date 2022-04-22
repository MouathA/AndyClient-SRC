package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.stream.*;
import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;

public class IntImmutableList extends IntLists.ImmutableListBase implements IntList, RandomAccess, Cloneable, Serializable
{
    private static final long serialVersionUID = 0L;
    static final IntImmutableList EMPTY;
    private final int[] a;
    
    public IntImmutableList(final int[] a) {
        this.a = a;
    }
    
    public IntImmutableList(final Collection collection) {
        this(collection.isEmpty() ? IntArrays.EMPTY_ARRAY : IntIterators.unwrap(IntIterators.asIntIterator(collection.iterator())));
    }
    
    public IntImmutableList(final IntCollection collection) {
        this(collection.isEmpty() ? IntArrays.EMPTY_ARRAY : IntIterators.unwrap(collection.iterator()));
    }
    
    public IntImmutableList(final IntList list) {
        this(list.isEmpty() ? IntArrays.EMPTY_ARRAY : new int[list.size()]);
        list.getElements(0, this.a, 0, list.size());
    }
    
    public IntImmutableList(final int[] array, final int n, final int n2) {
        this((n2 == 0) ? IntArrays.EMPTY_ARRAY : new int[n2]);
        System.arraycopy(array, n, this.a, 0, n2);
    }
    
    public IntImmutableList(final IntIterator intIterator) {
        this(intIterator.hasNext() ? IntIterators.unwrap(intIterator) : IntArrays.EMPTY_ARRAY);
    }
    
    public static IntImmutableList of() {
        return IntImmutableList.EMPTY;
    }
    
    public static IntImmutableList of(final int... array) {
        return (array.length == 0) ? of() : new IntImmutableList(array);
    }
    
    private static IntImmutableList convertTrustedToImmutableList(final IntArrayList list) {
        if (list.isEmpty()) {
            return of();
        }
        int[] array = list.elements();
        if (list.size() != array.length) {
            array = Arrays.copyOf(array, list.size());
        }
        return new IntImmutableList(array);
    }
    
    public static IntImmutableList toList(final IntStream intStream) {
        return convertTrustedToImmutableList(IntArrayList.toList(intStream));
    }
    
    public static IntImmutableList toListWithExpectedSize(final IntStream intStream, final int n) {
        return convertTrustedToImmutableList(IntArrayList.toListWithExpectedSize(intStream, n));
    }
    
    @Override
    public int getInt(final int n) {
        if (n >= this.a.length) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.a.length + ")");
        }
        return this.a[n];
    }
    
    @Override
    public int indexOf(final int n) {
        while (0 < this.a.length) {
            if (n == this.a[0]) {
                return 0;
            }
            int n2 = 0;
            ++n2;
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(final int n) {
        int length = this.a.length;
        while (length-- != 0) {
            if (n == this.a[length]) {
                return length;
            }
        }
        return -1;
    }
    
    @Override
    public int size() {
        return this.a.length;
    }
    
    @Override
    public boolean isEmpty() {
        return this.a.length == 0;
    }
    
    @Override
    public void getElements(final int n, final int[] array, final int n2, final int n3) {
        IntArrays.ensureOffsetLength(array, n2, n3);
        System.arraycopy(this.a, n, array, n2, n3);
    }
    
    @Override
    public void forEach(final IntConsumer intConsumer) {
        while (0 < this.a.length) {
            intConsumer.accept(this.a[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public int[] toIntArray() {
        return this.a.clone();
    }
    
    @Override
    public int[] toArray(int[] array) {
        if (array == null || array.length < this.size()) {
            array = new int[this.a.length];
        }
        System.arraycopy(this.a, 0, array, 0, array.length);
        return array;
    }
    
    @Override
    public IntListIterator listIterator(final int n) {
        this.ensureIndex(n);
        return new IntListIterator(n) {
            int pos = this.val$index;
            final int val$index;
            final IntImmutableList this$0;
            
            @Override
            public boolean hasNext() {
                return this.pos < IntImmutableList.access$000(this.this$0).length;
            }
            
            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }
            
            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return IntImmutableList.access$000(this.this$0)[this.pos++];
            }
            
            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                final int[] access$000 = IntImmutableList.access$000(this.this$0);
                final int pos = this.pos - 1;
                this.pos = pos;
                return access$000[pos];
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
            public void forEachRemaining(final IntConsumer intConsumer) {
                while (this.pos < IntImmutableList.access$000(this.this$0).length) {
                    intConsumer.accept(IntImmutableList.access$000(this.this$0)[this.pos++]);
                }
            }
            
            @Override
            public void add(final int n) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public void set(final int n) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                final int n2 = IntImmutableList.access$000(this.this$0).length - this.pos;
                if (n < n2) {
                    this.pos -= n;
                }
                else {
                    n = n2;
                    this.pos = 0;
                }
                return n;
            }
            
            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                final int n2 = IntImmutableList.access$000(this.this$0).length - this.pos;
                if (n < n2) {
                    this.pos += n;
                }
                else {
                    n = n2;
                    this.pos = IntImmutableList.access$000(this.this$0).length;
                }
                return n;
            }
            
            @Override
            public void forEachRemaining(final Object o) {
                this.forEachRemaining((IntConsumer)o);
            }
        };
    }
    
    @Override
    public IntSpliterator spliterator() {
        return new Spliterator();
    }
    
    @Override
    public IntList subList(final int n, final int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n == n2) {
            return IntImmutableList.EMPTY;
        }
        if (n > n2) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new ImmutableSubList(this, n, n2);
    }
    
    public IntImmutableList clone() {
        return this;
    }
    
    public boolean equals(final IntImmutableList list) {
        return list == this || this.a == list.a || (this.size() == list.size() && Arrays.equals(this.a, list.a));
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof List)) {
            return false;
        }
        if (o instanceof IntImmutableList) {
            return this.equals((IntImmutableList)o);
        }
        if (o instanceof ImmutableSubList) {
            return ((ImmutableSubList)o).equals(this);
        }
        return super.equals(o);
    }
    
    public int compareTo(final IntImmutableList list) {
        if (this.a == list.a) {
            return 0;
        }
        final int size = this.size();
        final int size2 = list.size();
        final int[] a = this.a;
        final int[] a2 = list.a;
        while (0 < size && 0 < size2) {
            final int compare;
            if ((compare = Integer.compare(a[0], a2[0])) != 0) {
                return compare;
            }
            int n = 0;
            ++n;
        }
        return (0 < size2) ? -1 : ((0 < size) ? 1 : 0);
    }
    
    @Override
    public int compareTo(final List list) {
        if (list instanceof IntImmutableList) {
            return this.compareTo((IntImmutableList)list);
        }
        if (list instanceof ImmutableSubList) {
            return -((ImmutableSubList)list).compareTo(this);
        }
        return super.compareTo(list);
    }
    
    @Override
    public java.util.Spliterator spliterator() {
        return this.spliterator();
    }
    
    @Override
    public List subList(final int n, final int n2) {
        return this.subList(n, n2);
    }
    
    @Override
    public ListIterator listIterator(final int n) {
        return this.listIterator(n);
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((List)o);
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static int[] access$000(final IntImmutableList list) {
        return list.a;
    }
    
    static {
        EMPTY = new IntImmutableList(IntArrays.EMPTY_ARRAY);
    }
    
    private final class Spliterator implements IntSpliterator
    {
        int pos;
        int max;
        static final boolean $assertionsDisabled;
        final IntImmutableList this$0;
        
        public Spliterator(final IntImmutableList list) {
            this(list, 0, IntImmutableList.access$000(list).length);
        }
        
        private Spliterator(final IntImmutableList this$0, final int pos, final int max) {
            this.this$0 = this$0;
            assert pos <= max : "pos " + pos + " must be <= max " + max;
            this.pos = pos;
            this.max = max;
        }
        
        @Override
        public int characteristics() {
            return 17744;
        }
        
        @Override
        public long estimateSize() {
            return this.max - this.pos;
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            if (this.pos >= this.max) {
                return false;
            }
            intConsumer.accept(IntImmutableList.access$000(this.this$0)[this.pos++]);
            return true;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            while (this.pos < this.max) {
                intConsumer.accept(IntImmutableList.access$000(this.this$0)[this.pos]);
                ++this.pos;
            }
        }
        
        @Override
        public long skip(long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (this.pos >= this.max) {
                return 0L;
            }
            final int n2 = this.max - this.pos;
            if (n < n2) {
                this.pos = SafeMath.safeLongToInt(this.pos + n);
                return n;
            }
            n = n2;
            this.pos = this.max;
            return n;
        }
        
        @Override
        public IntSpliterator trySplit() {
            final int n = this.max - this.pos >> 1;
            if (n <= 1) {
                return null;
            }
            final int pos = this.pos + n;
            final int pos2 = this.pos;
            this.pos = pos;
            return this.this$0.new Spliterator(pos2, pos);
        }
        
        @Override
        public OfInt trySplit() {
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
        public OfPrimitive trySplit() {
            return this.trySplit();
        }
        
        @Override
        public java.util.Spliterator trySplit() {
            return this.trySplit();
        }
        
        static {
            $assertionsDisabled = !IntImmutableList.class.desiredAssertionStatus();
        }
    }
    
    private static final class ImmutableSubList extends IntLists.ImmutableListBase implements RandomAccess, Serializable
    {
        private static final long serialVersionUID = 7054639518438982401L;
        final IntImmutableList innerList;
        final int from;
        final int to;
        final transient int[] a;
        
        ImmutableSubList(final IntImmutableList innerList, final int from, final int to) {
            this.innerList = innerList;
            this.from = from;
            this.to = to;
            this.a = IntImmutableList.access$000(innerList);
        }
        
        @Override
        public int getInt(final int n) {
            this.ensureRestrictedIndex(n);
            return this.a[n + this.from];
        }
        
        @Override
        public int indexOf(final int n) {
            for (int i = this.from; i < this.to; ++i) {
                if (n == this.a[i]) {
                    return i - this.from;
                }
            }
            return -1;
        }
        
        @Override
        public int lastIndexOf(final int n) {
            int to = this.to;
            while (to-- != this.from) {
                if (n == this.a[to]) {
                    return to - this.from;
                }
            }
            return -1;
        }
        
        @Override
        public int size() {
            return this.to - this.from;
        }
        
        @Override
        public boolean isEmpty() {
            return this.to <= this.from;
        }
        
        @Override
        public void getElements(final int n, final int[] array, final int n2, final int n3) {
            IntArrays.ensureOffsetLength(array, n2, n3);
            this.ensureRestrictedIndex(n);
            if (this.from + n3 > this.to) {
                throw new IndexOutOfBoundsException("Final index " + (this.from + n3) + " (startingIndex: " + this.from + " + length: " + n3 + ") is greater then list length " + this.size());
            }
            System.arraycopy(this.a, n + this.from, array, n2, n3);
        }
        
        @Override
        public void forEach(final IntConsumer intConsumer) {
            for (int i = this.from; i < this.to; ++i) {
                intConsumer.accept(this.a[i]);
            }
        }
        
        @Override
        public int[] toIntArray() {
            return Arrays.copyOfRange(this.a, this.from, this.to);
        }
        
        @Override
        public int[] toArray(int[] array) {
            if (array == null || array.length < this.size()) {
                array = new int[this.size()];
            }
            System.arraycopy(this.a, this.from, array, 0, this.size());
            return array;
        }
        
        @Override
        public IntListIterator listIterator(final int n) {
            this.ensureIndex(n);
            return new IntListIterator(n) {
                int pos = this.val$index;
                final int val$index;
                final ImmutableSubList this$0;
                
                @Override
                public boolean hasNext() {
                    return this.pos < this.this$0.to;
                }
                
                @Override
                public boolean hasPrevious() {
                    return this.pos > this.this$0.from;
                }
                
                @Override
                public int nextInt() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return this.this$0.a[this.pos++ + this.this$0.from];
                }
                
                @Override
                public int previousInt() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    final int[] a = this.this$0.a;
                    final int pos = this.pos - 1;
                    this.pos = pos;
                    return a[pos + this.this$0.from];
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
                public void forEachRemaining(final IntConsumer intConsumer) {
                    while (this.pos < this.this$0.to) {
                        intConsumer.accept(this.this$0.a[this.pos++ + this.this$0.from]);
                    }
                }
                
                @Override
                public void add(final int n) {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public void set(final int n) {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public void remove() {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public int back(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    final int n2 = this.this$0.to - this.pos;
                    if (n < n2) {
                        this.pos -= n;
                    }
                    else {
                        n = n2;
                        this.pos = 0;
                    }
                    return n;
                }
                
                @Override
                public int skip(int n) {
                    if (n < 0) {
                        throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                    }
                    final int n2 = this.this$0.to - this.pos;
                    if (n < n2) {
                        this.pos += n;
                    }
                    else {
                        n = n2;
                        this.pos = this.this$0.to;
                    }
                    return n;
                }
                
                @Override
                public void forEachRemaining(final Object o) {
                    this.forEachRemaining((IntConsumer)o);
                }
            };
        }
        
        @Override
        public IntSpliterator spliterator() {
            return new SubListSpliterator();
        }
        
        boolean contentsEquals(final int[] array, final int n, final int n2) {
            if (this.a == array && this.from == n && this.to == n2) {
                return true;
            }
            if (n2 - n != this.size()) {
                return false;
            }
            int i = this.from;
            int n3 = n;
            while (i < this.to) {
                if (this.a[i++] != array[n3++]) {
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (o == null) {
                return false;
            }
            if (!(o instanceof List)) {
                return false;
            }
            if (o instanceof IntImmutableList) {
                final IntImmutableList list = (IntImmutableList)o;
                return this.contentsEquals(IntImmutableList.access$000(list), 0, list.size());
            }
            if (o instanceof ImmutableSubList) {
                final ImmutableSubList list2 = (ImmutableSubList)o;
                return this.contentsEquals(list2.a, list2.from, list2.to);
            }
            return super.equals(o);
        }
        
        int contentsCompareTo(final int[] array, final int n, final int n2) {
            if (this.a == array && this.from == n && this.to == n2) {
                return 0;
            }
            int from = this.from;
            for (int n3 = n; from < this.to && from < n2; ++from, ++n3) {
                final int compare;
                if ((compare = Integer.compare(this.a[from], array[n3])) != 0) {
                    return compare;
                }
            }
            return (from < n2) ? -1 : (from < this.to);
        }
        
        @Override
        public int compareTo(final List list) {
            if (list instanceof IntImmutableList) {
                final IntImmutableList list2 = (IntImmutableList)list;
                return this.contentsCompareTo(IntImmutableList.access$000(list2), 0, list2.size());
            }
            if (list instanceof ImmutableSubList) {
                final ImmutableSubList list3 = (ImmutableSubList)list;
                return this.contentsCompareTo(list3.a, list3.from, list3.to);
            }
            return super.compareTo(list);
        }
        
        private Object readResolve() throws ObjectStreamException {
            return this.innerList.subList(this.from, this.to);
        }
        
        @Override
        public IntList subList(final int n, final int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n == n2) {
                return IntImmutableList.EMPTY;
            }
            if (n > n2) {
                throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
            }
            return new ImmutableSubList(this.innerList, n + this.from, n2 + this.from);
        }
        
        @Override
        public java.util.Spliterator spliterator() {
            return this.spliterator();
        }
        
        @Override
        public List subList(final int n, final int n2) {
            return this.subList(n, n2);
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            return this.listIterator(n);
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((List)o);
        }
        
        private final class SubListSpliterator extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator
        {
            final ImmutableSubList this$0;
            
            SubListSpliterator(final ImmutableSubList this$0) {
                this.this$0 = this$0;
                super(this$0.from, this$0.to);
            }
            
            private SubListSpliterator(final ImmutableSubList this$0, final int n, final int n2) {
                this.this$0 = this$0;
                super(n, n2);
            }
            
            @Override
            protected final int get(final int n) {
                return this.this$0.a[n];
            }
            
            @Override
            protected final SubListSpliterator makeForSplit(final int n, final int n2) {
                return this.this$0.new SubListSpliterator(n, n2);
            }
            
            @Override
            public boolean tryAdvance(final IntConsumer intConsumer) {
                if (this.pos >= this.maxPos) {
                    return false;
                }
                intConsumer.accept(this.this$0.a[this.pos++]);
                return true;
            }
            
            @Override
            public void forEachRemaining(final IntConsumer intConsumer) {
                while (this.pos < this.maxPos) {
                    intConsumer.accept(this.this$0.a[this.pos++]);
                }
            }
            
            @Override
            public int characteristics() {
                return 17744;
            }
            
            @Override
            protected IntSpliterator makeForSplit(final int n, final int n2) {
                return this.makeForSplit(n, n2);
            }
            
            @Override
            public void forEachRemaining(final Object o) {
                this.forEachRemaining((IntConsumer)o);
            }
            
            @Override
            public boolean tryAdvance(final Object o) {
                return this.tryAdvance((IntConsumer)o);
            }
        }
    }
}
