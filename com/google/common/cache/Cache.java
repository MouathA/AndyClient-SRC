package com.google.common.cache;

import com.google.common.annotations.*;
import javax.annotation.*;
import com.google.common.collect.*;
import java.util.*;
import java.util.concurrent.*;

@Beta
@GwtCompatible
public interface Cache
{
    @Nullable
    Object getIfPresent(final Object p0);
    
    Object get(final Object p0, final Callable p1) throws ExecutionException;
    
    ImmutableMap getAllPresent(final Iterable p0);
    
    void put(final Object p0, final Object p1);
    
    void putAll(final Map p0);
    
    void invalidate(final Object p0);
    
    void invalidateAll(final Iterable p0);
    
    void invalidateAll();
    
    long size();
    
    CacheStats stats();
    
    ConcurrentMap asMap();
    
    void cleanUp();
}
