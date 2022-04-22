package io.netty.util;

import java.util.concurrent.atomic.*;
import java.util.concurrent.*;

@Deprecated
public class UniqueName implements Comparable
{
    private static final AtomicInteger nextId;
    private final int id;
    private final String name;
    
    public UniqueName(final ConcurrentMap concurrentMap, final String name, final Object... array) {
        if (concurrentMap == null) {
            throw new NullPointerException("map");
        }
        if (name == null) {
            throw new NullPointerException("name");
        }
        if (array != null && array.length > 0) {
            this.validateArgs(array);
        }
        if (concurrentMap.putIfAbsent(name, Boolean.TRUE) != null) {
            throw new IllegalArgumentException(String.format("'%s' is already in use", name));
        }
        this.id = UniqueName.nextId.incrementAndGet();
        this.name = name;
    }
    
    protected void validateArgs(final Object... array) {
    }
    
    public final String name() {
        return this.name;
    }
    
    public final int id() {
        return this.id;
    }
    
    @Override
    public final int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public final boolean equals(final Object o) {
        return super.equals(o);
    }
    
    public int compareTo(final UniqueName uniqueName) {
        if (this == uniqueName) {
            return 0;
        }
        final int compareTo = this.name.compareTo(uniqueName.name);
        if (compareTo != 0) {
            return compareTo;
        }
        return Integer.valueOf(this.id).compareTo(uniqueName.id);
    }
    
    @Override
    public String toString() {
        return this.name();
    }
    
    @Override
    public int compareTo(final Object o) {
        return this.compareTo((UniqueName)o);
    }
    
    static {
        nextId = new AtomicInteger();
    }
}
