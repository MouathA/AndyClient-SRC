package com.google.common.collect;

import java.util.*;
import javax.annotation.*;
import com.google.common.base.*;
import com.google.common.annotations.*;

@GwtCompatible
public abstract class ForwardingMapEntry extends ForwardingObject implements Map.Entry
{
    protected ForwardingMapEntry() {
    }
    
    @Override
    protected abstract Map.Entry delegate();
    
    @Override
    public Object getKey() {
        return this.delegate().getKey();
    }
    
    @Override
    public Object getValue() {
        return this.delegate().getValue();
    }
    
    @Override
    public Object setValue(final Object value) {
        return this.delegate().setValue(value);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return this.delegate().equals(o);
    }
    
    @Override
    public int hashCode() {
        return this.delegate().hashCode();
    }
    
    protected boolean standardEquals(@Nullable final Object o) {
        if (o instanceof Map.Entry) {
            final Map.Entry entry = (Map.Entry)o;
            return Objects.equal(this.getKey(), entry.getKey()) && Objects.equal(this.getValue(), entry.getValue());
        }
        return false;
    }
    
    protected int standardHashCode() {
        final Object key = this.getKey();
        final Object value = this.getValue();
        return ((key == null) ? 0 : key.hashCode()) ^ ((value == null) ? 0 : value.hashCode());
    }
    
    @Beta
    protected String standardToString() {
        return this.getKey() + "=" + this.getValue();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
}
