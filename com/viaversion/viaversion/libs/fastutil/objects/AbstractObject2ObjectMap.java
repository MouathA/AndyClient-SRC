package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;

public abstract class AbstractObject2ObjectMap extends AbstractObject2ObjectFunction implements Object2ObjectMap, Serializable
{
    private static final long serialVersionUID = -4940583368468432370L;
    
    protected AbstractObject2ObjectMap() {
    }
    
    @Override
    public boolean containsKey(final Object o) {
        final ObjectIterator iterator = this.object2ObjectEntrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getKey() == o) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsValue(final Object o) {
        final ObjectIterator iterator = this.object2ObjectEntrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getValue() == o) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    @Override
    public ObjectSet keySet() {
        return new AbstractObjectSet() {
            final AbstractObject2ObjectMap this$0;
            
            @Override
            public boolean contains(final Object o) {
                return this.this$0.containsKey(o);
            }
            
            @Override
            public int size() {
                return this.this$0.size();
            }
            
            @Override
            public void clear() {
                this.this$0.clear();
            }
            
            @Override
            public ObjectIterator iterator() {
                return new ObjectIterator() {
                    private final ObjectIterator i = Object2ObjectMaps.fastIterator(this.this$1.this$0);
                    final AbstractObject2ObjectMap$1 this$1;
                    
                    @Override
                    public Object next() {
                        return this.i.next().getKey();
                    }
                    
                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }
                    
                    @Override
                    public void remove() {
                        this.i.remove();
                    }
                    
                    @Override
                    public void forEachRemaining(final Consumer consumer) {
                        this.i.forEachRemaining(AbstractObject2ObjectMap$1$1::lambda$forEachRemaining$0);
                    }
                    
                    private static void lambda$forEachRemaining$0(final Consumer consumer, final Entry entry) {
                        consumer.accept(entry.getKey());
                    }
                };
            }
            
            @Override
            public ObjectSpliterator spliterator() {
                return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 65);
            }
            
            @Override
            public Spliterator spliterator() {
                return this.spliterator();
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }
    
    @Override
    public ObjectCollection values() {
        return new AbstractObjectCollection() {
            final AbstractObject2ObjectMap this$0;
            
            @Override
            public boolean contains(final Object o) {
                return this.this$0.containsValue(o);
            }
            
            @Override
            public int size() {
                return this.this$0.size();
            }
            
            @Override
            public void clear() {
                this.this$0.clear();
            }
            
            @Override
            public ObjectIterator iterator() {
                return new ObjectIterator() {
                    private final ObjectIterator i = Object2ObjectMaps.fastIterator(this.this$1.this$0);
                    final AbstractObject2ObjectMap$2 this$1;
                    
                    @Override
                    public Object next() {
                        return this.i.next().getValue();
                    }
                    
                    @Override
                    public boolean hasNext() {
                        return this.i.hasNext();
                    }
                    
                    @Override
                    public void remove() {
                        this.i.remove();
                    }
                    
                    @Override
                    public void forEachRemaining(final Consumer consumer) {
                        this.i.forEachRemaining(AbstractObject2ObjectMap$2$1::lambda$forEachRemaining$0);
                    }
                    
                    private static void lambda$forEachRemaining$0(final Consumer consumer, final Entry entry) {
                        consumer.accept(entry.getValue());
                    }
                };
            }
            
            @Override
            public ObjectSpliterator spliterator() {
                return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 64);
            }
            
            @Override
            public Spliterator spliterator() {
                return this.spliterator();
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
    }
    
    @Override
    public void putAll(final Map map) {
        if (map instanceof Object2ObjectMap) {
            final ObjectIterator fastIterator = Object2ObjectMaps.fastIterator((Object2ObjectMap)map);
            while (fastIterator.hasNext()) {
                final Entry entry = fastIterator.next();
                this.put(entry.getKey(), entry.getValue());
            }
        }
        else {
            int size = map.size();
            final Iterator<Map.Entry<Object, Object>> iterator = map.entrySet().iterator();
            while (size-- != 0) {
                final Map.Entry<Object, Object> entry2 = iterator.next();
                this.put(entry2.getKey(), entry2.getValue());
            }
        }
    }
    
    @Override
    public int hashCode() {
        int size = this.size();
        final ObjectIterator fastIterator = Object2ObjectMaps.fastIterator(this);
        while (size-- != 0) {
            final int n = 0 + fastIterator.next().hashCode();
        }
        return 0;
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
        return map.size() == this.size() && this.object2ObjectEntrySet().containsAll(map.entrySet());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final ObjectIterator fastIterator = Object2ObjectMaps.fastIterator(this);
        int size = this.size();
        sb.append("{");
        while (size-- != 0) {
            if (!false) {
                sb.append(", ");
            }
            final Entry entry = fastIterator.next();
            if (this == entry.getKey()) {
                sb.append("(this map)");
            }
            else {
                sb.append(String.valueOf(entry.getKey()));
            }
            sb.append("=>");
            if (this == entry.getValue()) {
                sb.append("(this map)");
            }
            else {
                sb.append(String.valueOf(entry.getValue()));
            }
        }
        sb.append("}");
        return sb.toString();
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    public abstract static class BasicEntrySet extends AbstractObjectSet
    {
        protected final Object2ObjectMap map;
        
        public BasicEntrySet(final Object2ObjectMap map) {
            this.map = map;
        }
        
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Entry) {
                final Entry entry = (Entry)o;
                final Object key = entry.getKey();
                return this.map.containsKey(key) && Objects.equals(this.map.get(key), entry.getValue());
            }
            final Map.Entry entry2 = (Map.Entry)o;
            final Object key2 = entry2.getKey();
            final V value = entry2.getValue();
            return this.map.containsKey(key2) && Objects.equals(this.map.get(key2), value);
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Entry) {
                final Entry entry = (Entry)o;
                return this.map.remove(entry.getKey(), entry.getValue());
            }
            final Map.Entry entry2 = (Map.Entry)o;
            return this.map.remove(entry2.getKey(), entry2.getValue());
        }
        
        @Override
        public int size() {
            return this.map.size();
        }
        
        @Override
        public ObjectSpliterator spliterator() {
            return ObjectSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.map), 65);
        }
        
        @Override
        public Spliterator spliterator() {
            return this.spliterator();
        }
    }
    
    public static class BasicEntry implements Entry
    {
        protected Object key;
        protected Object value;
        
        public BasicEntry() {
        }
        
        public BasicEntry(final Object key, final Object value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public Object getKey() {
            return this.key;
        }
        
        @Override
        public Object getValue() {
            return this.value;
        }
        
        @Override
        public Object setValue(final Object o) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Entry) {
                final Entry entry = (Entry)o;
                return Objects.equals(this.key, entry.getKey()) && Objects.equals(this.value, entry.getValue());
            }
            final Map.Entry entry2 = (Map.Entry)o;
            final Object key = entry2.getKey();
            final V value = entry2.getValue();
            return Objects.equals(this.key, key) && Objects.equals(this.value, value);
        }
        
        @Override
        public int hashCode() {
            return ((this.key == null) ? 0 : this.key.hashCode()) ^ ((this.value == null) ? 0 : this.value.hashCode());
        }
        
        @Override
        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}
