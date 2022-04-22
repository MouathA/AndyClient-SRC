package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.*;

public final class Object2IntSortedMaps
{
    public static final EmptySortedMap EMPTY_MAP;
    
    private Object2IntSortedMaps() {
    }
    
    public static Comparator entryComparator(final Comparator comparator) {
        return Object2IntSortedMaps::lambda$entryComparator$0;
    }
    
    public static ObjectBidirectionalIterator fastIterator(final Object2IntSortedMap object2IntSortedMap) {
        final ObjectSortedSet object2IntEntrySet = object2IntSortedMap.object2IntEntrySet();
        return (object2IntEntrySet instanceof Object2IntSortedMap.FastSortedEntrySet) ? ((Object2IntSortedMap.FastSortedEntrySet)object2IntEntrySet).fastIterator() : object2IntEntrySet.iterator();
    }
    
    public static ObjectBidirectionalIterable fastIterable(final Object2IntSortedMap object2IntSortedMap) {
        final ObjectSortedSet object2IntEntrySet = object2IntSortedMap.object2IntEntrySet();
        ObjectBidirectionalIterable objectBidirectionalIterable;
        if (object2IntEntrySet instanceof Object2IntSortedMap.FastSortedEntrySet) {
            final Object2IntSortedMap.FastSortedEntrySet set = (Object2IntSortedMap.FastSortedEntrySet)object2IntEntrySet;
            Objects.requireNonNull(set);
            objectBidirectionalIterable = set::fastIterator;
        }
        else {
            objectBidirectionalIterable = object2IntEntrySet;
        }
        return objectBidirectionalIterable;
    }
    
    public static Object2IntSortedMap emptyMap() {
        return Object2IntSortedMaps.EMPTY_MAP;
    }
    
    public static Object2IntSortedMap singleton(final Object o, final Integer n) {
        return new Singleton(o, n);
    }
    
    public static Object2IntSortedMap singleton(final Object o, final Integer n, final Comparator comparator) {
        return new Singleton(o, n, comparator);
    }
    
    public static Object2IntSortedMap singleton(final Object o, final int n) {
        return new Singleton(o, n);
    }
    
    public static Object2IntSortedMap singleton(final Object o, final int n, final Comparator comparator) {
        return new Singleton(o, n, comparator);
    }
    
    public static Object2IntSortedMap synchronize(final Object2IntSortedMap object2IntSortedMap) {
        return (Object2IntSortedMap)new Object2IntSortedMaps.SynchronizedSortedMap(object2IntSortedMap);
    }
    
    public static Object2IntSortedMap synchronize(final Object2IntSortedMap object2IntSortedMap, final Object o) {
        return (Object2IntSortedMap)new Object2IntSortedMaps.SynchronizedSortedMap(object2IntSortedMap, o);
    }
    
    public static Object2IntSortedMap unmodifiable(final Object2IntSortedMap object2IntSortedMap) {
        return (Object2IntSortedMap)new Object2IntSortedMaps.UnmodifiableSortedMap(object2IntSortedMap);
    }
    
    private static int lambda$entryComparator$0(final Comparator comparator, final Map.Entry entry, final Map.Entry entry2) {
        return comparator.compare(entry.getKey(), entry2.getKey());
    }
    
    static {
        EMPTY_MAP = new EmptySortedMap();
    }
    
    public static class EmptySortedMap extends Object2IntMaps.EmptyMap implements Object2IntSortedMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptySortedMap() {
        }
        
        @Override
        public Comparator comparator() {
            return null;
        }
        
        @Override
        public ObjectSortedSet object2IntEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Deprecated
        @Override
        public ObjectSortedSet entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public ObjectSortedSet keySet() {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public Object2IntSortedMap subMap(final Object o, final Object o2) {
            return Object2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Object2IntSortedMap headMap(final Object o) {
            return Object2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Object2IntSortedMap tailMap(final Object o) {
            return Object2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Object firstKey() {
            throw new NoSuchElementException();
        }
        
        @Override
        public Object lastKey() {
            throw new NoSuchElementException();
        }
        
        @Override
        public ObjectSet keySet() {
            return this.keySet();
        }
        
        @Override
        public ObjectSet object2IntEntrySet() {
            return this.object2IntEntrySet();
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
        
        @Override
        public SortedMap tailMap(final Object o) {
            return this.tailMap(o);
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            return this.headMap(o);
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return this.subMap(o, o2);
        }
    }
    
    public static class Singleton extends Object2IntMaps.Singleton implements Object2IntSortedMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator comparator;
        
        protected Singleton(final Object o, final int n, final Comparator comparator) {
            super(o, n);
            this.comparator = comparator;
        }
        
        protected Singleton(final Object o, final int n) {
            this(o, n, null);
        }
        
        final int compare(final Object o, final Object o2) {
            return (this.comparator == null) ? ((Comparable)o).compareTo(o2) : this.comparator.compare(o, o2);
        }
        
        @Override
        public Comparator comparator() {
            return this.comparator;
        }
        
        @Override
        public ObjectSortedSet object2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractObject2IntMap.BasicEntry(this.key, this.value), Object2IntSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }
        
        @Deprecated
        @Override
        public ObjectSortedSet entrySet() {
            return this.object2IntEntrySet();
        }
        
        @Override
        public ObjectSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
            }
            return (ObjectSortedSet)this.keys;
        }
        
        @Override
        public Object2IntSortedMap subMap(final Object o, final Object o2) {
            if (this.compare(o, this.key) <= 0 && this.compare(this.key, o2) < 0) {
                return this;
            }
            return Object2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Object2IntSortedMap headMap(final Object o) {
            if (this.compare(this.key, o) < 0) {
                return this;
            }
            return Object2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Object2IntSortedMap tailMap(final Object o) {
            if (this.compare(o, this.key) <= 0) {
                return this;
            }
            return Object2IntSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Object firstKey() {
            return this.key;
        }
        
        @Override
        public Object lastKey() {
            return this.key;
        }
        
        @Override
        public ObjectSet keySet() {
            return this.keySet();
        }
        
        @Deprecated
        @Override
        public ObjectSet entrySet() {
            return this.entrySet();
        }
        
        @Override
        public ObjectSet object2IntEntrySet() {
            return this.object2IntEntrySet();
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
        
        @Override
        public SortedMap tailMap(final Object o) {
            return this.tailMap(o);
        }
        
        @Override
        public SortedMap headMap(final Object o) {
            return this.headMap(o);
        }
        
        @Override
        public SortedMap subMap(final Object o, final Object o2) {
            return this.subMap(o, o2);
        }
    }
}
