package com.google.common.collect;

import java.util.*;
import com.google.common.annotations.*;

@GwtCompatible
public abstract class UnmodifiableListIterator extends UnmodifiableIterator implements ListIterator
{
    protected UnmodifiableListIterator() {
    }
    
    @Deprecated
    @Override
    public final void add(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public final void set(final Object o) {
        throw new UnsupportedOperationException();
    }
}
