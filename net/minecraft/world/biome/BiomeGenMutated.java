package net.minecraft.world.biome;

import com.google.common.collect.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenMutated extends BiomeGenBase
{
    protected BiomeGenBase baseBiome;
    private static final String __OBFID;
    
    public BiomeGenMutated(final int n, final BiomeGenBase baseBiome) {
        super(n);
        this.baseBiome = baseBiome;
        this.func_150557_a(baseBiome.color, true);
        this.biomeName = String.valueOf(baseBiome.biomeName) + " M";
        this.topBlock = baseBiome.topBlock;
        this.fillerBlock = baseBiome.fillerBlock;
        this.fillerBlockMetadata = baseBiome.fillerBlockMetadata;
        this.minHeight = baseBiome.minHeight;
        this.maxHeight = baseBiome.maxHeight;
        this.temperature = baseBiome.temperature;
        this.rainfall = baseBiome.rainfall;
        this.waterColorMultiplier = baseBiome.waterColorMultiplier;
        this.enableSnow = baseBiome.enableSnow;
        this.enableRain = baseBiome.enableRain;
        this.spawnableCreatureList = Lists.newArrayList(baseBiome.spawnableCreatureList);
        this.spawnableMonsterList = Lists.newArrayList(baseBiome.spawnableMonsterList);
        this.spawnableCaveCreatureList = Lists.newArrayList(baseBiome.spawnableCaveCreatureList);
        this.spawnableWaterCreatureList = Lists.newArrayList(baseBiome.spawnableWaterCreatureList);
        this.temperature = baseBiome.temperature;
        this.rainfall = baseBiome.rainfall;
        this.minHeight = baseBiome.minHeight + 0.1f;
        this.maxHeight = baseBiome.maxHeight + 0.2f;
    }
    
    @Override
    public void func_180624_a(final World world, final Random random, final BlockPos blockPos) {
        this.baseBiome.theBiomeDecorator.func_180292_a(world, random, this, blockPos);
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        this.baseBiome.genTerrainBlocks(world, random, chunkPrimer, n, n2, n3);
    }
    
    @Override
    public float getSpawningChance() {
        return this.baseBiome.getSpawningChance();
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return this.baseBiome.genBigTreeChance(random);
    }
    
    @Override
    public int func_180625_c(final BlockPos blockPos) {
        return this.baseBiome.func_180625_c(blockPos);
    }
    
    @Override
    public int func_180627_b(final BlockPos blockPos) {
        return this.baseBiome.func_180627_b(blockPos);
    }
    
    @Override
    public Class getBiomeClass() {
        return this.baseBiome.getBiomeClass();
    }
    
    @Override
    public boolean isEqualTo(final BiomeGenBase biomeGenBase) {
        return this.baseBiome.isEqualTo(biomeGenBase);
    }
    
    @Override
    public TempCategory getTempCategory() {
        return this.baseBiome.getTempCategory();
    }
    
    static {
        __OBFID = "CL_00000178";
    }
}
