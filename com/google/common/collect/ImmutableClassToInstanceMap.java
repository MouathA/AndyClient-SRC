package com.google.common.collect;

import java.io.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;
import com.google.common.primitives.*;

public final class ImmutableClassToInstanceMap extends ForwardingMap implements ClassToInstanceMap, Serializable
{
    private final ImmutableMap delegate;
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static ImmutableClassToInstanceMap copyOf(final Map map) {
        if (map instanceof ImmutableClassToInstanceMap) {
            return (ImmutableClassToInstanceMap)map;
        }
        return new Builder().putAll(map).build();
    }
    
    private ImmutableClassToInstanceMap(final ImmutableMap delegate) {
        this.delegate = delegate;
    }
    
    @Override
    protected Map delegate() {
        return this.delegate;
    }
    
    @Nullable
    @Override
    public Object getInstance(final Class clazz) {
        return this.delegate.get(Preconditions.checkNotNull(clazz));
    }
    
    @Deprecated
    @Override
    public Object putInstance(final Class clazz, final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    ImmutableClassToInstanceMap(final ImmutableMap immutableMap, final ImmutableClassToInstanceMap$1 object) {
        this(immutableMap);
    }
    
    public static final class Builder
    {
        private final ImmutableMap.Builder mapBuilder;
        
        public Builder() {
            this.mapBuilder = ImmutableMap.builder();
        }
        
        public Builder put(final Class clazz, final Object o) {
            this.mapBuilder.put(clazz, o);
            return this;
        }
        
        public Builder putAll(final Map map) {
            for (final Map.Entry<Class, V> entry : map.entrySet()) {
                final Class clazz = entry.getKey();
                this.mapBuilder.put(clazz, cast(clazz, entry.getValue()));
            }
            return this;
        }
        
        private static Object cast(final Class clazz, final Object o) {
            return Primitives.wrap(clazz).cast(o);
        }
        
        public ImmutableClassToInstanceMap build() {
            return new ImmutableClassToInstanceMap(this.mapBuilder.build(), null);
        }
    }
}
