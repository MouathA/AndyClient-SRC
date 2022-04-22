package com.viaversion.viaversion.protocols.protocol1_17to1_16_4.types;

import com.viaversion.viaversion.api.type.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.google.common.base.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import java.util.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public final class Chunk1_17Type extends Type
{
    private static final CompoundTag[] EMPTY_COMPOUNDS;
    private final int ySectionCount;
    
    public Chunk1_17Type(final int ySectionCount) {
        super(Chunk.class);
        Preconditions.checkArgument(ySectionCount > 0);
        this.ySectionCount = ySectionCount;
    }
    
    @Override
    public Chunk read(final ByteBuf byteBuf) throws Exception {
        final int int1 = byteBuf.readInt();
        final int int2 = byteBuf.readInt();
        final BitSet value = BitSet.valueOf((long[])Type.LONG_ARRAY_PRIMITIVE.read(byteBuf));
        final CompoundTag compoundTag = (CompoundTag)Type.NBT.read(byteBuf);
        final int[] array = (int[])Type.VAR_INT_ARRAY_PRIMITIVE.read(byteBuf);
        Type.VAR_INT.readPrimitive(byteBuf);
        final ChunkSection[] array2 = new ChunkSection[this.ySectionCount];
        while (0 < this.ySectionCount) {
            if (value.get(0)) {
                final short short1 = byteBuf.readShort();
                final ChunkSection chunkSection = (ChunkSection)Types1_16.CHUNK_SECTION.read(byteBuf);
                chunkSection.setNonAirBlocksCount(short1);
                array2[0] = chunkSection;
            }
            int n = 0;
            ++n;
        }
        final ArrayList list = new ArrayList(Arrays.asList((Object[])Type.NBT_ARRAY.read(byteBuf)));
        if (byteBuf.readableBytes() > 0) {
            final byte[] array3 = (byte[])Type.REMAINING_BYTES.read(byteBuf);
            if (Via.getManager().isDebug()) {
                Via.getPlatform().getLogger().warning("Found " + array3.length + " more bytes than expected while reading the chunk: " + int1 + "/" + int2);
            }
        }
        return new BaseChunk(int1, int2, true, false, value, array2, array, compoundTag, list);
    }
    
    public void write(final ByteBuf byteBuf, final Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        Type.LONG_ARRAY_PRIMITIVE.write(byteBuf, chunk.getChunkMask().toLongArray());
        Type.NBT.write(byteBuf, chunk.getHeightMap());
        Type.VAR_INT_ARRAY_PRIMITIVE.write(byteBuf, chunk.getBiomeData());
        final ByteBuf buffer = byteBuf.alloc().buffer();
        final ChunkSection[] sections = chunk.getSections();
        while (0 < sections.length) {
            final ChunkSection chunkSection = sections[0];
            if (chunkSection != null) {
                buffer.writeShort(chunkSection.getNonAirBlocksCount());
                Types1_16.CHUNK_SECTION.write(buffer, chunkSection);
            }
            int n = 0;
            ++n;
        }
        buffer.readerIndex(0);
        Type.VAR_INT.writePrimitive(byteBuf, buffer.readableBytes());
        byteBuf.writeBytes(buffer);
        buffer.release();
        Type.NBT_ARRAY.write(byteBuf, chunk.getBlockEntities().toArray(Chunk1_17Type.EMPTY_COMPOUNDS));
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
