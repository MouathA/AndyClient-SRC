package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
final class FilteredKeyListMultimap extends FilteredKeyMultimap implements ListMultimap
{
    FilteredKeyListMultimap(final ListMultimap listMultimap, final Predicate predicate) {
        super(listMultimap, predicate);
    }
    
    @Override
    public ListMultimap unfiltered() {
        return (ListMultimap)super.unfiltered();
    }
    
    @Override
    public List get(final Object o) {
        return (List)super.get(o);
    }
    
    @Override
    public List removeAll(@Nullable final Object o) {
        return (List)super.removeAll(o);
    }
    
    @Override
    public List replaceValues(final Object o, final Iterable iterable) {
        return (List)super.replaceValues(o, iterable);
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
    public Multimap unfiltered() {
        return this.unfiltered();
    }
    
    @Override
    public Collection replaceValues(final Object o, final Iterable iterable) {
        return this.replaceValues(o, iterable);
    }
}
