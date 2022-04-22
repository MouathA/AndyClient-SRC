package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingSortedSetMultimap extends ForwardingSetMultimap implements SortedSetMultimap
{
    protected ForwardingSortedSetMultimap() {
    }
    
    @Override
    protected abstract SortedSetMultimap delegate();
    
    @Override
    public SortedSet get(@Nullable final Object o) {
        return this.delegate().get(o);
    }
    
    @Override
    public SortedSet removeAll(@Nullable final Object o) {
        return this.delegate().removeAll(o);
    }
    
    @Override
    public SortedSet replaceValues(final Object o, final Iterable iterable) {
        return this.delegate().replaceValues(o, iterable);
    }
    
    @Override
    public Comparator valueComparator() {
        return this.delegate().valueComparator();
    }
    
    @Override
    public Set replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    public Set removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    @Override
    public Set get(final Object o) {
        return this.get(o);
    }
    
    @Override
    protected SetMultimap delegate() {
        return this.delegate();
    }
    
    @Override
    public Collection get(final Object o) {
        return this.get(o);
    }
    
    @Override
    public Collection removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    @Override
    public Collection replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    protected Multimap delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
}
