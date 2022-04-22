package io.netty.channel;

import java.net.*;

public class CombinedChannelDuplexHandler extends ChannelDuplexHandler
{
    private ChannelInboundHandler inboundHandler;
    private ChannelOutboundHandler outboundHandler;
    
    protected CombinedChannelDuplexHandler() {
    }
    
    public CombinedChannelDuplexHandler(final ChannelInboundHandler channelInboundHandler, final ChannelOutboundHandler channelOutboundHandler) {
        this.init(channelInboundHandler, channelOutboundHandler);
    }
    
    protected final void init(final ChannelInboundHandler inboundHandler, final ChannelOutboundHandler outboundHandler) {
        this.validate(inboundHandler, outboundHandler);
        this.inboundHandler = inboundHandler;
        this.outboundHandler = outboundHandler;
    }
    
    private void validate(final ChannelInboundHandler channelInboundHandler, final ChannelOutboundHandler channelOutboundHandler) {
        if (this.inboundHandler != null) {
            throw new IllegalStateException("init() can not be invoked if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with non-default constructor.");
        }
        if (channelInboundHandler == null) {
            throw new NullPointerException("inboundHandler");
        }
        if (channelOutboundHandler == null) {
            throw new NullPointerException("outboundHandler");
        }
        if (channelInboundHandler instanceof ChannelOutboundHandler) {
            throw new IllegalArgumentException("inboundHandler must not implement " + ChannelOutboundHandler.class.getSimpleName() + " to get combined.");
        }
        if (channelOutboundHandler instanceof ChannelInboundHandler) {
            throw new IllegalArgumentException("outboundHandler must not implement " + ChannelInboundHandler.class.getSimpleName() + " to get combined.");
        }
    }
    
    protected final ChannelInboundHandler inboundHandler() {
        return this.inboundHandler;
    }
    
    protected final ChannelOutboundHandler outboundHandler() {
        return this.outboundHandler;
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext channelHandlerContext) throws Exception {
        if (this.inboundHandler == null) {
            throw new IllegalStateException("init() must be invoked before being added to a " + ChannelPipeline.class.getSimpleName() + " if " + CombinedChannelDuplexHandler.class.getSimpleName() + " was constructed with the default constructor.");
        }
        this.inboundHandler.handlerAdded(channelHandlerContext);
        this.outboundHandler.handlerAdded(channelHandlerContext);
    }
    
    @Override
    public void handlerRemoved(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.inboundHandler.handlerRemoved(channelHandlerContext);
        this.outboundHandler.handlerRemoved(channelHandlerContext);
    }
    
    @Override
    public void channelRegistered(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.inboundHandler.channelRegistered(channelHandlerContext);
    }
    
    @Override
    public void channelUnregistered(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.inboundHandler.channelUnregistered(channelHandlerContext);
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.inboundHandler.channelActive(channelHandlerContext);
    }
    
    @Override
    public void channelInactive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.inboundHandler.channelInactive(channelHandlerContext);
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        this.inboundHandler.exceptionCaught(channelHandlerContext, t);
    }
    
    @Override
    public void userEventTriggered(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        this.inboundHandler.userEventTriggered(channelHandlerContext, o);
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        this.inboundHandler.channelRead(channelHandlerContext, o);
    }
    
    @Override
    public void channelReadComplete(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.inboundHandler.channelReadComplete(channelHandlerContext);
    }
    
    @Override
    public void bind(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final ChannelPromise channelPromise) throws Exception {
        this.outboundHandler.bind(channelHandlerContext, socketAddress, channelPromise);
    }
    
    @Override
    public void connect(final ChannelHandlerContext channelHandlerContext, final SocketAddress socketAddress, final SocketAddress socketAddress2, final ChannelPromise channelPromise) throws Exception {
        this.outboundHandler.connect(channelHandlerContext, socketAddress, socketAddress2, channelPromise);
    }
    
    @Override
    public void disconnect(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        this.outboundHandler.disconnect(channelHandlerContext, channelPromise);
    }
    
    @Override
    public void close(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        this.outboundHandler.close(channelHandlerContext, channelPromise);
    }
    
    @Override
    public void deregister(final ChannelHandlerContext channelHandlerContext, final ChannelPromise channelPromise) throws Exception {
        this.outboundHandler.deregister(channelHandlerContext, channelPromise);
    }
    
    @Override
    public void read(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.outboundHandler.read(channelHandlerContext);
    }
    
    @Override
    public void write(final ChannelHandlerContext channelHandlerContext, final Object o, final ChannelPromise channelPromise) throws Exception {
        this.outboundHandler.write(channelHandlerContext, o, channelPromise);
    }
    
    @Override
    public void flush(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.outboundHandler.flush(channelHandlerContext);
    }
    
    @Override
    public void channelWritabilityChanged(final ChannelHandlerContext channelHandlerContext) throws Exception {
        this.inboundHandler.channelWritabilityChanged(channelHandlerContext);
    }
}
