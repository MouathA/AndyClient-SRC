package net.minecraft.world.biome;

import net.minecraft.init.*;
import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.*;
import net.minecraft.block.properties.*;

public class BiomeGenTaiga extends BiomeGenBase
{
    private static final WorldGenTaiga1 field_150639_aC;
    private static final WorldGenTaiga2 field_150640_aD;
    private static final WorldGenMegaPineTree field_150641_aE;
    private static final WorldGenMegaPineTree field_150642_aF;
    private static final WorldGenBlockBlob field_150643_aG;
    private int field_150644_aH;
    private static final String __OBFID;
    
    static {
        __OBFID = "CL_00000186";
        field_150639_aC = new WorldGenTaiga1();
        field_150640_aD = new WorldGenTaiga2(false);
        field_150641_aE = new WorldGenMegaPineTree(false, false);
        field_150642_aF = new WorldGenMegaPineTree(false, true);
        field_150643_aG = new WorldGenBlockBlob(Blocks.mossy_cobblestone, 0);
    }
    
    public BiomeGenTaiga(final int n, final int field_150644_aH) {
        super(n);
        this.field_150644_aH = field_150644_aH;
        this.spawnableCreatureList.add(new SpawnListEntry(EntityWolf.class, 8, 4, 4));
        this.theBiomeDecorator.treesPerChunk = 10;
        if (field_150644_aH != 1 && field_150644_aH != 2) {
            this.theBiomeDecorator.grassPerChunk = 1;
            this.theBiomeDecorator.mushroomsPerChunk = 1;
        }
        else {
            this.theBiomeDecorator.grassPerChunk = 7;
            this.theBiomeDecorator.deadBushPerChunk = 1;
            this.theBiomeDecorator.mushroomsPerChunk = 3;
        }
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (WorldGenAbstractTree)(((this.field_150644_aH == 1 || this.field_150644_aH == 2) && random.nextInt(3) == 0) ? ((this.field_150644_aH != 2 && random.nextInt(13) != 0) ? BiomeGenTaiga.field_150641_aE : BiomeGenTaiga.field_150642_aF) : ((random.nextInt(3) == 0) ? BiomeGenTaiga.field_150639_aC : BiomeGenTaiga.field_150640_aD));
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(final Random random) {
        return (WorldGenerator)((random.nextInt(5) > 0) ? new WorldGenTallGrass(BlockTallGrass.EnumType.FERN) : new WorldGenTallGrass(BlockTallGrass.EnumType.GRASS));
    }
    
    @Override
    public void func_180624_a(final World world, final Random random, final BlockPos blockPos) {
        int nextInt = 0;
        if (this.field_150644_aH == 1 || this.field_150644_aH == 2) {
            nextInt = random.nextInt(3);
            while (0 < 0) {
                BiomeGenTaiga.field_150643_aG.generate(world, random, world.getHorizon(blockPos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)));
                int n = 0;
                ++n;
            }
        }
        BiomeGenTaiga.field_180280_ag.func_180710_a(BlockDoublePlant.EnumPlantType.FERN);
        while (0 < 7) {
            final int n = random.nextInt(16) + 8;
            final int n2 = random.nextInt(16) + 8;
            BiomeGenTaiga.field_180280_ag.generate(world, random, blockPos.add(0, random.nextInt(world.getHorizon(blockPos.add(0, 0, n2)).getY() + 32), n2));
            ++nextInt;
        }
        super.func_180624_a(world, random, blockPos);
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        if (this.field_150644_aH == 1 || this.field_150644_aH == 2) {
            this.topBlock = Blocks.grass.getDefaultState();
            this.fillerBlock = Blocks.dirt.getDefaultState();
            if (n3 > 1.75) {
                this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.COARSE_DIRT);
            }
            else if (n3 > -0.95) {
                this.topBlock = Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt.DirtType.PODZOL);
            }
        }
        this.func_180628_b(world, random, chunkPrimer, n, n2, n3);
    }
    
    @Override
    protected BiomeGenBase createMutatedBiome(final int n) {
        return (this.biomeID == BiomeGenBase.megaTaiga.biomeID) ? new BiomeGenTaiga(n, 2).func_150557_a(5858897, true).setBiomeName("Mega Spruce Taiga").setFillerBlockMetadata(5159473).setTemperatureRainfall(0.25f, 0.8f).setHeight(new Height(this.minHeight, this.maxHeight)) : super.createMutatedBiome(n);
    }
}
