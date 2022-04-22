package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;
import java.util.function.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class Object2IntLinkedOpenHashMap extends AbstractObject2IntSortedMap implements Serializable, Cloneable, Hash
{
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient Object[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int first;
    protected transient int last;
    protected transient long[] link;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Object2IntSortedMap.FastSortedEntrySet entries;
    protected transient ObjectSortedSet keys;
    protected transient IntCollection values;
    
    public Object2IntLinkedOpenHashMap(final int n, final float f) {
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
        this.value = new int[this.n + 1];
        this.link = new long[this.n + 1];
    }
    
    public Object2IntLinkedOpenHashMap(final int n) {
        this(n, 0.75f);
    }
    
    public Object2IntLinkedOpenHashMap() {
        this(16, 0.75f);
    }
    
    public Object2IntLinkedOpenHashMap(final Map map, final float n) {
        this(map.size(), n);
        this.putAll(map);
    }
    
    public Object2IntLinkedOpenHashMap(final Map map) {
        this(map, 0.75f);
    }
    
    public Object2IntLinkedOpenHashMap(final Object2IntMap object2IntMap, final float n) {
        this(object2IntMap.size(), n);
        this.putAll(object2IntMap);
    }
    
    public Object2IntLinkedOpenHashMap(final Object2IntMap object2IntMap) {
        this(object2IntMap, 0.75f);
    }
    
    public Object2IntLinkedOpenHashMap(final Object[] array, final int[] array2, final float n) {
        this(array.length, n);
        if (array.length != array2.length) {
            throw new IllegalArgumentException("The key array and the value array have different lengths (" + array.length + " and " + array2.length + ")");
        }
        while (0 < array.length) {
            this.put(array[0], array2[0]);
            int n2 = 0;
            ++n2;
        }
    }
    
    public Object2IntLinkedOpenHashMap(final Object[] array, final int[] array2) {
        this(array, array2, 0.75f);
    }
    
    private int realSize() {
        return this.containsNullKey ? (this.size - 1) : this.size;
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
    
    private int removeEntry(final int n) {
        final int n2 = this.value[n];
        --this.size;
        this.fixPointers(n);
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n2;
    }
    
    private int removeNullEntry() {
        this.containsNullKey = false;
        this.key[this.n] = null;
        final int n = this.value[this.n];
        --this.size;
        this.fixPointers(this.n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n;
    }
    
    @Override
    public void putAll(final Map map) {
        if (this.f <= 0.5) {
            this.ensureCapacity(map.size());
        }
        else {
            this.tryCapacity(this.size() + map.size());
        }
        super.putAll(map);
    }
    
    private int find(final Object o) {
        if (o == null) {
            return this.containsNullKey ? this.n : (-(this.n + 1));
        }
        final Object[] key = this.key;
        int n;
        final Object o2;
        if ((o2 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
            return -(n + 1);
        }
        if (o.equals(o2)) {
            return n;
        }
        Object o3;
        while ((o3 = key[n = (n + 1 & this.mask)]) != null) {
            if (o.equals(o3)) {
                return n;
            }
        }
        return -(n + 1);
    }
    
    private void insert(final int last, final Object o, final int n) {
        if (last == this.n) {
            this.containsNullKey = true;
        }
        this.key[last] = o;
        this.value[last] = n;
        if (this.size == 0) {
            this.last = last;
            this.first = last;
            this.link[last] = -1L;
        }
        else {
            final long[] link = this.link;
            final int last2 = this.last;
            link[last2] ^= ((this.link[this.last] ^ ((long)last & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            this.link[last] = (((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL);
            this.last = last;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }
    
    @Override
    public int put(final Object o, final int n) {
        final int find = this.find(o);
        if (find < 0) {
            this.insert(-find - 1, o, n);
            return this.defRetValue;
        }
        final int n2 = this.value[find];
        this.value[find] = n;
        return n2;
    }
    
    private int addToValue(final int n, final int n2) {
        final int n3 = this.value[n];
        this.value[n] = n3 + n2;
        return n3;
    }
    
    public int addTo(final Object o, final int n) {
        int n2;
        if (o == null) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, n);
            }
            n2 = this.n;
            this.containsNullKey = true;
        }
        else {
            final Object[] key = this.key;
            final Object o2;
            if ((o2 = key[n2 = (HashCommon.mix(o.hashCode()) & this.mask)]) != null) {
                if (o2.equals(o)) {
                    return this.addToValue(n2, n);
                }
                Object o3;
                while ((o3 = key[n2 = (n2 + 1 & this.mask)]) != null) {
                    if (o3.equals(o)) {
                        return this.addToValue(n2, n);
                    }
                }
            }
        }
        this.key[n2] = o;
        this.value[n2] = this.defRetValue + n;
        if (this.size == 0) {
            final int n3 = n2;
            this.last = n3;
            this.first = n3;
            this.link[n2] = -1L;
        }
        else {
            final long[] link = this.link;
            final int last = this.last;
            link[last] ^= ((this.link[this.last] ^ ((long)n2 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            this.link[n2] = (((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL);
            this.last = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
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
                this.value[n2] = this.value[n];
                this.fixPointers(n, n2);
                continue Label_0006;
            }
            break;
        }
        key[n2] = null;
    }
    
    @Override
    public int removeInt(final Object o) {
        if (o == null) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        else {
            final Object[] key = this.key;
            int n;
            final Object o2;
            if ((o2 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
                return this.defRetValue;
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
            return this.defRetValue;
        }
    }
    
    private int setValue(final int n, final int n2) {
        final int n3 = this.value[n];
        this.value[n] = n2;
        return n3;
    }
    
    public int removeFirstInt() {
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
        --this.size;
        final int n = this.value[first];
        if (first == this.n) {
            this.containsNullKey = false;
            this.key[this.n] = null;
        }
        else {
            this.shiftKeys(first);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n;
    }
    
    public int removeLastInt() {
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
        --this.size;
        final int n = this.value[last];
        if (last == this.n) {
            this.containsNullKey = false;
            this.key[this.n] = null;
        }
        else {
            this.shiftKeys(last);
        }
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return n;
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
    
    public int getAndMoveToFirst(final Object o) {
        if (o == null) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        else {
            final Object[] key = this.key;
            int n;
            final Object o2;
            if ((o2 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
                return this.defRetValue;
            }
            if (o.equals(o2)) {
                this.moveIndexToFirst(n);
                return this.value[n];
            }
            Object o3;
            while ((o3 = key[n = (n + 1 & this.mask)]) != null) {
                if (o.equals(o3)) {
                    this.moveIndexToFirst(n);
                    return this.value[n];
                }
            }
            return this.defRetValue;
        }
    }
    
    public int getAndMoveToLast(final Object o) {
        if (o == null) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        else {
            final Object[] key = this.key;
            int n;
            final Object o2;
            if ((o2 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
                return this.defRetValue;
            }
            if (o.equals(o2)) {
                this.moveIndexToLast(n);
                return this.value[n];
            }
            Object o3;
            while ((o3 = key[n = (n + 1 & this.mask)]) != null) {
                if (o.equals(o3)) {
                    this.moveIndexToLast(n);
                    return this.value[n];
                }
            }
            return this.defRetValue;
        }
    }
    
    public int putAndMoveToFirst(final Object o, final int n) {
        int n2;
        if (o == null) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, n);
            }
            this.containsNullKey = true;
            n2 = this.n;
        }
        else {
            final Object[] key = this.key;
            final Object o2;
            if ((o2 = key[n2 = (HashCommon.mix(o.hashCode()) & this.mask)]) != null) {
                if (o2.equals(o)) {
                    this.moveIndexToFirst(n2);
                    return this.setValue(n2, n);
                }
                Object o3;
                while ((o3 = key[n2 = (n2 + 1 & this.mask)]) != null) {
                    if (o3.equals(o)) {
                        this.moveIndexToFirst(n2);
                        return this.setValue(n2, n);
                    }
                }
            }
        }
        this.key[n2] = o;
        this.value[n2] = n;
        if (this.size == 0) {
            final int n3 = n2;
            this.last = n3;
            this.first = n3;
            this.link[n2] = -1L;
        }
        else {
            final long[] link = this.link;
            final int first = this.first;
            link[first] ^= ((this.link[this.first] ^ ((long)n2 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            this.link[n2] = (0xFFFFFFFF00000000L | ((long)this.first & 0xFFFFFFFFL));
            this.first = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
    }
    
    public int putAndMoveToLast(final Object o, final int n) {
        int n2;
        if (o == null) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, n);
            }
            this.containsNullKey = true;
            n2 = this.n;
        }
        else {
            final Object[] key = this.key;
            final Object o2;
            if ((o2 = key[n2 = (HashCommon.mix(o.hashCode()) & this.mask)]) != null) {
                if (o2.equals(o)) {
                    this.moveIndexToLast(n2);
                    return this.setValue(n2, n);
                }
                Object o3;
                while ((o3 = key[n2 = (n2 + 1 & this.mask)]) != null) {
                    if (o3.equals(o)) {
                        this.moveIndexToLast(n2);
                        return this.setValue(n2, n);
                    }
                }
            }
        }
        this.key[n2] = o;
        this.value[n2] = n;
        if (this.size == 0) {
            final int n3 = n2;
            this.last = n3;
            this.first = n3;
            this.link[n2] = -1L;
        }
        else {
            final long[] link = this.link;
            final int last = this.last;
            link[last] ^= ((this.link[this.last] ^ ((long)n2 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            this.link[n2] = (((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL);
            this.last = n2;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
    }
    
    @Override
    public int getInt(final Object o) {
        if (o == null) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        final Object[] key = this.key;
        int n;
        final Object o2;
        if ((o2 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
            return this.defRetValue;
        }
        if (o.equals(o2)) {
            return this.value[n];
        }
        Object o3;
        while ((o3 = key[n = (n + 1 & this.mask)]) != null) {
            if (o.equals(o3)) {
                return this.value[n];
            }
        }
        return this.defRetValue;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        if (o == null) {
            return this.containsNullKey;
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
    
    @Override
    public boolean containsValue(final int n) {
        final int[] value = this.value;
        final Object[] key = this.key;
        if (this.containsNullKey && value[this.n] == n) {
            return true;
        }
        int n2 = this.n;
        while (n2-- != 0) {
            if (key[n2] != null && value[n2] == n) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int getOrDefault(final Object o, final int n) {
        if (o == null) {
            return this.containsNullKey ? this.value[this.n] : n;
        }
        final Object[] key = this.key;
        int n2;
        final Object o2;
        if ((o2 = key[n2 = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
            return n;
        }
        if (o.equals(o2)) {
            return this.value[n2];
        }
        Object o3;
        while ((o3 = key[n2 = (n2 + 1 & this.mask)]) != null) {
            if (o.equals(o3)) {
                return this.value[n2];
            }
        }
        return n;
    }
    
    @Override
    public int putIfAbsent(final Object o, final int n) {
        final int find = this.find(o);
        if (find >= 0) {
            return this.value[find];
        }
        this.insert(-find - 1, o, n);
        return this.defRetValue;
    }
    
    @Override
    public boolean remove(final Object o, final int n) {
        if (o == null) {
            if (this.containsNullKey && n == this.value[this.n]) {
                this.removeNullEntry();
                return true;
            }
            return false;
        }
        else {
            final Object[] key = this.key;
            int n2;
            final Object o2;
            if ((o2 = key[n2 = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
                return false;
            }
            if (o.equals(o2) && n == this.value[n2]) {
                this.removeEntry(n2);
                return true;
            }
            Object o3;
            while ((o3 = key[n2 = (n2 + 1 & this.mask)]) != null) {
                if (o.equals(o3) && n == this.value[n2]) {
                    this.removeEntry(n2);
                    return true;
                }
            }
            return false;
        }
    }
    
    @Override
    public boolean replace(final Object o, final int n, final int n2) {
        final int find = this.find(o);
        if (find < 0 || n != this.value[find]) {
            return false;
        }
        this.value[find] = n2;
        return true;
    }
    
    @Override
    public int replace(final Object o, final int n) {
        final int find = this.find(o);
        if (find < 0) {
            return this.defRetValue;
        }
        final int n2 = this.value[find];
        this.value[find] = n;
        return n2;
    }
    
    @Override
    public int computeIfAbsent(final Object o, final ToIntFunction toIntFunction) {
        Objects.requireNonNull(toIntFunction);
        final int find = this.find(o);
        if (find >= 0) {
            return this.value[find];
        }
        final int applyAsInt = toIntFunction.applyAsInt(o);
        this.insert(-find - 1, o, applyAsInt);
        return applyAsInt;
    }
    
    @Override
    public int computeIfAbsent(final Object o, final Object2IntFunction object2IntFunction) {
        Objects.requireNonNull(object2IntFunction);
        final int find = this.find(o);
        if (find >= 0) {
            return this.value[find];
        }
        if (!object2IntFunction.containsKey(o)) {
            return this.defRetValue;
        }
        final int int1 = object2IntFunction.getInt(o);
        this.insert(-find - 1, o, int1);
        return int1;
    }
    
    @Override
    public int computeIntIfPresent(final Object o, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(o);
        if (find < 0) {
            return this.defRetValue;
        }
        final Integer n = biFunction.apply(o, this.value[find]);
        if (n == null) {
            if (o == null) {
                this.removeNullEntry();
            }
            else {
                this.removeEntry(find);
            }
            return this.defRetValue;
        }
        return this.value[find] = n;
    }
    
    @Override
    public int computeInt(final Object o, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(o);
        final Integer n = biFunction.apply(o, (find >= 0) ? Integer.valueOf(this.value[find]) : null);
        if (n == null) {
            if (find >= 0) {
                if (o == null) {
                    this.removeNullEntry();
                }
                else {
                    this.removeEntry(find);
                }
            }
            return this.defRetValue;
        }
        final int intValue = n;
        if (find < 0) {
            this.insert(-find - 1, o, intValue);
            return intValue;
        }
        return this.value[find] = intValue;
    }
    
    @Override
    public int merge(final Object o, final int n, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(o);
        if (find < 0) {
            this.insert(-find - 1, o, n);
            return n;
        }
        final Integer n2 = biFunction.apply(this.value[find], n);
        if (n2 == null) {
            if (o == null) {
                this.removeNullEntry();
            }
            else {
                this.removeEntry(find);
            }
            return this.defRetValue;
        }
        return this.value[find] = n2;
    }
    
    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
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
    public Object firstKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }
    
    @Override
    public Object lastKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }
    
    @Override
    public Object2IntSortedMap tailMap(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Object2IntSortedMap headMap(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Object2IntSortedMap subMap(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Comparator comparator() {
        return null;
    }
    
    @Override
    public Object2IntSortedMap.FastSortedEntrySet object2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(null);
        }
        return this.entries;
    }
    
    @Override
    public ObjectSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(null);
        }
        return this.keys;
    }
    
    @Override
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection() {
                private static final int SPLITERATOR_CHARACTERISTICS = 336;
                final Object2IntLinkedOpenHashMap this$0;
                
                @Override
                public IntIterator iterator() {
                    return this.this$0.new ValueIterator();
                }
                
                @Override
                public IntSpliterator spliterator() {
                    return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 336);
                }
                
                @Override
                public void forEach(final IntConsumer intConsumer) {
                    int size = this.this$0.size;
                    int first = this.this$0.first;
                    while (size-- != 0) {
                        final int n = first;
                        first = (int)this.this$0.link[n];
                        intConsumer.accept(this.this$0.value[n]);
                    }
                }
                
                @Override
                public int size() {
                    return this.this$0.size;
                }
                
                @Override
                public boolean contains(final int n) {
                    return this.this$0.containsValue(n);
                }
                
                @Override
                public void clear() {
                    this.this$0.clear();
                }
                
                @Override
                public Spliterator spliterator() {
                    return this.spliterator();
                }
                
                @Override
                public Iterator iterator() {
                    return this.iterator();
                }
            };
        }
        return this.values;
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
        final int[] value = this.value;
        final int mask = n - 1;
        final Object[] key2 = new Object[n + 1];
        final int[] value2 = new int[n + 1];
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
            value2[first2] = value[first];
            link2[this.first = first2] = -1L;
            first = (int)link[first];
        }
        this.link = link2;
        this.last = -1;
        this.n = n;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = key2;
        this.value = value2;
    }
    
    public Object2IntLinkedOpenHashMap clone() {
        final Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap = (Object2IntLinkedOpenHashMap)super.clone();
        object2IntLinkedOpenHashMap.keys = null;
        object2IntLinkedOpenHashMap.values = null;
        object2IntLinkedOpenHashMap.entries = null;
        object2IntLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        object2IntLinkedOpenHashMap.key = this.key.clone();
        object2IntLinkedOpenHashMap.value = this.value.clone();
        object2IntLinkedOpenHashMap.link = this.link.clone();
        return object2IntLinkedOpenHashMap;
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
                this.key[0].hashCode();
            }
            final int n2 = 0x0 ^ this.value[0];
            ++n;
        }
        if (this.containsNullKey) {
            final int n3 = 0 + this.value[this.n];
        }
        return 0;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final Object[] key = this.key;
        final int[] value = this.value;
        final EntryIterator entryIterator = new EntryIterator();
        objectOutputStream.defaultWriteObject();
        int size = this.size;
        while (size-- != 0) {
            final int nextEntry = entryIterator.nextEntry();
            objectOutputStream.writeObject(key[nextEntry]);
            objectOutputStream.writeInt(value[nextEntry]);
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
        final int[] value = new int[this.n + 1];
        this.value = value;
        final int[] array2 = value;
        final long[] link = new long[this.n + 1];
        this.link = link;
        final long[] array3 = link;
        final int n = -1;
        this.last = n;
        this.first = n;
        int size = this.size;
        while (size-- != 0) {
            final Object object = objectInputStream.readObject();
            final int int1 = objectInputStream.readInt();
            int n2;
            if (object == null) {
                n2 = this.n;
                this.containsNullKey = true;
            }
            else {
                for (n2 = (HashCommon.mix(object.hashCode()) & this.mask); array[n2] != null; n2 = (n2 + 1 & this.mask)) {}
            }
            array[n2] = object;
            array2[n2] = int1;
            if (this.first != -1) {
                final long[] array4 = array3;
                final int n3 = -1;
                array4[n3] ^= ((array3[-1] ^ ((long)n2 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
                final long[] array5 = array3;
                final int n4 = n2;
                array5[n4] ^= ((array3[n2] ^ ((long)(-1) & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            }
            else {
                this.first = n2;
                final long[] array6 = array3;
                final int n5 = n2;
                array6[n5] |= 0xFFFFFFFF00000000L;
            }
        }
        this.last = -1;
    }
    
    private void checkTable() {
    }
    
    @Override
    public ObjectSortedSet object2IntEntrySet() {
        return this.object2IntEntrySet();
    }
    
    @Override
    public ObjectSet keySet() {
        return this.keySet();
    }
    
    @Override
    public ObjectSet object2IntEntrySet() {
        return this.object2IntEntrySet();
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    @Override
    public SortedMap tailMap(final Object o) {
        return this.tailMap(o);
    }
    
    @Override
    public SortedMap headMap(final Object o) {
        return this.headMap(o);
    }
    
    @Override
    public SortedMap subMap(final Object o, final Object o2) {
        return this.subMap(o, o2);
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static int access$100(final Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap) {
        return object2IntLinkedOpenHashMap.removeNullEntry();
    }
    
    static int access$200(final Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, final int n) {
        return object2IntLinkedOpenHashMap.removeEntry(n);
    }
    
    private final class MapEntrySet extends AbstractObjectSortedSet implements Object2IntSortedMap.FastSortedEntrySet
    {
        private static final int SPLITERATOR_CHARACTERISTICS = 81;
        final Object2IntLinkedOpenHashMap this$0;
        
        private MapEntrySet(final Object2IntLinkedOpenHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public ObjectBidirectionalIterator iterator() {
            return this.this$0.new EntryIterator();
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 81);
        }
        
        @Override
        public Comparator comparator() {
            return null;
        }
        
        public ObjectSortedSet subSet(final Object2IntMap.Entry entry, final Object2IntMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }
        
        public ObjectSortedSet headSet(final Object2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        public ObjectSortedSet tailSet(final Object2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Object2IntMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.new MapEntry(this.this$0.first);
        }
        
        @Override
        public Object2IntMap.Entry last() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.new MapEntry(this.this$0.last);
        }
        
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return false;
            }
            final Object key = entry.getKey();
            final int intValue = (int)entry.getValue();
            if (key == null) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == intValue;
            }
            final Object[] key2 = this.this$0.key;
            int n;
            final Object o2;
            if ((o2 = key2[n = (HashCommon.mix(key.hashCode()) & this.this$0.mask)]) == null) {
                return false;
            }
            if (key.equals(o2)) {
                return this.this$0.value[n] == intValue;
            }
            Object o3;
            while ((o3 = key2[n = (n + 1 & this.this$0.mask)]) != null) {
                if (key.equals(o3)) {
                    return this.this$0.value[n] == intValue;
                }
            }
            return false;
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return false;
            }
            final Object key = entry.getKey();
            final int intValue = (int)entry.getValue();
            if (key == null) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == intValue) {
                    Object2IntLinkedOpenHashMap.access$100(this.this$0);
                    return true;
                }
                return false;
            }
            else {
                final Object[] key2 = this.this$0.key;
                int n;
                final Object o2;
                if ((o2 = key2[n = (HashCommon.mix(key.hashCode()) & this.this$0.mask)]) == null) {
                    return false;
                }
                if (!o2.equals(key)) {
                    Object o3;
                    while ((o3 = key2[n = (n + 1 & this.this$0.mask)]) != null) {
                        if (o3.equals(key) && this.this$0.value[n] == intValue) {
                            Object2IntLinkedOpenHashMap.access$200(this.this$0, n);
                            return true;
                        }
                    }
                    return false;
                }
                if (this.this$0.value[n] == intValue) {
                    Object2IntLinkedOpenHashMap.access$200(this.this$0, n);
                    return true;
                }
                return false;
            }
        }
        
        @Override
        public int size() {
            return this.this$0.size;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        public ObjectListIterator iterator(final Object2IntMap.Entry entry) {
            return this.this$0.new EntryIterator(entry.getKey());
        }
        
        @Override
        public ObjectListIterator fastIterator() {
            return this.this$0.new FastEntryIterator();
        }
        
        @Override
        public ObjectListIterator fastIterator(final Object2IntMap.Entry entry) {
            return this.this$0.new FastEntryIterator(entry.getKey());
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            int size = this.this$0.size;
            int first = this.this$0.first;
            while (size-- != 0) {
                final int n = first;
                first = (int)this.this$0.link[n];
                consumer.accept(new BasicEntry(this.this$0.key[n], this.this$0.value[n]));
            }
        }
        
        @Override
        public void fastForEach(final Consumer consumer) {
            final BasicEntry basicEntry = new BasicEntry();
            int size = this.this$0.size;
            int first = this.this$0.first;
            while (size-- != 0) {
                final int n = first;
                first = (int)this.this$0.link[n];
                basicEntry.key = this.this$0.key[n];
                basicEntry.value = this.this$0.value[n];
                consumer.accept(basicEntry);
            }
        }
        
        @Override
        public ObjectSortedSet tailSet(final Object o) {
            return this.tailSet((Object2IntMap.Entry)o);
        }
        
        @Override
        public ObjectSortedSet headSet(final Object o) {
            return this.headSet((Object2IntMap.Entry)o);
        }
        
        @Override
        public ObjectSortedSet subSet(final Object o, final Object o2) {
            return this.subSet((Object2IntMap.Entry)o, (Object2IntMap.Entry)o2);
        }
        
        @Override
        public ObjectBidirectionalIterator iterator(final Object o) {
            return this.iterator((Object2IntMap.Entry)o);
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
        public Object last() {
            return this.last();
        }
        
        @Override
        public Object first() {
            return this.first();
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return this.tailSet((Object2IntMap.Entry)o);
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.headSet((Object2IntMap.Entry)o);
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.subSet((Object2IntMap.Entry)o, (Object2IntMap.Entry)o2);
        }
        
        @Override
        public ObjectBidirectionalIterator fastIterator(final Object2IntMap.Entry entry) {
            return this.fastIterator(entry);
        }
        
        @Override
        public ObjectBidirectionalIterator fastIterator() {
            return this.fastIterator();
        }
        
        @Override
        public ObjectIterator fastIterator() {
            return this.fastIterator();
        }
        
        MapEntrySet(final Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, final Object2IntLinkedOpenHashMap$1 abstractIntCollection) {
            this(object2IntLinkedOpenHashMap);
        }
    }
    
    private final class EntryIterator extends MapIterator implements ObjectListIterator
    {
        private MapEntry entry;
        final Object2IntLinkedOpenHashMap this$0;
        
        public EntryIterator(final Object2IntLinkedOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        public EntryIterator(final Object2IntLinkedOpenHashMap this$0, final Object o) {
            this.this$0 = this$0.super(o, null);
        }
        
        final void acceptOnIndex(final Consumer consumer, final int n) {
            consumer.accept(this.this$0.new MapEntry(n));
        }
        
        @Override
        public MapEntry next() {
            return this.entry = this.this$0.new MapEntry(this.nextEntry());
        }
        
        @Override
        public MapEntry previous() {
            return this.entry = this.this$0.new MapEntry(this.previousEntry());
        }
        
        @Override
        public void remove() {
            super.remove();
            this.entry.index = -1;
        }
        
        @Override
        void acceptOnIndex(final Object o, final int n) {
            this.acceptOnIndex((Consumer)o, n);
        }
        
        @Override
        public void add(final Object o) {
            super.add((Object2IntMap.Entry)o);
        }
        
        @Override
        public void set(final Object o) {
            super.set((Object2IntMap.Entry)o);
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            super.forEachRemaining(consumer);
        }
        
        @Override
        public Object next() {
            return this.next();
        }
        
        @Override
        public Object previous() {
            return this.previous();
        }
    }
    
    private abstract class MapIterator
    {
        int prev;
        int next;
        int curr;
        int index;
        final Object2IntLinkedOpenHashMap this$0;
        
        abstract void acceptOnIndex(final Object p0, final int p1);
        
        protected MapIterator(final Object2IntLinkedOpenHashMap this$0) {
            this.this$0 = this$0;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = this$0.first;
            this.index = 0;
        }
        
        private MapIterator(final Object2IntLinkedOpenHashMap this$0, final Object o) {
            this.this$0 = this$0;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (o == null) {
                if (this$0.containsNullKey) {
                    this.next = (int)this$0.link[this$0.n];
                    this.prev = this$0.n;
                    return;
                }
                throw new NoSuchElementException("The key " + o + " does not belong to this map.");
            }
            else {
                if (Objects.equals(this$0.key[this$0.last], o)) {
                    this.prev = this$0.last;
                    this.index = this$0.size;
                    return;
                }
                for (int prev = HashCommon.mix(o.hashCode()) & this$0.mask; this$0.key[prev] != null; prev = (prev + 1 & this$0.mask)) {
                    if (this$0.key[prev].equals(o)) {
                        this.next = (int)this$0.link[prev];
                        this.prev = prev;
                        return;
                    }
                }
                throw new NoSuchElementException("The key " + o + " does not belong to this map.");
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
        
        public int nextIndex() {
            this.ensureIndexKnown();
            return this.index;
        }
        
        public int previousIndex() {
            this.ensureIndexKnown();
            return this.index - 1;
        }
        
        public int nextEntry() {
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
            //    14: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.next:I
            //    17: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    20: aload_0        
            //    21: aload_0        
            //    22: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.this$0:Lcom/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap;
            //    25: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap.link:[J
            //    28: aload_0        
            //    29: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    32: laload         
            //    33: l2i            
            //    34: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.next:I
            //    37: aload_0        
            //    38: aload_0        
            //    39: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    42: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.prev:I
            //    45: aload_0        
            //    46: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.index:I
            //    49: iflt            62
            //    52: aload_0        
            //    53: dup            
            //    54: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.index:I
            //    57: iconst_1       
            //    58: iadd           
            //    59: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.index:I
            //    62: aload_0        
            //    63: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    66: ireturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public int previousEntry() {
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
            //    14: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.prev:I
            //    17: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    20: aload_0        
            //    21: aload_0        
            //    22: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.this$0:Lcom/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap;
            //    25: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap.link:[J
            //    28: aload_0        
            //    29: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    32: laload         
            //    33: bipush          32
            //    35: lushr          
            //    36: l2i            
            //    37: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.prev:I
            //    40: aload_0        
            //    41: aload_0        
            //    42: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    45: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.next:I
            //    48: aload_0        
            //    49: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.index:I
            //    52: iflt            65
            //    55: aload_0        
            //    56: dup            
            //    57: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.index:I
            //    60: iconst_1       
            //    61: isub           
            //    62: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.index:I
            //    65: aload_0        
            //    66: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    69: ireturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public void forEachRemaining(final Object p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: if_icmpeq       66
            //     4: aload_0        
            //     5: aload_0        
            //     6: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.next:I
            //     9: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    12: aload_0        
            //    13: aload_0        
            //    14: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.this$0:Lcom/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap;
            //    17: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap.link:[J
            //    20: aload_0        
            //    21: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    24: laload         
            //    25: l2i            
            //    26: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.next:I
            //    29: aload_0        
            //    30: aload_0        
            //    31: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    34: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.prev:I
            //    37: aload_0        
            //    38: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.index:I
            //    41: iflt            54
            //    44: aload_0        
            //    45: dup            
            //    46: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.index:I
            //    49: iconst_1       
            //    50: iadd           
            //    51: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.index:I
            //    54: aload_0        
            //    55: aload_1        
            //    56: aload_0        
            //    57: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.curr:I
            //    60: invokevirtual   com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.acceptOnIndex:(Ljava/lang/Object;I)V
            //    63: goto            0
            //    66: return         
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
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
            final Object2IntLinkedOpenHashMap this$0 = this.this$0;
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
                this.this$0.containsNullKey = false;
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
                    this.this$0.value[n] = this.this$0.value[curr];
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
        
        public int skip(final int p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: istore_2       
            //     2: iload_2        
            //     3: iinc            2, -1
            //     6: ifeq            21
            //     9: aload_0        
            //    10: if_icmpeq       21
            //    13: aload_0        
            //    14: invokevirtual   com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.nextEntry:()I
            //    17: pop            
            //    18: goto            2
            //    21: iload_1        
            //    22: iload_2        
            //    23: isub           
            //    24: iconst_1       
            //    25: isub           
            //    26: ireturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public int back(final int p0) {
            // 
            // This method could not be decompiled.
            // 
            // Original Bytecode:
            // 
            //     1: istore_2       
            //     2: iload_2        
            //     3: iinc            2, -1
            //     6: ifeq            21
            //     9: aload_0        
            //    10: if_icmpeq       21
            //    13: aload_0        
            //    14: invokevirtual   com/viaversion/viaversion/libs/fastutil/objects/Object2IntLinkedOpenHashMap$MapIterator.previousEntry:()I
            //    17: pop            
            //    18: goto            2
            //    21: iload_1        
            //    22: iload_2        
            //    23: isub           
            //    24: iconst_1       
            //    25: isub           
            //    26: ireturn        
            // 
            // The error that occurred was:
            // 
            // java.lang.ArrayIndexOutOfBoundsException
            // 
            throw new IllegalStateException("An error occurred while decompiling this method.");
        }
        
        public void set(final Object2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        public void add(final Object2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        MapIterator(final Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, final Object o, final Object2IntLinkedOpenHashMap$1 abstractIntCollection) {
            this(object2IntLinkedOpenHashMap, o);
        }
    }
    
    final class MapEntry implements Object2IntMap.Entry, Map.Entry, ObjectIntPair
    {
        int index;
        final Object2IntLinkedOpenHashMap this$0;
        
        MapEntry(final Object2IntLinkedOpenHashMap this$0, final int index) {
            this.this$0 = this$0;
            this.index = index;
        }
        
        MapEntry(final Object2IntLinkedOpenHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public Object getKey() {
            return this.this$0.key[this.index];
        }
        
        @Override
        public Object left() {
            return this.this$0.key[this.index];
        }
        
        @Override
        public int getIntValue() {
            return this.this$0.value[this.index];
        }
        
        @Override
        public int rightInt() {
            return this.this$0.value[this.index];
        }
        
        @Override
        public int setValue(final int n) {
            final int n2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = n;
            return n2;
        }
        
        @Override
        public ObjectIntPair right(final int n) {
            this.this$0.value[this.index] = n;
            return this;
        }
        
        @Deprecated
        @Override
        public Integer getValue() {
            return this.this$0.value[this.index];
        }
        
        @Deprecated
        @Override
        public Integer setValue(final Integer n) {
            return this.setValue((int)n);
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            return Objects.equals(this.this$0.key[this.index], entry.getKey()) && this.this$0.value[this.index] == (int)entry.getValue();
        }
        
        @Override
        public int hashCode() {
            return ((this.this$0.key[this.index] == null) ? 0 : this.this$0.key[this.index].hashCode()) ^ this.this$0.value[this.index];
        }
        
        @Override
        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }
        
        @Deprecated
        @Override
        public Object setValue(final Object o) {
            return this.setValue((Integer)o);
        }
        
        @Deprecated
        @Override
        public Object getValue() {
            return this.getValue();
        }
    }
    
    private final class FastEntryIterator extends MapIterator implements ObjectListIterator
    {
        final MapEntry entry;
        final Object2IntLinkedOpenHashMap this$0;
        
        public FastEntryIterator(final Object2IntLinkedOpenHashMap this$0) {
            this.this$0 = this$0.super();
            this.entry = this.this$0.new MapEntry();
        }
        
        public FastEntryIterator(final Object2IntLinkedOpenHashMap this$0, final Object o) {
            this.this$0 = this$0.super(o, null);
            this.entry = this.this$0.new MapEntry();
        }
        
        final void acceptOnIndex(final Consumer consumer, final int index) {
            this.entry.index = index;
            consumer.accept(this.entry);
        }
        
        @Override
        public MapEntry next() {
            this.entry.index = this.nextEntry();
            return this.entry;
        }
        
        @Override
        public MapEntry previous() {
            this.entry.index = this.previousEntry();
            return this.entry;
        }
        
        @Override
        void acceptOnIndex(final Object o, final int n) {
            this.acceptOnIndex((Consumer)o, n);
        }
        
        @Override
        public void add(final Object o) {
            super.add((Object2IntMap.Entry)o);
        }
        
        @Override
        public void set(final Object o) {
            super.set((Object2IntMap.Entry)o);
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            super.forEachRemaining(consumer);
        }
        
        @Override
        public Object next() {
            return this.next();
        }
        
        @Override
        public Object previous() {
            return this.previous();
        }
    }
    
    private final class KeySet extends AbstractObjectSortedSet
    {
        private static final int SPLITERATOR_CHARACTERISTICS = 81;
        final Object2IntLinkedOpenHashMap this$0;
        
        private KeySet(final Object2IntLinkedOpenHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public ObjectListIterator iterator(final Object o) {
            return this.this$0.new KeyIterator(o);
        }
        
        @Override
        public ObjectListIterator iterator() {
            return this.this$0.new KeyIterator();
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 81);
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            int size = this.this$0.size;
            int first = this.this$0.first;
            while (size-- != 0) {
                final int n = first;
                first = (int)this.this$0.link[n];
                consumer.accept(this.this$0.key[n]);
            }
        }
        
        @Override
        public int size() {
            return this.this$0.size;
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.this$0.containsKey(o);
        }
        
        @Override
        public boolean remove(final Object o) {
            final int size = this.this$0.size;
            this.this$0.removeInt(o);
            return this.this$0.size != size;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        @Override
        public Object first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.first];
        }
        
        @Override
        public Object last() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.last];
        }
        
        @Override
        public Comparator comparator() {
            return null;
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
        
        KeySet(final Object2IntLinkedOpenHashMap object2IntLinkedOpenHashMap, final Object2IntLinkedOpenHashMap$1 abstractIntCollection) {
            this(object2IntLinkedOpenHashMap);
        }
    }
    
    private final class KeyIterator extends MapIterator implements ObjectListIterator
    {
        final Object2IntLinkedOpenHashMap this$0;
        
        public KeyIterator(final Object2IntLinkedOpenHashMap this$0, final Object o) {
            this.this$0 = this$0.super(o, null);
        }
        
        @Override
        public Object previous() {
            return this.this$0.key[this.previousEntry()];
        }
        
        public KeyIterator(final Object2IntLinkedOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        final void acceptOnIndex(final Consumer consumer, final int n) {
            consumer.accept(this.this$0.key[n]);
        }
        
        @Override
        public Object next() {
            return this.this$0.key[this.nextEntry()];
        }
        
        @Override
        void acceptOnIndex(final Object o, final int n) {
            this.acceptOnIndex((Consumer)o, n);
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            super.forEachRemaining(consumer);
        }
    }
    
    private final class ValueIterator extends MapIterator implements IntListIterator
    {
        final Object2IntLinkedOpenHashMap this$0;
        
        @Override
        public int previousInt() {
            return this.this$0.value[this.previousEntry()];
        }
        
        public ValueIterator(final Object2IntLinkedOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        final void acceptOnIndex(final IntConsumer intConsumer, final int n) {
            intConsumer.accept(this.this$0.value[n]);
        }
        
        @Override
        public int nextInt() {
            return this.this$0.value[this.nextEntry()];
        }
        
        @Override
        void acceptOnIndex(final Object o, final int n) {
            this.acceptOnIndex((IntConsumer)o, n);
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }
    }
}
