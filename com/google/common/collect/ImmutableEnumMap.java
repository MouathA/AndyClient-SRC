package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.*;
import javax.annotation.*;
import java.io.*;

@GwtCompatible(serializable = true, emulated = true)
final class ImmutableEnumMap extends ImmutableMap
{
    private final transient EnumMap delegate;
    
    static ImmutableMap asImmutable(final EnumMap enumMap) {
        switch (enumMap.size()) {
            case 0: {
                return ImmutableMap.of();
            }
            case 1: {
                final Map.Entry entry = (Map.Entry)Iterables.getOnlyElement(enumMap.entrySet());
                return ImmutableMap.of(entry.getKey(), entry.getValue());
            }
            default: {
                return new ImmutableEnumMap(enumMap);
            }
        }
    }
    
    private ImmutableEnumMap(final EnumMap delegate) {
        this.delegate = delegate;
        Preconditions.checkArgument(!delegate.isEmpty());
    }
    
    @Override
    ImmutableSet createKeySet() {
        return new ImmutableSet() {
            final ImmutableEnumMap this$0;
            
            @Override
            public boolean contains(final Object o) {
                return ImmutableEnumMap.access$000(this.this$0).containsKey(o);
            }
            
            @Override
            public int size() {
                return this.this$0.size();
            }
            
            @Override
            public UnmodifiableIterator iterator() {
                return Iterators.unmodifiableIterator(ImmutableEnumMap.access$000(this.this$0).keySet().iterator());
            }
            
            @Override
            boolean isPartialView() {
                return true;
            }
            
            @Override
            public Iterator iterator() {
                return this.iterator();
            }
        };
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
    public Object get(final Object o) {
        return this.delegate.get(o);
    }
    
    @Override
    ImmutableSet createEntrySet() {
        return new ImmutableMapEntrySet() {
            final ImmutableEnumMap this$0;
            
            @Override
            ImmutableMap map() {
                return this.this$0;
            }
            
            @Override
            public UnmodifiableIterator iterator() {
                return new UnmodifiableIterator() {
                    private final Iterator backingIterator = ImmutableEnumMap.access$000(this.this$1.this$0).entrySet().iterator();
                    final ImmutableEnumMap$2 this$1;
                    
                    @Override
                    public boolean hasNext() {
                        return this.backingIterator.hasNext();
                    }
                    
                    @Override
                    public Map.Entry next() {
                        final Map.Entry<Object, V> entry = this.backingIterator.next();
                        return Maps.immutableEntry(entry.getKey(), entry.getValue());
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
    boolean isPartialView() {
        return false;
    }
    
    @Override
    Object writeReplace() {
        return new EnumSerializedForm(this.delegate);
    }
    
    static EnumMap access$000(final ImmutableEnumMap immutableEnumMap) {
        return immutableEnumMap.delegate;
    }
    
    ImmutableEnumMap(final EnumMap enumMap, final ImmutableEnumMap$1 immutableSet) {
        this(enumMap);
    }
    
    private static class EnumSerializedForm implements Serializable
    {
        final EnumMap delegate;
        private static final long serialVersionUID = 0L;
        
        EnumSerializedForm(final EnumMap delegate) {
            this.delegate = delegate;
        }
        
        Object readResolve() {
            return new ImmutableEnumMap(this.delegate, null);
        }
    }
}
