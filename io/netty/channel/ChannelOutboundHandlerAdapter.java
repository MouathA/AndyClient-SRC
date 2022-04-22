package io.netty.channel;

import java.net.*;

public class ChannelOutboundHandlerAdapter extends ChannelHandlerAdapter implements ChannelOutboundHandler
{
    @Override
    public void bind(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.bind(socketAddress, channelPromise);
    }
    
    @Override
    public void connect(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.connect(socketAddress, socketAddress2, channelPromise);
    }
    
    @Override
    public void disconnect(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.disconnect(channelPromise);
    }
    
    @Override
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.close(channelPromise);
    }
    
    @Override
    public void deregister(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.deregister(channelPromise);
    }
    
    @Override
    public void read(final ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.read();
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        channelHandlerContext.write(o, channelPromise);
    }
    
    @Override
    public void flush(final ChannelHandlerContext channelHandlerContext) throws Exception {
        channelHandlerContext.flush();
    }
}
