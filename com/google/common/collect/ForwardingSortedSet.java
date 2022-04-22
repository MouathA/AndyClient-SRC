package com.google.common.collect;

import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingSortedSet extends ForwardingSet implements SortedSet
{
    protected ForwardingSortedSet() {
    }
    
    @Override
    protected abstract SortedSet delegate();
    
    @Override
    public Comparator comparator() {
        return this.delegate().comparator();
    }
    
    @Override
    public Object first() {
        return this.delegate().first();
    }
    
    @Override
    public SortedSet headSet(final Object o) {
        return this.delegate().headSet(o);
    }
    
    @Override
    public Object last() {
        return this.delegate().last();
    }
    
    @Override
    public SortedSet subSet(final Object o, final Object o2) {
        return this.delegate().subSet(o, o2);
    }
    
    @Override
    public SortedSet tailSet(final Object o) {
        return this.delegate().tailSet(o);
    }
    
    private int unsafeCompare(final Object o, final Object o2) {
        final Comparator comparator = this.comparator();
        return (comparator == null) ? ((Comparable)o).compareTo(o2) : comparator.compare(o, o2);
    }
    
    @Beta
    @Override
    protected boolean standardContains(@Nullable final Object o) {
        return this.unsafeCompare(this.tailSet(o).first(), o) == 0;
    }
    
    @Beta
    @Override
    protected boolean standardRemove(@Nullable final Object o) {
        final Iterator<Object> iterator = this.tailSet(o).iterator();
        if (iterator.hasNext() && this.unsafeCompare(iterator.next(), o) == 0) {
            iterator.remove();
            return true;
        }
        return false;
    }
    
    @Beta
    protected SortedSet standardSubSet(final Object o, final Object o2) {
        return this.tailSet(o).headSet(o2);
    }
    
    @Override
    protected Set delegate() {
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
}
