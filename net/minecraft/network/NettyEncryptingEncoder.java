package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import javax.crypto.*;

public class NettyEncryptingEncoder extends MessageToByteEncoder
{
    private final NettyEncryptionTranslator encryptionCodec;
    private static final String __OBFID;
    
    public NettyEncryptingEncoder(final Cipher cipher) {
        this.encryptionCodec = new NettyEncryptionTranslator(cipher);
    }
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws ShortBufferException {
        this.encryptionCodec.cipher(byteBuf, byteBuf2);
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws ShortBufferException {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
    
    static {
        __OBFID = "CL_00001239";
    }
}
