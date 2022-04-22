package com.viaversion.viaversion.protocols.protocol1_15to1_14_4.types;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public class Chunk1_15Type extends Type
{
    private static final CompoundTag[] EMPTY_COMPOUNDS;
    
    public Chunk1_15Type() {
        super(Chunk.class);
    }
    
    @Override
    public Chunk read(final ByteBuf byteBuf) throws Exception {
        final int int1 = byteBuf.readInt();
        final int int2 = byteBuf.readInt();
        final boolean boolean1 = byteBuf.readBoolean();
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        final CompoundTag compoundTag = (CompoundTag)Type.NBT.read(byteBuf);
        final int[] array = (int[])(boolean1 ? new int[1024] : null);
        if (boolean1) {
            while (0 < 1024) {
                array[0] = byteBuf.readInt();
                int n = 0;
                ++n;
            }
        }
        Type.VAR_INT.readPrimitive(byteBuf);
        final ChunkSection[] array2 = new ChunkSection[16];
        while (0 < 16) {
            if ((primitive & 0x1) != 0x0) {
                final short short1 = byteBuf.readShort();
                final ChunkSection chunkSection = (ChunkSection)Types1_13.CHUNK_SECTION.read(byteBuf);
                chunkSection.setNonAirBlocksCount(short1);
                array2[0] = chunkSection;
            }
            int n2 = 0;
            ++n2;
        }
        final ArrayList list = new ArrayList(Arrays.asList((Object[])Type.NBT_ARRAY.read(byteBuf)));
        if (byteBuf.readableBytes() > 0) {
            final byte[] array3 = (byte[])Type.REMAINING_BYTES.read(byteBuf);
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Found " + array3.length + " more bytes than expected while reading the chunk: " + int1 + "/" + int2);
            }
        }
        return new BaseChunk(int1, int2, boolean1, false, primitive, array2, array, compoundTag, list);
    }
    
    public void write(final ByteBuf byteBuf, final Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        byteBuf.writeBoolean(chunk.isFullChunk());
        Type.VAR_INT.writePrimitive(byteBuf, chunk.getBitmask());
        Type.NBT.write(byteBuf, chunk.getHeightMap());
        int length = 0;
        if (chunk.isBiomeData()) {
            final int[] biomeData = chunk.getBiomeData();
            length = biomeData.length;
            while (0 < 0) {
                byteBuf.writeInt(biomeData[0]);
                int n = 0;
                ++n;
            }
        }
        final ByteBuf buffer = byteBuf.alloc().buffer();
        while (0 < 16) {
            final ChunkSection chunkSection = chunk.getSections()[0];
            if (chunkSection != null) {
                buffer.writeShort(chunkSection.getNonAirBlocksCount());
                Types1_13.CHUNK_SECTION.write(buffer, chunkSection);
            }
            ++length;
        }
        buffer.readerIndex(0);
        Type.VAR_INT.writePrimitive(byteBuf, buffer.readableBytes());
        byteBuf.writeBytes(buffer);
        buffer.release();
        Type.NBT_ARRAY.write(byteBuf, chunk.getBlockEntities().toArray(Chunk1_15Type.EMPTY_COMPOUNDS));
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
    
    static {
        EMPTY_COMPOUNDS = new CompoundTag[0];
    }
}
