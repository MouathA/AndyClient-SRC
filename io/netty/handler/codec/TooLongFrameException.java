package io.netty.handler.codec;

public class TooLongFrameException extends DecoderException
{
    private static final long serialVersionUID = -1995801950698951640L;
    
    public TooLongFrameException() {
    }
    
    public TooLongFrameException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public TooLongFrameException(final String s) {
        super(s);
    }
    
    public TooLongFrameException(final Throwable t) {
        super(t);
    }
}
