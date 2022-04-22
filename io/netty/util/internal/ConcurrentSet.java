package io.netty.util.internal;

import java.io.*;
import java.util.concurrent.*;
import java.util.*;

public final class ConcurrentSet extends AbstractSet implements Serializable
{
    private static final long serialVersionUID = -6761513279741915432L;
    private final ConcurrentMap map;
    
    public ConcurrentSet() {
        this.map = PlatformDependent.newConcurrentHashMap();
    }
    
    @Override
    public int size() {
        return this.map.size();
    }
    
    @Override
    public boolean contains(final Object o) {
        return this.map.containsKey(o);
    }
    
    @Override
    public boolean add(final Object o) {
        return this.map.putIfAbsent(o, Boolean.TRUE) == null;
    }
    
    @Override
    public boolean remove(final Object o) {
        return this.map.remove(o) != null;
    }
    
    @Override
    public void clear() {
        this.map.clear();
    }
    
    @Override
    public Iterator iterator() {
        return this.map.keySet().iterator();
    }
}
