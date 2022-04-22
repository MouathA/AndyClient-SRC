package com.viaversion.viaversion.libs.fastutil.objects;

import com.viaversion.viaversion.libs.fastutil.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.io.*;
import java.util.function.*;
import java.util.*;

public class Object2IntOpenHashMap extends AbstractObject2IntMap implements Serializable, Cloneable, Hash
{
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient Object[] key;
    protected transient int[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Object2IntMap.FastEntrySet entries;
    protected transient ObjectSet keys;
    protected transient IntCollection values;
    
    public Object2IntOpenHashMap(final int n, final float f) {
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
    }
    
    public Object2IntOpenHashMap(final int n) {
        this(n, 0.75f);
    }
    
    public Object2IntOpenHashMap() {
        this(16, 0.75f);
    }
    
    public Object2IntOpenHashMap(final Map map, final float n) {
        this(map.size(), n);
        this.putAll(map);
    }
    
    public Object2IntOpenHashMap(final Map map) {
        this(map, 0.75f);
    }
    
    public Object2IntOpenHashMap(final Object2IntMap object2IntMap, final float n) {
        this(object2IntMap.size(), n);
        this.putAll(object2IntMap);
    }
    
    public Object2IntOpenHashMap(final Object2IntMap object2IntMap) {
        this(object2IntMap, 0.75f);
    }
    
    public Object2IntOpenHashMap(final Object[] array, final int[] array2, final float n) {
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
    
    public Object2IntOpenHashMap(final Object[] array, final int[] array2) {
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
        this.key[this.n] = null;
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
    
    private void insert(final int n, final Object o, final int n2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = o;
        this.value[n] = n2;
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
    public Object2IntMap.FastEntrySet object2IntEntrySet() {
        if (this.entries == null) {
            this.entries = new MapEntrySet(null);
        }
        return this.entries;
    }
    
    @Override
    public ObjectSet keySet() {
        if (this.keys == null) {
            this.keys = new KeySet(null);
        }
        return this.keys;
    }
    
    @Override
    public IntCollection values() {
        if (this.values == null) {
            this.values = new AbstractIntCollection() {
                final Object2IntOpenHashMap this$0;
                
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
                        if (this.this$0.key[n] != null) {
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
        final Object[] key = this.key;
        final int[] value = this.value;
        final int mask = n - 1;
        final Object[] key2 = new Object[n + 1];
        final int[] value2 = new int[n + 1];
        int n2 = this.n;
        int realSize = this.realSize();
        while (realSize-- != 0) {
            while (key[--n2] == null) {}
            int n3;
            if (key2[n3 = (HashCommon.mix(key[n2].hashCode()) & mask)] != null) {
                while (key2[n3 = (n3 + 1 & mask)] != null) {}
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
    
    public Object2IntOpenHashMap clone() {
        final Object2IntOpenHashMap object2IntOpenHashMap = (Object2IntOpenHashMap)super.clone();
        object2IntOpenHashMap.keys = null;
        object2IntOpenHashMap.values = null;
        object2IntOpenHashMap.entries = null;
        object2IntOpenHashMap.containsNullKey = this.containsNullKey;
        object2IntOpenHashMap.key = this.key.clone();
        object2IntOpenHashMap.value = this.value.clone();
        return object2IntOpenHashMap;
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
        final EntryIterator entryIterator = new EntryIterator(null);
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
        int size = this.size;
        while (size-- != 0) {
            final Object object = objectInputStream.readObject();
            final int int1 = objectInputStream.readInt();
            int n;
            if (object == null) {
                n = this.n;
                this.containsNullKey = true;
            }
            else {
                for (n = (HashCommon.mix(object.hashCode()) & this.mask); array[n] != null; n = (n + 1 & this.mask)) {}
            }
            array[n] = object;
            array2[n] = int1;
        }
    }
    
    private void checkTable() {
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
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static int access$100(final Object2IntOpenHashMap object2IntOpenHashMap) {
        return object2IntOpenHashMap.realSize();
    }
    
    static int access$400(final Object2IntOpenHashMap object2IntOpenHashMap) {
        return object2IntOpenHashMap.removeNullEntry();
    }
    
    static int access$500(final Object2IntOpenHashMap object2IntOpenHashMap, final int n) {
        return object2IntOpenHashMap.removeEntry(n);
    }
    
    private final class MapEntrySet extends AbstractObjectSet implements Object2IntMap.FastEntrySet
    {
        final Object2IntOpenHashMap this$0;
        
        private MapEntrySet(final Object2IntOpenHashMap this$0) {
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
                    Object2IntOpenHashMap.access$400(this.this$0);
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
                            Object2IntOpenHashMap.access$500(this.this$0, n);
                            return true;
                        }
                    }
                    return false;
                }
                if (this.this$0.value[n] == intValue) {
                    Object2IntOpenHashMap.access$500(this.this$0, n);
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
                if (this.this$0.key[n] != null) {
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
                if (this.this$0.key[n] != null) {
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
        
        MapEntrySet(final Object2IntOpenHashMap object2IntOpenHashMap, final Object2IntOpenHashMap$1 abstractIntCollection) {
            this(object2IntOpenHashMap);
        }
    }
    
    private final class EntryIterator extends MapIterator implements ObjectIterator
    {
        private MapEntry entry;
        final Object2IntOpenHashMap this$0;
        
        private EntryIterator(final Object2IntOpenHashMap this$0) {
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
        
        EntryIterator(final Object2IntOpenHashMap object2IntOpenHashMap, final Object2IntOpenHashMap$1 abstractIntCollection) {
            this(object2IntOpenHashMap);
        }
    }
    
    private abstract class MapIterator
    {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ObjectArrayList wrapped;
        final Object2IntOpenHashMap this$0;
        
        private MapIterator(final Object2IntOpenHashMap this$0) {
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
            final Object[] key = this.this$0.key;
            while (--this.pos >= 0) {
                if (key[this.pos] != null) {
                    return this.last = this.pos;
                }
            }
            this.last = Integer.MIN_VALUE;
            Object value;
            int n;
            for (value = this.wrapped.get(-this.pos - 1), n = (HashCommon.mix(value.hashCode()) & this.this$0.mask); !value.equals(key[n]); n = (n + 1 & this.this$0.mask)) {}
            return n;
        }
        
        public void forEachRemaining(final Object o) {
            if (this.mustReturnNullKey) {
                this.mustReturnNullKey = false;
                this.acceptOnIndex(o, this.last = this.this$0.n);
                --this.c;
            }
            final Object[] key = this.this$0.key;
            while (this.c != 0) {
                if (--this.pos < 0) {
                    this.last = Integer.MIN_VALUE;
                    Object value;
                    int n;
                    for (value = this.wrapped.get(-this.pos - 1), n = (HashCommon.mix(value.hashCode()) & this.this$0.mask); !value.equals(key[n]); n = (n + 1 & this.this$0.mask)) {}
                    this.acceptOnIndex(o, n);
                    --this.c;
                }
                else {
                    if (key[this.pos] == null) {
                        continue;
                    }
                    this.acceptOnIndex(o, this.last = this.pos);
                    --this.c;
                }
            }
        }
        
        private void shiftKeys(int n) {
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
                    this.this$0.value[n2] = this.this$0.value[n];
                    continue Label_0009;
                }
                break;
            }
            key[n2] = null;
        }
        
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNullKey = false;
                this.this$0.key[this.this$0.n] = null;
            }
            else {
                if (this.pos < 0) {
                    this.this$0.removeInt(this.wrapped.set(-this.pos - 1, null));
                    this.last = -1;
                    return;
                }
                this.shiftKeys(this.last);
            }
            final Object2IntOpenHashMap this$0 = this.this$0;
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
        
        MapIterator(final Object2IntOpenHashMap object2IntOpenHashMap, final Object2IntOpenHashMap$1 abstractIntCollection) {
            this(object2IntOpenHashMap);
        }
    }
    
    final class MapEntry implements Object2IntMap.Entry, Map.Entry, ObjectIntPair
    {
        int index;
        final Object2IntOpenHashMap this$0;
        
        MapEntry(final Object2IntOpenHashMap this$0, final int index) {
            this.this$0 = this$0;
            this.index = index;
        }
        
        MapEntry(final Object2IntOpenHashMap this$0) {
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
    
    private final class FastEntryIterator extends MapIterator implements ObjectIterator
    {
        private final MapEntry entry;
        final Object2IntOpenHashMap this$0;
        
        private FastEntryIterator(final Object2IntOpenHashMap this$0) {
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
        
        FastEntryIterator(final Object2IntOpenHashMap object2IntOpenHashMap, final Object2IntOpenHashMap$1 abstractIntCollection) {
            this(object2IntOpenHashMap);
        }
    }
    
    private final class EntrySpliterator extends MapSpliterator implements ObjectSpliterator
    {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        final Object2IntOpenHashMap this$0;
        
        EntrySpliterator(final Object2IntOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        EntrySpliterator(final Object2IntOpenHashMap this$0, final int n, final int n2, final boolean b, final boolean b2) {
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
        final Object2IntOpenHashMap this$0;
        
        MapSpliterator(final Object2IntOpenHashMap this$0) {
            this.this$0 = this$0;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNullKey;
            this.hasSplit = false;
        }
        
        MapSpliterator(final Object2IntOpenHashMap this$0, final int pos, final int max, final boolean mustReturnNull, final boolean hasSplit) {
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
            final Object[] key = this.this$0.key;
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
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
            final Object[] key = this.this$0.key;
            while (this.pos < this.max) {
                if (key[this.pos] != null) {
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
            return Math.min(this.this$0.size - this.c, (long)(Object2IntOpenHashMap.access$100(this.this$0) / (double)this.this$0.n * (this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
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
            final Object[] key = this.this$0.key;
            while (this.pos < this.max && n > 0L) {
                if (key[this.pos++] != null) {
                    ++n2;
                    --n;
                }
            }
            return n2;
        }
    }
    
    private final class KeySet extends AbstractObjectSet
    {
        final Object2IntOpenHashMap this$0;
        
        private KeySet(final Object2IntOpenHashMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public ObjectIterator iterator() {
            return this.this$0.new KeyIterator();
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return this.this$0.new KeySpliterator();
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            if (this.this$0.containsNullKey) {
                consumer.accept(this.this$0.key[this.this$0.n]);
            }
            int n = this.this$0.n;
            while (n-- != 0) {
                final Object o = this.this$0.key[n];
                if (o != null) {
                    consumer.accept(o);
                }
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
        public Spliterator spliterator() {
            return this.spliterator();
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        KeySet(final Object2IntOpenHashMap object2IntOpenHashMap, final Object2IntOpenHashMap$1 abstractIntCollection) {
            this(object2IntOpenHashMap);
        }
    }
    
    private final class KeyIterator extends MapIterator implements ObjectIterator
    {
        final Object2IntOpenHashMap this$0;
        
        public KeyIterator(final Object2IntOpenHashMap this$0) {
            this.this$0 = this$0.super(null);
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
    
    private final class KeySpliterator extends MapSpliterator implements ObjectSpliterator
    {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        final Object2IntOpenHashMap this$0;
        
        KeySpliterator(final Object2IntOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        KeySpliterator(final Object2IntOpenHashMap this$0, final int n, final int n2, final boolean b, final boolean b2) {
            this.this$0 = this$0.super(n, n2, b, b2);
        }
        
        @Override
        public int characteristics() {
            return this.hasSplit ? 1 : 65;
        }
        
        final void acceptOnIndex(final Consumer consumer, final int n) {
            consumer.accept(this.this$0.key[n]);
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
    
    private final class ValueSpliterator extends MapSpliterator implements IntSpliterator
    {
        private static final int POST_SPLIT_CHARACTERISTICS = 256;
        final Object2IntOpenHashMap this$0;
        
        ValueSpliterator(final Object2IntOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        ValueSpliterator(final Object2IntOpenHashMap this$0, final int n, final int n2, final boolean b, final boolean b2) {
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
        final Object2IntOpenHashMap this$0;
        
        public ValueIterator(final Object2IntOpenHashMap this$0) {
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
