package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

public class Int2ObjectArrayMap extends AbstractInt2ObjectMap implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient Object[] value;
    private int size;
    private transient Int2ObjectMap.FastEntrySet entries;
    private transient IntSet keys;
    private transient ObjectCollection values;
    
    public Int2ObjectArrayMap(final int[] key, final Object[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }
    
    public Int2ObjectArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = ObjectArrays.EMPTY_ARRAY;
    }
    
    public Int2ObjectArrayMap(final int n) {
        this.key = new int[n];
        this.value = new Object[n];
    }
    
    public Int2ObjectArrayMap(final Int2ObjectMap int2ObjectMap) {
        this(int2ObjectMap.size());
        for (final Int2ObjectMap.Entry entry : int2ObjectMap.int2ObjectEntrySet()) {
            this.key[0] = entry.getIntKey();
            this.value[0] = entry.getValue();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public Int2ObjectArrayMap(final Map map) {
        this(map.size());
        for (final Map.Entry<Integer, V> entry : map.entrySet()) {
            this.key[0] = entry.getKey();
            this.value[0] = entry.getValue();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public Int2ObjectArrayMap(final int[] key, final Object[] value, final int size) {
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
    public Int2ObjectMap.FastEntrySet int2ObjectEntrySet() {
        if (this.entries == null) {
            this.entries = new EntrySet(null);
        }
        return this.entries;
    }
    
    private int findKey(final int n) {
        final int[] key = this.key;
        int size = this.size;
        while (size-- != 0) {
            if (key[size] == n) {
                return size;
            }
        }
        return -1;
    }
    
    @Override
    public Object get(final int n) {
        final int[] key = this.key;
        int size = this.size;
        while (size-- != 0) {
            if (key[size] == n) {
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
            this.value[size] = null;
        }
        this.size = 0;
    }
    
    @Override
    public boolean containsKey(final int n) {
        return this.findKey(n) != -1;
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
    public Object put(final int n, final Object o) {
        final int key = this.findKey(n);
        if (key != -1) {
            final Object o2 = this.value[key];
            this.value[key] = o;
            return o2;
        }
        if (this.size == this.key.length) {
            final int[] key2 = new int[(this.size == 0) ? 2 : (this.size * 2)];
            final Object[] value = new Object[(this.size == 0) ? 2 : (this.size * 2)];
            int size = this.size;
            while (size-- != 0) {
                key2[size] = this.key[size];
                value[size] = this.value[size];
            }
            this.key = key2;
            this.value = value;
        }
        this.key[this.size] = n;
        this.value[this.size] = o;
        ++this.size;
        return this.defRetValue;
    }
    
    @Override
    public Object remove(final int n) {
        final int key = this.findKey(n);
        if (key == -1) {
            return this.defRetValue;
        }
        final Object o = this.value[key];
        final int n2 = this.size - key - 1;
        System.arraycopy(this.key, key + 1, this.key, key, n2);
        System.arraycopy(this.value, key + 1, this.value, key, n2);
        --this.size;
        this.value[this.size] = null;
        return o;
    }
    
    @Override
    public IntSet keySet() {
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
    
    public Int2ObjectArrayMap clone() {
        final Int2ObjectArrayMap int2ObjectArrayMap = (Int2ObjectArrayMap)super.clone();
        int2ObjectArrayMap.key = this.key.clone();
        int2ObjectArrayMap.value = this.value.clone();
        int2ObjectArrayMap.entries = null;
        int2ObjectArrayMap.keys = null;
        int2ObjectArrayMap.values = null;
        return int2ObjectArrayMap;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        while (0 < this.size) {
            objectOutputStream.writeInt(this.key[0]);
            objectOutputStream.writeObject(this.value[0]);
            int n = 0;
            ++n;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new int[this.size];
        this.value = new Object[this.size];
        while (0 < this.size) {
            this.key[0] = objectInputStream.readInt();
            this.value[0] = objectInputStream.readObject();
            int n = 0;
            ++n;
        }
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
    
    public Object clone() throws CloneNotSupportedException {
        return this.clone();
    }
    
    static int access$000(final Int2ObjectArrayMap int2ObjectArrayMap) {
        return int2ObjectArrayMap.size;
    }
    
    static int[] access$100(final Int2ObjectArrayMap int2ObjectArrayMap) {
        return int2ObjectArrayMap.key;
    }
    
    static Object[] access$200(final Int2ObjectArrayMap int2ObjectArrayMap) {
        return int2ObjectArrayMap.value;
    }
    
    static int access$010(final Int2ObjectArrayMap int2ObjectArrayMap) {
        return int2ObjectArrayMap.size--;
    }
    
    static int access$300(final Int2ObjectArrayMap int2ObjectArrayMap, final int n) {
        return int2ObjectArrayMap.findKey(n);
    }
    
    private final class EntrySet extends AbstractObjectSet implements Int2ObjectMap.FastEntrySet
    {
        final Int2ObjectArrayMap this$0;
        
        private EntrySet(final Int2ObjectArrayMap this$0) {
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
                    return this.next < Int2ObjectArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public Int2ObjectMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final int[] access$100 = Int2ObjectArrayMap.access$100(this.this$1.this$0);
                    final int next = this.next;
                    this.curr = next;
                    return new BasicEntry(access$100[next], Int2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++]);
                }
                
                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    final int n = Int2ObjectArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2ObjectArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2ObjectArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2ObjectArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2ObjectArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Int2ObjectArrayMap.access$200(this.this$1.this$0)[Int2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.next < Int2ObjectArrayMap.access$000(this.this$1.this$0)) {
                        final int[] access$100 = Int2ObjectArrayMap.access$100(this.this$1.this$0);
                        final int next = this.next;
                        this.curr = next;
                        consumer.accept(new BasicEntry(access$100[next], Int2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++]));
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
                    return this.next < Int2ObjectArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public Int2ObjectMap.Entry next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final BasicEntry entry = this.entry;
                    final int[] access$100 = Int2ObjectArrayMap.access$100(this.this$1.this$0);
                    final int next = this.next;
                    this.curr = next;
                    entry.key = access$100[next];
                    this.entry.value = Int2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++];
                    return this.entry;
                }
                
                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    final int n = Int2ObjectArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2ObjectArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2ObjectArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2ObjectArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2ObjectArrayMap.access$200(this.this$1.this$0), this.next, n);
                    Int2ObjectArrayMap.access$200(this.this$1.this$0)[Int2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.next < Int2ObjectArrayMap.access$000(this.this$1.this$0)) {
                        final BasicEntry entry = this.entry;
                        final int[] access$100 = Int2ObjectArrayMap.access$100(this.this$1.this$0);
                        final int next = this.next;
                        this.curr = next;
                        entry.key = access$100[next];
                        this.entry.value = Int2ObjectArrayMap.access$200(this.this$1.this$0)[this.next++];
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
            return new EntrySetSpliterator(0, Int2ObjectArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            while (0 < Int2ObjectArrayMap.access$000(this.this$0)) {
                consumer.accept(new BasicEntry(Int2ObjectArrayMap.access$100(this.this$0)[0], Int2ObjectArrayMap.access$200(this.this$0)[0]));
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public void fastForEach(final Consumer consumer) {
            final BasicEntry basicEntry = new BasicEntry();
            while (0 < Int2ObjectArrayMap.access$000(this.this$0)) {
                basicEntry.key = Int2ObjectArrayMap.access$100(this.this$0)[0];
                basicEntry.value = Int2ObjectArrayMap.access$200(this.this$0)[0];
                consumer.accept(basicEntry);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Int2ObjectArrayMap.access$000(this.this$0);
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
            return this.this$0.containsKey(intValue) && Objects.equals(this.this$0.get(intValue), entry.getValue());
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
            final int access$300 = Int2ObjectArrayMap.access$300(this.this$0, intValue);
            if (access$300 == -1 || !Objects.equals(value, Int2ObjectArrayMap.access$200(this.this$0)[access$300])) {
                return false;
            }
            final int n = Int2ObjectArrayMap.access$000(this.this$0) - access$300 - 1;
            System.arraycopy(Int2ObjectArrayMap.access$100(this.this$0), access$300 + 1, Int2ObjectArrayMap.access$100(this.this$0), access$300, n);
            System.arraycopy(Int2ObjectArrayMap.access$200(this.this$0), access$300 + 1, Int2ObjectArrayMap.access$200(this.this$0), access$300, n);
            Int2ObjectArrayMap.access$010(this.this$0);
            Int2ObjectArrayMap.access$200(this.this$0)[Int2ObjectArrayMap.access$000(this.this$0)] = null;
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
        
        EntrySet(final Int2ObjectArrayMap int2ObjectArrayMap, final Int2ObjectArrayMap$1 object) {
            this(int2ObjectArrayMap);
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
            protected final Int2ObjectMap.Entry get(final int n) {
                return new BasicEntry(Int2ObjectArrayMap.access$100(this.this$1.this$0)[n], Int2ObjectArrayMap.access$200(this.this$1.this$0)[n]);
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
    
    private final class KeySet extends AbstractIntSet
    {
        final Int2ObjectArrayMap this$0;
        
        private KeySet(final Int2ObjectArrayMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean contains(final int n) {
            return Int2ObjectArrayMap.access$300(this.this$0, n) != -1;
        }
        
        @Override
        public boolean remove(final int n) {
            final int access$300 = Int2ObjectArrayMap.access$300(this.this$0, n);
            if (access$300 == -1) {
                return false;
            }
            final int n2 = Int2ObjectArrayMap.access$000(this.this$0) - access$300 - 1;
            System.arraycopy(Int2ObjectArrayMap.access$100(this.this$0), access$300 + 1, Int2ObjectArrayMap.access$100(this.this$0), access$300, n2);
            System.arraycopy(Int2ObjectArrayMap.access$200(this.this$0), access$300 + 1, Int2ObjectArrayMap.access$200(this.this$0), access$300, n2);
            Int2ObjectArrayMap.access$010(this.this$0);
            Int2ObjectArrayMap.access$200(this.this$0)[Int2ObjectArrayMap.access$000(this.this$0)] = null;
            return true;
        }
        
        @Override
        public IntIterator iterator() {
            return new IntIterator() {
                int pos = 0;
                final KeySet this$1;
                
                @Override
                public boolean hasNext() {
                    return this.pos < Int2ObjectArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public int nextInt() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return Int2ObjectArrayMap.access$100(this.this$1.this$0)[this.pos++];
                }
                
                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    final int n = Int2ObjectArrayMap.access$000(this.this$1.this$0) - this.pos;
                    System.arraycopy(Int2ObjectArrayMap.access$100(this.this$1.this$0), this.pos, Int2ObjectArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                    System.arraycopy(Int2ObjectArrayMap.access$200(this.this$1.this$0), this.pos, Int2ObjectArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                    Int2ObjectArrayMap.access$010(this.this$1.this$0);
                    --this.pos;
                    Int2ObjectArrayMap.access$200(this.this$1.this$0)[Int2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final IntConsumer intConsumer) {
                    while (this.pos < Int2ObjectArrayMap.access$000(this.this$1.this$0)) {
                        intConsumer.accept(Int2ObjectArrayMap.access$100(this.this$1.this$0)[this.pos++]);
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
            return new KeySetSpliterator(0, Int2ObjectArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final IntConsumer intConsumer) {
            while (0 < Int2ObjectArrayMap.access$000(this.this$0)) {
                intConsumer.accept(Int2ObjectArrayMap.access$100(this.this$0)[0]);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Int2ObjectArrayMap.access$000(this.this$0);
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
        
        KeySet(final Int2ObjectArrayMap int2ObjectArrayMap, final Int2ObjectArrayMap$1 object) {
            this(int2ObjectArrayMap);
        }
        
        final class KeySetSpliterator extends IntSpliterators.EarlyBindingSizeIndexBasedSpliterator implements IntSpliterator
        {
            final KeySet this$1;
            
            KeySetSpliterator(final KeySet this$1, final int n, final int n2) {
                this.this$1 = this$1;
                super(n, n2);
            }
            
            @Override
            public int characteristics() {
                return 16721;
            }
            
            @Override
            protected final int get(final int n) {
                return Int2ObjectArrayMap.access$100(this.this$1.this$0)[n];
            }
            
            @Override
            protected final KeySetSpliterator makeForSplit(final int n, final int n2) {
                return this.this$1.new KeySetSpliterator(n, n2);
            }
            
            @Override
            public void forEachRemaining(final IntConsumer intConsumer) {
                while (this.pos < Int2ObjectArrayMap.access$000(this.this$1.this$0)) {
                    intConsumer.accept(Int2ObjectArrayMap.access$100(this.this$1.this$0)[this.pos++]);
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
    
    private final class ValuesCollection extends AbstractObjectCollection
    {
        final Int2ObjectArrayMap this$0;
        
        private ValuesCollection(final Int2ObjectArrayMap this$0) {
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
                    //    13: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap$ValuesCollection$1.this$1:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap$ValuesCollection;
                    //    16: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap$ValuesCollection.this$0:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap;
                    //    19: invokestatic    com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap.access$200:(Lcom/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap;)[Ljava/lang/Object;
                    //    22: aload_0        
                    //    23: dup            
                    //    24: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap$ValuesCollection$1.pos:I
                    //    27: dup_x1         
                    //    28: iconst_1       
                    //    29: iadd           
                    //    30: putfield        com/viaversion/viaversion/libs/fastutil/ints/Int2ObjectArrayMap$ValuesCollection$1.pos:I
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
                    final int n = Int2ObjectArrayMap.access$000(this.this$1.this$0) - this.pos;
                    System.arraycopy(Int2ObjectArrayMap.access$100(this.this$1.this$0), this.pos, Int2ObjectArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                    System.arraycopy(Int2ObjectArrayMap.access$200(this.this$1.this$0), this.pos, Int2ObjectArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                    Int2ObjectArrayMap.access$010(this.this$1.this$0);
                    --this.pos;
                    Int2ObjectArrayMap.access$200(this.this$1.this$0)[Int2ObjectArrayMap.access$000(this.this$1.this$0)] = null;
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.pos < Int2ObjectArrayMap.access$000(this.this$1.this$0)) {
                        consumer.accept(Int2ObjectArrayMap.access$200(this.this$1.this$0)[this.pos++]);
                    }
                }
            };
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return new ValuesSpliterator(0, Int2ObjectArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            while (0 < Int2ObjectArrayMap.access$000(this.this$0)) {
                consumer.accept(Int2ObjectArrayMap.access$200(this.this$0)[0]);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Int2ObjectArrayMap.access$000(this.this$0);
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
        
        ValuesCollection(final Int2ObjectArrayMap int2ObjectArrayMap, final Int2ObjectArrayMap$1 object) {
            this(int2ObjectArrayMap);
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
                return Int2ObjectArrayMap.access$200(this.this$1.this$0)[n];
            }
            
            @Override
            protected final ValuesSpliterator makeForSplit(final int n, final int n2) {
                return this.this$1.new ValuesSpliterator(n, n2);
            }
            
            @Override
            public void forEachRemaining(final Consumer consumer) {
                while (this.pos < Int2ObjectArrayMap.access$000(this.this$1.this$0)) {
                    consumer.accept(Int2ObjectArrayMap.access$200(this.this$1.this$0)[this.pos++]);
                }
            }
            
            @Override
            protected ObjectSpliterator makeForSplit(final int n, final int n2) {
                return this.makeForSplit(n, n2);
            }
        }
    }
}
