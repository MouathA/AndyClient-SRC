package com.viaversion.viaversion.sponge.handlers;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.function.*;
import com.viaversion.viaversion.util.*;
import java.util.*;
import com.viaversion.viaversion.exception.*;

public class SpongeDecodeHandler extends ByteToMessageDecoder
{
    private final ByteToMessageDecoder minecraftDecoder;
    private final UserConnection info;
    
    public SpongeDecodeHandler(final UserConnection info, final ByteToMessageDecoder minecraftDecoder) {
        this.info = info;
        this.minecraftDecoder = minecraftDecoder;
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        if (!this.info.checkServerboundPacket()) {
            byteBuf.clear();
            throw CancelDecoderException.generate(null);
        }
        ByteBuf writeBytes = null;
        if (this.info.shouldTransformPacket()) {
            writeBytes = channelHandlerContext.alloc().buffer().writeBytes(byteBuf);
            this.info.transformServerbound(writeBytes, CancelDecoderException::generate);
        }
        list.addAll(PipelineUtil.callDecode(this.minecraftDecoder, channelHandlerContext, (writeBytes == null) ? byteBuf : writeBytes));
        if (writeBytes != null) {
            writeBytes.release();
        }
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        if (t instanceof CancelCodecException) {
            return;
        }
        super.exceptionCaught(channelHandlerContext, t);
    }
}
