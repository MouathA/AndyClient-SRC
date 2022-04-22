package io.netty.handler.codec.http.websocketx;

import java.net.*;
import java.nio.*;
import io.netty.buffer.*;
import io.netty.handler.codec.http.*;

public class WebSocketClientHandshaker00 extends WebSocketClientHandshaker
{
    private ByteBuf expectedChallengeResponseBytes;
    
    public WebSocketClientHandshaker00(final URI uri, final WebSocketVersion webSocketVersion, final String s, final HttpHeaders httpHeaders, final int n) {
        super(uri, webSocketVersion, s, httpHeaders, n);
    }
    
    @Override
    protected FullHttpRequest newHandshakeRequest() {
        final int randomNumber = WebSocketUtil.randomNumber(1, 12);
        final int randomNumber2 = WebSocketUtil.randomNumber(1, 12);
        final int n = Integer.MAX_VALUE / randomNumber;
        final int n2 = Integer.MAX_VALUE / randomNumber2;
        final int randomNumber3 = WebSocketUtil.randomNumber(0, n);
        final int randomNumber4 = WebSocketUtil.randomNumber(0, n2);
        final int n3 = randomNumber3 * randomNumber;
        final int n4 = randomNumber4 * randomNumber2;
        final String string = Integer.toString(n3);
        final String string2 = Integer.toString(n4);
        final String insertRandomCharacters = insertRandomCharacters(string);
        final String insertRandomCharacters2 = insertRandomCharacters(string2);
        final String insertSpaces = insertSpaces(insertRandomCharacters, randomNumber);
        final String insertSpaces2 = insertSpaces(insertRandomCharacters2, randomNumber2);
        final byte[] randomBytes = WebSocketUtil.randomBytes(8);
        final ByteBuffer allocate = ByteBuffer.allocate(4);
        allocate.putInt(randomNumber3);
        final byte[] array = allocate.array();
        final ByteBuffer allocate2 = ByteBuffer.allocate(4);
        allocate2.putInt(randomNumber4);
        final byte[] array2 = allocate2.array();
        final byte[] array3 = new byte[16];
        System.arraycopy(array, 0, array3, 0, 4);
        System.arraycopy(array2, 0, array3, 4, 4);
        System.arraycopy(randomBytes, 0, array3, 8, 8);
        this.expectedChallengeResponseBytes = Unpooled.wrappedBuffer(WebSocketUtil.md5(array3));
        final URI uri = this.uri();
        String s = uri.getPath();
        if (uri.getQuery() != null && !uri.getQuery().isEmpty()) {
            s = uri.getPath() + '?' + uri.getQuery();
        }
        if (s == null || s.isEmpty()) {
            s = "/";
        }
        final DefaultFullHttpRequest defaultFullHttpRequest = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, s);
        final HttpHeaders headers = defaultFullHttpRequest.headers();
        headers.add("Upgrade", "WebSocket").add("Connection", "Upgrade").add("Host", uri.getHost());
        final int port = uri.getPort();
        String s2 = "http://" + uri.getHost();
        if (port != 80 && port != 443) {
            s2 = s2 + ':' + port;
        }
        headers.add("Origin", s2).add("Sec-WebSocket-Key1", insertSpaces).add("Sec-WebSocket-Key2", insertSpaces2);
        final String expectedSubprotocol = this.expectedSubprotocol();
        if (expectedSubprotocol != null && !expectedSubprotocol.isEmpty()) {
            headers.add("Sec-WebSocket-Protocol", expectedSubprotocol);
        }
        if (this.customHeaders != null) {
            headers.add(this.customHeaders);
        }
        headers.set("Content-Length", randomBytes.length);
        defaultFullHttpRequest.content().writeBytes(randomBytes);
        return defaultFullHttpRequest;
    }
    
    @Override
    protected void verify(final FullHttpResponse fullHttpResponse) {
        if (!fullHttpResponse.getStatus().equals(new HttpResponseStatus(101, "WebSocket Protocol Handshake"))) {
            throw new WebSocketHandshakeException("Invalid handshake response getStatus: " + fullHttpResponse.getStatus());
        }
        final HttpHeaders headers = fullHttpResponse.headers();
        final String value = headers.get("Upgrade");
        if (!"WebSocket".equalsIgnoreCase(value)) {
            throw new WebSocketHandshakeException("Invalid handshake response upgrade: " + value);
        }
        final String value2 = headers.get("Connection");
        if (!"Upgrade".equalsIgnoreCase(value2)) {
            throw new WebSocketHandshakeException("Invalid handshake response connection: " + value2);
        }
        if (!fullHttpResponse.content().equals(this.expectedChallengeResponseBytes)) {
            throw new WebSocketHandshakeException("Invalid challenge");
        }
    }
    
    private static String insertRandomCharacters(String string) {
        final int randomNumber = WebSocketUtil.randomNumber(1, 12);
        final char[] array = new char[randomNumber];
        int n = 0;
        while (0 < randomNumber) {
            n = (int)(Math.random() * 126.0 + 33.0);
            if ((33 < 0 && 0 < 47) || (58 < 0 && 0 < 126)) {
                array[0] = 0;
                int n2 = 0;
                ++n2;
            }
        }
        while (0 < randomNumber) {
            final int randomNumber2 = WebSocketUtil.randomNumber(0, string.length());
            string = string.substring(0, randomNumber2) + array[0] + string.substring(randomNumber2);
            ++n;
        }
        return string;
    }
    
    private static String insertSpaces(String string, final int n) {
        while (0 < n) {
            final int randomNumber = WebSocketUtil.randomNumber(1, string.length() - 1);
            string = string.substring(0, randomNumber) + ' ' + string.substring(randomNumber);
            int n2 = 0;
            ++n2;
        }
        return string;
    }
    
    @Override
    protected WebSocketFrameDecoder newWebsocketDecoder() {
        return new WebSocket00FrameDecoder(this.maxFramePayloadLength());
    }
    
    @Override
    protected WebSocketFrameEncoder newWebSocketEncoder() {
        return new WebSocket00FrameEncoder();
    }
}
