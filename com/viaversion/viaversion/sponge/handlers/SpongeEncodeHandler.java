package com.viaversion.viaversion.sponge.handlers;

import io.netty.handler.codec.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.handlers.*;
import com.viaversion.viaversion.util.*;
import java.util.function.*;
import com.viaversion.viaversion.exception.*;

public class SpongeEncodeHandler extends MessageToByteEncoder implements ViaCodecHandler
{
    private final UserConnection info;
    private final MessageToByteEncoder minecraftEncoder;
    
    public SpongeEncodeHandler(final UserConnection info, final MessageToByteEncoder minecraftEncoder) {
        this.info = info;
        this.minecraftEncoder = minecraftEncoder;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        if (!(o instanceof ByteBuf)) {
            PipelineUtil.callEncode(this.minecraftEncoder, new ChannelHandlerContextWrapper(channelHandlerContext, this), o, byteBuf);
        }
        else {
            byteBuf.writeBytes((ByteBuf)o);
        }
        this.transform(byteBuf);
    }
    
    @Override
    public void transform(final ByteBuf byteBuf) throws Exception {
        if (!this.info.checkClientboundPacket()) {
            throw CancelEncoderException.generate(null);
        }
        if (!this.info.shouldTransformPacket()) {
            return;
        }
        this.info.transformClientbound(byteBuf, CancelEncoderException::generate);
    }
    
    @Override
    public void exceptionCaught(final ChannelHandlerContext channelHandlerContext, final Throwable t) throws Exception {
        if (t instanceof CancelCodecException) {
            return;
        }
        super.exceptionCaught(channelHandlerContext, t);
    }
}
