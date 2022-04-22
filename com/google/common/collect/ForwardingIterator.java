package com.google.common.collect;

import java.util.*;
import com.google.common.annotations.*;

@GwtCompatible
public abstract class ForwardingIterator extends ForwardingObject implements Iterator
{
    protected ForwardingIterator() {
    }
    
    @Override
    protected abstract Iterator delegate();
    
    @Override
    public boolean hasNext() {
        return this.delegate().hasNext();
    }
    
    @Override
    public Object next() {
        return this.delegate().next();
    }
    
    @Override
    public void remove() {
        this.delegate().remove();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
}
