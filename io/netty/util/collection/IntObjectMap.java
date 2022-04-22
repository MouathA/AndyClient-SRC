package io.netty.util.collection;

public interface IntObjectMap
{
    Object get(final int p0);
    
    Object put(final int p0, final Object p1);
    
    void putAll(final IntObjectMap p0);
    
    Object remove(final int p0);
    
    int size();
    
    boolean isEmpty();
    
    void clear();
    
    boolean containsKey(final int p0);
    
    boolean containsValue(final Object p0);
    
    Iterable entries();
    
    int[] keys();
    
    Object[] values(final Class p0);
    
    public interface Entry
    {
        int key();
        
        Object value();
        
        void setValue(final Object p0);
    }
}
