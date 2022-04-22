package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import com.google.common.base.*;
import java.util.concurrent.*;

@Beta
public final class FakeTimeLimiter implements TimeLimiter
{
    @Override
    public Object newProxy(final Object o, final Class clazz, final long n, final TimeUnit timeUnit) {
        Preconditions.checkNotNull(o);
        Preconditions.checkNotNull(clazz);
        Preconditions.checkNotNull(timeUnit);
        return o;
    }
    
    @Override
    public Object callWithTimeout(final Callable callable, final long n, final TimeUnit timeUnit, final boolean b) throws Exception {
        Preconditions.checkNotNull(timeUnit);
        return callable.call();
    }
}
