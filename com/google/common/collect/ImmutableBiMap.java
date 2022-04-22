package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableBiMap extends ImmutableMap implements BiMap
{
    private static final Map.Entry[] EMPTY_ENTRY_ARRAY;
    
    public static ImmutableBiMap of() {
        return EmptyImmutableBiMap.INSTANCE;
    }
    
    public static ImmutableBiMap of(final Object o, final Object o2) {
        return new SingletonImmutableBiMap(o, o2);
    }
    
    public static ImmutableBiMap of(final Object o, final Object o2, final Object o3, final Object o4) {
        return new RegularImmutableBiMap(new ImmutableMapEntry.TerminalEntry[] { ImmutableMap.entryOf(o, o2), ImmutableMap.entryOf(o3, o4) });
    }
    
    public static ImmutableBiMap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6) {
        return new RegularImmutableBiMap(new ImmutableMapEntry.TerminalEntry[] { ImmutableMap.entryOf(o, o2), ImmutableMap.entryOf(o3, o4), ImmutableMap.entryOf(o5, o6) });
    }
    
    public static ImmutableBiMap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8) {
        return new RegularImmutableBiMap(new ImmutableMapEntry.TerminalEntry[] { ImmutableMap.entryOf(o, o2), ImmutableMap.entryOf(o3, o4), ImmutableMap.entryOf(o5, o6), ImmutableMap.entryOf(o7, o8) });
    }
    
    public static ImmutableBiMap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8, final Object o9, final Object o10) {
        return new RegularImmutableBiMap(new ImmutableMapEntry.TerminalEntry[] { ImmutableMap.entryOf(o, o2), ImmutableMap.entryOf(o3, o4), ImmutableMap.entryOf(o5, o6), ImmutableMap.entryOf(o7, o8), ImmutableMap.entryOf(o9, o10) });
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    public static ImmutableBiMap copyOf(final Map map) {
        if (map instanceof ImmutableBiMap) {
            final ImmutableBiMap immutableBiMap = (ImmutableBiMap)map;
            if (!immutableBiMap.isPartialView()) {
                return immutableBiMap;
            }
        }
        final Map.Entry[] array = map.entrySet().toArray(ImmutableBiMap.EMPTY_ENTRY_ARRAY);
        switch (array.length) {
            case 0: {
                return of();
            }
            case 1: {
                final Map.Entry entry = array[0];
                return of(entry.getKey(), entry.getValue());
            }
            default: {
                return new RegularImmutableBiMap(array);
            }
        }
    }
    
    ImmutableBiMap() {
    }
    
    @Override
    public abstract ImmutableBiMap inverse();
    
    @Override
    public ImmutableSet values() {
        return this.inverse().keySet();
    }
    
    @Deprecated
    @Override
    public Object forcePut(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    Object writeReplace() {
        return new SerializedForm(this);
    }
    
    @Override
    public ImmutableCollection values() {
        return this.values();
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    public BiMap inverse() {
        return this.inverse();
    }
    
    @Override
    public Set values() {
        return this.values();
    }
    
    static {
        EMPTY_ENTRY_ARRAY = new Map.Entry[0];
    }
    
    private static class SerializedForm extends ImmutableMap.SerializedForm
    {
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final ImmutableBiMap immutableBiMap) {
            super(immutableBiMap);
        }
        
        @Override
        Object readResolve() {
            return this.createMap(new ImmutableBiMap.Builder());
        }
    }
    
    public static final class Builder extends ImmutableMap.Builder
    {
        @Override
        public Builder put(final Object o, final Object o2) {
            super.put(o, o2);
            return this;
        }
        
        @Override
        public Builder putAll(final Map map) {
            super.putAll(map);
            return this;
        }
        
        @Override
        public ImmutableBiMap build() {
            switch (this.size) {
                case 0: {
                    return ImmutableBiMap.of();
                }
                case 1: {
                    return ImmutableBiMap.of(this.entries[0].getKey(), this.entries[0].getValue());
                }
                default: {
                    return new RegularImmutableBiMap(this.size, this.entries);
                }
            }
        }
        
        @Override
        public ImmutableMap build() {
            return this.build();
        }
        
        @Override
        public ImmutableMap.Builder putAll(final Map map) {
            return this.putAll(map);
        }
        
        @Override
        public ImmutableMap.Builder put(final Object o, final Object o2) {
            return this.put(o, o2);
        }
    }
}
