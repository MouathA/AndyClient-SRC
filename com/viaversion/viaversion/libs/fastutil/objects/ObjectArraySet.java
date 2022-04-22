package com.viaversion.viaversion.libs.fastutil.objects;

import java.lang.reflect.*;
import java.io.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.fastutil.*;

public class ObjectArraySet extends AbstractObjectSet implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    private transient Object[] a;
    private int size;
    
    public ObjectArraySet(final Object[] a) {
        this.a = a;
        this.size = a.length;
    }
    
    public ObjectArraySet() {
        this.a = ObjectArrays.EMPTY_ARRAY;
    }
    
    public ObjectArraySet(final int n) {
        this.a = new Object[n];
    }
    
    public ObjectArraySet(final ObjectCollection collection) {
        this(collection.size());
        this.addAll(collection);
    }
    
    public ObjectArraySet(final Collection collection) {
        this(collection.size());
        this.addAll(collection);
    }
    
    public ObjectArraySet(final ObjectSet set) {
        this(set.size());
        final ObjectIterator iterator = set.iterator();
        while (iterator.hasNext()) {
            this.a[0] = iterator.next();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public ObjectArraySet(final Set set) {
        this(set.size());
        final Iterator<Object> iterator = set.iterator();
        while (iterator.hasNext()) {
            this.a[0] = iterator.next();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public ObjectArraySet(final Object[] a, final int size) {
        this.a = a;
        this.size = size;
        if (size > a.length) {
            throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the array size (" + a.length + ")");
        }
    }
    
    public static ObjectArraySet of() {
        return ofUnchecked();
    }
    
    public static ObjectArraySet of(final Object o) {
        return ofUnchecked(o);
    }
    
    @SafeVarargs
    public static ObjectArraySet of(final Object... array) {
        if (array.length == 2) {
            if (Objects.equals(array[0], array[1])) {
                throw new IllegalArgumentException("Duplicate element: " + array[1]);
            }
        }
        else if (array.length > 2) {
            ObjectOpenHashSet.of(array);
        }
        return ofUnchecked(array);
    }
    
    public static ObjectArraySet ofUnchecked() {
        return new ObjectArraySet();
    }
    
    @SafeVarargs
    public static ObjectArraySet ofUnchecked(final Object... array) {
        return new ObjectArraySet(array);
    }
    
    private int findKey(final Object o) {
        int size = this.size;
        while (size-- != 0) {
            if (Objects.equals(this.a[size], o)) {
                return size;
            }
        }
        return -1;
    }
    
    @Override
    public ObjectIterator iterator() {
        return new ObjectIterator() {
            int next = 0;
            final ObjectArraySet this$0;
            
            @Override
            public boolean hasNext() {
                return this.next < ObjectArraySet.access$000(this.this$0);
            }
            
            @Override
            public Object next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return ObjectArraySet.access$100(this.this$0)[this.next++];
            }
            
            @Override
            public void remove() {
                System.arraycopy(ObjectArraySet.access$100(this.this$0), this.next + 1, ObjectArraySet.access$100(this.this$0), this.next, ObjectArraySet.access$010(this.this$0) - this.next--);
                ObjectArraySet.access$100(this.this$0)[ObjectArraySet.access$000(this.this$0)] = null;
            }
            
            @Override
            public int skip(int n) {
                if (n < 0) {
                    throw new IllegalArgumentException("Argument must be nonnegative: " + n);
                }
                final int n2 = ObjectArraySet.access$000(this.this$0) - this.next;
                if (n < n2) {
                    this.next += n;
                    return n;
                }
                n = n2;
                this.next = ObjectArraySet.access$000(this.this$0);
                return n;
            }
        };
    }
    
    @Override
    public ObjectSpliterator spliterator() {
        return new Spliterator();
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.findKey(o) != -1;
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean remove(final Object o) {
        final int key = this.findKey(o);
        if (key == -1) {
            return false;
        }
        while (0 < this.size - key - 1) {
            this.a[key + 0] = this.a[key + 0 + 1];
            int n = 0;
            ++n;
        }
        --this.size;
        this.a[this.size] = null;
        return true;
    }
    
    @Override
    public boolean add(final Object o) {
        if (this.findKey(o) != -1) {
            return false;
        }
        if (this.size == this.a.length) {
            final Object[] a = new Object[(this.size == 0) ? 2 : (this.size * 2)];
            int size = this.size;
            while (size-- != 0) {
                a[size] = this.a[size];
            }
            this.a = a;
        }
        this.a[this.size++] = o;
        return true;
    }
    
    @Override
    public void clear() {
        Arrays.fill(this.a, 0, this.size, null);
        this.size = 0;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    @Override
    public Object[] toArray() {
        return Arrays.copyOf(this.a, this.size, (Class<? extends Object[]>)Object[].class);
    }
    
    @Override
    public Object[] toArray(Object[] array) {
        if (array == null) {
            array = new Object[this.size];
        }
        else if (array.length < this.size) {
            array = (Object[])Array.newInstance(array.getClass().getComponentType(), this.size);
        }
        System.arraycopy(this.a, 0, array, 0, this.size);
        if (array.length > this.size) {
            array[this.size] = null;
        }
        return array;
    }
    
    public ObjectArraySet clone() {
        final ObjectArraySet set = (ObjectArraySet)super.clone();
        set.a = this.a.clone();
        return set;
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
    public Iterator iterator() {
        return this.iterator();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static int access$000(final ObjectArraySet set) {
        return set.size;
    }
    
    static Object[] access$100(final ObjectArraySet set) {
        return set.a;
    }
    
    static int access$010(final ObjectArraySet set) {
        return set.size--;
    }
    
    private final class Spliterator implements ObjectSpliterator
    {
        boolean hasSplit;
        int pos;
        int max;
        static final boolean $assertionsDisabled;
        final ObjectArraySet this$0;
        
        public Spliterator(final ObjectArraySet set) {
            this(set, 0, ObjectArraySet.access$000(set), false);
        }
        
        private Spliterator(final ObjectArraySet this$0, final int pos, final int max, final boolean hasSplit) {
            this.this$0 = this$0;
            this.hasSplit = false;
            assert pos <= max : "pos " + pos + " must be <= max " + max;
            this.pos = pos;
            this.max = max;
            this.hasSplit = hasSplit;
        }
        
        private int getWorkingMax() {
            return this.hasSplit ? this.max : ObjectArraySet.access$000(this.this$0);
        }
        
        @Override
        public int characteristics() {
            return 16465;
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
            consumer.accept(ObjectArraySet.access$100(this.this$0)[this.pos++]);
            return true;
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            while (this.pos < this.getWorkingMax()) {
                consumer.accept(ObjectArraySet.access$100(this.this$0)[this.pos]);
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
            $assertionsDisabled = !ObjectArraySet.class.desiredAssertionStatus();
        }
    }
}
