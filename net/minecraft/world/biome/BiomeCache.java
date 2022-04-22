package net.minecraft.world.biome;

import net.minecraft.util.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.server.*;

public class BiomeCache
{
    private final WorldChunkManager chunkManager;
    private long lastCleanupTime;
    private LongHashMap cacheMap;
    private List cache;
    private static final String __OBFID;
    
    public BiomeCache(final WorldChunkManager chunkManager) {
        this.cacheMap = new LongHashMap();
        this.cache = Lists.newArrayList();
        this.chunkManager = chunkManager;
    }
    
    public Block getBiomeCacheBlock(int n, int n2) {
        n >>= 4;
        n2 >>= 4;
        final long n3 = ((long)n & 0xFFFFFFFFL) | ((long)n2 & 0xFFFFFFFFL) << 32;
        Block block = (Block)this.cacheMap.getValueByKey(n3);
        if (block == null) {
            block = new Block(n, n2);
            this.cacheMap.add(n3, block);
            this.cache.add(block);
        }
        block.lastAccessTime = MinecraftServer.getCurrentTimeMillis();
        return block;
    }
    
    public BiomeGenBase func_180284_a(final int n, final int n2, final BiomeGenBase biomeGenBase) {
        final BiomeGenBase biomeGen = this.getBiomeCacheBlock(n, n2).getBiomeGenAt(n, n2);
        return (biomeGen == null) ? biomeGenBase : biomeGen;
    }
    
    public void cleanupCache() {
        final long currentTimeMillis = MinecraftServer.getCurrentTimeMillis();
        final long n = currentTimeMillis - this.lastCleanupTime;
        if (n > 7500L || n < 0L) {
            this.lastCleanupTime = currentTimeMillis;
            while (0 < this.cache.size()) {
                final Block block = this.cache.get(0);
                final long n2 = currentTimeMillis - block.lastAccessTime;
                int n4 = 0;
                if (n2 > 30000L || n2 < 0L) {
                    final List cache = this.cache;
                    final int n3 = 0;
                    --n4;
                    cache.remove(n3);
                    this.cacheMap.remove(((long)block.xPosition & 0xFFFFFFFFL) | ((long)block.zPosition & 0xFFFFFFFFL) << 32);
                }
                ++n4;
            }
        }
    }
    
    public BiomeGenBase[] getCachedBiomes(final int n, final int n2) {
        return this.getBiomeCacheBlock(n, n2).biomes;
    }
    
    static WorldChunkManager access$0(final BiomeCache biomeCache) {
        return biomeCache.chunkManager;
    }
    
    static {
        __OBFID = "CL_00000162";
    }
    
    public class Block
    {
        public float[] rainfallValues;
        public BiomeGenBase[] biomes;
        public int xPosition;
        public int zPosition;
        public long lastAccessTime;
        private static final String __OBFID;
        final BiomeCache this$0;
        
        public Block(final BiomeCache this$0, final int xPosition, final int zPosition) {
            this.this$0 = this$0;
            this.rainfallValues = new float[256];
            this.biomes = new BiomeGenBase[256];
            this.xPosition = xPosition;
            this.zPosition = zPosition;
            BiomeCache.access$0(this$0).getRainfall(this.rainfallValues, xPosition << 4, zPosition << 4, 16, 16);
            BiomeCache.access$0(this$0).getBiomeGenAt(this.biomes, xPosition << 4, zPosition << 4, 16, 16, false);
        }
        
        public BiomeGenBase getBiomeGenAt(final int n, final int n2) {
            return this.biomes[(n & 0xF) | (n2 & 0xF) << 4];
        }
        
        static {
            __OBFID = "CL_00000163";
        }
    }
}
