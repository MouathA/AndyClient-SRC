package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.function.*;
import java.io.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.*;

public class Object2ObjectOpenHashMap extends AbstractObject2ObjectMap implements Serializable, Cloneable, Hash
{
    private static final long serialVersionUID = 0L;
    private static final boolean ASSERTS = false;
    protected transient Object[] key;
    protected transient Object[] value;
    protected transient int mask;
    protected transient boolean containsNullKey;
    protected transient int n;
    protected transient int maxFill;
    protected final transient int minN;
    protected int size;
    protected final float f;
    protected transient Object2ObjectMap.FastEntrySet entries;
    protected transient ObjectSet keys;
    protected transient ObjectCollection values;
    
    public Object2ObjectOpenHashMap(final int n, final float f) {
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
        this.value = new Object[this.n + 1];
    }
    
    public Object2ObjectOpenHashMap(final int n) {
        this(n, 0.75f);
    }
    
    public Object2ObjectOpenHashMap() {
        this(16, 0.75f);
    }
    
    public Object2ObjectOpenHashMap(final Map map, final float n) {
        this(map.size(), n);
        this.putAll(map);
    }
    
    public Object2ObjectOpenHashMap(final Map map) {
        this(map, 0.75f);
    }
    
    public Object2ObjectOpenHashMap(final Object2ObjectMap object2ObjectMap, final float n) {
        this(object2ObjectMap.size(), n);
        this.putAll(object2ObjectMap);
    }
    
    public Object2ObjectOpenHashMap(final Object2ObjectMap object2ObjectMap) {
        this(object2ObjectMap, 0.75f);
    }
    
    public Object2ObjectOpenHashMap(final Object[] array, final Object[] array2, final float n) {
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
    
    public Object2ObjectOpenHashMap(final Object[] array, final Object[] array2) {
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
        this.shiftKeys(n);
        if (this.n > this.minN && this.size < this.maxFill / 4 && this.n > 16) {
            this.rehash(this.n / 2);
        }
        return o;
    }
    
    private Object removeNullEntry() {
        this.containsNullKey = false;
        this.key[this.n] = null;
        final Object o = this.value[this.n];
        this.value[this.n] = null;
        --this.size;
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
    
    private void insert(final int n, final Object o, final Object o2) {
        if (n == this.n) {
            this.containsNullKey = true;
        }
        this.key[n] = o;
        this.value[n] = o2;
        if (this.size++ >= this.maxFill) {
            this.rehash(HashCommon.arraySize(this.size + 1, this.f));
        }
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        final int find = this.find(o);
        if (find < 0) {
            this.insert(-find - 1, o, o2);
            return this.defRetValue;
        }
        final Object o3 = this.value[find];
        this.value[find] = o2;
        return o3;
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
                Label_0097: {
                    if (n2 <= n) {
                        if (n2 >= n3) {
                            break Label_0097;
                        }
                        if (n3 > n) {
                            break Label_0097;
                        }
                    }
                    else if (n2 >= n3 && n3 > n) {
                        break Label_0097;
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
        this.value[n2] = null;
    }
    
    @Override
    public Object remove(final Object o) {
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
    public Object get(final Object o) {
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
    public boolean containsValue(final Object o) {
        final Object[] value = this.value;
        final Object[] key = this.key;
        if (this.containsNullKey && Objects.equals(value[this.n], o)) {
            return true;
        }
        int n = this.n;
        while (n-- != 0) {
            if (key[n] != null && Objects.equals(value[n], o)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public Object getOrDefault(final Object o, final Object o2) {
        if (o == null) {
            return this.containsNullKey ? this.value[this.n] : o2;
        }
        final Object[] key = this.key;
        int n;
        final Object o3;
        if ((o3 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
            return o2;
        }
        if (o.equals(o3)) {
            return this.value[n];
        }
        Object o4;
        while ((o4 = key[n = (n + 1 & this.mask)]) != null) {
            if (o.equals(o4)) {
                return this.value[n];
            }
        }
        return o2;
    }
    
    @Override
    public Object putIfAbsent(final Object o, final Object o2) {
        final int find = this.find(o);
        if (find >= 0) {
            return this.value[find];
        }
        this.insert(-find - 1, o, o2);
        return this.defRetValue;
    }
    
    @Override
    public boolean remove(final Object o, final Object o2) {
        if (o == null) {
            if (this.containsNullKey && Objects.equals(o2, this.value[this.n])) {
                this.removeNullEntry();
                return true;
            }
            return false;
        }
        else {
            final Object[] key = this.key;
            int n;
            final Object o3;
            if ((o3 = key[n = (HashCommon.mix(o.hashCode()) & this.mask)]) == null) {
                return false;
            }
            if (o.equals(o3) && Objects.equals(o2, this.value[n])) {
                this.removeEntry(n);
                return true;
            }
            Object o4;
            while ((o4 = key[n = (n + 1 & this.mask)]) != null) {
                if (o.equals(o4) && Objects.equals(o2, this.value[n])) {
                    this.removeEntry(n);
                    return true;
                }
            }
            return false;
        }
    }
    
    @Override
    public boolean replace(final Object o, final Object o2, final Object o3) {
        final int find = this.find(o);
        if (find < 0 || !Objects.equals(o2, this.value[find])) {
            return false;
        }
        this.value[find] = o3;
        return true;
    }
    
    @Override
    public Object replace(final Object o, final Object o2) {
        final int find = this.find(o);
        if (find < 0) {
            return this.defRetValue;
        }
        final Object o3 = this.value[find];
        this.value[find] = o2;
        return o3;
    }
    
    @Override
    public Object computeIfAbsent(final Object o, final Object2ObjectFunction object2ObjectFunction) {
        Objects.requireNonNull(object2ObjectFunction);
        final int find = this.find(o);
        if (find >= 0) {
            return this.value[find];
        }
        if (!object2ObjectFunction.containsKey(o)) {
            return this.defRetValue;
        }
        final Object value = object2ObjectFunction.get(o);
        this.insert(-find - 1, o, value);
        return value;
    }
    
    @Override
    public Object computeIfPresent(final Object o, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(o);
        if (find < 0) {
            return this.defRetValue;
        }
        final Object apply = biFunction.apply(o, this.value[find]);
        if (apply == null) {
            if (o == null) {
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
    public Object compute(final Object o, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(o);
        final Object apply = biFunction.apply(o, (find >= 0) ? this.value[find] : null);
        if (apply == null) {
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
        final Object o2 = apply;
        if (find < 0) {
            this.insert(-find - 1, o, o2);
            return o2;
        }
        return this.value[find] = o2;
    }
    
    @Override
    public Object merge(final Object o, final Object o2, final BiFunction biFunction) {
        Objects.requireNonNull(biFunction);
        final int find = this.find(o);
        if (find < 0 || this.value[find] == null) {
            if (o2 == null) {
                return this.defRetValue;
            }
            this.insert(-find - 1, o, o2);
            return o2;
        }
        else {
            final Object apply = biFunction.apply(this.value[find], o2);
            if (apply == null) {
                if (o == null) {
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
        Arrays.fill(this.key, null);
        Arrays.fill(this.value, null);
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
    public Object2ObjectMap.FastEntrySet object2ObjectEntrySet() {
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
    public ObjectCollection values() {
        if (this.values == null) {
            this.values = new AbstractObjectCollection() {
                final Object2ObjectOpenHashMap this$0;
                
                @Override
                public ObjectIterator iterator() {
                    return this.this$0.new ValueIterator();
                }
                
                @Override
                public ObjectSpliterator spliterator() {
                    return this.this$0.new ValueSpliterator();
                }
                
                @Override
                public void forEach(final Consumer consumer) {
                    if (this.this$0.containsNullKey) {
                        consumer.accept(this.this$0.value[this.this$0.n]);
                    }
                    int n = this.this$0.n;
                    while (n-- != 0) {
                        if (this.this$0.key[n] != null) {
                            consumer.accept(this.this$0.value[n]);
                        }
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
        final Object[] key = this.key;
        final Object[] value = this.value;
        final int mask = n - 1;
        final Object[] key2 = new Object[n + 1];
        final Object[] value2 = new Object[n + 1];
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
    
    public Object2ObjectOpenHashMap clone() {
        final Object2ObjectOpenHashMap object2ObjectOpenHashMap = (Object2ObjectOpenHashMap)super.clone();
        object2ObjectOpenHashMap.keys = null;
        object2ObjectOpenHashMap.values = null;
        object2ObjectOpenHashMap.entries = null;
        object2ObjectOpenHashMap.containsNullKey = this.containsNullKey;
        object2ObjectOpenHashMap.key = this.key.clone();
        object2ObjectOpenHashMap.value = this.value.clone();
        return object2ObjectOpenHashMap;
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
            if (this != this.value[0]) {
                final int n2 = 0x0 ^ ((this.value[0] == null) ? 0 : this.value[0].hashCode());
            }
            ++n;
        }
        if (this.containsNullKey) {
            final int n3 = 0 + ((this.value[this.n] == null) ? 0 : this.value[this.n].hashCode());
        }
        return 0;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        final Object[] key = this.key;
        final Object[] value = this.value;
        final EntryIterator entryIterator = new EntryIterator(null);
        objectOutputStream.defaultWriteObject();
        int size = this.size;
        while (size-- != 0) {
            final int nextEntry = entryIterator.nextEntry();
            objectOutputStream.writeObject(key[nextEntry]);
            objectOutputStream.writeObject(value[nextEntry]);
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
        final Object[] value = new Object[this.n + 1];
        this.value = value;
        final Object[] array2 = value;
        int size = this.size;
        while (size-- != 0) {
            final Object object = objectInputStream.readObject();
            final Object object2 = objectInputStream.readObject();
            int n;
            if (object == null) {
                n = this.n;
                this.containsNullKey = true;
            }
            else {
                for (n = (HashCommon.mix(object.hashCode()) & this.mask); array[n] != null; n = (n + 1 & this.mask)) {}
            }
            array[n] = object;
            array2[n] = object2;
        }
    }
    
    private void checkTable() {
    }
    
    @Override
    public ObjectSet object2ObjectEntrySet() {
        return this.object2ObjectEntrySet();
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
    
    static int access$100(final Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
        return object2ObjectOpenHashMap.realSize();
    }
    
    static Object access$400(final Object2ObjectOpenHashMap object2ObjectOpenHashMap) {
        return object2ObjectOpenHashMap.removeNullEntry();
    }
    
    static Object access$500(final Object2ObjectOpenHashMap object2ObjectOpenHashMap, final int n) {
        return object2ObjectOpenHashMap.removeEntry(n);
    }
    
    private final class MapEntrySet extends AbstractObjectSet implements Object2ObjectMap.FastEntrySet
    {
        final Object2ObjectOpenHashMap this$0;
        
        private MapEntrySet(final Object2ObjectOpenHashMap this$0) {
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
            final Object key = entry.getKey();
            final V value = entry.getValue();
            if (key == null) {
                return this.this$0.containsNullKey && Objects.equals(this.this$0.value[this.this$0.n], value);
            }
            final Object[] key2 = this.this$0.key;
            int n;
            final Object o2;
            if ((o2 = key2[n = (HashCommon.mix(key.hashCode()) & this.this$0.mask)]) == null) {
                return false;
            }
            if (key.equals(o2)) {
                return Objects.equals(this.this$0.value[n], value);
            }
            Object o3;
            while ((o3 = key2[n = (n + 1 & this.this$0.mask)]) != null) {
                if (key.equals(o3)) {
                    return Objects.equals(this.this$0.value[n], value);
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
            final Object key = entry.getKey();
            final V value = entry.getValue();
            if (key == null) {
                if (this.this$0.containsNullKey && Objects.equals(this.this$0.value[this.this$0.n], value)) {
                    Object2ObjectOpenHashMap.access$400(this.this$0);
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
                        if (o3.equals(key) && Objects.equals(this.this$0.value[n], value)) {
                            Object2ObjectOpenHashMap.access$500(this.this$0, n);
                            return true;
                        }
                    }
                    return false;
                }
                if (Objects.equals(this.this$0.value[n], value)) {
                    Object2ObjectOpenHashMap.access$500(this.this$0, n);
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
        
        MapEntrySet(final Object2ObjectOpenHashMap object2ObjectOpenHashMap, final Object2ObjectOpenHashMap$1 abstractObjectCollection) {
            this(object2ObjectOpenHashMap);
        }
    }
    
    private final class EntryIterator extends MapIterator implements ObjectIterator
    {
        private MapEntry entry;
        final Object2ObjectOpenHashMap this$0;
        
        private EntryIterator(final Object2ObjectOpenHashMap this$0) {
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
        
        EntryIterator(final Object2ObjectOpenHashMap object2ObjectOpenHashMap, final Object2ObjectOpenHashMap$1 abstractObjectCollection) {
            this(object2ObjectOpenHashMap);
        }
    }
    
    private abstract class MapIterator
    {
        int pos;
        int last;
        int c;
        boolean mustReturnNullKey;
        ObjectArrayList wrapped;
        final Object2ObjectOpenHashMap this$0;
        
        private MapIterator(final Object2ObjectOpenHashMap this$0) {
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
                    Label_0112: {
                        if (n2 <= n) {
                            if (n2 >= n3) {
                                break Label_0112;
                            }
                            if (n3 > n) {
                                break Label_0112;
                            }
                        }
                        else if (n2 >= n3 && n3 > n) {
                            break Label_0112;
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
            this.this$0.value[n2] = null;
        }
        
        public void remove() {
            if (this.last == -1) {
                throw new IllegalStateException();
            }
            if (this.last == this.this$0.n) {
                this.this$0.containsNullKey = false;
                this.this$0.key[this.this$0.n] = null;
                this.this$0.value[this.this$0.n] = null;
            }
            else {
                if (this.pos < 0) {
                    this.this$0.remove(this.wrapped.set(-this.pos - 1, null));
                    this.last = -1;
                    return;
                }
                this.shiftKeys(this.last);
            }
            final Object2ObjectOpenHashMap this$0 = this.this$0;
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
        
        MapIterator(final Object2ObjectOpenHashMap object2ObjectOpenHashMap, final Object2ObjectOpenHashMap$1 abstractObjectCollection) {
            this(object2ObjectOpenHashMap);
        }
    }
    
    final class MapEntry implements Object2ObjectMap.Entry, Map.Entry, Pair
    {
        int index;
        final Object2ObjectOpenHashMap this$0;
        
        MapEntry(final Object2ObjectOpenHashMap this$0, final int index) {
            this.this$0 = this$0;
            this.index = index;
        }
        
        MapEntry(final Object2ObjectOpenHashMap this$0) {
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
        public Pair right(final Object o) {
            this.this$0.value[this.index] = o;
            return this;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            return Objects.equals(this.this$0.key[this.index], entry.getKey()) && Objects.equals(this.this$0.value[this.index], entry.getValue());
        }
        
        @Override
        public int hashCode() {
            return ((this.this$0.key[this.index] == null) ? 0 : this.this$0.key[this.index].hashCode()) ^ ((this.this$0.value[this.index] == null) ? 0 : this.this$0.value[this.index].hashCode());
        }
        
        @Override
        public String toString() {
            return this.this$0.key[this.index] + "=>" + this.this$0.value[this.index];
        }
    }
    
    private final class FastEntryIterator extends MapIterator implements ObjectIterator
    {
        private final MapEntry entry;
        final Object2ObjectOpenHashMap this$0;
        
        private FastEntryIterator(final Object2ObjectOpenHashMap this$0) {
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
        
        FastEntryIterator(final Object2ObjectOpenHashMap object2ObjectOpenHashMap, final Object2ObjectOpenHashMap$1 abstractObjectCollection) {
            this(object2ObjectOpenHashMap);
        }
    }
    
    private final class EntrySpliterator extends MapSpliterator implements ObjectSpliterator
    {
        private static final int POST_SPLIT_CHARACTERISTICS = 1;
        final Object2ObjectOpenHashMap this$0;
        
        EntrySpliterator(final Object2ObjectOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        EntrySpliterator(final Object2ObjectOpenHashMap this$0, final int n, final int n2, final boolean b, final boolean b2) {
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
        final Object2ObjectOpenHashMap this$0;
        
        MapSpliterator(final Object2ObjectOpenHashMap this$0) {
            this.this$0 = this$0;
            this.pos = 0;
            this.max = this.this$0.n;
            this.c = 0;
            this.mustReturnNull = this.this$0.containsNullKey;
            this.hasSplit = false;
        }
        
        MapSpliterator(final Object2ObjectOpenHashMap this$0, final int pos, final int max, final boolean mustReturnNull, final boolean hasSplit) {
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
            return Math.min(this.this$0.size - this.c, (long)(Object2ObjectOpenHashMap.access$100(this.this$0) / (double)this.this$0.n * (this.max - this.pos)) + (long)(this.mustReturnNull ? 1 : 0));
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
        final Object2ObjectOpenHashMap this$0;
        
        private KeySet(final Object2ObjectOpenHashMap this$0) {
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
            this.this$0.remove(o);
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
        
        KeySet(final Object2ObjectOpenHashMap object2ObjectOpenHashMap, final Object2ObjectOpenHashMap$1 abstractObjectCollection) {
            this(object2ObjectOpenHashMap);
        }
    }
    
    private final class KeyIterator extends MapIterator implements ObjectIterator
    {
        final Object2ObjectOpenHashMap this$0;
        
        public KeyIterator(final Object2ObjectOpenHashMap this$0) {
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
        final Object2ObjectOpenHashMap this$0;
        
        KeySpliterator(final Object2ObjectOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        KeySpliterator(final Object2ObjectOpenHashMap this$0, final int n, final int n2, final boolean b, final boolean b2) {
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
    
    private final class ValueSpliterator extends MapSpliterator implements ObjectSpliterator
    {
        private static final int POST_SPLIT_CHARACTERISTICS = 0;
        final Object2ObjectOpenHashMap this$0;
        
        ValueSpliterator(final Object2ObjectOpenHashMap this$0) {
            this.this$0 = this$0.super();
        }
        
        ValueSpliterator(final Object2ObjectOpenHashMap this$0, final int n, final int n2, final boolean b, final boolean b2) {
            this.this$0 = this$0.super(n, n2, b, b2);
        }
        
        @Override
        public int characteristics() {
            return this.hasSplit ? 0 : 64;
        }
        
        final void acceptOnIndex(final Consumer consumer, final int n) {
            consumer.accept(this.this$0.value[n]);
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
    
    private final class ValueIterator extends MapIterator implements ObjectIterator
    {
        final Object2ObjectOpenHashMap this$0;
        
        public ValueIterator(final Object2ObjectOpenHashMap this$0) {
            this.this$0 = this$0.super(null);
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
