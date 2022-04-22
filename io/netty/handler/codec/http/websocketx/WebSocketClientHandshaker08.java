package io.netty.handler.codec.http.websocketx;

import java.net.*;
import io.netty.util.*;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.logging.*;

public class WebSocketClientHandshaker08 extends WebSocketClientHandshaker
{
    private static final InternalLogger logger;
    public static final String MAGIC_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private String expectedChallengeResponseString;
    private final boolean allowExtensions;
    
    public WebSocketClientHandshaker08(final URI uri, final WebSocketVersion webSocketVersion, final String s, final boolean allowExtensions, final HttpHeaders httpHeaders, final int n) {
        super(uri, webSocketVersion, s, httpHeaders, n);
        this.allowExtensions = allowExtensions;
    }
    
    @Override
    protected FullHttpRequest newHandshakeRequest() {
        final URI uri = this.uri();
        String s = uri.getPath();
        if (uri.getQuery() != null && !uri.getQuery().isEmpty()) {
            s = uri.getPath() + '?' + uri.getQuery();
        }
        if (s == null || s.isEmpty()) {
            s = "/";
        }
        final String base64 = WebSocketUtil.base64(WebSocketUtil.randomBytes(16));
        this.expectedChallengeResponseString = WebSocketUtil.base64(WebSocketUtil.sha1((base64 + "258EAFA5-E914-47DA-95CA-C5AB0DC85B11").getBytes(CharsetUtil.US_ASCII)));
        if (WebSocketClientHandshaker08.logger.isDebugEnabled()) {
            WebSocketClientHandshaker08.logger.debug("WebSocket version 08 client handshake key: {}, expected response: {}", base64, this.expectedChallengeResponseString);
        }
        final DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, s);
        final HttpHeaders headers = defaultFullHttpRequest.headers();
        headers.add("Upgrade", "WebSocket".toLowerCase()).add("Connection", "Upgrade").add("Sec-WebSocket-Key", base64).add("Host", uri.getHost());
        final int port = uri.getPort();
        String s2 = "http://" + uri.getHost();
        if (port != 80 && port != 443) {
            s2 = s2 + ':' + port;
        }
        headers.add("Sec-WebSocket-Origin", s2);
        final String expectedSubprotocol = this.expectedSubprotocol();
        if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
            headers.add("Sec-WebSocket-Protocol", expectedSubprotocol);
        }
        headers.add("Sec-WebSocket-Version", "8");
        if (this.customHeaders != null) {
            headers.add(this.customHeaders);
        }
        return defaultFullHttpRequest;
    }
    
    @Override
    protected void verify(final FullHttpResponse fullHttpResponse) {
        final HttpResponseStatus switching_PROTOCOLS = HttpResponseStatus.SWITCHING_PROTOCOLS;
        final HttpHeaders headers = fullHttpResponse.headers();
        if (!fullHttpResponse.getStatus().equals(switching_PROTOCOLS)) {
            throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + fullHttpResponse.getStatus());
        }
        final String value = headers.get("Upgrade");
        if (!"WebSocket".equalsIgnoreCase(value)) {
            throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + value);
        }
        final String value2 = headers.get("Connection");
        if (!"Upgrade".equalsIgnoreCase(value2)) {
            throw new WebSocketHandshakeException("Invalid handshake response connection: " + value2);
        }
        final String value3 = headers.get("Sec-WebSocket-Accept");
        if (value3 == null || !value3.equals(this.expectedChallengeResponseString)) {
            throw new WebSocketHandshakeException(String.format("Invalid challenge. Actual: %s. Expected: %s", value3, this.expectedChallengeResponseString));
        }
    }
    
    @Override
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket08FrameDecoder(false, this.allowExtensions, this.maxFramePayloadLength());
    }
    
    @Override
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket08FrameEncoder(true);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker08.class);
    }
}
