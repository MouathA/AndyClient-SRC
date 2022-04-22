package net.minecraft.client.renderer;

import net.minecraft.block.state.*;
import net.minecraft.init.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;
import net.minecraft.tileentity.*;
import net.minecraft.world.chunk.*;
import optifine.*;

public class RegionRenderCache extends ChunkCache
{
    private static final IBlockState field_175632_f;
    private final BlockPos field_175633_g;
    private int[] field_175634_h;
    private IBlockState[] field_175635_i;
    private static final String __OBFID;
    private static ArrayDeque cacheLights;
    private static ArrayDeque cacheStates;
    private static int maxCacheSize;
    
    static {
        __OBFID = "CL_00002565";
        field_175632_f = Blocks.air.getDefaultState();
        RegionRenderCache.cacheLights = new ArrayDeque();
        RegionRenderCache.cacheStates = new ArrayDeque();
        RegionRenderCache.maxCacheSize = Config.limit(Runtime.getRuntime().availableProcessors(), 1, 32);
    }
    
    public RegionRenderCache(final World world, final BlockPos blockPos, final BlockPos blockPos2, final int n) {
        super(world, blockPos, blockPos2, n);
        this.field_175633_g = blockPos.subtract(new Vec3i(n, n, n));
        Arrays.fill(this.field_175634_h = allocateLights(8000), -1);
        this.field_175635_i = allocateStates(8000);
    }
    
    @Override
    public TileEntity getTileEntity(final BlockPos blockPos) {
        return this.chunkArray[(blockPos.getX() >> 4) - this.chunkX][(blockPos.getZ() >> 4) - this.chunkZ].func_177424_a(blockPos, Chunk.EnumCreateEntityType.QUEUED);
    }
    
    @Override
    public int getCombinedLight(final BlockPos blockPos, final int n) {
        final int func_175630_e = this.func_175630_e(blockPos);
        int n2 = this.field_175634_h[func_175630_e];
        if (n2 == -1) {
            n2 = super.getCombinedLight(blockPos, n);
            if (Config.isDynamicLights() && !this.getBlockState(blockPos).getBlock().isOpaqueCube()) {
                n2 = DynamicLights.getCombinedLight(blockPos, n2);
            }
            this.field_175634_h[func_175630_e] = n2;
        }
        return n2;
    }
    
    @Override
    public IBlockState getBlockState(final BlockPos blockPos) {
        final int func_175630_e = this.func_175630_e(blockPos);
        IBlockState func_175631_c = this.field_175635_i[func_175630_e];
        if (func_175631_c == null) {
            func_175631_c = this.func_175631_c(blockPos);
            this.field_175635_i[func_175630_e] = func_175631_c;
        }
        return func_175631_c;
    }
    
    private IBlockState func_175631_c(final BlockPos blockPos) {
        if (blockPos.getY() >= 0 && blockPos.getY() < 256) {
            return this.chunkArray[(blockPos.getX() >> 4) - this.chunkX][(blockPos.getZ() >> 4) - this.chunkZ].getBlockState(blockPos);
        }
        return RegionRenderCache.field_175632_f;
    }
    
    private int func_175630_e(final BlockPos blockPos) {
        return (blockPos.getX() - this.field_175633_g.getX()) * 400 + (blockPos.getZ() - this.field_175633_g.getZ()) * 20 + (blockPos.getY() - this.field_175633_g.getY());
    }
    
    public void freeBuffers() {
        freeLights(this.field_175634_h);
        freeStates(this.field_175635_i);
    }
    
    private static int[] allocateLights(final int n) {
        final ArrayDeque cacheLights = RegionRenderCache.cacheLights;
        // monitorenter(cacheLights2 = RegionRenderCache.cacheLights)
        int[] array = RegionRenderCache.cacheLights.pollLast();
        if (array == null || array.length < n) {
            array = new int[n];
        }
        // monitorexit(cacheLights2)
        return array;
    }
    
    public static void freeLights(final int[] array) {
        final ArrayDeque cacheLights = RegionRenderCache.cacheLights;
        // monitorenter(cacheLights2 = RegionRenderCache.cacheLights)
        if (RegionRenderCache.cacheLights.size() < RegionRenderCache.maxCacheSize) {
            RegionRenderCache.cacheLights.add(array);
        }
    }
    // monitorexit(cacheLights2)
    
    private static IBlockState[] allocateStates(final int n) {
        final ArrayDeque cacheStates = RegionRenderCache.cacheStates;
        // monitorenter(cacheStates2 = RegionRenderCache.cacheStates)
        IBlockState[] array = RegionRenderCache.cacheStates.pollLast();
        if (array != null && array.length >= n) {
            Arrays.fill(array, null);
        }
        else {
            array = new IBlockState[n];
        }
        // monitorexit(cacheStates2)
        return array;
    }
    
    public static void freeStates(final IBlockState[] array) {
        final ArrayDeque cacheStates = RegionRenderCache.cacheStates;
        // monitorenter(cacheStates2 = RegionRenderCache.cacheStates)
        if (RegionRenderCache.cacheStates.size() < RegionRenderCache.maxCacheSize) {
            RegionRenderCache.cacheStates.add(array);
        }
    }
    // monitorexit(cacheStates2)
}
