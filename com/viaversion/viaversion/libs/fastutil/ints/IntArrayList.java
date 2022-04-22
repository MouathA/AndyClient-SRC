package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.stream.*;
import java.util.function.*;
import java.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.*;

public class IntArrayList extends AbstractIntList implements RandomAccess, Cloneable, Serializable
{
    private static final long serialVersionUID = -7046029254386353130L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected transient int[] a;
    protected int size;
    static final boolean $assertionsDisabled;
    
    private static final int[] copyArraySafe(final int[] array, final int n) {
        if (n == 0) {
            return IntArrays.EMPTY_ARRAY;
        }
        return Arrays.copyOf(array, n);
    }
    
    private static final int[] copyArrayFromSafe(final IntArrayList list) {
        return copyArraySafe(list.a, list.size);
    }
    
    protected IntArrayList(final int[] a, final boolean b) {
        this.a = a;
    }
    
    private void initArrayFromCapacity(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        if (n == 0) {
            this.a = IntArrays.EMPTY_ARRAY;
        }
        else {
            this.a = new int[n];
        }
    }
    
    public IntArrayList(final int n) {
        this.initArrayFromCapacity(n);
    }
    
    public IntArrayList() {
        this.a = IntArrays.DEFAULT_EMPTY_ARRAY;
    }
    
    public IntArrayList(final Collection collection) {
        if (collection instanceof IntArrayList) {
            this.a = copyArrayFromSafe((IntArrayList)collection);
            this.size = this.a.length;
        }
        else {
            this.initArrayFromCapacity(collection.size());
            if (collection instanceof IntList) {
                ((IntArrayList)collection).getElements(0, this.a, 0, this.size = collection.size());
            }
            else {
                this.size = IntIterators.unwrap(IntIterators.asIntIterator(collection.iterator()), this.a);
            }
        }
    }
    
    public IntArrayList(final IntCollection collection) {
        if (collection instanceof IntArrayList) {
            this.a = copyArrayFromSafe((IntArrayList)collection);
            this.size = this.a.length;
        }
        else {
            this.initArrayFromCapacity(collection.size());
            if (collection instanceof IntList) {
                ((IntList)collection).getElements(0, this.a, 0, this.size = collection.size());
            }
            else {
                this.size = IntIterators.unwrap(collection.iterator(), this.a);
            }
        }
    }
    
    public IntArrayList(final IntList list) {
        if (list instanceof IntArrayList) {
            this.a = copyArrayFromSafe((IntArrayList)list);
            this.size = this.a.length;
        }
        else {
            this.initArrayFromCapacity(list.size());
            list.getElements(0, this.a, 0, this.size = list.size());
        }
    }
    
    public IntArrayList(final int[] array) {
        this(array, 0, array.length);
    }
    
    public IntArrayList(final int[] array, final int n, final int size) {
        this(size);
        System.arraycopy(array, n, this.a, 0, size);
        this.size = size;
    }
    
    public IntArrayList(final Iterator iterator) {
        this();
        while (iterator.hasNext()) {
            this.add((int)iterator.next());
        }
    }
    
    public IntArrayList(final IntIterator intIterator) {
        this();
        while (intIterator.hasNext()) {
            this.add(intIterator.nextInt());
        }
    }
    
    public int[] elements() {
        return this.a;
    }
    
    public static IntArrayList wrap(final int[] array, final int size) {
        if (size > array.length) {
            throw new IllegalArgumentException("The specified length (" + size + ") is greater than the array size (" + array.length + ")");
        }
        final IntArrayList list = new IntArrayList(array, true);
        list.size = size;
        return list;
    }
    
    public static IntArrayList wrap(final int[] array) {
        return wrap(array, array.length);
    }
    
    public static IntArrayList of() {
        return new IntArrayList();
    }
    
    public static IntArrayList of(final int... array) {
        return wrap(array);
    }
    
    public static IntArrayList toList(final IntStream intStream) {
        return intStream.collect(IntArrayList::new, IntArrayList::add, IntList::addAll);
    }
    
    public static IntArrayList toListWithExpectedSize(final IntStream intStream, final int n) {
        if (n <= 10) {
            return toList(intStream);
        }
        return intStream.collect(new IntCollections.SizeDecreasingSupplier(n, IntArrayList::lambda$toListWithExpectedSize$0), IntArrayList::add, IntList::addAll);
    }
    
    public void ensureCapacity(final int n) {
        if (n <= this.a.length || (this.a == IntArrays.DEFAULT_EMPTY_ARRAY && n <= 10)) {
            return;
        }
        this.a = IntArrays.ensureCapacity(this.a, n, this.size);
        assert this.size <= this.a.length;
    }
    
    private void grow(int n) {
        if (10 <= this.a.length) {
            return;
        }
        if (this.a != IntArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min(this.a.length + (long)(this.a.length >> 1), 2147483639L), 10);
        }
        else if (10 < 10) {}
        this.a = IntArrays.forceCapacity(this.a, 10, this.size);
        assert this.size <= this.a.length;
    }
    
    @Override
    public void add(final int n, final int n2) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = n2;
        ++this.size;
        assert this.size <= this.a.length;
    }
    
    @Override
    public boolean add(final int n) {
        this.grow(this.size + 1);
        this.a[this.size++] = n;
        assert this.size <= this.a.length;
        return true;
    }
    
    @Override
    public int getInt(final int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }
    
    @Override
    public int indexOf(final int n) {
        while (0 < this.size) {
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
        int size = this.size;
        while (size-- != 0) {
            if (n == this.a[size]) {
                return size;
            }
        }
        return -1;
    }
    
    @Override
    public int removeInt(final int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        final int n2 = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        assert this.size <= this.a.length;
        return n2;
    }
    
    @Override
    public boolean rem(final int n) {
        final int index = this.indexOf(n);
        if (index == -1) {
            return false;
        }
        this.removeInt(index);
        assert this.size <= this.a.length;
        return true;
    }
    
    @Override
    public int set(final int n, final int n2) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        final int n3 = this.a[n];
        this.a[n] = n2;
        return n3;
    }
    
    @Override
    public void clear() {
        this.size = 0;
        assert this.size <= this.a.length;
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public void size(final int size) {
        if (size > this.a.length) {
            this.a = IntArrays.forceCapacity(this.a, size, this.size);
        }
        if (size > this.size) {
            Arrays.fill(this.a, this.size, size, 0);
        }
        this.size = size;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    public void trim() {
        this.trim(0);
    }
    
    public void trim(final int n) {
        if (n >= this.a.length || this.size == this.a.length) {
            return;
        }
        final int[] a = new int[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, a, 0, this.size);
        this.a = a;
        assert this.size <= this.a.length;
    }
    
    @Override
    public IntList subList(final int n, final int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n > n2) {
            throw new IndexOutOfBoundsException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new SubList(n, n2);
    }
    
    @Override
    public void getElements(final int n, final int[] array, final int n2, final int n3) {
        IntArrays.ensureOffsetLength(array, n2, n3);
        System.arraycopy(this.a, n, array, n2, n3);
    }
    
    @Override
    public void removeElements(final int n, final int n2) {
        com.viaversion.viaversion.libs.fastutil.Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
    }
    
    @Override
    public void addElements(final int n, final int[] array, final int n2, final int n3) {
        this.ensureIndex(n);
        IntArrays.ensureOffsetLength(array, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(array, n2, this.a, n, n3);
        this.size += n3;
    }
    
    @Override
    public void setElements(final int n, final int[] array, final int n2, final int n3) {
        this.ensureIndex(n);
        IntArrays.ensureOffsetLength(array, n2, n3);
        if (n + n3 > this.size) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size + ")");
        }
        System.arraycopy(array, n2, this.a, n, n3);
    }
    
    @Override
    public void forEach(final IntConsumer intConsumer) {
        while (0 < this.size) {
            intConsumer.accept(this.a[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean addAll(int n, final IntCollection collection) {
        if (collection instanceof IntList) {
            return this.addAll(n, (IntList)collection);
        }
        this.ensureIndex(n);
        int size = collection.size();
        if (size == 0) {
            return false;
        }
        this.grow(this.size + size);
        System.arraycopy(this.a, n, this.a, n + size, this.size - n);
        final IntIterator iterator = collection.iterator();
        this.size += size;
        while (size-- != 0) {
            this.a[n++] = iterator.nextInt();
        }
        assert this.size <= this.a.length;
        return true;
    }
    
    @Override
    public boolean addAll(final int n, final IntList list) {
        this.ensureIndex(n);
        final int size = list.size();
        if (size == 0) {
            return false;
        }
        this.grow(this.size + size);
        System.arraycopy(this.a, n, this.a, n + size, this.size - n);
        list.getElements(0, this.a, n, size);
        this.size += size;
        assert this.size <= this.a.length;
        return true;
    }
    
    @Override
    public boolean removeAll(final IntCollection collection) {
        final int[] a = this.a;
        while (0 < this.size) {
            if (!collection.contains(a[0])) {
                final int[] array = a;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array[n] = a[0];
            }
            int n3 = 0;
            ++n3;
        }
        int n3 = (this.size != 0) ? 1 : 0;
        this.size = 0;
        return false;
    }
    
    @Override
    public int[] toArray(int[] copy) {
        if (copy == null || copy.length < this.size) {
            copy = Arrays.copyOf(copy, this.size);
        }
        System.arraycopy(this.a, 0, copy, 0, this.size);
        return copy;
    }
    
    @Override
    public IntListIterator listIterator(final int n) {
        this.ensureIndex(n);
        return new IntListIterator(n) {
            int pos = this.val$index;
            int last = -1;
            final int val$index;
            final IntArrayList this$0;
            
            @Override
            public int nextInt() {
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
                //    13: getfield        com/viaversion/viaversion/libs/fastutil/ints/IntArrayList$1.this$0:Lcom/viaversion/viaversion/libs/fastutil/ints/IntArrayList;
                //    16: getfield        com/viaversion/viaversion/libs/fastutil/ints/IntArrayList.a:[I
                //    19: aload_0        
                //    20: aload_0        
                //    21: dup            
                //    22: getfield        com/viaversion/viaversion/libs/fastutil/ints/IntArrayList$1.pos:I
                //    25: dup_x1         
                //    26: iconst_1       
                //    27: iadd           
                //    28: putfield        com/viaversion/viaversion/libs/fastutil/ints/IntArrayList$1.pos:I
                //    31: dup_x1         
                //    32: putfield        com/viaversion/viaversion/libs/fastutil/ints/IntArrayList$1.last:I
                //    35: iaload         
                //    36: ireturn        
                // 
                // The error that occurred was:
                // 
                // java.lang.ArrayIndexOutOfBoundsException
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
            
            @Override
            public int previousInt() {
                if (this > 0) {
                    throw new NoSuchElementException();
                }
                final int[] a = this.this$0.a;
                final int n = this.pos - 1;
                this.pos = n;
                this.last = n;
                return a[n];
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
                this.this$0.add(this.pos++, n);
                this.last = -1;
            }
            
            @Override
            public void set(final int n) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, n);
            }
            
            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.removeInt(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
            
            @Override
            public void forEachRemaining(final IntConsumer intConsumer) {
                while (this.pos < this.this$0.size) {
                    final int[] a = this.this$0.a;
                    final int last = this.pos++;
                    this.last = last;
                    intConsumer.accept(a[last]);
                }
            }
            
            @Override
            public int back(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                final int n2 = this.this$0.size - this.pos;
                if (n < n2) {
                    this.pos -= n;
                }
                else {
                    n = n2;
                    this.pos = 0;
                }
                this.last = this.pos;
                return n;
            }
            
            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                final int n2 = this.this$0.size - this.pos;
                if (n < n2) {
                    this.pos += n;
                }
                else {
                    n = n2;
                    this.pos = this.this$0.size;
                }
                this.last = this.pos - 1;
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
    public void sort(final IntComparator intComparator) {
        if (intComparator == null) {
            IntArrays.stableSort(this.a, 0, this.size);
        }
        else {
            IntArrays.stableSort(this.a, 0, this.size, intComparator);
        }
    }
    
    @Override
    public void unstableSort(final IntComparator intComparator) {
        if (intComparator == null) {
            IntArrays.unstableSort(this.a, 0, this.size);
        }
        else {
            IntArrays.unstableSort(this.a, 0, this.size, intComparator);
        }
    }
    
    public IntArrayList clone() {
        IntArrayList list;
        if (this.getClass() == IntArrayList.class) {
            list = new IntArrayList(copyArraySafe(this.a, this.size), false);
            list.size = this.size;
        }
        else {
            list = (IntArrayList)super.clone();
            list.a = copyArraySafe(this.a, this.size);
        }
        return list;
    }
    
    public boolean equals(final IntArrayList list) {
        if (list == this) {
            return true;
        }
        int size = this.size();
        if (size != list.size()) {
            return false;
        }
        final int[] a = this.a;
        final int[] a2 = list.a;
        if (a == a2 && size == list.size()) {
            return true;
        }
        while (size-- != 0) {
            if (a[size] != a2[size]) {
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
        if (o instanceof IntArrayList) {
            return this.equals((IntArrayList)o);
        }
        if (o instanceof SubList) {
            return ((SubList)o).equals(this);
        }
        return super.equals(o);
    }
    
    public int compareTo(final IntArrayList list) {
        final int size = this.size();
        final int size2 = list.size();
        final int[] a = this.a;
        final int[] a2 = list.a;
        if (a == a2 && size == size2) {
            return 0;
        }
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
        if (list instanceof IntArrayList) {
            return this.compareTo((IntArrayList)list);
        }
        if (list instanceof SubList) {
            return -((SubList)list).compareTo(this);
        }
        return super.compareTo(list);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        while (0 < this.size) {
            objectOutputStream.writeInt(this.a[0]);
            int n = 0;
            ++n;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new int[this.size];
        while (0 < this.size) {
            this.a[0] = objectInputStream.readInt();
            int n = 0;
            ++n;
        }
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
    
    private static IntArrayList lambda$toListWithExpectedSize$0(final int n) {
        return (n <= 10) ? new IntArrayList() : new IntArrayList(n);
    }
    
    static {
        $assertionsDisabled = !IntArrayList.class.desiredAssertionStatus();
    }
    
    private class SubList extends IntRandomAccessSubList
    {
        private static final long serialVersionUID = -3185226345314976296L;
        final IntArrayList this$0;
        
        protected SubList(final IntArrayList this$0, final int n, final int n2) {
            super(this.this$0 = this$0, n, n2);
        }
        
        private int[] getParentArray() {
            return this.this$0.a;
        }
        
        @Override
        public int getInt(final int n) {
            this.ensureRestrictedIndex(n);
            return this.this$0.a[n + this.from];
        }
        
        @Override
        public IntListIterator listIterator(final int n) {
            return new SubListIterator(n);
        }
        
        @Override
        public IntSpliterator spliterator() {
            return new SubListSpliterator();
        }
        
        boolean contentsEquals(final int[] array, final int n, final int n2) {
            if (this.this$0.a == array && this.from == n && this.to == n2) {
                return true;
            }
            if (n2 - n != this.size()) {
                return false;
            }
            int i = this.from;
            int n3 = n;
            while (i < this.to) {
                if (this.this$0.a[i++] != array[n3++]) {
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
            if (o instanceof IntArrayList) {
                final IntArrayList list = (IntArrayList)o;
                return this.contentsEquals(list.a, 0, list.size());
            }
            if (o instanceof SubList) {
                final SubList list2 = (SubList)o;
                return this.contentsEquals(list2.getParentArray(), list2.from, list2.to);
            }
            return super.equals(o);
        }
        
        int contentsCompareTo(final int[] array, final int n, final int n2) {
            if (this.this$0.a == array && this.from == n && this.to == n2) {
                return 0;
            }
            int from = this.from;
            for (int n3 = n; from < this.to && from < n2; ++from, ++n3) {
                final int compare;
                if ((compare = Integer.compare(this.this$0.a[from], array[n3])) != 0) {
                    return compare;
                }
            }
            return (from < n2) ? -1 : (from < this.to);
        }
        
        @Override
        public int compareTo(final List list) {
            if (list instanceof IntArrayList) {
                final IntArrayList list2 = (IntArrayList)list;
                return this.contentsCompareTo(list2.a, 0, list2.size());
            }
            if (list instanceof SubList) {
                final SubList list3 = (SubList)list;
                return this.contentsCompareTo(list3.getParentArray(), list3.from, list3.to);
            }
            return super.compareTo(list);
        }
        
        @Override
        public java.util.Spliterator spliterator() {
            return this.spliterator();
        }
        
        @Override
        public ListIterator listIterator(final int n) {
            return this.listIterator(n);
        }
        
        @Override
        public int compareTo(final Object o) {
            return this.compareTo((List)o);
        }
        
        private final class SubListIterator extends IntIterators.AbstractIndexBasedListIterator
        {
            final SubList this$1;
            
            SubListIterator(final SubList this$1, final int n) {
                this.this$1 = this$1;
                super(0, n);
            }
            
            @Override
            protected final int get(final int n) {
                return this.this$1.this$0.a[this.this$1.from + n];
            }
            
            @Override
            protected final void add(final int n, final int n2) {
                this.this$1.add(n, n2);
            }
            
            @Override
            protected final void set(final int n, final int n2) {
                this.this$1.set(n, n2);
            }
            
            @Override
            protected final void remove(final int n) {
                this.this$1.removeInt(n);
            }
            
            @Override
            protected final int getMaxPos() {
                return this.this$1.to - this.this$1.from;
            }
            
            @Override
            public int nextInt() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                final int[] a = this.this$1.this$0.a;
                final int from = this.this$1.from;
                final int lastReturned = this.pos++;
                this.lastReturned = lastReturned;
                return a[from + lastReturned];
            }
            
            @Override
            public int previousInt() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                final int[] a = this.this$1.this$0.a;
                final int from = this.this$1.from;
                final int n = this.pos - 1;
                this.pos = n;
                this.lastReturned = n;
                return a[from + n];
            }
            
            @Override
            public void forEachRemaining(final IntConsumer intConsumer) {
                while (this.pos < this.this$1.to - this.this$1.from) {
                    final int[] a = this.this$1.this$0.a;
                    final int from = this.this$1.from;
                    final int lastReturned = this.pos++;
                    this.lastReturned = lastReturned;
                    intConsumer.accept(a[from + lastReturned]);
                }
            }
            
            @Override
            public void forEachRemaining(final Object o) {
                this.forEachRemaining((IntConsumer)o);
            }
        }
        
        private final class SubListSpliterator extends IntSpliterators.LateBindingSizeIndexBasedSpliterator
        {
            final SubList this$1;
            
            SubListSpliterator(final SubList this$1) {
                this.this$1 = this$1;
                super(this$1.from);
            }
            
            private SubListSpliterator(final SubList this$1, final int n, final int n2) {
                this.this$1 = this$1;
                super(n, n2);
            }
            
            @Override
            protected final int getMaxPosFromBackingStore() {
                return this.this$1.to;
            }
            
            @Override
            protected final int get(final int n) {
                return this.this$1.this$0.a[n];
            }
            
            @Override
            protected final SubListSpliterator makeForSplit(final int n, final int n2) {
                return this.this$1.new SubListSpliterator(n, n2);
            }
            
            @Override
            public boolean tryAdvance(final IntConsumer intConsumer) {
                if (this.pos >= this.getMaxPos()) {
                    return false;
                }
                intConsumer.accept(this.this$1.this$0.a[this.pos++]);
                return true;
            }
            
            @Override
            public void forEachRemaining(final IntConsumer intConsumer) {
                while (this.pos < this.getMaxPos()) {
                    intConsumer.accept(this.this$1.this$0.a[this.pos++]);
                }
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
    
    private final class Spliterator implements IntSpliterator
    {
        boolean hasSplit;
        int pos;
        int max;
        static final boolean $assertionsDisabled;
        final IntArrayList this$0;
        
        public Spliterator(final IntArrayList list) {
            this(list, 0, list.size, false);
        }
        
        private Spliterator(final IntArrayList this$0, final int pos, final int max, final boolean hasSplit) {
            this.this$0 = this$0;
            this.hasSplit = false;
            assert pos <= max : "pos " + pos + " must be <= max " + max;
            this.pos = pos;
            this.max = max;
            this.hasSplit = hasSplit;
        }
        
        private int getWorkingMax() {
            return this.hasSplit ? this.max : this.this$0.size;
        }
        
        @Override
        public int characteristics() {
            return 16720;
        }
        
        @Override
        public long estimateSize() {
            return this.getWorkingMax() - this.pos;
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            if (this.pos >= this.getWorkingMax()) {
                return false;
            }
            intConsumer.accept(this.this$0.a[this.pos++]);
            return true;
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            while (this.pos < this.getWorkingMax()) {
                intConsumer.accept(this.this$0.a[this.pos]);
                ++this.pos;
            }
        }
        
        @Override
        public long skip(long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            final int workingMax = this.getWorkingMax();
            if (this.pos >= workingMax) {
                return 0L;
            }
            final int n2 = workingMax - this.pos;
            if (n < n2) {
                this.pos = SafeMath.safeLongToInt(this.pos + n);
                return n;
            }
            n = n2;
            this.pos = workingMax;
            return n;
        }
        
        @Override
        public IntSpliterator trySplit() {
            final int workingMax = this.getWorkingMax();
            final int n = workingMax - this.pos >> 1;
            if (n <= 1) {
                return null;
            }
            this.max = workingMax;
            final int pos = this.pos + n;
            final int pos2 = this.pos;
            this.pos = pos;
            this.hasSplit = true;
            return this.this$0.new Spliterator(pos2, pos, true);
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
            $assertionsDisabled = !IntArrayList.class.desiredAssertionStatus();
        }
    }
}
