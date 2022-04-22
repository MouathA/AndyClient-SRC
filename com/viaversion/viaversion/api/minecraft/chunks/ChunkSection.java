package com.viaversion.viaversion.api.minecraft.chunks;

public interface ChunkSection
{
    public static final int SIZE = 4096;
    public static final int BIOME_SIZE = 64;
    
    default int index(final int n, final int n2, final int n3) {
        return n2 << 8 | n3 << 4 | n;
    }
    
    @Deprecated
    default int getFlatBlock(final int n) {
        return this.palette(PaletteType.BLOCKS).idAt(n);
    }
    
    @Deprecated
    default int getFlatBlock(final int n, final int n2, final int n3) {
        return this.getFlatBlock(index(n, n2, n3));
    }
    
    @Deprecated
    default void setFlatBlock(final int n, final int n2) {
        this.palette(PaletteType.BLOCKS).setIdAt(n, n2);
    }
    
    @Deprecated
    default void setFlatBlock(final int n, final int n2, final int n3, final int n4) {
        this.setFlatBlock(index(n, n2, n3), n4);
    }
    
    @Deprecated
    default int getBlockWithoutData(final int n, final int n2, final int n3) {
        return this.getFlatBlock(n, n2, n3) >> 4;
    }
    
    @Deprecated
    default int getBlockData(final int n, final int n2, final int n3) {
        return this.getFlatBlock(n, n2, n3) & 0xF;
    }
    
    @Deprecated
    default void setBlockWithData(final int n, final int n2, final int n3, final int n4, final int n5) {
        this.setFlatBlock(index(n, n2, n3), n4 << 4 | (n5 & 0xF));
    }
    
    @Deprecated
    default void setBlockWithData(final int n, final int n2, final int n3) {
        this.setFlatBlock(n, n2 << 4 | (n3 & 0xF));
    }
    
    @Deprecated
    default void setPaletteIndex(final int n, final int n2) {
        this.palette(PaletteType.BLOCKS).setPaletteIndexAt(n, n2);
    }
    
    @Deprecated
    default int getPaletteIndex(final int n) {
        return this.palette(PaletteType.BLOCKS).paletteIndexAt(n);
    }
    
    @Deprecated
    default int getPaletteSize() {
        return this.palette(PaletteType.BLOCKS).size();
    }
    
    @Deprecated
    default int getPaletteEntry(final int n) {
        return this.palette(PaletteType.BLOCKS).idByIndex(n);
    }
    
    @Deprecated
    default void setPaletteEntry(final int n, final int n2) {
        this.palette(PaletteType.BLOCKS).setIdByIndex(n, n2);
    }
    
    @Deprecated
    default void replacePaletteEntry(final int n, final int n2) {
        this.palette(PaletteType.BLOCKS).replaceId(n, n2);
    }
    
    @Deprecated
    default void addPaletteEntry(final int n) {
        this.palette(PaletteType.BLOCKS).addId(n);
    }
    
    @Deprecated
    default void clearPalette() {
        this.palette(PaletteType.BLOCKS).clear();
    }
    
    int getNonAirBlocksCount();
    
    void setNonAirBlocksCount(final int p0);
    
    default boolean hasLight() {
        return this.getLight() != null;
    }
    
    ChunkSectionLight getLight();
    
    void setLight(final ChunkSectionLight p0);
    
    DataPalette palette(final PaletteType p0);
    
    void addPalette(final PaletteType p0, final DataPalette p1);
    
    void removePalette(final PaletteType p0);
}
