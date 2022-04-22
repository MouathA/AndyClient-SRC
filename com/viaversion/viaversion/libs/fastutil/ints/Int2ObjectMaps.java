package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.function.*;
import java.util.*;

public final class Int2ObjectMaps
{
    public static final EmptyMap EMPTY_MAP;
    
    private Int2ObjectMaps() {
    }
    
    public static ObjectIterator fastIterator(final Int2ObjectMap int2ObjectMap) {
        final ObjectSet int2ObjectEntrySet = int2ObjectMap.int2ObjectEntrySet();
        return (int2ObjectEntrySet instanceof Int2ObjectMap.FastEntrySet) ? ((Int2ObjectMap.FastEntrySet)int2ObjectEntrySet).fastIterator() : int2ObjectEntrySet.iterator();
    }
    
    public static void fastForEach(final Int2ObjectMap int2ObjectMap, final Consumer consumer) {
        final ObjectSet int2ObjectEntrySet = int2ObjectMap.int2ObjectEntrySet();
        if (int2ObjectEntrySet instanceof Int2ObjectMap.FastEntrySet) {
            ((Int2ObjectMap.FastEntrySet)int2ObjectEntrySet).fastForEach(consumer);
        }
        else {
            int2ObjectEntrySet.forEach(consumer);
        }
    }
    
    public static ObjectIterable fastIterable(final Int2ObjectMap int2ObjectMap) {
        final ObjectSet int2ObjectEntrySet = int2ObjectMap.int2ObjectEntrySet();
        return (int2ObjectEntrySet instanceof Int2ObjectMap.FastEntrySet) ? new ObjectIterable() {
            final ObjectSet val$entries;
            
            @Override
            public ObjectIterator iterator() {
                return ((Int2ObjectMap.FastEntrySet)this.val$entries).fastIterator();
            }
            
            @Override
            public ObjectSpliterator spliterator() {
                return this.val$entries.spliterator();
            }
            
            @Override
            public void forEach(final Consumer consumer) {
                ((Int2ObjectMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }
            
            @Override
            public Spliterator spliterator() {
                return this.spliterator();
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : int2ObjectEntrySet;
    }
    
    public static Int2ObjectMap emptyMap() {
        return Int2ObjectMaps.EMPTY_MAP;
    }
    
    public static Int2ObjectMap singleton(final int n, final Object o) {
        return new Singleton(n, o);
    }
    
    public static Int2ObjectMap singleton(final Integer n, final Object o) {
        return new Singleton(n, o);
    }
    
    public static Int2ObjectMap synchronize(final Int2ObjectMap int2ObjectMap) {
        return (Int2ObjectMap)new Int2ObjectMaps.SynchronizedMap(int2ObjectMap);
    }
    
    public static Int2ObjectMap synchronize(final Int2ObjectMap int2ObjectMap, final Object o) {
        return (Int2ObjectMap)new Int2ObjectMaps.SynchronizedMap(int2ObjectMap, o);
    }
    
    public static Int2ObjectMap unmodifiable(final Int2ObjectMap int2ObjectMap) {
        return (Int2ObjectMap)new Int2ObjectMaps.UnmodifiableMap(int2ObjectMap);
    }
    
    static {
        EMPTY_MAP = new EmptyMap();
    }
    
    public static class EmptyMap extends Int2ObjectFunctions.EmptyFunction implements Int2ObjectMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptyMap() {
        }
        
        @Override
        public boolean containsValue(final Object o) {
            return false;
        }
        
        @Deprecated
        @Override
        public Object getOrDefault(final Object o, final Object o2) {
            return o2;
        }
        
        @Override
        public Object getOrDefault(final int n, final Object o) {
            return o;
        }
        
        @Override
        public void putAll(final Map map) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ObjectSet int2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }
        
        @Override
        public IntSet keySet() {
            return IntSets.EMPTY_SET;
        }
        
        @Override
        public ObjectCollection values() {
            return ObjectSets.EMPTY_SET;
        }
        
        @Override
        public void forEach(final BiConsumer biConsumer) {
        }
        
        @Override
        public Object clone() {
            return Int2ObjectMaps.EMPTY_MAP;
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
        
        @Override
        public Collection values() {
            return this.values();
        }
        
        @Override
        public Set keySet() {
            return this.keySet();
        }
    }
    
    public static class Singleton extends Int2ObjectFunctions.Singleton implements Int2ObjectMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet entries;
        protected transient IntSet keys;
        protected transient ObjectCollection values;
        
        protected Singleton(final int n, final Object o) {
            super(n, o);
        }
        
        @Override
        public boolean containsValue(final Object o) {
            return Objects.equals(this.value, o);
        }
        
        @Override
        public void putAll(final Map map) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ObjectSet int2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractInt2ObjectMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }
        
        @Deprecated
        @Override
        public ObjectSet entrySet() {
            return this.int2ObjectEntrySet();
        }
        
        @Override
        public IntSet keySet() {
            if (this.keys == null) {
                this.keys = IntSets.singleton(this.key);
            }
            return this.keys;
        }
        
        @Override
        public ObjectCollection values() {
            if (this.values == null) {
                this.values = ObjectSets.singleton(this.value);
            }
            return this.values;
        }
        
        @Override
        public boolean isEmpty() {
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.key ^ ((this.value == null) ? 0 : this.value.hashCode());
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
}
