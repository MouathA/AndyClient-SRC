package io.netty.channel;

import io.netty.util.concurrent.*;

public interface ChannelPromise extends ChannelFuture, Promise
{
    Channel channel();
    
    ChannelPromise setSuccess(final Void p0);
    
    ChannelPromise setSuccess();
    
    boolean trySuccess();
    
    ChannelPromise setFailure(final Throwable p0);
    
    ChannelPromise addListener(final GenericFutureListener p0);
    
    ChannelPromise addListeners(final GenericFutureListener... p0);
    
    ChannelPromise removeListener(final GenericFutureListener p0);
    
    ChannelPromise removeListeners(final GenericFutureListener... p0);
    
    ChannelPromise sync() throws InterruptedException;
    
    ChannelPromise syncUninterruptibly();
    
    ChannelPromise await() throws InterruptedException;
    
    ChannelPromise awaitUninterruptibly();
}
