package io.netty.handler.codec;

public class CorruptedFrameException extends DecoderException
{
    private static final long serialVersionUID = 3918052232492988408L;
    
    public CorruptedFrameException() {
    }
    
    public CorruptedFrameException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public CorruptedFrameException(final String s) {
        super(s);
    }
    
    public CorruptedFrameException(final Throwable t) {
        super(t);
    }
}
