package io.netty.handler.codec.bytes;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;

public class ByteArrayDecoder extends MessageToMessageDecoder
{
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        final byte[] array = new byte[byteBuf.readableBytes()];
        byteBuf.getBytes(0, array);
        list.add(array);
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)o, list);
    }
}
