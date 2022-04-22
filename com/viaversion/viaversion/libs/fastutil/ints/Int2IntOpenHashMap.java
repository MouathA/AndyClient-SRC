package com.viaversion.viaversion.libs.fastutil.ints;

import com.viaversion.viaversion.libs.fastutil.*;
import java.io.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.function.*;
import java.util.*;

public class Int2IntOpenHashMap extends AbstractInt2IntMap implements Serializable, Cloneable, Hash
{
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient int[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Int2IntMap.FastEntrySet entries;
    protected transient IntSet keys;
    protected transient IntCollection values;
    
    public Int2IntOpenHashMap(final int n, final float f) {
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
    }
    
    public Int2IntOpenHashMap(final int n) {
        this(n, 0.75f);
    }
    
    public Int2IntOpenHashMap() {
        this(16, 0.75f);
    }
    
    public Int2IntOpenHashMap(final Map map, final float n) {
        this(map.size(), n);
        this.putAll(map);
    }
    
    public Int2IntOpenHashMap(final Map map) {
        this(map, 0.75f);
    }
    
    public Int2IntOpenHashMap(final Int2IntMap int2IntMap, final float n) {
        this(int2IntMap.size(), n);
        this.putAll(int2IntMap);
    }
    
    public Int2IntOpenHashMap(final Int2IntMap int2IntMap) {
        this(int2IntMap, 0.75f);
    }
    
    public Int2IntOpenHashMap(final int[] array, final int[] array2, final float n) {
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
    
    public Int2IntOpenHashMap(final int[] array, final int[] array2) {
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
    
    private void insert(final int n, final int n2, final int n3) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = n2;
        this.value[n] = n3;
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
    public Int2IntMap.FastEntrySet int2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(null);
        }
        return this.entries;
    }
    
    @Override
    public IntSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(null);
        }
        return this.keys;
    }
    
    @Override
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection() {
                final Int2IntOpenHashMap this$0;
                
                @Override
                public IntIterator iterator() {
                    return this.this$0.new ValueIterator();
                }
                
                @Override
                public IntSpliterator spliterator() {
                    return this.this$0.new ValueSpliterator();
                }
                
                @Override
                public void forEach(final IntConsumer intConsumer) {
                    if (this.this$0.containsNullKey) {
                        intConsumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (this.this$0.key[n] != 0) {
                            intConsumer.accept(this.this$0.value[n]);
                        }
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
        int n2 = this.n;
        int realSize = this.realSize();
        while (realSize-- != 0) {
            while (key[--n2] == 0) {}
            int n3;
            if (key2[n3 = (HashCommon.mix(key[n2]) & mask)] != 0) {
                while (key2[n3 = (n3 + 1 & mask)] != 0) {}
            }
            key2[n3] = key[n2];
            value2[n3] = value[n2];
        }
        value2[n] = value[this.n];
        this.n = n;
        this.mask = mask;
        this.maxFill = HashCommon.maxFill(this.n, this.f);
        this.key = key2;
        this.value = value2;
    }
    
    public Int2IntOpenHashMap clone() {
        final Int2IntOpenHashMap int2IntOpenHashMap = (Int2IntOpenHashMap)super.clone();
        int2IntOpenHashMap.keys = null;
        int2IntOpenHashMap.values = null;
        int2IntOpenHashMap.entries = null;
        int2IntOpenHashMap.containsNullKey = this.containsNullKey;
        int2IntOpenHashMap.key = this.key.clone();
        int2IntOpenHashMap.value = this.value.clone();
        return int2IntOpenHashMap;
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
        final EntryIterator entryIterator = new EntryIterator(null);
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
        int size = this.size;
        while (size-- != 0) {
            final int int1 = objectInputStream.readInt();
            final int int2 = objectInputStream.readInt();
            int n;
            if (int1 == 0) {
                n = this.n;
                this.containsNullKey = true;
            }
            else {
                for (n = (HashCommon.mix(int1) & this.mask); array[n] != 0; n = (n + 1 & this.mask)) {}
            }
            array[n] = int1;
            array2[n] = int2;
        }
    }
    
    private void checkTable() {
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
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static int access$100(final Int2IntOpenHashMap int2IntOpenHashMap) {
        return int2IntOpenHashMap.realSize();
    }
    
    static int access$400(final Int2IntOpenHashMap int2IntOpenHashMap) {
        return int2IntOpenHashMap.removeNullEntry();
    }
    
    static int access$500(final Int2IntOpenHashMap int2IntOpenHashMap, final int n) {
        return int2IntOpenHashMap.removeEntry(n);
    }
    
    private final class MapEntrySet extends AbstractObjectSet implements Int2IntMap.FastEntrySet
    {
        final Int2IntOpenHashMap this$0;
        
        private MapEntrySet(final Int2IntOpenHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public ObjectIterator iterator() {
            return this.this$0.new EntryIterator(null);
        }
        
        @Override
        public ObjectIterator fastIterator() {
            return this.this$0.new FastEntryIterator(null);
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return this.this$0.new EntrySpliterator();
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
                    Int2IntOpenHashMap.access$400(this.this$0);
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
                            Int2IntOpenHashMap.access$500(this.this$0, n2);
                            return true;
                        }
                    }
                    return false;
                }
                if (this.this$0.value[n2] == intValue2) {
                    Int2IntOpenHashMap.access$500(this.this$0, n2);
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
        
        @Override
        public void forEach(final Consumer consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(new BasicEntry(this.this$0.key[this.this$0.n], this.this$0.value[this.this$0.n]));
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] != 0) {
                    consumer.accept(new BasicEntry(this.this$0.key[n], this.this$0.value[n]));
                }
            }
        }
        
        @Override
        public void fastForEach(final Consumer consumer) {
            final BasicEntry basicEntry = new BasicEntry();
            if (this.this$0.containsNullKey) {
                basicEntry.key = this.this$0.key[this.this$0.n];
                basicEntry.value = this.this$0.value[this.this$0.n];
                consumer.accept(basicEntry);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                if (this.this$0.key[n] != 0) {
                    basicEntry.key = this.this$0.key[n];
                    basicEntry.value = this.this$0.value[n];
                    consumer.accept(basicEntry);
                }
            }
        }
        
        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        MapEntrySet(final Int2IntOpenHashMap int2IntOpenHashMap, final Int2IntOpenHashMap$1 abstractIntCollection) {
            this(int2IntOpenHashMap);
        }
    }
    
    private final class EntryIterator extends MapIterator implements ObjectIterator
    {
        private MapEntry entry;
        final Int2IntOpenHashMap this$0;
        
        private EntryIterator(final Int2IntOpenHashMap this$0) {
            this.this$0 = this$0.super(null);
        }
        
        @Override
        public MapEntry next() {
            return this.entry = this.this$0.new MapEntry(this.nextEntry());
        }
        
        final void acceptOnIndex(final Consumer consumer, final int n) {
            consumer.accept(this.entry = this.this$0.new MapEntry(n));
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
        public void forEachRemaining(final Consumer consumer) {
            super.forEachRemaining(consumer);
        }
        
        @Override
        public Object next() {
            return this.next();
        }
        
        EntryIterator(final Int2IntOpenHashMap int2IntOpenHashMap, final Int2IntOpenHashMap$1 abstractIntCollection) {
            this(int2IntOpenHashMap);
        }
    }
    
    private abstract class MapIterator
    {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        IntArrayList wrapped;
        final Int2IntOpenHashMap this$0;
        
        private MapIterator(final Int2IntOpenHashMap this$0) {
            this.this$0 = this$0;
            this.pos = this.this$0.n;
            this.last = -1;
            this.c = this.this$0.size;
            this.mustReturnNullKey = this.this$0.containsNullKey;
        }
        
        abstract void acceptOnIndex(final Object p0, final int p1);
        
        public boolean hasNext() {
            return this.c != 0;
        }
        
        public int nextEntry() {
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            --this.c;
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                return this.last = this.this$0.n;
            }
            final int[] key = this.this$0.key;
            while (--this.pos >= 0) {
                if (key[this.pos] != 0) {
                    return this.last = this.pos;
                }
            }
            this.last = Integer.MIN_VALUE;
            int i;
            int n;
            for (i = this.wrapped.getInt(-this.pos - 1), n = (HashCommon.mix(i) & this.this$0.mask); i != key[n]; n = (n + 1 & this.this$0.mask)) {}
            return n;
        }
        
        public void forEachRemaining(final Object o) {
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                this.acceptOnIndex(o, this.last = this.this$0.n);
                --this.c;
            }
            final int[] key = this.this$0.key;
            while (this.c != 0) {
                if (--this.pos < 0) {
                    this.last = Integer.MIN_VALUE;
                    int i;
                    int n;
                    for (i = this.wrapped.getInt(-this.pos - 1), n = (HashCommon.mix(i) & this.this$0.mask); i != key[n]; n = (n + 1 & this.this$0.mask)) {}
                    this.acceptOnIndex(o, n);
                    --this.c;
                }
                else {
                    if (key[this.pos] == 0) {
                        continue;
                    }
                    this.acceptOnIndex(o, this.last = this.pos);
                    --this.c;
                }
            }
        }
        
        private void shiftKeys(int n) {
            final int[] key = this.this$0.key;
            int n2 = 0;
        Label_0009:
            while (true) {
                n = ((n2 = n) + 1 & this.this$0.mask);
                int n3;
                while ((n3 = key[n]) != 0) {
                    final int n4 = HashCommon.mix(n3) & this.this$0.mask;
                    Label_0099: {
                        if (n2 <= n) {
                            if (n2 >= n4) {
                                break Label_0099;
                            }
                            if (n4 > n) {
                                break Label_0099;
                            }
                        }
                        else if (n2 >= n4 && n4 > n) {
                            break Label_0099;
                        }
                        n = (n + 1 & this.this$0.mask);
                        continue;
                    }
                    if (n < n2) {
                        if (this.wrapped == null) {
                            this.wrapped = new IntArrayList(2);
                        }
                        this.wrapped.add(key[n]);
                    }
                    key[n2] = n3;
                    this.this$0.value[n2] = this.this$0.value[n];
                    continue Label_0009;
                }
                break;
            }
            key[n2] = 0;
        }
        
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNullKey = false;
            }
            else {
                if (this.pos < 0) {
                    this.this$0.remove(this.wrapped.getInt(-this.pos - 1));
                    this.last = -1;
                    return;
                }
                this.shiftKeys(this.last);
            }
            final Int2IntOpenHashMap this$0 = this.this$0;
            --this$0.size;
            this.last = -1;
        }
        
        public int skip(final int n) {
            int n2 = n;
            while (n2-- != 0 && this.hasNext()) {
                this.nextEntry();
            }
            return n - n2 - 1;
        }
        
        MapIterator(final Int2IntOpenHashMap int2IntOpenHashMap, final Int2IntOpenHashMap$1 abstractIntCollection) {
            this(int2IntOpenHashMap);
        }
    }
    
    final class MapEntry implements Int2IntMap.Entry, Map.Entry, IntIntPair
    {
        int index;
        final Int2IntOpenHashMap this$0;
        
        MapEntry(final Int2IntOpenHashMap this$0, final int index) {
            this.this$0 = this$0;
            this.index = index;
        }
        
        MapEntry(final Int2IntOpenHashMap this$0) {
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
    
    private final class FastEntryIterator extends MapIterator implements ObjectIterator
    {
        private final MapEntry entry;
        final Int2IntOpenHashMap this$0;
        
        private FastEntryIterator(final Int2IntOpenHashMap this$0) {
            this.this$0 = this$0.super(null);
            this.entry = this.this$0.new MapEntry();
        }
        
        @Override
        public MapEntry next() {
            this.entry.index = this.nextEntry();
            return this.entry;
        }
        
        final void acceptOnIndex(final Consumer consumer, final int index) {
            this.entry.index = index;
            consumer.accept(this.entry);
        }
        
        @Override
        void acceptOnIndex(final Object o, final int n) {
            this.acceptOnIndex((Consumer)o, n);
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            super.forEachRemaining(consumer);
        }
        
        @Override
        public Object next() {
            return this.next();
        }
        
        FastEntryIterator(final Int2IntOpenHashMap int2IntOpenHashMap, final Int2IntOpenHashMap$1 abstractIntCollection) {
            this(int2IntOpenHashMap);
        }
    }
    
    private final class EntrySpliterator extends MapSpliterator implements ObjectSpliterator
    {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        final Int2IntOpenHashMap this$0;
        
        EntrySpliterator(final Int2IntOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        EntrySpliterator(final Int2IntOpenHashMap this$0, final int n, final int n2, final boolean b, final boolean b2) {
            this.this$0 = this$0.super(n, n2, b, b2);
        }
        
        @Override
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }
        
        final void acceptOnIndex(final Consumer consumer, final int n) {
            consumer.accept(this.this$0.new MapEntry(n));
        }
        
        @Override
        final EntrySpliterator makeForSplit(final int n, final int n2, final boolean b) {
            return this.this$0.new EntrySpliterator(n, n2, b, true);
        }
        
        @Override
        MapSpliterator makeForSplit(final int n, final int n2, final boolean b) {
            return this.makeForSplit(n, n2, b);
        }
        
        @Override
        void acceptOnIndex(final Object o, final int n) {
            this.acceptOnIndex((Consumer)o, n);
        }
        
        @Override
        public ObjectSpliterator trySplit() {
            return (ObjectSpliterator)super.trySplit();
        }
        
        @Override
        public Spliterator trySplit() {
            return (Spliterator)super.trySplit();
        }
        
        @Override
        public void forEachRemaining(final Consumer consumer) {
            super.forEachRemaining(consumer);
        }
        
        @Override
        public boolean tryAdvance(final Consumer consumer) {
            return super.tryAdvance(consumer);
        }
    }
    
    private abstract class MapSpliterator
    {
        int pos;
        int max;
        int c;
        boolean mustReturnNull;
        boolean hasSplit;
        final Int2IntOpenHashMap this$0;
        
        MapSpliterator(final Int2IntOpenHashMap this$0) {
            this.this$0 = this$0;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNullKey;
            this.hasSplit = false;
        }
        
        MapSpliterator(final Int2IntOpenHashMap this$0, final int pos, final int max, final boolean mustReturnNull, final boolean hasSplit) {
            this.this$0 = this$0;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNullKey;
            this.hasSplit = false;
            this.pos = pos;
            this.max = max;
            this.mustReturnNull = mustReturnNull;
            this.hasSplit = hasSplit;
        }
        
        abstract void acceptOnIndex(final Object p0, final int p1);
        
        abstract MapSpliterator makeForSplit(final int p0, final int p1, final boolean p2);
        
        public boolean tryAdvance(final Object o) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++this.c;
                this.acceptOnIndex(o, this.this$0.n);
                return true;
            }
            final int[] key = this.this$0.key;
            while (this.pos < this.max) {
                if (key[this.pos] != 0) {
                    ++this.c;
                    this.acceptOnIndex(o, this.pos++);
                    return true;
                }
                ++this.pos;
            }
            return false;
        }
        
        public void forEachRemaining(final Object o) {
            if (this.mustReturnNull) {
                this.mustReturnNull = false;
                ++this.c;
                this.acceptOnIndex(o, this.this$0.n);
            }
            final int[] key = this.this$0.key;
            while (this.pos < this.max) {
                if (key[this.pos] != 0) {
                    this.acceptOnIndex(o, this.pos);
                    ++this.c;
                }
                ++this.pos;
            }
        }
        
        public long estimateSize() {
            if (!this.hasSplit) {
                return this.this$0.size - this.c;
            }
            return Math.min(this.this$0.size - this.c, (long)(Int2IntOpenHashMap.access$100(this.this$0) / (double)this.this$0.n * (this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
        }
        
        public MapSpliterator trySplit() {
            if (this.pos >= this.max - 1) {
                return null;
            }
            final int n = this.max - this.pos >> 1;
            if (n <= 1) {
                return null;
            }
            final int pos = this.pos + n;
            final MapSpliterator forSplit = this.makeForSplit(this.pos, pos, this.mustReturnNull);
            this.pos = pos;
            this.mustReturnNull = false;
            this.hasSplit = true;
            return forSplit;
        }
        
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
            final int[] key = this.this$0.key;
            while (this.pos < this.max && n > 0L) {
                if (key[this.pos++] != 0) {
                    ++n2;
                    --n;
                }
            }
            return n2;
        }
    }
    
    private final class KeySet extends AbstractIntSet
    {
        final Int2IntOpenHashMap this$0;
        
        private KeySet(final Int2IntOpenHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public IntIterator iterator() {
            return this.this$0.new KeyIterator();
        }
        
        @Override
        public IntSpliterator spliterator() {
            return this.this$0.new KeySpliterator();
        }
        
        @Override
        public void forEach(final IntConsumer intConsumer) {
            if (this.this$0.containsNullKey) {
                intConsumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                final int n2 = this.this$0.key[n];
                if (n2 != 0) {
                    intConsumer.accept(n2);
                }
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
        public Spliterator spliterator() {
            return this.spliterator();
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        KeySet(final Int2IntOpenHashMap int2IntOpenHashMap, final Int2IntOpenHashMap$1 abstractIntCollection) {
            this(int2IntOpenHashMap);
        }
    }
    
    private final class KeyIterator extends MapIterator implements IntIterator
    {
        final Int2IntOpenHashMap this$0;
        
        public KeyIterator(final Int2IntOpenHashMap this$0) {
            this.this$0 = this$0.super(null);
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
    
    private final class KeySpliterator extends MapSpliterator implements IntSpliterator
    {
        private static final int POST_SPLIT_CHARACTERISTICS = 257;
        final Int2IntOpenHashMap this$0;
        
        KeySpliterator(final Int2IntOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        KeySpliterator(final Int2IntOpenHashMap this$0, final int n, final int n2, final boolean b, final boolean b2) {
            this.this$0 = this$0.super(n, n2, b, b2);
        }
        
        @Override
        public int characteristics() {
            return this.hasSplit ? 257 : 321;
        }
        
        final void acceptOnIndex(final IntConsumer intConsumer, final int n) {
            intConsumer.accept(this.this$0.key[n]);
        }
        
        @Override
        final KeySpliterator makeForSplit(final int n, final int n2, final boolean b) {
            return this.this$0.new KeySpliterator(n, n2, b, true);
        }
        
        @Override
        MapSpliterator makeForSplit(final int n, final int n2, final boolean b) {
            return this.makeForSplit(n, n2, b);
        }
        
        @Override
        void acceptOnIndex(final Object o, final int n) {
            this.acceptOnIndex((IntConsumer)o, n);
        }
        
        @Override
        public IntSpliterator trySplit() {
            return (IntSpliterator)super.trySplit();
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            return super.tryAdvance(intConsumer);
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return (Spliterator.OfInt)super.trySplit();
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive)super.trySplit();
        }
        
        @Override
        public Spliterator trySplit() {
            return (Spliterator)super.trySplit();
        }
    }
    
    private final class ValueSpliterator extends MapSpliterator implements IntSpliterator
    {
        private static final int POST_SPLIT_CHARACTERISTICS = 256;
        final Int2IntOpenHashMap this$0;
        
        ValueSpliterator(final Int2IntOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        ValueSpliterator(final Int2IntOpenHashMap this$0, final int n, final int n2, final boolean b, final boolean b2) {
            this.this$0 = this$0.super(n, n2, b, b2);
        }
        
        @Override
        public int characteristics() {
            return this.hasSplit ? 256 : 320;
        }
        
        final void acceptOnIndex(final IntConsumer intConsumer, final int n) {
            intConsumer.accept(this.this$0.value[n]);
        }
        
        @Override
        final ValueSpliterator makeForSplit(final int n, final int n2, final boolean b) {
            return this.this$0.new ValueSpliterator(n, n2, b, true);
        }
        
        @Override
        MapSpliterator makeForSplit(final int n, final int n2, final boolean b) {
            return this.makeForSplit(n, n2, b);
        }
        
        @Override
        void acceptOnIndex(final Object o, final int n) {
            this.acceptOnIndex((IntConsumer)o, n);
        }
        
        @Override
        public IntSpliterator trySplit() {
            return (IntSpliterator)super.trySplit();
        }
        
        @Override
        public void forEachRemaining(final IntConsumer intConsumer) {
            super.forEachRemaining(intConsumer);
        }
        
        @Override
        public boolean tryAdvance(final IntConsumer intConsumer) {
            return super.tryAdvance(intConsumer);
        }
        
        @Override
        public Spliterator.OfInt trySplit() {
            return (Spliterator.OfInt)super.trySplit();
        }
        
        @Override
        public Spliterator.OfPrimitive trySplit() {
            return (Spliterator.OfPrimitive)super.trySplit();
        }
        
        @Override
        public Spliterator trySplit() {
            return (Spliterator)super.trySplit();
        }
    }
    
    private final class ValueIterator extends MapIterator implements IntIterator
    {
        final Int2IntOpenHashMap this$0;
        
        public ValueIterator(final Int2IntOpenHashMap this$0) {
            this.this$0 = this$0.super(null);
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
