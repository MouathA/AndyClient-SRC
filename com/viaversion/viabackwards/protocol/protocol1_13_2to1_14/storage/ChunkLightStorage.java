package com.viaversion.viabackwards.protocol.protocol1_13_2to1_14.storage;

import java.lang.reflect.*;
import com.viaversion.viaversion.api.connection.*;
import java.util.*;

public class ChunkLightStorage extends StoredObject
{
    public static final byte[] FULL_LIGHT;
    public static final byte[] EMPTY_LIGHT;
    private static Constructor fastUtilLongObjectHashMap;
    private final Map storedLight;
    
    public ChunkLightStorage(final UserConnection userConnection) {
        super(userConnection);
        this.storedLight = this.createLongObjectMap();
    }
    
    public void setStoredLight(final byte[][] array, final byte[][] array2, final int n, final int n2) {
        this.storedLight.put(this.getChunkSectionIndex(n, n2), new ChunkLight(array, array2));
    }
    
    public ChunkLight getStoredLight(final int n, final int n2) {
        return this.storedLight.get(this.getChunkSectionIndex(n, n2));
    }
    
    public void clear() {
        this.storedLight.clear();
    }
    
    public void unloadChunk(final int n, final int n2) {
        this.storedLight.remove(this.getChunkSectionIndex(n, n2));
    }
    
    private long getChunkSectionIndex(final int n, final int n2) {
        return ((long)n & 0x3FFFFFFL) << 38 | ((long)n2 & 0x3FFFFFFL);
    }
    
    private Map createLongObjectMap() {
        if (ChunkLightStorage.fastUtilLongObjectHashMap != null) {
            return ChunkLightStorage.fastUtilLongObjectHashMap.newInstance(new Object[0]);
        }
        return new HashMap();
    }
    
    static {
        FULL_LIGHT = new byte[2048];
        EMPTY_LIGHT = new byte[2048];
        Arrays.fill(ChunkLightStorage.FULL_LIGHT, (byte)(-1));
        Arrays.fill(ChunkLightStorage.EMPTY_LIGHT, (byte)0);
        ChunkLightStorage.fastUtilLongObjectHashMap = Class.forName("com.viaversion.viaversion.libs.fastutil.longs.Long2ObjectOpenHashMap").getConstructor((Class<?>[])new Class[0]);
    }
    
    public static class ChunkLight
    {
        private final byte[][] skyLight;
        private final byte[][] blockLight;
        
        public ChunkLight(final byte[][] skyLight, final byte[][] blockLight) {
            this.skyLight = skyLight;
            this.blockLight = blockLight;
        }
        
        public byte[][] getSkyLight() {
            return this.skyLight;
        }
        
        public byte[][] getBlockLight() {
            return this.blockLight;
        }
    }
}
