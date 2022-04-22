package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(emulated = true)
final class EmptyImmutableBiMap extends ImmutableBiMap
{
    static final EmptyImmutableBiMap INSTANCE;
    
    private EmptyImmutableBiMap() {
    }
    
    @Override
    public ImmutableBiMap inverse() {
        return this;
    }
    
    @Override
    public int size() {
        return 0;
    }
    
    @Override
    public boolean isEmpty() {
        return true;
    }
    
    @Override
    public Object get(@Nullable final Object o) {
        return null;
    }
    
    @Override
    public ImmutableSet entrySet() {
        return ImmutableSet.of();
    }
    
    @Override
    ImmutableSet createEntrySet() {
        throw new AssertionError((Object)"should never be called");
    }
    
    @Override
    public ImmutableSetMultimap asMultimap() {
        return ImmutableSetMultimap.of();
    }
    
    @Override
    public ImmutableSet keySet() {
        return ImmutableSet.of();
    }
    
    @Override
    boolean isPartialView() {
        return false;
    }
    
    Object readResolve() {
        return EmptyImmutableBiMap.INSTANCE;
    }
    
    @Override
    public BiMap inverse() {
        return this.inverse();
    }
    
    @Override
    public Set entrySet() {
        return this.entrySet();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    static {
        INSTANCE = new EmptyImmutableBiMap();
    }
}
