package io.netty.util.concurrent;

import java.util.concurrent.*;

public interface Future extends java.util.concurrent.Future
{
    boolean isSuccess();
    
    boolean isCancellable();
    
    Throwable cause();
    
    Future addListener(final GenericFutureListener p0);
    
    Future addListeners(final GenericFutureListener... p0);
    
    Future removeListener(final GenericFutureListener p0);
    
    Future removeListeners(final GenericFutureListener... p0);
    
    Future sync() throws InterruptedException;
    
    Future syncUninterruptibly();
    
    Future await() throws InterruptedException;
    
    Future awaitUninterruptibly();
    
    boolean await(final long p0, final TimeUnit p1) throws InterruptedException;
    
    boolean await(final long p0) throws InterruptedException;
    
    boolean awaitUninterruptibly(final long p0, final TimeUnit p1);
    
    boolean awaitUninterruptibly(final long p0);
    
    Object getNow();
    
    boolean cancel(final boolean p0);
}
