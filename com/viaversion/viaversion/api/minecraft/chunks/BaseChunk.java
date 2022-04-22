package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;

public class BaseChunk implements Chunk
{
    protected final int x;
    protected final int z;
    protected final boolean fullChunk;
    protected boolean ignoreOldLightData;
    protected BitSet chunkSectionBitSet;
    protected int bitmask;
    protected ChunkSection[] sections;
    protected int[] biomeData;
    protected CompoundTag heightMap;
    protected final List blockEntities;
    
    public BaseChunk(final int x, final int z, final boolean fullChunk, final boolean ignoreOldLightData, final BitSet chunkSectionBitSet, final ChunkSection[] sections, final int[] biomeData, final CompoundTag heightMap, final List blockEntities) {
        this.x = x;
        this.z = z;
        this.fullChunk = fullChunk;
        this.ignoreOldLightData = ignoreOldLightData;
        this.chunkSectionBitSet = chunkSectionBitSet;
        this.sections = sections;
        this.biomeData = biomeData;
        this.heightMap = heightMap;
        this.blockEntities = blockEntities;
    }
    
    public BaseChunk(final int n, final int n2, final boolean b, final boolean b2, final int bitmask, final ChunkSection[] array, final int[] array2, final CompoundTag compoundTag, final List list) {
        this(n, n2, b, b2, null, array, array2, compoundTag, list);
        this.bitmask = bitmask;
    }
    
    public BaseChunk(final int n, final int n2, final boolean b, final boolean b2, final int n3, final ChunkSection[] array, final int[] array2, final List list) {
        this(n, n2, b, b2, n3, array, array2, null, list);
    }
    
    @Override
    public boolean isBiomeData() {
        return this.biomeData != null;
    }
    
    @Override
    public int getX() {
        return this.x;
    }
    
    @Override
    public int getZ() {
        return this.z;
    }
    
    @Override
    public boolean isFullChunk() {
        return this.fullChunk;
    }
    
    @Override
    public boolean isIgnoreOldLightData() {
        return this.ignoreOldLightData;
    }
    
    @Override
    public void setIgnoreOldLightData(final boolean ignoreOldLightData) {
        this.ignoreOldLightData = ignoreOldLightData;
    }
    
    @Override
    public int getBitmask() {
        return this.bitmask;
    }
    
    @Override
    public void setBitmask(final int bitmask) {
        this.bitmask = bitmask;
    }
    
    @Override
    public BitSet getChunkMask() {
        return this.chunkSectionBitSet;
    }
    
    @Override
    public void setChunkMask(final BitSet chunkSectionBitSet) {
        this.chunkSectionBitSet = chunkSectionBitSet;
    }
    
    @Override
    public ChunkSection[] getSections() {
        return this.sections;
    }
    
    @Override
    public void setSections(final ChunkSection[] sections) {
        this.sections = sections;
    }
    
    @Override
    public int[] getBiomeData() {
        return this.biomeData;
    }
    
    @Override
    public void setBiomeData(final int[] biomeData) {
        this.biomeData = biomeData;
    }
    
    @Override
    public CompoundTag getHeightMap() {
        return this.heightMap;
    }
    
    @Override
    public void setHeightMap(final CompoundTag heightMap) {
        this.heightMap = heightMap;
    }
    
    @Override
    public List getBlockEntities() {
        return this.blockEntities;
    }
    
    @Override
    public List blockEntities() {
        throw new UnsupportedOperationException();
    }
}
