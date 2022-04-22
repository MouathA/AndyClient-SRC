package com.google.common.collect;

import java.io.*;
import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible(serializable = true)
final class AllEqualOrdering extends Ordering implements Serializable
{
    static final AllEqualOrdering INSTANCE;
    private static final long serialVersionUID = 0L;
    
    @Override
    public int compare(@Nullable final Object o, @Nullable final Object o2) {
        return 0;
    }
    
    @Override
    public List sortedCopy(final Iterable iterable) {
        return Lists.newArrayList(iterable);
    }
    
    @Override
    public ImmutableList immutableSortedCopy(final Iterable iterable) {
        return ImmutableList.copyOf(iterable);
    }
    
    @Override
    public Ordering reverse() {
        return this;
    }
    
    private Object readResolve() {
        return AllEqualOrdering.INSTANCE;
    }
    
    @Override
    public String toString() {
        return "Ordering.allEqual()";
    }
    
    static {
        INSTANCE = new AllEqualOrdering();
    }
}
