package io.netty.handler.codec.spdy;

public class SpdyProtocolException extends Exception
{
    private static final long serialVersionUID = 7870000537743847264L;
    
    public SpdyProtocolException() {
    }
    
    public SpdyProtocolException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public SpdyProtocolException(final String s) {
        super(s);
    }
    
    public SpdyProtocolException(final Throwable t) {
        super(t);
    }
}
