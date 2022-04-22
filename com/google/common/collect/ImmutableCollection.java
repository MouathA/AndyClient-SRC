package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(emulated = true)
public abstract class ImmutableCollection extends AbstractCollection implements Serializable
{
    private transient ImmutableList asList;
    
    ImmutableCollection() {
    }
    
    @Override
    public abstract UnmodifiableIterator iterator();
    
    @Override
    public final Object[] toArray() {
        if (this.size() == 0) {
            return ObjectArrays.EMPTY_ARRAY;
        }
        final Object[] array = new Object[this.size()];
        this.copyIntoArray(array, 0);
        return array;
    }
    
    @Override
    public final Object[] toArray(Object[] array) {
        Preconditions.checkNotNull(array);
        final int size = this.size();
        if (array.length < size) {
            array = ObjectArrays.newArray(array, size);
        }
        else if (array.length > size) {
            array[size] = null;
        }
        this.copyIntoArray(array, 0);
        return array;
    }
    
    @Override
    public boolean contains(@Nullable final Object o) {
        return o != null && super.contains(o);
    }
    
    @Deprecated
    @Override
    public final boolean add(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final boolean remove(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final boolean addAll(final Collection collection) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final boolean removeAll(final Collection collection) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final boolean retainAll(final Collection collection) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final void clear() {
        throw new UnsupportedOperationException();
    }
    
    public ImmutableList asList() {
        final ImmutableList asList = this.asList;
        return (asList == null) ? (this.asList = this.createAsList()) : asList;
    }
    
    ImmutableList createAsList() {
        switch (this.size()) {
            case 0: {
                return ImmutableList.of();
            }
            case 1: {
                return ImmutableList.of(this.iterator().next());
            }
            default: {
                return new RegularImmutableAsList(this, this.toArray());
            }
        }
    }
    
    abstract boolean isPartialView();
    
    int copyIntoArray(final Object[] array, int n) {
        final Iterator iterator = this.iterator();
        while (iterator.hasNext()) {
            array[n++] = iterator.next();
        }
        return n;
    }
    
    Object writeReplace() {
        return new ImmutableList.SerializedForm(this.toArray());
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    abstract static class ArrayBasedBuilder extends Builder
    {
        Object[] contents;
        int size;
        
        ArrayBasedBuilder(final int n) {
            CollectPreconditions.checkNonnegative(n, "initialCapacity");
            this.contents = new Object[n];
            this.size = 0;
        }
        
        private void ensureCapacity(final int n) {
            if (this.contents.length < n) {
                this.contents = ObjectArrays.arraysCopyOf(this.contents, Builder.expandedCapacity(this.contents.length, n));
            }
        }
        
        @Override
        public ArrayBasedBuilder add(final Object o) {
            Preconditions.checkNotNull(o);
            this.ensureCapacity(this.size + 1);
            this.contents[this.size++] = o;
            return this;
        }
        
        @Override
        public Builder add(final Object... array) {
            ObjectArrays.checkElementsNotNull(array);
            this.ensureCapacity(this.size + array.length);
            System.arraycopy(array, 0, this.contents, this.size, array.length);
            this.size += array.length;
            return this;
        }
        
        @Override
        public Builder addAll(final Iterable iterable) {
            if (iterable instanceof Collection) {
                this.ensureCapacity(this.size + ((Collection)iterable).size());
            }
            super.addAll(iterable);
            return this;
        }
        
        @Override
        public Builder add(final Object o) {
            return this.add(o);
        }
    }
    
    public abstract static class Builder
    {
        static final int DEFAULT_INITIAL_CAPACITY = 4;
        
        static int expandedCapacity(final int n, final int n2) {
            if (n2 < 0) {
                throw new AssertionError((Object)"cannot store more than MAX_VALUE elements");
            }
            if (Integer.MAX_VALUE < n2) {
                final int n3 = Integer.highestOneBit(n2 - 1) << 1;
            }
            return Integer.MAX_VALUE;
        }
        
        Builder() {
        }
        
        public abstract Builder add(final Object p0);
        
        public Builder add(final Object... array) {
            while (0 < array.length) {
                this.add(array[0]);
                int n = 0;
                ++n;
            }
            return this;
        }
        
        public Builder addAll(final Iterable iterable) {
            final Iterator<Object> iterator = iterable.iterator();
            while (iterator.hasNext()) {
                this.add(iterator.next());
            }
            return this;
        }
        
        public Builder addAll(final Iterator iterator) {
            while (iterator.hasNext()) {
                this.add(iterator.next());
            }
            return this;
        }
        
        public abstract ImmutableCollection build();
    }
}
