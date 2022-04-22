package io.netty.handler.codec;

public class CodecException extends RuntimeException
{
    private static final long serialVersionUID = -1464830400709348473L;
    
    public CodecException() {
    }
    
    public CodecException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public CodecException(final String s) {
        super(s);
    }
    
    public CodecException(final Throwable t) {
        super(t);
    }
}
