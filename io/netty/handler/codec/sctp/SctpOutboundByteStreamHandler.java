package io.netty.handler.codec.sctp;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import io.netty.channel.sctp.*;

public class SctpOutboundByteStreamHandler extends MessageToMessageEncoder
{
    private final int streamIdentifier;
    private final int protocolIdentifier;
    
    public SctpOutboundByteStreamHandler(final int streamIdentifier, final int protocolIdentifier) {
        this.streamIdentifier = streamIdentifier;
        this.protocolIdentifier = protocolIdentifier;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        list.add(new SctpMessage(this.streamIdentifier, this.protocolIdentifier, byteBuf.retain()));
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, list);
    }
}
