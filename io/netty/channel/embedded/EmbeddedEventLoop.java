package io.netty.channel.embedded;

import java.util.*;
import java.util.concurrent.*;
import io.netty.channel.*;
import io.netty.util.concurrent.*;

final class EmbeddedEventLoop extends AbstractEventExecutor implements EventLoop
{
    private final Queue tasks;
    
    EmbeddedEventLoop() {
        this.tasks = new ArrayDeque(2);
    }
    
    @Override
    public void execute(final Runnable runnable) {
        if (runnable == null) {
            throw new NullPointerException("command");
        }
        this.tasks.add(runnable);
    }
    
    void runTasks() {
        while (true) {
            final Runnable runnable = this.tasks.poll();
            if (runnable == null) {
                break;
            }
            runnable.run();
        }
    }
    
    @Override
    public Future shutdownGracefully(final long n, final long n2, final TimeUnit timeUnit) {
        throw new UnsupportedOperationException();
    }
    
    @Override
    public Future terminationFuture() {
        throw new UnsupportedOperationException();
    }
    
    @Deprecated
    @Override
    public void shutdown() {
        throw new UnsupportedOperationException();
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
    public boolean awaitTermination(final long n, final TimeUnit timeUnit) throws InterruptedException {
        Thread.sleep(timeUnit.toMillis(n));
        return false;
    }
    
    @Override
    public ChannelFuture register(final Channel channel) {
        return this.register(channel, new DefaultChannelPromise(channel, this));
    }
    
    @Override
    public ChannelFuture register(final Channel channel, final ChannelPromise channelPromise) {
        channel.unsafe().register(this, channelPromise);
        return channelPromise;
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
    public EventLoop next() {
        return this;
    }
    
    @Override
    public EventLoopGroup parent() {
        return this;
    }
    
    @Override
    public EventExecutor next() {
        return this.next();
    }
    
    @Override
    public EventExecutorGroup parent() {
        return this.parent();
    }
}
