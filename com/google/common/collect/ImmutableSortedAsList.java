package com.google.common.collect;

import java.util.*;
import javax.annotation.*;
import com.google.common.annotations.*;

@GwtCompatible(emulated = true)
final class ImmutableSortedAsList extends RegularImmutableAsList implements SortedIterable
{
    ImmutableSortedAsList(final ImmutableSortedSet set, final ImmutableList list) {
        super(set, list);
    }
    
    @Override
    ImmutableSortedSet delegateCollection() {
        return (ImmutableSortedSet)super.delegateCollection();
    }
    
    @Override
    public Comparator comparator() {
        return this.delegateCollection().comparator();
    }
    
    @GwtIncompatible("ImmutableSortedSet.indexOf")
    @Override
    public int indexOf(@Nullable final Object o) {
        final int index = this.delegateCollection().indexOf(o);
        return (index >= 0 && this.get(index).equals(o)) ? index : -1;
    }
    
    @GwtIncompatible("ImmutableSortedSet.indexOf")
    @Override
    public int lastIndexOf(@Nullable final Object o) {
        return this.indexOf(o);
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.indexOf(o) >= 0;
    }
    
    @GwtIncompatible("super.subListUnchecked does not exist; inherited subList is valid if slow")
    @Override
    ImmutableList subListUnchecked(final int n, final int n2) {
        return new RegularImmutableSortedSet(super.subListUnchecked(n, n2), this.comparator()).asList();
    }
    
    @Override
    ImmutableCollection delegateCollection() {
        return this.delegateCollection();
    }
}
