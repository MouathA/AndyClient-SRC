package io.netty.channel;

import java.util.concurrent.*;
import io.netty.util.concurrent.*;
import io.netty.util.internal.logging.*;
import io.netty.util.internal.*;

public abstract class MultithreadEventLoopGroup extends MultithreadEventExecutorGroup implements EventLoopGroup
{
    private static final InternalLogger logger;
    private static final int DEFAULT_EVENT_LOOP_THREADS;
    
    protected MultithreadEventLoopGroup(final int n, final ThreadFactory threadFactory, final Object... array) {
        super((n == 0) ? MultithreadEventLoopGroup.DEFAULT_EVENT_LOOP_THREADS : n, threadFactory, array);
    }
    
    @Override
    protected ThreadFactory newDefaultThreadFactory() {
        return new DefaultThreadFactory(this.getClass(), 10);
    }
    
    @Override
    public EventLoop next() {
        return (EventLoop)super.next();
    }
    
    @Override
    public ChannelFuture register(final Channel channel) {
        return this.next().register(channel);
    }
    
    @Override
    public ChannelFuture register(final Channel channel, final ChannelPromise channelPromise) {
        return this.next().register(channel, channelPromise);
    }
    
    @Override
    public EventExecutor next() {
        return this.next();
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(MultithreadEventLoopGroup.class);
        DEFAULT_EVENT_LOOP_THREADS = Math.max(1, SystemPropertyUtil.getInt("io.netty.eventLoopThreads", Runtime.getRuntime().availableProcessors() * 2));
        if (MultithreadEventLoopGroup.logger.isDebugEnabled()) {
            MultithreadEventLoopGroup.logger.debug("-Dio.netty.eventLoopThreads: {}", (Object)MultithreadEventLoopGroup.DEFAULT_EVENT_LOOP_THREADS);
        }
    }
}
