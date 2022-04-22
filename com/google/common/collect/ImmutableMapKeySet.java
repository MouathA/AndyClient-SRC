package com.google.common.collect;

import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;
import java.io.*;

@GwtCompatible(emulated = true)
final class ImmutableMapKeySet extends ImmutableSet
{
    private final ImmutableMap map;
    
    ImmutableMapKeySet(final ImmutableMap map) {
        this.map = map;
    }
    
    @Override
    public int size() {
        return this.map.size();
    }
    
    @Override
    public UnmodifiableIterator iterator() {
        return this.asList().iterator();
    }
    
    @Override
    public boolean contains(@Nullable final Object o) {
        return this.map.containsKey(o);
    }
    
    @Override
    ImmutableList createAsList() {
        return new ImmutableAsList(this.map.entrySet().asList()) {
            final ImmutableList val$entryList;
            final ImmutableMapKeySet this$0;
            
            @Override
            public Object get(final int n) {
                return this.val$entryList.get(n).getKey();
            }
            
            @Override
            ImmutableCollection delegateCollection() {
                return this.this$0;
            }
        };
    }
    
    @Override
    boolean isPartialView() {
        return true;
    }
    
    @GwtIncompatible("serialization")
    @Override
    Object writeReplace() {
        return new KeySetSerializedForm(this.map);
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    @GwtIncompatible("serialization")
    private static class KeySetSerializedForm implements Serializable
    {
        final ImmutableMap map;
        private static final long serialVersionUID = 0L;
        
        KeySetSerializedForm(final ImmutableMap map) {
            this.map = map;
        }
        
        Object readResolve() {
            return this.map.keySet();
        }
    }
}
