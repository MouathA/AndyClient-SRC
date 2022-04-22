package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.stream.*;
import java.util.function.*;
import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.*;

public class ObjectArrayList extends AbstractObjectList implements RandomAccess, Cloneable, Serializable
{
    private static final long serialVersionUID = -7046029254386353131L;
    public static final int DEFAULT_INITIAL_CAPACITY = 10;
    protected final boolean wrapped;
    protected transient Object[] a;
    protected int size;
    private static final Collector TO_LIST_COLLECTOR;
    static final boolean $assertionsDisabled;
    
    private static final Object[] copyArraySafe(final Object[] array, final int n) {
        if (n == 0) {
            return ObjectArrays.EMPTY_ARRAY;
        }
        return Arrays.copyOf(array, n, (Class<? extends Object[]>)Object[].class);
    }
    
    private static final Object[] copyArrayFromSafe(final ObjectArrayList list) {
        return copyArraySafe(list.a, list.size);
    }
    
    protected ObjectArrayList(final Object[] a, final boolean wrapped) {
        this.a = a;
        this.wrapped = wrapped;
    }
    
    private void initArrayFromCapacity(final int n) {
        if (n < 0) {
            throw new IllegalArgumentException("Initial capacity (" + n + ") is negative");
        }
        if (n == 0) {
            this.a = ObjectArrays.EMPTY_ARRAY;
        }
        else {
            this.a = new Object[n];
        }
    }
    
    public ObjectArrayList(final int n) {
        this.initArrayFromCapacity(n);
        this.wrapped = false;
    }
    
    public ObjectArrayList() {
        this.a = ObjectArrays.DEFAULT_EMPTY_ARRAY;
        this.wrapped = false;
    }
    
    public ObjectArrayList(final Collection collection) {
        if (collection instanceof ObjectArrayList) {
            this.a = copyArrayFromSafe((ObjectArrayList)collection);
            this.size = this.a.length;
        }
        else {
            this.initArrayFromCapacity(collection.size());
            if (collection instanceof ObjectList) {
                ((ObjectArrayList)collection).getElements(0, this.a, 0, this.size = collection.size());
            }
            else {
                this.size = ObjectIterators.unwrap(collection.iterator(), this.a);
            }
        }
        this.wrapped = false;
    }
    
    public ObjectArrayList(final ObjectCollection collection) {
        if (collection instanceof ObjectArrayList) {
            this.a = copyArrayFromSafe((ObjectArrayList)collection);
            this.size = this.a.length;
        }
        else {
            this.initArrayFromCapacity(collection.size());
            if (collection instanceof ObjectList) {
                ((ObjectList)collection).getElements(0, this.a, 0, this.size = collection.size());
            }
            else {
                this.size = ObjectIterators.unwrap(collection.iterator(), this.a);
            }
        }
        this.wrapped = false;
    }
    
    public ObjectArrayList(final ObjectList list) {
        if (list instanceof ObjectArrayList) {
            this.a = copyArrayFromSafe((ObjectArrayList)list);
            this.size = this.a.length;
        }
        else {
            this.initArrayFromCapacity(list.size());
            list.getElements(0, this.a, 0, this.size = list.size());
        }
        this.wrapped = false;
    }
    
    public ObjectArrayList(final Object[] array) {
        this(array, 0, array.length);
    }
    
    public ObjectArrayList(final Object[] array, final int n, final int size) {
        this(size);
        System.arraycopy(array, n, this.a, 0, size);
        this.size = size;
    }
    
    public ObjectArrayList(final Iterator iterator) {
        this();
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
    }
    
    public ObjectArrayList(final ObjectIterator objectIterator) {
        this();
        while (objectIterator.hasNext()) {
            this.add(objectIterator.next());
        }
    }
    
    public Object[] elements() {
        return this.a;
    }
    
    public static ObjectArrayList wrap(final Object[] array, final int size) {
        if (size > array.length) {
            throw new IllegalArgumentException("The specified length (" + size + ") is greater than the array size (" + array.length + ")");
        }
        final ObjectArrayList list = new ObjectArrayList(array, true);
        list.size = size;
        return list;
    }
    
    public static ObjectArrayList wrap(final Object[] array) {
        return wrap(array, array.length);
    }
    
    public static ObjectArrayList of() {
        return new ObjectArrayList();
    }
    
    @SafeVarargs
    public static ObjectArrayList of(final Object... array) {
        return wrap(array);
    }
    
    ObjectArrayList combine(final ObjectArrayList list) {
        this.addAll(list);
        return this;
    }
    
    public static Collector toList() {
        return ObjectArrayList.TO_LIST_COLLECTOR;
    }
    
    public static Collector toListWithExpectedSize(final int n) {
        if (n <= 10) {
            return toList();
        }
        return Collector.of(new ObjectCollections.SizeDecreasingSupplier(n, ObjectArrayList::lambda$toListWithExpectedSize$0), ObjectArrayList::add, ObjectArrayList::combine, new Collector.Characteristics[0]);
    }
    
    public void ensureCapacity(final int n) {
        if (n <= this.a.length || (this.a == ObjectArrays.DEFAULT_EMPTY_ARRAY && n <= 10)) {
            return;
        }
        if (this.wrapped) {
            this.a = ObjectArrays.ensureCapacity(this.a, n, this.size);
        }
        else if (n > this.a.length) {
            final Object[] a = new Object[n];
            System.arraycopy(this.a, 0, a, 0, this.size);
            this.a = a;
        }
        assert this.size <= this.a.length;
    }
    
    private void grow(int n) {
        if (10 <= this.a.length) {
            return;
        }
        if (this.a != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            n = (int)Math.max(Math.min(this.a.length + (long)(this.a.length >> 1), 2147483639L), 10);
        }
        else if (10 < 10) {}
        if (this.wrapped) {
            this.a = ObjectArrays.forceCapacity(this.a, 10, this.size);
        }
        else {
            final Object[] a = new Object[10];
            System.arraycopy(this.a, 0, a, 0, this.size);
            this.a = a;
        }
        assert this.size <= this.a.length;
    }
    
    @Override
    public void add(final int n, final Object o) {
        this.ensureIndex(n);
        this.grow(this.size + 1);
        if (n != this.size) {
            System.arraycopy(this.a, n, this.a, n + 1, this.size - n);
        }
        this.a[n] = o;
        ++this.size;
        assert this.size <= this.a.length;
    }
    
    @Override
    public boolean add(final Object o) {
        this.grow(this.size + 1);
        this.a[this.size++] = o;
        assert this.size <= this.a.length;
        return true;
    }
    
    @Override
    public Object get(final int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        return this.a[n];
    }
    
    @Override
    public int indexOf(final Object o) {
        while (0 < this.size) {
            if (Objects.equals(o, this.a[0])) {
                return 0;
            }
            int n = 0;
            ++n;
        }
        return -1;
    }
    
    @Override
    public int lastIndexOf(final Object o) {
        int size = this.size;
        while (size-- != 0) {
            if (Objects.equals(o, this.a[size])) {
                return size;
            }
        }
        return -1;
    }
    
    @Override
    public Object remove(final int n) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        final Object o = this.a[n];
        --this.size;
        if (n != this.size) {
            System.arraycopy(this.a, n + 1, this.a, n, this.size - n);
        }
        this.a[this.size] = null;
        assert this.size <= this.a.length;
        return o;
    }
    
    @Override
    public boolean remove(final Object o) {
        final int index = this.indexOf(o);
        if (index == -1) {
            return false;
        }
        this.remove(index);
        assert this.size <= this.a.length;
        return true;
    }
    
    @Override
    public Object set(final int n, final Object o) {
        if (n >= this.size) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.size + ")");
        }
        final Object o2 = this.a[n];
        this.a[n] = o;
        return o2;
    }
    
    @Override
    public void clear() {
        Arrays.fill(this.a, 0, this.size, null);
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
            this.a = ObjectArrays.forceCapacity(this.a, size, this.size);
        }
        if (size > this.size) {
            Arrays.fill(this.a, this.size, size, null);
        }
        else {
            Arrays.fill(this.a, size, this.size, null);
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
        final Object[] a = new Object[Math.max(n, this.size)];
        System.arraycopy(this.a, 0, a, 0, this.size);
        this.a = a;
        assert this.size <= this.a.length;
    }
    
    @Override
    public ObjectList subList(final int n, final int n2) {
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
    public void getElements(final int n, final Object[] array, final int n2, final int n3) {
        ObjectArrays.ensureOffsetLength(array, n2, n3);
        System.arraycopy(this.a, n, array, n2, n3);
    }
    
    @Override
    public void removeElements(final int n, final int n2) {
        com.viaversion.viaversion.libs.fastutil.Arrays.ensureFromTo(this.size, n, n2);
        System.arraycopy(this.a, n2, this.a, n, this.size - n2);
        this.size -= n2 - n;
        int n3 = n2 - n;
        while (n3-- != 0) {
            this.a[this.size + n3] = null;
        }
    }
    
    @Override
    public void addElements(final int n, final Object[] array, final int n2, final int n3) {
        this.ensureIndex(n);
        ObjectArrays.ensureOffsetLength(array, n2, n3);
        this.grow(this.size + n3);
        System.arraycopy(this.a, n, this.a, n + n3, this.size - n);
        System.arraycopy(array, n2, this.a, n, n3);
        this.size += n3;
    }
    
    @Override
    public void setElements(final int n, final Object[] array, final int n2, final int n3) {
        this.ensureIndex(n);
        ObjectArrays.ensureOffsetLength(array, n2, n3);
        if (n + n3 > this.size) {
            throw new IndexOutOfBoundsException("End index (" + (n + n3) + ") is greater than list size (" + this.size + ")");
        }
        System.arraycopy(array, n2, this.a, n, n3);
    }
    
    @Override
    public void forEach(final Consumer consumer) {
        while (0 < this.size) {
            consumer.accept(this.a[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public boolean addAll(int n, final Collection collection) {
        if (collection instanceof ObjectList) {
            return this.addAll(n, (ObjectList)collection);
        }
        this.ensureIndex(n);
        int size = collection.size();
        if (size == 0) {
            return false;
        }
        this.grow(this.size + size);
        System.arraycopy(this.a, n, this.a, n + size, this.size - n);
        final Iterator<Object> iterator = collection.iterator();
        this.size += size;
        while (size-- != 0) {
            this.a[n++] = iterator.next();
        }
        assert this.size <= this.a.length;
        return true;
    }
    
    @Override
    public boolean addAll(final int n, final ObjectList list) {
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
    public boolean removeAll(final Collection collection) {
        final Object[] a = this.a;
        while (0 < this.size) {
            if (!collection.contains(a[0])) {
                final Object[] array = a;
                final int n = 0;
                int n2 = 0;
                ++n2;
                array[n] = a[0];
            }
            int n3 = 0;
            ++n3;
        }
        Arrays.fill(a, 0, this.size, null);
        int n3 = (this.size != 0) ? 1 : 0;
        this.size = 0;
        return false;
    }
    
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.a, this.size(), (Class<? extends Object[]>)Object[].class);
    }
    
    @Override
    public Object[] toArray(Object[] array) {
        if (array == null) {
            array = new Object[this.size()];
        }
        else if (array.length < this.size()) {
            array = (Object[])Array.newInstance(array.getClass().getComponentType(), this.size());
        }
        System.arraycopy(this.a, 0, array, 0, this.size());
        if (array.length > this.size()) {
            array[this.size()] = null;
        }
        return array;
    }
    
    @Override
    public ObjectListIterator listIterator(final int n) {
        this.ensureIndex(n);
        return new ObjectListIterator(n) {
            int pos = this.val$index;
            int last = -1;
            final int val$index;
            final ObjectArrayList this$0;
            
            @Override
            public boolean hasNext() {
                return this.pos < this.this$0.size;
            }
            
            @Override
            public boolean hasPrevious() {
                return this.pos > 0;
            }
            
            @Override
            public Object next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                final Object[] a = this.this$0.a;
                final int last = this.pos++;
                this.last = last;
                return a[last];
            }
            
            @Override
            public Object previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                final Object[] a = this.this$0.a;
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
            public void add(final Object o) {
                this.this$0.add(this.pos++, o);
                this.last = -1;
            }
            
            @Override
            public void set(final Object o) {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.set(this.last, o);
            }
            
            @Override
            public void remove() {
                if (this.last == -1) {
                    throw new IllegalStateException();
                }
                this.this$0.remove(this.last);
                if (this.last < this.pos) {
                    --this.pos;
                }
                this.last = -1;
            }
            
            @Override
            public void forEachRemaining(final Consumer consumer) {
                while (this.pos < this.this$0.size) {
                    final Object[] a = this.this$0.a;
                    final int last = this.pos++;
                    this.last = last;
                    consumer.accept(a[last]);
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
        };
    }
    
    @Override
    public ObjectSpliterator spliterator() {
        return new Spliterator();
    }
    
    @Override
    public void sort(final Comparator comparator) {
        if (comparator == null) {
            ObjectArrays.stableSort(this.a, 0, this.size);
        }
        else {
            ObjectArrays.stableSort(this.a, 0, this.size, comparator);
        }
    }
    
    @Override
    public void unstableSort(final Comparator comparator) {
        if (comparator == null) {
            ObjectArrays.unstableSort(this.a, 0, this.size);
        }
        else {
            ObjectArrays.unstableSort(this.a, 0, this.size, comparator);
        }
    }
    
    public ObjectArrayList clone() {
        ObjectArrayList list;
        if (this.getClass() == ObjectArrayList.class) {
            list = new ObjectArrayList(copyArraySafe(this.a, this.size), false);
            list.size = this.size;
        }
        else {
            list = (ObjectArrayList)super.clone();
            list.a = copyArraySafe(this.a, this.size);
        }
        return list;
    }
    
    public boolean equals(final ObjectArrayList list) {
        if (list == this) {
            return true;
        }
        int size = this.size();
        if (size != list.size()) {
            return false;
        }
        final Object[] a = this.a;
        final Object[] a2 = list.a;
        if (a == a2 && size == list.size()) {
            return true;
        }
        while (size-- != 0) {
            if (!Objects.equals(a[size], a2[size])) {
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
        if (o instanceof ObjectArrayList) {
            return this.equals((ObjectArrayList)o);
        }
        if (o instanceof SubList) {
            return ((SubList)o).equals(this);
        }
        return super.equals(o);
    }
    
    public int compareTo(final ObjectArrayList list) {
        final int size = this.size();
        final int size2 = list.size();
        final Object[] a = this.a;
        final Object[] a2 = list.a;
        while (0 < size && 0 < size2) {
            final int compareTo;
            if ((compareTo = ((Comparable<Object>)a[0]).compareTo(a2[0])) != 0) {
                return compareTo;
            }
            int n = 0;
            ++n;
        }
        return (0 < size2) ? -1 : ((0 < size) ? 1 : 0);
    }
    
    @Override
    public int compareTo(final List list) {
        if (list instanceof ObjectArrayList) {
            return this.compareTo((ObjectArrayList)list);
        }
        if (list instanceof SubList) {
            return -((SubList)list).compareTo(this);
        }
        return super.compareTo(list);
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        while (0 < this.size) {
            objectOutputStream.writeObject(this.a[0]);
            int n = 0;
            ++n;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.a = new Object[this.size];
        while (0 < this.size) {
            this.a[0] = objectInputStream.readObject();
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
    
    private static ObjectArrayList lambda$toListWithExpectedSize$0(final int n) {
        return (n <= 10) ? new ObjectArrayList() : new ObjectArrayList(n);
    }
    
    static {
        $assertionsDisabled = !ObjectArrayList.class.desiredAssertionStatus();
        TO_LIST_COLLECTOR = Collector.of(ObjectArrayList::new, ObjectArrayList::add, ObjectArrayList::combine, new Collector.Characteristics[0]);
    }
    
    private class SubList extends ObjectRandomAccessSubList
    {
        private static final long serialVersionUID = -3185226345314976296L;
        final ObjectArrayList this$0;
        
        protected SubList(final ObjectArrayList this$0, final int n, final int n2) {
            super(this.this$0 = this$0, n, n2);
        }
        
        private Object[] getParentArray() {
            return this.this$0.a;
        }
        
        @Override
        public Object get(final int n) {
            this.ensureRestrictedIndex(n);
            return this.this$0.a[n + this.from];
        }
        
        @Override
        public ObjectListIterator listIterator(final int n) {
            return new SubListIterator(n);
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return new SubListSpliterator();
        }
        
        boolean contentsEquals(final Object[] array, final int n, final int n2) {
            if (this.this$0.a == array && this.from == n && this.to == n2) {
                return true;
            }
            if (n2 - n != this.size()) {
                return false;
            }
            int i = this.from;
            int n3 = n;
            while (i < this.to) {
                if (!Objects.equals(this.this$0.a[i++], array[n3++])) {
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
            if (o instanceof ObjectArrayList) {
                final ObjectArrayList list = (ObjectArrayList)o;
                return this.contentsEquals(list.a, 0, list.size());
            }
            if (o instanceof SubList) {
                final SubList list2 = (SubList)o;
                return this.contentsEquals(list2.getParentArray(), list2.from, list2.to);
            }
            return super.equals(o);
        }
        
        int contentsCompareTo(final Object[] array, final int n, final int n2) {
            int from = this.from;
            for (int n3 = n; from < this.to && from < n2; ++from, ++n3) {
                final int compareTo;
                if ((compareTo = ((Comparable<Object>)this.this$0.a[from]).compareTo(array[n3])) != 0) {
                    return compareTo;
                }
            }
            return (from < n2) ? -1 : (from < this.to);
        }
        
        @Override
        public int compareTo(final List list) {
            if (list instanceof ObjectArrayList) {
                final ObjectArrayList list2 = (ObjectArrayList)list;
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
        
        private final class SubListIterator extends ObjectIterators.AbstractIndexBasedListIterator
        {
            final SubList this$1;
            
            SubListIterator(final SubList this$1, final int n) {
                this.this$1 = this$1;
                super(0, n);
            }
            
            @Override
            protected final Object get(final int n) {
                return this.this$1.this$0.a[this.this$1.from + n];
            }
            
            @Override
            protected final void add(final int n, final Object o) {
                this.this$1.add(n, o);
            }
            
            @Override
            protected final void set(final int n, final Object o) {
                this.this$1.set(n, o);
            }
            
            @Override
            protected final void remove(final int n) {
                this.this$1.remove(n);
            }
            
            @Override
            protected final int getMaxPos() {
                return this.this$1.to - this.this$1.from;
            }
            
            @Override
            public Object next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                final Object[] a = this.this$1.this$0.a;
                final int from = this.this$1.from;
                final int lastReturned = this.pos++;
                this.lastReturned = lastReturned;
                return a[from + lastReturned];
            }
            
            @Override
            public Object previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                final Object[] a = this.this$1.this$0.a;
                final int from = this.this$1.from;
                final int n = this.pos - 1;
                this.pos = n;
                this.lastReturned = n;
                return a[from + n];
            }
            
            @Override
            public void forEachRemaining(final Consumer consumer) {
                while (this.pos < this.this$1.to - this.this$1.from) {
                    final Object[] a = this.this$1.this$0.a;
                    final int from = this.this$1.from;
                    final int lastReturned = this.pos++;
                    this.lastReturned = lastReturned;
                    consumer.accept(a[from + lastReturned]);
                }
            }
        }
        
        private final class SubListSpliterator extends ObjectSpliterators.LateBindingSizeIndexBasedSpliterator
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
            protected final Object get(final int n) {
                return this.this$1.this$0.a[n];
            }
            
            @Override
            protected final SubListSpliterator makeForSplit(final int n, final int n2) {
                return this.this$1.new SubListSpliterator(n, n2);
            }
            
            @Override
            public boolean tryAdvance(final Consumer consumer) {
                if (this.pos >= this.getMaxPos()) {
                    return false;
                }
                consumer.accept(this.this$1.this$0.a[this.pos++]);
                return true;
            }
            
            @Override
            public void forEachRemaining(final Consumer consumer) {
                while (this.pos < this.getMaxPos()) {
                    consumer.accept(this.this$1.this$0.a[this.pos++]);
                }
            }
            
            @Override
            protected ObjectSpliterator makeForSplit(final int n, final int n2) {
                return this.makeForSplit(n, n2);
            }
        }
    }
    
    private final class Spliterator implements ObjectSpliterator
    {
        boolean hasSplit;
        int pos;
        int max;
        static final boolean $assertionsDisabled;
        final ObjectArrayList this$0;
        
        public Spliterator(final ObjectArrayList list) {
            this(list, 0, list.size, false);
        }
        
        private Spliterator(final ObjectArrayList this$0, final int pos, final int max, final boolean hasSplit) {
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
            return 16464;
        }
        
        @Override
        public long estimateSize() {
            return this.getWorkingMax() - this.pos;
        }
        
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            if (this.pos >= this.getWorkingMax()) {
                return false;
            }
            consumer.accept(this.this$0.a[this.pos++]);
            return true;
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            while (this.pos < this.getWorkingMax()) {
                consumer.accept(this.this$0.a[this.pos]);
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
        public ObjectSpliterator trySplit() {
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
        public java.util.Spliterator trySplit() {
            return this.trySplit();
        }
        
        static {
            $assertionsDisabled = !ObjectArrayList.class.desiredAssertionStatus();
        }
    }
}
