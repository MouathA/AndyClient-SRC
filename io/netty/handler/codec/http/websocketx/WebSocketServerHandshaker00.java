package io.netty.handler.codec.http.websocketx;

import java.util.regex.*;
import io.netty.handler.codec.http.*;
import io.netty.buffer.*;
import io.netty.channel.*;

public class WebSocketServerHandshaker00 extends WebSocketServerHandshaker
{
    private static final Pattern BEGINNING_DIGIT;
    private static final Pattern BEGINNING_SPACE;
    
    public WebSocketServerHandshaker00(final String s, final String s2, final int n) {
        super(WebSocketVersion.V00, s, s2, n);
    }
    
    @Override
    protected FullHttpResponse newHandshakeResponse(final FullHttpRequest fullHttpRequest, final HttpHeaders httpHeaders) {
        if (!"Upgrade".equalsIgnoreCase(fullHttpRequest.headers().get("Connection")) || !"WebSocket".equalsIgnoreCase(fullHttpRequest.headers().get("Upgrade"))) {
            throw new WebSocketHandshakeException("not a WebSocket handshake request: missing upgrade");
        }
        final boolean b = fullHttpRequest.headers().contains("Sec-WebSocket-Key1") && fullHttpRequest.headers().contains("Sec-WebSocket-Key2");
        final DefaultFullHttpResponse defaultFullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, new HttpResponseStatus(101, b ? "WebSocket Protocol Handshake" : "Web Socket Protocol Handshake"));
        if (httpHeaders != null) {
            defaultFullHttpResponse.headers().add(httpHeaders);
        }
        defaultFullHttpResponse.headers().add("Upgrade", "WebSocket");
        defaultFullHttpResponse.headers().add("Connection", "Upgrade");
        if (b) {
            defaultFullHttpResponse.headers().add("Sec-WebSocket-Origin", fullHttpRequest.headers().get("Origin"));
            defaultFullHttpResponse.headers().add("Sec-WebSocket-Location", this.uri());
            final String value = fullHttpRequest.headers().get("Sec-WebSocket-Protocol");
            if (value != null) {
                final String selectSubprotocol = this.selectSubprotocol(value);
                if (selectSubprotocol == null) {
                    if (WebSocketServerHandshaker00.logger.isDebugEnabled()) {
                        WebSocketServerHandshaker00.logger.debug("Requested subprotocol(s) not supported: {}", value);
                    }
                }
                else {
                    defaultFullHttpResponse.headers().add("Sec-WebSocket-Protocol", selectSubprotocol);
                }
            }
            final String value2 = fullHttpRequest.headers().get("Sec-WebSocket-Key1");
            final String value3 = fullHttpRequest.headers().get("Sec-WebSocket-Key2");
            final int n = (int)(Long.parseLong(WebSocketServerHandshaker00.BEGINNING_DIGIT.matcher(value2).replaceAll("")) / WebSocketServerHandshaker00.BEGINNING_SPACE.matcher(value2).replaceAll("").length());
            final int n2 = (int)(Long.parseLong(WebSocketServerHandshaker00.BEGINNING_DIGIT.matcher(value3).replaceAll("")) / WebSocketServerHandshaker00.BEGINNING_SPACE.matcher(value3).replaceAll("").length());
            final long long1 = fullHttpRequest.content().readLong();
            final ByteBuf buffer = Unpooled.buffer(16);
            buffer.writeInt(n);
            buffer.writeInt(n2);
            buffer.writeLong(long1);
            defaultFullHttpResponse.content().writeBytes(WebSocketUtil.md5(buffer.array()));
        }
        else {
            defaultFullHttpResponse.headers().add("WebSocket-Origin", fullHttpRequest.headers().get("Origin"));
            defaultFullHttpResponse.headers().add("WebSocket-Location", this.uri());
            final String value4 = fullHttpRequest.headers().get("WebSocket-Protocol");
            if (value4 != null) {
                defaultFullHttpResponse.headers().add("WebSocket-Protocol", this.selectSubprotocol(value4));
            }
        }
        return defaultFullHttpResponse;
    }
    
    @Override
    public ChannelFuture close(final Channel channel, final CloseWebSocketFrame closeWebSocketFrame, final ChannelPromise channelPromise) {
        return channel.writeAndFlush(closeWebSocketFrame, channelPromise);
    }
    
    @Override
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket00FrameDecoder(this.maxFramePayloadLength());
    }
    
    @Override
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket00FrameEncoder();
    }
    
    static {
        BEGINNING_DIGIT = Pattern.compile("[^0-9]");
        BEGINNING_SPACE = Pattern.compile("[^ ]");
    }
}
