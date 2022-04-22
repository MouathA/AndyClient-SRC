package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.*;

public final class Int2ObjectSortedMaps
{
    public static final EmptySortedMap EMPTY_MAP;
    
    private Int2ObjectSortedMaps() {
    }
    
    public static Comparator entryComparator(final IntComparator intComparator) {
        return Int2ObjectSortedMaps::lambda$entryComparator$0;
    }
    
    public static ObjectBidirectionalIterator fastIterator(final Int2ObjectSortedMap int2ObjectSortedMap) {
        final ObjectSortedSet int2ObjectEntrySet = int2ObjectSortedMap.int2ObjectEntrySet();
        return (int2ObjectEntrySet instanceof Int2ObjectSortedMap.FastSortedEntrySet) ? ((Int2ObjectSortedMap.FastSortedEntrySet)int2ObjectEntrySet).fastIterator() : int2ObjectEntrySet.iterator();
    }
    
    public static ObjectBidirectionalIterable fastIterable(final Int2ObjectSortedMap int2ObjectSortedMap) {
        final ObjectSortedSet int2ObjectEntrySet = int2ObjectSortedMap.int2ObjectEntrySet();
        ObjectBidirectionalIterable objectBidirectionalIterable;
        if (int2ObjectEntrySet instanceof Int2ObjectSortedMap.FastSortedEntrySet) {
            final Int2ObjectSortedMap.FastSortedEntrySet set = (Int2ObjectSortedMap.FastSortedEntrySet)int2ObjectEntrySet;
            Objects.requireNonNull(set);
            objectBidirectionalIterable = set::fastIterator;
        }
        else {
            objectBidirectionalIterable = int2ObjectEntrySet;
        }
        return objectBidirectionalIterable;
    }
    
    public static Int2ObjectSortedMap emptyMap() {
        return Int2ObjectSortedMaps.EMPTY_MAP;
    }
    
    public static Int2ObjectSortedMap singleton(final Integer n, final Object o) {
        return new Singleton(n, o);
    }
    
    public static Int2ObjectSortedMap singleton(final Integer n, final Object o, final IntComparator intComparator) {
        return new Singleton(n, o, intComparator);
    }
    
    public static Int2ObjectSortedMap singleton(final int n, final Object o) {
        return new Singleton(n, o);
    }
    
    public static Int2ObjectSortedMap singleton(final int n, final Object o, final IntComparator intComparator) {
        return new Singleton(n, o, intComparator);
    }
    
    public static Int2ObjectSortedMap synchronize(final Int2ObjectSortedMap int2ObjectSortedMap) {
        return (Int2ObjectSortedMap)new Int2ObjectSortedMaps.SynchronizedSortedMap(int2ObjectSortedMap);
    }
    
    public static Int2ObjectSortedMap synchronize(final Int2ObjectSortedMap int2ObjectSortedMap, final Object o) {
        return (Int2ObjectSortedMap)new Int2ObjectSortedMaps.SynchronizedSortedMap(int2ObjectSortedMap, o);
    }
    
    public static Int2ObjectSortedMap unmodifiable(final Int2ObjectSortedMap int2ObjectSortedMap) {
        return (Int2ObjectSortedMap)new Int2ObjectSortedMaps.UnmodifiableSortedMap(int2ObjectSortedMap);
    }
    
    private static int lambda$entryComparator$0(final IntComparator intComparator, final Map.Entry entry, final Map.Entry entry2) {
        return intComparator.compare((int)entry.getKey(), (int)entry2.getKey());
    }
    
    static {
        EMPTY_MAP = new EmptySortedMap();
    }
    
    public static class EmptySortedMap extends Int2ObjectMaps.EmptyMap implements Int2ObjectSortedMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptySortedMap() {
        }
        
        @Override
        public IntComparator comparator() {
            return null;
        }
        
        @Override
        public ObjectSortedSet int2ObjectEntrySet() {
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
        public Int2ObjectSortedMap subMap(final int n, final int n2) {
            return Int2ObjectSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Int2ObjectSortedMap headMap(final int n) {
            return Int2ObjectSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Int2ObjectSortedMap tailMap(final int n) {
            return Int2ObjectSortedMaps.EMPTY_MAP;
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
        public Int2ObjectSortedMap headMap(final Integer n) {
            return this.headMap((int)n);
        }
        
        @Deprecated
        @Override
        public Int2ObjectSortedMap tailMap(final Integer n) {
            return this.tailMap((int)n);
        }
        
        @Deprecated
        @Override
        public Int2ObjectSortedMap subMap(final Integer n, final Integer n2) {
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
        public ObjectSet int2ObjectEntrySet() {
            return this.int2ObjectEntrySet();
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
    
    public static class Singleton extends Int2ObjectMaps.Singleton implements Int2ObjectSortedMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final IntComparator comparator;
        
        protected Singleton(final int n, final Object o, final IntComparator comparator) {
            super(n, o);
            this.comparator = comparator;
        }
        
        protected Singleton(final int n, final Object o) {
            this(n, o, null);
        }
        
        final int compare(final int n, final int n2) {
            return (this.comparator == null) ? Integer.compare(n, n2) : this.comparator.compare(n, n2);
        }
        
        @Override
        public IntComparator comparator() {
            return this.comparator;
        }
        
        @Override
        public ObjectSortedSet int2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractInt2ObjectMap.BasicEntry(this.key, this.value), Int2ObjectSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }
        
        @Deprecated
        @Override
        public ObjectSortedSet entrySet() {
            return this.int2ObjectEntrySet();
        }
        
        @Override
        public IntSortedSet keySet() {
            if (this.keys == null) {
                this.keys = IntSortedSets.singleton(this.key, this.comparator);
            }
            return (IntSortedSet)this.keys;
        }
        
        @Override
        public Int2ObjectSortedMap subMap(final int n, final int n2) {
            if (this.compare(n, this.key) <= 0 && this.compare(this.key, n2) < 0) {
                return this;
            }
            return Int2ObjectSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Int2ObjectSortedMap headMap(final int n) {
            if (this.compare(this.key, n) < 0) {
                return this;
            }
            return Int2ObjectSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Int2ObjectSortedMap tailMap(final int n) {
            if (this.compare(n, this.key) <= 0) {
                return this;
            }
            return Int2ObjectSortedMaps.EMPTY_MAP;
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
        public Int2ObjectSortedMap headMap(final Integer n) {
            return this.headMap((int)n);
        }
        
        @Deprecated
        @Override
        public Int2ObjectSortedMap tailMap(final Integer n) {
            return this.tailMap((int)n);
        }
        
        @Deprecated
        @Override
        public Int2ObjectSortedMap subMap(final Integer n, final Integer n2) {
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
        public ObjectSet int2ObjectEntrySet() {
            return this.int2ObjectEntrySet();
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
