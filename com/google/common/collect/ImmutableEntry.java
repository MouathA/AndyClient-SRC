package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import javax.annotation.*;

@GwtCompatible(serializable = true)
class ImmutableEntry extends AbstractMapEntry implements Serializable
{
    final Object key;
    final Object value;
    private static final long serialVersionUID = 0L;
    
    ImmutableEntry(@Nullable final Object key, @Nullable final Object value) {
        this.key = key;
        this.value = value;
    }
    
    @Nullable
    @Override
    public final Object getKey() {
        return this.key;
    }
    
    @Nullable
    @Override
    public final Object getValue() {
        return this.value;
    }
    
    @Override
    public final Object setValue(final Object o) {
        throw new UnsupportedOperationException();
    }
}
