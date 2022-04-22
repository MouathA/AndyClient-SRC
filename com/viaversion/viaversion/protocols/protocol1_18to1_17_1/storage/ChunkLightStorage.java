package com.viaversion.viaversion.protocols.protocol1_18to1_17_1.storage;

import com.viaversion.viaversion.api.connection.*;
import java.util.*;

public final class ChunkLightStorage implements StorableObject
{
    private final Map lightPackets;
    private final Set loadedChunks;
    
    public ChunkLightStorage() {
        this.lightPackets = new HashMap();
        this.loadedChunks = new HashSet();
    }
    
    public void storeLight(final int n, final int n2, final ChunkLight chunkLight) {
        this.lightPackets.put(this.getChunkSectionIndex(n, n2), chunkLight);
    }
    
    public ChunkLight removeLight(final int n, final int n2) {
        return this.lightPackets.remove(this.getChunkSectionIndex(n, n2));
    }
    
    public ChunkLight getLight(final int n, final int n2) {
        return this.lightPackets.get(this.getChunkSectionIndex(n, n2));
    }
    
    public boolean addLoadedChunk(final int n, final int n2) {
        return this.loadedChunks.add(this.getChunkSectionIndex(n, n2));
    }
    
    public boolean isLoaded(final int n, final int n2) {
        return this.loadedChunks.contains(this.getChunkSectionIndex(n, n2));
    }
    
    public void clear(final int n, final int n2) {
        final long chunkSectionIndex = this.getChunkSectionIndex(n, n2);
        this.lightPackets.remove(chunkSectionIndex);
        this.loadedChunks.remove(chunkSectionIndex);
    }
    
    public void clear() {
        this.loadedChunks.clear();
        this.lightPackets.clear();
    }
    
    private long getChunkSectionIndex(final int n, final int n2) {
        return ((long)n & 0x3FFFFFFL) << 38 | ((long)n2 & 0x3FFFFFFL);
    }
    
    public static final class ChunkLight
    {
        private final boolean trustEdges;
        private final long[] skyLightMask;
        private final long[] blockLightMask;
        private final long[] emptySkyLightMask;
        private final long[] emptyBlockLightMask;
        private final byte[][] skyLight;
        private final byte[][] blockLight;
        
        public ChunkLight(final boolean trustEdges, final long[] skyLightMask, final long[] blockLightMask, final long[] emptySkyLightMask, final long[] emptyBlockLightMask, final byte[][] skyLight, final byte[][] blockLight) {
            this.trustEdges = trustEdges;
            this.skyLightMask = skyLightMask;
            this.emptySkyLightMask = emptySkyLightMask;
            this.blockLightMask = blockLightMask;
            this.emptyBlockLightMask = emptyBlockLightMask;
            this.skyLight = skyLight;
            this.blockLight = blockLight;
        }
        
        public boolean trustEdges() {
            return this.trustEdges;
        }
        
        public long[] skyLightMask() {
            return this.skyLightMask;
        }
        
        public long[] emptySkyLightMask() {
            return this.emptySkyLightMask;
        }
        
        public long[] blockLightMask() {
            return this.blockLightMask;
        }
        
        public long[] emptyBlockLightMask() {
            return this.emptyBlockLightMask;
        }
        
        public byte[][] skyLight() {
            return this.skyLight;
        }
        
        public byte[][] blockLight() {
            return this.blockLight;
        }
    }
}
