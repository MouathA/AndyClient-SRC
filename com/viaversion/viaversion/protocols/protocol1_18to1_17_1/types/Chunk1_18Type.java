package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.types;

import com.viaversion.viaversion.api.type.*;
import com.google.common.base.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import com.viaversion.viaversion.api.*;
import com.viaversion.viaversion.api.type.types.version.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.api.minecraft.blockentity.*;
import java.util.*;
import com.viaversion.viaversion.api.type.types.minecraft.*;

public final class Chunk1_18Type extends Type
{
    private final ChunkSectionType1_18 sectionType;
    private final int ySectionCount;
    
    public Chunk1_18Type(final int ySectionCount, final int n, final int n2) {
        super(Chunk.class);
        Preconditions.checkArgument(ySectionCount > 0);
        this.sectionType = new ChunkSectionType1_18(n, n2);
        this.ySectionCount = ySectionCount;
    }
    
    @Override
    public Chunk read(final ByteBuf byteBuf) throws Exception {
        final int int1 = byteBuf.readInt();
        final int int2 = byteBuf.readInt();
        final CompoundTag compoundTag = (CompoundTag)Type.NBT.read(byteBuf);
        final ByteBuf bytes = byteBuf.readBytes(Type.VAR_INT.readPrimitive(byteBuf));
        final ChunkSection[] array = new ChunkSection[this.ySectionCount];
        while (0 < this.ySectionCount) {
            array[0] = this.sectionType.read(bytes);
            int primitive = 0;
            ++primitive;
        }
        if (bytes.readableBytes() > 0 && Via.getManager().isDebug()) {
            Via.getPlatform().getLogger().warning("Found " + bytes.readableBytes() + " more bytes than expected while reading the chunk: " + int1 + "/" + int2);
        }
        bytes.release();
        int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        final ArrayList<Object> list = new ArrayList<Object>(0);
        while (0 < 0) {
            list.add(Types1_18.BLOCK_ENTITY.read(byteBuf));
            int n = 0;
            ++n;
        }
        return new Chunk1_18(int1, int2, array, compoundTag, list);
    }
    
    public void write(final ByteBuf byteBuf, final Chunk chunk) throws Exception {
        byteBuf.writeInt(chunk.getX());
        byteBuf.writeInt(chunk.getZ());
        Type.NBT.write(byteBuf, chunk.getHeightMap());
        final ByteBuf buffer = byteBuf.alloc().buffer();
        final ChunkSection[] sections = chunk.getSections();
        while (0 < sections.length) {
            this.sectionType.write(buffer, sections[0]);
            int n = 0;
            ++n;
        }
        buffer.readerIndex(0);
        Type.VAR_INT.writePrimitive(byteBuf, buffer.readableBytes());
        byteBuf.writeBytes(buffer);
        buffer.release();
        Type.VAR_INT.writePrimitive(byteBuf, chunk.blockEntities().size());
        final Iterator<BlockEntity> iterator = chunk.blockEntities().iterator();
        while (iterator.hasNext()) {
            Types1_18.BLOCK_ENTITY.write(byteBuf, iterator.next());
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
