package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.function.*;
import java.util.*;

public final class Object2ObjectMaps
{
    public static final EmptyMap EMPTY_MAP;
    
    private Object2ObjectMaps() {
    }
    
    public static ObjectIterator fastIterator(final Object2ObjectMap object2ObjectMap) {
        final ObjectSet object2ObjectEntrySet = object2ObjectMap.object2ObjectEntrySet();
        return (object2ObjectEntrySet instanceof Object2ObjectMap.FastEntrySet) ? ((Object2ObjectMap.FastEntrySet)object2ObjectEntrySet).fastIterator() : object2ObjectEntrySet.iterator();
    }
    
    public static void fastForEach(final Object2ObjectMap object2ObjectMap, final Consumer consumer) {
        final ObjectSet object2ObjectEntrySet = object2ObjectMap.object2ObjectEntrySet();
        if (object2ObjectEntrySet instanceof Object2ObjectMap.FastEntrySet) {
            ((Object2ObjectMap.FastEntrySet)object2ObjectEntrySet).fastForEach(consumer);
        }
        else {
            object2ObjectEntrySet.forEach(consumer);
        }
    }
    
    public static ObjectIterable fastIterable(final Object2ObjectMap object2ObjectMap) {
        final ObjectSet object2ObjectEntrySet = object2ObjectMap.object2ObjectEntrySet();
        return (object2ObjectEntrySet instanceof Object2ObjectMap.FastEntrySet) ? new ObjectIterable() {
            final ObjectSet val$entries;
            
            @Override
            public ObjectIterator iterator() {
                return ((Object2ObjectMap.FastEntrySet)this.val$entries).fastIterator();
            }
            
            @Override
            public ObjectSpliterator spliterator() {
                return this.val$entries.spliterator();
            }
            
            @Override
            public void forEach(final Consumer consumer) {
                ((Object2ObjectMap.FastEntrySet)this.val$entries).fastForEach(consumer);
            }
            
            @Override
            public Spliterator spliterator() {
                return this.spliterator();
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        } : object2ObjectEntrySet;
    }
    
    public static Object2ObjectMap emptyMap() {
        return Object2ObjectMaps.EMPTY_MAP;
    }
    
    public static Object2ObjectMap singleton(final Object o, final Object o2) {
        return new Singleton(o, o2);
    }
    
    public static Object2ObjectMap synchronize(final Object2ObjectMap object2ObjectMap) {
        return (Object2ObjectMap)new Object2ObjectMaps.SynchronizedMap(object2ObjectMap);
    }
    
    public static Object2ObjectMap synchronize(final Object2ObjectMap object2ObjectMap, final Object o) {
        return (Object2ObjectMap)new Object2ObjectMaps.SynchronizedMap(object2ObjectMap, o);
    }
    
    public static Object2ObjectMap unmodifiable(final Object2ObjectMap object2ObjectMap) {
        return (Object2ObjectMap)new Object2ObjectMaps.UnmodifiableMap(object2ObjectMap);
    }
    
    static {
        EMPTY_MAP = new EmptyMap();
    }
    
    public static class EmptyMap extends Object2ObjectFunctions.EmptyFunction implements Object2ObjectMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        
        protected EmptyMap() {
        }
        
        @Override
        public boolean containsValue(final Object o) {
            return false;
        }
        
        @Override
        public Object getOrDefault(final Object o, final Object o2) {
            return o2;
        }
        
        @Override
        public void putAll(final Map map) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public ObjectSet object2ObjectEntrySet() {
            return ObjectSets.EMPTY_SET;
        }
        
        @Override
        public ObjectSet keySet() {
            return ObjectSets.EMPTY_SET;
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
            return Object2ObjectMaps.EMPTY_MAP;
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
    
    public static class Singleton extends Object2ObjectFunctions.Singleton implements Object2ObjectMap, Serializable, Cloneable
    {
        private static final long serialVersionUID = -7046029254386353129L;
        protected transient ObjectSet entries;
        protected transient ObjectSet keys;
        protected transient ObjectCollection values;
        
        protected Singleton(final Object o, final Object o2) {
            super(o, o2);
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
        public ObjectSet object2ObjectEntrySet() {
            if (this.entries == null) {
                this.entries = ObjectSets.singleton(new AbstractObject2ObjectMap.BasicEntry(this.key, this.value));
            }
            return this.entries;
        }
        
        @Override
        public ObjectSet entrySet() {
            return this.object2ObjectEntrySet();
        }
        
        @Override
        public ObjectSet keySet() {
            if (this.keys == null) {
                this.keys = ObjectSets.singleton(this.key);
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
            return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value.hashCode());
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
