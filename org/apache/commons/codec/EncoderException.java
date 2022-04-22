package org.apache.commons.codec;

public class EncoderException extends Exception
{
    private static final long serialVersionUID = 1L;
    
    public EncoderException() {
    }
    
    public EncoderException(final String s) {
        super(s);
    }
    
    public EncoderException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public EncoderException(final Throwable t) {
        super(t);
    }
}
