package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;
import com.viaversion.viaversion.util.*;

public class ChunkSectionType1_16 extends Type
{
    private static final int GLOBAL_PALETTE = 15;
    
    public ChunkSectionType1_16() {
        super("Chunk Section Type", ChunkSection.class);
    }
    
    @Override
    public ChunkSection read(final ByteBuf byteBuf) throws Exception {
        byteBuf.readUnsignedByte();
        if (15 == 0 || 15 > 8) {}
        ChunkSectionImpl chunkSectionImpl;
        if (15 != 15) {
            final int primitive = Type.VAR_INT.readPrimitive(byteBuf);
            chunkSectionImpl = new ChunkSectionImpl(false, primitive);
            while (0 < primitive) {
                chunkSectionImpl.addPaletteEntry(Type.VAR_INT.readPrimitive(byteBuf));
                int n = 0;
                ++n;
            }
        }
        else {
            chunkSectionImpl = new ChunkSectionImpl(false);
        }
        final long[] array = new long[Type.VAR_INT.readPrimitive(byteBuf)];
        if (array.length > 0) {
            final int n = 0;
            if (array.length != 37) {
                throw new IllegalStateException("Block data length (" + array.length + ") does not match expected length (" + 37 + ")! bitsPerBlock=" + 15 + ", originalBitsPerBlock=" + 15);
            }
            while (0 < array.length) {
                array[0] = byteBuf.readLong();
                int n2 = 0;
                ++n2;
            }
            CompactArrayUtil.iterateCompactArrayWithPadding(15, 4096, array, (15 == 15) ? chunkSectionImpl::setFlatBlock : chunkSectionImpl::setPaletteIndex);
        }
        return chunkSectionImpl;
    }
    
    public void write(final ByteBuf byteBuf, final ChunkSection chunkSection) throws Exception {
        while (chunkSection.getPaletteSize() > 32768) {
            int n = 0;
            ++n;
        }
        if (15 > 8) {}
        byteBuf.writeByte(15);
        if (15 != 15) {
            Type.VAR_INT.writePrimitive(byteBuf, chunkSection.getPaletteSize());
            while (0 < chunkSection.getPaletteSize()) {
                Type.VAR_INT.writePrimitive(byteBuf, chunkSection.getPaletteEntry(0));
                int n2 = 0;
                ++n2;
            }
        }
        final long[] compactArrayWithPadding = CompactArrayUtil.createCompactArrayWithPadding(15, 4096, (15 == 15) ? chunkSection::getFlatBlock : chunkSection::getPaletteIndex);
        Type.VAR_INT.writePrimitive(byteBuf, compactArrayWithPadding.length);
        final long[] array = compactArrayWithPadding;
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
