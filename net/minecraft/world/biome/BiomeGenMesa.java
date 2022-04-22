package net.minecraft.world.biome;

import net.minecraft.block.state.*;
import net.minecraft.world.gen.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import java.util.*;

public class BiomeGenMesa extends BiomeGenBase
{
    private IBlockState[] field_150621_aC;
    private long field_150622_aD;
    private NoiseGeneratorPerlin field_150623_aE;
    private NoiseGeneratorPerlin field_150624_aF;
    private NoiseGeneratorPerlin field_150625_aG;
    private boolean field_150626_aH;
    private boolean field_150620_aI;
    private static final String __OBFID;
    
    public BiomeGenMesa(final int n, final boolean field_150626_aH, final boolean field_150620_aI) {
        super(n);
        this.field_150626_aH = field_150626_aH;
        this.field_150620_aI = field_150620_aI;
        this.setDisableRain();
        this.setTemperatureRainfall(2.0f, 0.0f);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand.getDefaultState().withProperty(BlockSand.VARIANT_PROP, BlockSand.EnumType.RED_SAND);
        this.fillerBlock = Blocks.stained_hardened_clay.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 20;
        this.theBiomeDecorator.reedsPerChunk = 3;
        this.theBiomeDecorator.cactiPerChunk = 5;
        this.theBiomeDecorator.flowersPerChunk = 0;
        this.spawnableCreatureList.clear();
        if (field_150620_aI) {
            this.theBiomeDecorator.treesPerChunk = 5;
        }
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (WorldGenAbstractTree)this.worldGeneratorTrees;
    }
    
    @Override
    public int func_180625_c(final BlockPos blockPos) {
        return 10387789;
    }
    
    @Override
    public int func_180627_b(final BlockPos blockPos) {
        return 9470285;
    }
    
    @Override
    public void func_180624_a(final World world, final Random random, final BlockPos blockPos) {
        super.func_180624_a(world, random, blockPos);
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        if (this.field_150621_aC == null || this.field_150622_aD != world.getSeed()) {
            this.func_150619_a(world.getSeed());
        }
        if (this.field_150623_aE == null || this.field_150624_aF == null || this.field_150622_aD != world.getSeed()) {
            final Random random2 = new Random(this.field_150622_aD);
            this.field_150623_aE = new NoiseGeneratorPerlin(random2, 4);
            this.field_150624_aF = new NoiseGeneratorPerlin(random2, 1);
        }
        this.field_150622_aD = world.getSeed();
        if (this.field_150626_aH) {
            final int n4 = (n & 0xFFFFFFF0) + (n2 & 0xF);
            final int n5 = (n2 & 0xFFFFFFF0) + (n & 0xF);
            final double min = Math.min(Math.abs(n3), this.field_150623_aE.func_151601_a(n4 * 0.25, n5 * 0.25));
            if (min > 0.0) {
                final double n6 = 0.001953125;
                final double abs = Math.abs(this.field_150624_aF.func_151601_a(n4 * n6, n5 * n6));
                double n7 = min * min * 2.5;
                final double n8 = Math.ceil(abs * 50.0) + 14.0;
                if (n7 > n8) {
                    n7 = n8;
                }
            }
        }
        Blocks.stained_hardened_clay.getDefaultState();
        final IBlockState fillerBlock = this.fillerBlock;
        final int n9 = (int)(n3 / 3.0 + 3.0 + random.nextDouble() * 0.25);
        final boolean b = Math.cos(n3 / 3.0 * 3.141592653589793) > 0.0;
    }
    
    private void func_150619_a(final long n) {
        Arrays.fill(this.field_150621_aC = new IBlockState[64], Blocks.hardened_clay.getDefaultState());
        final Random random = new Random(n);
        this.field_150625_aG = new NoiseGeneratorPerlin(random, 1);
        final int n2 = random.nextInt(4) + 2;
        final int n3 = random.nextInt(4) + 2;
        final int n4 = random.nextInt(4) + 2;
        final int n5 = random.nextInt(3) + 3;
    }
    
    private IBlockState func_180629_a(final int n, final int n2, final int n3) {
        return this.field_150621_aC[(n2 + (int)Math.round(this.field_150625_aG.func_151601_a(n * 1.0 / 512.0, n * 1.0 / 512.0) * 2.0) + 64) % 64];
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        final boolean b = this.biomeID == BiomeGenBase.mesa.biomeID;
        final BiomeGenMesa biomeGenMesa = new BiomeGenMesa(n, b, this.field_150620_aI);
        if (!b) {
            biomeGenMesa.setHeight(BiomeGenMesa.height_LowHills);
            biomeGenMesa.setBiomeName(String.valueOf(this.biomeName) + " M");
        }
        else {
            biomeGenMesa.setBiomeName(String.valueOf(this.biomeName) + " (Bryce)");
        }
        biomeGenMesa.func_150557_a(this.color, true);
        return biomeGenMesa;
    }
    
    static {
        __OBFID = "CL_00000176";
    }
}
