package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.function.*;
import java.util.*;

public final class Object2IntMaps
{
    public static final EmptyMap EMPTY_MAP;
    
    private Object2IntMaps() {
    }
    
    public static ObjectIterator fastIterator(final Object2IntMap object2IntMap) {
        final ObjectSet object2IntEntrySet = object2IntMap.object2IntEntrySet();
        return (object2IntEntrySet instanceof Object2IntMap.FastEntrySet) ? ((Object2IntMap.FastEntrySet)object2IntEntrySet).fastIterator() : object2IntEntrySet.iterator();
    }
    
    public static void fastForEach(final Object2IntMap object2IntMap, final Consumer consumer) {
        final ObjectSet object2IntEntrySet = object2IntMap.object2IntEntrySet();
        if (object2IntEntrySet instanceof Object2IntMap.FastEntrySet) {
            ((Object2IntMap.FastEntrySet)object2IntEntrySet).fastForEach(consumer);
        }
        else {
            object2IntEntrySet.forEach(consumer);
        }
    }
    
    public static ObjectIterable fastIterable(final Object2IntMap object2IntMap) {
        final ObjectSet object2IntEntrySet = object2IntMap.object2IntEntrySet();
        return (object2IntEntrySet instanceof Object2IntMap.FastEntrySet) ? new ObjectIterable() {
            final ObjectSet val$entries;
            
            @Override
            public ObjectIterator iterator() {
                return ((Object2IntMap.FastEntrySet)this.val$entries).fastIterator();
            }
            
            @Override
            public ObjectSpliterator spliterator() {
                return this.val$entries.spliterator();
            }
            
            @Override
            public void forEach(final Consumer consumer) {
                ((Object2IntMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }
            
            @Override
            public Spliterator spliterator() {
                return this.spliterator();
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : object2IntEntrySet;
    }
    
    public static Object2IntMap emptyMap() {
        return Object2IntMaps.EMPTY_MAP;
    }
    
    public static Object2IntMap singleton(final Object o, final int n) {
        return new Singleton(o, n);
    }
    
    public static Object2IntMap singleton(final Object o, final Integer n) {
        return new Singleton(o, n);
    }
    
    public static Object2IntMap synchronize(final Object2IntMap object2IntMap) {
        return (Object2IntMap)new Object2IntMaps.SynchronizedMap(object2IntMap);
    }
    
    public static Object2IntMap synchronize(final Object2IntMap object2IntMap, final Object o) {
        return (Object2IntMap)new Object2IntMaps.SynchronizedMap(object2IntMap, o);
    }
    
    public static Object2IntMap unmodifiable(final Object2IntMap object2IntMap) {
        return (Object2IntMap)new Object2IntMaps.UnmodifiableMap(object2IntMap);
    }
    
    static {
        EMPTY_MAP = new EmptyMap();
    }
    
    public static class EmptyMap extends Object2IntFunctions.EmptyFunction implements Object2IntMap, Serializable, Cloneable
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
        public int getOrDefault(final Object o, final int n) {
            return n;
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
        public ObjectSet object2IntEntrySet() {
            return ObjectSets.EMPTY_SET;
        }
        
        @Override
        public ObjectSet keySet() {
            return ObjectSets.EMPTY_SET;
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
            return Object2IntMaps.EMPTY_MAP;
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
    
    public static class Singleton extends Object2IntFunctions.Singleton implements Object2IntMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet entries;
        protected transient ObjectSet keys;
        protected transient IntCollection values;
        
        protected Singleton(final Object o, final int n) {
            super(o, n);
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
        public ObjectSet object2IntEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractObject2IntMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }
        
        @Deprecated
        @Override
        public ObjectSet entrySet() {
            return this.object2IntEntrySet();
        }
        
        @Override
        public ObjectSet keySet() {
            if (this.keys == null) {
                this.keys = ObjectSets.singleton(this.key);
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
            return ((this.key == null) ? 0 : this.key.hashCode()) ^ this.value;
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
