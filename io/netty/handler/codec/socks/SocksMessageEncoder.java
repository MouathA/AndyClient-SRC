package io.netty.handler.codec.socks;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;

@ChannelHandler.Sharable
public class SocksMessageEncoder extends MessageToByteEncoder
{
    private static final String name = "SOCKS_MESSAGE_ENCODER";
    
    @Deprecated
    public static String getName() {
        return "SOCKS_MESSAGE_ENCODER";
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final SocksMessage socksMessage, final ByteBuf byteBuf) throws Exception {
        socksMessage.encodeAsByteBuf(byteBuf);
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (SocksMessage)o, byteBuf);
    }
}
