package com.google.common.collect;

import java.io.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;
import com.google.common.base.*;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableMap implements Map, Serializable
{
    private static final Entry[] EMPTY_ENTRY_ARRAY;
    private transient ImmutableSet entrySet;
    private transient ImmutableSet keySet;
    private transient ImmutableCollection values;
    private transient ImmutableSetMultimap multimapView;
    
    public static ImmutableMap of() {
        return ImmutableBiMap.of();
    }
    
    public static ImmutableMap of(final Object o, final Object o2) {
        return ImmutableBiMap.of(o, o2);
    }
    
    public static ImmutableMap of(final Object o, final Object o2, final Object o3, final Object o4) {
        return new RegularImmutableMap(new ImmutableMapEntry.TerminalEntry[] { entryOf(o, o2), entryOf(o3, o4) });
    }
    
    public static ImmutableMap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6) {
        return new RegularImmutableMap(new ImmutableMapEntry.TerminalEntry[] { entryOf(o, o2), entryOf(o3, o4), entryOf(o5, o6) });
    }
    
    public static ImmutableMap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8) {
        return new RegularImmutableMap(new ImmutableMapEntry.TerminalEntry[] { entryOf(o, o2), entryOf(o3, o4), entryOf(o5, o6), entryOf(o7, o8) });
    }
    
    public static ImmutableMap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8, final Object o9, final Object o10) {
        return new RegularImmutableMap(new ImmutableMapEntry.TerminalEntry[] { entryOf(o, o2), entryOf(o3, o4), entryOf(o5, o6), entryOf(o7, o8), entryOf(o9, o10) });
    }
    
    static ImmutableMapEntry.TerminalEntry entryOf(final Object o, final Object o2) {
        CollectPreconditions.checkEntryNotNull(o, o2);
        return new ImmutableMapEntry.TerminalEntry(o, o2);
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    static void checkNoConflict(final boolean b, final String s, final Entry entry, final Entry entry2) {
        if (!b) {
            throw new IllegalArgumentException("Multiple entries with same " + s + ": " + entry + " and " + entry2);
        }
    }
    
    public static ImmutableMap copyOf(final Map map) {
        if (map instanceof ImmutableMap && !(map instanceof ImmutableSortedMap)) {
            final ImmutableMap immutableMap = (ImmutableMap)map;
            if (!immutableMap.isPartialView()) {
                return immutableMap;
            }
        }
        else if (map instanceof EnumMap) {
            return copyOfEnumMapUnsafe(map);
        }
        final Entry[] array = map.entrySet().toArray(ImmutableMap.EMPTY_ENTRY_ARRAY);
        switch (array.length) {
            case 0: {
                return of();
            }
            case 1: {
                final Entry entry = array[0];
                return of(entry.getKey(), entry.getValue());
            }
            default: {
                return new RegularImmutableMap(array);
            }
        }
    }
    
    private static ImmutableMap copyOfEnumMapUnsafe(final Map map) {
        return copyOfEnumMap(map);
    }
    
    private static ImmutableMap copyOfEnumMap(final Map map) {
        final EnumMap<Object, Object> enumMap = new EnumMap<Object, Object>(map);
        for (final Entry<Object, V> entry : enumMap.entrySet()) {
            CollectPreconditions.checkEntryNotNull(entry.getKey(), entry.getValue());
        }
        return ImmutableEnumMap.asImmutable(enumMap);
    }
    
    ImmutableMap() {
    }
    
    @Deprecated
    @Override
    public final Object put(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final Object remove(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final void putAll(final Map map) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public boolean isEmpty() {
        return this.size() == 0;
    }
    
    @Override
    public boolean containsKey(@Nullable final Object o) {
        return this.get(o) != null;
    }
    
    @Override
    public boolean containsValue(@Nullable final Object o) {
        return this.values().contains(o);
    }
    
    @Override
    public abstract Object get(@Nullable final Object p0);
    
    @Override
    public ImmutableSet entrySet() {
        final ImmutableSet entrySet = this.entrySet;
        return (entrySet == null) ? (this.entrySet = this.createEntrySet()) : entrySet;
    }
    
    abstract ImmutableSet createEntrySet();
    
    @Override
    public ImmutableSet keySet() {
        final ImmutableSet keySet = this.keySet;
        return (keySet == null) ? (this.keySet = this.createKeySet()) : keySet;
    }
    
    ImmutableSet createKeySet() {
        return new ImmutableMapKeySet(this);
    }
    
    @Override
    public ImmutableCollection values() {
        final ImmutableCollection values = this.values;
        return (values == null) ? (this.values = new ImmutableMapValues(this)) : values;
    }
    
    @Beta
    public ImmutableSetMultimap asMultimap() {
        final ImmutableSetMultimap multimapView = this.multimapView;
        return (multimapView == null) ? (this.multimapView = this.createMultimapView()) : multimapView;
    }
    
    private ImmutableSetMultimap createMultimapView() {
        final ImmutableMap viewMapValuesAsSingletonSets = this.viewMapValuesAsSingletonSets();
        return new ImmutableSetMultimap(viewMapValuesAsSingletonSets, viewMapValuesAsSingletonSets.size(), null);
    }
    
    private ImmutableMap viewMapValuesAsSingletonSets() {
        return new MapViewOfValuesAsSingletonSets(this);
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return Maps.equalsImpl(this, o);
    }
    
    abstract boolean isPartialView();
    
    @Override
    public int hashCode() {
        return this.entrySet().hashCode();
    }
    
    @Override
    public String toString() {
        return Maps.toStringImpl(this);
    }
    
    Object writeReplace() {
        return new SerializedForm(this);
    }
    
    @Override
    public Set entrySet() {
        return this.entrySet();
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
    
    static {
        EMPTY_ENTRY_ARRAY = new Entry[0];
    }
    
    static class SerializedForm implements Serializable
    {
        private final Object[] keys;
        private final Object[] values;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final ImmutableMap immutableMap) {
            this.keys = new Object[immutableMap.size()];
            this.values = new Object[immutableMap.size()];
            for (final Entry<Object, V> entry : immutableMap.entrySet()) {
                this.keys[0] = entry.getKey();
                this.values[0] = entry.getValue();
                int n = 0;
                ++n;
            }
        }
        
        Object readResolve() {
            return this.createMap(new Builder());
        }
        
        Object createMap(final Builder builder) {
            while (0 < this.keys.length) {
                builder.put(this.keys[0], this.values[0]);
                int n = 0;
                ++n;
            }
            return builder.build();
        }
    }
    
    public static class Builder
    {
        ImmutableMapEntry.TerminalEntry[] entries;
        int size;
        
        public Builder() {
            this(4);
        }
        
        Builder(final int n) {
            this.entries = new ImmutableMapEntry.TerminalEntry[n];
            this.size = 0;
        }
        
        private void ensureCapacity(final int n) {
            if (n > this.entries.length) {
                this.entries = (ImmutableMapEntry.TerminalEntry[])ObjectArrays.arraysCopyOf(this.entries, ImmutableCollection.Builder.expandedCapacity(this.entries.length, n));
            }
        }
        
        public Builder put(final Object o, final Object o2) {
            this.ensureCapacity(this.size + 1);
            this.entries[this.size++] = ImmutableMap.entryOf(o, o2);
            return this;
        }
        
        public Builder put(final Entry entry) {
            return this.put(entry.getKey(), entry.getValue());
        }
        
        public Builder putAll(final Map map) {
            this.ensureCapacity(this.size + map.size());
            final Iterator iterator = map.entrySet().iterator();
            while (iterator.hasNext()) {
                this.put(iterator.next());
            }
            return this;
        }
        
        public ImmutableMap build() {
            switch (this.size) {
                case 0: {
                    return ImmutableMap.of();
                }
                case 1: {
                    return ImmutableMap.of(this.entries[0].getKey(), this.entries[0].getValue());
                }
                default: {
                    return new RegularImmutableMap(this.size, this.entries);
                }
            }
        }
    }
    
    private static final class MapViewOfValuesAsSingletonSets extends ImmutableMap
    {
        private final ImmutableMap delegate;
        
        MapViewOfValuesAsSingletonSets(final ImmutableMap immutableMap) {
            this.delegate = (ImmutableMap)Preconditions.checkNotNull(immutableMap);
        }
        
        @Override
        public int size() {
            return this.delegate.size();
        }
        
        @Override
        public boolean containsKey(@Nullable final Object o) {
            return this.delegate.containsKey(o);
        }
        
        @Override
        public ImmutableSet get(@Nullable final Object o) {
            final Object value = this.delegate.get(o);
            return (value == null) ? null : ImmutableSet.of(value);
        }
        
        @Override
        boolean isPartialView() {
            return false;
        }
        
        @Override
        ImmutableSet createEntrySet() {
            return new ImmutableMapEntrySet() {
                final MapViewOfValuesAsSingletonSets this$0;
                
                @Override
                ImmutableMap map() {
                    return this.this$0;
                }
                
                @Override
                public UnmodifiableIterator iterator() {
                    return new UnmodifiableIterator((Iterator)MapViewOfValuesAsSingletonSets.access$000(this.this$0).entrySet().iterator()) {
                        final Iterator val$backingIterator;
                        final ImmutableMap$MapViewOfValuesAsSingletonSets$1 this$1;
                        
                        @Override
                        public boolean hasNext() {
                            return this.val$backingIterator.hasNext();
                        }
                        
                        @Override
                        public Entry next() {
                            return new AbstractMapEntry((Entry)this.val$backingIterator.next()) {
                                final Entry val$backingEntry;
                                final ImmutableMap$MapViewOfValuesAsSingletonSets$1$1 this$2;
                                
                                @Override
                                public Object getKey() {
                                    return this.val$backingEntry.getKey();
                                }
                                
                                @Override
                                public ImmutableSet getValue() {
                                    return ImmutableSet.of(this.val$backingEntry.getValue());
                                }
                                
                                @Override
                                public Object getValue() {
                                    return this.getValue();
                                }
                            };
                        }
                        
                        @Override
                        public Object next() {
                            return this.next();
                        }
                    };
                }
                
                @Override
                public Iterator iterator() {
                    return this.iterator();
                }
            };
        }
        
        @Override
        public Object get(final Object o) {
            return this.get(o);
        }
        
        @Override
        public Set entrySet() {
            return super.entrySet();
        }
        
        @Override
        public Collection values() {
            return super.values();
        }
        
        @Override
        public Set keySet() {
            return super.keySet();
        }
        
        static ImmutableMap access$000(final MapViewOfValuesAsSingletonSets mapViewOfValuesAsSingletonSets) {
            return mapViewOfValuesAsSingletonSets.delegate;
        }
    }
}
