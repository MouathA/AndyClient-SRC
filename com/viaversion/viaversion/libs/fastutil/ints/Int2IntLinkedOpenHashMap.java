package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.function.*;
import java.util.*;

public class Int2IntLinkedOpenHashMap extends AbstractInt2IntSortedMap implements Serializable, Cloneable, Hash
{
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
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
    protected transient Int2IntSortedMap.FastSortedEntrySet entries;
    protected transient IntSortedSet keys;
    protected transient IntCollection values;
    
    public Int2IntLinkedOpenHashMap(final int n, final float f) {
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
        this.value = new int[this.n + 1];
        this.link = new long[this.n + 1];
    }
    
    public Int2IntLinkedOpenHashMap(final int n) {
        this(n, 0.75f);
    }
    
    public Int2IntLinkedOpenHashMap() {
        this(16, 0.75f);
    }
    
    public Int2IntLinkedOpenHashMap(final Map map, final float n) {
        this(map.size(), n);
        this.putAll(map);
    }
    
    public Int2IntLinkedOpenHashMap(final Map map) {
        this(map, 0.75f);
    }
    
    public Int2IntLinkedOpenHashMap(final Int2IntMap int2IntMap, final float n) {
        this(int2IntMap.size(), n);
        this.putAll(int2IntMap);
    }
    
    public Int2IntLinkedOpenHashMap(final Int2IntMap int2IntMap) {
        this(int2IntMap, 0.75f);
    }
    
    public Int2IntLinkedOpenHashMap(final int[] array, final int[] array2, final float n) {
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
    
    public Int2IntLinkedOpenHashMap(final int[] array, final int[] array2) {
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
    
    private void insert(final int last, final int n, final int n2) {
        if (last == this.n) {
            this.containsNullKey = true;
        }
        this.key[last] = n;
        this.value[last] = n2;
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
    public int put(final int n, final int n2) {
        final int find = this.find(n);
        if (find < 0) {
            this.insert(-find - 1, n, n2);
            return this.defRetValue;
        }
        final int n3 = this.value[find];
        this.value[find] = n2;
        return n3;
    }
    
    private int addToValue(final int n, final int n2) {
        final int n3 = this.value[n];
        this.value[n] = n3 + n2;
        return n3;
    }
    
    public int addTo(final int n, final int n2) {
        int n3;
        if (n == 0) {
            if (this.containsNullKey) {
                return this.addToValue(this.n, n2);
            }
            n3 = this.n;
            this.containsNullKey = true;
        }
        else {
            final int[] key = this.key;
            final int n4;
            if ((n4 = key[n3 = (HashCommon.mix(n) & this.mask)]) != 0) {
                if (n4 == n) {
                    return this.addToValue(n3, n2);
                }
                int n5;
                while ((n5 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
                    if (n5 == n) {
                        return this.addToValue(n3, n2);
                    }
                }
            }
        }
        this.key[n3] = n;
        this.value[n3] = this.defRetValue + n2;
        if (this.size == 0) {
            final int n6 = n3;
            this.last = n6;
            this.first = n6;
            this.link[n3] = -1L;
        }
        else {
            final long[] link = this.link;
            final int last = this.last;
            link[last] ^= ((this.link[this.last] ^ ((long)n3 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            this.link[n3] = (((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL);
            this.last = n3;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
        return this.defRetValue;
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
                Label_0087: {
                    if (n2 <= n) {
                        if (n2 >= n4) {
                            break Label_0087;
                        }
                        if (n4 > n) {
                            break Label_0087;
                        }
                    }
                    else if (n2 >= n4 && n4 > n) {
                        break Label_0087;
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
    }
    
    @Override
    public int remove(final int n) {
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
    
    public int getAndMoveToFirst(final int n) {
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
    
    public int getAndMoveToLast(final int n) {
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
    
    public int putAndMoveToFirst(final int n, final int n2) {
        int n3;
        if (n == 0) {
            if (this.containsNullKey) {
                this.moveIndexToFirst(this.n);
                return this.setValue(this.n, n2);
            }
            this.containsNullKey = true;
            n3 = this.n;
        }
        else {
            final int[] key = this.key;
            final int n4;
            if ((n4 = key[n3 = (HashCommon.mix(n) & this.mask)]) != 0) {
                if (n4 == n) {
                    this.moveIndexToFirst(n3);
                    return this.setValue(n3, n2);
                }
                int n5;
                while ((n5 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
                    if (n5 == n) {
                        this.moveIndexToFirst(n3);
                        return this.setValue(n3, n2);
                    }
                }
            }
        }
        this.key[n3] = n;
        this.value[n3] = n2;
        if (this.size == 0) {
            final int n6 = n3;
            this.last = n6;
            this.first = n6;
            this.link[n3] = -1L;
        }
        else {
            final long[] link = this.link;
            final int first = this.first;
            link[first] ^= ((this.link[this.first] ^ ((long)n3 & 0xFFFFFFFFL) << 32) & 0xFFFFFFFF00000000L);
            this.link[n3] = (0xFFFFFFFF00000000L | ((long)this.first & 0xFFFFFFFFL));
            this.first = n3;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
    }
    
    public int putAndMoveToLast(final int n, final int n2) {
        int n3;
        if (n == 0) {
            if (this.containsNullKey) {
                this.moveIndexToLast(this.n);
                return this.setValue(this.n, n2);
            }
            this.containsNullKey = true;
            n3 = this.n;
        }
        else {
            final int[] key = this.key;
            final int n4;
            if ((n4 = key[n3 = (HashCommon.mix(n) & this.mask)]) != 0) {
                if (n4 == n) {
                    this.moveIndexToLast(n3);
                    return this.setValue(n3, n2);
                }
                int n5;
                while ((n5 = key[n3 = (n3 + 1 & this.mask)]) != 0) {
                    if (n5 == n) {
                        this.moveIndexToLast(n3);
                        return this.setValue(n3, n2);
                    }
                }
            }
        }
        this.key[n3] = n;
        this.value[n3] = n2;
        if (this.size == 0) {
            final int n6 = n3;
            this.last = n6;
            this.first = n6;
            this.link[n3] = -1L;
        }
        else {
            final long[] link = this.link;
            final int last = this.last;
            link[last] ^= ((this.link[this.last] ^ ((long)n3 & 0xFFFFFFFFL)) & 0xFFFFFFFFL);
            this.link[n3] = (((long)this.last & 0xFFFFFFFFL) << 32 | 0xFFFFFFFFL);
            this.last = n3;
        }
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size, this.f));
        }
        return this.defRetValue;
    }
    
    @Override
    public int get(final int n) {
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
    public boolean containsValue(final int n) {
        final int[] value = this.value;
        final int[] key = this.key;
        if (this.containsNullKey && value[this.n] == n) {
            return true;
        }
        int n2 = this.n;
        while (n2-- != 0) {
            if (key[n2] != 0 && value[n2] == n) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public int getOrDefault(final int n, final int n2) {
        if (n == 0) {
            return this.containsNullKey ? this.value[this.n] : n2;
        }
        final int[] key = this.key;
        int n4;
        final int n3;
        if ((n3 = key[n4 = (HashCommon.mix(n) & this.mask)]) == 0) {
            return n2;
        }
        if (n == n3) {
            return this.value[n4];
        }
        int n5;
        while ((n5 = key[n4 = (n4 + 1 & this.mask)]) != 0) {
            if (n == n5) {
                return this.value[n4];
            }
        }
        return n2;
    }
    
    @Override
    public int putIfAbsent(final int n, final int n2) {
        final int find = this.find(n);
        if (find >= 0) {
            return this.value[find];
        }
        this.insert(-find - 1, n, n2);
        return this.defRetValue;
    }
    
    @Override
    public boolean remove(final int n, final int n2) {
        if (n == 0) {
            if (this.containsNullKey && n2 == this.value[this.n]) {
                this.removeNullEntry();
                return true;
            }
            return false;
        }
        else {
            final int[] key = this.key;
            int n4;
            final int n3;
            if ((n3 = key[n4 = (HashCommon.mix(n) & this.mask)]) == 0) {
                return false;
            }
            if (n == n3 && n2 == this.value[n4]) {
                this.removeEntry(n4);
                return true;
            }
            int n5;
            while ((n5 = key[n4 = (n4 + 1 & this.mask)]) != 0) {
                if (n == n5 && n2 == this.value[n4]) {
                    this.removeEntry(n4);
                    return true;
                }
            }
            return false;
        }
    }
    
    @Override
    public boolean replace(final int n, final int n2, final int n3) {
        final int find = this.find(n);
        if (find < 0 || n2 != this.value[find]) {
            return false;
        }
        this.value[find] = n3;
        return true;
    }
    
    @Override
    public int replace(final int n, final int n2) {
        final int find = this.find(n);
        if (find < 0) {
            return this.defRetValue;
        }
        final int n3 = this.value[find];
        this.value[find] = n2;
        return n3;
    }
    
    @Override
    public int computeIfAbsent(final int n, final IntUnaryOperator intUnaryOperator) {
        Objects.requireNonNull(intUnaryOperator);
        final int find = this.find(n);
        if (find >= 0) {
            return this.value[find];
        }
        final int applyAsInt = intUnaryOperator.applyAsInt(n);
        this.insert(-find - 1, n, applyAsInt);
        return applyAsInt;
    }
    
    @Override
    public int computeIfAbsent(final int n, final Int2IntFunction int2IntFunction) {
        Objects.requireNonNull(int2IntFunction);
        final int find = this.find(n);
        if (find >= 0) {
            return this.value[find];
        }
        if (!int2IntFunction.containsKey(n)) {
            return this.defRetValue;
        }
        final int value = int2IntFunction.get(n);
        this.insert(-find - 1, n, value);
        return value;
    }
    
    @Override
    public int computeIfAbsentNullable(final int n, final IntFunction intFunction) {
        Objects.requireNonNull(intFunction);
        final int find = this.find(n);
        if (find >= 0) {
            return this.value[find];
        }
        final Integer n2 = intFunction.apply(n);
        if (n2 == null) {
            return this.defRetValue;
        }
        final int intValue = n2;
        this.insert(-find - 1, n, intValue);
        return intValue;
    }
    
    @Override
    public int computeIfPresent(final int n, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(n);
        if (find < 0) {
            return this.defRetValue;
        }
        final Integer n2 = biFunction.apply(n, this.value[find]);
        if (n2 == null) {
            if (n == 0) {
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
    public int compute(final int n, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(n);
        final Integer n2 = biFunction.apply(n, (find >= 0) ? Integer.valueOf(this.value[find]) : null);
        if (n2 == null) {
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
        final int intValue = n2;
        if (find < 0) {
            this.insert(-find - 1, n, intValue);
            return intValue;
        }
        return this.value[find] = intValue;
    }
    
    @Override
    public int merge(final int n, final int n2, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(n);
        if (find < 0) {
            this.insert(-find - 1, n, n2);
            return n2;
        }
        final Integer n3 = biFunction.apply(this.value[find], n2);
        if (n3 == null) {
            if (n == 0) {
                this.removeNullEntry();
            }
            else {
                this.removeEntry(find);
            }
            return this.defRetValue;
        }
        return this.value[find] = n3;
    }
    
    @Override
    public void clear() {
        if (this.size == 0) {
            return;
        }
        this.size = 0;
        this.containsNullKey = false;
        Arrays.fill(this.key, 0);
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
    public Int2IntSortedMap tailMap(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Int2IntSortedMap headMap(final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Int2IntSortedMap subMap(final int n, final int n2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public IntComparator comparator() {
        return null;
    }
    
    @Override
    public Int2IntSortedMap.FastSortedEntrySet int2IntEntrySet() {
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
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection() {
                private static final int SPLITERATOR_CHARACTERISTICS = 336;
                final Int2IntLinkedOpenHashMap this$0;
                
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
        final int[] key = this.key;
        final int[] value = this.value;
        final int mask = n - 1;
        final int[] key2 = new int[n + 1];
        final int[] value2 = new int[n + 1];
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
    
    public Int2IntLinkedOpenHashMap clone() {
        final Int2IntLinkedOpenHashMap int2IntLinkedOpenHashMap = (Int2IntLinkedOpenHashMap)super.clone();
        int2IntLinkedOpenHashMap.keys = null;
        int2IntLinkedOpenHashMap.values = null;
        int2IntLinkedOpenHashMap.entries = null;
        int2IntLinkedOpenHashMap.containsNullKey = this.containsNullKey;
        int2IntLinkedOpenHashMap.key = this.key.clone();
        int2IntLinkedOpenHashMap.value = this.value.clone();
        int2IntLinkedOpenHashMap.link = this.link.clone();
        return int2IntLinkedOpenHashMap;
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
            final int n3 = 0x0 ^ this.value[0];
            ++n;
        }
        if (this.containsNullKey) {
            final int n4 = 0 + this.value[this.n];
        }
        return 0;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final int[] key = this.key;
        final int[] value = this.value;
        final EntryIterator entryIterator = new EntryIterator();
        objectOutputStream.defaultWriteObject();
        int size = this.size;
        while (size-- != 0) {
            final int nextEntry = entryIterator.nextEntry();
            objectOutputStream.writeInt(key[nextEntry]);
            objectOutputStream.writeInt(value[nextEntry]);
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
            final int int1 = objectInputStream.readInt();
            final int int2 = objectInputStream.readInt();
            int n2;
            if (int1 == 0) {
                n2 = this.n;
                this.containsNullKey = true;
            }
            else {
                for (n2 = (HashCommon.mix(int1) & this.mask); array[n2] != 0; n2 = (n2 + 1 & this.mask)) {}
            }
            array[n2] = int1;
            array2[n2] = int2;
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
    public ObjectSortedSet int2IntEntrySet() {
        return this.int2IntEntrySet();
    }
    
    @Override
    public IntSet keySet() {
        return this.keySet();
    }
    
    @Override
    public ObjectSet int2IntEntrySet() {
        return this.int2IntEntrySet();
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
    
    static int access$100(final Int2IntLinkedOpenHashMap int2IntLinkedOpenHashMap) {
        return int2IntLinkedOpenHashMap.removeNullEntry();
    }
    
    static int access$200(final Int2IntLinkedOpenHashMap int2IntLinkedOpenHashMap, final int n) {
        return int2IntLinkedOpenHashMap.removeEntry(n);
    }
    
    private final class MapEntrySet extends AbstractObjectSortedSet implements Int2IntSortedMap.FastSortedEntrySet
    {
        private static final int SPLITERATOR_CHARACTERISTICS = 81;
        final Int2IntLinkedOpenHashMap this$0;
        
        private MapEntrySet(final Int2IntLinkedOpenHashMap this$0) {
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
        
        public ObjectSortedSet subSet(final Int2IntMap.Entry entry, final Int2IntMap.Entry entry2) {
            throw new UnsupportedOperationException();
        }
        
        public ObjectSortedSet headSet(final Int2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        public ObjectSortedSet tailSet(final Int2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public Int2IntMap.Entry first() {
            if (this.this$0.size == 0) {
                throw new NoSuchElementException();
            }
            return this.this$0.new MapEntry(this.this$0.first);
        }
        
        @Override
        public Int2IntMap.Entry last() {
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return false;
            }
            final int intValue = entry.getKey();
            final int intValue2 = (int)entry.getValue();
            if (intValue == 0) {
                return this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == intValue2;
            }
            final int[] key = this.this$0.key;
            int n2;
            final int n;
            if ((n = key[n2 = (HashCommon.mix(intValue) & this.this$0.mask)]) == 0) {
                return false;
            }
            if (intValue == n) {
                return this.this$0.value[n2] == intValue2;
            }
            int n3;
            while ((n3 = key[n2 = (n2 + 1 & this.this$0.mask)]) != 0) {
                if (intValue == n3) {
                    return this.this$0.value[n2] == intValue2;
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
            if (entry.getValue() == null || !(entry.getValue() instanceof Integer)) {
                return false;
            }
            final int intValue = entry.getKey();
            final int intValue2 = (int)entry.getValue();
            if (intValue == 0) {
                if (this.this$0.containsNullKey && this.this$0.value[this.this$0.n] == intValue2) {
                    Int2IntLinkedOpenHashMap.access$100(this.this$0);
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
                        if (n3 == intValue && this.this$0.value[n2] == intValue2) {
                            Int2IntLinkedOpenHashMap.access$200(this.this$0, n2);
                            return true;
                        }
                    }
                    return false;
                }
                if (this.this$0.value[n2] == intValue2) {
                    Int2IntLinkedOpenHashMap.access$200(this.this$0, n2);
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
        
        public ObjectListIterator iterator(final Int2IntMap.Entry entry) {
            return this.this$0.new EntryIterator(entry.getIntKey());
        }
        
        @Override
        public ObjectListIterator fastIterator() {
            return this.this$0.new FastEntryIterator();
        }
        
        @Override
        public ObjectListIterator fastIterator(final Int2IntMap.Entry entry) {
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
            return this.tailSet((Int2IntMap.Entry)o);
        }
        
        @Override
        public ObjectSortedSet headSet(final Object o) {
            return this.headSet((Int2IntMap.Entry)o);
        }
        
        @Override
        public ObjectSortedSet subSet(final Object o, final Object o2) {
            return this.subSet((Int2IntMap.Entry)o, (Int2IntMap.Entry)o2);
        }
        
        @Override
        public ObjectBidirectionalIterator iterator(final Object o) {
            return this.iterator((Int2IntMap.Entry)o);
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
            return this.tailSet((Int2IntMap.Entry)o);
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.headSet((Int2IntMap.Entry)o);
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.subSet((Int2IntMap.Entry)o, (Int2IntMap.Entry)o2);
        }
        
        @Override
        public ObjectBidirectionalIterator fastIterator(final Int2IntMap.Entry entry) {
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
        
        MapEntrySet(final Int2IntLinkedOpenHashMap int2IntLinkedOpenHashMap, final Int2IntLinkedOpenHashMap$1 abstractIntCollection) {
            this(int2IntLinkedOpenHashMap);
        }
    }
    
    private final class EntryIterator extends MapIterator implements ObjectListIterator
    {
        private MapEntry entry;
        final Int2IntLinkedOpenHashMap this$0;
        
        public EntryIterator(final Int2IntLinkedOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        public EntryIterator(final Int2IntLinkedOpenHashMap this$0, final int n) {
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
            super.add((Int2IntMap.Entry)o);
        }
        
        @Override
        public void set(final Object o) {
            super.set((Int2IntMap.Entry)o);
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
        final Int2IntLinkedOpenHashMap this$0;
        
        abstract void acceptOnIndex(final Object p0, final int p1);
        
        protected MapIterator(final Int2IntLinkedOpenHashMap this$0) {
            this.this$0 = this$0;
            this.prev = -1;
            this.next = -1;
            this.curr = -1;
            this.index = -1;
            this.next = this$0.first;
            this.index = 0;
        }
        
        private MapIterator(final Int2IntLinkedOpenHashMap this$0, final int n) {
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
            final Int2IntLinkedOpenHashMap this$0 = this.this$0;
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
                return;
            }
            final int[] key = this.this$0.key;
            int n = 0;
        Label_0264:
            while (true) {
                curr = ((n = curr) + 1 & this.this$0.mask);
                int n2;
                while ((n2 = key[curr]) != 0) {
                    final int n3 = HashCommon.mix(n2) & this.this$0.mask;
                    Label_0354: {
                        if (n <= curr) {
                            if (n >= n3) {
                                break Label_0354;
                            }
                            if (n3 > curr) {
                                break Label_0354;
                            }
                        }
                        else if (n >= n3 && n3 > curr) {
                            break Label_0354;
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
                    continue Label_0264;
                }
                break;
            }
            key[n] = 0;
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
        
        public void set(final Int2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        public void add(final Int2IntMap.Entry entry) {
            throw new UnsupportedOperationException();
        }
        
        MapIterator(final Int2IntLinkedOpenHashMap int2IntLinkedOpenHashMap, final int n, final Int2IntLinkedOpenHashMap$1 abstractIntCollection) {
            this(int2IntLinkedOpenHashMap, n);
        }
    }
    
    final class MapEntry implements Int2IntMap.Entry, Map.Entry, IntIntPair
    {
        int index;
        final Int2IntLinkedOpenHashMap this$0;
        
        MapEntry(final Int2IntLinkedOpenHashMap this$0, final int index) {
            this.this$0 = this$0;
            this.index = index;
        }
        
        MapEntry(final Int2IntLinkedOpenHashMap this$0) {
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
        public IntIntPair right(final int n) {
            this.this$0.value[this.index] = n;
            return this;
        }
        
        @Deprecated
        @Override
        public Integer getKey() {
            return this.this$0.key[this.index];
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
            return this.this$0.key[this.index] == entry.getKey() && this.this$0.value[this.index] == (int)entry.getValue();
        }
        
        @Override
        public int hashCode() {
            return this.this$0.key[this.index] ^ this.this$0.value[this.index];
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
        
        @Deprecated
        @Override
        public Object getKey() {
            return this.getKey();
        }
    }
    
    private final class FastEntryIterator extends MapIterator implements ObjectListIterator
    {
        final MapEntry entry;
        final Int2IntLinkedOpenHashMap this$0;
        
        public FastEntryIterator(final Int2IntLinkedOpenHashMap this$0) {
            this.this$0 = this$0.super();
            this.entry = this.this$0.new MapEntry();
        }
        
        public FastEntryIterator(final Int2IntLinkedOpenHashMap this$0, final int n) {
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
            super.add((Int2IntMap.Entry)o);
        }
        
        @Override
        public void set(final Object o) {
            super.set((Int2IntMap.Entry)o);
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
        final Int2IntLinkedOpenHashMap this$0;
        
        private KeySet(final Int2IntLinkedOpenHashMap this$0) {
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
        
        KeySet(final Int2IntLinkedOpenHashMap int2IntLinkedOpenHashMap, final Int2IntLinkedOpenHashMap$1 abstractIntCollection) {
            this(int2IntLinkedOpenHashMap);
        }
    }
    
    private final class KeyIterator extends MapIterator implements IntListIterator
    {
        final Int2IntLinkedOpenHashMap this$0;
        
        public KeyIterator(final Int2IntLinkedOpenHashMap this$0, final int n) {
            this.this$0 = this$0.super(n, null);
        }
        
        @Override
        public int previousInt() {
            return this.this$0.key[this.previousEntry()];
        }
        
        public KeyIterator(final Int2IntLinkedOpenHashMap this$0) {
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
    
    private final class ValueIterator extends MapIterator implements IntListIterator
    {
        final Int2IntLinkedOpenHashMap this$0;
        
        @Override
        public int previousInt() {
            return this.this$0.value[this.previousEntry()];
        }
        
        public ValueIterator(final Int2IntLinkedOpenHashMap this$0) {
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
