package io.netty.handler.codec.http.websocketx;

import java.net.*;
import io.netty.handler.codec.http.*;

public final class WebSocketClientHandshakerFactory
{
    private WebSocketClientHandshakerFactory() {
    }
    
    public static WebSocketClientHandshaker newHandshaker(final URI uri, final WebSocketVersion webSocketVersion, final String s, final boolean b, final HttpHeaders httpHeaders) {
        return newHandshaker(uri, webSocketVersion, s, b, httpHeaders, 65536);
    }
    
    public static WebSocketClientHandshaker newHandshaker(final URI uri, final WebSocketVersion webSocketVersion, final String s, final boolean b, final HttpHeaders httpHeaders, final int n) {
        if (webSocketVersion == WebSocketVersion.V13) {
            return new WebSocketClientHandshaker13(uri, WebSocketVersion.V13, s, b, httpHeaders, n);
        }
        if (webSocketVersion == WebSocketVersion.V08) {
            return new WebSocketClientHandshaker08(uri, WebSocketVersion.V08, s, b, httpHeaders, n);
        }
        if (webSocketVersion == WebSocketVersion.V07) {
            return new WebSocketClientHandshaker07(uri, WebSocketVersion.V07, s, b, httpHeaders, n);
        }
        if (webSocketVersion == WebSocketVersion.V00) {
            return new WebSocketClientHandshaker00(uri, WebSocketVersion.V00, s, httpHeaders, n);
        }
        throw new WebSocketHandshakeException("Protocol version " + webSocketVersion + " not supported.");
    }
}
