package net.minecraft.network;

import io.netty.channel.*;
import java.util.*;
import io.netty.handler.codec.*;
import io.netty.buffer.*;
import java.util.zip.*;

public class NettyCompressionDecoder extends ByteToMessageDecoder
{
    private final Inflater inflater;
    private int treshold;
    private static final String __OBFID;
    
    public NettyCompressionDecoder(final int treshold) {
        this.treshold = treshold;
        this.inflater = new Inflater();
    }
    
    @Override
    protected void decode(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final List list) throws DataFormatException {
        if (byteBuf.readableBytes() != 0) {
            final PacketBuffer packetBuffer = new PacketBuffer(byteBuf);
            final int varIntFromBuffer = packetBuffer.readVarIntFromBuffer();
            if (varIntFromBuffer == 0) {
                list.add(packetBuffer.readBytes(packetBuffer.readableBytes()));
            }
            else {
                if (varIntFromBuffer < this.treshold) {
                    throw new DecoderException("Badly compressed packet - size of " + varIntFromBuffer + " is below server threshold of " + this.treshold);
                }
                if (varIntFromBuffer > 2097152) {
                    throw new DecoderException("Badly compressed packet - size of " + varIntFromBuffer + " is larger than protocol maximum of " + 2097152);
                }
                final byte[] input = new byte[packetBuffer.readableBytes()];
                packetBuffer.readBytes(input);
                this.inflater.setInput(input);
                final byte[] array = new byte[varIntFromBuffer];
                this.inflater.inflate(array);
                list.add(Unpooled.wrappedBuffer(array));
                this.inflater.reset();
            }
        }
    }
    
    public void setCompressionTreshold(final int treshold) {
        this.treshold = treshold;
    }
    
    static {
        __OBFID = "CL_00002314";
    }
}
