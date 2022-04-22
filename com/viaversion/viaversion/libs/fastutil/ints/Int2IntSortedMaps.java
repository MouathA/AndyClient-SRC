package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.*;

public final class Int2IntSortedMaps
{
    public static final EmptySortedMap EMPTY_MAP;
    
    private Int2IntSortedMaps() {
    }
    
    public static Comparator entryComparator(final IntComparator intComparator) {
        return Int2IntSortedMaps::lambda$entryComparator$0;
    }
    
    public static ObjectBidirectionalIterator fastIterator(final Int2IntSortedMap int2IntSortedMap) {
        final ObjectSortedSet int2IntEntrySet = int2IntSortedMap.int2IntEntrySet();
        return (int2IntEntrySet instanceof Int2IntSortedMap.FastSortedEntrySet) ? ((Int2IntSortedMap.FastSortedEntrySet)int2IntEntrySet).fastIterator() : int2IntEntrySet.iterator();
    }
    
    public static ObjectBidirectionalIterable fastIterable(final Int2IntSortedMap int2IntSortedMap) {
        final ObjectSortedSet int2IntEntrySet = int2IntSortedMap.int2IntEntrySet();
        ObjectBidirectionalIterable objectBidirectionalIterable;
        if (int2IntEntrySet instanceof Int2IntSortedMap.FastSortedEntrySet) {
            final Int2IntSortedMap.FastSortedEntrySet set = (Int2IntSortedMap.FastSortedEntrySet)int2IntEntrySet;
            Objects.requireNonNull(set);
            objectBidirectionalIterable = set::fastIterator;
        }
        else {
            objectBidirectionalIterable = int2IntEntrySet;
        }
        return objectBidirectionalIterable;
    }
    
    public static Int2IntSortedMap singleton(final Integer n, final Integer n2) {
        return new Singleton(n, n2);
    }
    
    public static Int2IntSortedMap singleton(final Integer n, final Integer n2, final IntComparator intComparator) {
        return new Singleton(n, n2, intComparator);
    }
    
    public static Int2IntSortedMap singleton(final int n, final int n2) {
        return new Singleton(n, n2);
    }
    
    public static Int2IntSortedMap singleton(final int n, final int n2, final IntComparator intComparator) {
        return new Singleton(n, n2, intComparator);
    }
    
    public static Int2IntSortedMap synchronize(final Int2IntSortedMap int2IntSortedMap) {
        return (Int2IntSortedMap)new Int2IntSortedMaps.SynchronizedSortedMap(int2IntSortedMap);
    }
    
    public static Int2IntSortedMap synchronize(final Int2IntSortedMap int2IntSortedMap, final Object o) {
        return (Int2IntSortedMap)new Int2IntSortedMaps.SynchronizedSortedMap(int2IntSortedMap, o);
    }
    
    public static Int2IntSortedMap unmodifiable(final Int2IntSortedMap int2IntSortedMap) {
        return (Int2IntSortedMap)new Int2IntSortedMaps.UnmodifiableSortedMap(int2IntSortedMap);
    }
    
    private static int lambda$entryComparator$0(final IntComparator intComparator, final Map.Entry entry, final Map.Entry entry2) {
        return intComparator.compare((int)entry.getKey(), (int)entry2.getKey());
    }
    
    static {
        EMPTY_MAP = new EmptySortedMap();
    }
    
    public static class Singleton extends Int2IntMaps.Singleton implements Int2IntSortedMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntComparator comparator;
        
        protected Singleton(final int n, final int n2, final IntComparator comparator) {
            super(n, n2);
            this.comparator = comparator;
        }
        
        protected Singleton(final int n, final int n2) {
            this(n, n2, null);
        }
        
        final int compare(final int n, final int n2) {
            return (this.comparator == null) ? Integer.compare(n, n2) : this.comparator.compare(n, n2);
        }
        
        @Override
        public IntComparator comparator() {
            return this.comparator;
        }
        
        @Override
        public ObjectSortedSet int2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractInt2IntMap.BasicEntry(this.key, this.value), Int2IntSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }
        
        @Deprecated
        @Override
        public ObjectSortedSet entrySet() {
            return this.int2IntEntrySet();
        }
        
        @Override
        public IntSortedSet keySet() {
            if (this.keys == null) {
                this.keys = IntSortedSets.singleton(this.key, this.comparator);
            }
            return (IntSortedSet)this.keys;
        }
        
        @Override
        public Int2IntSortedMap subMap(final int n, final int n2) {
            if (this.compare(n, this.key) <= 0 && this.compare(this.key, n2) < 0) {
                return this;
            }
            return Int2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Int2IntSortedMap headMap(final int n) {
            if (this.compare(this.key, n) < 0) {
                return this;
            }
            return Int2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Int2IntSortedMap tailMap(final int n) {
            if (this.compare(n, this.key) <= 0) {
                return this;
            }
            return Int2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public int firstIntKey() {
            return this.key;
        }
        
        @Override
        public int lastIntKey() {
            return this.key;
        }
        
        @Deprecated
        @Override
        public Int2IntSortedMap headMap(final Integer n) {
            return this.headMap((int)n);
        }
        
        @Deprecated
        @Override
        public Int2IntSortedMap tailMap(final Integer n) {
            return this.tailMap((int)n);
        }
        
        @Deprecated
        @Override
        public Int2IntSortedMap subMap(final Integer n, final Integer n2) {
            return this.subMap((int)n, (int)n2);
        }
        
        @Deprecated
        @Override
        public Integer firstKey() {
            return this.firstIntKey();
        }
        
        @Deprecated
        @Override
        public Integer lastKey() {
            return this.lastIntKey();
        }
        
        @Override
        public IntSet keySet() {
            return this.keySet();
        }
        
        @Deprecated
        @Override
        public ObjectSet entrySet() {
            return this.entrySet();
        }
        
        @Override
        public ObjectSet int2IntEntrySet() {
            return this.int2IntEntrySet();
        }
        
        @Deprecated
        @Override
        public Set entrySet() {
            return this.entrySet();
        }
        
        @Override
        public Set keySet() {
            return this.keySet();
        }
        
        @Deprecated
        @Override
        public Object lastKey() {
            return this.lastKey();
        }
        
        @Deprecated
        @Override
        public Object firstKey() {
            return this.firstKey();
        }
        
        @Deprecated
        @Override
        public SortedMap tailMap(final Object o) {
            return this.tailMap((Integer)o);
        }
        
        @Deprecated
        @Override
        public SortedMap headMap(final Object o) {
            return this.headMap((Integer)o);
        }
        
        @Deprecated
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return this.subMap((Integer)o, (Integer)o2);
        }
        
        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
    
    public static class EmptySortedMap extends Int2IntMaps.EmptyMap implements Int2IntSortedMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptySortedMap() {
        }
        
        @Override
        public IntComparator comparator() {
            return null;
        }
        
        @Override
        public ObjectSortedSet int2IntEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Deprecated
        @Override
        public ObjectSortedSet entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public IntSortedSet keySet() {
            return IntSortedSets.EMPTY_SET;
        }
        
        @Override
        public Int2IntSortedMap subMap(final int n, final int n2) {
            return Int2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Int2IntSortedMap headMap(final int n) {
            return Int2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Int2IntSortedMap tailMap(final int n) {
            return Int2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public int firstIntKey() {
            throw new NoSuchElementException();
        }
        
        @Override
        public int lastIntKey() {
            throw new NoSuchElementException();
        }
        
        @Deprecated
        @Override
        public Int2IntSortedMap headMap(final Integer n) {
            return this.headMap((int)n);
        }
        
        @Deprecated
        @Override
        public Int2IntSortedMap tailMap(final Integer n) {
            return this.tailMap((int)n);
        }
        
        @Deprecated
        @Override
        public Int2IntSortedMap subMap(final Integer n, final Integer n2) {
            return this.subMap((int)n, (int)n2);
        }
        
        @Deprecated
        @Override
        public Integer firstKey() {
            return this.firstIntKey();
        }
        
        @Deprecated
        @Override
        public Integer lastKey() {
            return this.lastIntKey();
        }
        
        @Override
        public IntSet keySet() {
            return this.keySet();
        }
        
        @Override
        public ObjectSet int2IntEntrySet() {
            return this.int2IntEntrySet();
        }
        
        @Deprecated
        @Override
        public ObjectSet entrySet() {
            return this.entrySet();
        }
        
        @Deprecated
        @Override
        public Set entrySet() {
            return this.entrySet();
        }
        
        @Override
        public Set keySet() {
            return this.keySet();
        }
        
        @Deprecated
        @Override
        public Object lastKey() {
            return this.lastKey();
        }
        
        @Deprecated
        @Override
        public Object firstKey() {
            return this.firstKey();
        }
        
        @Deprecated
        @Override
        public SortedMap tailMap(final Object o) {
            return this.tailMap((Integer)o);
        }
        
        @Deprecated
        @Override
        public SortedMap headMap(final Object o) {
            return this.headMap((Integer)o);
        }
        
        @Deprecated
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return this.subMap((Integer)o, (Integer)o2);
        }
        
        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
}
