package com.ibm.icu.impl;

public interface ICUCache
{
    public static final int SOFT = 0;
    public static final int WEAK = 1;
    public static final Object NULL = new Object();
    
    void clear();
    
    void put(final Object p0, final Object p1);
    
    Object get(final Object p0);
}
