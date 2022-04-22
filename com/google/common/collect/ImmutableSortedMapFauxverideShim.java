package com.google.common.collect;

abstract class ImmutableSortedMapFauxverideShim extends ImmutableMap
{
    @Deprecated
    public static ImmutableSortedMap.Builder builder() {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedMap of(final Object o, final Object o2) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedMap of(final Object o, final Object o2, final Object o3, final Object o4) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedMap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedMap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8) {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    public static ImmutableSortedMap of(final Object o, final Object o2, final Object o3, final Object o4, final Object o5, final Object o6, final Object o7, final Object o8, final Object o9, final Object o10) {
        throw new UnsupportedOperationException();
    }
}
