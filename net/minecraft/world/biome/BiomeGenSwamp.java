package net.minecraft.world.biome;

import net.minecraft.entity.monster.*;
import java.util.*;
import net.minecraft.world.gen.feature.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.world.*;
import net.minecraft.world.chunk.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;

public class BiomeGenSwamp extends BiomeGenBase
{
    private static final String __OBFID;
    
    protected BiomeGenSwamp(final int n) {
        super(n);
        this.theBiomeDecorator.treesPerChunk = 2;
        this.theBiomeDecorator.flowersPerChunk = 1;
        this.theBiomeDecorator.deadBushPerChunk = 1;
        this.theBiomeDecorator.mushroomsPerChunk = 8;
        this.theBiomeDecorator.reedsPerChunk = 10;
        this.theBiomeDecorator.clayPerChunk = 1;
        this.theBiomeDecorator.waterlilyPerChunk = 4;
        this.theBiomeDecorator.sandPerChunk2 = 0;
        this.theBiomeDecorator.sandPerChunk = 0;
        this.theBiomeDecorator.grassPerChunk = 5;
        this.waterColorMultiplier = 14745518;
        this.spawnableMonsterList.add(new SpawnListEntry(EntitySlime.class, 1, 1, 1));
    }
    
    @Override
    public WorldGenAbstractTree genBigTreeChance(final Random random) {
        return (WorldGenAbstractTree)this.worldGeneratorSwamp;
    }
    
    @Override
    public int func_180627_b(final BlockPos blockPos) {
        return (BiomeGenSwamp.field_180281_af.func_151601_a(blockPos.getX() * 0.0225, blockPos.getZ() * 0.0225) < -0.1) ? 5011004 : 6975545;
    }
    
    @Override
    public int func_180625_c(final BlockPos blockPos) {
        return 6975545;
    }
    
    @Override
    public BlockFlower.EnumFlowerType pickRandomFlower(final Random random, final BlockPos blockPos) {
        return BlockFlower.EnumFlowerType.BLUE_ORCHID;
    }
    
    @Override
    public void genTerrainBlocks(final World world, final Random random, final ChunkPrimer chunkPrimer, final int n, final int n2, final double n3) {
        final double func_151601_a = BiomeGenSwamp.field_180281_af.func_151601_a(n * 0.25, n2 * 0.25);
        if (func_151601_a > 0.0) {
            final int n4 = n & 0xF;
            final int n5 = n2 & 0xF;
            while (255 >= 0) {
                if (chunkPrimer.getBlockState(n5, 255, n4).getBlock().getMaterial() != Material.air) {
                    if (255 != 62 || chunkPrimer.getBlockState(n5, 255, n4).getBlock() == Blocks.water) {
                        break;
                    }
                    chunkPrimer.setBlockState(n5, 255, n4, Blocks.water.getDefaultState());
                    if (func_151601_a < 0.12) {
                        chunkPrimer.setBlockState(n5, 256, n4, Blocks.waterlily.getDefaultState());
                        break;
                    }
                    break;
                }
                else {
                    int n6 = 0;
                    --n6;
                }
            }
        }
        this.func_180628_b(world, random, chunkPrimer, n, n2, n3);
    }
    
    static {
        __OBFID = "CL_00000185";
    }
}
