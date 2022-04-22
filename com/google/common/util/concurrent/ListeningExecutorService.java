package com.google.common.util.concurrent;

import java.util.*;
import java.util.concurrent.*;

public interface ListeningExecutorService extends ExecutorService
{
    ListenableFuture submit(final Callable p0);
    
    ListenableFuture submit(final Runnable p0);
    
    ListenableFuture submit(final Runnable p0, final Object p1);
    
    List invokeAll(final Collection p0) throws InterruptedException;
    
    List invokeAll(final Collection p0, final long p1, final TimeUnit p2) throws InterruptedException;
}
