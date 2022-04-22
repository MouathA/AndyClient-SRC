package io.netty.util.concurrent;

public interface ProgressiveFuture extends Future
{
    ProgressiveFuture addListener(final GenericFutureListener p0);
    
    ProgressiveFuture addListeners(final GenericFutureListener... p0);
    
    ProgressiveFuture removeListener(final GenericFutureListener p0);
    
    ProgressiveFuture removeListeners(final GenericFutureListener... p0);
    
    ProgressiveFuture sync() throws InterruptedException;
    
    ProgressiveFuture syncUninterruptibly();
    
    ProgressiveFuture await() throws InterruptedException;
    
    ProgressiveFuture awaitUninterruptibly();
}
