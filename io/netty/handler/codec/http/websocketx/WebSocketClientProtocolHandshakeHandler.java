package io.netty.handler.codec.http.websocketx;

import io.netty.util.concurrent.*;
import io.netty.handler.codec.http.*;
import io.netty.channel.*;

class WebSocketClientProtocolHandshakeHandler extends ChannelInboundHandlerAdapter
{
    private final WebSocketClientHandshaker handshaker;
    
    WebSocketClientProtocolHandshakeHandler(final WebSocketClientHandshaker handshaker) {
        this.handshaker = handshaker;
    }
    
    @Override
    public void channelActive(final ChannelHandlerContext channelHandlerContext) throws Exception {
        super.channelActive(channelHandlerContext);
        this.handshaker.handshake(channelHandlerContext.channel()).addListener((GenericFutureListener)new ChannelFutureListener(channelHandlerContext) {
            final ChannelHandlerContext val$ctx;
            final WebSocketClientProtocolHandshakeHandler this$0;
            
            public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                if (!channelFuture.isSuccess()) {
                    this.val$ctx.fireExceptionCaught(channelFuture.cause());
                }
                else {
                    this.val$ctx.fireUserEventTriggered(WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_ISSUED);
                }
            }
            
            @Override
            public void operationComplete(final Future future) throws Exception {
                this.operationComplete((ChannelFuture)future);
            }
        });
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        if (!(o instanceof FullHttpResponse)) {
            channelHandlerContext.fireChannelRead(o);
            return;
        }
        if (!this.handshaker.isHandshakeComplete()) {
            this.handshaker.finishHandshake(channelHandlerContext.channel(), (FullHttpResponse)o);
            channelHandlerContext.fireUserEventTriggered(WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE);
            channelHandlerContext.pipeline().remove(this);
            return;
        }
        throw new IllegalStateException("WebSocketClientHandshaker should have been non finished yet");
    }
}
