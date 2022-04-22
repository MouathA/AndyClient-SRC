package net.minecraft.util;

import io.netty.channel.*;
import java.util.*;
import io.netty.buffer.*;
import net.minecraft.network.*;
import io.netty.handler.codec.*;

public class MessageDeserializer2 extends ByteToMessageDecoder
{
    private static final String __OBFID;
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) {
        byteBuf.markReaderIndex();
        final byte[] array = new byte[3];
        while (0 < array.length) {
            if (!byteBuf.isReadable()) {
                byteBuf.resetReaderIndex();
                return;
            }
            array[0] = byteBuf.readByte();
            if (array[0] >= 0) {
                final PacketBuffer packetBuffer = new PacketBuffer(Unpooled.wrappedBuffer(array));
                final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
                if (byteBuf.readableBytes() >= varIntFromBuffer) {
                    list.add(byteBuf.readBytes(varIntFromBuffer));
                    packetBuffer.release();
                    return;
                }
                byteBuf.resetReaderIndex();
                packetBuffer.release();
                return;
            }
            else {
                int n = 0;
                ++n;
            }
        }
        throw new CorruptedFrameException("length wider than 21-bit");
    }
    
    static {
        __OBFID = "CL_00001255";
    }
}
