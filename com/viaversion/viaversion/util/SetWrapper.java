package com.viaversion.viaversion.util;

import com.google.common.collect.*;
import java.util.function.*;
import java.util.*;

public class SetWrapper extends ForwardingSet
{
    private final Set set;
    private final Consumer addListener;
    
    public SetWrapper(final Set set, final Consumer addListener) {
        this.set = set;
        this.addListener = addListener;
    }
    
    @Override
    public boolean add(final Object o) {
        this.addListener.accept(o);
        return super.add(o);
    }
    
    @Override
    public boolean addAll(final Collection collection) {
        final Iterator<Object> iterator = collection.iterator();
        while (iterator.hasNext()) {
            this.addListener.accept(iterator.next());
        }
        return super.addAll(collection);
    }
    
    @Override
    protected Set delegate() {
        return this.originalSet();
    }
    
    public Set originalSet() {
        return this.set;
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
