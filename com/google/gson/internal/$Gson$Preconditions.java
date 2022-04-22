package com.google.gson.internal;

public final class $Gson$Preconditions
{
    public static Object checkNotNull(final Object o) {
        if (o == null) {
            throw new NullPointerException();
        }
        return o;
    }
    
    public static void checkArgument(final boolean b) {
        if (!b) {
            throw new IllegalArgumentException();
        }
    }
}
