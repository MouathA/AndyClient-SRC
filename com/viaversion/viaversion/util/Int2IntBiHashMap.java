package com.viaversion.viaversion.util;

import com.google.common.base.*;
import com.viaversion.viaversion.libs.fastutil.objects.*;
import com.viaversion.viaversion.libs.fastutil.ints.*;
import java.util.*;

public class Int2IntBiHashMap implements Int2IntBiMap
{
    private final Int2IntMap map;
    private final Int2IntBiHashMap inverse;
    
    public Int2IntBiHashMap() {
        this.map = new Int2IntOpenHashMap();
        this.inverse = new Int2IntBiHashMap(this);
    }
    
    private Int2IntBiHashMap(final Int2IntBiHashMap inverse) {
        this.map = new Int2IntOpenHashMap();
        this.inverse = inverse;
    }
    
    @Override
    public Int2IntBiMap inverse() {
        return this.inverse;
    }
    
    @Override
    public int put(final int n, final int n2) {
        if (this.containsKey(n) && n2 == this.get(n)) {
            return n2;
        }
        Preconditions.checkArgument(!this.containsValue(n2), "value already present: %s", n2);
        this.map.put(n, n2);
        this.inverse.map.put(n2, n);
        return this.defaultReturnValue();
    }
    
    @Override
    public boolean remove(final int n, final int n2) {
        this.map.remove(n, n2);
        return this.inverse.map.remove(n, n2);
    }
    
    @Override
    public int get(final int n) {
        return this.map.get(n);
    }
    
    @Override
    public void clear() {
        this.map.clear();
        this.inverse.map.clear();
    }
    
    @Override
    public int size() {
        return this.map.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.map.isEmpty();
    }
    
    @Override
    public void defaultReturnValue(final int n) {
        this.map.defaultReturnValue(n);
        this.inverse.map.defaultReturnValue(n);
    }
    
    @Override
    public int defaultReturnValue() {
        return this.map.defaultReturnValue();
    }
    
    @Override
    public ObjectSet int2IntEntrySet() {
        return this.map.int2IntEntrySet();
    }
    
    @Override
    public IntSet keySet() {
        return this.map.keySet();
    }
    
    @Override
    public IntSet values() {
        return this.inverse.map.keySet();
    }
    
    @Override
    public boolean containsKey(final int n) {
        return this.map.containsKey(n);
    }
    
    @Override
    public boolean containsValue(final int n) {
        return this.inverse.map.containsKey(n);
    }
    
    @Override
    public IntCollection values() {
        return this.values();
    }
    
    @Override
    public Collection values() {
        return this.values();
    }
    
    @Override
    public Set keySet() {
        return this.keySet();
    }
}
