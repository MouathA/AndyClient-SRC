package com.viaversion.viaversion.libs.fastutil.objects;

import java.util.*;

public abstract class AbstractObject2ObjectSortedMap extends AbstractObject2ObjectMap implements Object2ObjectSortedMap
{
    private static final long serialVersionUID = -1773560792952436569L;
    
    protected AbstractObject2ObjectSortedMap() {
    }
    
    @Override
    public ObjectSortedSet keySet() {
        return new KeySet();
    }
    
    @Override
    public ObjectCollection values() {
        return new ValuesCollection();
    }
    
    @Override
    public ObjectSet keySet() {
        return this.keySet();
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    protected class KeySet extends AbstractObjectSortedSet
    {
        final AbstractObject2ObjectSortedMap this$0;
        
        protected KeySet(final AbstractObject2ObjectSortedMap this$0) {
            this.this$0 = this$0;
        }
        
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
        public Comparator comparator() {
            return this.this$0.comparator();
        }
        
        @Override
        public Object first() {
            return this.this$0.firstKey();
        }
        
        @Override
        public Object last() {
            return this.this$0.lastKey();
        }
        
        @Override
        public ObjectSortedSet headSet(final Object o) {
            return this.this$0.headMap(o).keySet();
        }
        
        @Override
        public ObjectSortedSet tailSet(final Object o) {
            return this.this$0.tailMap(o).keySet();
        }
        
        @Override
        public ObjectSortedSet subSet(final Object o, final Object o2) {
            return this.this$0.subMap(o, o2).keySet();
        }
        
        @Override
        public ObjectBidirectionalIterator iterator(final Object o) {
            return new KeySetIterator(this.this$0.object2ObjectEntrySet().iterator(new BasicEntry(o, null)));
        }
        
        @Override
        public ObjectBidirectionalIterator iterator() {
            return new KeySetIterator(Object2ObjectSortedMaps.fastIterator(this.this$0));
        }
        
        @Override
        public ObjectIterator iterator() {
            return this.iterator();
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        @Override
        public SortedSet tailSet(final Object o) {
            return this.tailSet(o);
        }
        
        @Override
        public SortedSet headSet(final Object o) {
            return this.headSet(o);
        }
        
        @Override
        public SortedSet subSet(final Object o, final Object o2) {
            return this.subSet(o, o2);
        }
    }
    
    protected static class KeySetIterator implements ObjectBidirectionalIterator
    {
        protected final ObjectBidirectionalIterator i;
        
        public KeySetIterator(final ObjectBidirectionalIterator i) {
            this.i = i;
        }
        
        @Override
        public Object next() {
            return this.i.next().getKey();
        }
        
        @Override
        public Object previous() {
            return ((Object2ObjectMap.Entry)this.i.previous()).getKey();
        }
        
        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
        
        @Override
        public boolean hasPrevious() {
            return this.i.hasPrevious();
        }
    }
    
    protected class ValuesCollection extends AbstractObjectCollection
    {
        final AbstractObject2ObjectSortedMap this$0;
        
        protected ValuesCollection(final AbstractObject2ObjectSortedMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public ObjectIterator iterator() {
            return new ValuesIterator(Object2ObjectSortedMaps.fastIterator(this.this$0));
        }
        
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
        public Iterator iterator() {
            return this.iterator();
        }
    }
    
    protected static class ValuesIterator implements ObjectIterator
    {
        protected final ObjectBidirectionalIterator i;
        
        public ValuesIterator(final ObjectBidirectionalIterator i) {
            this.i = i;
        }
        
        @Override
        public Object next() {
            return this.i.next().getValue();
        }
        
        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }
}
