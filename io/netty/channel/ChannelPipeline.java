package io.netty.channel;

import io.netty.util.concurrent.*;
import java.util.*;
import java.net.*;

public interface ChannelPipeline extends Iterable
{
    ChannelPipeline addFirst(final String p0, final ChannelHandler p1);
    
    ChannelPipeline addFirst(final EventExecutorGroup p0, final String p1, final ChannelHandler p2);
    
    ChannelPipeline addLast(final String p0, final ChannelHandler p1);
    
    ChannelPipeline addLast(final EventExecutorGroup p0, final String p1, final ChannelHandler p2);
    
    ChannelPipeline addBefore(final String p0, final String p1, final ChannelHandler p2);
    
    ChannelPipeline addBefore(final EventExecutorGroup p0, final String p1, final String p2, final ChannelHandler p3);
    
    ChannelPipeline addAfter(final String p0, final String p1, final ChannelHandler p2);
    
    ChannelPipeline addAfter(final EventExecutorGroup p0, final String p1, final String p2, final ChannelHandler p3);
    
    ChannelPipeline addFirst(final ChannelHandler... p0);
    
    ChannelPipeline addFirst(final EventExecutorGroup p0, final ChannelHandler... p1);
    
    ChannelPipeline addLast(final ChannelHandler... p0);
    
    ChannelPipeline addLast(final EventExecutorGroup p0, final ChannelHandler... p1);
    
    ChannelPipeline remove(final ChannelHandler p0);
    
    ChannelHandler remove(final String p0);
    
    ChannelHandler remove(final Class p0);
    
    ChannelHandler removeFirst();
    
    ChannelHandler removeLast();
    
    ChannelPipeline replace(final ChannelHandler p0, final String p1, final ChannelHandler p2);
    
    ChannelHandler replace(final String p0, final String p1, final ChannelHandler p2);
    
    ChannelHandler replace(final Class p0, final String p1, final ChannelHandler p2);
    
    ChannelHandler first();
    
    ChannelHandlerContext firstContext();
    
    ChannelHandler last();
    
    ChannelHandlerContext lastContext();
    
    ChannelHandler get(final String p0);
    
    ChannelHandler get(final Class p0);
    
    ChannelHandlerContext context(final ChannelHandler p0);
    
    ChannelHandlerContext context(final String p0);
    
    ChannelHandlerContext context(final Class p0);
    
    Channel channel();
    
    List names();
    
    Map toMap();
    
    ChannelPipeline fireChannelRegistered();
    
    ChannelPipeline fireChannelUnregistered();
    
    ChannelPipeline fireChannelActive();
    
    ChannelPipeline fireChannelInactive();
    
    ChannelPipeline fireExceptionCaught(final Throwable p0);
    
    ChannelPipeline fireUserEventTriggered(final Object p0);
    
    ChannelPipeline fireChannelRead(final Object p0);
    
    ChannelPipeline fireChannelReadComplete();
    
    ChannelPipeline fireChannelWritabilityChanged();
    
    ChannelFuture bind(final SocketAddress p0);
    
    ChannelFuture connect(final SocketAddress p0);
    
    ChannelFuture connect(final SocketAddress p0, final SocketAddress p1);
    
    ChannelFuture disconnect();
    
    ChannelFuture close();
    
    ChannelFuture deregister();
    
    ChannelFuture bind(final SocketAddress p0, final ChannelPromise p1);
    
    ChannelFuture connect(final SocketAddress p0, final ChannelPromise p1);
    
    ChannelFuture connect(final SocketAddress p0, final SocketAddress p1, final ChannelPromise p2);
    
    ChannelFuture disconnect(final ChannelPromise p0);
    
    ChannelFuture close(final ChannelPromise p0);
    
    ChannelFuture deregister(final ChannelPromise p0);
    
    ChannelPipeline read();
    
    ChannelFuture write(final Object p0);
    
    ChannelFuture write(final Object p0, final ChannelPromise p1);
    
    ChannelPipeline flush();
    
    ChannelFuture writeAndFlush(final Object p0, final ChannelPromise p1);
    
    ChannelFuture writeAndFlush(final Object p0);
}
