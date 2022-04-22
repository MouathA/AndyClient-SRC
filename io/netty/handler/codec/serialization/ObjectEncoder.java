package io.netty.handler.codec.serialization;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.io.*;

@ChannelHandler.Sharable
public class ObjectEncoder extends MessageToByteEncoder
{
    private static final byte[] LENGTH_PLACEHOLDER;
    
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Serializable s, final ByteBuf byteBuf) throws Exception {
        final int writerIndex = byteBuf.writerIndex();
        final ByteBufOutputStream byteBufOutputStream = new ByteBufOutputStream(byteBuf);
        byteBufOutputStream.write(ObjectEncoder.LENGTH_PLACEHOLDER);
        final CompactObjectOutputStream compactObjectOutputStream = new CompactObjectOutputStream(byteBufOutputStream);
        compactObjectOutputStream.writeObject(s);
        compactObjectOutputStream.flush();
        compactObjectOutputStream.close();
        byteBuf.setInt(writerIndex, byteBuf.writerIndex() - writerIndex - 4);
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (Serializable)o, byteBuf);
    }
    
    static {
        LENGTH_PLACEHOLDER = new byte[4];
    }
}
