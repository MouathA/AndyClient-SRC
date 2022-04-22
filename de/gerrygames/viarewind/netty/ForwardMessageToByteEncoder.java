package de.gerrygames.viarewind.netty;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;

public class ForwardMessageToByteEncoder extends MessageToByteEncoder
{
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws Exception {
        byteBuf2.writeBytes(byteBuf);
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
}
