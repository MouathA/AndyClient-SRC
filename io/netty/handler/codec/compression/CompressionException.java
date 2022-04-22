package io.netty.handler.codec.compression;

import io.netty.handler.codec.*;

public class CompressionException extends EncoderException
{
    private static final long serialVersionUID = 5603413481274811897L;
    
    public CompressionException() {
    }
    
    public CompressionException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public CompressionException(final String s) {
        super(s);
    }
    
    public CompressionException(final Throwable t) {
        super(t);
    }
}
