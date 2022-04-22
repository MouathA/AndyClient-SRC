package com.google.common.cache;

import com.google.common.base.*;
import com.google.common.annotations.*;
import com.google.common.collect.*;
import java.util.concurrent.*;

@Beta
@GwtCompatible
public interface LoadingCache extends Cache, Function
{
    Object get(final Object p0) throws ExecutionException;
    
    Object getUnchecked(final Object p0);
    
    ImmutableMap getAll(final Iterable p0) throws ExecutionException;
    
    @Deprecated
    Object apply(final Object p0);
    
    void refresh(final Object p0);
    
    ConcurrentMap asMap();
}
