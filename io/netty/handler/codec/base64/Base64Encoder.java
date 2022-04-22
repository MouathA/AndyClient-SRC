package io.netty.handler.codec.base64;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;

@ChannelHandler.Sharable
public class Base64Encoder extends MessageToMessageEncoder
{
    private final boolean breakLines;
    private final Base64Dialect dialect;
    
    public Base64Encoder() {
        this(true);
    }
    
    public Base64Encoder(final boolean b) {
        this(b, Base64Dialect.STANDARD);
    }
    
    public Base64Encoder(final boolean breakLines, final Base64Dialect dialect) {
        if (dialect == null) {
            throw new NullPointerException("dialect");
        }
        this.breakLines = breakLines;
        this.dialect = dialect;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        list.add(Base64.encode(byteBuf, byteBuf.readerIndex(), byteBuf.readableBytes(), this.breakLines, this.dialect));
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, list);
    }
}
