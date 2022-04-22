package net.minecraft.network;

import io.netty.handler.codec.*;
import java.util.zip.*;
import io.netty.channel.*;
import io.netty.buffer.*;

public class NettyCompressionEncoder extends MessageToByteEncoder
{
    private final byte[] buffer;
    private final Deflater deflater;
    private int treshold;
    private static final String __OBFID;
    
    public NettyCompressionEncoder(final int treshold) {
        this.buffer = new byte[8192];
        this.treshold = treshold;
        this.deflater = new Deflater();
    }
    
    protected void compress(final ChannelHandlerContext channelHandlerContext, final ByteBuf byteBuf, final ByteBuf byteBuf2) {
        final int readableBytes = byteBuf.readableBytes();
        final PacketBuffer packetBuffer = new PacketBuffer(byteBuf2);
        if (readableBytes < this.treshold) {
            packetBuffer.writeVarIntToBuffer(0);
            packetBuffer.writeBytes(byteBuf);
        }
        else {
            final byte[] array = new byte[readableBytes];
            byteBuf.readBytes(array);
            packetBuffer.writeVarIntToBuffer(array.length);
            this.deflater.setInput(array, 0, readableBytes);
            this.deflater.finish();
            while (!this.deflater.finished()) {
                packetBuffer.writeBytes(this.buffer, 0, this.deflater.deflate(this.buffer));
            }
            this.deflater.reset();
        }
    }
    
    public void setCompressionTreshold(final int treshold) {
        this.treshold = treshold;
    }
    
    @Override
    protected void encode(final ChannelHandlerContext channelHandlerContext, final Object o, final ByteBuf byteBuf) {
        this.compress(channelHandlerContext, (ByteBuf)o, byteBuf);
    }
    
    static {
        __OBFID = "CL_00002313";
    }
}
