package com.viaversion.viaversion.libs.flare;

import java.util.concurrent.*;
import java.util.*;
import java.util.function.*;

public interface SyncMap extends ConcurrentMap
{
    default SyncMap hashmap() {
        return of(HashMap::new, 16);
    }
    
    default SyncMap hashmap(final int initialCapacity) {
        return of(HashMap::new, initialCapacity);
    }
    
    default Set hashset() {
        return setOf(HashMap::new, 16);
    }
    
    default Set hashset(final int initialCapacity) {
        return setOf(HashMap::new, initialCapacity);
    }
    
    default SyncMap of(final IntFunction function, final int initialCapacity) {
        return new SyncMapImpl(function, initialCapacity);
    }
    
    default Set setOf(final IntFunction function, final int initialCapacity) {
        return Collections.newSetFromMap((Map<Object, Boolean>)new SyncMapImpl(function, initialCapacity));
    }
    
    Set entrySet();
    
    int size();
    
    void clear();
    
    public interface InsertionResult
    {
        byte operation();
        
        Object previous();
        
        Object current();
    }
    
    public interface ExpungingEntry
    {
        boolean exists();
        
        Object get();
        
        Object getOr(final Object other);
        
        InsertionResult setIfAbsent(final Object value);
        
        InsertionResult computeIfAbsent(final Object key, final Function function);
        
        InsertionResult computeIfPresent(final Object key, final BiFunction remappingFunction);
        
        InsertionResult compute(final Object key, final BiFunction remappingFunction);
        
        void set(final Object value);
        
        boolean replace(final Object compare, final Object value);
        
        Object clear();
        
        boolean trySet(final Object value);
        
        Object tryReplace(final Object value);
        
        boolean tryExpunge();
        
        boolean tryUnexpungeAndSet(final Object value);
        
        boolean tryUnexpungeAndCompute(final Object key, final Function function);
        
        boolean tryUnexpungeAndCompute(final Object key, final BiFunction remappingFunction);
    }
}
