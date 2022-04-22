package io.netty.util.concurrent;

public interface ProgressivePromise extends Promise, ProgressiveFuture
{
    ProgressivePromise setProgress(final long p0, final long p1);
    
    boolean tryProgress(final long p0, final long p1);
    
    ProgressivePromise setSuccess(final Object p0);
    
    ProgressivePromise setFailure(final Throwable p0);
    
    ProgressivePromise addListener(final GenericFutureListener p0);
    
    ProgressivePromise addListeners(final GenericFutureListener... p0);
    
    ProgressivePromise removeListener(final GenericFutureListener p0);
    
    ProgressivePromise removeListeners(final GenericFutureListener... p0);
    
    ProgressivePromise await() throws InterruptedException;
    
    ProgressivePromise awaitUninterruptibly();
    
    ProgressivePromise sync() throws InterruptedException;
    
    ProgressivePromise syncUninterruptibly();
}
