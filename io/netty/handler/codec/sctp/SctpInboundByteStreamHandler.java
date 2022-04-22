package io.netty.handler.codec.sctp;

import io.netty.channel.sctp.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.handler.codec.*;

public class SctpInboundByteStreamHandler extends MessageToMessageDecoder
{
    private final int protocolIdentifier;
    private final int streamIdentifier;
    
    public SctpInboundByteStreamHandler(final int protocolIdentifier, final int streamIdentifier) {
        this.protocolIdentifier = protocolIdentifier;
        this.streamIdentifier = streamIdentifier;
    }
    
    @Override
    public final boolean acceptInboundMessage(final Object o) throws Exception {
        return super.acceptInboundMessage(o) && this.acceptInboundMessage((SctpMessage)o);
    }
    
    protected boolean acceptInboundMessage(final SctpMessage sctpMessage) {
        return sctpMessage.protocolIdentifier() == this.protocolIdentifier && sctpMessage.streamIdentifier() == this.streamIdentifier;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final SctpMessage sctpMessage, final List list) throws Exception {
        if (!sctpMessage.isComplete()) {
            throw new CodecException(String.format("Received SctpMessage is not complete, please add %s in the pipeline before this handler", SctpMessageCompletionHandler.class.getSimpleName()));
        }
        list.add(sctpMessage.content().retain());
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (SctpMessage)o, list);
    }
}
