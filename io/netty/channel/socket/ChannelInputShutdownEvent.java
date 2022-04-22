package io.netty.channel.socket;

public final class ChannelInputShutdownEvent
{
    public static final ChannelInputShutdownEvent INSTANCE;
    
    private ChannelInputShutdownEvent() {
    }
    
    static {
        INSTANCE = new ChannelInputShutdownEvent();
    }
}
