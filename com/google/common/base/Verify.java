package com.google.common.base;

import com.google.common.annotations.*;
import javax.annotation.*;

@Beta
@GwtCompatible
public final class Verify
{
    public static void verify(final boolean b) {
        if (!b) {
            throw new VerifyException();
        }
    }
    
    public static void verify(final boolean b, @Nullable final String s, @Nullable final Object... array) {
        if (!b) {
            throw new VerifyException(Preconditions.format(s, array));
        }
    }
    
    public static Object verifyNotNull(@Nullable final Object o) {
        return verifyNotNull(o, "expected a non-null reference", new Object[0]);
    }
    
    public static Object verifyNotNull(@Nullable final Object o, @Nullable final String s, @Nullable final Object... array) {
        verify(o != null, s, array);
        return o;
    }
    
    private Verify() {
    }
}
