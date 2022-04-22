package net.minecraft.network;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import javax.crypto.*;

public class NettyEncryptingDecoder extends MessageToMessageDecoder
{
    private final NettyEncryptionTranslator decryptionCodec;
    private static final String __OBFID;
    
    public NettyEncryptingDecoder(final Cipher cipher) {
        this.decryptionCodec = new NettyEncryptionTranslator(cipher);
    }
    
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws ShortBufferException {
        list.add(this.decryptionCodec.decipher(channelHandlerContext, byteBuf));
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final Object o, final List list) throws ShortBufferException {
        this.decode(channelHandlerContext, (ByteBuf)o, list);
    }
    
    static {
        __OBFID = "CL_00001238";
    }
}
