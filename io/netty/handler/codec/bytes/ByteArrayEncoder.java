package io.netty.handler.codec.bytes;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import java.util.*;
import io.netty.buffer.*;

@ChannelHandler.Sharable
public class ByteArrayEncoder extends MessageToMessageEncoder
{
    protected void encode(final ChannelHandlerContext channelHandlerContext, final byte[] array, final List list) throws Exception {
        list.add(Unpooled.wrappedBuffer(array));
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (byte[])o, list);
    }
}
