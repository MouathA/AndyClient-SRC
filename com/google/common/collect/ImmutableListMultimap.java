package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.io.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
public class ImmutableListMultimap extends ImmutableMultimap implements ListMultimap
{
    private transient ImmutableListMultimap inverse;
    @GwtIncompatible("Not needed in emulated source")
    private static final long serialVersionUID = 0L;
    
    public static ImmutableListMultimap of() {
        return EmptyImmutableListMultimap.INSTANCE;
    }
    
    public static ImmutableListMultimap of(final Object o, final Object o2) {
        final Builder builder = builder();
        builder.put(o, o2);
        return builder.build();
    }
    
    public static ImmutableListMultimap of(final Object o, final Object o2, final Object o3, final Object o4) {
        final Builder builder = builder();
        builder.put(o, o2);
        builder.put(o3, o4);
        return builder.build();
    }
    
    public static ImmutableListMultimap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6) {
        final Builder builder = builder();
        builder.put(o, o2);
        builder.put(o3, o4);
        builder.put(o5, o6);
        return builder.build();
    }
    
    public static ImmutableListMultimap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8) {
        final Builder builder = builder();
        builder.put(o, o2);
        builder.put(o3, o4);
        builder.put(o5, o6);
        builder.put(o7, o8);
        return builder.build();
    }
    
    public static ImmutableListMultimap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8, final Object o9, final Object o10) {
        final Builder builder = builder();
        builder.put(o, o2);
        builder.put(o3, o4);
        builder.put(o5, o6);
        builder.put(o7, o8);
        builder.put(o9, o10);
        return builder.build();
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static ImmutableListMultimap copyOf(final Multimap multimap) {
        if (multimap.isEmpty()) {
            return of();
        }
        if (multimap instanceof ImmutableListMultimap) {
            final ImmutableListMultimap immutableListMultimap = (ImmutableListMultimap)multimap;
            if (!immutableListMultimap.isPartialView()) {
                return immutableListMultimap;
            }
        }
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        for (final Map.Entry<K, Collection> entry : multimap.asMap().entrySet()) {
            final ImmutableList copy = ImmutableList.copyOf(entry.getValue());
            if (!copy.isEmpty()) {
                builder.put(entry.getKey(), copy);
                final int n = 0 + copy.size();
            }
        }
        return new ImmutableListMultimap(builder.build(), 0);
    }
    
    ImmutableListMultimap(final ImmutableMap immutableMap, final int n) {
        super(immutableMap, n);
    }
    
    @Override
    public ImmutableList get(@Nullable final Object o) {
        final ImmutableList list = (ImmutableList)this.map.get(o);
        return (list == null) ? ImmutableList.of() : list;
    }
    
    @Override
    public ImmutableListMultimap inverse() {
        final ImmutableListMultimap inverse = this.inverse;
        return (inverse == null) ? (this.inverse = this.invert()) : inverse;
    }
    
    private ImmutableListMultimap invert() {
        final Builder builder = builder();
        for (final Map.Entry<K, Object> entry : this.entries()) {
            builder.put(entry.getValue(), (Object)entry.getKey());
        }
        final ImmutableListMultimap build = builder.build();
        build.inverse = this;
        return build;
    }
    
    @Deprecated
    @Override
    public ImmutableList removeAll(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public ImmutableList replaceValues(final Object o, final Iterable iterable) {
        throw new UnsupportedOperationException();
    }
    
    @GwtIncompatible("java.io.ObjectOutputStream")
    private void writeObject(final ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
        Serialization.writeMultimap(this, objectOutputStream);
    }
    
    @GwtIncompatible("java.io.ObjectInputStream")
    private void readObject(final ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
        final int int1 = objectInputStream.readInt();
        if (int1 < 0) {
            throw new InvalidObjectException("Invalid key count " + int1);
        }
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        while (0 < int1) {
            final Object object = objectInputStream.readObject();
            final int int2 = objectInputStream.readInt();
            if (int2 <= 0) {
                throw new InvalidObjectException("Invalid value count " + int2);
            }
            final Object[] array = new Object[int2];
            while (0 < int2) {
                array[0] = objectInputStream.readObject();
                int n = 0;
                ++n;
            }
            builder.put(object, ImmutableList.copyOf(array));
            int n2 = 0;
            ++n2;
        }
        FieldSettersHolder.MAP_FIELD_SETTER.set(this, builder.build());
        FieldSettersHolder.SIZE_FIELD_SETTER.set(this, 0);
    }
    
    @Override
    public ImmutableMultimap inverse() {
        return this.inverse();
    }
    
    @Override
    public ImmutableCollection get(final Object o) {
        return this.get(o);
    }
    
    @Override
    public ImmutableCollection replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    public ImmutableCollection removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    @Override
    public Collection replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    public Collection get(final Object o) {
        return this.get(o);
    }
    
    @Override
    public Collection removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    @Override
    public List replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
    
    @Override
    public List removeAll(final Object o) {
        return this.removeAll(o);
    }
    
    @Override
    public List get(final Object o) {
        return this.get(o);
    }
    
    public static final class Builder extends ImmutableMultimap.Builder
    {
        @Override
        public Builder put(final Object o, final Object o2) {
            super.put(o, o2);
            return this;
        }
        
        @Override
        public Builder put(final Map.Entry entry) {
            super.put(entry);
            return this;
        }
        
        @Override
        public Builder putAll(final Object o, final Iterable iterable) {
            super.putAll(o, iterable);
            return this;
        }
        
        @Override
        public Builder putAll(final Object o, final Object... array) {
            super.putAll(o, array);
            return this;
        }
        
        @Override
        public Builder putAll(final Multimap multimap) {
            super.putAll(multimap);
            return this;
        }
        
        @Override
        public Builder orderKeysBy(final Comparator comparator) {
            super.orderKeysBy(comparator);
            return this;
        }
        
        @Override
        public Builder orderValuesBy(final Comparator comparator) {
            super.orderValuesBy(comparator);
            return this;
        }
        
        @Override
        public ImmutableListMultimap build() {
            return (ImmutableListMultimap)super.build();
        }
        
        @Override
        public ImmutableMultimap build() {
            return this.build();
        }
        
        @Override
        public ImmutableMultimap.Builder orderValuesBy(final Comparator comparator) {
            return this.orderValuesBy(comparator);
        }
        
        @Override
        public ImmutableMultimap.Builder orderKeysBy(final Comparator comparator) {
            return this.orderKeysBy(comparator);
        }
        
        @Override
        public ImmutableMultimap.Builder putAll(final Multimap multimap) {
            return this.putAll(multimap);
        }
        
        @Override
        public ImmutableMultimap.Builder putAll(final Object o, final Object[] array) {
            return this.putAll(o, array);
        }
        
        @Override
        public ImmutableMultimap.Builder putAll(final Object o, final Iterable iterable) {
            return this.putAll(o, iterable);
        }
        
        @Override
        public ImmutableMultimap.Builder put(final Map.Entry entry) {
            return this.put(entry);
        }
        
        @Override
        public ImmutableMultimap.Builder put(final Object o, final Object o2) {
            return this.put(o, o2);
        }
    }
}
