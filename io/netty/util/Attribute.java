package io.netty.util;

public interface Attribute
{
    AttributeKey key();
    
    Object get();
    
    void set(final Object p0);
    
    Object getAndSet(final Object p0);
    
    Object setIfAbsent(final Object p0);
    
    Object getAndRemove();
    
    boolean compareAndSet(final Object p0, final Object p1);
    
    void remove();
}
