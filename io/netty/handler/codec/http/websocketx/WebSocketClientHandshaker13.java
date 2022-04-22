package io.netty.handler.codec.http.websocketx;

import java.net.*;
import io.netty.util.*;
import io.netty.handler.codec.http.*;
import io.netty.util.internal.logging.*;

public class WebSocketClientHandshaker13 extends WebSocketClientHandshaker
{
    private static final InternalLogger logger;
    public static final String MAGIC_GUID = "258EAFA5-E914-47DA-95CA-C5AB0DC85B11";
    private String expectedChallengeResponseString;
    private final boolean allowExtensions;
    
    public WebSocketClientHandshaker13(final URI uri, final WebSocketVersion webSocketVersion, final String s, final boolean allowExtensions, final HttpHeaders httpHeaders, final int n) {
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
        if (WebSocketClientHandshaker13.logger.isDebugEnabled()) {
            WebSocketClientHandshaker13.logger.debug("WebSocket version 13 client handshake key: {}, expected response: {}", base64, this.expectedChallengeResponseString);
        }
        uri.getPort();
        if (80 != -1 || "wss".equals(uri.getScheme())) {}
        final DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, s);
        final HttpHeaders headers = defaultFullHttpRequest.headers();
        headers.add("Upgrade", "WebSocket".toLowerCase()).add("Connection", "Upgrade").add("Sec-WebSocket-Key", base64).add("Host", uri.getHost() + ':' + 80);
        String s2 = "http://" + uri.getHost();
        if (80 != 80 && 80 != 443) {
            s2 = s2 + ':' + 80;
        }
        headers.add("Sec-WebSocket-Origin", s2);
        final String expectedSubprotocol = this.expectedSubprotocol();
        if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
            headers.add("Sec-WebSocket-Protocol", expectedSubprotocol);
        }
        headers.add("Sec-WebSocket-Version", "13");
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
        return new WebSocket13FrameDecoder(false, this.allowExtensions, this.maxFramePayloadLength());
    }
    
    @Override
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket13FrameEncoder(true);
    }
    
    static {
        logger = InternalLoggerFactory.getInstance(WebSocketClientHandshaker13.class);
    }
}
