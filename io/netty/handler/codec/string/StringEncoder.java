package io.netty.handler.codec.string;

import io.netty.handler.codec.*;
import java.nio.charset.*;
import io.netty.channel.*;
import java.util.*;
import java.nio.*;
import io.netty.buffer.*;

@ChannelHandler.Sharable
public class StringEncoder extends MessageToMessageEncoder
{
    private final Charset charset;
    
    public StringEncoder() {
        this(Charset.defaultCharset());
    }
    
    public StringEncoder(final Charset charset) {
        if (charset == null) {
            throw new NullPointerException("charset");
        }
        this.charset = charset;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final CharSequence charSequence, final List list) throws Exception {
        if (charSequence.length() == 0) {
            return;
        }
        list.add(ByteBufUtil.encodeString(channelHandlerContext.alloc(), CharBuffer.wrap(charSequence), this.charset));
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (CharSequence)o, list);
    }
}
