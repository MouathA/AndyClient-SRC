package io.netty.util.concurrent;

public interface Promise extends Future
{
    Promise setSuccess(final Object p0);
    
    boolean trySuccess(final Object p0);
    
    Promise setFailure(final Throwable p0);
    
    boolean tryFailure(final Throwable p0);
    
    boolean setUncancellable();
    
    Promise addListener(final GenericFutureListener p0);
    
    Promise addListeners(final GenericFutureListener... p0);
    
    Promise removeListener(final GenericFutureListener p0);
    
    Promise removeListeners(final GenericFutureListener... p0);
    
    Promise await() throws InterruptedException;
    
    Promise awaitUninterruptibly();
    
    Promise sync() throws InterruptedException;
    
    Promise syncUninterruptibly();
}
