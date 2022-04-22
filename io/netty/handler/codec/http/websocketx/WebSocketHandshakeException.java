package io.netty.handler.codec.http.websocketx;

public class WebSocketHandshakeException extends RuntimeException
{
    private static final long serialVersionUID = 1L;
    
    public WebSocketHandshakeException(final String s) {
        super(s);
    }
    
    public WebSocketHandshakeException(final String s, final Throwable t) {
        super(s, t);
    }
}
