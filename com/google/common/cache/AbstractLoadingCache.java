package com.google.common.cache;

import com.google.common.annotations.*;
import com.google.common.collect.*;
import java.util.*;
import java.util.concurrent.*;

@Beta
public abstract class AbstractLoadingCache extends AbstractCache implements LoadingCache
{
    protected AbstractLoadingCache() {
    }
    
    @Override
    public Object getUnchecked(final Object o) {
        return this.get(o);
    }
    
    @Override
    public ImmutableMap getAll(final Iterable iterable) throws ExecutionException {
        final LinkedHashMap linkedHashMap = Maps.newLinkedHashMap();
        for (final Object next : iterable) {
            if (!linkedHashMap.containsKey(next)) {
                linkedHashMap.put(next, this.get(next));
            }
        }
        return ImmutableMap.copyOf(linkedHashMap);
    }
    
    @Override
    public final Object apply(final Object o) {
        return this.getUnchecked(o);
    }
    
    @Override
    public void refresh(final Object o) {
        throw new UnsupportedOperationException();
    }
}
