package com.google.common.collect;

import java.util.concurrent.*;
import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible
public abstract class ForwardingConcurrentMap extends ForwardingMap implements ConcurrentMap
{
    protected ForwardingConcurrentMap() {
    }
    
    @Override
    protected abstract ConcurrentMap delegate();
    
    @Override
    public Object putIfAbsent(final Object o, final Object o2) {
        return this.delegate().putIfAbsent(o, o2);
    }
    
    @Override
    public boolean remove(final Object o, final Object o2) {
        return this.delegate().remove(o, o2);
    }
    
    @Override
    public Object replace(final Object o, final Object o2) {
        return this.delegate().replace(o, o2);
    }
    
    @Override
    public boolean replace(final Object o, final Object o2, final Object o3) {
        return this.delegate().replace(o, o2, o3);
    }
    
    @Override
    protected Map delegate() {
        return this.delegate();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
}
