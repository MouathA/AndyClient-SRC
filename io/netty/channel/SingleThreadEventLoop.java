package io.netty.channel;

import java.util.concurrent.*;
import io.netty.util.concurrent.*;

public abstract class SingleThreadEventLoop extends SingleThreadEventExecutor implements EventLoop
{
    protected SingleThreadEventLoop(final EventLoopGroup eventLoopGroup, final ThreadFactory threadFactory, final boolean b) {
        super(eventLoopGroup, threadFactory, b);
    }
    
    @Override
    public EventLoopGroup parent() {
        return (EventLoopGroup)super.parent();
    }
    
    @Override
    public EventLoop next() {
        return (EventLoop)super.next();
    }
    
    @Override
    public ChannelFuture register(final Channel channel) {
        return this.register(channel, new DefaultChannelPromise(channel, this));
    }
    
    @Override
    public ChannelFuture register(final Channel channel, final ChannelPromise channelPromise) {
        if (channel == null) {
            throw new NullPointerException("channel");
        }
        if (channelPromise == null) {
            throw new NullPointerException("promise");
        }
        channel.unsafe().register(this, channelPromise);
        return channelPromise;
    }
    
    @Override
    protected boolean wakesUpForTask(final Runnable runnable) {
        return !(runnable instanceof NonWakeupRunnable);
    }
    
    @Override
    public EventExecutorGroup parent() {
        return this.parent();
    }
    
    @Override
    public EventExecutor next() {
        return this.next();
    }
    
    interface NonWakeupRunnable extends Runnable
    {
    }
}
