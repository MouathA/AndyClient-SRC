package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.io.*;
import java.util.*;
import com.google.common.base.*;

@GwtCompatible(serializable = true, emulated = true)
public final class LinkedHashMultimap extends AbstractSetMultimap
{
    private static final int DEFAULT_KEY_CAPACITY = 16;
    private static final int DEFAULT_VALUE_SET_CAPACITY = 2;
    @VisibleForTesting
    static final double VALUE_SET_LOAD_FACTOR = 1.0;
    @VisibleForTesting
    transient int valueSetCapacity;
    private transient ValueEntry multimapHeaderEntry;
    @GwtIncompatible("java serialization not supported")
    private static final long serialVersionUID = 1L;
    
    public static LinkedHashMultimap create() {
        return new LinkedHashMultimap(16, 2);
    }
    
    public static LinkedHashMultimap create(final int n, final int n2) {
        return new LinkedHashMultimap(Maps.capacity(n), Maps.capacity(n2));
    }
    
    public static LinkedHashMultimap create(final Multimap multimap) {
        final LinkedHashMultimap create = create(multimap.keySet().size(), 2);
        create.putAll(multimap);
        return create;
    }
    
    private static void succeedsInValueSet(final ValueSetLink predecessorInValueSet, final ValueSetLink successorInValueSet) {
        predecessorInValueSet.setSuccessorInValueSet(successorInValueSet);
        successorInValueSet.setPredecessorInValueSet(predecessorInValueSet);
    }
    
    private static void succeedsInMultimap(final ValueEntry predecessorInMultimap, final ValueEntry successorInMultimap) {
        predecessorInMultimap.setSuccessorInMultimap(successorInMultimap);
        successorInMultimap.setPredecessorInMultimap(predecessorInMultimap);
    }
    
    private static void deleteFromValueSet(final ValueSetLink valueSetLink) {
        succeedsInValueSet(valueSetLink.getPredecessorInValueSet(), valueSetLink.getSuccessorInValueSet());
    }
    
    private static void deleteFromMultimap(final ValueEntry valueEntry) {
        succeedsInMultimap(valueEntry.getPredecessorInMultimap(), valueEntry.getSuccessorInMultimap());
    }
    
    private LinkedHashMultimap(final int n, final int valueSetCapacity) {
        super(new LinkedHashMap(n));
        this.valueSetCapacity = 2;
        CollectPreconditions.checkNonnegative(valueSetCapacity, "expectedValuesPerKey");
        this.valueSetCapacity = valueSetCapacity;
        succeedsInMultimap(this.multimapHeaderEntry = new ValueEntry(null, null, 0, null), this.multimapHeaderEntry);
    }
    
    @Override
    Set createCollection() {
        return new LinkedHashSet(this.valueSetCapacity);
    }
    
    @Override
    Collection createCollection(final Object o) {
        return new ValueSet(o, this.valueSetCapacity);
    }
    
    @Override
    public Set replaceValues(@Nullable final Object o, final Iterable iterable) {
        return super.replaceValues(o, iterable);
    }
    
    @Override
    public Set entries() {
        return super.entries();
    }
    
    @Override
    public Collection values() {
        return super.values();
    }
    
    @Override
    Iterator entryIterator() {
        return new Iterator() {
            ValueEntry nextEntry = LinkedHashMultimap.access$300(this.this$0).successorInMultimap;
            ValueEntry toRemove;
            final LinkedHashMultimap this$0;
            
            @Override
            public boolean hasNext() {
                return this.nextEntry != LinkedHashMultimap.access$300(this.this$0);
            }
            
            @Override
            public Map.Entry next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                final ValueEntry nextEntry = this.nextEntry;
                this.toRemove = nextEntry;
                this.nextEntry = this.nextEntry.successorInMultimap;
                return nextEntry;
            }
            
            @Override
            public void remove() {
                CollectPreconditions.checkRemove(this.toRemove != null);
                this.this$0.remove(this.toRemove.getKey(), this.toRemove.getValue());
                this.toRemove = null;
            }
            
            @Override
            public Object next() {
                return this.next();
            }
        };
    }
    
    @Override
    Iterator valueIterator() {
        return Maps.valueIterator(this.entryIterator());
    }
    
    @Override
    public void clear() {
        super.clear();
        succeedsInMultimap(this.multimapHeaderEntry, this.multimapHeaderEntry);
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        objectOutputStream.writeInt(this.valueSetCapacity);
        objectOutputStream.writeInt(this.keySet().size());
        final Iterator<Object> iterator = this.keySet().iterator();
        while (iterator.hasNext()) {
            objectOutputStream.writeObject(iterator.next());
        }
        objectOutputStream.writeInt(this.size());
        for (final Map.Entry<Object, V> entry : this.entries()) {
            objectOutputStream.writeObject(entry.getKey());
            objectOutputStream.writeObject(entry.getValue());
        }
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream p0) throws IOException, ClassNotFoundException {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: invokevirtual   java/io/ObjectInputStream.defaultReadObject:()V
        //     4: aload_0        
        //     5: new             Lcom/google/common/collect/LinkedHashMultimap$ValueEntry;
        //     8: dup            
        //     9: aconst_null    
        //    10: aconst_null    
        //    11: iconst_0       
        //    12: aconst_null    
        //    13: invokespecial   com/google/common/collect/LinkedHashMultimap$ValueEntry.<init>:(Ljava/lang/Object;Ljava/lang/Object;ILcom/google/common/collect/LinkedHashMultimap$ValueEntry;)V
        //    16: putfield        com/google/common/collect/LinkedHashMultimap.multimapHeaderEntry:Lcom/google/common/collect/LinkedHashMultimap$ValueEntry;
        //    19: aload_0        
        //    20: getfield        com/google/common/collect/LinkedHashMultimap.multimapHeaderEntry:Lcom/google/common/collect/LinkedHashMultimap$ValueEntry;
        //    23: aload_0        
        //    24: getfield        com/google/common/collect/LinkedHashMultimap.multimapHeaderEntry:Lcom/google/common/collect/LinkedHashMultimap$ValueEntry;
        //    27: invokestatic    com/google/common/collect/LinkedHashMultimap.succeedsInMultimap:(Lcom/google/common/collect/LinkedHashMultimap$ValueEntry;Lcom/google/common/collect/LinkedHashMultimap$ValueEntry;)V
        //    30: aload_0        
        //    31: aload_1        
        //    32: invokevirtual   java/io/ObjectInputStream.readInt:()I
        //    35: putfield        com/google/common/collect/LinkedHashMultimap.valueSetCapacity:I
        //    38: aload_1        
        //    39: invokevirtual   java/io/ObjectInputStream.readInt:()I
        //    42: istore_2       
        //    43: new             Ljava/util/LinkedHashMap;
        //    46: dup            
        //    47: iload_2        
        //    48: invokestatic    com/google/common/collect/Maps.capacity:(I)I
        //    51: invokespecial   java/util/LinkedHashMap.<init>:(I)V
        //    54: astore_3       
        //    55: iconst_0       
        //    56: iload_2        
        //    57: if_icmpge       87
        //    60: aload_1        
        //    61: invokevirtual   java/io/ObjectInputStream.readObject:()Ljava/lang/Object;
        //    64: astore          5
        //    66: aload_3        
        //    67: aload           5
        //    69: aload_0        
        //    70: aload           5
        //    72: invokevirtual   com/google/common/collect/LinkedHashMultimap.createCollection:(Ljava/lang/Object;)Ljava/util/Collection;
        //    75: invokeinterface java/util/Map.put:(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
        //    80: pop            
        //    81: iinc            4, 1
        //    84: goto            55
        //    87: aload_1        
        //    88: invokevirtual   java/io/ObjectInputStream.readInt:()I
        //    91: istore          4
        //    93: iconst_0       
        //    94: iconst_0       
        //    95: if_icmpge       135
        //    98: aload_1        
        //    99: invokevirtual   java/io/ObjectInputStream.readObject:()Ljava/lang/Object;
        //   102: astore          6
        //   104: aload_1        
        //   105: invokevirtual   java/io/ObjectInputStream.readObject:()Ljava/lang/Object;
        //   108: astore          7
        //   110: aload_3        
        //   111: aload           6
        //   113: invokeinterface java/util/Map.get:(Ljava/lang/Object;)Ljava/lang/Object;
        //   118: checkcast       Ljava/util/Collection;
        //   121: aload           7
        //   123: invokeinterface java/util/Collection.add:(Ljava/lang/Object;)Z
        //   128: pop            
        //   129: iinc            5, 1
        //   132: goto            93
        //   135: aload_0        
        //   136: aload_3        
        //   137: invokevirtual   com/google/common/collect/LinkedHashMultimap.setMap:(Ljava/util/Map;)V
        //   140: return         
        //    Exceptions:
        //  throws java.io.IOException
        //  throws java.lang.ClassNotFoundException
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    @Override
    public boolean equals(final Object o) {
        return super.equals(o);
    }
    
    @Override
    public boolean put(final Object o, final Object o2) {
        return super.put(o, o2);
    }
    
    @Override
    public Map asMap() {
        return super.asMap();
    }
    
    @Override
    public Set removeAll(final Object o) {
        return super.removeAll(o);
    }
    
    @Override
    public Set get(final Object o) {
        return super.get(o);
    }
    
    @Override
    public Collection entries() {
        return this.entries();
    }
    
    @Override
    public Collection replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return super.containsKey(o);
    }
    
    @Override
    public int size() {
        return super.size();
    }
    
    @Override
    Collection createCollection() {
        return this.createCollection();
    }
    
    @Override
    public String toString() {
        return super.toString();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public Multiset keys() {
        return super.keys();
    }
    
    @Override
    public Set keySet() {
        return super.keySet();
    }
    
    @Override
    public boolean putAll(final Multimap multimap) {
        return super.putAll(multimap);
    }
    
    @Override
    public boolean putAll(final Object o, final Iterable iterable) {
        return super.putAll(o, iterable);
    }
    
    @Override
    public boolean remove(final Object o, final Object o2) {
        return super.remove(o, o2);
    }
    
    @Override
    public boolean containsEntry(final Object o, final Object o2) {
        return super.containsEntry(o, o2);
    }
    
    @Override
    public boolean containsValue(final Object o) {
        return super.containsValue(o);
    }
    
    @Override
    public boolean isEmpty() {
        return super.isEmpty();
    }
    
    static void access$200(final ValueSetLink valueSetLink, final ValueSetLink valueSetLink2) {
        succeedsInValueSet(valueSetLink, valueSetLink2);
    }
    
    static ValueEntry access$300(final LinkedHashMultimap linkedHashMultimap) {
        return linkedHashMultimap.multimapHeaderEntry;
    }
    
    static void access$400(final ValueEntry valueEntry, final ValueEntry valueEntry2) {
        succeedsInMultimap(valueEntry, valueEntry2);
    }
    
    static void access$500(final ValueSetLink valueSetLink) {
        deleteFromValueSet(valueSetLink);
    }
    
    static void access$600(final ValueEntry valueEntry) {
        deleteFromMultimap(valueEntry);
    }
    
    @VisibleForTesting
    final class ValueSet extends Sets.ImprovedAbstractSet implements ValueSetLink
    {
        private final Object key;
        @VisibleForTesting
        ValueEntry[] hashTable;
        private int size;
        private int modCount;
        private ValueSetLink firstEntry;
        private ValueSetLink lastEntry;
        final LinkedHashMultimap this$0;
        
        ValueSet(final LinkedHashMultimap this$0, final Object key, final int n) {
            this.this$0 = this$0;
            this.size = 0;
            this.modCount = 0;
            this.key = key;
            this.firstEntry = this;
            this.lastEntry = this;
            this.hashTable = new ValueEntry[Hashing.closedTableSize(n, 1.0)];
        }
        
        private int mask() {
            return this.hashTable.length - 1;
        }
        
        @Override
        public ValueSetLink getPredecessorInValueSet() {
            return this.lastEntry;
        }
        
        @Override
        public ValueSetLink getSuccessorInValueSet() {
            return this.firstEntry;
        }
        
        @Override
        public void setPredecessorInValueSet(final ValueSetLink lastEntry) {
            this.lastEntry = lastEntry;
        }
        
        @Override
        public void setSuccessorInValueSet(final ValueSetLink firstEntry) {
            this.firstEntry = firstEntry;
        }
        
        @Override
        public Iterator iterator() {
            return new Iterator() {
                ValueSetLink nextEntry = ValueSet.access$000(this.this$1);
                ValueEntry toRemove;
                int expectedModCount = ValueSet.access$100(this.this$1);
                final ValueSet this$1;
                
                private void checkForComodification() {
                    if (ValueSet.access$100(this.this$1) != this.expectedModCount) {
                        throw new ConcurrentModificationException();
                    }
                }
                
                @Override
                public boolean hasNext() {
                    this.checkForComodification();
                    return this.nextEntry != this.this$1;
                }
                
                @Override
                public Object next() {
                    if (!this.hasNext()) {
                        throw new NoSuchElementException();
                    }
                    final ValueEntry toRemove = (ValueEntry)this.nextEntry;
                    final Object value = toRemove.getValue();
                    this.toRemove = toRemove;
                    this.nextEntry = toRemove.getSuccessorInValueSet();
                    return value;
                }
                
                @Override
                public void remove() {
                    this.checkForComodification();
                    CollectPreconditions.checkRemove(this.toRemove != null);
                    this.this$1.remove(this.toRemove.getValue());
                    this.expectedModCount = ValueSet.access$100(this.this$1);
                    this.toRemove = null;
                }
            };
        }
        
        @Override
        public int size() {
            return this.size;
        }
        
        @Override
        public boolean contains(@Nullable final Object o) {
            final int smearedHash = Hashing.smearedHash(o);
            for (ValueEntry nextInValueBucket = this.hashTable[smearedHash & this.mask()]; nextInValueBucket != null; nextInValueBucket = nextInValueBucket.nextInValueBucket) {
                if (nextInValueBucket.matchesValue(o, smearedHash)) {
                    return true;
                }
            }
            return false;
        }
        
        @Override
        public boolean add(@Nullable final Object o) {
            final int smearedHash = Hashing.smearedHash(o);
            final int n = smearedHash & this.mask();
            ValueEntry nextInValueBucket;
            ValueEntry valueEntry;
            for (valueEntry = (nextInValueBucket = this.hashTable[n]); nextInValueBucket != null; nextInValueBucket = nextInValueBucket.nextInValueBucket) {
                if (nextInValueBucket.matchesValue(o, smearedHash)) {
                    return false;
                }
            }
            final ValueEntry valueEntry2 = new ValueEntry(this.key, o, smearedHash, valueEntry);
            LinkedHashMultimap.access$200(this.lastEntry, valueEntry2);
            LinkedHashMultimap.access$200(valueEntry2, this);
            LinkedHashMultimap.access$400(LinkedHashMultimap.access$300(this.this$0).getPredecessorInMultimap(), valueEntry2);
            LinkedHashMultimap.access$400(valueEntry2, LinkedHashMultimap.access$300(this.this$0));
            this.hashTable[n] = valueEntry2;
            ++this.size;
            ++this.modCount;
            this.rehashIfNecessary();
            return true;
        }
        
        private void rehashIfNecessary() {
            if (Hashing.needsResizing(this.size, this.hashTable.length, 1.0)) {
                final ValueEntry[] hashTable = new ValueEntry[this.hashTable.length * 2];
                this.hashTable = hashTable;
                final int n = hashTable.length - 1;
                for (ValueSetLink valueSetLink = this.firstEntry; valueSetLink != this; valueSetLink = valueSetLink.getSuccessorInValueSet()) {
                    final ValueEntry valueEntry = (ValueEntry)valueSetLink;
                    final int n2 = valueEntry.smearedValueHash & n;
                    valueEntry.nextInValueBucket = hashTable[n2];
                    hashTable[n2] = valueEntry;
                }
            }
        }
        
        @Override
        public boolean remove(@Nullable final Object o) {
            final int smearedHash = Hashing.smearedHash(o);
            final int n = smearedHash & this.mask();
            ValueEntry valueEntry = null;
            for (ValueEntry nextInValueBucket = this.hashTable[n]; nextInValueBucket != null; nextInValueBucket = nextInValueBucket.nextInValueBucket) {
                if (nextInValueBucket.matchesValue(o, smearedHash)) {
                    if (valueEntry == null) {
                        this.hashTable[n] = nextInValueBucket.nextInValueBucket;
                    }
                    else {
                        valueEntry.nextInValueBucket = nextInValueBucket.nextInValueBucket;
                    }
                    LinkedHashMultimap.access$500(nextInValueBucket);
                    LinkedHashMultimap.access$600(nextInValueBucket);
                    --this.size;
                    ++this.modCount;
                    return true;
                }
                valueEntry = nextInValueBucket;
            }
            return false;
        }
        
        @Override
        public void clear() {
            Arrays.fill(this.hashTable, null);
            this.size = 0;
            for (ValueSetLink valueSetLink = this.firstEntry; valueSetLink != this; valueSetLink = valueSetLink.getSuccessorInValueSet()) {
                LinkedHashMultimap.access$600((ValueEntry)valueSetLink);
            }
            LinkedHashMultimap.access$200(this, this);
            ++this.modCount;
        }
        
        static ValueSetLink access$000(final ValueSet set) {
            return set.firstEntry;
        }
        
        static int access$100(final ValueSet set) {
            return set.modCount;
        }
    }
    
    @VisibleForTesting
    static final class ValueEntry extends ImmutableEntry implements ValueSetLink
    {
        final int smearedValueHash;
        @Nullable
        ValueEntry nextInValueBucket;
        ValueSetLink predecessorInValueSet;
        ValueSetLink successorInValueSet;
        ValueEntry predecessorInMultimap;
        ValueEntry successorInMultimap;
        
        ValueEntry(@Nullable final Object o, @Nullable final Object o2, final int smearedValueHash, @Nullable final ValueEntry nextInValueBucket) {
            super(o, o2);
            this.smearedValueHash = smearedValueHash;
            this.nextInValueBucket = nextInValueBucket;
        }
        
        boolean matchesValue(@Nullable final Object o, final int n) {
            return this.smearedValueHash == n && Objects.equal(this.getValue(), o);
        }
        
        @Override
        public ValueSetLink getPredecessorInValueSet() {
            return this.predecessorInValueSet;
        }
        
        @Override
        public ValueSetLink getSuccessorInValueSet() {
            return this.successorInValueSet;
        }
        
        @Override
        public void setPredecessorInValueSet(final ValueSetLink predecessorInValueSet) {
            this.predecessorInValueSet = predecessorInValueSet;
        }
        
        @Override
        public void setSuccessorInValueSet(final ValueSetLink successorInValueSet) {
            this.successorInValueSet = successorInValueSet;
        }
        
        public ValueEntry getPredecessorInMultimap() {
            return this.predecessorInMultimap;
        }
        
        public ValueEntry getSuccessorInMultimap() {
            return this.successorInMultimap;
        }
        
        public void setSuccessorInMultimap(final ValueEntry successorInMultimap) {
            this.successorInMultimap = successorInMultimap;
        }
        
        public void setPredecessorInMultimap(final ValueEntry predecessorInMultimap) {
            this.predecessorInMultimap = predecessorInMultimap;
        }
    }
    
    private interface ValueSetLink
    {
        ValueSetLink getPredecessorInValueSet();
        
        ValueSetLink getSuccessorInValueSet();
        
        void setPredecessorInValueSet(final ValueSetLink p0);
        
        void setSuccessorInValueSet(final ValueSetLink p0);
    }
}
