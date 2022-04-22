package io.netty.handler.codec.base64;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;

@ChannelHandler.Sharable
public class Base64Decoder extends MessageToMessageDecoder
{
    private final Base64Dialect dialect;
    
    public Base64Decoder() {
        this(Base64Dialect.STANDARD);
    }
    
    public Base64Decoder(final Base64Dialect dialect) {
        if (dialect == null) {
            throw new NullPointerException("dialect");
        }
        this.dialect = dialect;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        list.add(Base64.decode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), this.dialect));
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)o, list);
    }
}
