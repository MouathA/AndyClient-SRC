package net.minecraft.world.biome;

import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class BiomeGenSavanna extends BiomeGenBase
{
    private static final WorldGenSavannaTree field_150627_aC;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000182";
        field_150627_aC = new WorldGenSavannaTree(false);
    }
    
    protected BiomeGenSavanna(final int n) {
        super(n);
        this.spawnableCreatureList.add(new SpawnListEntry(EntityHorse.class, 1, 2, 6));
        this.theBiomeDecorator.treesPerChunk = 1;
        this.theBiomeDecorator.flowersPerChunk = 4;
        this.theBiomeDecorator.grassPerChunk = 20;
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (WorldGenAbstractTree)((random.nextInt(5) > 0) ? BiomeGenSavanna.field_150627_aC : this.worldGeneratorTrees);
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        final Mutated mutated = new Mutated(n, this);
        mutated.temperature = (this.temperature + 1.0f) * 0.5f;
        mutated.minHeight = this.minHeight * 0.5f + 0.3f;
        mutated.maxHeight = this.maxHeight * 0.5f + 1.2f;
        return mutated;
    }
    
    @Override
    public void func_180624_a(final World world, final Random random, final BlockPos blockPos) {
        BiomeGenSavanna.field_180280_ag.func_180710_a(BlockDoublePlant.EnumPlantType.GRASS);
        super.func_180624_a(world, random, blockPos);
    }
    
    public static class Mutated extends BiomeGenMutated
    {
        private static final String __OBFID;
        
        public Mutated(final int n, final BiomeGenBase biomeGenBase) {
            super(n, biomeGenBase);
            this.theBiomeDecorator.treesPerChunk = 2;
            this.theBiomeDecorator.flowersPerChunk = 2;
            this.theBiomeDecorator.grassPerChunk = 5;
        }
        
        @Override
        public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
            this.topBlock = Blocks.grass.getDefaultState();
            this.fillerBlock = Blocks.dirt.getDefaultState();
            if (n3 > 1.75) {
                this.topBlock = Blocks.stone.getDefaultState();
                this.fillerBlock = Blocks.stone.getDefaultState();
            }
            else if (n3 > -0.5) {
                this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
            }
            this.func_180628_b(world, random, chunkPrimer, n, n2, n3);
        }
        
        @Override
        public void func_180624_a(final World world, final Random random, final BlockPos blockPos) {
            this.theBiomeDecorator.func_180292_a(world, random, this, blockPos);
        }
        
        static {
            __OBFID = "CL_00000183";
        }
    }
}
