package net.minecraft.world.biome;

import net.minecraft.init.*;
import net.minecraft.world.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.gen.feature.*;

public class BiomeGenDesert extends BiomeGenBase
{
    private static final String __OBFID;
    
    public BiomeGenDesert(final int n) {
        super(n);
        this.spawnableCreatureList.clear();
        this.topBlock = Blocks.sand.getDefaultState();
        this.fillerBlock = Blocks.sand.getDefaultState();
        this.theBiomeDecorator.treesPerChunk = -999;
        this.theBiomeDecorator.deadBushPerChunk = 2;
        this.theBiomeDecorator.reedsPerChunk = 50;
        this.theBiomeDecorator.cactiPerChunk = 10;
        this.spawnableCreatureList.clear();
    }
    
    @Override
    public void func_180624_a(final World world, final Random random, final BlockPos blockPos) {
        super.func_180624_a(world, random, blockPos);
        if (random.nextInt(1000) == 0) {
            new WorldGenDesertWells().generate(world, random, world.getHorizon(blockPos.add(random.nextInt(16) + 8, 0, random.nextInt(16) + 8)).offsetUp());
        }
    }
    
    static {
        __OBFID = "CL_00000167";
    }
}
