package com.google.common.collect;

import javax.annotation.*;
import java.util.*;
import com.google.common.annotations.*;
import java.io.*;

@GwtCompatible(emulated = true)
abstract class ImmutableMapEntrySet extends ImmutableSet
{
    abstract ImmutableMap map();
    
    @Override
    public int size() {
        return this.map().size();
    }
    
    @Override
    public boolean contains(@Nullable final Object o) {
        if (o instanceof Map.Entry) {
            final Map.Entry entry = (Map.Entry)o;
            final Object value = this.map().get(entry.getKey());
            return value != null && value.equals(entry.getValue());
        }
        return false;
    }
    
    @Override
    boolean isPartialView() {
        return this.map().isPartialView();
    }
    
    @GwtIncompatible("serialization")
    @Override
    Object writeReplace() {
        return new EntrySetSerializedForm(this.map());
    }
    
    @GwtIncompatible("serialization")
    private static class EntrySetSerializedForm implements Serializable
    {
        final ImmutableMap map;
        private static final long serialVersionUID = 0L;
        
        EntrySetSerializedForm(final ImmutableMap map) {
            this.map = map;
        }
        
        Object readResolve() {
            return this.map.entrySet();
        }
    }
}
