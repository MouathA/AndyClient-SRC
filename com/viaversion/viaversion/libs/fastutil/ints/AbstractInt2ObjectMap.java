package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import java.util.*;

public abstract class AbstractInt2ObjectMap extends AbstractInt2ObjectFunction implements Int2ObjectMap, Serializable
{
    private static final long serialVersionUID = -4940583368468432370L;
    
    protected AbstractInt2ObjectMap() {
    }
    
    @Override
    public boolean containsKey(final int n) {
        final ObjectIterator iterator = this.int2ObjectEntrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getIntKey() == n) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsValue(final Object o) {
        final ObjectIterator iterator = this.int2ObjectEntrySet().iterator();
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
    public IntSet keySet() {
        return new AbstractIntSet() {
            final AbstractInt2ObjectMap this$0;
            
            @Override
            public boolean contains(final int n) {
                return this.this$0.containsKey(n);
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
            public IntIterator iterator() {
                return new IntIterator() {
                    private final ObjectIterator i = Int2ObjectMaps.fastIterator(this.this$1.this$0);
                    final AbstractInt2ObjectMap$1 this$1;
                    
                    @Override
                    public int nextInt() {
                        return this.i.next().getIntKey();
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
                    public void forEachRemaining(final IntConsumer intConsumer) {
                        this.i.forEachRemaining(AbstractInt2ObjectMap$1$1::lambda$forEachRemaining$0);
                    }
                    
                    @Override
                    public void forEachRemaining(final Object o) {
                        this.forEachRemaining((IntConsumer)o);
                    }
                    
                    private static void lambda$forEachRemaining$0(final IntConsumer intConsumer, final Entry entry) {
                        intConsumer.accept(entry.getIntKey());
                    }
                };
            }
            
            @Override
            public IntSpliterator spliterator() {
                return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 321);
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
            final AbstractInt2ObjectMap this$0;
            
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
                    private final ObjectIterator i = Int2ObjectMaps.fastIterator(this.this$1.this$0);
                    final AbstractInt2ObjectMap$2 this$1;
                    
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
                        this.i.forEachRemaining(AbstractInt2ObjectMap$2$1::lambda$forEachRemaining$0);
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
        if (map instanceof Int2ObjectMap) {
            final ObjectIterator fastIterator = Int2ObjectMaps.fastIterator((Int2ObjectMap)map);
            while (fastIterator.hasNext()) {
                final Entry entry = fastIterator.next();
                this.put(entry.getIntKey(), entry.getValue());
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
        final ObjectIterator fastIterator = Int2ObjectMaps.fastIterator(this);
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
        return map.size() == this.size() && this.int2ObjectEntrySet().containsAll(map.entrySet());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final ObjectIterator fastIterator = Int2ObjectMaps.fastIterator(this);
        int size = this.size();
        sb.append("{");
        while (size-- != 0) {
            if (!false) {
                sb.append(", ");
            }
            final Entry entry = fastIterator.next();
            sb.append(String.valueOf(entry.getIntKey()));
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
        protected final Int2ObjectMap map;
        
        public BasicEntrySet(final Int2ObjectMap map) {
            this.map = map;
        }
        
        @Override
        public boolean contains(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Entry) {
                final Entry entry = (Entry)o;
                final int intKey = entry.getIntKey();
                return this.map.containsKey(intKey) && Objects.equals(this.map.get(intKey), entry.getValue());
            }
            final Map.Entry entry2 = (Map.Entry)o;
            final Integer key = entry2.getKey();
            if (key == null || !(key instanceof Integer)) {
                return false;
            }
            final int intValue = key;
            final V value = entry2.getValue();
            return this.map.containsKey(intValue) && Objects.equals(this.map.get(intValue), value);
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Entry) {
                final Entry entry = (Entry)o;
                return this.map.remove(entry.getIntKey(), entry.getValue());
            }
            final Map.Entry entry2 = (Map.Entry)o;
            final Integer key = entry2.getKey();
            return key != null && key instanceof Integer && this.map.remove((int)key, entry2.getValue());
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
        protected int key;
        protected Object value;
        
        public BasicEntry() {
        }
        
        public BasicEntry(final Integer n, final Object value) {
            this.key = n;
            this.value = value;
        }
        
        public BasicEntry(final int key, final Object value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public int getIntKey() {
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
                return this.key == entry.getIntKey() && Objects.equals(this.value, entry.getValue());
            }
            final Map.Entry entry2 = (Map.Entry)o;
            final Integer key = entry2.getKey();
            if (key == null || !(key instanceof Integer)) {
                return false;
            }
            final V value = entry2.getValue();
            return this.key == key && Objects.equals(this.value, value);
        }
        
        @Override
        public int hashCode() {
            return this.key ^ ((this.value == null) ? 0 : this.value.hashCode());
        }
        
        @Override
        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}
