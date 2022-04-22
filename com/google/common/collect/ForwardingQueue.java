package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingQueue extends ForwardingCollection implements Queue
{
    protected ForwardingQueue() {
    }
    
    @Override
    protected abstract Queue delegate();
    
    @Override
    public boolean offer(final Object o) {
        return this.delegate().offer(o);
    }
    
    @Override
    public Object poll() {
        return this.delegate().poll();
    }
    
    @Override
    public Object remove() {
        return this.delegate().remove();
    }
    
    @Override
    public Object peek() {
        return this.delegate().peek();
    }
    
    @Override
    public Object element() {
        return this.delegate().element();
    }
    
    protected boolean standardOffer(final Object o) {
        return this.add(o);
    }
    
    protected Object standardPeek() {
        return this.element();
    }
    
    protected Object standardPoll() {
        return this.remove();
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
