package io.netty.channel;

public final class ChannelMetadata
{
    private final boolean hasDisconnect;
    
    public ChannelMetadata(final boolean hasDisconnect) {
        this.hasDisconnect = hasDisconnect;
    }
    
    public boolean hasDisconnect() {
        return this.hasDisconnect;
    }
}
