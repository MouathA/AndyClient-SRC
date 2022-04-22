package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingListIterator extends ForwardingIterator implements ListIterator
{
    protected ForwardingListIterator() {
    }
    
    @Override
    protected abstract ListIterator delegate();
    
    @Override
    public void add(final Object o) {
        this.delegate().add(o);
    }
    
    @Override
    public boolean hasPrevious() {
        return this.delegate().hasPrevious();
    }
    
    @Override
    public int nextIndex() {
        return this.delegate().nextIndex();
    }
    
    @Override
    public Object previous() {
        return this.delegate().previous();
    }
    
    @Override
    public int previousIndex() {
        return this.delegate().previousIndex();
    }
    
    @Override
    public void set(final Object o) {
        this.delegate().set(o);
    }
    
    @Override
    protected Iterator delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
}
