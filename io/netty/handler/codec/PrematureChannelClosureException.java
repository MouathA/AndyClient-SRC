package io.netty.handler.codec;

public class PrematureChannelClosureException extends CodecException
{
    private static final long serialVersionUID = 4907642202594703094L;
    
    public PrematureChannelClosureException() {
    }
    
    public PrematureChannelClosureException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public PrematureChannelClosureException(final String s) {
        super(s);
    }
    
    public PrematureChannelClosureException(final Throwable t) {
        super(t);
    }
}
