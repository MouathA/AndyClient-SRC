package io.netty.handler.codec;

public class DecoderException extends CodecException
{
    private static final long serialVersionUID = 6926716840699621852L;
    
    public DecoderException() {
    }
    
    public DecoderException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public DecoderException(final String s) {
        super(s);
    }
    
    public DecoderException(final Throwable t) {
        super(t);
    }
}
