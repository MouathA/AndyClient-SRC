package com.viaversion.viaversion.api.minecraft.chunks;

public enum PaletteType
{
    BLOCKS("BLOCKS", 0, 4096, 8), 
    BIOMES("BIOMES", 1, 64, 3);
    
    private final int size;
    private final int highestBitsPerValue;
    private static final PaletteType[] $VALUES;
    
    private PaletteType(final String s, final int n, final int size, final int highestBitsPerValue) {
        this.size = size;
        this.highestBitsPerValue = highestBitsPerValue;
    }
    
    public int size() {
        return this.size;
    }
    
    public int highestBitsPerValue() {
        return this.highestBitsPerValue;
    }
    
    static {
        $VALUES = new PaletteType[] { PaletteType.BLOCKS, PaletteType.BIOMES };
    }
}
