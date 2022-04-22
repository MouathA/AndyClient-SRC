package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.base.*;

@GwtCompatible
public abstract class ForwardingSet extends ForwardingCollection implements Set
{
    protected ForwardingSet() {
    }
    
    @Override
    protected abstract Set delegate();
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return o == this || this.delegate().equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    @Override
    protected boolean standardRemoveAll(final Collection collection) {
        return Sets.removeAllImpl(this, (Collection)Preconditions.checkNotNull(collection));
    }
    
    protected boolean standardEquals(@Nullable final Object o) {
        return Sets.equalsImpl(this, o);
    }
    
    protected int standardHashCode() {
        return Sets.hashCodeImpl(this);
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
