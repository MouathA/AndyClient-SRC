package io.netty.handler.codec.http.websocketx;

import io.netty.util.concurrent.*;
import io.netty.handler.codec.http.*;
import io.netty.channel.*;
import io.netty.handler.ssl.*;

class WebSocketServerProtocolHandshakeHandler extends ChannelInboundHandlerAdapter
{
    private final String websocketPath;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadSize;
    
    WebSocketServerProtocolHandshakeHandler(final String websocketPath, final String subprotocols, final boolean allowExtensions, final int maxFramePayloadSize) {
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        this.maxFramePayloadSize = maxFramePayloadSize;
    }
    
    @Override
    public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        final FullHttpRequest fullHttpRequest = (FullHttpRequest)o;
        if (fullHttpRequest.getMethod() != HttpMethod.GET) {
            sendHttpResponse(channelHandlerContext, fullHttpRequest, new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
            fullHttpRequest.release();
            return;
        }
        final WebSocketServerHandshaker handshaker = new WebSocketServerHandshakerFactory(getWebSocketLocation(channelHandlerContext.pipeline(), fullHttpRequest, this.websocketPath), this.subprotocols, this.allowExtensions, this.maxFramePayloadSize).newHandshaker(fullHttpRequest);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channelHandlerContext.channel());
        }
        else {
            handshaker.handshake(channelHandlerContext.channel(), fullHttpRequest).addListener((GenericFutureListener)new ChannelFutureListener(channelHandlerContext) {
                final ChannelHandlerContext val$ctx;
                final WebSocketServerProtocolHandshakeHandler this$0;
                
                public void operationComplete(final ChannelFuture channelFuture) throws Exception {
                    if (!channelFuture.isSuccess()) {
                        this.val$ctx.fireExceptionCaught(channelFuture.cause());
                    }
                    else {
                        this.val$ctx.fireUserEventTriggered(WebSocketServerProtocolHandler.ServerHandshakeStateEvent.HANDSHAKE_COMPLETE);
                    }
                }
                
                @Override
                public void operationComplete(final Future future) throws Exception {
                    this.operationComplete((ChannelFuture)future);
                }
            });
            WebSocketServerProtocolHandler.setHandshaker(channelHandlerContext, handshaker);
            channelHandlerContext.pipeline().replace(this, "WS403Responder", WebSocketServerProtocolHandler.forbiddenHttpRequestResponder());
        }
        fullHttpRequest.release();
    }
    
    private static void sendHttpResponse(final ChannelHandlerContext channelHandlerContext, final HttpRequest httpRequest, final HttpResponse httpResponse) {
        final ChannelFuture writeAndFlush = channelHandlerContext.channel().writeAndFlush(httpResponse);
        if (!HttpHeaders.isKeepAlive(httpRequest) || httpResponse.getStatus().code() != 200) {
            writeAndFlush.addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
        }
    }
    
    private static String getWebSocketLocation(final ChannelPipeline channelPipeline, final HttpRequest httpRequest, final String s) {
        String s2 = "ws";
        if (channelPipeline.get(SslHandler.class) != null) {
            s2 = "wss";
        }
        return s2 + "://" + httpRequest.headers().get("Host") + s;
    }
}
