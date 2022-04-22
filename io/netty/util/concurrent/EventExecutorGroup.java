package io.netty.util.concurrent;

import java.util.*;
import java.util.concurrent.*;

public interface EventExecutorGroup extends ScheduledExecutorService, Iterable
{
    boolean isShuttingDown();
    
    Future shutdownGracefully();
    
    Future shutdownGracefully(final long p0, final long p1, final TimeUnit p2);
    
    Future terminationFuture();
    
    @Deprecated
    void shutdown();
    
    @Deprecated
    List shutdownNow();
    
    EventExecutor next();
    
    Iterator iterator();
    
    Future submit(final Runnable p0);
    
    Future submit(final Runnable p0, final Object p1);
    
    Future submit(final Callable p0);
    
    ScheduledFuture schedule(final Runnable p0, final long p1, final TimeUnit p2);
    
    ScheduledFuture schedule(final Callable p0, final long p1, final TimeUnit p2);
    
    ScheduledFuture scheduleAtFixedRate(final Runnable p0, final long p1, final long p2, final TimeUnit p3);
    
    ScheduledFuture scheduleWithFixedDelay(final Runnable p0, final long p1, final long p2, final TimeUnit p3);
}
