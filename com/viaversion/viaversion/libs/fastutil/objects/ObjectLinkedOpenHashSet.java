package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.stream.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;
import java.io.*;
import java.util.*;

public class ObjectLinkedOpenHashSet extends AbstractObjectSortedSet implements Serializable, Cloneable, Hash
{
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient Object[] key;
    protected transient int mask;
    protected transient boolean containsNull;
    protected transient int first;
    protected transient int last;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    private static final Collector TO_SET_COLLECTOR;
    private static final int SPLITERATOR_CHARACTERISTICS = 81;
    
    public ObjectLinkedOpenHashSet(final int n, final float f) {
        this.first = -1;
        this.last = -1;
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
        this.link = new long[this.n + 1];
    }
    
    public ObjectLinkedOpenHashSet(final int n) {
        this(n, 0.75f);
    }
    
    public ObjectLinkedOpenHashSet() {
        this(16, 0.75f);
    }
    
    public ObjectLinkedOpenHashSet(final Collection collection, final float n) {
        this(collection.size(), n);
        this.addAll(collection);
    }
    
    public ObjectLinkedOpenHashSet(final Collection collection) {
        this(collection, 0.75f);
    }
    
    public ObjectLinkedOpenHashSet(final ObjectCollection collection, final float n) {
        this(collection.size(), n);
        this.addAll(collection);
    }
    
    public ObjectLinkedOpenHashSet(final ObjectCollection collection) {
        this(collection, 0.75f);
    }
    
    public ObjectLinkedOpenHashSet(final Iterator iterator, final float n) {
        this(16, n);
        while (iterator.hasNext()) {
            this.add(iterator.next());
        }
    }
    
    public ObjectLinkedOpenHashSet(final Iterator iterator) {
        this(iterator, 0.75f);
    }
    
    public ObjectLinkedOpenHashSet(final Object[] array, final int n, final int n2, final float n3) {
        this((n2 < 0) ? 0 : n2, n3);
        ObjectArrays.ensureOffsetLength(array, n, n2);
        while (0 < n2) {
            this.add(array[n + 0]);
            int n4 = 0;
            ++n4;
        }
    }
    
    public ObjectLinkedOpenHashSet(final Object[] array, final int n, final int n2) {
        this(array, n, n2, 0.75f);
    }
    
    public ObjectLinkedOpenHashSet(final Object[] array, final float n) {
        this(array, 0, array.length, n);
    }
    
    public ObjectLinkedOpenHashSet(final Object[] array) {
        this(array, 0.75f);
    }
    
    public static ObjectLinkedOpenHashSet of() {
        return new ObjectLinkedOpenHashSet();
    }
    
    public static ObjectLinkedOpenHashSet of(final Object o) {
        final ObjectLinkedOpenHashSet set = new ObjectLinkedOpenHashSet(1, 0.75f);
        set.add(o);
        return set;
    }
    
    public static ObjectLinkedOpenHashSet of(final Object o, final Object o2) {
        final ObjectLinkedOpenHashSet set = new ObjectLinkedOpenHashSet(2, 0.75f);
        set.add(o);
        if (!set.add(o2)) {
            throw new IllegalArgumentException("Duplicate element: " + o2);
        }
        return set;
    }
    
    public static ObjectLinkedOpenHashSet of(final Object o, final Object o2, final Object o3) {
        final ObjectLinkedOpenHashSet set = new ObjectLinkedOpenHashSet(3, 0.75f);
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
    public static ObjectLinkedOpenHashSet of(final Object... array) {
        final ObjectLinkedOpenHashSet set = new ObjectLinkedOpenHashSet(array.length, 0.75f);
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
    
    private ObjectLinkedOpenHashSet combine(final ObjectLinkedOpenHashSet set) {
        this.addAll(set);
        return this;
    }
    
    public static Collector toSet() {
        return ObjectLinkedOpenHashSet.TO_SET_COLLECTOR;
    }
    
    public static Collector toSetWithExpectedSize(final int n) {
        if (n <= 16) {
            return toSet();
        }
        return Collector.of(new ObjectCollections.SizeDecreasingSupplier(n, ObjectLinkedOpenHashSet::lambda$toSetWithExpectedSize$0), ObjectLinkedOpenHashSet::add, ObjectLinkedOpenHashSet::combine, new Collector.Characteristics[0]);
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
        int n;
        if (o == null) {
            if (this.containsNull) {
                return false;
            }
            n = this.n;
            this.containsNull = true;
        }
        else {
            final Object[] key = this.key;
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
        if (this.size == 0) {
            final int n2 = n;
            this.last = n2;
            this.first = n2;
            this.link[n] = -1L;
        }
        else {
            final long[] link = this.link;
            final int last = this.last;
            link[last] ^= ((this.link[this.last] ^ ((long)n & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            this.link[n] = (((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL);
            this.last = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return true;
    }
    
    public Object addOrGet(final Object o) {
        int n;
        if (o == null) {
            if (this.containsNull) {
                return this.key[this.n];
            }
            n = this.n;
            this.containsNull = true;
        }
        else {
            final Object[] key = this.key;
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
        if (this.size == 0) {
            final int n2 = n;
            this.last = n2;
            this.first = n2;
            this.link[n] = -1L;
        }
        else {
            final long[] link = this.link;
            final int last = this.last;
            link[last] ^= ((this.link[this.last] ^ ((long)n & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            this.link[n] = (((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL);
            this.last = n;
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
                this.fixPointers(n, n2);
                continue Label_0006;
            }
            break;
        }
        key[n2] = null;
    }
    
    private boolean removeEntry(final int n) {
        --this.size;
        this.fixPointers(n);
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
        this.fixPointers(this.n);
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
    
    public Object removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        final int first = this.first;
        this.first = (int)this.link[first];
        if (0 <= this.first) {
            final long[] link = this.link;
            final int first2 = this.first;
            link[first2] |= 0xFFFFFFFF00000000L;
        }
        final Object o = this.key[first];
        --this.size;
        if (o == null) {
            this.containsNull = false;
            this.key[this.n] = null;
        }
        else {
            this.shiftKeys(first);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return o;
    }
    
    public Object removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        final int last = this.last;
        this.last = (int)(this.link[last] >>> 32);
        if (0 <= this.last) {
            final long[] link = this.link;
            final int last2 = this.last;
            link[last2] |= 0xFFFFFFFFL;
        }
        final Object o = this.key[last];
        --this.size;
        if (o == null) {
            this.containsNull = false;
            this.key[this.n] = null;
        }
        else {
            this.shiftKeys(last);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return o;
    }
    
    private void moveIndexToFirst(final int first) {
        if (this.size == 1 || this.first == first) {
            return;
        }
        if (this.last == first) {
            this.last = (int)(this.link[first] >>> 32);
            final long[] link = this.link;
            final int last = this.last;
            link[last] |= 0xFFFFFFFFL;
        }
        else {
            final long n = this.link[first];
            final int n2 = (int)(n >>> 32);
            final int n3 = (int)n;
            final long[] link2 = this.link;
            final int n4 = n2;
            link2[n4] ^= ((this.link[n2] ^ (n & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            final long[] link3 = this.link;
            final int n5 = n3;
            link3[n5] ^= ((this.link[n3] ^ (n & 0xFFFFFFFF00000000L)) & 0xFFFFFFFF00000000L);
        }
        final long[] link4 = this.link;
        final int first2 = this.first;
        link4[first2] ^= ((this.link[this.first] ^ ((long)first & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
        this.link[first] = (0xFFFFFFFF00000000L | ((long)this.first & 0xFFFFFFFFL));
        this.first = first;
    }
    
    private void moveIndexToLast(final int last) {
        if (this.size == 1 || this.last == last) {
            return;
        }
        if (this.first == last) {
            this.first = (int)this.link[last];
            final long[] link = this.link;
            final int first = this.first;
            link[first] |= 0xFFFFFFFF00000000L;
        }
        else {
            final long n = this.link[last];
            final int n2 = (int)(n >>> 32);
            final int n3 = (int)n;
            final long[] link2 = this.link;
            final int n4 = n2;
            link2[n4] ^= ((this.link[n2] ^ (n & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            final long[] link3 = this.link;
            final int n5 = n3;
            link3[n5] ^= ((this.link[n3] ^ (n & 0xFFFFFFFF00000000L)) & 0xFFFFFFFF00000000L);
        }
        final long[] link4 = this.link;
        final int last2 = this.last;
        link4[last2] ^= ((this.link[this.last] ^ ((long)last & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
        this.link[last] = (((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL);
        this.last = last;
    }
    
    public boolean addAndMoveToFirst(final Object o) {
        int n;
        if (o == null) {
            if (this.containsNull) {
                this.moveIndexToFirst(this.n);
                return false;
            }
            this.containsNull = true;
            n = this.n;
        }
        else {
            Object[] key;
            for (key = this.key, n = (HashCommon.mix(o.hashCode()) & this.mask); key[n] != null; n = (n + 1 & this.mask)) {
                if (o.equals(key[n])) {
                    this.moveIndexToFirst(n);
                    return false;
                }
            }
        }
        this.key[n] = o;
        if (this.size == 0) {
            final int n2 = n;
            this.last = n2;
            this.first = n2;
            this.link[n] = -1L;
        }
        else {
            final long[] link = this.link;
            final int first = this.first;
            link[first] ^= ((this.link[this.first] ^ ((long)n & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            this.link[n] = (0xFFFFFFFF00000000L | ((long)this.first & 0xFFFFFFFFL));
            this.first = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return true;
    }
    
    public boolean addAndMoveToLast(final Object o) {
        int n;
        if (o == null) {
            if (this.containsNull) {
                this.moveIndexToLast(this.n);
                return false;
            }
            this.containsNull = true;
            n = this.n;
        }
        else {
            Object[] key;
            for (key = this.key, n = (HashCommon.mix(o.hashCode()) & this.mask); key[n] != null; n = (n + 1 & this.mask)) {
                if (o.equals(key[n])) {
                    this.moveIndexToLast(n);
                    return false;
                }
            }
        }
        this.key[n] = o;
        if (this.size == 0) {
            final int n2 = n;
            this.last = n2;
            this.first = n2;
            this.link[n] = -1L;
        }
        else {
            final long[] link = this.link;
            final int last = this.last;
            link[last] ^= ((this.link[this.last] ^ ((long)n & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            this.link[n] = (((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL);
            this.last = n;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return true;
    }
    
    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNull = false;
        Arrays.fill(this.key, null);
        final int n = -1;
        this.last = n;
        this.first = n;
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    protected void fixPointers(final int n) {
        if (this.size == 0) {
            final int n2 = -1;
            this.last = n2;
            this.first = n2;
            return;
        }
        if (this.first == n) {
            this.first = (int)this.link[n];
            if (0 <= this.first) {
                final long[] link = this.link;
                final int first = this.first;
                link[first] |= 0xFFFFFFFF00000000L;
            }
            return;
        }
        if (this.last == n) {
            this.last = (int)(this.link[n] >>> 32);
            if (0 <= this.last) {
                final long[] link2 = this.link;
                final int last = this.last;
                link2[last] |= 0xFFFFFFFFL;
            }
            return;
        }
        final long n3 = this.link[n];
        final int n4 = (int)(n3 >>> 32);
        final int n5 = (int)n3;
        final long[] link3 = this.link;
        final int n6 = n4;
        link3[n6] ^= ((this.link[n4] ^ (n3 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
        final long[] link4 = this.link;
        final int n7 = n5;
        link4[n7] ^= ((this.link[n5] ^ (n3 & 0xFFFFFFFF00000000L)) & 0xFFFFFFFF00000000L);
    }
    
    protected void fixPointers(final int n, final int n2) {
        if (this.size == 1) {
            this.last = n2;
            this.first = n2;
            this.link[n2] = -1L;
            return;
        }
        if (this.first == n) {
            this.first = n2;
            final long[] link = this.link;
            final int n3 = (int)this.link[n];
            link[n3] ^= ((this.link[(int)this.link[n]] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            this.link[n2] = this.link[n];
            return;
        }
        if (this.last == n) {
            this.last = n2;
            final long[] link2 = this.link;
            final int n4 = (int)(this.link[n] >>> 32);
            link2[n4] ^= ((this.link[(int)(this.link[n] >>> 32)] ^ ((long)n2 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            this.link[n2] = this.link[n];
            return;
        }
        final long n5 = this.link[n];
        final int n6 = (int)(n5 >>> 32);
        final int n7 = (int)n5;
        final long[] link3 = this.link;
        final int n8 = n6;
        link3[n8] ^= ((this.link[n6] ^ ((long)n2 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
        final long[] link4 = this.link;
        final int n9 = n7;
        link4[n9] ^= ((this.link[n7] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
        this.link[n2] = n5;
    }
    
    @Override
    public Object first() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }
    
    @Override
    public Object last() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }
    
    @Override
    public ObjectSortedSet tailSet(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ObjectSortedSet headSet(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public ObjectSortedSet subSet(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Comparator comparator() {
        return null;
    }
    
    @Override
    public ObjectListIterator iterator(final Object o) {
        return new SetIterator(o);
    }
    
    @Override
    public ObjectListIterator iterator() {
        return new SetIterator();
    }
    
    @Override
    public ObjectSpliterator spliterator() {
        return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this), 81);
    }
    
    @Override
    public void forEach(final Consumer consumer) {
        int i = this.first;
        while (i != -1) {
            final int n = i;
            i = (int)this.link[n];
            consumer.accept(this.key[n]);
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
        int first = this.first;
        final long[] link = this.link;
        final long[] link2 = new long[n + 1];
        this.first = -1;
        int size = this.size;
        while (size-- != 0) {
            int first2;
            if (key[first] == null) {
                first2 = n;
            }
            else {
                for (first2 = (HashCommon.mix(key[first].hashCode()) & mask); key2[first2] != null; first2 = (first2 + 1 & mask)) {}
            }
            key2[first2] = key[first];
            if (-1 != -1) {
                final long[] array = link2;
                final int n2 = -1;
                array[n2] ^= ((link2[-1] ^ ((long)first2 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
                final long[] array2 = link2;
                final int n3 = first2;
                array2[n3] ^= ((link2[first2] ^ ((long)(-1) & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            }
            else {
                link2[this.first = first2] = -1L;
            }
            first = (int)link[first];
        }
        this.link = link2;
        this.last = -1;
        if (-1 != -1) {
            final long[] array3 = link2;
            final int n4 = -1;
            array3[n4] |= 0xFFFFFFFFL;
        }
        this.n = n;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = key2;
    }
    
    public ObjectLinkedOpenHashSet clone() {
        final ObjectLinkedOpenHashSet set = (ObjectLinkedOpenHashSet)super.clone();
        set.key = this.key.clone();
        set.containsNull = this.containsNull;
        set.link = this.link.clone();
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
        final ObjectListIterator iterator = this.iterator();
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
        final long[] link = new long[this.n + 1];
        this.link = link;
        final long[] array2 = link;
        final int n = -1;
        this.last = n;
        this.first = n;
        int size = this.size;
        while (size-- != 0) {
            final Object object = objectInputStream.readObject();
            int n2;
            if (object == null) {
                n2 = this.n;
                this.containsNull = true;
            }
            else if (array[n2 = (HashCommon.mix(object.hashCode()) & this.mask)] != null) {
                while (array[n2 = (n2 + 1 & this.mask)] != null) {}
            }
            array[n2] = object;
            if (this.first != -1) {
                final long[] array3 = array2;
                final int n3 = -1;
                array3[n3] ^= ((array2[-1] ^ ((long)n2 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
                final long[] array4 = array2;
                final int n4 = n2;
                array4[n4] ^= ((array2[n2] ^ ((long)(-1) & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            }
            else {
                this.first = n2;
                final long[] array5 = array2;
                final int n5 = n2;
                array5[n5] |= 0xFFFFFFFF00000000L;
            }
        }
        this.last = -1;
        if (-1 != -1) {
            final long[] array6 = array2;
            final int n6 = -1;
            array6[n6] |= 0xFFFFFFFFL;
        }
    }
    
    private void checkTable() {
    }
    
    @Override
    public ObjectBidirectionalIterator iterator() {
        return this.iterator();
    }
    
    @Override
    public ObjectBidirectionalIterator iterator(final Object o) {
        return this.iterator(o);
    }
    
    @Override
    public ObjectIterator iterator() {
        return this.iterator();
    }
    
    @Override
    public Spliterator spliterator() {
        return this.spliterator();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    @Override
    public SortedSet tailSet(final Object o) {
        return this.tailSet(o);
    }
    
    @Override
    public SortedSet headSet(final Object o) {
        return this.headSet(o);
    }
    
    @Override
    public SortedSet subSet(final Object o, final Object o2) {
        return this.subSet(o, o2);
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    private static ObjectLinkedOpenHashSet lambda$toSetWithExpectedSize$0(final int n) {
        return (n <= 16) ? new ObjectLinkedOpenHashSet() : new ObjectLinkedOpenHashSet(n);
    }
    
    static {
        TO_SET_COLLECTOR = Collector.of(ObjectLinkedOpenHashSet::new, ObjectLinkedOpenHashSet::add, ObjectLinkedOpenHashSet::combine, new Collector.Characteristics[0]);
    }
    
    private final class SetIterator implements ObjectListIterator
    {
        int prev;
        int next;
        int curr;
        int index;
        final ObjectLinkedOpenHashSet this$0;
        
        SetIterator(final ObjectLinkedOpenHashSet this$0) {
            this.this$0 = this$0;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = this$0.first;
            this.index = 0;
        }
        
        SetIterator(final ObjectLinkedOpenHashSet this$0, final Object o) {
            this.this$0 = this$0;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (o == null) {
                if (this$0.containsNull) {
                    this.next = (int)this$0.link[this$0.n];
                    this.prev = this$0.n;
                    return;
                }
                throw new NoSuchElementException("The key " + o + " does not belong to this set.");
            }
            else {
                if (Objects.equals(this$0.key[this$0.last], o)) {
                    this.prev = this$0.last;
                    this.index = this$0.size;
                    return;
                }
                final Object[] key = this$0.key;
                for (int prev = HashCommon.mix(o.hashCode()) & this$0.mask; key[prev] != null; prev = (prev + 1 & this$0.mask)) {
                    if (key[prev].equals(o)) {
                        this.next = (int)this$0.link[prev];
                        this.prev = prev;
                        return;
                    }
                }
                throw new NoSuchElementException("The key " + o + " does not belong to this set.");
            }
        }
        
        @Override
        public Object next() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: if_icmpeq       12
            //     4: new             Ljava/util/NoSuchElementException;
            //     7: dup            
            //     8: invokespecial   java/util/NoSuchElementException.<init>:()V
            //    11: athrow         
            //    12: aload_0        
            //    13: aload_0        
            //    14: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.next:I
            //    17: putfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.curr:I
            //    20: aload_0        
            //    21: aload_0        
            //    22: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.this$0:Lcom/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet;
            //    25: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet.link:[J
            //    28: aload_0        
            //    29: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.curr:I
            //    32: laload         
            //    33: l2i            
            //    34: putfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.next:I
            //    37: aload_0        
            //    38: aload_0        
            //    39: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.curr:I
            //    42: putfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.prev:I
            //    45: aload_0        
            //    46: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.index:I
            //    49: iflt            62
            //    52: aload_0        
            //    53: dup            
            //    54: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.index:I
            //    57: iconst_1       
            //    58: iadd           
            //    59: putfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.index:I
            //    62: aload_0        
            //    63: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.this$0:Lcom/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet;
            //    66: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet.key:[Ljava/lang/Object;
            //    69: aload_0        
            //    70: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.curr:I
            //    73: aaload         
            //    74: areturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        @Override
        public Object previous() {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: if_icmpeq       12
            //     4: new             Ljava/util/NoSuchElementException;
            //     7: dup            
            //     8: invokespecial   java/util/NoSuchElementException.<init>:()V
            //    11: athrow         
            //    12: aload_0        
            //    13: aload_0        
            //    14: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.prev:I
            //    17: putfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.curr:I
            //    20: aload_0        
            //    21: aload_0        
            //    22: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.this$0:Lcom/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet;
            //    25: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet.link:[J
            //    28: aload_0        
            //    29: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.curr:I
            //    32: laload         
            //    33: bipush          32
            //    35: lushr          
            //    36: l2i            
            //    37: putfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.prev:I
            //    40: aload_0        
            //    41: aload_0        
            //    42: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.curr:I
            //    45: putfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.next:I
            //    48: aload_0        
            //    49: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.index:I
            //    52: iflt            65
            //    55: aload_0        
            //    56: dup            
            //    57: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.index:I
            //    60: iconst_1       
            //    61: isub           
            //    62: putfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.index:I
            //    65: aload_0        
            //    66: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.this$0:Lcom/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet;
            //    69: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet.key:[Ljava/lang/Object;
            //    72: aload_0        
            //    73: getfield        com/viaversion/viaversion/libs/fastutil/objects/ObjectLinkedOpenHashSet$SetIterator.curr:I
            //    76: aaload         
            //    77: areturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            final Object[] key = this.this$0.key;
            final long[] link = this.this$0.link;
            while (this.next != -1) {
                this.curr = this.next;
                this.next = (int)link[this.curr];
                this.prev = this.curr;
                if (this.index >= 0) {
                    ++this.index;
                }
                consumer.accept(key[this.curr]);
            }
        }
        
        private final void ensureIndexKnown() {
            if (this.index >= 0) {
                return;
            }
            if (this.prev == -1) {
                this.index = 0;
                return;
            }
            if (this.next == -1) {
                this.index = this.this$0.size;
                return;
            }
            int i = this.this$0.first;
            this.index = 1;
            while (i != this.prev) {
                i = (int)this.this$0.link[i];
                ++this.index;
            }
        }
        
        @Override
        public int nextIndex() {
            this.ensureIndexKnown();
            return this.index;
        }
        
        @Override
        public int previousIndex() {
            this.ensureIndexKnown();
            return this.index - 1;
        }
        
        @Override
        public void remove() {
            this.ensureIndexKnown();
            if (this.curr == -1) {
                throw new IllegalStateException();
            }
            if (this.curr == this.prev) {
                --this.index;
                this.prev = (int)(this.this$0.link[this.curr] >>> 32);
            }
            else {
                this.next = (int)this.this$0.link[this.curr];
            }
            final ObjectLinkedOpenHashSet this$0 = this.this$0;
            --this$0.size;
            if (this.prev == -1) {
                this.this$0.first = this.next;
            }
            else {
                final long[] link = this.this$0.link;
                final int prev = this.prev;
                link[prev] ^= ((this.this$0.link[this.prev] ^ ((long)this.next & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            }
            if (this.next == -1) {
                this.this$0.last = this.prev;
            }
            else {
                final long[] link2 = this.this$0.link;
                final int next = this.next;
                link2[next] ^= ((this.this$0.link[this.next] ^ ((long)this.prev & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            }
            int curr = this.curr;
            this.curr = -1;
            if (curr == this.this$0.n) {
                this.this$0.containsNull = false;
                this.this$0.key[this.this$0.n] = null;
                return;
            }
            final Object[] key = this.this$0.key;
            int n = 0;
        Label_0280:
            while (true) {
                curr = ((n = curr) + 1 & this.this$0.mask);
                Object o;
                while ((o = key[curr]) != null) {
                    final int n2 = HashCommon.mix(o.hashCode()) & this.this$0.mask;
                    Label_0373: {
                        if (n <= curr) {
                            if (n >= n2) {
                                break Label_0373;
                            }
                            if (n2 > curr) {
                                break Label_0373;
                            }
                        }
                        else if (n >= n2 && n2 > curr) {
                            break Label_0373;
                        }
                        curr = (curr + 1 & this.this$0.mask);
                        continue;
                    }
                    key[n] = o;
                    if (this.next == curr) {
                        this.next = n;
                    }
                    if (this.prev == curr) {
                        this.prev = n;
                    }
                    this.this$0.fixPointers(curr, n);
                    continue Label_0280;
                }
                break;
            }
            key[n] = null;
        }
    }
}
