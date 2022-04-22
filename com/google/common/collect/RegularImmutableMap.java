package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
final class RegularImmutableMap extends ImmutableMap
{
    private final transient ImmutableMapEntry[] entries;
    private final transient ImmutableMapEntry[] table;
    private final transient int mask;
    private static final double MAX_LOAD_FACTOR = 1.2;
    private static final long serialVersionUID = 0L;
    
    RegularImmutableMap(final ImmutableMapEntry.TerminalEntry... array) {
        this(array.length, array);
    }
    
    RegularImmutableMap(final int n, final ImmutableMapEntry.TerminalEntry[] array) {
        this.entries = this.createEntryArray(n);
        final int closedTableSize = Hashing.closedTableSize(n, 1.2);
        this.table = this.createEntryArray(closedTableSize);
        this.mask = closedTableSize - 1;
        while (0 < n) {
            final ImmutableMapEntry.TerminalEntry terminalEntry = array[0];
            final Object key = terminalEntry.getKey();
            final int n2 = Hashing.smear(key.hashCode()) & this.mask;
            final ImmutableMapEntry immutableMapEntry = this.table[n2];
            final NonTerminalMapEntry nonTerminalMapEntry = (NonTerminalMapEntry)((immutableMapEntry == null) ? terminalEntry : new NonTerminalMapEntry(terminalEntry, immutableMapEntry));
            this.table[n2] = nonTerminalMapEntry;
            this.checkNoConflictInBucket(key, this.entries[0] = nonTerminalMapEntry, immutableMapEntry);
            int n3 = 0;
            ++n3;
        }
    }
    
    RegularImmutableMap(final Map.Entry[] array) {
        final int length = array.length;
        this.entries = this.createEntryArray(length);
        final int closedTableSize = Hashing.closedTableSize(length, 1.2);
        this.table = this.createEntryArray(closedTableSize);
        this.mask = closedTableSize - 1;
        while (0 < length) {
            final Map.Entry entry = array[0];
            final Object key = entry.getKey();
            final V value = entry.getValue();
            CollectPreconditions.checkEntryNotNull(key, value);
            final int n = Hashing.smear(key.hashCode()) & this.mask;
            final ImmutableMapEntry immutableMapEntry = this.table[n];
            final ImmutableMapEntry immutableMapEntry2 = (immutableMapEntry == null) ? new ImmutableMapEntry.TerminalEntry(key, value) : new NonTerminalMapEntry(key, value, immutableMapEntry);
            this.table[n] = immutableMapEntry2;
            this.checkNoConflictInBucket(key, this.entries[0] = immutableMapEntry2, immutableMapEntry);
            int n2 = 0;
            ++n2;
        }
    }
    
    private void checkNoConflictInBucket(final Object o, final ImmutableMapEntry immutableMapEntry, ImmutableMapEntry nextInKeyBucket) {
        while (nextInKeyBucket != null) {
            ImmutableMap.checkNoConflict(!o.equals(nextInKeyBucket.getKey()), "key", immutableMapEntry, nextInKeyBucket);
            nextInKeyBucket = nextInKeyBucket.getNextInKeyBucket();
        }
    }
    
    private ImmutableMapEntry[] createEntryArray(final int n) {
        return new ImmutableMapEntry[n];
    }
    
    @Override
    public Object get(@Nullable final Object o) {
        if (o == null) {
            return null;
        }
        for (ImmutableMapEntry nextInKeyBucket = this.table[Hashing.smear(o.hashCode()) & this.mask]; nextInKeyBucket != null; nextInKeyBucket = nextInKeyBucket.getNextInKeyBucket()) {
            if (o.equals(nextInKeyBucket.getKey())) {
                return nextInKeyBucket.getValue();
            }
        }
        return null;
    }
    
    @Override
    public int size() {
        return this.entries.length;
    }
    
    @Override
    boolean isPartialView() {
        return false;
    }
    
    @Override
    ImmutableSet createEntrySet() {
        return new EntrySet(null);
    }
    
    static ImmutableMapEntry[] access$100(final RegularImmutableMap regularImmutableMap) {
        return regularImmutableMap.entries;
    }
    
    private class EntrySet extends ImmutableMapEntrySet
    {
        final RegularImmutableMap this$0;
        
        private EntrySet(final RegularImmutableMap this$0) {
            this.this$0 = this$0;
        }
        
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
            return new RegularImmutableAsList(this, RegularImmutableMap.access$100(this.this$0));
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        EntrySet(final RegularImmutableMap regularImmutableMap, final RegularImmutableMap$1 object) {
            this(regularImmutableMap);
        }
    }
    
    private static final class NonTerminalMapEntry extends ImmutableMapEntry
    {
        private final ImmutableMapEntry nextInKeyBucket;
        
        NonTerminalMapEntry(final Object o, final Object o2, final ImmutableMapEntry nextInKeyBucket) {
            super(o, o2);
            this.nextInKeyBucket = nextInKeyBucket;
        }
        
        NonTerminalMapEntry(final ImmutableMapEntry immutableMapEntry, final ImmutableMapEntry nextInKeyBucket) {
            super(immutableMapEntry);
            this.nextInKeyBucket = nextInKeyBucket;
        }
        
        @Override
        ImmutableMapEntry getNextInKeyBucket() {
            return this.nextInKeyBucket;
        }
        
        @Nullable
        @Override
        ImmutableMapEntry getNextInValueBucket() {
            return null;
        }
    }
}
