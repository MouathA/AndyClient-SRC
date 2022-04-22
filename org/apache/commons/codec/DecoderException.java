package org.apache.commons.codec;

public class DecoderException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public DecoderException() {
    }
    
    public DecoderException(final String s) {
        super(s);
    }
    
    public DecoderException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public DecoderException(final Throwable t) {
        super(t);
    }
}
