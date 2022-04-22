package net.minecraft.world.biome;

import net.minecraft.world.gen.layer.*;
import com.google.common.collect.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class WorldChunkManager
{
    private GenLayer genBiomes;
    private GenLayer biomeIndexLayer;
    private BiomeCache biomeCache;
    private List biomesToSpawnIn;
    private String field_180301_f;
    private static final String __OBFID;
    
    protected WorldChunkManager() {
        this.biomeCache = new BiomeCache(this);
        this.field_180301_f = "";
        (this.biomesToSpawnIn = Lists.newArrayList()).add(BiomeGenBase.forest);
        this.biomesToSpawnIn.add(BiomeGenBase.plains);
        this.biomesToSpawnIn.add(BiomeGenBase.taiga);
        this.biomesToSpawnIn.add(BiomeGenBase.taigaHills);
        this.biomesToSpawnIn.add(BiomeGenBase.forestHills);
        this.biomesToSpawnIn.add(BiomeGenBase.jungle);
        this.biomesToSpawnIn.add(BiomeGenBase.jungleHills);
    }
    
    public WorldChunkManager(final long n, final WorldType worldType, final String field_180301_f) {
        this();
        this.field_180301_f = field_180301_f;
        final GenLayer[] func_180781_a = GenLayer.func_180781_a(n, worldType, field_180301_f);
        this.genBiomes = func_180781_a[0];
        this.biomeIndexLayer = func_180781_a[1];
    }
    
    public WorldChunkManager(final World world) {
        this(world.getSeed(), world.getWorldInfo().getTerrainType(), world.getWorldInfo().getGeneratorOptions());
    }
    
    public List getBiomesToSpawnIn() {
        return this.biomesToSpawnIn;
    }
    
    public BiomeGenBase func_180631_a(final BlockPos blockPos) {
        return this.func_180300_a(blockPos, null);
    }
    
    public BiomeGenBase func_180300_a(final BlockPos blockPos, final BiomeGenBase biomeGenBase) {
        return this.biomeCache.func_180284_a(blockPos.getX(), blockPos.getZ(), biomeGenBase);
    }
    
    public float[] getRainfall(float[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new float[n3 * n4];
        }
        final int[] ints = this.biomeIndexLayer.getInts(n, n2, n3, n4);
        while (0 < n3 * n4) {
            float n5 = BiomeGenBase.getBiomeFromBiomeList(ints[0], BiomeGenBase.field_180279_ad).getIntRainfall() / 65536.0f;
            if (n5 > 1.0f) {
                n5 = 1.0f;
            }
            array[0] = n5;
            int n6 = 0;
            ++n6;
        }
        return array;
    }
    
    public float getTemperatureAtHeight(final float n, final int n2) {
        return n;
    }
    
    public BiomeGenBase[] getBiomesForGeneration(BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4) {
        if (array == null || array.length < n3 * n4) {
            array = new BiomeGenBase[n3 * n4];
        }
        final int[] ints = this.genBiomes.getInts(n, n2, n3, n4);
        while (0 < n3 * n4) {
            array[0] = BiomeGenBase.getBiomeFromBiomeList(ints[0], BiomeGenBase.field_180279_ad);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public BiomeGenBase[] loadBlockGeneratorData(final BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4) {
        return this.getBiomeGenAt(array, n, n2, n3, n4, true);
    }
    
    public BiomeGenBase[] getBiomeGenAt(BiomeGenBase[] array, final int n, final int n2, final int n3, final int n4, final boolean b) {
        if (array == null || array.length < n3 * n4) {
            array = new BiomeGenBase[n3 * n4];
        }
        if (b && n3 == 16 && n4 == 16 && (n & 0xF) == 0x0 && (n2 & 0xF) == 0x0) {
            System.arraycopy(this.biomeCache.getCachedBiomes(n, n2), 0, array, 0, n3 * n4);
            return array;
        }
        final int[] ints = this.biomeIndexLayer.getInts(n, n2, n3, n4);
        while (0 < n3 * n4) {
            array[0] = BiomeGenBase.getBiomeFromBiomeList(ints[0], BiomeGenBase.field_180279_ad);
            int n5 = 0;
            ++n5;
        }
        return array;
    }
    
    public boolean areBiomesViable(final int n, final int n2, final int n3, final List list) {
        final int n4 = n - n3 >> 2;
        final int n5 = n2 - n3 >> 2;
        final int n6 = n + n3 >> 2;
        final int n7 = n2 + n3 >> 2;
        final int n8 = n6 - n4 + 1;
        final int n9 = n7 - n5 + 1;
        final int[] ints = this.genBiomes.getInts(n4, n5, n8, n9);
        while (0 < n8 * n9) {
            if (!list.contains(BiomeGenBase.getBiome(ints[0]))) {
                return false;
            }
            int n10 = 0;
            ++n10;
        }
        return true;
    }
    
    public BlockPos findBiomePosition(final int n, final int n2, final int n3, final List list, final Random random) {
        final int n4 = n - n3 >> 2;
        final int n5 = n2 - n3 >> 2;
        final int n6 = n + n3 >> 2;
        final int n7 = n2 + n3 >> 2;
        final int n8 = n6 - n4 + 1;
        final int n9 = n7 - n5 + 1;
        final int[] ints = this.genBiomes.getInts(n4, n5, n8, n9);
        BlockPos blockPos = null;
        while (0 < n8 * n9) {
            final int n10 = n4 + 0 % n8 << 2;
            final int n11 = n5 + 0 / n8 << 2;
            if (list.contains(BiomeGenBase.getBiome(ints[0])) && (blockPos == null || random.nextInt(1) == 0)) {
                blockPos = new BlockPos(n10, 0, n11);
                int n12 = 0;
                ++n12;
            }
            int n13 = 0;
            ++n13;
        }
        return blockPos;
    }
    
    public void cleanupCache() {
        this.biomeCache.cleanupCache();
    }
    
    static {
        __OBFID = "CL_00000166";
    }
}
