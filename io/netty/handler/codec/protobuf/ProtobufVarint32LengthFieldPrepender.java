package io.netty.handler.codec.protobuf;

import io.netty.handler.codec.*;
import io.netty.channel.*;
import com.google.protobuf.*;
import io.netty.buffer.*;
import java.io.*;

@ChannelHandler.Sharable
public class ProtobufVarint32LengthFieldPrepender extends MessageToByteEncoder
{
    protected void encode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) throws Exception {
        final int readableBytes = byteBuf.readableBytes();
        final int computeRawVarint32Size = CodedOutputStream.computeRawVarint32Size(readableBytes);
        byteBuf2.ensureWritable(computeRawVarint32Size + readableBytes);
        final CodedOutputStream instance = CodedOutputStream.newInstance((OutputStream)new ByteBufOutputStream(byteBuf2), computeRawVarint32Size);
        instance.writeRawVarint32(readableBytes);
        instance.flush();
        byteBuf2.writeBytes(byteBuf, byteBuf.readerIndex(), readableBytes);
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) throws Exception {
        this.encode(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
}
