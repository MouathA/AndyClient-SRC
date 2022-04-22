package com.viaversion.viaversion.protocols.protocol1_14to1_13_2.types;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public class Chunk1_14Type extends Type
{
    public Chunk1_14Type() {
        super(Chunk.class);
    }
    
    @Override
    public Chunk read(final ByteBuf byteBuf) throws Exception {
        byteBuf.readInt();
        byteBuf.readInt();
        byteBuf.readBoolean();
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        final CompoundTag compoundTag = (CompoundTag)Type.NBT.read(byteBuf);
        Type.VAR_INT.readPrimitive(byteBuf);
        final ChunkSection[] array = new ChunkSection[16];
        while (true) {
            if ((primitive & 0x1) != 0x0) {
                byteBuf.readShort();
                final ChunkSection chunkSection = (ChunkSection)Types1_13.CHUNK_SECTION.read(byteBuf);
                chunkSection.setNonAirBlocksCount(0);
                array[0] = chunkSection;
            }
            int n = 0;
            ++n;
        }
    }
    
    public void write(final ByteBuf byteBuf, final Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        byteBuf.writeBoolean(chunk.isFullChunk());
        Type.VAR_INT.writePrimitive(byteBuf, chunk.getBitmask());
        Type.NBT.write(byteBuf, chunk.getHeightMap());
        final ByteBuf buffer = byteBuf.alloc().buffer();
        while (true) {
            final ChunkSection chunkSection = chunk.getSections()[0];
            if (chunkSection != null) {
                buffer.writeShort(chunkSection.getNonAirBlocksCount());
                Types1_13.CHUNK_SECTION.write(buffer, chunkSection);
            }
            int n = 0;
            ++n;
        }
    }
    
    @Override
    public Class getBaseClass() {
        return BaseChunkType.class;
    }
    
    @Override
    public Object read(final ByteBuf byteBuf) throws Exception {
        return this.read(byteBuf);
    }
    
    @Override
    public void write(final ByteBuf byteBuf, final Object o) throws Exception {
        this.write(byteBuf, (Chunk)o);
    }
}
