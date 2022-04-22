package com.viaversion.viaversion.bungee.handlers;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.exception.*;

@ChannelHandler.Sharable
public class BungeeDecodeHandler extends MessageToMessageDecoder
{
    private final UserConnection info;
    
    public BungeeDecodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (!channelHandlerContext.channel().isActive()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.info.checkServerboundPacket()) {
            throw CancelDecoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            list.add(byteBuf.retain());
            return;
        }
        final ByteBuf writeBytes = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
        this.info.transformServerbound(writeBytes, CancelDecoderException::generate);
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
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws Exception {
        this.decode(channelHandlerContext, (ByteBuf)o, list);
    }
}
