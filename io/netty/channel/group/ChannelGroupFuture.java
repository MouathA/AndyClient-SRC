package io.netty.channel.group;

import io.netty.channel.*;
import io.netty.util.concurrent.*;
import java.util.*;

public interface ChannelGroupFuture extends Future, Iterable
{
    ChannelGroup group();
    
    ChannelFuture find(final Channel p0);
    
    boolean isSuccess();
    
    ChannelGroupException cause();
    
    boolean isPartialSuccess();
    
    boolean isPartialFailure();
    
    ChannelGroupFuture addListener(final GenericFutureListener p0);
    
    ChannelGroupFuture addListeners(final GenericFutureListener... p0);
    
    ChannelGroupFuture removeListener(final GenericFutureListener p0);
    
    ChannelGroupFuture removeListeners(final GenericFutureListener... p0);
    
    ChannelGroupFuture await() throws InterruptedException;
    
    ChannelGroupFuture awaitUninterruptibly();
    
    ChannelGroupFuture syncUninterruptibly();
    
    ChannelGroupFuture sync() throws InterruptedException;
    
    Iterator iterator();
}
