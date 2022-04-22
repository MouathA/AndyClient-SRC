package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;

public class Object2IntArrayMap extends AbstractObject2IntMap implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    private transient Object[] key;
    private transient int[] value;
    private int size;
    private transient Object2IntMap.FastEntrySet entries;
    private transient ObjectSet keys;
    private transient IntCollection values;
    
    public Object2IntArrayMap(final Object[] key, final int[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }
    
    public Object2IntArrayMap() {
        this.key = ObjectArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }
    
    public Object2IntArrayMap(final int n) {
        this.key = new Object[n];
        this.value = new int[n];
    }
    
    public Object2IntArrayMap(final Object2IntMap object2IntMap) {
        this(object2IntMap.size());
        for (final Object2IntMap.Entry entry : object2IntMap.object2IntEntrySet()) {
            this.key[0] = entry.getKey();
            this.value[0] = entry.getIntValue();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public Object2IntArrayMap(final Map map) {
        this(map.size());
        for (final Map.Entry<Object, V> entry : map.entrySet()) {
            this.key[0] = entry.getKey();
            this.value[0] = (int)entry.getValue();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public Object2IntArrayMap(final Object[] key, final int[] value, final int size) {
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
    public Object2IntMap.FastEntrySet object2IntEntrySet() {
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
    public int getInt(final Object o) {
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
        }
        this.size = 0;
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.findKey(o) != -1;
    }
    
    @Override
    public boolean containsValue(final int n) {
        int size = this.size;
        while (size-- != 0) {
            if (this.value[size] == n) {
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
    public int put(final Object o, final int n) {
        final int key = this.findKey(o);
        if (key != -1) {
            final int n2 = this.value[key];
            this.value[key] = n;
            return n2;
        }
        if (this.size == this.key.length) {
            final Object[] key2 = new Object[(this.size == 0) ? 2 : (this.size * 2)];
            final int[] value = new int[(this.size == 0) ? 2 : (this.size * 2)];
            int size = this.size;
            while (size-- != 0) {
                key2[size] = this.key[size];
                value[size] = this.value[size];
            }
            this.key = key2;
            this.value = value;
        }
        this.key[this.size] = o;
        this.value[this.size] = n;
        ++this.size;
        return this.defRetValue;
    }
    
    @Override
    public int removeInt(final Object o) {
        final int key = this.findKey(o);
        if (key == -1) {
            return this.defRetValue;
        }
        final int n = this.value[key];
        final int n2 = this.size - key - 1;
        System.arraycopy(this.key, key + 1, this.key, key, n2);
        System.arraycopy(this.value, key + 1, this.value, key, n2);
        --this.size;
        this.key[this.size] = null;
        return n;
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
            this.values = new ValuesCollection(null);
        }
        return this.values;
    }
    
    public Object2IntArrayMap clone() {
        final Object2IntArrayMap object2IntArrayMap = (Object2IntArrayMap)super.clone();
        object2IntArrayMap.key = this.key.clone();
        object2IntArrayMap.value = this.value.clone();
        object2IntArrayMap.entries = null;
        object2IntArrayMap.keys = null;
        object2IntArrayMap.values = null;
        return object2IntArrayMap;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        while (0 < this.size) {
            objectOutputStream.writeObject(this.key[0]);
            objectOutputStream.writeInt(this.value[0]);
            int n = 0;
            ++n;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new Object[this.size];
        this.value = new int[this.size];
        while (0 < this.size) {
            this.key[0] = objectInputStream.readObject();
            this.value[0] = objectInputStream.readInt();
            int n = 0;
            ++n;
        }
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
    
    static int access$000(final Object2IntArrayMap object2IntArrayMap) {
        return object2IntArrayMap.size;
    }
    
    static Object[] access$100(final Object2IntArrayMap object2IntArrayMap) {
        return object2IntArrayMap.key;
    }
    
    static int[] access$200(final Object2IntArrayMap object2IntArrayMap) {
        return object2IntArrayMap.value;
    }
    
    static int access$010(final Object2IntArrayMap object2IntArrayMap) {
        return object2IntArrayMap.size--;
    }
    
    static int access$300(final Object2IntArrayMap object2IntArrayMap, final Object o) {
        return object2IntArrayMap.findKey(o);
    }
    
    private final class EntrySet extends AbstractObjectSet implements Object2IntMap.FastEntrySet
    {
        final Object2IntArrayMap this$0;
        
        private EntrySet(final Object2IntArrayMap this$0) {
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
                    return this.next < Object2IntArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public Object2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final Object[] access$100 = Object2IntArrayMap.access$100(this.this$1.this$0);
                    final int next = this.next;
                    this.curr = next;
                    return new BasicEntry(access$100[next], Object2IntArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }
                
                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    final int n = Object2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2IntArrayMap.access$100(this.this$1.this$0)[Object2IntArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.next < Object2IntArrayMap.access$000(this.this$1.this$0)) {
                        final Object[] access$100 = Object2IntArrayMap.access$100(this.this$1.this$0);
                        final int next = this.next;
                        this.curr = next;
                        consumer.accept(new BasicEntry(access$100[next], Object2IntArrayMap.access$200(this.this$1.this$0)[this.next++]));
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
                    return this.next < Object2IntArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public Object2IntMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final BasicEntry entry = this.entry;
                    final Object[] access$100 = Object2IntArrayMap.access$100(this.this$1.this$0);
                    final int next = this.next;
                    this.curr = next;
                    entry.key = access$100[next];
                    this.entry.value = Object2IntArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }
                
                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    final int n = Object2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Object2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Object2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Object2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Object2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Object2IntArrayMap.access$100(this.this$1.this$0)[Object2IntArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.next < Object2IntArrayMap.access$000(this.this$1.this$0)) {
                        final BasicEntry entry = this.entry;
                        final Object[] access$100 = Object2IntArrayMap.access$100(this.this$1.this$0);
                        final int next = this.next;
                        this.curr = next;
                        entry.key = access$100[next];
                        this.entry.value = Object2IntArrayMap.access$200(this.this$1.this$0)[this.next++];
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
            return new EntrySetSpliterator(0, Object2IntArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            while (0 < Object2IntArrayMap.access$000(this.this$0)) {
                consumer.accept(new BasicEntry(Object2IntArrayMap.access$100(this.this$0)[0], Object2IntArrayMap.access$200(this.this$0)[0]));
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public void fastForEach(final Consumer consumer) {
            final BasicEntry basicEntry = new BasicEntry();
            while (0 < Object2IntArrayMap.access$000(this.this$0)) {
                basicEntry.key = Object2IntArrayMap.access$100(this.this$0)[0];
                basicEntry.value = Object2IntArrayMap.access$200(this.this$0)[0];
                consumer.accept(basicEntry);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Object2IntArrayMap.access$000(this.this$0);
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
            return this.this$0.containsKey(key) && this.this$0.getInt(key) == (int)entry.getValue();
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
            final int access$300 = Object2IntArrayMap.access$300(this.this$0, key);
            if (access$300 == -1 || intValue != Object2IntArrayMap.access$200(this.this$0)[access$300]) {
                return false;
            }
            final int n = Object2IntArrayMap.access$000(this.this$0) - access$300 - 1;
            System.arraycopy(Object2IntArrayMap.access$100(this.this$0), access$300 + 1, Object2IntArrayMap.access$100(this.this$0), access$300, n);
            System.arraycopy(Object2IntArrayMap.access$200(this.this$0), access$300 + 1, Object2IntArrayMap.access$200(this.this$0), access$300, n);
            Object2IntArrayMap.access$010(this.this$0);
            Object2IntArrayMap.access$100(this.this$0)[Object2IntArrayMap.access$000(this.this$0)] = null;
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
        
        EntrySet(final Object2IntArrayMap object2IntArrayMap, final Object2IntArrayMap$1 object) {
            this(object2IntArrayMap);
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
            protected final Object2IntMap.Entry get(final int n) {
                return new BasicEntry(Object2IntArrayMap.access$100(this.this$1.this$0)[n], Object2IntArrayMap.access$200(this.this$1.this$0)[n]);
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
        final Object2IntArrayMap this$0;
        
        private KeySet(final Object2IntArrayMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean contains(final Object o) {
            return Object2IntArrayMap.access$300(this.this$0, o) != -1;
        }
        
        @Override
        public boolean remove(final Object o) {
            final int access$300 = Object2IntArrayMap.access$300(this.this$0, o);
            if (access$300 == -1) {
                return false;
            }
            final int n = Object2IntArrayMap.access$000(this.this$0) - access$300 - 1;
            System.arraycopy(Object2IntArrayMap.access$100(this.this$0), access$300 + 1, Object2IntArrayMap.access$100(this.this$0), access$300, n);
            System.arraycopy(Object2IntArrayMap.access$200(this.this$0), access$300 + 1, Object2IntArrayMap.access$200(this.this$0), access$300, n);
            Object2IntArrayMap.access$010(this.this$0);
            Object2IntArrayMap.access$100(this.this$0)[Object2IntArrayMap.access$000(this.this$0)] = null;
            return true;
        }
        
        @Override
        public ObjectIterator iterator() {
            return new ObjectIterator() {
                int pos = 0;
                final KeySet this$1;
                
                @Override
                public Object next() {
                    // 
                    // This method could not be decompiled.
                    // 
                    // Original Bytecode:
                    // 
                    //     1: if_icmpge       12
                    //     4: new             Ljava/util/NoSuchElementException;
                    //     7: dup            
                    //     8: invokespecial   java/util/NoSuchElementException.<init>:()V
                    //    11: athrow         
                    //    12: aload_0        
                    //    13: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap$KeySet$1.this$1:Lcom/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap$KeySet;
                    //    16: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap$KeySet.this$0:Lcom/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap;
                    //    19: invokestatic    com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap.access$100:(Lcom/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap;)[Ljava/lang/Object;
                    //    22: aload_0        
                    //    23: dup            
                    //    24: getfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap$KeySet$1.pos:I
                    //    27: dup_x1         
                    //    28: iconst_1       
                    //    29: iadd           
                    //    30: putfield        com/viaversion/viaversion/libs/fastutil/objects/Object2IntArrayMap$KeySet$1.pos:I
                    //    33: aaload         
                    //    34: areturn        
                    // 
                    // The error that occurred was:
                    // 
                    // java.lang.ArrayIndexOutOfBoundsException
                    // 
                    throw new IllegalStateException("An error occurred while decompiling this method.");
                }
                
                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    final int n = Object2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                    System.arraycopy(Object2IntArrayMap.access$100(this.this$1.this$0), this.pos, Object2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                    System.arraycopy(Object2IntArrayMap.access$200(this.this$1.this$0), this.pos, Object2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                    Object2IntArrayMap.access$010(this.this$1.this$0);
                    --this.pos;
                    Object2IntArrayMap.access$100(this.this$1.this$0)[Object2IntArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.pos < Object2IntArrayMap.access$000(this.this$1.this$0)) {
                        consumer.accept(Object2IntArrayMap.access$100(this.this$1.this$0)[this.pos++]);
                    }
                }
            };
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return new KeySetSpliterator(0, Object2IntArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            while (0 < Object2IntArrayMap.access$000(this.this$0)) {
                consumer.accept(Object2IntArrayMap.access$100(this.this$0)[0]);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Object2IntArrayMap.access$000(this.this$0);
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
        
        KeySet(final Object2IntArrayMap object2IntArrayMap, final Object2IntArrayMap$1 object) {
            this(object2IntArrayMap);
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
                return Object2IntArrayMap.access$100(this.this$1.this$0)[n];
            }
            
            @Override
            protected final KeySetSpliterator makeForSplit(final int n, final int n2) {
                return this.this$1.new KeySetSpliterator(n, n2);
            }
            
            @Override
            public void forEachRemaining(final Consumer consumer) {
                while (this.pos < Object2IntArrayMap.access$000(this.this$1.this$0)) {
                    consumer.accept(Object2IntArrayMap.access$100(this.this$1.this$0)[this.pos++]);
                }
            }
            
            @Override
            protected ObjectSpliterator makeForSplit(final int n, final int n2) {
                return this.makeForSplit(n, n2);
            }
        }
    }
    
    private final class ValuesCollection extends AbstractIntCollection
    {
        final Object2IntArrayMap this$0;
        
        private ValuesCollection(final Object2IntArrayMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean contains(final int n) {
            return this.this$0.containsValue(n);
        }
        
        @Override
        public IntIterator iterator() {
            return new IntIterator() {
                int pos = 0;
                final ValuesCollection this$1;
                
                @Override
                public boolean hasNext() {
                    return this.pos < Object2IntArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public int nextInt() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return Object2IntArrayMap.access$200(this.this$1.this$0)[this.pos++];
                }
                
                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    final int n = Object2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                    System.arraycopy(Object2IntArrayMap.access$100(this.this$1.this$0), this.pos, Object2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                    System.arraycopy(Object2IntArrayMap.access$200(this.this$1.this$0), this.pos, Object2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                    Object2IntArrayMap.access$010(this.this$1.this$0);
                    --this.pos;
                    Object2IntArrayMap.access$100(this.this$1.this$0)[Object2IntArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final IntConsumer intConsumer) {
                    while (this.pos < Object2IntArrayMap.access$000(this.this$1.this$0)) {
                        intConsumer.accept(Object2IntArrayMap.access$200(this.this$1.this$0)[this.pos++]);
                    }
                }
                
                @Override
                public void forEachRemaining(final Object o) {
                    this.forEachRemaining((IntConsumer)o);
                }
            };
        }
        
        @Override
        public IntSpliterator spliterator() {
            return new ValuesSpliterator(0, Object2IntArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final IntConsumer intConsumer) {
            while (0 < Object2IntArrayMap.access$000(this.this$0)) {
                intConsumer.accept(Object2IntArrayMap.access$200(this.this$0)[0]);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Object2IntArrayMap.access$000(this.this$0);
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
        
        ValuesCollection(final Object2IntArrayMap object2IntArrayMap, final Object2IntArrayMap$1 object) {
            this(object2IntArrayMap);
        }
        
        final class ValuesSpliterator extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator implements IntSpliterator
        {
            final ValuesCollection this$1;
            
            ValuesSpliterator(final ValuesCollection this$1, final int n, final int n2) {
                this.this$1 = this$1;
                super(n, n2);
            }
            
            @Override
            public int characteristics() {
                return 16720;
            }
            
            @Override
            protected final int get(final int n) {
                return Object2IntArrayMap.access$200(this.this$1.this$0)[n];
            }
            
            @Override
            protected final ValuesSpliterator makeForSplit(final int n, final int n2) {
                return this.this$1.new ValuesSpliterator(n, n2);
            }
            
            @Override
            public void forEachRemaining(final IntConsumer intConsumer) {
                while (this.pos < Object2IntArrayMap.access$000(this.this$1.this$0)) {
                    intConsumer.accept(Object2IntArrayMap.access$200(this.this$1.this$0)[this.pos++]);
                }
            }
            
            @Override
            protected IntSpliterator makeForSplit(final int n, final int n2) {
                return this.makeForSplit(n, n2);
            }
            
            @Override
            public void forEachRemaining(final Object o) {
                this.forEachRemaining((IntConsumer)o);
            }
        }
    }
}
