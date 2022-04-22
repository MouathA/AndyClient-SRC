package io.netty.handler.codec.http.websocketx;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;

public class WebSocketServerHandshakerFactory
{
    private final String webSocketURL;
    private final String subprotocols;
    private final boolean allowExtensions;
    private final int maxFramePayloadLength;
    
    public WebSocketServerHandshakerFactory(final String s, final String s2, final boolean b) {
        this(s, s2, b, 65536);
    }
    
    public WebSocketServerHandshakerFactory(final String webSocketURL, final String subprotocols, final boolean allowExtensions, final int maxFramePayloadLength) {
        this.webSocketURL = webSocketURL;
        this.subprotocols = subprotocols;
        this.allowExtensions = allowExtensions;
        this.maxFramePayloadLength = maxFramePayloadLength;
    }
    
    public WebSocketServerHandshaker newHandshaker(final HttpRequest httpRequest) {
        final String value = httpRequest.headers().get("Sec-WebSocket-Version");
        if (value == null) {
            return new WebSocketServerHandshaker00(this.webSocketURL, this.subprotocols, this.maxFramePayloadLength);
        }
        if (value.equals(WebSocketVersion.V13.toHttpHeaderValue())) {
            return new WebSocketServerHandshaker13(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
        }
        if (value.equals(WebSocketVersion.V08.toHttpHeaderValue())) {
            return new WebSocketServerHandshaker08(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
        }
        if (value.equals(WebSocketVersion.V07.toHttpHeaderValue())) {
            return new WebSocketServerHandshaker07(this.webSocketURL, this.subprotocols, this.allowExtensions, this.maxFramePayloadLength);
        }
        return null;
    }
    
    @Deprecated
    public static void sendUnsupportedWebSocketVersionResponse(final Channel channel) {
        sendUnsupportedVersionResponse(channel);
    }
    
    public static ChannelFuture sendUnsupportedVersionResponse(final Channel channel) {
        return sendUnsupportedVersionResponse(channel, channel.newPromise());
    }
    
    public static ChannelFuture sendUnsupportedVersionResponse(final Channel channel, final ChannelPromise channelPromise) {
        final DefaultHttpResponse defaultHttpResponse = new DefaultHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.UPGRADE_REQUIRED);
        defaultHttpResponse.headers().set("Sec-WebSocket-Version", WebSocketVersion.V13.toHttpHeaderValue());
        return channel.write(defaultHttpResponse, channelPromise);
    }
}
