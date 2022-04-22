package com.viaversion.viaversion.api.minecraft.chunks;

import com.viaversion.viaversion.libs.opennbt.tag.builtin.*;
import java.util.*;

public interface Chunk
{
    int getX();
    
    int getZ();
    
    boolean isBiomeData();
    
    boolean isFullChunk();
    
    boolean isIgnoreOldLightData();
    
    void setIgnoreOldLightData(final boolean p0);
    
    int getBitmask();
    
    void setBitmask(final int p0);
    
    BitSet getChunkMask();
    
    void setChunkMask(final BitSet p0);
    
    ChunkSection[] getSections();
    
    void setSections(final ChunkSection[] p0);
    
    int[] getBiomeData();
    
    void setBiomeData(final int[] p0);
    
    CompoundTag getHeightMap();
    
    void setHeightMap(final CompoundTag p0);
    
    List getBlockEntities();
    
    List blockEntities();
}
