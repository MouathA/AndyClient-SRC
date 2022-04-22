package com.viaversion.viaversion.libs.gson.internal;

public final class $Gson$Preconditions
{
    private $Gson$Preconditions() {
        throw new UnsupportedOperationException();
    }
    
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
