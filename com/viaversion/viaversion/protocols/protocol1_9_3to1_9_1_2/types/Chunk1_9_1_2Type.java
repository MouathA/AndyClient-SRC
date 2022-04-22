package com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.types;

import com.viaversion.viaversion.protocols.protocol1_9_3to1_9_1_2.storage.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.type.*;
import java.util.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public class Chunk1_9_1_2Type extends PartialType
{
    public Chunk1_9_1_2Type(final ClientWorld clientWorld) {
        super(clientWorld, Chunk.class);
    }
    
    public Chunk read(final ByteBuf byteBuf, final ClientWorld clientWorld) throws Exception {
        byteBuf.readInt();
        byteBuf.readInt();
        byteBuf.readBoolean();
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        Type.VAR_INT.readPrimitive(byteBuf);
        final BitSet set = new BitSet(16);
        final ChunkSection[] array = new ChunkSection[16];
        while (true) {
            if ((primitive & 0x1) != 0x0) {
                set.set(0);
            }
            int n = 0;
            ++n;
        }
    }
    
    public void write(final ByteBuf byteBuf, final ClientWorld clientWorld, final Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        byteBuf.writeBoolean(chunk.isFullChunk());
        Type.VAR_INT.writePrimitive(byteBuf, chunk.getBitmask());
        final ByteBuf buffer = byteBuf.alloc().buffer();
        while (true) {
            final ChunkSection chunkSection = chunk.getSections()[0];
            if (chunkSection != null) {
                Types1_9.CHUNK_SECTION.write(buffer, chunkSection);
                chunkSection.getLight().writeBlockLight(buffer);
                if (chunkSection.getLight().hasSkyLight()) {
                    chunkSection.getLight().writeSkyLight(buffer);
                }
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
    public void write(final ByteBuf byteBuf, final Object o, final Object o2) throws Exception {
        this.write(byteBuf, (ClientWorld)o, (Chunk)o2);
    }
    
    @Override
    public Object read(final ByteBuf byteBuf, final Object o) throws Exception {
        return this.read(byteBuf, (ClientWorld)o);
    }
}
