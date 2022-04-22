package com.viaversion.viaversion.velocity.handlers;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.exception.*;

@ChannelHandler.Sharable
public class VelocityEncodeHandler extends MessageToMessageEncoder
{
    private final UserConnection info;
    
    public VelocityEncodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (!this.info.checkOutgoingPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            list.add(byteBuf.retain());
            return;
        }
        final ByteBuf writeBytes = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
        this.info.transformOutgoing(writeBytes, CancelEncoderException::generate);
        list.add(writeBytes.retain());
        writeBytes.release();
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        if (t instanceof CancelCodecException) {
            return;
        }
        super.exceptionCaught(channelHandlerContext, t);
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, list);
    }
}
