package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.*;
import java.util.function.*;

public class Int2IntArrayMap extends AbstractInt2IntMap implements Serializable, Cloneable
{
    private static final long serialVersionUID = 1L;
    private transient int[] key;
    private transient int[] value;
    private int size;
    private transient Int2IntMap.FastEntrySet entries;
    private transient IntSet keys;
    private transient IntCollection values;
    
    public Int2IntArrayMap(final int[] key, final int[] value) {
        this.key = key;
        this.value = value;
        this.size = key.length;
        if (key.length != value.length) {
            throw new IllegalArgumentException("Keys and values have different lengths (" + key.length + ", " + value.length + ")");
        }
    }
    
    public Int2IntArrayMap() {
        this.key = IntArrays.EMPTY_ARRAY;
        this.value = IntArrays.EMPTY_ARRAY;
    }
    
    public Int2IntArrayMap(final int n) {
        this.key = new int[n];
        this.value = new int[n];
    }
    
    public Int2IntArrayMap(final Int2IntMap int2IntMap) {
        this(int2IntMap.size());
        for (final Int2IntMap.Entry entry : int2IntMap.int2IntEntrySet()) {
            this.key[0] = entry.getIntKey();
            this.value[0] = entry.getIntValue();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public Int2IntArrayMap(final Map map) {
        this(map.size());
        for (final Map.Entry<Integer, V> entry : map.entrySet()) {
            this.key[0] = entry.getKey();
            this.value[0] = (int)entry.getValue();
            int n = 0;
            ++n;
        }
        this.size = 0;
    }
    
    public Int2IntArrayMap(final int[] key, final int[] value, final int size) {
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
    public Int2IntMap.FastEntrySet int2IntEntrySet() {
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
    public int get(final int n) {
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
        this.size = 0;
    }
    
    @Override
    public boolean containsKey(final int n) {
        return this.findKey(n) != -1;
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
    public int put(final int n, final int n2) {
        final int key = this.findKey(n);
        if (key != -1) {
            final int n3 = this.value[key];
            this.value[key] = n2;
            return n3;
        }
        if (this.size == this.key.length) {
            final int[] key2 = new int[(this.size == 0) ? 2 : (this.size * 2)];
            final int[] value = new int[(this.size == 0) ? 2 : (this.size * 2)];
            int size = this.size;
            while (size-- != 0) {
                key2[size] = this.key[size];
                value[size] = this.value[size];
            }
            this.key = key2;
            this.value = value;
        }
        this.key[this.size] = n;
        this.value[this.size] = n2;
        ++this.size;
        return this.defRetValue;
    }
    
    @Override
    public int remove(final int n) {
        final int key = this.findKey(n);
        if (key == -1) {
            return this.defRetValue;
        }
        final int n2 = this.value[key];
        final int n3 = this.size - key - 1;
        System.arraycopy(this.key, key + 1, this.key, key, n3);
        System.arraycopy(this.value, key + 1, this.value, key, n3);
        --this.size;
        return n2;
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
            this.values = new ValuesCollection(null);
        }
        return this.values;
    }
    
    public Int2IntArrayMap clone() {
        final Int2IntArrayMap int2IntArrayMap = (Int2IntArrayMap)super.clone();
        int2IntArrayMap.key = this.key.clone();
        int2IntArrayMap.value = this.value.clone();
        int2IntArrayMap.entries = null;
        int2IntArrayMap.keys = null;
        int2IntArrayMap.values = null;
        return int2IntArrayMap;
    }
    
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        while (0 < this.size) {
            objectOutputStream.writeInt(this.key[0]);
            objectOutputStream.writeInt(this.value[0]);
            int n = 0;
            ++n;
        }
    }
    
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        this.key = new int[this.size];
        this.value = new int[this.size];
        while (0 < this.size) {
            this.key[0] = objectInputStream.readInt();
            this.value[0] = objectInputStream.readInt();
            int n = 0;
            ++n;
        }
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
    
    static int access$000(final Int2IntArrayMap int2IntArrayMap) {
        return int2IntArrayMap.size;
    }
    
    static int[] access$100(final Int2IntArrayMap int2IntArrayMap) {
        return int2IntArrayMap.key;
    }
    
    static int[] access$200(final Int2IntArrayMap int2IntArrayMap) {
        return int2IntArrayMap.value;
    }
    
    static int access$010(final Int2IntArrayMap int2IntArrayMap) {
        return int2IntArrayMap.size--;
    }
    
    static int access$300(final Int2IntArrayMap int2IntArrayMap, final int n) {
        return int2IntArrayMap.findKey(n);
    }
    
    private final class EntrySet extends AbstractObjectSet implements Int2IntMap.FastEntrySet
    {
        final Int2IntArrayMap this$0;
        
        private EntrySet(final Int2IntArrayMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public ObjectIterator iterator() {
            return new ObjectIterator() {
                int curr = -1;
                int next = 0;
                final EntrySet this$1;
                
                @Override
                public Int2IntMap.Entry next() {
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
                    //    12: new             Lcom/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntMap$BasicEntry;
                    //    15: dup            
                    //    16: aload_0        
                    //    17: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$1.this$1:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet;
                    //    20: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet.this$0:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap;
                    //    23: invokestatic    com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap.access$100:(Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap;)[I
                    //    26: aload_0        
                    //    27: aload_0        
                    //    28: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$1.next:I
                    //    31: dup_x1         
                    //    32: putfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$1.curr:I
                    //    35: iaload         
                    //    36: aload_0        
                    //    37: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$1.this$1:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet;
                    //    40: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet.this$0:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap;
                    //    43: invokestatic    com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap.access$200:(Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap;)[I
                    //    46: aload_0        
                    //    47: dup            
                    //    48: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$1.next:I
                    //    51: dup_x1         
                    //    52: iconst_1       
                    //    53: iadd           
                    //    54: putfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$1.next:I
                    //    57: iaload         
                    //    58: invokespecial   com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntMap$BasicEntry.<init>:(II)V
                    //    61: areturn        
                    // 
                    // The error that occurred was:
                    // 
                    // java.lang.ArrayIndexOutOfBoundsException
                    // 
                    throw new IllegalStateException("An error occurred while decompiling this method.");
                }
                
                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    final int n = Int2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.next < Int2IntArrayMap.access$000(this.this$1.this$0)) {
                        final int[] access$100 = Int2IntArrayMap.access$100(this.this$1.this$0);
                        final int next = this.next;
                        this.curr = next;
                        consumer.accept(new BasicEntry(access$100[next], Int2IntArrayMap.access$200(this.this$1.this$0)[this.next++]));
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
                public Int2IntMap.Entry next() {
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
                    //    13: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$2.entry:Lcom/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntMap$BasicEntry;
                    //    16: aload_0        
                    //    17: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$2.this$1:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet;
                    //    20: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet.this$0:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap;
                    //    23: invokestatic    com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap.access$100:(Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap;)[I
                    //    26: aload_0        
                    //    27: aload_0        
                    //    28: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$2.next:I
                    //    31: dup_x1         
                    //    32: putfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$2.curr:I
                    //    35: iaload         
                    //    36: putfield        com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntMap$BasicEntry.key:I
                    //    39: aload_0        
                    //    40: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$2.entry:Lcom/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntMap$BasicEntry;
                    //    43: aload_0        
                    //    44: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$2.this$1:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet;
                    //    47: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet.this$0:Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap;
                    //    50: invokestatic    com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap.access$200:(Lcom/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap;)[I
                    //    53: aload_0        
                    //    54: dup            
                    //    55: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$2.next:I
                    //    58: dup_x1         
                    //    59: iconst_1       
                    //    60: iadd           
                    //    61: putfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$2.next:I
                    //    64: iaload         
                    //    65: putfield        com/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntMap$BasicEntry.value:I
                    //    68: aload_0        
                    //    69: getfield        com/viaversion/viaversion/libs/fastutil/ints/Int2IntArrayMap$EntrySet$2.entry:Lcom/viaversion/viaversion/libs/fastutil/ints/AbstractInt2IntMap$BasicEntry;
                    //    72: areturn        
                    // 
                    // The error that occurred was:
                    // 
                    // java.lang.ArrayIndexOutOfBoundsException
                    // 
                    throw new IllegalStateException("An error occurred while decompiling this method.");
                }
                
                @Override
                public void remove() {
                    if (this.curr == -1) {
                        throw new IllegalStateException();
                    }
                    this.curr = -1;
                    final int n = Int2IntArrayMap.access$010(this.this$1.this$0) - this.next--;
                    System.arraycopy(Int2IntArrayMap.access$100(this.this$1.this$0), this.next + 1, Int2IntArrayMap.access$100(this.this$1.this$0), this.next, n);
                    System.arraycopy(Int2IntArrayMap.access$200(this.this$1.this$0), this.next + 1, Int2IntArrayMap.access$200(this.this$1.this$0), this.next, n);
                }
                
                @Override
                public void forEachRemaining(final Consumer consumer) {
                    while (this.next < Int2IntArrayMap.access$000(this.this$1.this$0)) {
                        final BasicEntry entry = this.entry;
                        final int[] access$100 = Int2IntArrayMap.access$100(this.this$1.this$0);
                        final int next = this.next;
                        this.curr = next;
                        entry.key = access$100[next];
                        this.entry.value = Int2IntArrayMap.access$200(this.this$1.this$0)[this.next++];
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
            return new EntrySetSpliterator(0, Int2IntArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final Consumer consumer) {
            while (0 < Int2IntArrayMap.access$000(this.this$0)) {
                consumer.accept(new BasicEntry(Int2IntArrayMap.access$100(this.this$0)[0], Int2IntArrayMap.access$200(this.this$0)[0]));
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public void fastForEach(final Consumer consumer) {
            final BasicEntry basicEntry = new BasicEntry();
            while (0 < Int2IntArrayMap.access$000(this.this$0)) {
                basicEntry.key = Int2IntArrayMap.access$100(this.this$0)[0];
                basicEntry.value = Int2IntArrayMap.access$200(this.this$0)[0];
                consumer.accept(basicEntry);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Int2IntArrayMap.access$000(this.this$0);
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
            return this.this$0.containsKey(intValue) && this.this$0.get(intValue) == (int)entry.getValue();
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
            final int access$300 = Int2IntArrayMap.access$300(this.this$0, intValue);
            if (access$300 == -1 || intValue2 != Int2IntArrayMap.access$200(this.this$0)[access$300]) {
                return false;
            }
            final int n = Int2IntArrayMap.access$000(this.this$0) - access$300 - 1;
            System.arraycopy(Int2IntArrayMap.access$100(this.this$0), access$300 + 1, Int2IntArrayMap.access$100(this.this$0), access$300, n);
            System.arraycopy(Int2IntArrayMap.access$200(this.this$0), access$300 + 1, Int2IntArrayMap.access$200(this.this$0), access$300, n);
            Int2IntArrayMap.access$010(this.this$0);
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
        
        EntrySet(final Int2IntArrayMap int2IntArrayMap, final Int2IntArrayMap$1 object) {
            this(int2IntArrayMap);
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
            protected final Int2IntMap.Entry get(final int n) {
                return new BasicEntry(Int2IntArrayMap.access$100(this.this$1.this$0)[n], Int2IntArrayMap.access$200(this.this$1.this$0)[n]);
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
        final Int2IntArrayMap this$0;
        
        private KeySet(final Int2IntArrayMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public boolean contains(final int n) {
            return Int2IntArrayMap.access$300(this.this$0, n) != -1;
        }
        
        @Override
        public boolean remove(final int n) {
            final int access$300 = Int2IntArrayMap.access$300(this.this$0, n);
            if (access$300 == -1) {
                return false;
            }
            final int n2 = Int2IntArrayMap.access$000(this.this$0) - access$300 - 1;
            System.arraycopy(Int2IntArrayMap.access$100(this.this$0), access$300 + 1, Int2IntArrayMap.access$100(this.this$0), access$300, n2);
            System.arraycopy(Int2IntArrayMap.access$200(this.this$0), access$300 + 1, Int2IntArrayMap.access$200(this.this$0), access$300, n2);
            Int2IntArrayMap.access$010(this.this$0);
            return true;
        }
        
        @Override
        public IntIterator iterator() {
            return new IntIterator() {
                int pos = 0;
                final KeySet this$1;
                
                @Override
                public boolean hasNext() {
                    return this.pos < Int2IntArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public int nextInt() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return Int2IntArrayMap.access$100(this.this$1.this$0)[this.pos++];
                }
                
                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    final int n = Int2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                    System.arraycopy(Int2IntArrayMap.access$100(this.this$1.this$0), this.pos, Int2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                    System.arraycopy(Int2IntArrayMap.access$200(this.this$1.this$0), this.pos, Int2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                    Int2IntArrayMap.access$010(this.this$1.this$0);
                    --this.pos;
                }
                
                @Override
                public void forEachRemaining(final IntConsumer intConsumer) {
                    while (this.pos < Int2IntArrayMap.access$000(this.this$1.this$0)) {
                        intConsumer.accept(Int2IntArrayMap.access$100(this.this$1.this$0)[this.pos++]);
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
            return new KeySetSpliterator(0, Int2IntArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final IntConsumer intConsumer) {
            while (0 < Int2IntArrayMap.access$000(this.this$0)) {
                intConsumer.accept(Int2IntArrayMap.access$100(this.this$0)[0]);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Int2IntArrayMap.access$000(this.this$0);
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
        
        KeySet(final Int2IntArrayMap int2IntArrayMap, final Int2IntArrayMap$1 object) {
            this(int2IntArrayMap);
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
                return Int2IntArrayMap.access$100(this.this$1.this$0)[n];
            }
            
            @Override
            protected final KeySetSpliterator makeForSplit(final int n, final int n2) {
                return this.this$1.new KeySetSpliterator(n, n2);
            }
            
            @Override
            public void forEachRemaining(final IntConsumer intConsumer) {
                while (this.pos < Int2IntArrayMap.access$000(this.this$1.this$0)) {
                    intConsumer.accept(Int2IntArrayMap.access$100(this.this$1.this$0)[this.pos++]);
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
    
    private final class ValuesCollection extends AbstractIntCollection
    {
        final Int2IntArrayMap this$0;
        
        private ValuesCollection(final Int2IntArrayMap this$0) {
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
                    return this.pos < Int2IntArrayMap.access$000(this.this$1.this$0);
                }
                
                @Override
                public int nextInt() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    return Int2IntArrayMap.access$200(this.this$1.this$0)[this.pos++];
                }
                
                @Override
                public void remove() {
                    if (this.pos == 0) {
                        throw new IllegalStateException();
                    }
                    final int n = Int2IntArrayMap.access$000(this.this$1.this$0) - this.pos;
                    System.arraycopy(Int2IntArrayMap.access$100(this.this$1.this$0), this.pos, Int2IntArrayMap.access$100(this.this$1.this$0), this.pos - 1, n);
                    System.arraycopy(Int2IntArrayMap.access$200(this.this$1.this$0), this.pos, Int2IntArrayMap.access$200(this.this$1.this$0), this.pos - 1, n);
                    Int2IntArrayMap.access$010(this.this$1.this$0);
                    --this.pos;
                }
                
                @Override
                public void forEachRemaining(final IntConsumer intConsumer) {
                    while (this.pos < Int2IntArrayMap.access$000(this.this$1.this$0)) {
                        intConsumer.accept(Int2IntArrayMap.access$200(this.this$1.this$0)[this.pos++]);
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
            return new ValuesSpliterator(0, Int2IntArrayMap.access$000(this.this$0));
        }
        
        @Override
        public void forEach(final IntConsumer intConsumer) {
            while (0 < Int2IntArrayMap.access$000(this.this$0)) {
                intConsumer.accept(Int2IntArrayMap.access$200(this.this$0)[0]);
                int n = 0;
                ++n;
            }
        }
        
        @Override
        public int size() {
            return Int2IntArrayMap.access$000(this.this$0);
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
        
        ValuesCollection(final Int2IntArrayMap int2IntArrayMap, final Int2IntArrayMap$1 object) {
            this(int2IntArrayMap);
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
                return Int2IntArrayMap.access$200(this.this$1.this$0)[n];
            }
            
            @Override
            protected final ValuesSpliterator makeForSplit(final int n, final int n2) {
                return this.this$1.new ValuesSpliterator(n, n2);
            }
            
            @Override
            public void forEachRemaining(final IntConsumer intConsumer) {
                while (this.pos < Int2IntArrayMap.access$000(this.this$1.this$0)) {
                    intConsumer.accept(Int2IntArrayMap.access$200(this.this$1.this$0)[this.pos++]);
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
