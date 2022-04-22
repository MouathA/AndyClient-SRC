package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import java.io.*;

@GwtCompatible(serializable = true, emulated = true)
class RegularImmutableBiMap extends ImmutableBiMap
{
    static final double MAX_LOAD_FACTOR = 1.2;
    private final transient ImmutableMapEntry[] keyTable;
    private final transient ImmutableMapEntry[] valueTable;
    private final transient ImmutableMapEntry[] entries;
    private final transient int mask;
    private final transient int hashCode;
    private transient ImmutableBiMap inverse;
    
    RegularImmutableBiMap(final ImmutableMapEntry.TerminalEntry... array) {
        this(array.length, array);
    }
    
    RegularImmutableBiMap(final int n, final ImmutableMapEntry.TerminalEntry[] array) {
        final int closedTableSize = Hashing.closedTableSize(n, 1.2);
        this.mask = closedTableSize - 1;
        final ImmutableMapEntry[] entryArray = createEntryArray(closedTableSize);
        final ImmutableMapEntry[] entryArray2 = createEntryArray(closedTableSize);
        final ImmutableMapEntry[] entryArray3 = createEntryArray(n);
        while (0 < n) {
            final ImmutableMapEntry.TerminalEntry terminalEntry = array[0];
            final Object key = terminalEntry.getKey();
            final Object value = terminalEntry.getValue();
            final int hashCode = key.hashCode();
            final int hashCode2 = value.hashCode();
            final int n2 = Hashing.smear(hashCode) & this.mask;
            final int n3 = Hashing.smear(hashCode2) & this.mask;
            ImmutableMapEntry nextInKeyBucket;
            ImmutableMapEntry immutableMapEntry;
            for (immutableMapEntry = (nextInKeyBucket = entryArray[n2]); nextInKeyBucket != null; nextInKeyBucket = nextInKeyBucket.getNextInKeyBucket()) {
                ImmutableMap.checkNoConflict(!key.equals(nextInKeyBucket.getKey()), "key", terminalEntry, nextInKeyBucket);
            }
            ImmutableMapEntry nextInValueBucket;
            ImmutableMapEntry immutableMapEntry2;
            for (immutableMapEntry2 = (nextInValueBucket = entryArray2[n3]); nextInValueBucket != null; nextInValueBucket = nextInValueBucket.getNextInValueBucket()) {
                ImmutableMap.checkNoConflict(!value.equals(nextInValueBucket.getValue()), "value", terminalEntry, nextInValueBucket);
            }
            final NonTerminalBiMapEntry nonTerminalBiMapEntry = (NonTerminalBiMapEntry)((immutableMapEntry == null && immutableMapEntry2 == null) ? terminalEntry : new NonTerminalBiMapEntry(terminalEntry, immutableMapEntry, immutableMapEntry2));
            entryArray[n2] = nonTerminalBiMapEntry;
            entryArray3[0] = (entryArray2[n3] = nonTerminalBiMapEntry);
            int n4 = 0;
            ++n4;
        }
        this.keyTable = entryArray;
        this.valueTable = entryArray2;
        this.entries = entryArray3;
        this.hashCode = 0;
    }
    
    RegularImmutableBiMap(final Map.Entry[] array) {
        final int length = array.length;
        final int closedTableSize = Hashing.closedTableSize(length, 1.2);
        this.mask = closedTableSize - 1;
        final ImmutableMapEntry[] entryArray = createEntryArray(closedTableSize);
        final ImmutableMapEntry[] entryArray2 = createEntryArray(closedTableSize);
        final ImmutableMapEntry[] entryArray3 = createEntryArray(length);
        while (0 < length) {
            final Map.Entry entry = array[0];
            final Object key = entry.getKey();
            final V value = entry.getValue();
            CollectPreconditions.checkEntryNotNull(key, value);
            final int hashCode = key.hashCode();
            final int hashCode2 = value.hashCode();
            final int n = Hashing.smear(hashCode) & this.mask;
            final int n2 = Hashing.smear(hashCode2) & this.mask;
            ImmutableMapEntry nextInKeyBucket;
            ImmutableMapEntry immutableMapEntry;
            for (immutableMapEntry = (nextInKeyBucket = entryArray[n]); nextInKeyBucket != null; nextInKeyBucket = nextInKeyBucket.getNextInKeyBucket()) {
                ImmutableMap.checkNoConflict(!key.equals(nextInKeyBucket.getKey()), "key", entry, nextInKeyBucket);
            }
            ImmutableMapEntry nextInValueBucket;
            ImmutableMapEntry immutableMapEntry2;
            for (immutableMapEntry2 = (nextInValueBucket = entryArray2[n2]); nextInValueBucket != null; nextInValueBucket = nextInValueBucket.getNextInValueBucket()) {
                ImmutableMap.checkNoConflict(!value.equals(nextInValueBucket.getValue()), "value", entry, nextInValueBucket);
            }
            final ImmutableMapEntry immutableMapEntry3 = (immutableMapEntry == null && immutableMapEntry2 == null) ? new ImmutableMapEntry.TerminalEntry(key, value) : new NonTerminalBiMapEntry(key, value, immutableMapEntry, immutableMapEntry2);
            entryArray[n] = immutableMapEntry3;
            entryArray3[0] = (entryArray2[n2] = immutableMapEntry3);
            int n3 = 0;
            ++n3;
        }
        this.keyTable = entryArray;
        this.valueTable = entryArray2;
        this.entries = entryArray3;
        this.hashCode = 0;
    }
    
    private static ImmutableMapEntry[] createEntryArray(final int n) {
        return new ImmutableMapEntry[n];
    }
    
    @Nullable
    @Override
    public Object get(@Nullable final Object o) {
        if (o == null) {
            return null;
        }
        for (ImmutableMapEntry nextInKeyBucket = this.keyTable[Hashing.smear(o.hashCode()) & this.mask]; nextInKeyBucket != null; nextInKeyBucket = nextInKeyBucket.getNextInKeyBucket()) {
            if (o.equals(nextInKeyBucket.getKey())) {
                return nextInKeyBucket.getValue();
            }
        }
        return null;
    }
    
    @Override
    ImmutableSet createEntrySet() {
        return new ImmutableMapEntrySet() {
            final RegularImmutableBiMap this$0;
            
            @Override
            ImmutableMap map() {
                return this.this$0;
            }
            
            @Override
            public UnmodifiableIterator iterator() {
                return this.asList().iterator();
            }
            
            @Override
            ImmutableList createAsList() {
                return new RegularImmutableAsList(this, RegularImmutableBiMap.access$000(this.this$0));
            }
            
            @Override
            boolean isHashCodeFast() {
                return true;
            }
            
            @Override
            public int hashCode() {
                return RegularImmutableBiMap.access$100(this.this$0);
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }
    
    @Override
    boolean isPartialView() {
        return false;
    }
    
    @Override
    public int size() {
        return this.entries.length;
    }
    
    @Override
    public ImmutableBiMap inverse() {
        final ImmutableBiMap inverse = this.inverse;
        return (inverse == null) ? (this.inverse = new Inverse(null)) : inverse;
    }
    
    @Override
    public BiMap inverse() {
        return this.inverse();
    }
    
    static ImmutableMapEntry[] access$000(final RegularImmutableBiMap regularImmutableBiMap) {
        return regularImmutableBiMap.entries;
    }
    
    static int access$100(final RegularImmutableBiMap regularImmutableBiMap) {
        return regularImmutableBiMap.hashCode;
    }
    
    static int access$300(final RegularImmutableBiMap regularImmutableBiMap) {
        return regularImmutableBiMap.mask;
    }
    
    static ImmutableMapEntry[] access$400(final RegularImmutableBiMap regularImmutableBiMap) {
        return regularImmutableBiMap.valueTable;
    }
    
    private static class InverseSerializedForm implements Serializable
    {
        private final ImmutableBiMap forward;
        private static final long serialVersionUID = 1L;
        
        InverseSerializedForm(final ImmutableBiMap forward) {
            this.forward = forward;
        }
        
        Object readResolve() {
            return this.forward.inverse();
        }
    }
    
    private final class Inverse extends ImmutableBiMap
    {
        final RegularImmutableBiMap this$0;
        
        private Inverse(final RegularImmutableBiMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public int size() {
            return this.inverse().size();
        }
        
        @Override
        public ImmutableBiMap inverse() {
            return this.this$0;
        }
        
        @Override
        public Object get(@Nullable final Object o) {
            if (o == null) {
                return null;
            }
            for (ImmutableMapEntry nextInValueBucket = RegularImmutableBiMap.access$400(this.this$0)[Hashing.smear(o.hashCode()) & RegularImmutableBiMap.access$300(this.this$0)]; nextInValueBucket != null; nextInValueBucket = nextInValueBucket.getNextInValueBucket()) {
                if (o.equals(nextInValueBucket.getValue())) {
                    return nextInValueBucket.getKey();
                }
            }
            return null;
        }
        
        @Override
        ImmutableSet createEntrySet() {
            return new InverseEntrySet();
        }
        
        @Override
        boolean isPartialView() {
            return false;
        }
        
        @Override
        Object writeReplace() {
            return new InverseSerializedForm(this.this$0);
        }
        
        @Override
        public BiMap inverse() {
            return this.inverse();
        }
        
        Inverse(final RegularImmutableBiMap regularImmutableBiMap, final RegularImmutableBiMap$1 immutableMapEntrySet) {
            this(regularImmutableBiMap);
        }
        
        final class InverseEntrySet extends ImmutableMapEntrySet
        {
            final Inverse this$1;
            
            InverseEntrySet(final Inverse this$1) {
                this.this$1 = this$1;
            }
            
            @Override
            ImmutableMap map() {
                return this.this$1;
            }
            
            @Override
            boolean isHashCodeFast() {
                return true;
            }
            
            @Override
            public int hashCode() {
                return RegularImmutableBiMap.access$100(this.this$1.this$0);
            }
            
            @Override
            public UnmodifiableIterator iterator() {
                return this.asList().iterator();
            }
            
            @Override
            ImmutableList createAsList() {
                return new ImmutableAsList() {
                    final InverseEntrySet this$2;
                    
                    @Override
                    public Map.Entry get(final int n) {
                        final ImmutableMapEntry immutableMapEntry = RegularImmutableBiMap.access$000(this.this$2.this$1.this$0)[n];
                        return Maps.immutableEntry(immutableMapEntry.getValue(), immutableMapEntry.getKey());
                    }
                    
                    @Override
                    ImmutableCollection delegateCollection() {
                        return this.this$2;
                    }
                    
                    @Override
                    public Object get(final int n) {
                        return this.get(n);
                    }
                };
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        }
    }
    
    private static final class NonTerminalBiMapEntry extends ImmutableMapEntry
    {
        @Nullable
        private final ImmutableMapEntry nextInKeyBucket;
        @Nullable
        private final ImmutableMapEntry nextInValueBucket;
        
        NonTerminalBiMapEntry(final Object o, final Object o2, @Nullable final ImmutableMapEntry nextInKeyBucket, @Nullable final ImmutableMapEntry nextInValueBucket) {
            super(o, o2);
            this.nextInKeyBucket = nextInKeyBucket;
            this.nextInValueBucket = nextInValueBucket;
        }
        
        NonTerminalBiMapEntry(final ImmutableMapEntry immutableMapEntry, @Nullable final ImmutableMapEntry nextInKeyBucket, @Nullable final ImmutableMapEntry nextInValueBucket) {
            super(immutableMapEntry);
            this.nextInKeyBucket = nextInKeyBucket;
            this.nextInValueBucket = nextInValueBucket;
        }
        
        @Nullable
        @Override
        ImmutableMapEntry getNextInKeyBucket() {
            return this.nextInKeyBucket;
        }
        
        @Nullable
        @Override
        ImmutableMapEntry getNextInValueBucket() {
            return this.nextInValueBucket;
        }
    }
}
