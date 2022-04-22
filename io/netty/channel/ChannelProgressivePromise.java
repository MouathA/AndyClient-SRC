package io.netty.channel;

import io.netty.util.concurrent.*;

public interface ChannelProgressivePromise extends ProgressivePromise, ChannelProgressiveFuture, ChannelPromise
{
    ChannelProgressivePromise addListener(final GenericFutureListener p0);
    
    ChannelProgressivePromise addListeners(final GenericFutureListener... p0);
    
    ChannelProgressivePromise removeListener(final GenericFutureListener p0);
    
    ChannelProgressivePromise removeListeners(final GenericFutureListener... p0);
    
    ChannelProgressivePromise sync() throws InterruptedException;
    
    ChannelProgressivePromise syncUninterruptibly();
    
    ChannelProgressivePromise await() throws InterruptedException;
    
    ChannelProgressivePromise awaitUninterruptibly();
    
    ChannelProgressivePromise setSuccess(final Void p0);
    
    ChannelProgressivePromise setSuccess();
    
    ChannelProgressivePromise setFailure(final Throwable p0);
    
    ChannelProgressivePromise setProgress(final long p0, final long p1);
}
