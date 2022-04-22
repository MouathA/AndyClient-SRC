package com.viaversion.viaversion.bukkit.handlers;

import io.netty.handler.codec.*;
import java.lang.reflect.*;
import com.viaversion.viaversion.api.connection.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.handlers.*;
import com.viaversion.viaversion.util.*;
import java.util.function.*;
import com.viaversion.viaversion.bukkit.util.*;
import com.viaversion.viaversion.exception.*;
import com.viaversion.viaversion.api.protocol.packet.*;
import com.viaversion.viaversion.api.*;

public class BukkitEncodeHandler extends MessageToByteEncoder implements ViaCodecHandler
{
    private static Field versionField;
    private final UserConnection info;
    private final MessageToByteEncoder minecraftEncoder;
    
    public BukkitEncodeHandler(final UserConnection info, final MessageToByteEncoder minecraftEncoder) {
        this.info = info;
        this.minecraftEncoder = minecraftEncoder;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        if (BukkitEncodeHandler.versionField != null) {
            BukkitEncodeHandler.versionField.set(this.minecraftEncoder, BukkitEncodeHandler.versionField.get(this));
        }
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
        if (PipelineUtil.containsCause(t, CancelCodecException.class)) {
            return;
        }
        super.exceptionCaught(channelHandlerContext, t);
        if (!NMSUtil.isDebugPropertySet() && PipelineUtil.containsCause(t, InformativeException.class) && (this.info.getProtocolInfo().getState() != State.HANDSHAKE || Via.getManager().isDebug())) {
            t.printStackTrace();
        }
    }
    
    static {
        (BukkitEncodeHandler.versionField = NMSUtil.nms("PacketEncoder", "net.minecraft.network.PacketEncoder").getDeclaredField("version")).setAccessible(true);
    }
}
