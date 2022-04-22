package com.google.common.collect;

import com.google.common.annotations.*;
import java.util.*;

@GwtCompatible(emulated = true)
class RegularImmutableAsList extends ImmutableAsList
{
    private final ImmutableCollection delegate;
    private final ImmutableList delegateList;
    
    RegularImmutableAsList(final ImmutableCollection delegate, final ImmutableList delegateList) {
        this.delegate = delegate;
        this.delegateList = delegateList;
    }
    
    RegularImmutableAsList(final ImmutableCollection collection, final Object[] array) {
        this(collection, ImmutableList.asImmutableList(array));
    }
    
    @Override
    ImmutableCollection delegateCollection() {
        return this.delegate;
    }
    
    ImmutableList delegateList() {
        return this.delegateList;
    }
    
    @Override
    public UnmodifiableListIterator listIterator(final int n) {
        return this.delegateList.listIterator(n);
    }
    
    @GwtIncompatible("not present in emulated superclass")
    @Override
    int copyIntoArray(final Object[] array, final int n) {
        return this.delegateList.copyIntoArray(array, n);
    }
    
    @Override
    public Object get(final int n) {
        return this.delegateList.get(n);
    }
    
    @Override
    public ListIterator listIterator(final int n) {
        return this.listIterator(n);
    }
}
