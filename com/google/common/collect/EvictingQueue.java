package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;

@Beta
@GwtIncompatible("java.util.ArrayDeque")
public final class EvictingQueue extends ForwardingQueue implements Serializable
{
    private final Queue delegate;
    @VisibleForTesting
    final int maxSize;
    private static final long serialVersionUID = 0L;
    
    private EvictingQueue(final int maxSize) {
        Preconditions.checkArgument(maxSize >= 0, "maxSize (%s) must >= 0", maxSize);
        this.delegate = new ArrayDeque(maxSize);
        this.maxSize = maxSize;
    }
    
    public static EvictingQueue create(final int n) {
        return new EvictingQueue(n);
    }
    
    public int remainingCapacity() {
        return this.maxSize - this.size();
    }
    
    @Override
    protected Queue delegate() {
        return this.delegate;
    }
    
    @Override
    public boolean offer(final Object o) {
        return this.add(o);
    }
    
    @Override
    public boolean add(final Object o) {
        Preconditions.checkNotNull(o);
        if (this.maxSize == 0) {
            return true;
        }
        if (this.size() == this.maxSize) {
            this.delegate.remove();
        }
        this.delegate.add(o);
        return true;
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        return this.standardAddAll(collection);
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.delegate().contains(Preconditions.checkNotNull(o));
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.delegate().remove(Preconditions.checkNotNull(o));
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
