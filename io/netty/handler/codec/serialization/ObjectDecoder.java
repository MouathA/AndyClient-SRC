package io.netty.handler.codec.serialization;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import io.netty.buffer.*;
import java.io.*;

public class ObjectDecoder extends LengthFieldBasedFrameDecoder
{
    private final ClassResolver classResolver;
    
    public ObjectDecoder(final ClassResolver classResolver) {
        this(1048576, classResolver);
    }
    
    public ObjectDecoder(final int n, final ClassResolver classResolver) {
        super(n, 0, 4, 0, 4);
        this.classResolver = classResolver;
    }
    
    @Override
    protected Object decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf) throws Exception {
        final ByteBuf byteBuf2 = (ByteBuf)super.decode(channelHandlerContext, byteBuf);
        if (byteBuf2 == null) {
            return null;
        }
        final CompactObjectInputStream compactObjectInputStream = new CompactObjectInputStream(new ByteBufInputStream(byteBuf2), this.classResolver);
        final Object object = compactObjectInputStream.readObject();
        compactObjectInputStream.close();
        return object;
    }
    
    @Override
    protected ByteBuf extractFrame(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final int n, final int n2) {
        return byteBuf.slice(n, n2);
    }
}
