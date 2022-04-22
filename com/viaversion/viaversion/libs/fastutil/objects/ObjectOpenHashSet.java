package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.stream.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;
import java.io.*;
import java.util.*;

public class ObjectOpenHashSet extends AbstractObjectSet implements Serializable, Cloneable, Hash
{
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient Object[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    private static final Collector TO_SET_COLLECTOR;
    
    public ObjectOpenHashSet(final int n, final float f) {
        if (f <= 0.0f || f >= 1.0f) {
            throw new IllegalArgumentException("Load factor must be greater than 0 and smaller than 1");
        }
        if (n < 0) {
            throw new IllegalArgumentException("The expected number of elements must be nonnegative");
        }
        this.f = f;
        final int arraySize = HashCommon.arraySize(n, f);
        this.n = arraySize;
        this.minN = arraySize;
        this.mask = this.n - 1;
        this.maxFill = HashCommon.maxFill(this.n, f);
        this.key = new Object[this.n + 1];
    }
    
    public ObjectOpenHashSet(final int n) {
        this(n, 0.75f);
    }
    
    public ObjectOpenHashSet() {
        this(16, 0.75f);
    }
    
    public ObjectOpenHashSet(final Collection collection, final float n) {
        this(collection.size(), n);
        this.addAll(collection);
    }
    
    public ObjectOpenHashSet(final Collection collection) {
        this(collection, 0.75f);
    }
    
    public ObjectOpenHashSet(final ObjectCollection collection, final float n) {
        this(collection.size(), n);
        this.addAll(collection);
    }
    
    public ObjectOpenHashSet(final ObjectCollection collection) {
        this(collection, 0.75f);
    }
    
    public ObjectOpenHashSet(final Iterator iterator, final float n) {
        this(16, n);
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
    }
    
    public ObjectOpenHashSet(final Iterator iterator) {
        this(iterator, 0.75f);
    }
    
    public ObjectOpenHashSet(final Object[] array, final int n, final int n2, final float n3) {
        this((n2 < 0) ? 0 : n2, n3);
        ObjectArrays.ensureOffsetLength(array, n, n2);
        while (0 < n2) {
            this.add(array[n + 0]);
            int n4 = 0;
            ++n4;
        }
    }
    
    public ObjectOpenHashSet(final Object[] array, final int n, final int n2) {
        this(array, n, n2, 0.75f);
    }
    
    public ObjectOpenHashSet(final Object[] array, final float n) {
        this(array, 0, array.length, n);
    }
    
    public ObjectOpenHashSet(final Object[] array) {
        this(array, 0.75f);
    }
    
    public static ObjectOpenHashSet of() {
        return new ObjectOpenHashSet();
    }
    
    public static ObjectOpenHashSet of(final Object o) {
        final ObjectOpenHashSet set = new ObjectOpenHashSet(1, 0.75f);
        set.add(o);
        return set;
    }
    
    public static ObjectOpenHashSet of(final Object o, final Object o2) {
        final ObjectOpenHashSet set = new ObjectOpenHashSet(2, 0.75f);
        set.add(o);
        if (!set.add(o2)) {
            throw new IllegalArgumentException("Duplicate element: " + o2);
        }
        return set;
    }
    
    public static ObjectOpenHashSet of(final Object o, final Object o2, final Object o3) {
        final ObjectOpenHashSet set = new ObjectOpenHashSet(3, 0.75f);
        set.add(o);
        if (!set.add(o2)) {
            throw new IllegalArgumentException("Duplicate element: " + o2);
        }
        if (!set.add(o3)) {
            throw new IllegalArgumentException("Duplicate element: " + o3);
        }
        return set;
    }
    
    @SafeVarargs
    public static ObjectOpenHashSet of(final Object... array) {
        final ObjectOpenHashSet set = new ObjectOpenHashSet(array.length, 0.75f);
        while (0 < array.length) {
            final Object o = array[0];
            if (!set.add(o)) {
                throw new IllegalArgumentException("Duplicate element " + o);
            }
            int n = 0;
            ++n;
        }
        return set;
    }
    
    private ObjectOpenHashSet combine(final ObjectOpenHashSet set) {
        this.addAll(set);
        return this;
    }
    
    public static Collector toSet() {
        return ObjectOpenHashSet.TO_SET_COLLECTOR;
    }
    
    public static Collector toSetWithExpectedSize(final int n) {
        if (n <= 16) {
            return toSet();
        }
        return Collector.of(new ObjectCollections.SizeDecreasingSupplier(n, ObjectOpenHashSet::lambda$toSetWithExpectedSize$0), ObjectOpenHashSet::add, ObjectOpenHashSet::combine, Collector.Characteristics.UNORDERED);
    }
    
    private int realSize() {
        return this.containsNull ? (this.size - 1) : this.size;
    }
    
    private void ensureCapacity(final int n) {
        final int arraySize = HashCommon.arraySize(n, this.f);
        if (arraySize > this.n) {
            this.rehash(arraySize);
        }
    }
    
    private void tryCapacity(final long n) {
        final int n2 = (int)Math.min(1073741824L, Math.max(2L, HashCommon.nextPowerOfTwo((long)Math.ceil(n / this.f))));
        if (n2 > this.n) {
            this.rehash(n2);
        }
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        if (this.f <= 0.5) {
            this.ensureCapacity(collection.size());
        }
        else {
            this.tryCapacity(this.size() + collection.size());
        }
        return super.addAll(collection);
    }
    
    @Override
    public boolean add(final Object o) {
        if (o == null) {
            if (this.containsNull) {
                return false;
            }
            this.containsNull = true;
        }
        else {
            final Object[] key = this.key;
            int n;
            final Object o2;
            if ((o2 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) != null) {
                if (o2.equals(o)) {
                    return false;
                }
                Object o3;
                while ((o3 = key[n = (n + 1 & this.mask)]) != null) {
                    if (o3.equals(o)) {
                        return false;
                    }
                }
            }
            key[n] = o;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return true;
    }
    
    public Object addOrGet(final Object o) {
        if (o == null) {
            if (this.containsNull) {
                return this.key[this.n];
            }
            this.containsNull = true;
        }
        else {
            final Object[] key = this.key;
            int n;
            final Object o2;
            if ((o2 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) != null) {
                if (o2.equals(o)) {
                    return o2;
                }
                Object o3;
                while ((o3 = key[n = (n + 1 & this.mask)]) != null) {
                    if (o3.equals(o)) {
                        return o3;
                    }
                }
            }
            key[n] = o;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return o;
    }
    
    protected final void shiftKeys(int n) {
        final Object[] key = this.key;
        int n2 = 0;
    Label_0006:
        while (true) {
            n = ((n2 = n) + 1 & this.mask);
            Object o;
            while ((o = key[n]) != null) {
                final int n3 = HashCommon.mix(o.hashCode()) & this.mask;
                Label_0090: {
                    if (n2 <= n) {
                        if (n2 >= n3) {
                            break Label_0090;
                        }
                        if (n3 > n) {
                            break Label_0090;
                        }
                    }
                    else if (n2 >= n3 && n3 > n) {
                        break Label_0090;
                    }
                    n = (n + 1 & this.mask);
                    continue;
                }
                key[n2] = o;
                continue Label_0006;
            }
            break;
        }
        key[n2] = null;
    }
    
    private boolean removeEntry(final int n) {
        --this.size;
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return true;
    }
    
    private boolean removeNullEntry() {
        this.containsNull = false;
        this.key[this.n] = null;
        --this.size;
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return true;
    }
    
    @Override
    public boolean remove(final Object o) {
        if (o == null) {
            return this.containsNull && this.removeNullEntry();
        }
        final Object[] key = this.key;
        int n;
        final Object o2;
        if ((o2 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
            return false;
        }
        if (o.equals(o2)) {
            return this.removeEntry(n);
        }
        Object o3;
        while ((o3 = key[n = (n + 1 & this.mask)]) != null) {
            if (o.equals(o3)) {
                return this.removeEntry(n);
            }
        }
        return false;
    }
    
    @Override
    public boolean contains(final Object o) {
        if (o == null) {
            return this.containsNull;
        }
        final Object[] key = this.key;
        int n;
        final Object o2;
        if ((o2 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
            return false;
        }
        if (o.equals(o2)) {
            return true;
        }
        Object o3;
        while ((o3 = key[n = (n + 1 & this.mask)]) != null) {
            if (o.equals(o3)) {
                return true;
            }
        }
        return false;
    }
    
    public Object get(final Object o) {
        if (o == null) {
            return this.key[this.n];
        }
        final Object[] key = this.key;
        int n;
        final Object o2;
        if ((o2 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
            return null;
        }
        if (o.equals(o2)) {
            return o2;
        }
        Object o3;
        while ((o3 = key[n = (n + 1 & this.mask)]) != null) {
            if (o.equals(o3)) {
                return o3;
            }
        }
        return null;
    }
    
    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, null);
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    @Override
    public ObjectIterator iterator() {
        return new SetIterator(null);
    }
    
    @Override
    public ObjectSpliterator spliterator() {
        return new SetSpliterator();
    }
    
    @Override
    public void forEach(final Consumer consumer) {
        if (this.containsNull) {
            consumer.accept(this.key[this.n]);
        }
        final Object[] key = this.key;
        int n = this.n;
        while (n-- != 0) {
            if (key[n] != null) {
                consumer.accept(key[n]);
            }
        }
    }
    
    public boolean trim() {
        return this.trim(this.size);
    }
    
    public boolean trim(final int n) {
        final int nextPowerOfTwo = HashCommon.nextPowerOfTwo((int)Math.ceil(n / this.f));
        if (nextPowerOfTwo >= this.n || this.size > HashCommon.maxFill(nextPowerOfTwo, this.f)) {
            return true;
        }
        this.rehash(nextPowerOfTwo);
        return true;
    }
    
    protected void rehash(final int n) {
        final Object[] key = this.key;
        final int mask = n - 1;
        final Object[] key2 = new Object[n + 1];
        int n2 = this.n;
        int realSize = this.realSize();
        while (realSize-- != 0) {
            while (key[--n2] == null) {}
            int n3;
            if (key2[n3 = (HashCommon.mix(key[n2].hashCode()) & mask)] != null) {
                while (key2[n3 = (n3 + 1 & mask)] != null) {}
            }
            key2[n3] = key[n2];
        }
        this.n = n;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = key2;
    }
    
    public ObjectOpenHashSet clone() {
        final ObjectOpenHashSet set = (ObjectOpenHashSet)super.clone();
        set.key = this.key.clone();
        set.containsNull = this.containsNull;
        return set;
    }
    
    @Override
    public int hashCode() {
        int realSize = this.realSize();
        while (realSize-- != 0) {
            int n = 0;
            while (this.key[0] == null) {
                ++n;
            }
            if (this != this.key[0]) {
                final int n2 = 0 + this.key[0].hashCode();
            }
            ++n;
        }
        return 0;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final ObjectIterator iterator = this.iterator();
        objectOutputStream.defaultWriteObject();
        int size = this.size;
        while (size-- != 0) {
            objectOutputStream.writeObject(iterator.next());
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        final Object[] key = new Object[this.n + 1];
        this.key = key;
        final Object[] array = key;
        int size = this.size;
        while (size-- != 0) {
            final Object object = objectInputStream.readObject();
            int n;
            if (object == null) {
                n = this.n;
                this.containsNull = true;
            }
            else if (array[n = (HashCommon.mix(object.hashCode()) & this.mask)] != null) {
                while (array[n = (n + 1 & this.mask)] != null) {}
            }
            array[n] = object;
        }
    }
    
    private void checkTable() {
    }
    
    @Override
    public Spliterator spliterator() {
        return this.spliterator();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    private static ObjectOpenHashSet lambda$toSetWithExpectedSize$0(final int n) {
        return (n <= 16) ? new ObjectOpenHashSet() : new ObjectOpenHashSet(n);
    }
    
    static int access$100(final ObjectOpenHashSet set) {
        return set.realSize();
    }
    
    static {
        TO_SET_COLLECTOR = Collector.of(ObjectOpenHashSet::new, ObjectOpenHashSet::add, ObjectOpenHashSet::combine, Collector.Characteristics.UNORDERED);
    }
    
    private final class SetIterator implements ObjectIterator
    {
        int pos;
        int last;
        int c;
        boolean mustReturnNull;
        ObjectArrayList wrapped;
        final ObjectOpenHashSet this$0;
        
        private SetIterator(final ObjectOpenHashSet this$0) {
            this.this$0 = this$0;
            this.pos = this.this$0.n;
            this.last = -1;
            this.c = this.this$0.size;
            this.mustReturnNull = this.this$0.containsNull;
        }
        
        @Override
        public Object next() {
            if (this != 0) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                return this.this$0.key[this.this$0.n];
            }
            final Object[] key = this.this$0.key;
            while (--this.pos >= 0) {
                if (key[this.pos] != null) {
                    final Object[] array = key;
                    final int pos = this.pos;
                    this.last = pos;
                    return array[pos];
                }
            }
            this.last = Integer.MIN_VALUE;
            return this.wrapped.get(-this.pos - 1);
        }
        
        private final void shiftKeys(int n) {
            final Object[] key = this.this$0.key;
            int n2 = 0;
        Label_0009:
            while (true) {
                n = ((n2 = n) + 1 & this.this$0.mask);
                Object o;
                while ((o = key[n]) != null) {
                    final int n3 = HashCommon.mix(o.hashCode()) & this.this$0.mask;
                    Label_0102: {
                        if (n2 <= n) {
                            if (n2 >= n3) {
                                break Label_0102;
                            }
                            if (n3 > n) {
                                break Label_0102;
                            }
                        }
                        else if (n2 >= n3 && n3 > n) {
                            break Label_0102;
                        }
                        n = (n + 1 & this.this$0.mask);
                        continue;
                    }
                    if (n < n2) {
                        if (this.wrapped == null) {
                            this.wrapped = new ObjectArrayList(2);
                        }
                        this.wrapped.add(key[n]);
                    }
                    key[n2] = o;
                    continue Label_0009;
                }
                break;
            }
            key[n2] = null;
        }
        
        @Override
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNull = false;
                this.this$0.key[this.this$0.n] = null;
            }
            else {
                if (this.pos < 0) {
                    this.this$0.remove(this.wrapped.set(-this.pos - 1, null));
                    this.last = -1;
                    return;
                }
                this.shiftKeys(this.last);
            }
            final ObjectOpenHashSet this$0 = this.this$0;
            --this$0.size;
            this.last = -1;
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            final Object[] key = this.this$0.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                this.last = this.this$0.n;
                consumer.accept(key[this.this$0.n]);
                --this.c;
            }
            while (this.c != 0) {
                if (--this.pos < 0) {
                    this.last = Integer.MIN_VALUE;
                    consumer.accept(this.wrapped.get(-this.pos - 1));
                    --this.c;
                }
                else {
                    if (key[this.pos] == null) {
                        continue;
                    }
                    final Object[] array = key;
                    final int pos = this.pos;
                    this.last = pos;
                    consumer.accept(array[pos]);
                    --this.c;
                }
            }
        }
        
        SetIterator(final ObjectOpenHashSet set, final ObjectOpenHashSet$1 object) {
            this(set);
        }
    }
    
    private final class SetSpliterator implements ObjectSpliterator
    {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        int pos;
        int max;
        int c;
        boolean mustReturnNull;
        boolean hasSplit;
        final ObjectOpenHashSet this$0;
        
        SetSpliterator(final ObjectOpenHashSet this$0) {
            this.this$0 = this$0;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNull;
            this.hasSplit = false;
        }
        
        SetSpliterator(final ObjectOpenHashSet this$0, final int pos, final int max, final boolean mustReturnNull, final boolean hasSplit) {
            this.this$0 = this$0;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNull;
            this.hasSplit = false;
            this.pos = pos;
            this.max = max;
            this.mustReturnNull = mustReturnNull;
            this.hasSplit = hasSplit;
        }
        
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++this.c;
                consumer.accept(this.this$0.key[this.this$0.n]);
                return true;
            }
            final Object[] key = this.this$0.key;
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
                    ++this.c;
                    consumer.accept(key[this.pos++]);
                    return true;
                }
                ++this.pos;
            }
            return false;
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            final Object[] key = this.this$0.key;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                consumer.accept(key[this.this$0.n]);
                ++this.c;
            }
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
                    consumer.accept(key[this.pos]);
                    ++this.c;
                }
                ++this.pos;
            }
        }
        
        @Override
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }
        
        @Override
        public long estimateSize() {
            if (!this.hasSplit) {
                return this.this$0.size - this.c;
            }
            return Math.min(this.this$0.size - this.c, (long)(ObjectOpenHashSet.access$100(this.this$0) / (double)this.this$0.n * (this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
        }
        
        @Override
        public SetSpliterator trySplit() {
            if (this.pos >= this.max - 1) {
                return null;
            }
            final int n = this.max - this.pos >> 1;
            if (n <= 1) {
                return null;
            }
            final int pos = this.pos + n;
            final SetSpliterator setSpliterator = this.this$0.new SetSpliterator(this.pos, pos, this.mustReturnNull, true);
            this.pos = pos;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return setSpliterator;
        }
        
        @Override
        public long skip(long n) {
            if (n < 0L) {
                throw new IllegalArgumentException("Argument must be nonnegative: " + n);
            }
            if (n == 0L) {
                return 0L;
            }
            long n2 = 0L;
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++n2;
                --n;
            }
            final Object[] key = this.this$0.key;
            while (this.pos < this.max && n > 0L) {
                if (key[this.pos++] != null) {
                    ++n2;
                    --n;
                }
            }
            return n2;
        }
        
        @Override
        public ObjectSpliterator trySplit() {
            return this.trySplit();
        }
        
        @Override
        public Spliterator trySplit() {
            return this.trySplit();
        }
    }
}
