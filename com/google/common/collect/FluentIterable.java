package com.google.common.collect;

import javax.annotation.*;
import com.google.common.base.*;
import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(emulated = true)
public abstract class FluentIterable implements Iterable
{
    private final Iterable iterable;
    
    protected FluentIterable() {
        this.iterable = this;
    }
    
    FluentIterable(final Iterable iterable) {
        this.iterable = (Iterable)Preconditions.checkNotNull(iterable);
    }
    
    public static FluentIterable from(final Iterable iterable) {
        return (iterable instanceof FluentIterable) ? ((FluentIterable)iterable) : new FluentIterable(iterable, iterable) {
            final Iterable val$iterable;
            
            @Override
            public Iterator iterator() {
                return this.val$iterable.iterator();
            }
        };
    }
    
    @Deprecated
    public static FluentIterable from(final FluentIterable fluentIterable) {
        return (FluentIterable)Preconditions.checkNotNull(fluentIterable);
    }
    
    @Override
    public String toString() {
        return Iterables.toString(this.iterable);
    }
    
    public final int size() {
        return Iterables.size(this.iterable);
    }
    
    public final boolean contains(@Nullable final Object o) {
        return Iterables.contains(this.iterable, o);
    }
    
    @CheckReturnValue
    public final FluentIterable cycle() {
        return from(Iterables.cycle(this.iterable));
    }
    
    @CheckReturnValue
    public final FluentIterable filter(final Predicate predicate) {
        return from(Iterables.filter(this.iterable, predicate));
    }
    
    @CheckReturnValue
    @GwtIncompatible("Class.isInstance")
    public final FluentIterable filter(final Class clazz) {
        return from(Iterables.filter(this.iterable, clazz));
    }
    
    public final boolean anyMatch(final Predicate predicate) {
        return Iterables.any(this.iterable, predicate);
    }
    
    public final boolean allMatch(final Predicate predicate) {
        return Iterables.all(this.iterable, predicate);
    }
    
    public final Optional firstMatch(final Predicate predicate) {
        return Iterables.tryFind(this.iterable, predicate);
    }
    
    public final FluentIterable transform(final Function function) {
        return from(Iterables.transform(this.iterable, function));
    }
    
    public FluentIterable transformAndConcat(final Function function) {
        return from(Iterables.concat(this.transform(function)));
    }
    
    public final Optional first() {
        final Iterator<Object> iterator = (Iterator<Object>)this.iterable.iterator();
        return iterator.hasNext() ? Optional.of(iterator.next()) : Optional.absent();
    }
    
    public final Optional last() {
        if (this.iterable instanceof List) {
            final List list = (List)this.iterable;
            if (list.isEmpty()) {
                return Optional.absent();
            }
            return Optional.of(list.get(list.size() - 1));
        }
        else {
            final Iterator<Object> iterator = (Iterator<Object>)this.iterable.iterator();
            if (!iterator.hasNext()) {
                return Optional.absent();
            }
            if (this.iterable instanceof SortedSet) {
                return Optional.of(((SortedSet)this.iterable).last());
            }
            Object next;
            do {
                next = iterator.next();
            } while (iterator.hasNext());
            return Optional.of(next);
        }
    }
    
    @CheckReturnValue
    public final FluentIterable skip(final int n) {
        return from(Iterables.skip(this.iterable, n));
    }
    
    @CheckReturnValue
    public final FluentIterable limit(final int n) {
        return from(Iterables.limit(this.iterable, n));
    }
    
    public final boolean isEmpty() {
        return !this.iterable.iterator().hasNext();
    }
    
    public final ImmutableList toList() {
        return ImmutableList.copyOf(this.iterable);
    }
    
    @Beta
    public final ImmutableList toSortedList(final Comparator comparator) {
        return Ordering.from(comparator).immutableSortedCopy(this.iterable);
    }
    
    public final ImmutableSet toSet() {
        return ImmutableSet.copyOf(this.iterable);
    }
    
    public final ImmutableSortedSet toSortedSet(final Comparator comparator) {
        return ImmutableSortedSet.copyOf(comparator, this.iterable);
    }
    
    public final ImmutableMap toMap(final Function function) {
        return Maps.toMap(this.iterable, function);
    }
    
    public final ImmutableListMultimap index(final Function function) {
        return Multimaps.index(this.iterable, function);
    }
    
    public final ImmutableMap uniqueIndex(final Function function) {
        return Maps.uniqueIndex(this.iterable, function);
    }
    
    @GwtIncompatible("Array.newArray(Class, int)")
    public final Object[] toArray(final Class clazz) {
        return Iterables.toArray(this.iterable, clazz);
    }
    
    public final Collection copyInto(final Collection collection) {
        Preconditions.checkNotNull(collection);
        if (this.iterable instanceof Collection) {
            collection.addAll(Collections2.cast(this.iterable));
        }
        else {
            final Iterator<Object> iterator = (Iterator<Object>)this.iterable.iterator();
            while (iterator.hasNext()) {
                collection.add(iterator.next());
            }
        }
        return collection;
    }
    
    public final Object get(final int n) {
        return Iterables.get(this.iterable, n);
    }
    
    private static class FromIterableFunction implements Function
    {
        public FluentIterable apply(final Iterable iterable) {
            return FluentIterable.from(iterable);
        }
        
        @Override
        public Object apply(final Object o) {
            return this.apply((Iterable)o);
        }
    }
}
