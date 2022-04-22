package com.ibm.icu.impl;

public abstract class CacheBase
{
    public abstract Object getInstance(final Object p0, final Object p1);
    
    protected abstract Object createInstance(final Object p0, final Object p1);
}
