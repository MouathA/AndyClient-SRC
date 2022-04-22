package com.google.common.cache;

import java.util.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.base.*;

@Beta
@GwtCompatible
public final class RemovalNotification implements Map.Entry
{
    @Nullable
    private final Object key;
    @Nullable
    private final Object value;
    private final RemovalCause cause;
    private static final long serialVersionUID = 0L;
    
    RemovalNotification(@Nullable final Object key, @Nullable final Object value, final RemovalCause removalCause) {
        this.key = key;
        this.value = value;
        this.cause = (RemovalCause)Preconditions.checkNotNull(removalCause);
    }
    
    public RemovalCause getCause() {
        return this.cause;
    }
    
    public boolean wasEvicted() {
        return this.cause.wasEvicted();
    }
    
    @Nullable
    @Override
    public Object getKey() {
        return this.key;
    }
    
    @Nullable
    @Override
    public Object getValue() {
        return this.value;
    }
    
    @Override
    public final Object setValue(final Object o) {
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
