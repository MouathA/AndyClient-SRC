package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.io.*;
import com.google.common.base.*;
import java.util.*;

@GwtCompatible(emulated = true)
public final class HashBiMap extends AbstractMap implements BiMap, Serializable
{
    private static final double LOAD_FACTOR = 1.0;
    private transient BiEntry[] hashTableKToV;
    private transient BiEntry[] hashTableVToK;
    private transient int size;
    private transient int mask;
    private transient int modCount;
    private transient BiMap inverse;
    @GwtIncompatible("Not needed in emulated source")
    private static final long serialVersionUID = 0L;
    
    public static HashBiMap create() {
        return create(16);
    }
    
    public static HashBiMap create(final int n) {
        return new HashBiMap(n);
    }
    
    public static HashBiMap create(final Map map) {
        final HashBiMap create = create(map.size());
        create.putAll(map);
        return create;
    }
    
    private HashBiMap(final int n) {
        this.init(n);
    }
    
    private void init(final int n) {
        CollectPreconditions.checkNonnegative(n, "expectedSize");
        final int closedTableSize = Hashing.closedTableSize(n, 1.0);
        this.hashTableKToV = this.createTable(closedTableSize);
        this.hashTableVToK = this.createTable(closedTableSize);
        this.mask = closedTableSize - 1;
        this.modCount = 0;
        this.size = 0;
    }
    
    private void delete(final BiEntry biEntry) {
        final int n = biEntry.keyHash & this.mask;
        BiEntry biEntry2 = null;
        for (BiEntry nextInKToVBucket = this.hashTableKToV[n]; nextInKToVBucket != biEntry; nextInKToVBucket = nextInKToVBucket.nextInKToVBucket) {
            biEntry2 = nextInKToVBucket;
        }
        if (biEntry2 == null) {
            this.hashTableKToV[n] = biEntry.nextInKToVBucket;
        }
        else {
            biEntry2.nextInKToVBucket = biEntry.nextInKToVBucket;
        }
        final int n2 = biEntry.valueHash & this.mask;
        BiEntry biEntry3 = null;
        for (BiEntry nextInVToKBucket = this.hashTableVToK[n2]; nextInVToKBucket != biEntry; nextInVToKBucket = nextInVToKBucket.nextInVToKBucket) {
            biEntry3 = nextInVToKBucket;
        }
        if (biEntry3 == null) {
            this.hashTableVToK[n2] = biEntry.nextInVToKBucket;
        }
        else {
            biEntry3.nextInVToKBucket = biEntry.nextInVToKBucket;
        }
        --this.size;
        ++this.modCount;
    }
    
    private void insert(final BiEntry biEntry) {
        final int n = biEntry.keyHash & this.mask;
        biEntry.nextInKToVBucket = this.hashTableKToV[n];
        this.hashTableKToV[n] = biEntry;
        final int n2 = biEntry.valueHash & this.mask;
        biEntry.nextInVToKBucket = this.hashTableVToK[n2];
        this.hashTableVToK[n2] = biEntry;
        ++this.size;
        ++this.modCount;
    }
    
    private static int hash(@Nullable final Object o) {
        return Hashing.smear((o == null) ? 0 : o.hashCode());
    }
    
    private BiEntry seekByKey(@Nullable final Object o, final int n) {
        for (BiEntry nextInKToVBucket = this.hashTableKToV[n & this.mask]; nextInKToVBucket != null; nextInKToVBucket = nextInKToVBucket.nextInKToVBucket) {
            if (n == nextInKToVBucket.keyHash && Objects.equal(o, nextInKToVBucket.key)) {
                return nextInKToVBucket;
            }
        }
        return null;
    }
    
    private BiEntry seekByValue(@Nullable final Object o, final int n) {
        for (BiEntry nextInVToKBucket = this.hashTableVToK[n & this.mask]; nextInVToKBucket != null; nextInVToKBucket = nextInVToKBucket.nextInVToKBucket) {
            if (n == nextInVToKBucket.valueHash && Objects.equal(o, nextInVToKBucket.value)) {
                return nextInVToKBucket;
            }
        }
        return null;
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        return this.seekByKey(o, hash(o)) != null;
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return this.seekByValue(o, hash(o)) != null;
    }
    
    @Nullable
    @Override
    public Object get(@Nullable final Object o) {
        final BiEntry seekByKey = this.seekByKey(o, hash(o));
        return (seekByKey == null) ? null : seekByKey.value;
    }
    
    @Override
    public Object put(@Nullable final Object o, @Nullable final Object o2) {
        return this.put(o, o2, false);
    }
    
    @Override
    public Object forcePut(@Nullable final Object o, @Nullable final Object o2) {
        return this.put(o, o2, true);
    }
    
    private Object put(@Nullable final Object o, @Nullable final Object o2, final boolean b) {
        final int hash = hash(o);
        final int hash2 = hash(o2);
        final BiEntry seekByKey = this.seekByKey(o, hash);
        if (seekByKey != null && hash2 == seekByKey.valueHash && Objects.equal(o2, seekByKey.value)) {
            return o2;
        }
        final BiEntry seekByValue = this.seekByValue(o2, hash2);
        if (seekByValue != null) {
            if (!b) {
                throw new IllegalArgumentException("value already present: " + o2);
            }
            this.delete(seekByValue);
        }
        if (seekByKey != null) {
            this.delete(seekByKey);
        }
        this.insert(new BiEntry(o, hash, o2, hash2));
        this.rehashIfNecessary();
        return (seekByKey == null) ? null : seekByKey.value;
    }
    
    @Nullable
    private Object putInverse(@Nullable final Object o, @Nullable final Object o2, final boolean b) {
        final int hash = hash(o);
        final int hash2 = hash(o2);
        final BiEntry seekByValue = this.seekByValue(o, hash);
        if (seekByValue != null && hash2 == seekByValue.keyHash && Objects.equal(o2, seekByValue.key)) {
            return o2;
        }
        final BiEntry seekByKey = this.seekByKey(o2, hash2);
        if (seekByKey != null) {
            if (!b) {
                throw new IllegalArgumentException("value already present: " + o2);
            }
            this.delete(seekByKey);
        }
        if (seekByValue != null) {
            this.delete(seekByValue);
        }
        this.insert(new BiEntry(o2, hash2, o, hash));
        this.rehashIfNecessary();
        return (seekByValue == null) ? null : seekByValue.key;
    }
    
    private void rehashIfNecessary() {
        final BiEntry[] hashTableKToV = this.hashTableKToV;
        if (Hashing.needsResizing(this.size, hashTableKToV.length, 1.0)) {
            final int n = hashTableKToV.length * 2;
            this.hashTableKToV = this.createTable(n);
            this.hashTableVToK = this.createTable(n);
            this.mask = n - 1;
            this.size = 0;
            while (0 < hashTableKToV.length) {
                BiEntry nextInKToVBucket;
                for (BiEntry biEntry = hashTableKToV[0]; biEntry != null; biEntry = nextInKToVBucket) {
                    nextInKToVBucket = biEntry.nextInKToVBucket;
                    this.insert(biEntry);
                }
                int n2 = 0;
                ++n2;
            }
            ++this.modCount;
        }
    }
    
    private BiEntry[] createTable(final int n) {
        return new BiEntry[n];
    }
    
    @Override
    public Object remove(@Nullable final Object o) {
        final BiEntry seekByKey = this.seekByKey(o, hash(o));
        if (seekByKey == null) {
            return null;
        }
        this.delete(seekByKey);
        return seekByKey.value;
    }
    
    @Override
    public void clear() {
        this.size = 0;
        Arrays.fill(this.hashTableKToV, null);
        Arrays.fill(this.hashTableVToK, null);
        ++this.modCount;
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public Set keySet() {
        return new KeySet();
    }
    
    @Override
    public Set values() {
        return this.inverse().keySet();
    }
    
    @Override
    public Set entrySet() {
        return new EntrySet(null);
    }
    
    @Override
    public BiMap inverse() {
        return (this.inverse == null) ? (this.inverse = new Inverse(null)) : this.inverse;
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMap(this, objectOutputStream);
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final int count = Serialization.readCount(objectInputStream);
        this.init(count);
        Serialization.populateMap(this, objectInputStream, count);
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    static int access$000(final HashBiMap hashBiMap) {
        return hashBiMap.modCount;
    }
    
    static BiEntry[] access$100(final HashBiMap hashBiMap) {
        return hashBiMap.hashTableKToV;
    }
    
    static void access$200(final HashBiMap hashBiMap, final BiEntry biEntry) {
        hashBiMap.delete(biEntry);
    }
    
    static int access$300(final Object o) {
        return hash(o);
    }
    
    static BiEntry access$400(final HashBiMap hashBiMap, final Object o, final int n) {
        return hashBiMap.seekByKey(o, n);
    }
    
    static BiEntry access$600(final HashBiMap hashBiMap, final Object o, final int n) {
        return hashBiMap.seekByValue(o, n);
    }
    
    static void access$700(final HashBiMap hashBiMap, final BiEntry biEntry) {
        hashBiMap.insert(biEntry);
    }
    
    static int access$900(final HashBiMap hashBiMap) {
        return hashBiMap.size;
    }
    
    static Object access$1000(final HashBiMap hashBiMap, final Object o, final Object o2, final boolean b) {
        return hashBiMap.putInverse(o, o2, b);
    }
    
    private static final class InverseSerializedForm implements Serializable
    {
        private final HashBiMap bimap;
        
        InverseSerializedForm(final HashBiMap bimap) {
            this.bimap = bimap;
        }
        
        Object readResolve() {
            return this.bimap.inverse();
        }
    }
    
    private final class Inverse extends AbstractMap implements BiMap, Serializable
    {
        final HashBiMap this$0;
        
        private Inverse(final HashBiMap this$0) {
            this.this$0 = this$0;
        }
        
        BiMap forward() {
            return this.this$0;
        }
        
        @Override
        public int size() {
            return HashBiMap.access$900(this.this$0);
        }
        
        @Override
        public void clear() {
            this.forward().clear();
        }
        
        @Override
        public boolean containsKey(@Nullable final Object o) {
            return this.forward().containsValue(o);
        }
        
        @Override
        public Object get(@Nullable final Object o) {
            final BiEntry access$600 = HashBiMap.access$600(this.this$0, o, HashBiMap.access$300(o));
            return (access$600 == null) ? null : access$600.key;
        }
        
        @Override
        public Object put(@Nullable final Object o, @Nullable final Object o2) {
            return HashBiMap.access$1000(this.this$0, o, o2, false);
        }
        
        @Override
        public Object forcePut(@Nullable final Object o, @Nullable final Object o2) {
            return HashBiMap.access$1000(this.this$0, o, o2, true);
        }
        
        @Override
        public Object remove(@Nullable final Object o) {
            final BiEntry access$600 = HashBiMap.access$600(this.this$0, o, HashBiMap.access$300(o));
            if (access$600 == null) {
                return null;
            }
            HashBiMap.access$200(this.this$0, access$600);
            return access$600.key;
        }
        
        @Override
        public BiMap inverse() {
            return this.forward();
        }
        
        @Override
        public Set keySet() {
            return new InverseKeySet();
        }
        
        @Override
        public Set values() {
            return this.forward().keySet();
        }
        
        @Override
        public Set entrySet() {
            return new Maps.EntrySet() {
                final Inverse this$1;
                
                @Override
                Map map() {
                    return this.this$1;
                }
                
                @Override
                public Iterator iterator() {
                    return new Itr() {
                        final HashBiMap$Inverse$1 this$2;
                        
                        @Override
                        Map.Entry output(final BiEntry biEntry) {
                            return new InverseEntry(biEntry);
                        }
                        
                        @Override
                        Object output(final BiEntry biEntry) {
                            return this.output(biEntry);
                        }
                        
                        class InverseEntry extends AbstractMapEntry
                        {
                            BiEntry delegate;
                            final HashBiMap$Inverse$1$1 this$3;
                            
                            InverseEntry(final HashBiMap$Inverse$1$1 this$3, final BiEntry delegate) {
                                this.this$3 = this$3;
                                this.delegate = delegate;
                            }
                            
                            @Override
                            public Object getKey() {
                                return this.delegate.value;
                            }
                            
                            @Override
                            public Object getValue() {
                                return this.delegate.key;
                            }
                            
                            @Override
                            public Object setValue(final Object o) {
                                final Object key = this.delegate.key;
                                final int access$300 = HashBiMap.access$300(o);
                                if (access$300 == this.delegate.keyHash && Objects.equal(o, key)) {
                                    return o;
                                }
                                Preconditions.checkArgument(HashBiMap.access$400(this.this$3.this$2.this$1.this$0, o, access$300) == null, "value already present: %s", o);
                                HashBiMap.access$200(this.this$3.this$2.this$1.this$0, this.delegate);
                                HashBiMap.access$700(this.this$3.this$2.this$1.this$0, new BiEntry(o, access$300, this.delegate.value, this.delegate.valueHash));
                                this.this$3.expectedModCount = HashBiMap.access$000(this.this$3.this$2.this$1.this$0);
                                return key;
                            }
                        }
                    };
                }
            };
        }
        
        Object writeReplace() {
            return new InverseSerializedForm(this.this$0);
        }
        
        @Override
        public Collection values() {
            return this.values();
        }
        
        Inverse(final HashBiMap hashBiMap, final HashBiMap$1 object) {
            this(hashBiMap);
        }
        
        private final class InverseKeySet extends Maps.KeySet
        {
            final Inverse this$1;
            
            InverseKeySet(final Inverse this$1) {
                super(this.this$1 = this$1);
            }
            
            @Override
            public boolean remove(@Nullable final Object o) {
                final BiEntry access$600 = HashBiMap.access$600(this.this$1.this$0, o, HashBiMap.access$300(o));
                if (access$600 == null) {
                    return false;
                }
                HashBiMap.access$200(this.this$1.this$0, access$600);
                return true;
            }
            
            @Override
            public Iterator iterator() {
                return new Itr() {
                    final InverseKeySet this$2;
                    
                    @Override
                    Object output(final BiEntry biEntry) {
                        return biEntry.value;
                    }
                };
            }
        }
    }
    
    private static final class BiEntry extends ImmutableEntry
    {
        final int keyHash;
        final int valueHash;
        @Nullable
        BiEntry nextInKToVBucket;
        @Nullable
        BiEntry nextInVToKBucket;
        
        BiEntry(final Object o, final int keyHash, final Object o2, final int valueHash) {
            super(o, o2);
            this.keyHash = keyHash;
            this.valueHash = valueHash;
        }
    }
    
    abstract class Itr implements Iterator
    {
        int nextBucket;
        BiEntry next;
        BiEntry toRemove;
        int expectedModCount;
        final HashBiMap this$0;
        
        Itr(final HashBiMap this$0) {
            this.this$0 = this$0;
            this.nextBucket = 0;
            this.next = null;
            this.toRemove = null;
            this.expectedModCount = HashBiMap.access$000(this.this$0);
        }
        
        private void checkForConcurrentModification() {
            if (HashBiMap.access$000(this.this$0) != this.expectedModCount) {
                throw new ConcurrentModificationException();
            }
        }
        
        @Override
        public boolean hasNext() {
            this.checkForConcurrentModification();
            if (this.next != null) {
                return true;
            }
            while (this.nextBucket < HashBiMap.access$100(this.this$0).length) {
                if (HashBiMap.access$100(this.this$0)[this.nextBucket] != null) {
                    this.next = HashBiMap.access$100(this.this$0)[this.nextBucket++];
                    return true;
                }
                ++this.nextBucket;
            }
            return false;
        }
        
        @Override
        public Object next() {
            this.checkForConcurrentModification();
            if (!this.hasNext()) {
                throw new NoSuchElementException();
            }
            final BiEntry next = this.next;
            this.next = next.nextInKToVBucket;
            this.toRemove = next;
            return this.output(next);
        }
        
        @Override
        public void remove() {
            this.checkForConcurrentModification();
            CollectPreconditions.checkRemove(this.toRemove != null);
            HashBiMap.access$200(this.this$0, this.toRemove);
            this.expectedModCount = HashBiMap.access$000(this.this$0);
            this.toRemove = null;
        }
        
        abstract Object output(final BiEntry p0);
    }
    
    private final class EntrySet extends Maps.EntrySet
    {
        final HashBiMap this$0;
        
        private EntrySet(final HashBiMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        Map map() {
            return this.this$0;
        }
        
        @Override
        public Iterator iterator() {
            return new Itr() {
                final EntrySet this$1;
                
                @Override
                Map.Entry output(final BiEntry biEntry) {
                    return new MapEntry(biEntry);
                }
                
                @Override
                Object output(final BiEntry biEntry) {
                    return this.output(biEntry);
                }
                
                class MapEntry extends AbstractMapEntry
                {
                    BiEntry delegate;
                    final HashBiMap$EntrySet$1 this$2;
                    
                    MapEntry(final HashBiMap$EntrySet$1 this$2, final BiEntry delegate) {
                        this.this$2 = this$2;
                        this.delegate = delegate;
                    }
                    
                    @Override
                    public Object getKey() {
                        return this.delegate.key;
                    }
                    
                    @Override
                    public Object getValue() {
                        return this.delegate.value;
                    }
                    
                    @Override
                    public Object setValue(final Object o) {
                        final Object value = this.delegate.value;
                        final int access$300 = HashBiMap.access$300(o);
                        if (access$300 == this.delegate.valueHash && Objects.equal(o, value)) {
                            return o;
                        }
                        Preconditions.checkArgument(HashBiMap.access$600(this.this$2.this$1.this$0, o, access$300) == null, "value already present: %s", o);
                        HashBiMap.access$200(this.this$2.this$1.this$0, this.delegate);
                        final BiEntry biEntry = new BiEntry(this.delegate.key, this.delegate.keyHash, o, access$300);
                        HashBiMap.access$700(this.this$2.this$1.this$0, biEntry);
                        this.this$2.expectedModCount = HashBiMap.access$000(this.this$2.this$1.this$0);
                        if (this.this$2.toRemove == this.delegate) {
                            this.this$2.toRemove = biEntry;
                        }
                        this.delegate = biEntry;
                        return value;
                    }
                }
            };
        }
        
        EntrySet(final HashBiMap hashBiMap, final HashBiMap$1 object) {
            this(hashBiMap);
        }
    }
    
    private final class KeySet extends Maps.KeySet
    {
        final HashBiMap this$0;
        
        KeySet(final HashBiMap this$0) {
            super(this.this$0 = this$0);
        }
        
        @Override
        public Iterator iterator() {
            return new Itr() {
                final KeySet this$1;
                
                @Override
                Object output(final BiEntry biEntry) {
                    return biEntry.key;
                }
            };
        }
        
        @Override
        public boolean remove(@Nullable final Object o) {
            final BiEntry access$400 = HashBiMap.access$400(this.this$0, o, HashBiMap.access$300(o));
            if (access$400 == null) {
                return false;
            }
            HashBiMap.access$200(this.this$0, access$400);
            return true;
        }
    }
}
