package io.netty.handler.codec;

import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;

public class FixedLengthFrameDecoder extends ByteToMessageDecoder
{
    private final int frameLength;
    
    public FixedLengthFrameDecoder(final int frameLength) {
        if (frameLength <= 0) {
            throw new IllegalArgumentException("frameLength must be a positive integer: " + frameLength);
        }
        this.frameLength = frameLength;
    }
    
    @Override
    protected final void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        final Object decode = this.decode(channelHandlerContext, byteBuf);
        if (decode != null) {
            list.add(decode);
        }
    }
    
    protected Object decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws Exception {
        if (byteBuf.readableBytes() < this.frameLength) {
            return null;
        }
        return byteBuf.readSlice(this.frameLength).retain();
    }
}
