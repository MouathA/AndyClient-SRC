package de.gerrygames.viarewind.protocol.protocol1_7_6_10to1_8.types;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.*;
import java.util.zip.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;

public class Chunk1_7_10Type extends PartialType
{
    public Chunk1_7_10Type(final ClientWorld clientWorld) {
        super(clientWorld, Chunk.class);
    }
    
    public Chunk read(final ByteBuf byteBuf, final ClientWorld clientWorld) throws Exception {
        throw new UnsupportedOperationException();
    }
    
    public void write(final ByteBuf byteBuf, final ClientWorld clientWorld, final Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        byteBuf.writeBoolean(chunk.isFullChunk());
        byteBuf.writeShort(chunk.getBitmask());
        byteBuf.writeShort(0);
        final ByteBuf buffer = byteBuf.alloc().buffer();
        final ByteBuf buffer2 = byteBuf.alloc().buffer();
        int n = 0;
        while (0 < chunk.getSections().length) {
            if ((chunk.getBitmask() & 0x1) == 0x0) {
                ++n;
            }
            else {
                final ChunkSection chunkSection = chunk.getSections()[0];
                while (true) {
                    buffer.writeByte(chunkSection.getFlatBlock(0, 0, 0) >> 4);
                    int n2 = 0;
                    ++n2;
                }
            }
        }
        buffer.writeBytes(buffer2);
        buffer2.release();
        while (0 < chunk.getSections().length) {
            if ((chunk.getBitmask() & 0x1) != 0x0) {
                chunk.getSections()[0].getLight().writeBlockLight(buffer);
            }
            ++n;
        }
        n = ((clientWorld != null && clientWorld.getEnvironment() == Environment.NORMAL) ? 1 : 0);
        if (chunk.isFullChunk() && chunk.isBiomeData()) {
            final int length = chunk.getBiomeData().length;
        }
        buffer.readerIndex(0);
        final byte[] array = new byte[buffer.readableBytes()];
        buffer.readBytes(array);
        buffer.release();
        final Deflater deflater = new Deflater(4);
        deflater.setInput(array, 0, array.length);
        deflater.finish();
        final byte[] array2 = new byte[array.length];
        deflater.deflate(array2);
        deflater.end();
        byteBuf.writeInt(0);
        byteBuf.writeBytes(array2, 0, 0);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o, final Object o2) throws Exception {
        this.write(byteBuf, (ClientWorld)o, (Chunk)o2);
    }
    
    @Override
    public Object read(final ByteBuf byteBuf, final Object o) throws Exception {
        return this.read(byteBuf, (ClientWorld)o);
    }
}
