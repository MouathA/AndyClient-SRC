package io.netty.channel;

public class ChannelInboundHandlerAdapter extends ChannelHandlerAdapter implements ChannelInboundHandler
{
    @Override
    public void channelRegistered(final ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelRegistered();
    }
    
    @Override
    public void channelUnregistered(final ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelUnregistered();
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelActive();
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelInactive();
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        channelHandlerContext.fireChannelRead(o);
    }
    
    @Override
    public void channelReadComplete(final ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelReadComplete();
    }
    
    @Override
    public void userEventTriggered(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        channelHandlerContext.fireUserEventTriggered(o);
    }
    
    @Override
    public void channelWritabilityChanged(final ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.fireChannelWritabilityChanged();
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        channelHandlerContext.fireExceptionCaught(t);
    }
}
