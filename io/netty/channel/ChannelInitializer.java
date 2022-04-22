package io.netty.channel;

import io.netty.util.internal.logging.*;

@ChannelHandler.Sharable
public abstract class ChannelInitializer extends ChannelInboundHandlerAdapter
{
    private static final InternalLogger logger;
    
    protected abstract void initChannel(final Channel p0) throws Exception;
    
    @Override
    public final void channelRegistered(final ChannelHandlerContext channelHandlerContext) throws Exception {
        final ChannelPipeline pipeline = channelHandlerContext.pipeline();
        this.initChannel(channelHandlerContext.channel());
        pipeline.remove(this);
        channelHandlerContext.fireChannelRegistered();
        if (pipeline.context(this) != null) {
            pipeline.remove(this);
        }
        if (!true) {
            channelHandlerContext.close();
        }
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(ChannelInitializer.class);
    }
}
