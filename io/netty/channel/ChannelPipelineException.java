package io.netty.channel;

public class ChannelPipelineException extends ChannelException
{
    private static final long serialVersionUID = 3379174210419885980L;
    
    public ChannelPipelineException() {
    }
    
    public ChannelPipelineException(final String s, final Throwable t) {
        super(s, t);
    }
    
    public ChannelPipelineException(final String s) {
        super(s);
    }
    
    public ChannelPipelineException(final Throwable t) {
        super(t);
    }
}
