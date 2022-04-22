package com.google.common.collect;

import com.google.common.annotations.*;
import com.google.common.base.*;

@GwtCompatible
final class CollectPreconditions
{
    static void checkEntryNotNull(final Object o, final Object o2) {
        if (o == null) {
            throw new NullPointerException("null key in entry: null=" + o2);
        }
        if (o2 == null) {
            throw new NullPointerException("null value in entry: " + o + "=null");
        }
    }
    
    static int checkNonnegative(final int n, final String s) {
        if (n < 0) {
            throw new IllegalArgumentException(s + " cannot be negative but was: " + n);
        }
        return n;
    }
    
    static void checkRemove(final boolean b) {
        Preconditions.checkState(b, (Object)"no calls to next() since the last call to remove()");
    }
}
