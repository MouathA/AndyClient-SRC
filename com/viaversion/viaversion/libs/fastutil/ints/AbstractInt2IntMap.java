package com.viaversion.viaversion.libs.fastutil.ints;

import java.io.*;
import java.util.function.*;
import com.viaversion.viaversion.libs.fastutil.*;
import java.util.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

public abstract class AbstractInt2IntMap extends AbstractInt2IntFunction implements Int2IntMap, Serializable
{
    private static final long serialVersionUID = -4940583368468432370L;
    
    protected AbstractInt2IntMap() {
    }
    
    @Override
    public boolean containsKey(final int n) {
        final ObjectIterator iterator = this.int2IntEntrySet().iterator();
        while (iterator.hasNext()) {
            if (iterator.next().getIntKey() == n) {
                return true;
            }
        }
        return false;
    }
    
    @Override
    public boolean containsValue(final int n) {
        final ObjectIterator iterator = this.int2IntEntrySet().iterator();
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
    public final int mergeInt(final int n, final int n2, final IntBinaryOperator intBinaryOperator) {
        return this.mergeInt(n, n2, (java.util.function.IntBinaryOperator)intBinaryOperator);
    }
    
    @Override
    public IntSet keySet() {
        return new AbstractIntSet() {
            final AbstractInt2IntMap this$0;
            
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
                    private final ObjectIterator i = Int2IntMaps.fastIterator(this.this$1.this$0);
                    final AbstractInt2IntMap$1 this$1;
                    
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
                        this.i.forEachRemaining(AbstractInt2IntMap$1$1::lambda$forEachRemaining$0);
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
    public IntCollection values() {
        return new AbstractIntCollection() {
            final AbstractInt2IntMap this$0;
            
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
                    private final ObjectIterator i = Int2IntMaps.fastIterator(this.this$1.this$0);
                    final AbstractInt2IntMap$2 this$1;
                    
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
                        this.i.forEachRemaining(AbstractInt2IntMap$2$1::lambda$forEachRemaining$0);
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
        if (map instanceof Int2IntMap) {
            final ObjectIterator fastIterator = Int2IntMaps.fastIterator((Int2IntMap)map);
            while (fastIterator.hasNext()) {
                final Entry entry = fastIterator.next();
                this.put(entry.getIntKey(), entry.getIntValue());
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
        final ObjectIterator fastIterator = Int2IntMaps.fastIterator(this);
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
        return map.size() == this.size() && this.int2IntEntrySet().containsAll(map.entrySet());
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        final ObjectIterator fastIterator = Int2IntMaps.fastIterator(this);
        int size = this.size();
        sb.append("{");
        while (size-- != 0) {
            if (!false) {
                sb.append(", ");
            }
            final Entry entry = fastIterator.next();
            sb.append(String.valueOf(entry.getIntKey()));
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
        protected final Int2IntMap map;
        
        public BasicEntrySet(final Int2IntMap map) {
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
                return this.map.containsKey(intKey) && this.map.get(intKey) == entry.getIntValue();
            }
            final Map.Entry entry2 = (Map.Entry)o;
            final Integer key = entry2.getKey();
            if (key == null || !(key instanceof Integer)) {
                return false;
            }
            final int intValue = key;
            final V value = entry2.getValue();
            return value != null && value instanceof Integer && this.map.containsKey(intValue) && this.map.get(intValue) == (int)value;
        }
        
        @Override
        public boolean remove(final Object o) {
            if (!(o instanceof Map.Entry)) {
                return false;
            }
            if (o instanceof Entry) {
                final Entry entry = (Entry)o;
                return this.map.remove(entry.getIntKey(), entry.getIntValue());
            }
            final Map.Entry entry2 = (Map.Entry)o;
            final Integer key = entry2.getKey();
            if (key == null || !(key instanceof Integer)) {
                return false;
            }
            final int intValue = key;
            final V value = entry2.getValue();
            return value != null && value instanceof Integer && this.map.remove(intValue, (int)value);
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
        protected int value;
        
        public BasicEntry() {
        }
        
        public BasicEntry(final Integer n, final Integer n2) {
            this.key = n;
            this.value = n2;
        }
        
        public BasicEntry(final int key, final int value) {
            this.key = key;
            this.value = value;
        }
        
        @Override
        public int getIntKey() {
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
                return this.key == entry.getIntKey() && this.value == entry.getIntValue();
            }
            final Map.Entry entry2 = (Map.Entry)o;
            final Integer key = entry2.getKey();
            if (key == null || !(key instanceof Integer)) {
                return false;
            }
            final V value = entry2.getValue();
            return value != null && value instanceof Integer && this.key == key && this.value == (int)value;
        }
        
        @Override
        public int hashCode() {
            return this.key ^ this.value;
        }
        
        @Override
        public String toString() {
            return this.key + "->" + this.value;
        }
    }
}
