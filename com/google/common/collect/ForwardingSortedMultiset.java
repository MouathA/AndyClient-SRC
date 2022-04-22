package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@Beta
@GwtCompatible(emulated = true)
public abstract class ForwardingSortedMultiset extends ForwardingMultiset implements SortedMultiset
{
    protected ForwardingSortedMultiset() {
    }
    
    @Override
    protected abstract SortedMultiset delegate();
    
    @Override
    public NavigableSet elementSet() {
        return (NavigableSet)super.elementSet();
    }
    
    @Override
    public Comparator comparator() {
        return this.delegate().comparator();
    }
    
    @Override
    public SortedMultiset descendingMultiset() {
        return this.delegate().descendingMultiset();
    }
    
    @Override
    public Multiset.Entry firstEntry() {
        return this.delegate().firstEntry();
    }
    
    protected Multiset.Entry standardFirstEntry() {
        final Iterator<Multiset.Entry> iterator = (Iterator<Multiset.Entry>)this.entrySet().iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        final Multiset.Entry entry = iterator.next();
        return Multisets.immutableEntry(entry.getElement(), entry.getCount());
    }
    
    @Override
    public Multiset.Entry lastEntry() {
        return this.delegate().lastEntry();
    }
    
    protected Multiset.Entry standardLastEntry() {
        final Iterator<Multiset.Entry> iterator = (Iterator<Multiset.Entry>)this.descendingMultiset().entrySet().iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        final Multiset.Entry entry = iterator.next();
        return Multisets.immutableEntry(entry.getElement(), entry.getCount());
    }
    
    @Override
    public Multiset.Entry pollFirstEntry() {
        return this.delegate().pollFirstEntry();
    }
    
    protected Multiset.Entry standardPollFirstEntry() {
        final Iterator iterator = this.entrySet().iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        final Multiset.Entry entry = iterator.next();
        final Multiset.Entry immutableEntry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        iterator.remove();
        return immutableEntry;
    }
    
    @Override
    public Multiset.Entry pollLastEntry() {
        return this.delegate().pollLastEntry();
    }
    
    protected Multiset.Entry standardPollLastEntry() {
        final Iterator iterator = this.descendingMultiset().entrySet().iterator();
        if (!iterator.hasNext()) {
            return null;
        }
        final Multiset.Entry entry = iterator.next();
        final Multiset.Entry immutableEntry = Multisets.immutableEntry(entry.getElement(), entry.getCount());
        iterator.remove();
        return immutableEntry;
    }
    
    @Override
    public SortedMultiset headMultiset(final Object o, final BoundType boundType) {
        return this.delegate().headMultiset(o, boundType);
    }
    
    @Override
    public SortedMultiset subMultiset(final Object o, final BoundType boundType, final Object o2, final BoundType boundType2) {
        return this.delegate().subMultiset(o, boundType, o2, boundType2);
    }
    
    protected SortedMultiset standardSubMultiset(final Object o, final BoundType boundType, final Object o2, final BoundType boundType2) {
        return this.tailMultiset(o, boundType).headMultiset(o2, boundType2);
    }
    
    @Override
    public SortedMultiset tailMultiset(final Object o, final BoundType boundType) {
        return this.delegate().tailMultiset(o, boundType);
    }
    
    @Override
    public Set elementSet() {
        return this.elementSet();
    }
    
    @Override
    protected Multiset delegate() {
        return this.delegate();
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
    
    protected abstract class StandardDescendingMultiset extends DescendingMultiset
    {
        final ForwardingSortedMultiset this$0;
        
        public StandardDescendingMultiset(final ForwardingSortedMultiset this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        SortedMultiset forwardMultiset() {
            return this.this$0;
        }
    }
    
    protected class StandardElementSet extends SortedMultisets.NavigableElementSet
    {
        final ForwardingSortedMultiset this$0;
        
        public StandardElementSet(final ForwardingSortedMultiset this$0) {
            super(this.this$0 = this$0);
        }
    }
}
