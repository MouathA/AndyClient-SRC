package com.viaversion.viaversion.libs.fastutil;

import java.util.*;

public interface BigList extends Collection, Size64
{
    Object get(final long p0);
    
    Object remove(final long p0);
    
    Object set(final long p0, final Object p1);
    
    void add(final long p0, final Object p1);
    
    void size(final long p0);
    
    boolean addAll(final long p0, final Collection p1);
    
    long indexOf(final Object p0);
    
    long lastIndexOf(final Object p0);
    
    BigListIterator listIterator();
    
    BigListIterator listIterator(final long p0);
    
    BigList subList(final long p0, final long p1);
    
    @Deprecated
    default int size() {
        return super.size();
    }
}
