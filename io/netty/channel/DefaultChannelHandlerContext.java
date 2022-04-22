package io.netty.channel;

import io.netty.util.concurrent.*;

final class DefaultChannelHandlerContext extends AbstractChannelHandlerContext
{
    private final ChannelHandler handler;
    
    DefaultChannelHandlerContext(final DefaultChannelPipeline defaultChannelPipeline, final EventExecutorGroup eventExecutorGroup, final String s, final ChannelHandler handler) {
        super(defaultChannelPipeline, eventExecutorGroup, s, isInbound(handler), isOutbound(handler));
        if (handler == null) {
            throw new NullPointerException("handler");
        }
        this.handler = handler;
    }
    
    @Override
    public ChannelHandler handler() {
        return this.handler;
    }
    
    private static boolean isInbound(final ChannelHandler channelHandler) {
        return channelHandler instanceof ChannelInboundHandler;
    }
    
    private static boolean isOutbound(final ChannelHandler channelHandler) {
        return channelHandler instanceof ChannelOutboundHandler;
    }
}
