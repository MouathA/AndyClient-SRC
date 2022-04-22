package com.viaversion.viaversion.bungee.handlers;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import java.util.function.*;
import com.viaversion.viaversion.bungee.util.*;
import com.viaversion.viaversion.exception.*;

@ChannelHandler.Sharable
public class BungeeEncodeHandler extends MessageToMessageEncoder
{
    private final UserConnection info;
    private boolean handledCompression;
    
    public BungeeEncodeHandler(final UserConnection info) {
        this.info = info;
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (!channelHandlerContext.channel().isActive()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.checkClientboundPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            list.add(byteBuf.retain());
            return;
        }
        final ByteBuf writeBytes = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
        final boolean handleCompressionOrder = this.handleCompressionOrder(channelHandlerContext, writeBytes);
        this.info.transformClientbound(writeBytes, CancelEncoderException::generate);
        if (handleCompressionOrder) {
            this.recompress(channelHandlerContext, writeBytes);
        }
        list.add(writeBytes.retain());
        writeBytes.release();
    }
    
    private boolean handleCompressionOrder(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) {
        if (!this.handledCompression && channelHandlerContext.pipeline().names().indexOf("compress") > channelHandlerContext.pipeline().names().indexOf("via-encoder")) {
            final ByteBuf decompress = BungeePipelineUtil.decompress(channelHandlerContext, byteBuf);
            if (byteBuf != decompress) {
                byteBuf.clear().writeBytes(decompress);
                decompress.release();
            }
            final ChannelHandler value = channelHandlerContext.pipeline().get("via-decoder");
            final ChannelHandler value2 = channelHandlerContext.pipeline().get("via-encoder");
            channelHandlerContext.pipeline().remove(value);
            channelHandlerContext.pipeline().remove(value2);
            channelHandlerContext.pipeline().addAfter("decompress", "via-decoder", value);
            channelHandlerContext.pipeline().addAfter("compress", "via-encoder", value2);
            this.handledCompression = true;
        }
        return true;
    }
    
    private void recompress(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) {
        final ByteBuf compress = BungeePipelineUtil.compress(channelHandlerContext, byteBuf);
        byteBuf.clear().writeBytes(compress);
        compress.release();
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
