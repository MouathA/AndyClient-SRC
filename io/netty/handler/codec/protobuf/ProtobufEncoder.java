package io.netty.handler.codec.protobuf;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import java.util.*;
import com.google.protobuf.*;
import io.netty.buffer.*;

@ChannelHandler.Sharable
public class ProtobufEncoder extends MessageToMessageEncoder
{
    protected void encode(final ChannelHandlerContext channelHandlerContext, final MessageLiteOrBuilder messageLiteOrBuilder, final List list) throws Exception {
        if (messageLiteOrBuilder instanceof MessageLite) {
            list.add(Unpooled.wrappedBuffer(((MessageLite)messageLiteOrBuilder).toByteArray()));
            return;
        }
        if (messageLiteOrBuilder instanceof MessageLite.Builder) {
            list.add(Unpooled.wrappedBuffer(((MessageLite.Builder)messageLiteOrBuilder).build().toByteArray()));
        }
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (MessageLiteOrBuilder)o, list);
    }
}
