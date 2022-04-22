package com.viaversion.viaversion.libs.fastutil.ints;

import java.util.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;

public abstract class AbstractInt2IntSortedMap extends AbstractInt2IntMap implements Int2IntSortedMap
{
    private static final long serialVersionUID = -1773560792952436569L;
    
    protected AbstractInt2IntSortedMap() {
    }
    
    @Override
    public IntSortedSet keySet() {
        return new KeySet();
    }
    
    @Override
    public IntCollection values() {
        return new ValuesCollection();
    }
    
    @Override
    public IntSet keySet() {
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
    
    protected class KeySet extends AbstractIntSortedSet
    {
        final AbstractInt2IntSortedMap this$0;
        
        protected KeySet(final AbstractInt2IntSortedMap this$0) {
            this.this$0 = this$0;
        }
        
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
        public IntComparator comparator() {
            return this.this$0.comparator();
        }
        
        @Override
        public int firstInt() {
            return this.this$0.firstIntKey();
        }
        
        @Override
        public int lastInt() {
            return this.this$0.lastIntKey();
        }
        
        @Override
        public IntSortedSet headSet(final int n) {
            return this.this$0.headMap(n).keySet();
        }
        
        @Override
        public IntSortedSet tailSet(final int n) {
            return this.this$0.tailMap(n).keySet();
        }
        
        @Override
        public IntSortedSet subSet(final int n, final int n2) {
            return this.this$0.subMap(n, n2).keySet();
        }
        
        @Override
        public IntBidirectionalIterator iterator(final int n) {
            return new KeySetIterator(this.this$0.int2IntEntrySet().iterator(new BasicEntry(n, 0)));
        }
        
        @Override
        public IntBidirectionalIterator iterator() {
            return new KeySetIterator(Int2IntSortedMaps.fastIterator(this.this$0));
        }
        
        @Override
        public IntIterator iterator() {
            return this.iterator();
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        @Override
        public Comparator comparator() {
            return this.comparator();
        }
    }
    
    protected static class KeySetIterator implements IntBidirectionalIterator
    {
        protected final ObjectBidirectionalIterator i;
        
        public KeySetIterator(final ObjectBidirectionalIterator i) {
            this.i = i;
        }
        
        @Override
        public int nextInt() {
            return this.i.next().getIntKey();
        }
        
        @Override
        public int previousInt() {
            return ((Int2IntMap.Entry)this.i.previous()).getIntKey();
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
    
    protected class ValuesCollection extends AbstractIntCollection
    {
        final AbstractInt2IntSortedMap this$0;
        
        protected ValuesCollection(final AbstractInt2IntSortedMap this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        public IntIterator iterator() {
            return new ValuesIterator(Int2IntSortedMaps.fastIterator(this.this$0));
        }
        
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
        public Iterator iterator() {
            return this.iterator();
        }
    }
    
    protected static class ValuesIterator implements IntIterator
    {
        protected final ObjectBidirectionalIterator i;
        
        public ValuesIterator(final ObjectBidirectionalIterator i) {
            this.i = i;
        }
        
        @Override
        public int nextInt() {
            return this.i.next().getIntValue();
        }
        
        @Override
        public boolean hasNext() {
            return this.i.hasNext();
        }
    }
}