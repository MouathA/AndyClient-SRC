package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(emulated = true)
final class UnmodifiableSortedMultiset extends Multisets.UnmodifiableMultiset implements SortedMultiset
{
    private transient UnmodifiableSortedMultiset descendingMultiset;
    private static final long serialVersionUID = 0L;
    
    UnmodifiableSortedMultiset(final SortedMultiset sortedMultiset) {
        super(sortedMultiset);
    }
    
    @Override
    protected SortedMultiset delegate() {
        return (SortedMultiset)super.delegate();
    }
    
    @Override
    public Comparator comparator() {
        return this.delegate().comparator();
    }
    
    @Override
    NavigableSet createElementSet() {
        return Sets.unmodifiableNavigableSet(this.delegate().elementSet());
    }
    
    @Override
    public NavigableSet elementSet() {
        return (NavigableSet)super.elementSet();
    }
    
    @Override
    public SortedMultiset descendingMultiset() {
        final UnmodifiableSortedMultiset descendingMultiset = this.descendingMultiset;
        if (descendingMultiset == null) {
            final UnmodifiableSortedMultiset descendingMultiset2 = new UnmodifiableSortedMultiset(this.delegate().descendingMultiset());
            descendingMultiset2.descendingMultiset = this;
            return this.descendingMultiset = descendingMultiset2;
        }
        return descendingMultiset;
    }
    
    @Override
    public Multiset.Entry firstEntry() {
        return this.delegate().firstEntry();
    }
    
    @Override
    public Multiset.Entry lastEntry() {
        return this.delegate().lastEntry();
    }
    
    @Override
    public Multiset.Entry pollFirstEntry() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Multiset.Entry pollLastEntry() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public SortedMultiset headMultiset(final Object o, final BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(this.delegate().headMultiset(o, boundType));
    }
    
    @Override
    public SortedMultiset subMultiset(final Object o, final BoundType boundType, final Object o2, final BoundType boundType2) {
        return Multisets.unmodifiableSortedMultiset(this.delegate().subMultiset(o, boundType, o2, boundType2));
    }
    
    @Override
    public SortedMultiset tailMultiset(final Object o, final BoundType boundType) {
        return Multisets.unmodifiableSortedMultiset(this.delegate().tailMultiset(o, boundType));
    }
    
    @Override
    public Set elementSet() {
        return this.elementSet();
    }
    
    @Override
    Set createElementSet() {
        return this.createElementSet();
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
}
