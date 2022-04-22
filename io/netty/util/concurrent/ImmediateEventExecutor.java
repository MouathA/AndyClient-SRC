package io.netty.util.concurrent;

import java.util.concurrent.*;

public final class ImmediateEventExecutor extends AbstractEventExecutor
{
    public static final ImmediateEventExecutor INSTANCE;
    private final Future terminationFuture;
    
    private ImmediateEventExecutor() {
        this.terminationFuture = new FailedFuture(GlobalEventExecutor.INSTANCE, new UnsupportedOperationException());
    }
    
    @Override
    public EventExecutorGroup parent() {
        return null;
    }
    
    @Override
    public boolean inEventLoop() {
        return true;
    }
    
    @Override
    public boolean inEventLoop(final Thread thread) {
        return true;
    }
    
    @Override
    public Future shutdownGracefully(final long n, final long n2, final TimeUnit timeUnit) {
        return this.terminationFuture();
    }
    
    @Override
    public Future terminationFuture() {
        return this.terminationFuture;
    }
    
    @Deprecated
    @Override
    public void shutdown() {
    }
    
    @Override
    public boolean isShuttingDown() {
        return false;
    }
    
    @Override
    public boolean isShutdown() {
        return false;
    }
    
    @Override
    public boolean isTerminated() {
        return false;
    }
    
    @Override
    public boolean awaitTermination(final long n, final TimeUnit timeUnit) {
        return false;
    }
    
    @Override
    public void execute(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("command");
        }
        runnable.run();
    }
    
    @Override
    public Promise newPromise() {
        return new ImmediatePromise(this);
    }
    
    @Override
    public ProgressivePromise newProgressivePromise() {
        return new ImmediateProgressivePromise(this);
    }
    
    static {
        INSTANCE = new ImmediateEventExecutor();
    }
    
    static class ImmediateProgressivePromise extends DefaultProgressivePromise
    {
        ImmediateProgressivePromise(final EventExecutor eventExecutor) {
            super(eventExecutor);
        }
        
        @Override
        protected void checkDeadLock() {
        }
    }
    
    static class ImmediatePromise extends DefaultPromise
    {
        ImmediatePromise(final EventExecutor eventExecutor) {
            super(eventExecutor);
        }
        
        @Override
        protected void checkDeadLock() {
        }
    }
}
