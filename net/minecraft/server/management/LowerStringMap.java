package net.minecraft.server.management;

import com.google.common.collect.*;
import java.util.*;

public class LowerStringMap implements Map
{
    private final Map internalMap;
    private static final String __OBFID;
    
    public LowerStringMap() {
        this.internalMap = Maps.newLinkedHashMap();
    }
    
    @Override
    public int size() {
        return this.internalMap.size();
    }
    
    @Override
    public boolean isEmpty() {
        return this.internalMap.isEmpty();
    }
    
    @Override
    public boolean containsKey(final Object o) {
        return this.internalMap.containsKey(o.toString().toLowerCase());
    }
    
    @Override
    public boolean containsValue(final Object o) {
        return this.internalMap.containsKey(o);
    }
    
    @Override
    public Object get(final Object o) {
        return this.internalMap.get(o.toString().toLowerCase());
    }
    
    public Object put(final String s, final Object o) {
        return this.internalMap.put(s.toLowerCase(), o);
    }
    
    @Override
    public Object remove(final Object o) {
        return this.internalMap.remove(o.toString().toLowerCase());
    }
    
    @Override
    public void putAll(final Map map) {
        for (final Entry<String, V> entry : map.entrySet()) {
            this.put(entry.getKey(), entry.getValue());
        }
    }
    
    @Override
    public void clear() {
        this.internalMap.clear();
    }
    
    @Override
    public Set keySet() {
        return this.internalMap.keySet();
    }
    
    @Override
    public Collection values() {
        return this.internalMap.values();
    }
    
    @Override
    public Set entrySet() {
        return this.internalMap.entrySet();
    }
    
    @Override
    public Object put(final Object o, final Object o2) {
        return this.put((String)o, o2);
    }
    
    static {
        __OBFID = "CL_00001488";
    }
}
