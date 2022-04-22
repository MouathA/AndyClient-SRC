package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.type.*;
import io.netty.buffer.*;
import com.viaversion.viaversion.api.minecraft.chunks.*;

public final class ChunkSectionType1_18 extends Type
{
    private final PaletteType1_18 blockPaletteType;
    private final PaletteType1_18 biomePaletteType;
    
    public ChunkSectionType1_18(final int n, final int n2) {
        super("Chunk Section Type", ChunkSection.class);
        this.blockPaletteType = new PaletteType1_18(PaletteType.BLOCKS, n);
        this.biomePaletteType = new PaletteType1_18(PaletteType.BIOMES, n2);
    }
    
    @Override
    public ChunkSection read(final ByteBuf byteBuf) throws Exception {
        final ChunkSectionImpl chunkSectionImpl = new ChunkSectionImpl();
        chunkSectionImpl.setNonAirBlocksCount(byteBuf.readShort());
        chunkSectionImpl.addPalette(PaletteType.BLOCKS, this.blockPaletteType.read(byteBuf));
        chunkSectionImpl.addPalette(PaletteType.BIOMES, this.biomePaletteType.read(byteBuf));
        return chunkSectionImpl;
    }
    
    public void write(final ByteBuf byteBuf, final ChunkSection chunkSection) throws Exception {
        byteBuf.writeShort(chunkSection.getNonAirBlocksCount());
        this.blockPaletteType.write(byteBuf, chunkSection.palette(PaletteType.BLOCKS));
        this.biomePaletteType.write(byteBuf, chunkSection.palette(PaletteType.BIOMES));
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
