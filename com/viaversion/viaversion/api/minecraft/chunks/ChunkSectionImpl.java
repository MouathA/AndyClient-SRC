package com.viaversion.viaversion.api.minecraft.chunks;

import java.util.*;

public class ChunkSectionImpl implements ChunkSection
{
    private final EnumMap palettes;
    private ChunkSectionLight light;
    private int nonAirBlocksCount;
    
    public ChunkSectionImpl() {
        this.palettes = new EnumMap((Class<K>)PaletteType.class);
    }
    
    public ChunkSectionImpl(final boolean b) {
        this.palettes = new EnumMap((Class<K>)PaletteType.class);
        this.addPalette(PaletteType.BLOCKS, new DataPaletteImpl(4096));
        if (b) {
            this.light = new ChunkSectionLightImpl();
        }
    }
    
    public ChunkSectionImpl(final boolean b, final int n) {
        this.palettes = new EnumMap((Class<K>)PaletteType.class);
        this.addPalette(PaletteType.BLOCKS, new DataPaletteImpl(4096, n));
        if (b) {
            this.light = new ChunkSectionLightImpl();
        }
    }
    
    @Override
    public int getNonAirBlocksCount() {
        return this.nonAirBlocksCount;
    }
    
    @Override
    public void setNonAirBlocksCount(final int nonAirBlocksCount) {
        this.nonAirBlocksCount = nonAirBlocksCount;
    }
    
    @Override
    public ChunkSectionLight getLight() {
        return this.light;
    }
    
    @Override
    public void setLight(final ChunkSectionLight light) {
        this.light = light;
    }
    
    @Override
    public DataPalette palette(final PaletteType paletteType) {
        return this.palettes.get(paletteType);
    }
    
    @Override
    public void addPalette(final PaletteType paletteType, final DataPalette dataPalette) {
        this.palettes.put(paletteType, dataPalette);
    }
    
    @Override
    public void removePalette(final PaletteType paletteType) {
        this.palettes.remove(paletteType);
    }
}
