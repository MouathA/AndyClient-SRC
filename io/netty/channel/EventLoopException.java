package io.netty.channel;

public class EventLoopException extends ChannelException
{
    private static final long serialVersionUID = -8969100344583703616L;
    
    public EventLoopException() {
    }
    
    public EventLoopException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public EventLoopException(final String s) {
        super(s);
    }
    
    public EventLoopException(final Throwable t) {
        super(t);
    }
}
