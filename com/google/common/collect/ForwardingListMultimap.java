package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingListMultimap extends ForwardingMultimap implements ListMultimap
{
    protected ForwardingListMultimap() {
    }
    
    @Override
    protected abstract ListMultimap delegate();
    
    @Override
    public List get(@Nullable final Object o) {
        return this.delegate().get(o);
    }
    
    @Override
    public List removeAll(@Nullable final Object o) {
        return this.delegate().removeAll(o);
    }
    
    @Override
    public List replaceValues(final Object o, final Iterable iterable) {
        return this.delegate().replaceValues(o, iterable);
    }
    
    @Override
    public Collection replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    public Collection removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    @Override
    public Collection get(final Object o) {
        return this.get(o);
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
