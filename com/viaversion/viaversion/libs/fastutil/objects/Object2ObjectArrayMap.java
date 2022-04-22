package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.function.*;
import java.util.*;

public class Object2ObjectArrayMap extends AbstractObject2ObjectMap implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient Object[] value;
    private int size;
    private transient Object2ObjectMap.FastEntrySet entries;
    private transient ObjectSet keys;
    private transient ObjectCollection values;
    
    public Object2ObjectArrayMap(final Object[] key, final Object[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }
    
    public Object2ObjectArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }
    
    public Object2ObjectArrayMap(final int n) {
        this.key = new Object[n];
        this.value = new Object[n];
    }
    
    public Object2ObjectArrayMap(final Object2ObjectMap object2ObjectMap) {
        this(object2ObjectMap.size());
        for (final Object2ObjectMap.Entry entry : object2ObjectMap.object2ObjectEntrySet()) {
            this.key[0] = entry.getKey();
            this.value[0] = entry.getValue();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public Object2ObjectArrayMap(final Map map) {
        this(map.size());
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            this.key[0] = entry.getKey();
            this.value[0] = entry.getValue();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public Object2ObjectArrayMap(final Object[] key, final Object[] value, final int size) {
        this.key = key;
        this.value = value;
        this.size = size;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
        if (size > key.length) {
            throw new IllegalArgumentException("The provided size (" + size + ") is larger than or equal to the backing-arrays size (" + key.length + ")");
        }
    }
    
    @Override
    public Object2ObjectMap.FastEntrySet object2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new EntrySet(null);
        }
        return this.entries;
    }
    
    private int findKey(final Object o) {
        final Object[] key = this.key;
        int size = this.size;
        while (size-- != 0) {
            if (Objects.equals(key[size], o)) {
                return size;
            }
        }
        return -1;
    }
    
    @Override
    public Object get(final Object o) {
        final Object[] key = this.key;
        int size = this.size;
        while (size-- != 0) {
            if (Objects.equals(key[size], o)) {
                return this.value[size];
            }
        }
        return this.defRetValue;
    }
    
    @Override
    public int size() {
        return this.size;
    }
    
    @Override
    public void clear() {
        int size = this.size;
        while (size-- != 0) {
            this.key[size] = null;
            this.value[size] = null;
        }
        this.size = 0;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.findKey(o) != -1;
    }
    
    @Override
    public boolean containsValue(final Object o) {
        int size = this.size;
        while (size-- != 0) {
            if (Objects.equals(this.value[size], o)) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        final int key = this.findKey(o);
        if (key != -1) {
            final Object o3 = this.value[key];
            this.value[key] = o2;
            return o3;
        }
        if (this.size == this.key.length) {
            final Object[] key2 = new Object[(this.size == 0) ? 2 : (this.size * 2)];
            final Object[] value = new Object[(this.size == 0) ? 2 : (this.size * 2)];
            int size = this.size;
            while (size-- != 0) {
                key2[size] = this.key[size];
                value[size] = this.value[size];
            }
            this.key = key2;
            this.value = value;
        }
        this.key[this.size] = o;
        this.value[this.size] = o2;
        ++this.size;
        return this.defRetValue;
    }
    
    @Override
    public Object remove(final Object o) {
        final int key = this.findKey(o);
        if (key == -1) {
            return this.defRetValue;
        }
        final Object o2 = this.value[key];
        final int n = this.size - key - 1;
        System.arraycopy(this.key, key + 1, this.key, key, n);
        System.arraycopy(this.value, key + 1, this.value, key, n);
        --this.size;
        this.key[this.size] = null;
        this.value[this.size] = null;
        return o2;
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
            this.values = new ValuesCollection(null);
        }
        return this.values;
    }
    
    public Object2ObjectArrayMap clone() {
        final Object2ObjectArrayMap object2ObjectArrayMap = (Object2ObjectArrayMap)super.clone();
        object2ObjectArrayMap.key = this.key.clone();
        object2ObjectArrayMap.value = this.value.clone();
        object2ObjectArrayMap.entries = null;
        object2ObjectArrayMap.keys = null;
        object2ObjectArrayMap.values = null;
        return object2ObjectArrayMap;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        while (0 < this.size) {
            objectOutputStream.writeObject(this.key[0]);
            objectOutputStream.writeObject(this.value[0]);
            int n = 0;
            ++n;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new Object[this.size];
        while (0 < this.size) {
            this.key[0] = objectInputStream.readObject();
            this.value[0] = objectInputStream.readObject();
            int n = 0;
            ++n;
        }
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
    
    static int access$000(final Object2ObjectArrayMap object2ObjectArrayMap) {
        return object2ObjectArrayMap.size;
    }
    
    static Object[] access$100(final Object2ObjectArrayMap object2ObjectArrayMap) {
        return object2ObjectArrayMap.key;
    }
    
    static Object[] access$200(final Object2ObjectArrayMap object2ObjectArrayMap) {
        return object2ObjectArrayMap.value;
    }
    
    static int access$010(final Object2ObjectArrayMap object2ObjectArrayMap) {
        return object2ObjectArrayMap.size--;
    }
    
    static int access$300(final Object2ObjectArrayMap object2ObjectArrayMap, final Object o) {
        return object2ObjectArrayMap.findKey(o);
    }
    
    private final class EntrySet extends AbstractObjectSet implements Object2ObjectMap.FastEntrySet
    {
        final Object2ObjectArrayMap this$0;
        
        private EntrySet(final Object2ObjectArrayMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public ObjectIterator iterator() {
            return new ObjectIterator() {
                int curr = -1;
                int next = 0;
                final EntrySet this$1;
                
                @Override
                public boolean hasNext() {
                    return this.next < Object2ObjectArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public Object2ObjectMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final Object[] access$100 = Object2ObjectArrayMap.access$100(this.this$1.this$0);
                    final int next = this.next;
                    this.curr = next;
                    return new BasicEntry(access$100[next], Object2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }
                
                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    final int n = Object2ObjectArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2ObjectArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2ObjectArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2ObjectArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2ObjectArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2ObjectArrayMap.access$100(this.this$1.this$0)[Object2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                    Object2ObjectArrayMap.access$200(this.this$1.this$0)[Object2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.next < Object2ObjectArrayMap.access$000(this.this$1.this$0)) {
                        final Object[] access$100 = Object2ObjectArrayMap.access$100(this.this$1.this$0);
                        final int next = this.next;
                        this.curr = next;
                        consumer.accept(new BasicEntry(access$100[next], Object2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++]));
                    }
                }
                
                @Override
                public Object next() {
                    return this.next();
                }
            };
        }
        
        @Override
        public ObjectIterator fastIterator() {
            return new ObjectIterator() {
                int next = 0;
                int curr = -1;
                final BasicEntry entry = new BasicEntry();
                final EntrySet this$1;
                
                @Override
                public boolean hasNext() {
                    return this.next < Object2ObjectArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public Object2ObjectMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final BasicEntry entry = this.entry;
                    final Object[] access$100 = Object2ObjectArrayMap.access$100(this.this$1.this$0);
                    final int next = this.next;
                    this.curr = next;
                    entry.key = access$100[next];
                    this.entry.value = Object2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }
                
                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    final int n = Object2ObjectArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2ObjectArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2ObjectArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2ObjectArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2ObjectArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2ObjectArrayMap.access$100(this.this$1.this$0)[Object2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                    Object2ObjectArrayMap.access$200(this.this$1.this$0)[Object2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.next < Object2ObjectArrayMap.access$000(this.this$1.this$0)) {
                        final BasicEntry entry = this.entry;
                        final Object[] access$100 = Object2ObjectArrayMap.access$100(this.this$1.this$0);
                        final int next = this.next;
                        this.curr = next;
                        entry.key = access$100[next];
                        this.entry.value = Object2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++];
                        consumer.accept(this.entry);
                    }
                }
                
                @Override
                public Object next() {
                    return this.next();
                }
            };
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return new EntrySetSpliterator(0, Object2ObjectArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            while (0 < Object2ObjectArrayMap.access$000(this.this$0)) {
                consumer.accept(new BasicEntry(Object2ObjectArrayMap.access$100(this.this$0)[0], Object2ObjectArrayMap.access$200(this.this$0)[0]));
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public void fastForEach(final Consumer consumer) {
            final BasicEntry basicEntry = new BasicEntry();
            while (0 < Object2ObjectArrayMap.access$000(this.this$0)) {
                basicEntry.key = Object2ObjectArrayMap.access$100(this.this$0)[0];
                basicEntry.value = Object2ObjectArrayMap.access$200(this.this$0)[0];
                consumer.accept(basicEntry);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Object2ObjectArrayMap.access$000(this.this$0);
        }
        
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            final Object key = entry.getKey();
            return this.this$0.containsKey(key) && Objects.equals(this.this$0.get(key), entry.getValue());
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            final Map.Entry entry = (Map.Entry)o;
            final Object key = entry.getKey();
            final V value = entry.getValue();
            final int access$300 = Object2ObjectArrayMap.access$300(this.this$0, key);
            if (access$300 == -1 || !Objects.equals(value, Object2ObjectArrayMap.access$200(this.this$0)[access$300])) {
                return false;
            }
            final int n = Object2ObjectArrayMap.access$000(this.this$0) - access$300 - 1;
            System.arraycopy(Object2ObjectArrayMap.access$100(this.this$0), access$300 + 1, Object2ObjectArrayMap.access$100(this.this$0), access$300, n);
            System.arraycopy(Object2ObjectArrayMap.access$200(this.this$0), access$300 + 1, Object2ObjectArrayMap.access$200(this.this$0), access$300, n);
            Object2ObjectArrayMap.access$010(this.this$0);
            Object2ObjectArrayMap.access$100(this.this$0)[Object2ObjectArrayMap.access$000(this.this$0)] = null;
            Object2ObjectArrayMap.access$200(this.this$0)[Object2ObjectArrayMap.access$000(this.this$0)] = null;
            return true;
        }
        
        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        EntrySet(final Object2ObjectArrayMap object2ObjectArrayMap, final Object2ObjectArrayMap$1 object) {
            this(object2ObjectArrayMap);
        }
        
        final class EntrySetSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator implements ObjectSpliterator
        {
            final EntrySet this$1;
            
            EntrySetSpliterator(final EntrySet this$1, final int n, final int n2) {
                this.this$1 = this$1;
                super(n, n2);
            }
            
            @Override
            public int characteristics() {
                return 16465;
            }
            
            @Override
            protected final Object2ObjectMap.Entry get(final int n) {
                return new BasicEntry(Object2ObjectArrayMap.access$100(this.this$1.this$0)[n], Object2ObjectArrayMap.access$200(this.this$1.this$0)[n]);
            }
            
            @Override
            protected final EntrySetSpliterator makeForSplit(final int n, final int n2) {
                return this.this$1.new EntrySetSpliterator(n, n2);
            }
            
            @Override
            protected ObjectSpliterator makeForSplit(final int n, final int n2) {
                return this.makeForSplit(n, n2);
            }
            
            @Override
            protected Object get(final int n) {
                return this.get(n);
            }
        }
    }
    
    private final class KeySet extends AbstractObjectSet
    {
        final Object2ObjectArrayMap this$0;
        
        private KeySet(final Object2ObjectArrayMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean contains(final Object o) {
            return Object2ObjectArrayMap.access$300(this.this$0, o) != -1;
        }
        
        @Override
        public boolean remove(final Object o) {
            final int access$300 = Object2ObjectArrayMap.access$300(this.this$0, o);
            if (access$300 == -1) {
                return false;
            }
            final int n = Object2ObjectArrayMap.access$000(this.this$0) - access$300 - 1;
            System.arraycopy(Object2ObjectArrayMap.access$100(this.this$0), access$300 + 1, Object2ObjectArrayMap.access$100(this.this$0), access$300, n);
            System.arraycopy(Object2ObjectArrayMap.access$200(this.this$0), access$300 + 1, Object2ObjectArrayMap.access$200(this.this$0), access$300, n);
            Object2ObjectArrayMap.access$010(this.this$0);
            Object2ObjectArrayMap.access$100(this.this$0)[Object2ObjectArrayMap.access$000(this.this$0)] = null;
            Object2ObjectArrayMap.access$200(this.this$0)[Object2ObjectArrayMap.access$000(this.this$0)] = null;
            return true;
        }
        
        @Override
        public ObjectIterator iterator() {
            return new ObjectIterator() {
                int pos = 0;
                final KeySet this$1;
                
                @Override
                public boolean hasNext() {
                    return this.pos < Object2ObjectArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public Object next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return Object2ObjectArrayMap.access$100(this.this$1.this$0)[this.pos++];
                }
                
                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    final int n = Object2ObjectArrayMap.access$000(this.this$1.this$0) - this.pos;
                    System.arraycopy(Object2ObjectArrayMap.access$100(this.this$1.this$0), this.pos, Object2ObjectArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                    System.arraycopy(Object2ObjectArrayMap.access$200(this.this$1.this$0), this.pos, Object2ObjectArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                    Object2ObjectArrayMap.access$010(this.this$1.this$0);
                    --this.pos;
                    Object2ObjectArrayMap.access$100(this.this$1.this$0)[Object2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                    Object2ObjectArrayMap.access$200(this.this$1.this$0)[Object2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.pos < Object2ObjectArrayMap.access$000(this.this$1.this$0)) {
                        consumer.accept(Object2ObjectArrayMap.access$100(this.this$1.this$0)[this.pos++]);
                    }
                }
            };
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return new KeySetSpliterator(0, Object2ObjectArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            while (0 < Object2ObjectArrayMap.access$000(this.this$0)) {
                consumer.accept(Object2ObjectArrayMap.access$100(this.this$0)[0]);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Object2ObjectArrayMap.access$000(this.this$0);
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
        
        KeySet(final Object2ObjectArrayMap object2ObjectArrayMap, final Object2ObjectArrayMap$1 object) {
            this(object2ObjectArrayMap);
        }
        
        final class KeySetSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator implements ObjectSpliterator
        {
            final KeySet this$1;
            
            KeySetSpliterator(final KeySet this$1, final int n, final int n2) {
                this.this$1 = this$1;
                super(n, n2);
            }
            
            @Override
            public int characteristics() {
                return 16465;
            }
            
            @Override
            protected final Object get(final int n) {
                return Object2ObjectArrayMap.access$100(this.this$1.this$0)[n];
            }
            
            @Override
            protected final KeySetSpliterator makeForSplit(final int n, final int n2) {
                return this.this$1.new KeySetSpliterator(n, n2);
            }
            
            @Override
            public void forEachRemaining(final Consumer consumer) {
                while (this.pos < Object2ObjectArrayMap.access$000(this.this$1.this$0)) {
                    consumer.accept(Object2ObjectArrayMap.access$100(this.this$1.this$0)[this.pos++]);
                }
            }
            
            @Override
            protected ObjectSpliterator makeForSplit(final int n, final int n2) {
                return this.makeForSplit(n, n2);
            }
        }
    }
    
    private final class ValuesCollection extends AbstractObjectCollection
    {
        final Object2ObjectArrayMap this$0;
        
        private ValuesCollection(final Object2ObjectArrayMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean contains(final Object o) {
            return this.this$0.containsValue(o);
        }
        
        @Override
        public ObjectIterator iterator() {
            return new ObjectIterator() {
                int pos = 0;
                final ValuesCollection this$1;
                
                @Override
                public boolean hasNext() {
                    return this.pos < Object2ObjectArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public Object next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return Object2ObjectArrayMap.access$200(this.this$1.this$0)[this.pos++];
                }
                
                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    final int n = Object2ObjectArrayMap.access$000(this.this$1.this$0) - this.pos;
                    System.arraycopy(Object2ObjectArrayMap.access$100(this.this$1.this$0), this.pos, Object2ObjectArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                    System.arraycopy(Object2ObjectArrayMap.access$200(this.this$1.this$0), this.pos, Object2ObjectArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                    Object2ObjectArrayMap.access$010(this.this$1.this$0);
                    --this.pos;
                    Object2ObjectArrayMap.access$100(this.this$1.this$0)[Object2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                    Object2ObjectArrayMap.access$200(this.this$1.this$0)[Object2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.pos < Object2ObjectArrayMap.access$000(this.this$1.this$0)) {
                        consumer.accept(Object2ObjectArrayMap.access$200(this.this$1.this$0)[this.pos++]);
                    }
                }
            };
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return new ValuesSpliterator(0, Object2ObjectArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            while (0 < Object2ObjectArrayMap.access$000(this.this$0)) {
                consumer.accept(Object2ObjectArrayMap.access$200(this.this$0)[0]);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Object2ObjectArrayMap.access$000(this.this$0);
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
        
        ValuesCollection(final Object2ObjectArrayMap object2ObjectArrayMap, final Object2ObjectArrayMap$1 object) {
            this(object2ObjectArrayMap);
        }
        
        final class ValuesSpliterator extends ObjectSpliterators.EarlyBindingSizeIndexBasedSpliterator implements ObjectSpliterator
        {
            final ValuesCollection this$1;
            
            ValuesSpliterator(final ValuesCollection this$1, final int n, final int n2) {
                this.this$1 = this$1;
                super(n, n2);
            }
            
            @Override
            public int characteristics() {
                return 16464;
            }
            
            @Override
            protected final Object get(final int n) {
                return Object2ObjectArrayMap.access$200(this.this$1.this$0)[n];
            }
            
            @Override
            protected final ValuesSpliterator makeForSplit(final int n, final int n2) {
                return this.this$1.new ValuesSpliterator(n, n2);
            }
            
            @Override
            public void forEachRemaining(final Consumer consumer) {
                while (this.pos < Object2ObjectArrayMap.access$000(this.this$1.this$0)) {
                    consumer.accept(Object2ObjectArrayMap.access$200(this.this$1.this$0)[this.pos++]);
                }
            }
            
            @Override
            protected ObjectSpliterator makeForSplit(final int n, final int n2) {
                return this.makeForSplit(n, n2);
            }
        }
    }
}
