package io.netty.channel;

public class ChannelException extends RuntimeException
{
    private static final long serialVersionUID = 2908618315971075004L;
    
    public ChannelException() {
    }
    
    public ChannelException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public ChannelException(final String s) {
        super(s);
    }
    
    public ChannelException(final Throwable t) {
        super(t);
    }
}
