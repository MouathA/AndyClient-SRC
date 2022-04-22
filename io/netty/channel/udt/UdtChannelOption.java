package io.netty.channel.udt;

import io.netty.channel.*;

public final class UdtChannelOption extends ChannelOption
{
    public static final UdtChannelOption PROTOCOL_RECEIVE_BUFFER_SIZE;
    public static final UdtChannelOption PROTOCOL_SEND_BUFFER_SIZE;
    public static final UdtChannelOption SYSTEM_RECEIVE_BUFFER_SIZE;
    public static final UdtChannelOption SYSTEM_SEND_BUFFER_SIZE;
    
    private UdtChannelOption(final String s) {
        super(s);
    }
    
    static {
        PROTOCOL_RECEIVE_BUFFER_SIZE = new UdtChannelOption("PROTOCOL_RECEIVE_BUFFER_SIZE");
        PROTOCOL_SEND_BUFFER_SIZE = new UdtChannelOption("PROTOCOL_SEND_BUFFER_SIZE");
        SYSTEM_RECEIVE_BUFFER_SIZE = new UdtChannelOption("SYSTEM_RECEIVE_BUFFER_SIZE");
        SYSTEM_SEND_BUFFER_SIZE = new UdtChannelOption("SYSTEM_SEND_BUFFER_SIZE");
    }
}
