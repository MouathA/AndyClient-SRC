package io.netty.handler.codec.http.websocketx;

import io.netty.util.*;
import java.util.*;
import io.netty.buffer.*;
import io.netty.util.concurrent.*;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;

public class WebSocketServerProtocolHandler extends WebSocketProtocolHandler
{
    private static final AttributeKey HANDSHAKER_ATTR_KEY;
    private final String websocketPath;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadLength;
    
    public WebSocketServerProtocolHandler(final String s) {
        this(s, null, false);
    }
    
    public WebSocketServerProtocolHandler(final String s, final String s2) {
        this(s, s2, false);
    }
    
    public WebSocketServerProtocolHandler(final String s, final String s2, final boolean b) {
        this(s, s2, b, 65536);
    }
    
    public WebSocketServerProtocolHandler(final String websocketPath, final String subprotocols, final boolean allowExtensions, final int maxFramePayloadLength) {
        this.websocketPath = websocketPath;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        this.maxFramePayloadLength = maxFramePayloadLength;
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext.pipeline().get(WebSocketServerProtocolHandshakeHandler.class) == null) {
            channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), WebSocketServerProtocolHandshakeHandler.class.getName(), new WebSocketServerProtocolHandshakeHandler(this.websocketPath, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength));
        }
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final WebSocketFrame webSocketFrame, final List list) throws Exception {
        if (webSocketFrame instanceof CloseWebSocketFrame) {
            final WebSocketServerHandshaker handshaker = getHandshaker(channelHandlerContext);
            if (handshaker != null) {
                webSocketFrame.retain();
                handshaker.close(channelHandlerContext.channel(), (CloseWebSocketFrame)webSocketFrame);
            }
            else {
                channelHandlerContext.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
            }
            return;
        }
        super.decode(channelHandlerContext, webSocketFrame, list);
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        if (t instanceof WebSocketHandshakeException) {
            channelHandlerContext.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST, Unpooled.wrappedBuffer(t.getMessage().getBytes()))).addListener((GenericFutureListener)ChannelFutureListener.CLOSE);
        }
        else {
            channelHandlerContext.close();
        }
    }
    
    static WebSocketServerHandshaker getHandshaker(final ChannelHandlerContext channelHandlerContext) {
        return (WebSocketServerHandshaker)channelHandlerContext.attr(WebSocketServerProtocolHandler.HANDSHAKER_ATTR_KEY).get();
    }
    
    static void setHandshaker(final ChannelHandlerContext channelHandlerContext, final WebSocketServerHandshaker webSocketServerHandshaker) {
        channelHandlerContext.attr(WebSocketServerProtocolHandler.HANDSHAKER_ATTR_KEY).set(webSocketServerHandshaker);
    }
    
    static ChannelHandler forbiddenHttpRequestResponder() {
        return new ChannelInboundHandlerAdapter() {
            @Override
            public void channelRead(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
                if (o instanceof FullHttpRequest) {
                    ((FullHttpRequest)o).release();
                    channelHandlerContext.channel().writeAndFlush(new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.FORBIDDEN));
                }
                else {
                    channelHandlerContext.fireChannelRead(o);
                }
            }
        };
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (WebSocketFrame)o, list);
    }
    
    static {
        HANDSHAKER_ATTR_KEY = AttributeKey.valueOf(WebSocketServerHandshaker.class.getName() + ".HANDSHAKER");
    }
    
    public enum ServerHandshakeStateEvent
    {
        HANDSHAKE_COMPLETE("HANDSHAKE_COMPLETE", 0);
        
        private static final ServerHandshakeStateEvent[] $VALUES;
        
        private ServerHandshakeStateEvent(final String s, final int n) {
        }
        
        static {
            $VALUES = new ServerHandshakeStateEvent[] { ServerHandshakeStateEvent.HANDSHAKE_COMPLETE };
        }
    }
}
