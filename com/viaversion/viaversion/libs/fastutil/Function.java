package com.viaversion.viaversion.libs.fastutil;

@FunctionalInterface
public interface Function extends java.util.function.Function
{
    default Object apply(final Object o) {
        return this.get(o);
    }
    
    default Object put(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    Object get(final Object p0);
    
    default Object getOrDefault(final Object o, final Object o2) {
        final Object value = this.get(o);
        return (value != null || this.containsKey(o)) ? value : o2;
    }
    
    default boolean containsKey(final Object o) {
        return true;
    }
    
    default Object remove(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    default int size() {
        return -1;
    }
    
    default void clear() {
        throw new UnsupportedOperationException();
    }
}
