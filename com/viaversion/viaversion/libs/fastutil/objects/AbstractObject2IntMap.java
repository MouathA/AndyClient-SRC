package com.viaversion.viaversion.libs.fastutil.objects;

import java.io.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.*;

public abstract class AbstractObject2IntMap extends AbstractObject2IntFunction implements Object2IntMap, Serializable
{
    private static final long serialVersionUID = -4940583368468432370L;
    
    protected AbstractObject2IntMap() {
    }
    
    @Override
    public boolean containsKey(final Object o) {
        final ObjectIterator iterator = this.object2IntEntrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getKey() == o) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsValue(final int n) {
        final ObjectIterator iterator = this.object2IntEntrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getIntValue() == n) {
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
    public final int mergeInt(final Object o, final int n, final IntBinaryOperator intBinaryOperator) {
        return this.mergeInt(o, n, (java.util.function.IntBinaryOperator)intBinaryOperator);
    }
    
    @Override
    public ObjectSet keySet() {
        return new AbstractObjectSet() {
            final AbstractObject2IntMap this$0;
            
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
                    private final ObjectIterator i = Object2IntMaps.fastIterator(this.this$1.this$0);
                    final AbstractObject2IntMap$1 this$1;
                    
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
                        this.i.forEachRemaining(AbstractObject2IntMap$1$1::lambda$forEachRemaining$0);
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
    public IntCollection values() {
        return new AbstractIntCollection() {
            final AbstractObject2IntMap this$0;
            
            @Override
            public boolean contains(final int n) {
                return this.this$0.containsValue(n);
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
                    private final ObjectIterator i = Object2IntMaps.fastIterator(this.this$1.this$0);
                    final AbstractObject2IntMap$2 this$1;
                    
                    @Override
                    public int nextInt() {
                        return this.i.next().getIntValue();
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
                        this.i.forEachRemaining(AbstractObject2IntMap$2$1::lambda$forEachRemaining$0);
                    }
                    
                    @Override
                    public void forEachRemaining(final Object o) {
                        this.forEachRemaining((IntConsumer)o);
                    }
                    
                    private static void lambda$forEachRemaining$0(final IntConsumer intConsumer, final Entry entry) {
                        intConsumer.accept(entry.getIntValue());
                    }
                };
            }
            
            @Override
            public IntSpliterator spliterator() {
                return IntSpliterators.asSpliterator(this.iterator(), Size64.sizeOf(this.this$0), 320);
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
        if (map instanceof Object2IntMap) {
            final ObjectIterator fastIterator = Object2IntMaps.fastIterator((Object2IntMap)map);
            while (fastIterator.hasNext()) {
                final Entry entry = fastIterator.next();
                this.put(entry.getKey(), entry.getIntValue());
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
        final ObjectIterator fastIterator = Object2IntMaps.fastIterator(this);
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
        return map.size() == this.size() && this.object2IntEntrySet().containsAll(map.entrySet());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final ObjectIterator fastIterator = Object2IntMaps.fastIterator(this);
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
            sb.append(String.valueOf(entry.getIntValue()));
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
        protected final Object2IntMap map;
        
        public BasicEntrySet(final Object2IntMap map) {
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
                return this.map.containsKey(key) && this.map.getInt(key) == entry.getIntValue();
            }
            final Map.Entry entry2 = (Map.Entry)o;
            final Object key2 = entry2.getKey();
            final V value = entry2.getValue();
            return value != null && value instanceof Integer && this.map.containsKey(key2) && this.map.getInt(key2) == (int)value;
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Entry) {
                final Entry entry = (Entry)o;
                return this.map.remove(entry.getKey(), entry.getIntValue());
            }
            final Map.Entry entry2 = (Map.Entry)o;
            final Object key = entry2.getKey();
            final V value = entry2.getValue();
            return value != null && value instanceof Integer && this.map.remove(key, (int)value);
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
        protected int value;
        
        public BasicEntry() {
        }
        
        public BasicEntry(final Object key, final Integer n) {
            this.key = key;
            this.value = n;
        }
        
        public BasicEntry(final Object key, final int value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public Object getKey() {
            return this.key;
        }
        
        @Override
        public int getIntValue() {
            return this.value;
        }
        
        @Override
        public int setValue(final int n) {
            throw new UnsupportedOperationException();
        }
        
        @Override
        public boolean equals(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Entry) {
                final Entry entry = (Entry)o;
                return Objects.equals(this.key, entry.getKey()) && this.value == entry.getIntValue();
            }
            final Map.Entry entry2 = (Map.Entry)o;
            final Object key = entry2.getKey();
            final V value = entry2.getValue();
            return value != null && value instanceof Integer && Objects.equals(this.key, key) && this.value == (int)value;
        }
        
        @Override
        public int hashCode() {
            return ((this.key == null) ? 0 : this.key.hashCode()) ^ this.value;
        }
        
        @Override
        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}
