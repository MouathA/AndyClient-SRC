package io.netty.util.concurrent;

public interface EventExecutor extends EventExecutorGroup
{
    EventExecutor next();
    
    EventExecutorGroup parent();
    
    boolean inEventLoop();
    
    boolean inEventLoop(final Thread p0);
    
    Promise newPromise();
    
    ProgressivePromise newProgressivePromise();
    
    Future newSucceededFuture(final Object p0);
    
    Future newFailedFuture(final Throwable p0);
}
