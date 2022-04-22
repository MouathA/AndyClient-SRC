package io.netty.handler.codec;

public class EncoderException extends CodecException
{
    private static final long serialVersionUID = -5086121160476476774L;
    
    public EncoderException() {
    }
    
    public EncoderException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public EncoderException(final String s) {
        super(s);
    }
    
    public EncoderException(final Throwable t) {
        super(t);
    }
}
