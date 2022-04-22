package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;
import java.io.*;

@GwtCompatible(serializable = true, emulated = true)
final class ImmutableEnumSet extends ImmutableSet
{
    private final transient EnumSet delegate;
    private transient int hashCode;
    
    static ImmutableSet asImmutable(final EnumSet set) {
        switch (set.size()) {
            case 0: {
                return ImmutableSet.of();
            }
            case 1: {
                return ImmutableSet.of(Iterables.getOnlyElement(set));
            }
            default: {
                return new ImmutableEnumSet(set);
            }
        }
    }
    
    private ImmutableEnumSet(final EnumSet delegate) {
        this.delegate = delegate;
    }
    
    @Override
    boolean isPartialView() {
        return false;
    }
    
    @Override
    public UnmodifiableIterator iterator() {
        return Iterators.unmodifiableIterator(this.delegate.iterator());
    }
    
    @Override
    public int size() {
        return this.delegate.size();
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.delegate.contains(o);
    }
    
    @Override
    public boolean containsAll(final Collection collection) {
        return this.delegate.containsAll(collection);
    }
    
    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }
    
    @Override
    public boolean equals(final Object o) {
        return o == this || this.delegate.equals(o);
    }
    
    @Override
    public int hashCode() {
        final int hashCode = this.hashCode;
        return (hashCode == 0) ? (this.hashCode = this.delegate.hashCode()) : hashCode;
    }
    
    @Override
    public String toString() {
        return this.delegate.toString();
    }
    
    @Override
    Object writeReplace() {
        return new EnumSerializedForm(this.delegate);
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    ImmutableEnumSet(final EnumSet set, final ImmutableEnumSet$1 object) {
        this(set);
    }
    
    private static class EnumSerializedForm implements Serializable
    {
        final EnumSet delegate;
        private static final long serialVersionUID = 0L;
        
        EnumSerializedForm(final EnumSet delegate) {
            this.delegate = delegate;
        }
        
        Object readResolve() {
            return new ImmutableEnumSet(this.delegate.clone(), null);
        }
    }
}
