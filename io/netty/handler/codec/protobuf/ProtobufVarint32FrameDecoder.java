package io.netty.handler.codec.protobuf;

import io.netty.channel.*;
import io.netty.buffer.*;
import java.util.*;
import com.google.protobuf.*;
import io.netty.handler.codec.*;

public class ProtobufVarint32FrameDecoder extends ByteToMessageDecoder
{
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws Exception {
        byteBuf.markReaderIndex();
        final byte[] array = new byte[5];
        while (0 < array.length) {
            if (!byteBuf.isReadable()) {
                byteBuf.resetReaderIndex();
                return;
            }
            array[0] = byteBuf.readByte();
            if (array[0] >= 0) {
                final int rawVarint32 = CodedInputStream.newInstance(array, 0, 1).readRawVarint32();
                if (rawVarint32 < 0) {
                    throw new CorruptedFrameException("negative length: " + rawVarint32);
                }
                if (byteBuf.readableBytes() < rawVarint32) {
                    byteBuf.resetReaderIndex();
                    return;
                }
                list.add(byteBuf.readBytes(rawVarint32));
                return;
            }
            else {
                int n = 0;
                ++n;
            }
        }
        throw new CorruptedFrameException("length wider than 32-bit");
    }
}
