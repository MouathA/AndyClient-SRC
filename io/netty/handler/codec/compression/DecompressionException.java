package io.netty.handler.codec.compression;

import io.netty.handler.codec.*;

public class DecompressionException extends DecoderException
{
    private static final long serialVersionUID = 3546272712208105199L;
    
    public DecompressionException() {
    }
    
    public DecompressionException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public DecompressionException(final String s) {
        super(s);
    }
    
    public DecompressionException(final Throwable t) {
        super(t);
    }
}
