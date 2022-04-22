package com.google.common.collect;

import java.util.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;

@GwtCompatible
abstract class AbstractMapEntry implements Map.Entry
{
    @Override
    public abstract Object getKey();
    
    @Override
    public abstract Object getValue();
    
    @Override
    public Object setValue(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        if (o instanceof Map.Entry) {
            final Map.Entry entry = (Map.Entry)o;
            return Objects.equal(this.getKey(), entry.getKey()) && Objects.equal(this.getValue(), entry.getValue());
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        final Object key = this.getKey();
        final Object value = this.getValue();
        return ((key == null) ? 0 : key.hashCode()) ^ ((value == null) ? 0 : value.hashCode());
    }
    
    @Override
    public String toString() {
        return this.getKey() + "=" + this.getValue();
    }
}
