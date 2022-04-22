package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(emulated = true)
abstract class DescendingMultiset extends ForwardingMultiset implements SortedMultiset
{
    private transient Comparator comparator;
    private transient NavigableSet elementSet;
    private transient Set entrySet;
    
    abstract SortedMultiset forwardMultiset();
    
    @Override
    public Comparator comparator() {
        final Comparator comparator = this.comparator;
        if (comparator == null) {
            return this.comparator = Ordering.from(this.forwardMultiset().comparator()).reverse();
        }
        return comparator;
    }
    
    @Override
    public NavigableSet elementSet() {
        final NavigableSet elementSet = this.elementSet;
        if (elementSet == null) {
            return this.elementSet = new SortedMultisets.NavigableElementSet(this);
        }
        return elementSet;
    }
    
    @Override
    public Multiset.Entry pollFirstEntry() {
        return this.forwardMultiset().pollLastEntry();
    }
    
    @Override
    public Multiset.Entry pollLastEntry() {
        return this.forwardMultiset().pollFirstEntry();
    }
    
    @Override
    public SortedMultiset headMultiset(final Object o, final BoundType boundType) {
        return this.forwardMultiset().tailMultiset(o, boundType).descendingMultiset();
    }
    
    @Override
    public SortedMultiset subMultiset(final Object o, final BoundType boundType, final Object o2, final BoundType boundType2) {
        return this.forwardMultiset().subMultiset(o2, boundType2, o, boundType).descendingMultiset();
    }
    
    @Override
    public SortedMultiset tailMultiset(final Object o, final BoundType boundType) {
        return this.forwardMultiset().headMultiset(o, boundType).descendingMultiset();
    }
    
    @Override
    protected Multiset delegate() {
        return this.forwardMultiset();
    }
    
    @Override
    public SortedMultiset descendingMultiset() {
        return this.forwardMultiset();
    }
    
    @Override
    public Multiset.Entry firstEntry() {
        return this.forwardMultiset().lastEntry();
    }
    
    @Override
    public Multiset.Entry lastEntry() {
        return this.forwardMultiset().firstEntry();
    }
    
    abstract Iterator entryIterator();
    
    @Override
    public Set entrySet() {
        final Set entrySet = this.entrySet;
        return (entrySet == null) ? (this.entrySet = this.createEntrySet()) : entrySet;
    }
    
    Set createEntrySet() {
        return new Multisets.EntrySet() {
            final DescendingMultiset this$0;
            
            @Override
            Multiset multiset() {
                return this.this$0;
            }
            
            @Override
            public Iterator iterator() {
                return this.this$0.entryIterator();
            }
            
            @Override
            public int size() {
                return this.this$0.forwardMultiset().entrySet().size();
            }
        };
    }
    
    @Override
    public Iterator iterator() {
        return Multisets.iteratorImpl(this);
    }
    
    @Override
    public Object[] toArray() {
        return this.standardToArray();
    }
    
    @Override
    public Object[] toArray(final Object[] array) {
        return this.standardToArray(array);
    }
    
    @Override
    public String toString() {
        return this.entrySet().toString();
    }
    
    @Override
    public Set elementSet() {
        return this.elementSet();
    }
    
    @Override
    protected Collection delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    @Override
    public SortedSet elementSet() {
        return this.elementSet();
    }
}
