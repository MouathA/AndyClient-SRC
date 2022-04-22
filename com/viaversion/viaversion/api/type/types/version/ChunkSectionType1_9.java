package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.util.*;

public class ChunkSectionType1_9 extends Type
{
    private static final int GLOBAL_PALETTE = 13;
    
    public ChunkSectionType1_9() {
        super("Chunk Section Type", ChunkSection.class);
    }
    
    @Override
    public ChunkSection read(final ByteBuf byteBuf) throws Exception {
        byteBuf.readUnsignedByte();
        if (13 == 0) {}
        if (13 < 4) {}
        if (13 > 8) {}
        final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
        final ChunkSectionImpl chunkSectionImpl = (13 != 13) ? new ChunkSectionImpl(true, primitive) : new ChunkSectionImpl(true);
        while (0 < primitive) {
            if (13 != 13) {
                chunkSectionImpl.addPaletteEntry(Type.VAR_INT.readPrimitive(byteBuf));
            }
            else {
                Type.VAR_INT.readPrimitive(byteBuf);
            }
            int n = 0;
            ++n;
        }
        final long[] array = new long[Type.VAR_INT.readPrimitive(byteBuf)];
        if (array.length > 0) {
            final int n2 = (int)Math.ceil(53248 / 64.0);
            if (array.length != n2) {
                throw new IllegalStateException("Block data length (" + array.length + ") does not match expected length (" + n2 + ")! bitsPerBlock=" + 13 + ", originalBitsPerBlock=" + 13);
            }
            while (0 < array.length) {
                array[0] = byteBuf.readLong();
                int n3 = 0;
                ++n3;
            }
            CompactArrayUtil.iterateCompactArray(13, 4096, array, (13 == 13) ? chunkSectionImpl::setFlatBlock : chunkSectionImpl::setPaletteIndex);
        }
        return chunkSectionImpl;
    }
    
    public void write(final ByteBuf byteBuf, final ChunkSection chunkSection) throws Exception {
        while (chunkSection.getPaletteSize() > 8192) {
            int n = 0;
            ++n;
        }
        if (13 > 8) {}
        byteBuf.writeByte(13);
        if (13 != 13) {
            Type.VAR_INT.writePrimitive(byteBuf, chunkSection.getPaletteSize());
            while (0 < chunkSection.getPaletteSize()) {
                Type.VAR_INT.writePrimitive(byteBuf, chunkSection.getPaletteEntry(0));
                int n2 = 0;
                ++n2;
            }
        }
        else {
            Type.VAR_INT.writePrimitive(byteBuf, 0);
        }
        final long[] compactArray = CompactArrayUtil.createCompactArray(13, 4096, (13 == 13) ? chunkSection::getFlatBlock : chunkSection::getPaletteIndex);
        Type.VAR_INT.writePrimitive(byteBuf, compactArray.length);
        final long[] array = compactArray;
        while (0 < array.length) {
            byteBuf.writeLong(array[0]);
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
