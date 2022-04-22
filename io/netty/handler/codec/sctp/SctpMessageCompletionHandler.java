package io.netty.handler.codec.sctp;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.channel.sctp.*;
import java.util.*;
import io.netty.buffer.*;

public class SctpMessageCompletionHandler extends MessageToMessageDecoder
{
    private final Map fragments;
    
    public SctpMessageCompletionHandler() {
        this.fragments = new HashMap();
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final SctpMessage sctpMessage, final List list) throws Exception {
        final ByteBuf content = sctpMessage.content();
        final int protocolIdentifier = sctpMessage.protocolIdentifier();
        final int streamIdentifier = sctpMessage.streamIdentifier();
        final boolean complete = sctpMessage.isComplete();
        ByteBuf empty_BUFFER;
        if (this.fragments.containsKey(streamIdentifier)) {
            empty_BUFFER = this.fragments.remove(streamIdentifier);
        }
        else {
            empty_BUFFER = Unpooled.EMPTY_BUFFER;
        }
        if (complete && !empty_BUFFER.isReadable()) {
            list.add(sctpMessage);
        }
        else if (!complete && empty_BUFFER.isReadable()) {
            this.fragments.put(streamIdentifier, Unpooled.wrappedBuffer(empty_BUFFER, content));
        }
        else if (complete && empty_BUFFER.isReadable()) {
            this.fragments.remove(streamIdentifier);
            list.add(new SctpMessage(protocolIdentifier, streamIdentifier, Unpooled.wrappedBuffer(empty_BUFFER, content)));
        }
        else {
            this.fragments.put(streamIdentifier, content);
        }
        content.retain();
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (SctpMessage)o, list);
    }
}
