package com.google.common.base;

import com.google.common.annotations.*;
import java.lang.ref.*;

@GwtCompatible(emulated = true)
final class Platform
{
    private Platform() {
    }
    
    static long systemNanoTime() {
        return System.nanoTime();
    }
    
    static CharMatcher precomputeCharMatcher(final CharMatcher charMatcher) {
        return charMatcher.precomputedInternal();
    }
    
    static Optional getEnumIfPresent(final Class clazz, final String s) {
        final WeakReference<Object> weakReference = Enums.getEnumConstants(clazz).get(s);
        return (weakReference == null) ? Optional.absent() : Optional.of(clazz.cast(weakReference.get()));
    }
}
