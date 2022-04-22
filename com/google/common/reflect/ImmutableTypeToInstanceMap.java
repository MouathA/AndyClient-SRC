package com.google.common.reflect;

import com.google.common.annotations.*;
import com.google.common.collect.*;
import java.util.*;

@Beta
public final class ImmutableTypeToInstanceMap extends ForwardingMap implements TypeToInstanceMap
{
    private final ImmutableMap delegate;
    
    public static ImmutableTypeToInstanceMap of() {
        return new ImmutableTypeToInstanceMap(ImmutableMap.of());
    }
    
    public static Builder builder() {
        return new Builder(null);
    }
    
    private ImmutableTypeToInstanceMap(final ImmutableMap delegate) {
        this.delegate = delegate;
    }
    
    @Override
    public Object getInstance(final TypeToken typeToken) {
        return this.trustedGet(typeToken.rejectTypeVariables());
    }
    
    @Override
    public Object putInstance(final TypeToken typeToken, final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Object getInstance(final Class clazz) {
        return this.trustedGet(TypeToken.of(clazz));
    }
    
    @Override
    public Object putInstance(final Class clazz, final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    protected Map delegate() {
        return this.delegate;
    }
    
    private Object trustedGet(final TypeToken typeToken) {
        return this.delegate.get(typeToken);
    }
    
    @Override
    protected Object delegate() {
        return this.delegate();
    }
    
    ImmutableTypeToInstanceMap(final ImmutableMap immutableMap, final ImmutableTypeToInstanceMap$1 object) {
        this(immutableMap);
    }
    
    @Beta
    public static final class Builder
    {
        private final ImmutableMap.Builder mapBuilder;
        
        private Builder() {
            this.mapBuilder = ImmutableMap.builder();
        }
        
        public Builder put(final Class clazz, final Object o) {
            this.mapBuilder.put(TypeToken.of(clazz), o);
            return this;
        }
        
        public Builder put(final TypeToken typeToken, final Object o) {
            this.mapBuilder.put(typeToken.rejectTypeVariables(), o);
            return this;
        }
        
        public ImmutableTypeToInstanceMap build() {
            return new ImmutableTypeToInstanceMap(this.mapBuilder.build(), null);
        }
        
        Builder(final ImmutableTypeToInstanceMap$1 object) {
            this();
        }
    }
}
