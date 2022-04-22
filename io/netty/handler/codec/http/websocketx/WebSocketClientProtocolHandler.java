package io.netty.handler.codec.http.websocketx;

import java.net.*;
import io.netty.handler.codec.http.*;
import java.util.*;
import io.netty.channel.*;

public class WebSocketClientProtocolHandler extends WebSocketProtocolHandler
{
    private final WebSocketClientHandshaker handshaker;
    private final boolean handleCloseFrames;
    
    public WebSocketClientProtocolHandler(final URI uri, final WebSocketVersion webSocketVersion, final String s, final boolean b, final HttpHeaders httpHeaders, final int n, final boolean b2) {
        this(WebSocketClientHandshakerFactory.newHandshaker(uri, webSocketVersion, s, b, httpHeaders, n), b2);
    }
    
    public WebSocketClientProtocolHandler(final URI uri, final WebSocketVersion webSocketVersion, final String s, final boolean b, final HttpHeaders httpHeaders, final int n) {
        this(uri, webSocketVersion, s, b, httpHeaders, n, true);
    }
    
    public WebSocketClientProtocolHandler(final WebSocketClientHandshaker handshaker, final boolean handleCloseFrames) {
        this.handshaker = handshaker;
        this.handleCloseFrames = handleCloseFrames;
    }
    
    public WebSocketClientProtocolHandler(final WebSocketClientHandshaker webSocketClientHandshaker) {
        this(webSocketClientHandshaker, true);
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final WebSocketFrame webSocketFrame, final List list) throws Exception {
        if (this.handleCloseFrames && webSocketFrame instanceof CloseWebSocketFrame) {
            channelHandlerContext.close();
            return;
        }
        super.decode(channelHandlerContext, webSocketFrame, list);
    }
    
    @Override
    public void handlerAdded(final ChannelHandlerContext channelHandlerContext) {
        if (channelHandlerContext.pipeline().get(WebSocketClientProtocolHandshakeHandler.class) == null) {
            channelHandlerContext.pipeline().addBefore(channelHandlerContext.name(), WebSocketClientProtocolHandshakeHandler.class.getName(), new WebSocketClientProtocolHandshakeHandler(this.handshaker));
        }
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        super.exceptionCaught(channelHandlerContext, t);
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (WebSocketFrame)o, list);
    }
    
    public enum ClientHandshakeStateEvent
    {
        HANDSHAKE_ISSUED("HANDSHAKE_ISSUED", 0), 
        HANDSHAKE_COMPLETE("HANDSHAKE_COMPLETE", 1);
        
        private static final ClientHandshakeStateEvent[] $VALUES;
        
        private ClientHandshakeStateEvent(final String s, final int n) {
        }
        
        static {
            $VALUES = new ClientHandshakeStateEvent[] { ClientHandshakeStateEvent.HANDSHAKE_ISSUED, ClientHandshakeStateEvent.HANDSHAKE_COMPLETE };
        }
    }
}
