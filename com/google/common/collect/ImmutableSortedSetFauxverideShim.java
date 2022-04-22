package com.google.common.collect;

abstract class ImmutableSortedSetFauxverideShim extends ImmutableSet
{
    @Deprecated
    public static ImmutableSortedSet.Builder builder() {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedSet of(final Object o) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedSet of(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedSet of(final Object o, final Object o2, final Object o3) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedSet of(final Object o, final Object o2, final Object o3, final Object o4) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedSet of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedSet of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object... array) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedSet copyOf(final Object[] array) {
        throw new UnsupportedOperationException();
    }
}
