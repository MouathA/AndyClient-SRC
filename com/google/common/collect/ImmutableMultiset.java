package com.google.common.collect;

import com.google.common.primitives.*;
import javax.annotation.*;
import com.google.common.annotations.*;
import java.util.*;
import com.google.common.base.*;
import java.io.*;

@GwtCompatible(serializable = true, emulated = true)
public abstract class ImmutableMultiset extends ImmutableCollection implements Multiset
{
    private static final ImmutableMultiset EMPTY;
    private transient ImmutableSet entrySet;
    
    public static ImmutableMultiset of() {
        return ImmutableMultiset.EMPTY;
    }
    
    public static ImmutableMultiset of(final Object o) {
        return copyOfInternal(o);
    }
    
    public static ImmutableMultiset of(final Object o, final Object o2) {
        return copyOfInternal(o, o2);
    }
    
    public static ImmutableMultiset of(final Object o, final Object o2, final Object o3) {
        return copyOfInternal(o, o2, o3);
    }
    
    public static ImmutableMultiset of(final Object o, final Object o2, final Object o3, final Object o4) {
        return copyOfInternal(o, o2, o3, o4);
    }
    
    public static ImmutableMultiset of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5) {
        return copyOfInternal(o, o2, o3, o4, o5);
    }
    
    public static ImmutableMultiset of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object... array) {
        return new Builder().add(o).add(o2).add(o3).add(o4).add(o5).add(o6).add(array).build();
    }
    
    public static ImmutableMultiset copyOf(final Object[] array) {
        return copyOf(Arrays.asList(array));
    }
    
    public static ImmutableMultiset copyOf(final Iterable iterable) {
        if (iterable instanceof ImmutableMultiset) {
            final ImmutableMultiset immutableMultiset = (ImmutableMultiset)iterable;
            if (!immutableMultiset.isPartialView()) {
                return immutableMultiset;
            }
        }
        return copyOfInternal((iterable instanceof Multiset) ? Multisets.cast(iterable) : LinkedHashMultiset.create(iterable));
    }
    
    private static ImmutableMultiset copyOfInternal(final Object... array) {
        return copyOf(Arrays.asList(array));
    }
    
    private static ImmutableMultiset copyOfInternal(final Multiset multiset) {
        return copyFromEntries(multiset.entrySet());
    }
    
    static ImmutableMultiset copyFromEntries(final Collection collection) {
        long n = 0L;
        final ImmutableMap.Builder builder = ImmutableMap.builder();
        for (final Entry entry : collection) {
            final int count = entry.getCount();
            if (count > 0) {
                builder.put(entry.getElement(), count);
                n += count;
            }
        }
        if (n == 0L) {
            return of();
        }
        return new RegularImmutableMultiset(builder.build(), Ints.saturatedCast(n));
    }
    
    public static ImmutableMultiset copyOf(final Iterator iterator) {
        final LinkedHashMultiset create = LinkedHashMultiset.create();
        Iterators.addAll(create, iterator);
        return copyOfInternal(create);
    }
    
    ImmutableMultiset() {
    }
    
    @Override
    public UnmodifiableIterator iterator() {
        return new UnmodifiableIterator((Iterator)this.entrySet().iterator()) {
            int remaining;
            Object element;
            final Iterator val$entryIterator;
            final ImmutableMultiset this$0;
            
            @Override
            public boolean hasNext() {
                return this.remaining > 0 || this.val$entryIterator.hasNext();
            }
            
            @Override
            public Object next() {
                if (this.remaining <= 0) {
                    final Entry entry = this.val$entryIterator.next();
                    this.element = entry.getElement();
                    this.remaining = entry.getCount();
                }
                --this.remaining;
                return this.element;
            }
        };
    }
    
    @Override
    public boolean contains(@Nullable final Object o) {
        return this.count(o) > 0;
    }
    
    @Override
    public boolean containsAll(final Collection collection) {
        return this.elementSet().containsAll(collection);
    }
    
    @Deprecated
    @Override
    public final int add(final Object o, final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final int remove(final Object o, final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final int setCount(final Object o, final int n) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final boolean setCount(final Object o, final int n, final int n2) {
        throw new UnsupportedOperationException();
    }
    
    @GwtIncompatible("not present in emulated superclass")
    @Override
    int copyIntoArray(final Object[] array, int n) {
        for (final Entry entry : this.entrySet()) {
            Arrays.fill(array, n, n + entry.getCount(), entry.getElement());
            n += entry.getCount();
        }
        return n;
    }
    
    @Override
    public boolean equals(@Nullable final Object o) {
        return Multisets.equalsImpl(this, o);
    }
    
    @Override
    public int hashCode() {
        return Sets.hashCodeImpl(this.entrySet());
    }
    
    @Override
    public String toString() {
        return this.entrySet().toString();
    }
    
    @Override
    public ImmutableSet entrySet() {
        final ImmutableSet entrySet = this.entrySet;
        return (entrySet == null) ? (this.entrySet = this.createEntrySet()) : entrySet;
    }
    
    private final ImmutableSet createEntrySet() {
        return this.isEmpty() ? ImmutableSet.of() : new EntrySet(null);
    }
    
    abstract Entry getEntry(final int p0);
    
    @Override
    Object writeReplace() {
        return new SerializedForm(this);
    }
    
    public static Builder builder() {
        return new Builder();
    }
    
    @Override
    public Iterator iterator() {
        return this.iterator();
    }
    
    @Override
    public Set entrySet() {
        return this.entrySet();
    }
    
    static {
        EMPTY = new RegularImmutableMultiset(ImmutableMap.of(), 0);
    }
    
    public static class Builder extends ImmutableCollection.Builder
    {
        final Multiset contents;
        
        public Builder() {
            this(LinkedHashMultiset.create());
        }
        
        Builder(final Multiset contents) {
            this.contents = contents;
        }
        
        @Override
        public Builder add(final Object o) {
            this.contents.add(Preconditions.checkNotNull(o));
            return this;
        }
        
        public Builder addCopies(final Object o, final int n) {
            this.contents.add(Preconditions.checkNotNull(o), n);
            return this;
        }
        
        public Builder setCount(final Object o, final int n) {
            this.contents.setCount(Preconditions.checkNotNull(o), n);
            return this;
        }
        
        @Override
        public Builder add(final Object... array) {
            super.add(array);
            return this;
        }
        
        @Override
        public Builder addAll(final Iterable iterable) {
            if (iterable instanceof Multiset) {
                for (final Entry entry : Multisets.cast(iterable).entrySet()) {
                    this.addCopies(entry.getElement(), entry.getCount());
                }
            }
            else {
                super.addAll(iterable);
            }
            return this;
        }
        
        @Override
        public Builder addAll(final Iterator iterator) {
            super.addAll(iterator);
            return this;
        }
        
        @Override
        public ImmutableMultiset build() {
            return ImmutableMultiset.copyOf(this.contents);
        }
        
        @Override
        public ImmutableCollection build() {
            return this.build();
        }
        
        @Override
        public ImmutableCollection.Builder addAll(final Iterator iterator) {
            return this.addAll(iterator);
        }
        
        @Override
        public ImmutableCollection.Builder addAll(final Iterable iterable) {
            return this.addAll(iterable);
        }
        
        @Override
        public ImmutableCollection.Builder add(final Object[] array) {
            return this.add(array);
        }
        
        @Override
        public ImmutableCollection.Builder add(final Object o) {
            return this.add(o);
        }
    }
    
    private static class SerializedForm implements Serializable
    {
        final Object[] elements;
        final int[] counts;
        private static final long serialVersionUID = 0L;
        
        SerializedForm(final Multiset multiset) {
            final int size = multiset.entrySet().size();
            this.elements = new Object[size];
            this.counts = new int[size];
            for (final Entry entry : multiset.entrySet()) {
                this.elements[0] = entry.getElement();
                this.counts[0] = entry.getCount();
                int n = 0;
                ++n;
            }
        }
        
        Object readResolve() {
            final LinkedHashMultiset create = LinkedHashMultiset.create(this.elements.length);
            while (0 < this.elements.length) {
                create.add(this.elements[0], this.counts[0]);
                int n = 0;
                ++n;
            }
            return ImmutableMultiset.copyOf(create);
        }
    }
    
    static class EntrySetSerializedForm implements Serializable
    {
        final ImmutableMultiset multiset;
        
        EntrySetSerializedForm(final ImmutableMultiset multiset) {
            this.multiset = multiset;
        }
        
        Object readResolve() {
            return this.multiset.entrySet();
        }
    }
    
    private final class EntrySet extends ImmutableSet
    {
        private static final long serialVersionUID = 0L;
        final ImmutableMultiset this$0;
        
        private EntrySet(final ImmutableMultiset this$0) {
            this.this$0 = this$0;
        }
        
        @Override
        boolean isPartialView() {
            return this.this$0.isPartialView();
        }
        
        @Override
        public UnmodifiableIterator iterator() {
            return this.asList().iterator();
        }
        
        @Override
        ImmutableList createAsList() {
            return new ImmutableAsList() {
                final EntrySet this$1;
                
                @Override
                public Entry get(final int n) {
                    return this.this$1.this$0.getEntry(n);
                }
                
                @Override
                ImmutableCollection delegateCollection() {
                    return this.this$1;
                }
                
                @Override
                public Object get(final int n) {
                    return this.get(n);
                }
            };
        }
        
        @Override
        public int size() {
            return this.this$0.elementSet().size();
        }
        
        @Override
        public boolean contains(final Object o) {
            if (o instanceof Entry) {
                final Entry entry = (Entry)o;
                return entry.getCount() > 0 && this.this$0.count(entry.getElement()) == entry.getCount();
            }
            return false;
        }
        
        @Override
        public int hashCode() {
            return this.this$0.hashCode();
        }
        
        @Override
        Object writeReplace() {
            return new EntrySetSerializedForm(this.this$0);
        }
        
        @Override
        public Iterator iterator() {
            return this.iterator();
        }
        
        EntrySet(final ImmutableMultiset immutableMultiset, final ImmutableMultiset$1 unmodifiableIterator) {
            this(immutableMultiset);
        }
    }
}
