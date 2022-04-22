package com.google.common.util.concurrent;

import com.google.common.annotations.*;
import java.util.concurrent.*;

@Beta
public interface TimeLimiter
{
    Object newProxy(final Object p0, final Class p1, final long p2, final TimeUnit p3);
    
    Object callWithTimeout(final Callable p0, final long p1, final TimeUnit p2, final boolean p3) throws Exception;
}
