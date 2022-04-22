package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;
import javax.annotation.*;

@GwtCompatible(serializable = true, emulated = true)
final class SingletonImmutableBiMap extends ImmutableBiMap
{
    final transient Object singleKey;
    final transient Object singleValue;
    transient ImmutableBiMap inverse;
    
    SingletonImmutableBiMap(final Object singleKey, final Object singleValue) {
        CollectPreconditions.checkEntryNotNull(singleKey, singleValue);
        this.singleKey = singleKey;
        this.singleValue = singleValue;
    }
    
    private SingletonImmutableBiMap(final Object singleKey, final Object singleValue, final ImmutableBiMap inverse) {
        this.singleKey = singleKey;
        this.singleValue = singleValue;
        this.inverse = inverse;
    }
    
    SingletonImmutableBiMap(final Map.Entry entry) {
        this(entry.getKey(), entry.getValue());
    }
    
    @Override
    public Object get(@Nullable final Object o) {
        return this.singleKey.equals(o) ? this.singleValue : null;
    }
    
    @Override
    public int size() {
        return 1;
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        return this.singleKey.equals(o);
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return this.singleValue.equals(o);
    }
    
    @Override
    boolean isPartialView() {
        return false;
    }
    
    @Override
    ImmutableSet createEntrySet() {
        return ImmutableSet.of(Maps.immutableEntry(this.singleKey, this.singleValue));
    }
    
    @Override
    ImmutableSet createKeySet() {
        return ImmutableSet.of(this.singleKey);
    }
    
    @Override
    public ImmutableBiMap inverse() {
        final ImmutableBiMap inverse = this.inverse;
        if (inverse == null) {
            return this.inverse = new SingletonImmutableBiMap(this.singleValue, this.singleKey, this);
        }
        return inverse;
    }
    
    @Override
    public BiMap inverse() {
        return this.inverse();
    }
}
