package io.netty.channel;

import io.netty.util.concurrent.*;

public interface ChannelProgressiveFuture extends ChannelFuture, ProgressiveFuture
{
    ChannelProgressiveFuture addListener(final GenericFutureListener p0);
    
    ChannelProgressiveFuture addListeners(final GenericFutureListener... p0);
    
    ChannelProgressiveFuture removeListener(final GenericFutureListener p0);
    
    ChannelProgressiveFuture removeListeners(final GenericFutureListener... p0);
    
    ChannelProgressiveFuture sync() throws InterruptedException;
    
    ChannelProgressiveFuture syncUninterruptibly();
    
    ChannelProgressiveFuture await() throws InterruptedException;
    
    ChannelProgressiveFuture awaitUninterruptibly();
}
