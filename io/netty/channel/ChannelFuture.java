package io.netty.channel;

import io.netty.util.concurrent.*;

public interface ChannelFuture extends Future
{
    Channel channel();
    
    ChannelFuture addListener(final GenericFutureListener p0);
    
    ChannelFuture addListeners(final GenericFutureListener... p0);
    
    ChannelFuture removeListener(final GenericFutureListener p0);
    
    ChannelFuture removeListeners(final GenericFutureListener... p0);
    
    ChannelFuture sync() throws InterruptedException;
    
    ChannelFuture syncUninterruptibly();
    
    ChannelFuture await() throws InterruptedException;
    
    ChannelFuture awaitUninterruptibly();
}
