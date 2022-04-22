package com.google.common.collect;

import javax.annotation.*;
import java.util.*;
import com.google.common.annotations.*;
import java.io.*;

@GwtCompatible(emulated = true)
final class ImmutableMapValues extends ImmutableCollection
{
    private final ImmutableMap map;
    
    ImmutableMapValues(final ImmutableMap map) {
        this.map = map;
    }
    
    @Override
    public int size() {
        return this.map.size();
    }
    
    @Override
    public UnmodifiableIterator iterator() {
        return Maps.valueIterator(this.map.entrySet().iterator());
    }
    
    @Override
    public boolean contains(@Nullable final Object o) {
        return o != null && Iterators.contains(this.iterator(), o);
    }
    
    @Override
    boolean isPartialView() {
        return true;
    }
    
    @Override
    ImmutableList createAsList() {
        return new ImmutableAsList(this.map.entrySet().asList()) {
            final ImmutableList val$entryList;
            final ImmutableMapValues this$0;
            
            @Override
            public Object get(final int n) {
                return this.val$entryList.get(n).getValue();
            }
            
            @Override
            ImmutableCollection delegateCollection() {
                return this.this$0;
            }
        };
    }
    
    @GwtIncompatible("serialization")
    @Override
    Object writeReplace() {
        return new SerializedForm(this.map);
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    @GwtIncompatible("serialization")
    private static class SerializedForm implements Serializable
    {
        final ImmutableMap map;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final ImmutableMap map) {
            this.map = map;
        }
        
        Object readResolve() {
            return this.map.values();
        }
    }
}
