package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.*;

public final class Object2ObjectSortedMaps
{
    public static final EmptySortedMap EMPTY_MAP;
    
    private Object2ObjectSortedMaps() {
    }
    
    public static Comparator entryComparator(final Comparator comparator) {
        return Object2ObjectSortedMaps::lambda$entryComparator$0;
    }
    
    public static ObjectBidirectionalIterator fastIterator(final Object2ObjectSortedMap object2ObjectSortedMap) {
        final ObjectSortedSet object2ObjectEntrySet = object2ObjectSortedMap.object2ObjectEntrySet();
        return (object2ObjectEntrySet instanceof Object2ObjectSortedMap.FastSortedEntrySet) ? ((Object2ObjectSortedMap.FastSortedEntrySet)object2ObjectEntrySet).fastIterator() : object2ObjectEntrySet.iterator();
    }
    
    public static ObjectBidirectionalIterable fastIterable(final Object2ObjectSortedMap object2ObjectSortedMap) {
        final ObjectSortedSet object2ObjectEntrySet = object2ObjectSortedMap.object2ObjectEntrySet();
        ObjectBidirectionalIterable objectBidirectionalIterable;
        if (object2ObjectEntrySet instanceof Object2ObjectSortedMap.FastSortedEntrySet) {
            final Object2ObjectSortedMap.FastSortedEntrySet set = (Object2ObjectSortedMap.FastSortedEntrySet)object2ObjectEntrySet;
            Objects.requireNonNull(set);
            objectBidirectionalIterable = set::fastIterator;
        }
        else {
            objectBidirectionalIterable = object2ObjectEntrySet;
        }
        return objectBidirectionalIterable;
    }
    
    public static Object2ObjectSortedMap emptyMap() {
        return Object2ObjectSortedMaps.EMPTY_MAP;
    }
    
    public static Object2ObjectSortedMap singleton(final Object o, final Object o2) {
        return new Singleton(o, o2);
    }
    
    public static Object2ObjectSortedMap singleton(final Object o, final Object o2, final Comparator comparator) {
        return new Singleton(o, o2, comparator);
    }
    
    public static Object2ObjectSortedMap synchronize(final Object2ObjectSortedMap object2ObjectSortedMap) {
        return (Object2ObjectSortedMap)new Object2ObjectSortedMaps.SynchronizedSortedMap(object2ObjectSortedMap);
    }
    
    public static Object2ObjectSortedMap synchronize(final Object2ObjectSortedMap object2ObjectSortedMap, final Object o) {
        return (Object2ObjectSortedMap)new Object2ObjectSortedMaps.SynchronizedSortedMap(object2ObjectSortedMap, o);
    }
    
    public static Object2ObjectSortedMap unmodifiable(final Object2ObjectSortedMap object2ObjectSortedMap) {
        return (Object2ObjectSortedMap)new Object2ObjectSortedMaps.UnmodifiableSortedMap(object2ObjectSortedMap);
    }
    
    private static int lambda$entryComparator$0(final Comparator comparator, final Map.Entry entry, final Map.Entry entry2) {
        return comparator.compare(entry.getKey(), entry2.getKey());
    }
    
    static {
        EMPTY_MAP = new EmptySortedMap();
    }
    
    public static class EmptySortedMap extends Object2ObjectMaps.EmptyMap implements Object2ObjectSortedMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptySortedMap() {
        }
        
        @Override
        public Comparator comparator() {
            return null;
        }
        
        @Override
        public ObjectSortedSet object2ObjectEntrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public ObjectSortedSet entrySet() {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public ObjectSortedSet keySet() {
            return ObjectSortedSets.EMPTY_SET;
        }
        
        @Override
        public Object2ObjectSortedMap subMap(final Object o, final Object o2) {
            return Object2ObjectSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Object2ObjectSortedMap headMap(final Object o) {
            return Object2ObjectSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Object2ObjectSortedMap tailMap(final Object o) {
            return Object2ObjectSortedMaps.EMPTY_MAP;
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
        public ObjectSet object2ObjectEntrySet() {
            return this.object2ObjectEntrySet();
        }
        
        @Override
        public ObjectSet entrySet() {
            return this.entrySet();
        }
        
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
    
    public static class Singleton extends Object2ObjectMaps.Singleton implements Object2ObjectSortedMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected final Comparator comparator;
        
        protected Singleton(final Object o, final Object o2, final Comparator comparator) {
            super(o, o2);
            this.comparator = comparator;
        }
        
        protected Singleton(final Object o, final Object o2) {
            this(o, o2, null);
        }
        
        final int compare(final Object o, final Object o2) {
            return (this.comparator == null) ? ((Comparable)o).compareTo(o2) : this.comparator.compare(o, o2);
        }
        
        @Override
        public Comparator comparator() {
            return this.comparator;
        }
        
        @Override
        public ObjectSortedSet object2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSortedSets.singleton(new AbstractObject2ObjectMap.BasicEntry(this.key, this.value), Object2ObjectSortedMaps.entryComparator(this.comparator));
            }
            return (ObjectSortedSet)this.entries;
        }
        
        @Override
        public ObjectSortedSet entrySet() {
            return this.object2ObjectEntrySet();
        }
        
        @Override
        public ObjectSortedSet keySet() {
            if (this.keys == null) {
                this.keys = ObjectSortedSets.singleton(this.key, this.comparator);
            }
            return (ObjectSortedSet)this.keys;
        }
        
        @Override
        public Object2ObjectSortedMap subMap(final Object o, final Object o2) {
            if (this.compare(o, this.key) <= 0 && this.compare(this.key, o2) < 0) {
                return this;
            }
            return Object2ObjectSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Object2ObjectSortedMap headMap(final Object o) {
            if (this.compare(this.key, o) < 0) {
                return this;
            }
            return Object2ObjectSortedMaps.EMPTY_MAP;
        }
        
        @Override
        public Object2ObjectSortedMap tailMap(final Object o) {
            if (this.compare(o, this.key) <= 0) {
                return this;
            }
            return Object2ObjectSortedMaps.EMPTY_MAP;
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
        
        @Override
        public ObjectSet entrySet() {
            return this.entrySet();
        }
        
        @Override
        public ObjectSet object2ObjectEntrySet() {
            return this.object2ObjectEntrySet();
        }
        
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
