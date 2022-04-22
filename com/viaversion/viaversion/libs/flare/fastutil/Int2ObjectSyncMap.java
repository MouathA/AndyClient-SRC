package com.viaversion.viaversion.libs.flare.fastutil;

import com.viaversion.viaversion.libs.fastutil.objects.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.function.*;

public interface Int2ObjectSyncMap extends Int2ObjectMap
{
    default Int2ObjectSyncMap hashmap() {
        return of(Int2ObjectOpenHashMap::new, 16);
    }
    
    default Int2ObjectSyncMap hashmap(final int initialCapacity) {
        return of(Int2ObjectOpenHashMap::new, initialCapacity);
    }
    
    default IntSet hashset() {
        return setOf(Int2ObjectOpenHashMap::new, 16);
    }
    
    default IntSet hashset(final int initialCapacity) {
        return setOf(Int2ObjectOpenHashMap::new, initialCapacity);
    }
    
    default Int2ObjectSyncMap of(final IntFunction function, final int initialCapacity) {
        return new Int2ObjectSyncMapImpl(function, initialCapacity);
    }
    
    default IntSet setOf(final IntFunction function, final int initialCapacity) {
        return new Int2ObjectSyncMapSet(new Int2ObjectSyncMapImpl(function, initialCapacity));
    }
    
    ObjectSet int2ObjectEntrySet();
    
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
        
        InsertionResult computeIfAbsent(final int key, final IntFunction function);
        
        InsertionResult computeIfAbsentPrimitive(final int key, final Int2ObjectFunction function);
        
        InsertionResult computeIfPresent(final int key, final BiFunction remappingFunction);
        
        InsertionResult compute(final int key, final BiFunction remappingFunction);
        
        void set(final Object value);
        
        boolean replace(final Object compare, final Object value);
        
        Object clear();
        
        boolean trySet(final Object value);
        
        Object tryReplace(final Object value);
        
        boolean tryExpunge();
        
        boolean tryUnexpungeAndSet(final Object value);
        
        boolean tryUnexpungeAndCompute(final int key, final IntFunction function);
        
        boolean tryUnexpungeAndComputePrimitive(final int key, final Int2ObjectFunction function);
        
        boolean tryUnexpungeAndCompute(final int key, final BiFunction remappingFunction);
    }
}
