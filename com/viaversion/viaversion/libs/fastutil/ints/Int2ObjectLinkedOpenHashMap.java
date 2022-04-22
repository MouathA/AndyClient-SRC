package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;

public class Int2ObjectLinkedOpenHashMap extends AbstractInt2ObjectSortedMap implements Serializable, Cloneable, Hash
{
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient Object[] value;
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
    protected transient Int2ObjectSortedMap.FastSortedEntrySet entries;
    protected transient IntSortedSet keys;
    protected transient ObjectCollection values;
    
    public Int2ObjectLinkedOpenHashMap(final int n, final float f) {
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
        this.key = new int[this.n + 1];
        this.value = new Object[this.n + 1];
        this.link = new long[this.n + 1];
    }
    
    public Int2ObjectLinkedOpenHashMap(final int n) {
        this(n, 0.75f);
    }
    
    public Int2ObjectLinkedOpenHashMap() {
        this(16, 0.75f);
    }
    
    public Int2ObjectLinkedOpenHashMap(final Map map, final float n) {
        this(map.size(), n);
        this.putAll(map);
    }
    
    public Int2ObjectLinkedOpenHashMap(final Map map) {
        this(map, 0.75f);
    }
    
    public Int2ObjectLinkedOpenHashMap(final Int2ObjectMap int2ObjectMap, final float n) {
        this(int2ObjectMap.size(), n);
        this.putAll(int2ObjectMap);
    }
    
    public Int2ObjectLinkedOpenHashMap(final Int2ObjectMap int2ObjectMap) {
        this(int2ObjectMap, 0.75f);
    }
    
    public Int2ObjectLinkedOpenHashMap(final int[] array, final Object[] array2, final float n) {
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
    
    public Int2ObjectLinkedOpenHashMap(final int[] array, final Object[] array2) {
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
    
    private Object removeEntry(final int n) {
        final Object o = this.value[n];
        this.value[n] = null;
        --this.size;
        this.fixPointers(n);
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return o;
    }
    
    private Object removeNullEntry() {
        this.containsNullKey = false;
        final Object o = this.value[this.n];
        this.value[this.n] = null;
        --this.size;
        this.fixPointers(this.n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return o;
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
    
    private int find(final int n) {
        if (n == 0) {
            return this.containsNullKey ? this.n : (-(this.n + 1));
        }
        final int[] key = this.key;
        int n3;
        final int n2;
        if ((n2 = key[n3 = (HashCommon.mix(n) & this.mask)]) == 0) {
            return -(n3 + 1);
        }
        if (n == n2) {
            return n3;
        }
        int n4;
        while ((n4 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
            if (n == n4) {
                return n3;
            }
        }
        return -(n3 + 1);
    }
    
    private void insert(final int last, final int n, final Object o) {
        if (last == this.n) {
            this.containsNullKey = true;
        }
        this.key[last] = n;
        this.value[last] = o;
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
    public Object put(final int n, final Object o) {
        final int find = this.find(n);
        if (find < 0) {
            this.insert(-find - 1, n, o);
            return this.defRetValue;
        }
        final Object o2 = this.value[find];
        this.value[find] = o;
        return o2;
    }
    
    protected final void shiftKeys(int n) {
        final int[] key = this.key;
        int n2 = 0;
    Label_0006:
        while (true) {
            n = ((n2 = n) + 1 & this.mask);
            int n3;
            while ((n3 = key[n]) != 0) {
                final int n4 = HashCommon.mix(n3) & this.mask;
                Label_0094: {
                    if (n2 <= n) {
                        if (n2 >= n4) {
                            break Label_0094;
                        }
                        if (n4 > n) {
                            break Label_0094;
                        }
                    }
                    else if (n2 >= n4 && n4 > n) {
                        break Label_0094;
                    }
                    n = (n + 1 & this.mask);
                    continue;
                }
                key[n2] = n3;
                this.value[n2] = this.value[n];
                this.fixPointers(n, n2);
                continue Label_0006;
            }
            break;
        }
        key[n2] = 0;
        this.value[n2] = null;
    }
    
    @Override
    public Object remove(final int n) {
        if (n == 0) {
            if (this.containsNullKey) {
                return this.removeNullEntry();
            }
            return this.defRetValue;
        }
        else {
            final int[] key = this.key;
            int n3;
            final int n2;
            if ((n2 = key[n3 = (HashCommon.mix(n) & this.mask)]) == 0) {
                return this.defRetValue;
            }
            if (n == n2) {
                return this.removeEntry(n3);
            }
            int n4;
            while ((n4 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
                if (n == n4) {
                    return this.removeEntry(n3);
                }
            }
            return this.defRetValue;
        }
    }
    
    private Object setValue(final int n, final Object o) {
        final Object o2 = this.value[n];
        this.value[n] = o;
        return o2;
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
        --this.size;
        final Object o = this.value[first];
        if (first == this.n) {
            this.containsNullKey = false;
            this.value[this.n] = null;
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
        --this.size;
        final Object o = this.value[last];
        if (last == this.n) {
            this.containsNullKey = false;
            this.value[this.n] = null;
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
    
    public Object getAndMoveToFirst(final int n) {
        if (n == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        else {
            final int[] key = this.key;
            int n3;
            final int n2;
            if ((n2 = key[n3 = (HashCommon.mix(n) & this.mask)]) == 0) {
                return this.defRetValue;
            }
            if (n == n2) {
                this.moveIndexToFirst(n3);
                return this.value[n3];
            }
            int n4;
            while ((n4 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
                if (n == n4) {
                    this.moveIndexToFirst(n3);
                    return this.value[n3];
                }
            }
            return this.defRetValue;
        }
    }
    
    public Object getAndMoveToLast(final int n) {
        if (n == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.value[this.n];
            }
            return this.defRetValue;
        }
        else {
            final int[] key = this.key;
            int n3;
            final int n2;
            if ((n2 = key[n3 = (HashCommon.mix(n) & this.mask)]) == 0) {
                return this.defRetValue;
            }
            if (n == n2) {
                this.moveIndexToLast(n3);
                return this.value[n3];
            }
            int n4;
            while ((n4 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
                if (n == n4) {
                    this.moveIndexToLast(n3);
                    return this.value[n3];
                }
            }
            return this.defRetValue;
        }
    }
    
    public Object putAndMoveToFirst(final int n, final Object o) {
        int n2;
        if (n == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, o);
            }
            this.containsNullKey = true;
            n2 = this.n;
        }
        else {
            final int[] key = this.key;
            final int n3;
            if ((n3 = key[n2 = (HashCommon.mix(n) & this.mask)]) != 0) {
                if (n3 == n) {
                    this.moveIndexToFirst(n2);
                    return this.setValue(n2, o);
                }
                int n4;
                while ((n4 = key[n2 = (n2 + 1 & this.mask)]) != 0) {
                    if (n4 == n) {
                        this.moveIndexToFirst(n2);
                        return this.setValue(n2, o);
                    }
                }
            }
        }
        this.key[n2] = n;
        this.value[n2] = o;
        if (this.size == 0) {
            final int n5 = n2;
            this.last = n5;
            this.first = n5;
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
    
    public Object putAndMoveToLast(final int n, final Object o) {
        int n2;
        if (n == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, o);
            }
            this.containsNullKey = true;
            n2 = this.n;
        }
        else {
            final int[] key = this.key;
            final int n3;
            if ((n3 = key[n2 = (HashCommon.mix(n) & this.mask)]) != 0) {
                if (n3 == n) {
                    this.moveIndexToLast(n2);
                    return this.setValue(n2, o);
                }
                int n4;
                while ((n4 = key[n2 = (n2 + 1 & this.mask)]) != 0) {
                    if (n4 == n) {
                        this.moveIndexToLast(n2);
                        return this.setValue(n2, o);
                    }
                }
            }
        }
        this.key[n2] = n;
        this.value[n2] = o;
        if (this.size == 0) {
            final int n5 = n2;
            this.last = n5;
            this.first = n5;
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
    public Object get(final int n) {
        if (n == 0) {
            return this.containsNullKey ? this.value[this.n] : this.defRetValue;
        }
        final int[] key = this.key;
        int n3;
        final int n2;
        if ((n2 = key[n3 = (HashCommon.mix(n) & this.mask)]) == 0) {
            return this.defRetValue;
        }
        if (n == n2) {
            return this.value[n3];
        }
        int n4;
        while ((n4 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
            if (n == n4) {
                return this.value[n3];
            }
        }
        return this.defRetValue;
    }
    
    @Override
    public boolean containsKey(final int n) {
        if (n == 0) {
            return this.containsNullKey;
        }
        final int[] key = this.key;
        int n3;
        final int n2;
        if ((n2 = key[n3 = (HashCommon.mix(n) & this.mask)]) == 0) {
            return false;
        }
        if (n == n2) {
            return true;
        }
        int n4;
        while ((n4 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
            if (n == n4) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsValue(final Object o) {
        final Object[] value = this.value;
        final int[] key = this.key;
        if (this.containsNullKey && Objects.equals(value[this.n], o)) {
            return true;
        }
        int n = this.n;
        while (n-- != 0) {
            if (key[n] != 0 && Objects.equals(value[n], o)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Object getOrDefault(final int n, final Object o) {
        if (n == 0) {
            return this.containsNullKey ? this.value[this.n] : o;
        }
        final int[] key = this.key;
        int n3;
        final int n2;
        if ((n2 = key[n3 = (HashCommon.mix(n) & this.mask)]) == 0) {
            return o;
        }
        if (n == n2) {
            return this.value[n3];
        }
        int n4;
        while ((n4 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
            if (n == n4) {
                return this.value[n3];
            }
        }
        return o;
    }
    
    @Override
    public Object putIfAbsent(final int n, final Object o) {
        final int find = this.find(n);
        if (find >= 0) {
            return this.value[find];
        }
        this.insert(-find - 1, n, o);
        return this.defRetValue;
    }
    
    @Override
    public boolean remove(final int n, final Object o) {
        if (n == 0) {
            if (this.containsNullKey && Objects.equals(o, this.value[this.n])) {
                this.removeNullEntry();
                return true;
            }
            return false;
        }
        else {
            final int[] key = this.key;
            int n3;
            final int n2;
            if ((n2 = key[n3 = (HashCommon.mix(n) & this.mask)]) == 0) {
                return false;
            }
            if (n == n2 && Objects.equals(o, this.value[n3])) {
                this.removeEntry(n3);
                return true;
            }
            int n4;
            while ((n4 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
                if (n == n4 && Objects.equals(o, this.value[n3])) {
                    this.removeEntry(n3);
                    return true;
                }
            }
            return false;
        }
    }
    
    @Override
    public boolean replace(final int n, final Object o, final Object o2) {
        final int find = this.find(n);
        if (find < 0 || !Objects.equals(o, this.value[find])) {
            return false;
        }
        this.value[find] = o2;
        return true;
    }
    
    @Override
    public Object replace(final int n, final Object o) {
        final int find = this.find(n);
        if (find < 0) {
            return this.defRetValue;
        }
        final Object o2 = this.value[find];
        this.value[find] = o;
        return o2;
    }
    
    @Override
    public Object computeIfAbsent(final int n, final IntFunction intFunction) {
        Objects.requireNonNull(intFunction);
        final int find = this.find(n);
        if (find >= 0) {
            return this.value[find];
        }
        final Object apply = intFunction.apply(n);
        this.insert(-find - 1, n, apply);
        return apply;
    }
    
    @Override
    public Object computeIfAbsent(final int n, final Int2ObjectFunction int2ObjectFunction) {
        Objects.requireNonNull(int2ObjectFunction);
        final int find = this.find(n);
        if (find >= 0) {
            return this.value[find];
        }
        if (!int2ObjectFunction.containsKey(n)) {
            return this.defRetValue;
        }
        final Object value = int2ObjectFunction.get(n);
        this.insert(-find - 1, n, value);
        return value;
    }
    
    @Override
    public Object computeIfPresent(final int n, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(n);
        if (find < 0) {
            return this.defRetValue;
        }
        final Object apply = biFunction.apply(n, this.value[find]);
        if (apply == null) {
            if (n == 0) {
                this.removeNullEntry();
            }
            else {
                this.removeEntry(find);
            }
            return this.defRetValue;
        }
        return this.value[find] = apply;
    }
    
    @Override
    public Object compute(final int n, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(n);
        final Object apply = biFunction.apply(n, (find >= 0) ? this.value[find] : null);
        if (apply == null) {
            if (find >= 0) {
                if (n == 0) {
                    this.removeNullEntry();
                }
                else {
                    this.removeEntry(find);
                }
            }
            return this.defRetValue;
        }
        final Object o = apply;
        if (find < 0) {
            this.insert(-find - 1, n, o);
            return o;
        }
        return this.value[find] = o;
    }
    
    @Override
    public Object merge(final int n, final Object o, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(n);
        if (find < 0 || this.value[find] == null) {
            if (o == null) {
                return this.defRetValue;
            }
            this.insert(-find - 1, n, o);
            return o;
        }
        else {
            final Object apply = biFunction.apply(this.value[find], o);
            if (apply == null) {
                if (n == 0) {
                    this.removeNullEntry();
                }
                else {
                    this.removeEntry(find);
                }
                return this.defRetValue;
            }
            return this.value[find] = apply;
        }
    }
    
    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, 0);
        Arrays.fill(this.value, null);
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
    public int firstIntKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.first];
    }
    
    @Override
    public int lastIntKey() {
        if (this.size == 0) {
            throw new NoSuchElementException();
        }
        return this.key[this.last];
    }
    
    @Override
    public Int2ObjectSortedMap tailMap(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Int2ObjectSortedMap headMap(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Int2ObjectSortedMap subMap(final int n, final int n2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public IntComparator comparator() {
        return null;
    }
    
    @Override
    public Int2ObjectSortedMap.FastSortedEntrySet int2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(null);
        }
        return this.entries;
    }
    
    @Override
    public IntSortedSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(null);
        }
        return this.keys;
    }
    
    @Override
    public ObjectCollection values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection() {
                private static final int SPLITERATOR_CHARACTERISTICS = 80;
                final Int2ObjectLinkedOpenHashMap this$0;
                
                @Override
                public ObjectIterator iterator() {
                    return this.this$0.new ValueIterator();
                }
                
                @Override
                public ObjectSpliterator spliterator() {
                    return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 80);
                }
                
                @Override
                public void forEach(final Consumer consumer) {
                    int size = this.this$0.size;
                    int first = this.this$0.first;
                    while (size-- != 0) {
                        final int n = first;
                        first = (int)this.this$0.link[n];
                        consumer.accept(this.this$0.value[n]);
                    }
                }
                
                @Override
                public int size() {
                    return this.this$0.size;
                }
                
                @Override
                public boolean contains(final Object o) {
                    return this.this$0.containsValue(o);
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
        final int[] key = this.key;
        final Object[] value = this.value;
        final int mask = n - 1;
        final int[] key2 = new int[n + 1];
        final Object[] value2 = new Object[n + 1];
        int first = this.first;
        final long[] link = this.link;
        final long[] link2 = new long[n + 1];
        this.first = -1;
        int size = this.size;
        while (size-- != 0) {
            int first2;
            if (key[first] == 0) {
                first2 = n;
            }
            else {
                for (first2 = (HashCommon.mix(key[first]) & mask); key2[first2] != 0; first2 = (first2 + 1 & mask)) {}
            }
            key2[first2] = key[first];
            value2[first2] = value[first];
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
        this.value = value2;
    }
    
    public Int2ObjectLinkedOpenHashMap clone() {
        final Int2ObjectLinkedOpenHashMap int2ObjectLinkedOpenHashMap = (Int2ObjectLinkedOpenHashMap)super.clone();
        int2ObjectLinkedOpenHashMap.keys = null;
        int2ObjectLinkedOpenHashMap.values = null;
        int2ObjectLinkedOpenHashMap.entries = null;
        int2ObjectLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        int2ObjectLinkedOpenHashMap.key = this.key.clone();
        int2ObjectLinkedOpenHashMap.value = this.value.clone();
        int2ObjectLinkedOpenHashMap.link = this.link.clone();
        return int2ObjectLinkedOpenHashMap;
    }
    
    @Override
    public int hashCode() {
        int realSize = this.realSize();
        while (realSize-- != 0) {
            int n = 0;
            while (this.key[0] == 0) {
                ++n;
            }
            final int n2 = this.key[0];
            if (this != this.value[0]) {
                final int n3 = 0x0 ^ ((this.value[0] == null) ? 0 : this.value[0].hashCode());
            }
            ++n;
        }
        if (this.containsNullKey) {
            final int n4 = 0 + ((this.value[this.n] == null) ? 0 : this.value[this.n].hashCode());
        }
        return 0;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final int[] key = this.key;
        final Object[] value = this.value;
        final EntryIterator entryIterator = new EntryIterator();
        objectOutputStream.defaultWriteObject();
        int size = this.size;
        while (size-- != 0) {
            final int nextEntry = entryIterator.nextEntry();
            objectOutputStream.writeInt(key[nextEntry]);
            objectOutputStream.writeObject(value[nextEntry]);
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.n = HashCommon.arraySize(this.size, this.f);
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.mask = this.n - 1;
        final int[] key = new int[this.n + 1];
        this.key = key;
        final int[] array = key;
        final Object[] value = new Object[this.n + 1];
        this.value = value;
        final Object[] array2 = value;
        final long[] link = new long[this.n + 1];
        this.link = link;
        final long[] array3 = link;
        final int n = -1;
        this.last = n;
        this.first = n;
        int size = this.size;
        while (size-- != 0) {
            final int int1 = objectInputStream.readInt();
            final Object object = objectInputStream.readObject();
            int n2;
            if (int1 == 0) {
                n2 = this.n;
                this.containsNullKey = true;
            }
            else {
                for (n2 = (HashCommon.mix(int1) & this.mask); array[n2] != 0; n2 = (n2 + 1 & this.mask)) {}
            }
            array[n2] = int1;
            array2[n2] = object;
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
        if (-1 != -1) {
            final long[] array7 = array3;
            final int n6 = -1;
            array7[n6] |= 0xFFFFFFFFL;
        }
    }
    
    private void checkTable() {
    }
    
    @Override
    public ObjectSortedSet int2ObjectEntrySet() {
        return this.int2ObjectEntrySet();
    }
    
    @Override
    public IntSet keySet() {
        return this.keySet();
    }
    
    @Override
    public ObjectSet int2ObjectEntrySet() {
        return this.int2ObjectEntrySet();
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
    public Comparator comparator() {
        return this.comparator();
    }
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static Object access$100(final Int2ObjectLinkedOpenHashMap int2ObjectLinkedOpenHashMap) {
        return int2ObjectLinkedOpenHashMap.removeNullEntry();
    }
    
    static Object access$200(final Int2ObjectLinkedOpenHashMap int2ObjectLinkedOpenHashMap, final int n) {
        return int2ObjectLinkedOpenHashMap.removeEntry(n);
    }
    
    private final class MapEntrySet extends AbstractObjectSortedSet implements Int2ObjectSortedMap.FastSortedEntrySet
    {
        private static final int SPLITERATOR_CHARACTERISTICS = 81;
        final Int2ObjectLinkedOpenHashMap this$0;
        
        private MapEntrySet(final Int2ObjectLinkedOpenHashMap this$0) {
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
        
        public ObjectSortedSet subSet(final Int2ObjectMap.Entry entry, final Int2ObjectMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }
        
        public ObjectSortedSet headSet(final Int2ObjectMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        public ObjectSortedSet tailSet(final Int2ObjectMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Int2ObjectMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.new MapEntry(this.this$0.first);
        }
        
        @Override
        public Int2ObjectMap.Entry last() {
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
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return false;
            }
            final int intValue = entry.getKey();
            final V value = entry.getValue();
            if (intValue == 0) {
                return this.this$0.containsNullKey && Objects.equals(this.this$0.value[this.this$0.n], value);
            }
            final int[] key = this.this$0.key;
            int n2;
            final int n;
            if ((n = key[n2 = (HashCommon.mix(intValue) & this.this$0.mask)]) == 0) {
                return false;
            }
            if (intValue == n) {
                return Objects.equals(this.this$0.value[n2], value);
            }
            int n3;
            while ((n3 = key[n2 = (n2 + 1 & this.this$0.mask)]) != 0) {
                if (intValue == n3) {
                    return Objects.equals(this.this$0.value[n2], value);
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
            if (entry.getKey() == null || !(entry.getKey() instanceof Integer)) {
                return false;
            }
            final int intValue = entry.getKey();
            final V value = entry.getValue();
            if (intValue == 0) {
                if (this.this$0.containsNullKey && Objects.equals(this.this$0.value[this.this$0.n], value)) {
                    Int2ObjectLinkedOpenHashMap.access$100(this.this$0);
                    return true;
                }
                return false;
            }
            else {
                final int[] key = this.this$0.key;
                int n2;
                final int n;
                if ((n = key[n2 = (HashCommon.mix(intValue) & this.this$0.mask)]) == 0) {
                    return false;
                }
                if (n != intValue) {
                    int n3;
                    while ((n3 = key[n2 = (n2 + 1 & this.this$0.mask)]) != 0) {
                        if (n3 == intValue && Objects.equals(this.this$0.value[n2], value)) {
                            Int2ObjectLinkedOpenHashMap.access$200(this.this$0, n2);
                            return true;
                        }
                    }
                    return false;
                }
                if (Objects.equals(this.this$0.value[n2], value)) {
                    Int2ObjectLinkedOpenHashMap.access$200(this.this$0, n2);
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
        
        public ObjectListIterator iterator(final Int2ObjectMap.Entry entry) {
            return this.this$0.new EntryIterator(entry.getIntKey());
        }
        
        @Override
        public ObjectListIterator fastIterator() {
            return this.this$0.new FastEntryIterator();
        }
        
        @Override
        public ObjectListIterator fastIterator(final Int2ObjectMap.Entry entry) {
            return this.this$0.new FastEntryIterator(entry.getIntKey());
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
            return this.tailSet((Int2ObjectMap.Entry)o);
        }
        
        @Override
        public ObjectSortedSet headSet(final Object o) {
            return this.headSet((Int2ObjectMap.Entry)o);
        }
        
        @Override
        public ObjectSortedSet subSet(final Object o, final Object o2) {
            return this.subSet((Int2ObjectMap.Entry)o, (Int2ObjectMap.Entry)o2);
        }
        
        @Override
        public ObjectBidirectionalIterator iterator(final Object o) {
            return this.iterator((Int2ObjectMap.Entry)o);
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
            return this.tailSet((Int2ObjectMap.Entry)o);
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.headSet((Int2ObjectMap.Entry)o);
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.subSet((Int2ObjectMap.Entry)o, (Int2ObjectMap.Entry)o2);
        }
        
        @Override
        public ObjectBidirectionalIterator fastIterator(final Int2ObjectMap.Entry entry) {
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
        
        MapEntrySet(final Int2ObjectLinkedOpenHashMap int2ObjectLinkedOpenHashMap, final Int2ObjectLinkedOpenHashMap$1 abstractObjectCollection) {
            this(int2ObjectLinkedOpenHashMap);
        }
    }
    
    private final class EntryIterator extends MapIterator implements ObjectListIterator
    {
        private MapEntry entry;
        final Int2ObjectLinkedOpenHashMap this$0;
        
        public EntryIterator(final Int2ObjectLinkedOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        public EntryIterator(final Int2ObjectLinkedOpenHashMap this$0, final int n) {
            this.this$0 = this$0.super(n, null);
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
            super.add((Int2ObjectMap.Entry)o);
        }
        
        @Override
        public void set(final Object o) {
            super.set((Int2ObjectMap.Entry)o);
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
        final Int2ObjectLinkedOpenHashMap this$0;
        
        abstract void acceptOnIndex(final Object p0, final int p1);
        
        protected MapIterator(final Int2ObjectLinkedOpenHashMap this$0) {
            this.this$0 = this$0;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = this$0.first;
            this.index = 0;
        }
        
        private MapIterator(final Int2ObjectLinkedOpenHashMap this$0, final int n) {
            this.this$0 = this$0;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            if (n == 0) {
                if (this$0.containsNullKey) {
                    this.next = (int)this$0.link[this$0.n];
                    this.prev = this$0.n;
                    return;
                }
                throw new NoSuchElementException("The key " + n + " does not belong to this map.");
            }
            else {
                if (this$0.key[this$0.last] == n) {
                    this.prev = this$0.last;
                    this.index = this$0.size;
                    return;
                }
                for (int prev = HashCommon.mix(n) & this$0.mask; this$0.key[prev] != 0; prev = (prev + 1 & this$0.mask)) {
                    if (this$0.key[prev] == n) {
                        this.next = (int)this$0.link[prev];
                        this.prev = prev;
                        return;
                    }
                }
                throw new NoSuchElementException("The key " + n + " does not belong to this map.");
            }
        }
        
        public boolean hasNext() {
            return this.next != -1;
        }
        
        public boolean hasPrevious() {
            return this.prev != -1;
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
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            this.curr = this.next;
            this.next = (int)this.this$0.link[this.curr];
            this.prev = this.curr;
            if (this.index >= 0) {
                ++this.index;
            }
            return this.curr;
        }
        
        public int previousEntry() {
            if (!this.hasPrevious()) {
                throw new NoSuchElementException();
            }
            this.curr = this.prev;
            this.prev = (int)(this.this$0.link[this.curr] >>> 32);
            this.next = this.curr;
            if (this.index >= 0) {
                --this.index;
            }
            return this.curr;
        }
        
        public void forEachRemaining(final Object o) {
            while (this.hasNext()) {
                this.curr = this.next;
                this.next = (int)this.this$0.link[this.curr];
                this.prev = this.curr;
                if (this.index >= 0) {
                    ++this.index;
                }
                this.acceptOnIndex(o, this.curr);
            }
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
            final Int2ObjectLinkedOpenHashMap this$0 = this.this$0;
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
                this.this$0.value[this.this$0.n] = null;
                return;
            }
            final int[] key = this.this$0.key;
            int n = 0;
        Label_0280:
            while (true) {
                curr = ((n = curr) + 1 & this.this$0.mask);
                int n2;
                while ((n2 = key[curr]) != 0) {
                    final int n3 = HashCommon.mix(n2) & this.this$0.mask;
                    Label_0380: {
                        if (n <= curr) {
                            if (n >= n3) {
                                break Label_0380;
                            }
                            if (n3 > curr) {
                                break Label_0380;
                            }
                        }
                        else if (n >= n3 && n3 > curr) {
                            break Label_0380;
                        }
                        curr = (curr + 1 & this.this$0.mask);
                        continue;
                    }
                    key[n] = n2;
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
            key[n] = 0;
            this.this$0.value[n] = null;
        }
        
        public int skip(final int n) {
            int n2 = n;
            while (n2-- != 0 && this.hasNext()) {
                this.nextEntry();
            }
            return n - n2 - 1;
        }
        
        public int back(final int n) {
            int n2 = n;
            while (n2-- != 0 && this.hasPrevious()) {
                this.previousEntry();
            }
            return n - n2 - 1;
        }
        
        public void set(final Int2ObjectMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        public void add(final Int2ObjectMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        MapIterator(final Int2ObjectLinkedOpenHashMap int2ObjectLinkedOpenHashMap, final int n, final Int2ObjectLinkedOpenHashMap$1 abstractObjectCollection) {
            this(int2ObjectLinkedOpenHashMap, n);
        }
    }
    
    final class MapEntry implements Int2ObjectMap.Entry, Map.Entry, IntObjectPair
    {
        int index;
        final Int2ObjectLinkedOpenHashMap this$0;
        
        MapEntry(final Int2ObjectLinkedOpenHashMap this$0, final int index) {
            this.this$0 = this$0;
            this.index = index;
        }
        
        MapEntry(final Int2ObjectLinkedOpenHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public int getIntKey() {
            return this.this$0.key[this.index];
        }
        
        @Override
        public int leftInt() {
            return this.this$0.key[this.index];
        }
        
        @Override
        public Object getValue() {
            return this.this$0.value[this.index];
        }
        
        @Override
        public Object right() {
            return this.this$0.value[this.index];
        }
        
        @Override
        public Object setValue(final Object o) {
            final Object o2 = this.this$0.value[this.index];
            this.this$0.value[this.index] = o;
            return o2;
        }
        
        @Override
        public IntObjectPair right(final Object o) {
            this.this$0.value[this.index] = o;
            return this;
        }
        
        @Deprecated
        @Override
        public Integer getKey() {
            return this.this$0.key[this.index];
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            return this.this$0.key[this.index] == entry.getKey() && Objects.equals(this.this$0.value[this.index], entry.getValue());
        }
        
        @Override
        public int hashCode() {
            return this.this$0.key[this.index] ^ ((this.this$0.value[this.index] == null) ? 0 : this.this$0.value[this.index].hashCode());
        }
        
        @Override
        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }
        
        @Deprecated
        @Override
        public Object getKey() {
            return this.getKey();
        }
        
        @Override
        public Pair right(final Object o) {
            return this.right(o);
        }
    }
    
    private final class FastEntryIterator extends MapIterator implements ObjectListIterator
    {
        final MapEntry entry;
        final Int2ObjectLinkedOpenHashMap this$0;
        
        public FastEntryIterator(final Int2ObjectLinkedOpenHashMap this$0) {
            this.this$0 = this$0.super();
            this.entry = this.this$0.new MapEntry();
        }
        
        public FastEntryIterator(final Int2ObjectLinkedOpenHashMap this$0, final int n) {
            this.this$0 = this$0.super(n, null);
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
            super.add((Int2ObjectMap.Entry)o);
        }
        
        @Override
        public void set(final Object o) {
            super.set((Int2ObjectMap.Entry)o);
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
    
    private final class KeySet extends AbstractIntSortedSet
    {
        private static final int SPLITERATOR_CHARACTERISTICS = 337;
        final Int2ObjectLinkedOpenHashMap this$0;
        
        private KeySet(final Int2ObjectLinkedOpenHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public IntListIterator iterator(final int n) {
            return this.this$0.new KeyIterator(n);
        }
        
        @Override
        public IntListIterator iterator() {
            return this.this$0.new KeyIterator();
        }
        
        @Override
        public IntSpliterator spliterator() {
            return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 337);
        }
        
        @Override
        public void forEach(final IntConsumer intConsumer) {
            int size = this.this$0.size;
            int first = this.this$0.first;
            while (size-- != 0) {
                final int n = first;
                first = (int)this.this$0.link[n];
                intConsumer.accept(this.this$0.key[n]);
            }
        }
        
        @Override
        public int size() {
            return this.this$0.size;
        }
        
        @Override
        public boolean contains(final int n) {
            return this.this$0.containsKey(n);
        }
        
        @Override
        public boolean remove(final int n) {
            final int size = this.this$0.size;
            this.this$0.remove(n);
            return this.this$0.size != size;
        }
        
        @Override
        public void clear() {
            this.this$0.clear();
        }
        
        @Override
        public int firstInt() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.first];
        }
        
        @Override
        public int lastInt() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.key[this.this$0.last];
        }
        
        @Override
        public IntComparator comparator() {
            return null;
        }
        
        @Override
        public IntSortedSet tailSet(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public IntSortedSet headSet(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public IntSortedSet subSet(final int n, final int n2) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public IntBidirectionalIterator iterator() {
            return this.iterator();
        }
        
        @Override
        public IntBidirectionalIterator iterator(final int n) {
            return this.iterator(n);
        }
        
        @Override
        public IntIterator iterator() {
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
        public Comparator comparator() {
            return this.comparator();
        }
        
        KeySet(final Int2ObjectLinkedOpenHashMap int2ObjectLinkedOpenHashMap, final Int2ObjectLinkedOpenHashMap$1 abstractObjectCollection) {
            this(int2ObjectLinkedOpenHashMap);
        }
    }
    
    private final class KeyIterator extends MapIterator implements IntListIterator
    {
        final Int2ObjectLinkedOpenHashMap this$0;
        
        public KeyIterator(final Int2ObjectLinkedOpenHashMap this$0, final int n) {
            this.this$0 = this$0.super(n, null);
        }
        
        @Override
        public int previousInt() {
            return this.this$0.key[this.previousEntry()];
        }
        
        public KeyIterator(final Int2ObjectLinkedOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        final void acceptOnIndex(final IntConsumer intConsumer, final int n) {
            intConsumer.accept(this.this$0.key[n]);
        }
        
        @Override
        public int nextInt() {
            return this.this$0.key[this.nextEntry()];
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
    
    private final class ValueIterator extends MapIterator implements ObjectListIterator
    {
        final Int2ObjectLinkedOpenHashMap this$0;
        
        @Override
        public Object previous() {
            return this.this$0.value[this.previousEntry()];
        }
        
        public ValueIterator(final Int2ObjectLinkedOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        final void acceptOnIndex(final Consumer consumer, final int n) {
            consumer.accept(this.this$0.value[n]);
        }
        
        @Override
        public Object next() {
            return this.this$0.value[this.nextEntry()];
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
}
