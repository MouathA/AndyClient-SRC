package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.util.*;

public class ChunkSectionType1_13 extends Type
{
    private static final int GLOBAL_PALETTE = 14;
    
    public ChunkSectionType1_13() {
        super("Chunk Section Type", ChunkSection.class);
    }
    
    @Override
    public ChunkSection read(final ByteBuf byteBuf) throws Exception {
        byteBuf.readUnsignedByte();
        if (14 == 0 || 14 > 8) {}
        ChunkSectionImpl chunkSectionImpl;
        if (14 != 14) {
            final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
            chunkSectionImpl = new ChunkSectionImpl(true, primitive);
            while (0 < primitive) {
                chunkSectionImpl.addPaletteEntry(Type.VAR_INT.readPrimitive(byteBuf));
                int n = 0;
                ++n;
            }
        }
        else {
            chunkSectionImpl = new ChunkSectionImpl(true);
        }
        final long[] array = new long[Type.VAR_INT.readPrimitive(byteBuf)];
        if (array.length > 0) {
            final int n = (int)Math.ceil(57344 / 64.0);
            if (array.length != 0) {
                throw new IllegalStateException("Block data length (" + array.length + ") does not match expected length (" + 0 + ")! bitsPerBlock=" + 14 + ", originalBitsPerBlock=" + 14);
            }
            while (0 < array.length) {
                array[0] = byteBuf.readLong();
                int n2 = 0;
                ++n2;
            }
            CompactArrayUtil.iterateCompactArray(14, 4096, array, (14 == 14) ? chunkSectionImpl::setFlatBlock : chunkSectionImpl::setPaletteIndex);
        }
        return chunkSectionImpl;
    }
    
    public void write(final ByteBuf byteBuf, final ChunkSection chunkSection) throws Exception {
        while (chunkSection.getPaletteSize() > 16384) {
            int n = 0;
            ++n;
        }
        if (14 > 8) {}
        byteBuf.writeByte(14);
        if (14 != 14) {
            Type.VAR_INT.writePrimitive(byteBuf, chunkSection.getPaletteSize());
            while (0 < chunkSection.getPaletteSize()) {
                Type.VAR_INT.writePrimitive(byteBuf, chunkSection.getPaletteEntry(0));
                int n2 = 0;
                ++n2;
            }
        }
        final long[] compactArray = CompactArrayUtil.createCompactArray(14, 4096, (14 == 14) ? chunkSection::getFlatBlock : chunkSection::getPaletteIndex);
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
