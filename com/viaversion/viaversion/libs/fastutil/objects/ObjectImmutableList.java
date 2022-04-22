package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.stream.*;
import java.util.function.*;
import java.lang.reflect.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;

public class ObjectImmutableList extends ObjectLists.ImmutableListBase implements ObjectList, RandomAccess, Cloneable, Serializable
{
    private static final long serialVersionUID = 0L;
    static final ObjectImmutableList EMPTY;
    private final Object[] a;
    private static final Collector TO_LIST_COLLECTOR;
    
    private static final Object[] emptyArray() {
        return ObjectArrays.EMPTY_ARRAY;
    }
    
    public ObjectImmutableList(final Object[] a) {
        this.a = a;
    }
    
    public ObjectImmutableList(final Collection collection) {
        this(collection.isEmpty() ? emptyArray() : ObjectIterators.unwrap(collection.iterator()));
    }
    
    public ObjectImmutableList(final ObjectCollection collection) {
        this(collection.isEmpty() ? emptyArray() : ObjectIterators.unwrap(collection.iterator()));
    }
    
    public ObjectImmutableList(final ObjectList list) {
        this(list.isEmpty() ? emptyArray() : new Object[list.size()]);
        list.getElements(0, this.a, 0, list.size());
    }
    
    public ObjectImmutableList(final Object[] array, final int n, final int n2) {
        this((n2 == 0) ? emptyArray() : new Object[n2]);
        System.arraycopy(array, n, this.a, 0, n2);
    }
    
    public ObjectImmutableList(final ObjectIterator objectIterator) {
        this(objectIterator.hasNext() ? ObjectIterators.unwrap(objectIterator) : emptyArray());
    }
    
    public static ObjectImmutableList of() {
        return ObjectImmutableList.EMPTY;
    }
    
    @SafeVarargs
    public static ObjectImmutableList of(final Object... array) {
        return (array.length == 0) ? of() : new ObjectImmutableList(array);
    }
    
    private static ObjectImmutableList convertTrustedToImmutableList(final ObjectArrayList list) {
        if (list.isEmpty()) {
            return of();
        }
        Object[] array = list.elements();
        if (list.size() != array.length) {
            array = Arrays.copyOf(array, list.size());
        }
        return new ObjectImmutableList(array);
    }
    
    public static Collector toList() {
        return ObjectImmutableList.TO_LIST_COLLECTOR;
    }
    
    public static Collector toListWithExpectedSize(final int n) {
        if (n <= 10) {
            return toList();
        }
        return Collector.of(new ObjectCollections.SizeDecreasingSupplier(n, ObjectImmutableList::lambda$toListWithExpectedSize$0), ObjectArrayList::add, ObjectArrayList::combine, ObjectImmutableList::convertTrustedToImmutableList, new Collector.Characteristics[0]);
    }
    
    @Override
    public Object get(final int n) {
        if (n >= this.a.length) {
            throw new IndexOutOfBoundsException("Index (" + n + ") is greater than or equal to list size (" + this.a.length + ")");
        }
        return this.a[n];
    }
    
    @Override
    public int indexOf(final Object o) {
        while (0 < this.a.length) {
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
        int length = this.a.length;
        while (length-- != 0) {
            if (Objects.equals(o, this.a[length])) {
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
    public void getElements(final int n, final Object[] array, final int n2, final int n3) {
        ObjectArrays.ensureOffsetLength(array, n2, n3);
        System.arraycopy(this.a, n, array, n2, n3);
    }
    
    @Override
    public void forEach(final Consumer consumer) {
        while (0 < this.a.length) {
            consumer.accept(this.a[0]);
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public Object[] toArray() {
        if (this.a.getClass().equals(Object[].class)) {
            return this.a.clone();
        }
        return Arrays.copyOf(this.a, this.a.length, (Class<? extends Object[]>)Object[].class);
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
            final int val$index;
            final ObjectImmutableList this$0;
            
            @Override
            public boolean hasNext() {
                return this.pos < ObjectImmutableList.access$000(this.this$0).length;
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
                return ObjectImmutableList.access$000(this.this$0)[this.pos++];
            }
            
            @Override
            public Object previous() {
                if (!this.hasPrevious()) {
                    throw new NoSuchElementException();
                }
                final Object[] access$000 = ObjectImmutableList.access$000(this.this$0);
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
            public void forEachRemaining(final Consumer consumer) {
                while (this.pos < ObjectImmutableList.access$000(this.this$0).length) {
                    consumer.accept(ObjectImmutableList.access$000(this.this$0)[this.pos++]);
                }
            }
            
            @Override
            public void add(final Object o) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public void set(final Object o) {
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
                final int n2 = ObjectImmutableList.access$000(this.this$0).length - this.pos;
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
                final int n2 = ObjectImmutableList.access$000(this.this$0).length - this.pos;
                if (n < n2) {
                    this.pos += n;
                }
                else {
                    n = n2;
                    this.pos = ObjectImmutableList.access$000(this.this$0).length;
                }
                return n;
            }
        };
    }
    
    @Override
    public ObjectSpliterator spliterator() {
        return new Spliterator();
    }
    
    @Override
    public ObjectList subList(final int n, final int n2) {
        if (n == 0 && n2 == this.size()) {
            return this;
        }
        this.ensureIndex(n);
        this.ensureIndex(n2);
        if (n == n2) {
            return ObjectImmutableList.EMPTY;
        }
        if (n > n2) {
            throw new IllegalArgumentException("Start index (" + n + ") is greater than end index (" + n2 + ")");
        }
        return new ImmutableSubList(this, n, n2);
    }
    
    public ObjectImmutableList clone() {
        return this;
    }
    
    public boolean equals(final ObjectImmutableList list) {
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
        if (o instanceof ObjectImmutableList) {
            return this.equals((ObjectImmutableList)o);
        }
        if (o instanceof ImmutableSubList) {
            return ((ImmutableSubList)o).equals(this);
        }
        return super.equals(o);
    }
    
    public int compareTo(final ObjectImmutableList list) {
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
        if (list instanceof ObjectImmutableList) {
            return this.compareTo((ObjectImmutableList)list);
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
    
    private static ObjectArrayList lambda$toListWithExpectedSize$0(final int n) {
        return (n <= 10) ? new ObjectArrayList() : new ObjectArrayList(n);
    }
    
    static Object[] access$000(final ObjectImmutableList list) {
        return list.a;
    }
    
    static {
        EMPTY = new ObjectImmutableList(ObjectArrays.EMPTY_ARRAY);
        TO_LIST_COLLECTOR = Collector.of(ObjectArrayList::new, ObjectArrayList::add, ObjectArrayList::combine, ObjectImmutableList::convertTrustedToImmutableList, new Collector.Characteristics[0]);
    }
    
    private final class Spliterator implements ObjectSpliterator
    {
        int pos;
        int max;
        static final boolean $assertionsDisabled;
        final ObjectImmutableList this$0;
        
        public Spliterator(final ObjectImmutableList list) {
            this(list, 0, ObjectImmutableList.access$000(list).length);
        }
        
        private Spliterator(final ObjectImmutableList this$0, final int pos, final int max) {
            this.this$0 = this$0;
            assert pos <= max : "pos " + pos + " must be <= max " + max;
            this.pos = pos;
            this.max = max;
        }
        
        @Override
        public int characteristics() {
            return 17488;
        }
        
        @Override
        public long estimateSize() {
            return this.max - this.pos;
        }
        
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            if (this.pos >= this.max) {
                return false;
            }
            consumer.accept(ObjectImmutableList.access$000(this.this$0)[this.pos++]);
            return true;
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            while (this.pos < this.max) {
                consumer.accept(ObjectImmutableList.access$000(this.this$0)[this.pos]);
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
        public ObjectSpliterator trySplit() {
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
        public java.util.Spliterator trySplit() {
            return this.trySplit();
        }
        
        static {
            $assertionsDisabled = !ObjectImmutableList.class.desiredAssertionStatus();
        }
    }
    
    private static final class ImmutableSubList extends ObjectLists.ImmutableListBase implements RandomAccess, Serializable
    {
        private static final long serialVersionUID = 7054639518438982401L;
        final ObjectImmutableList innerList;
        final int from;
        final int to;
        final transient Object[] a;
        
        ImmutableSubList(final ObjectImmutableList innerList, final int from, final int to) {
            this.innerList = innerList;
            this.from = from;
            this.to = to;
            this.a = ObjectImmutableList.access$000(innerList);
        }
        
        @Override
        public Object get(final int n) {
            this.ensureRestrictedIndex(n);
            return this.a[n + this.from];
        }
        
        @Override
        public int indexOf(final Object o) {
            for (int i = this.from; i < this.to; ++i) {
                if (Objects.equals(o, this.a[i])) {
                    return i - this.from;
                }
            }
            return -1;
        }
        
        @Override
        public int lastIndexOf(final Object o) {
            int to = this.to;
            while (to-- != this.from) {
                if (Objects.equals(o, this.a[to])) {
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
        public void getElements(final int n, final Object[] array, final int n2, final int n3) {
            ObjectArrays.ensureOffsetLength(array, n2, n3);
            this.ensureRestrictedIndex(n);
            if (this.from + n3 > this.to) {
                throw new IndexOutOfBoundsException("Final index " + (this.from + n3) + " (startingIndex: " + this.from + " + length: " + n3 + ") is greater then list length " + this.size());
            }
            System.arraycopy(this.a, n + this.from, array, n2, n3);
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            for (int i = this.from; i < this.to; ++i) {
                consumer.accept(this.a[i]);
            }
        }
        
        @Override
        public Object[] toArray() {
            return Arrays.copyOfRange(this.a, this.from, this.to, (Class<? extends Object[]>)Object[].class);
        }
        
        @Override
        public Object[] toArray(Object[] array) {
            final int size = this.size();
            if (array == null) {
                array = new Object[size];
            }
            else if (array.length < size) {
                array = (Object[])Array.newInstance(array.getClass().getComponentType(), size);
            }
            System.arraycopy(this.a, this.from, array, 0, size);
            if (array.length > size) {
                array[size] = null;
            }
            return array;
        }
        
        @Override
        public ObjectListIterator listIterator(final int n) {
            this.ensureIndex(n);
            return new ObjectListIterator(n) {
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
                public Object next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return this.this$0.a[this.pos++ + this.this$0.from];
                }
                
                @Override
                public Object previous() {
                    if (!this.hasPrevious()) {
                        throw new NoSuchElementException();
                    }
                    final Object[] a = this.this$0.a;
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
                public void forEachRemaining(final Consumer consumer) {
                    while (this.pos < this.this$0.to) {
                        consumer.accept(this.this$0.a[this.pos++ + this.this$0.from]);
                    }
                }
                
                @Override
                public void add(final Object o) {
                    throw new UnsupportedOperationException();
                }
                
                @Override
                public void set(final Object o) {
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
            };
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return new SubListSpliterator();
        }
        
        boolean contentsEquals(final Object[] array, final int n, final int n2) {
            if (this.a == array && this.from == n && this.to == n2) {
                return true;
            }
            if (n2 - n != this.size()) {
                return false;
            }
            int i = this.from;
            int n3 = n;
            while (i < this.to) {
                if (!Objects.equals(this.a[i++], array[n3++])) {
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
            if (o instanceof ObjectImmutableList) {
                final ObjectImmutableList list = (ObjectImmutableList)o;
                return this.contentsEquals(ObjectImmutableList.access$000(list), 0, list.size());
            }
            if (o instanceof ImmutableSubList) {
                final ImmutableSubList list2 = (ImmutableSubList)o;
                return this.contentsEquals(list2.a, list2.from, list2.to);
            }
            return super.equals(o);
        }
        
        int contentsCompareTo(final Object[] array, final int n, final int n2) {
            int from = this.from;
            for (int n3 = n; from < this.to && from < n2; ++from, ++n3) {
                final int compareTo;
                if ((compareTo = ((Comparable<Object>)this.a[from]).compareTo(array[n3])) != 0) {
                    return compareTo;
                }
            }
            return (from < n2) ? -1 : (from < this.to);
        }
        
        @Override
        public int compareTo(final List list) {
            if (list instanceof ObjectImmutableList) {
                final ObjectImmutableList list2 = (ObjectImmutableList)list;
                return this.contentsCompareTo(ObjectImmutableList.access$000(list2), 0, list2.size());
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
        public ObjectList subList(final int n, final int n2) {
            this.ensureIndex(n);
            this.ensureIndex(n2);
            if (n == n2) {
                return ObjectImmutableList.EMPTY;
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
        
        private final class SubListSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator
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
            protected final Object get(final int n) {
                return this.this$0.a[n];
            }
            
            @Override
            protected final SubListSpliterator makeForSplit(final int n, final int n2) {
                return this.this$0.new SubListSpliterator(n, n2);
            }
            
            @Override
            public boolean tryAdvance(final Consumer consumer) {
                if (this.pos >= this.maxPos) {
                    return false;
                }
                consumer.accept(this.this$0.a[this.pos++]);
                return true;
            }
            
            @Override
            public void forEachRemaining(final Consumer consumer) {
                while (this.pos < this.maxPos) {
                    consumer.accept(this.this$0.a[this.pos++]);
                }
            }
            
            @Override
            public int characteristics() {
                return 17488;
            }
            
            @Override
            protected ObjectSpliterator makeForSplit(final int n, final int n2) {
                return this.makeForSplit(n, n2);
            }
        }
    }
}
