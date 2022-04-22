package io.netty.handler.codec.http.websocketx;

import io.netty.handler.codec.http.*;
import io.netty.util.*;

public class WebSocketServerHandshaker07 extends WebSocketServerHandshaker
{
    public static final String WEBSOCKET_07_ACCEPT_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private final boolean allowExtensions;
    
    public WebSocketServerHandshaker07(final String s, final String s2, final boolean allowExtensions, final int n) {
        super(WebSocketVersion.V07, s, s2, n);
        this.allowExtensions = allowExtensions;
    }
    
    @Override
    protected FullHttpResponse newHandshakeResponse(final FullHttpRequest fullHttpRequest, final HttpHeaders httpHeaders) {
        final DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.SWITCHING_PROTOCOLS);
        if (httpHeaders != null) {
            defaultFullHttpResponse.headers().add(httpHeaders);
        }
        final String value = fullHttpRequest.headers().get("Sec-WebSocket-Key");
        if (value == null) {
            throw new WebSocketHandshakeException("not a WebSocket request: missing key");
        }
        final String base64 = WebSocketUtil.base64(WebSocketUtil.sha1((value + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes(CharsetUtil.US_ASCII)));
        if (WebSocketServerHandshaker07.logger.isDebugEnabled()) {
            WebSocketServerHandshaker07.logger.debug("WebSocket version 07 server handshake key: {}, response: {}.", value, base64);
        }
        defaultFullHttpResponse.headers().add("Upgrade", "WebSocket".toLowerCase());
        defaultFullHttpResponse.headers().add("Connection", "Upgrade");
        defaultFullHttpResponse.headers().add("Sec-WebSocket-Accept", base64);
        final String value2 = fullHttpRequest.headers().get("Sec-WebSocket-Protocol");
        if (value2 != null) {
            final String selectSubprotocol = this.selectSubprotocol(value2);
            if (selectSubprotocol == null) {
                if (WebSocketServerHandshaker07.logger.isDebugEnabled()) {
                    WebSocketServerHandshaker07.logger.debug("Requested subprotocol(s) not supported: {}", value2);
                }
            }
            else {
                defaultFullHttpResponse.headers().add("Sec-WebSocket-Protocol", selectSubprotocol);
            }
        }
        return defaultFullHttpResponse;
    }
    
    @Override
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket07FrameDecoder(true, this.allowExtensions, this.maxFramePayloadLength());
    }
    
    @Override
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket07FrameEncoder(false);
    }
}
