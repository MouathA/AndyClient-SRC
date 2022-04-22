package io.netty.handler.codec.string;

import io.netty.handler.codec.*;
import java.nio.charset.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;

@ChannelHandler.Sharable
public class StringDecoder extends MessageToMessageDecoder
{
    private final Charset charset;
    
    public StringDecoder() {
        this(Charset.defaultCharset());
    }
    
    public StringDecoder(final Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        list.add(byteBuf.toString(this.charset));
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)o, list);
    }
}
