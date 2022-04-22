package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.nio.*;

public class ChunkSectionType1_8 extends Type
{
    public ChunkSectionType1_8() {
        super("Chunk Section Type", ChunkSection.class);
    }
    
    @Override
    public ChunkSection read(final ByteBuf byteBuf) throws Exception {
        final ChunkSectionImpl chunkSectionImpl = new ChunkSectionImpl(true);
        chunkSectionImpl.addPaletteEntry(0);
        final ByteBuf order = byteBuf.order(ByteOrder.LITTLE_ENDIAN);
        while (0 < 4096) {
            final short short1 = order.readShort();
            chunkSectionImpl.setBlockWithData(0, short1 >> 4, short1 & 0xF);
            int n = 0;
            ++n;
        }
        return chunkSectionImpl;
    }
    
    public void write(final ByteBuf byteBuf, final ChunkSection chunkSection) throws Exception {
        while (0 < 16) {
            while (0 < 16) {
                while (0 < 16) {
                    final int flatBlock = chunkSection.getFlatBlock(0, 0, 0);
                    byteBuf.writeByte(flatBlock);
                    byteBuf.writeByte(flatBlock >> 8);
                    int n = 0;
                    ++n;
                }
                int n2 = 0;
                ++n2;
            }
            int n3 = 0;
            ++n3;
        }
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (ChunkSection)o);
    }
}
