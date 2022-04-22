package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.*;
import java.util.function.*;

public final class Int2IntMaps
{
    public static final EmptyMap EMPTY_MAP;
    
    private Int2IntMaps() {
    }
    
    public static ObjectIterator fastIterator(final Int2IntMap int2IntMap) {
        final ObjectSet int2IntEntrySet = int2IntMap.int2IntEntrySet();
        return (int2IntEntrySet instanceof Int2IntMap.FastEntrySet) ? ((Int2IntMap.FastEntrySet)int2IntEntrySet).fastIterator() : int2IntEntrySet.iterator();
    }
    
    public static void fastForEach(final Int2IntMap int2IntMap, final Consumer consumer) {
        final ObjectSet int2IntEntrySet = int2IntMap.int2IntEntrySet();
        if (int2IntEntrySet instanceof Int2IntMap.FastEntrySet) {
            ((Int2IntMap.FastEntrySet)int2IntEntrySet).fastForEach(consumer);
        }
        else {
            int2IntEntrySet.forEach(consumer);
        }
    }
    
    public static ObjectIterable fastIterable(final Int2IntMap int2IntMap) {
        final ObjectSet int2IntEntrySet = int2IntMap.int2IntEntrySet();
        return (int2IntEntrySet instanceof Int2IntMap.FastEntrySet) ? new ObjectIterable() {
            final ObjectSet val$entries;
            
            @Override
            public ObjectIterator iterator() {
                return ((Int2IntMap.FastEntrySet)this.val$entries).fastIterator();
            }
            
            @Override
            public ObjectSpliterator spliterator() {
                return this.val$entries.spliterator();
            }
            
            @Override
            public void forEach(final Consumer consumer) {
                ((Int2IntMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }
            
            @Override
            public Spliterator spliterator() {
                return this.spliterator();
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : int2IntEntrySet;
    }
    
    public static Int2IntMap singleton(final int n, final int n2) {
        return new Singleton(n, n2);
    }
    
    public static Int2IntMap singleton(final Integer n, final Integer n2) {
        return new Singleton(n, n2);
    }
    
    public static Int2IntMap synchronize(final Int2IntMap int2IntMap) {
        return (Int2IntMap)new Int2IntMaps.SynchronizedMap(int2IntMap);
    }
    
    public static Int2IntMap synchronize(final Int2IntMap int2IntMap, final Object o) {
        return (Int2IntMap)new Int2IntMaps.SynchronizedMap(int2IntMap, o);
    }
    
    public static Int2IntMap unmodifiable(final Int2IntMap int2IntMap) {
        return (Int2IntMap)new Int2IntMaps.UnmodifiableMap(int2IntMap);
    }
    
    static {
        EMPTY_MAP = new EmptyMap();
    }
    
    public static class Singleton extends Int2IntFunctions.Singleton implements Int2IntMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet entries;
        protected transient IntSet keys;
        protected transient IntCollection values;
        
        protected Singleton(final int n, final int n2) {
            super(n, n2);
        }
        
        @Override
        public boolean containsValue(final int n) {
            return this.value == n;
        }
        
        @Deprecated
        @Override
        public boolean containsValue(final Object o) {
            return (int)o == this.value;
        }
        
        @Override
        public void putAll(final Map map) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ObjectSet int2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractInt2IntMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }
        
        @Deprecated
        @Override
        public ObjectSet entrySet() {
            return this.int2IntEntrySet();
        }
        
        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.singleton(this.key);
            }
            return this.keys;
        }
        
        @Override
        public IntCollection values() {
            if (this.values == null) {
                this.values = IntSets.singleton(this.value);
            }
            return this.values;
        }
        
        @Override
        public boolean isEmpty() {
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.key ^ this.value;
        }
        
        @Override
        public boolean equals(final Object o) {
            if (o == this) {
                return true;
            }
            if (!(o instanceof Map)) {
                return false;
            }
            final Map map = (Map)o;
            return map.size() == 1 && ((Map.Entry)map.entrySet().iterator().next()).equals(this.entrySet().iterator().next());
        }
        
        @Override
        public String toString() {
            return "{" + this.key + "=>" + this.value + "}";
        }
        
        @Deprecated
        @Override
        public Set entrySet() {
            return this.entrySet();
        }
        
        @Override
        public Collection values() {
            return this.values();
        }
        
        @Override
        public Set keySet() {
            return this.keySet();
        }
    }
    
    public static class EmptyMap extends Int2IntFunctions.EmptyFunction implements Int2IntMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptyMap() {
        }
        
        @Override
        public boolean containsValue(final int n) {
            return false;
        }
        
        @Deprecated
        @Override
        public Integer getOrDefault(final Object o, final Integer n) {
            return n;
        }
        
        @Override
        public int getOrDefault(final int n, final int n2) {
            return n2;
        }
        
        @Deprecated
        @Override
        public boolean containsValue(final Object o) {
            return false;
        }
        
        @Override
        public void putAll(final Map map) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ObjectSet int2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }
        
        @Override
        public IntSet keySet() {
            return IntSets.EMPTY_SET;
        }
        
        @Override
        public IntCollection values() {
            return IntSets.EMPTY_SET;
        }
        
        @Override
        public void forEach(final BiConsumer biConsumer) {
        }
        
        @Override
        public Object clone() {
            return Int2IntMaps.EMPTY_MAP;
        }
        
        @Override
        public boolean isEmpty() {
            return true;
        }
        
        @Override
        public int hashCode() {
            return 0;
        }
        
        @Override
        public boolean equals(final Object o) {
            return o instanceof Map && ((Map)o).isEmpty();
        }
        
        @Override
        public String toString() {
            return "{}";
        }
        
        @Deprecated
        @Override
        public Object getOrDefault(final Object o, final Object o2) {
            return this.getOrDefault(o, (Integer)o2);
        }
        
        @Override
        public Collection values() {
            return this.values();
        }
        
        @Override
        public Set keySet() {
            return this.keySet();
        }
    }
}
