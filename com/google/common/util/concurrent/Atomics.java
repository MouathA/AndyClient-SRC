package com.google.common.util.concurrent;

import javax.annotation.*;
import java.util.concurrent.atomic.*;

public final class Atomics
{
    private Atomics() {
    }
    
    public static AtomicReference newReference() {
        return new AtomicReference();
    }
    
    public static AtomicReference newReference(@Nullable final Object o) {
        return new AtomicReference((V)o);
    }
    
    public static AtomicReferenceArray newReferenceArray(final int n) {
        return new AtomicReferenceArray(n);
    }
    
    public static AtomicReferenceArray newReferenceArray(final Object[] array) {
        return new AtomicReferenceArray((E[])array);
    }
}
