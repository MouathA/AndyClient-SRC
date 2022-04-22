package com.google.common.collect;

import com.google.common.annotations.*;
import javax.annotation.*;
import java.util.*;

@GwtCompatible
public abstract class AbstractSequentialIterator extends UnmodifiableIterator
{
    private Object nextOrNull;
    
    protected AbstractSequentialIterator(@Nullable final Object nextOrNull) {
        this.nextOrNull = nextOrNull;
    }
    
    protected abstract Object computeNext(final Object p0);
    
    @Override
    public final boolean hasNext() {
        return this.nextOrNull != null;
    }
    
    @Override
    public final Object next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        final Object nextOrNull = this.nextOrNull;
        this.nextOrNull = this.computeNext(this.nextOrNull);
        return nextOrNull;
    }
}
