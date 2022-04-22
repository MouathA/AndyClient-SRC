package com.viaversion.viaversion.velocity.handlers;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.buffer.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.exception.*;
import io.netty.channel.*;

@ChannelHandler.Sharable
public class VelocityDecodeHandler extends MessageToMessageDecoder
{
    private final UserConnection info;
    
    public VelocityDecodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (!this.info.checkIncomingPacket()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            list.add(byteBuf.retain());
            return;
        }
        final ByteBuf writeBytes = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
        this.info.transformIncoming(writeBytes, CancelDecoderException::generate);
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
    public void userEventTriggered(final ChannelHandlerContext channelHandlerContext, final Object o) throws Exception {
        if (o != VelocityChannelInitializer.COMPRESSION_ENABLED_EVENT) {
            super.userEventTriggered(channelHandlerContext, o);
            return;
        }
        final ChannelPipeline pipeline = channelHandlerContext.pipeline();
        final ChannelHandler value = pipeline.get("via-encoder");
        pipeline.remove(value);
        pipeline.addBefore("minecraft-encoder", "via-encoder", value);
        final ChannelHandler value2 = pipeline.get("via-decoder");
        pipeline.remove(value2);
        pipeline.addBefore("minecraft-decoder", "via-decoder", value2);
        super.userEventTriggered(channelHandlerContext, o);
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)o, list);
    }
}
